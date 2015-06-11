package domemo.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


import domemo.player.GUIHumanPlayer;
import domemo.player.LittleSmartComputer;
import domemo.player.Player;
import domemo.player.SampleComputer;
import domemo.result.Result;
import domemo.util.WaitTime;
import domemo.view.ViewFrame;
import javax.swing.Timer;


public class DomemoManager {
	/** ゲーム中のプレイヤー  */
	private ArrayList<Player> players;
	/** ゲーム中、終わったプレイヤーに関わらない全てのプレイヤー  */
	private LinkedHashMap<Integer,Player> allPlayers;
	/** 終わったプレイヤー　勝った順番に入れる 0番目→１位 */
	private ArrayList<Player> finishPlayers;
	/** 場にオープンになっているカード  */
	private Hand openCards;
	/** 場に伏せられているカード */
	private Hand closedCards;
	/** 各プレイヤーの手札  */
	private LinkedHashMap<Player,Hand> playerHands;
	/** プレイヤーの発言ログ  */
	private ArrayList<Statement> playerStatements;
	
	/** 参加人数 */
	private int playerCount; 
	/** ターン数(1人のプレイヤーの行動が終わると+1) */
	private int turnCount=0;
	/** GUIモードで起動されてるかどうか */
	private boolean isGUI=false;
	/** オートモード(自動進行)かどうか  */
	private boolean isAutoMode=false;
	/** 待機するミリ秒  */
	private int waitMillis=1500;
	/** 何回ゲームがプレイされたか */
	private static int gameCount=0; 
	
	private int nextPlayerID=0;
	
	ViewFrame frame;
	WaitTime waitTime;
	
	/**
	 * 
	 * @param playerCount 参加人数
	 * @param isGUI   GUIモードかどうか
	 */
	public DomemoManager(int playerCount,boolean isGUI){
		players=new ArrayList<Player>();
		finishPlayers=new ArrayList<Player>();
		allPlayers=new LinkedHashMap<Integer,Player>();
		playerStatements=new ArrayList<Statement>();
		playerHands=new LinkedHashMap<Player,Hand>();
		waitTime=new WaitTime();
		this.isGUI=isGUI;
		this.playerCount=playerCount;
	}
	public DomemoManager(){
		this(4,true);
	}

	public int getPlayerCount(){
		return playerCount;
	}
	/** ターンが終わった後に待機するミリ秒をセット */
	public void setWaitMillis(int millis){
		this.waitMillis=millis;
	}
	public boolean isAutoMode(){ return isAutoMode; }
	public void setAutoMode(boolean flg){
		isAutoMode=flg;
	}
	/** idが一致するプレイヤーの名前(toString)を返す */
	public String getPlayerName(int id){
		if(id<0 || id>=playerCount) return "";
		return allPlayers.get(id).toString();
	}
	/** 現在場に表に表示されているカードを返す  */
	public Hand getOpenCards(){
		return openCards;
	}
	/**  全プレイヤーの発言履歴を返す */
	public ArrayList<Statement> getPlayerStatements(){
		return playerStatements;
	}
	/**  現在ゲームに参加している(上がってない)自分以外のプレイヤーIDを返す */
	public ArrayList<Integer> getOtherPlayers(Player me){
		ArrayList<Integer> list=new ArrayList<Integer>();
		for(Player p:players){
			if(p!=me) list.add(p.getID());
		}
		return list;
	}
	public int getNextPlayerID(){
		return nextPlayerID;
	}
	/** 指定されたプレイヤーの順位を返す。もしまだクリアしてなければ-1を返す */
	public int getPlayerRank(int id){
		for(int i=0;i<finishPlayers.size();i++){
			if(finishPlayers.get(i).getID()==id) return i+1;
		}
		return -1;
	}
	/**
	 * 
	 * @param mine 自分
	 * @return 自分以外のプレイヤーの手札（マップでプレイヤーIDと紐付けしてある）
	 */
	public LinkedHashMap<Integer,Hand> getOtherPlayerHands(Player me){
		LinkedHashMap<Integer,Hand> hands=new LinkedHashMap<Integer,Hand>();
		for(Map.Entry<Player,Hand> entry : playerHands.entrySet()) {
			if(entry.getKey()!=me) hands.put(entry.getKey().getID(), entry.getValue());
		}
		return hands;
	}
	/** プレイヤーの追加  */
	public void addPlayer(Player player){
		players.add(player);
		nextPlayerID++;
	}
	/** ゲームスタート（プレイヤーが足りなければ自動で追加して） */ 
	public void start(){
		if(gameCount!=0){
			 prepareForRestart();
			 System.out.println("restart");
		}
		gameCount++;
		//足りない分プレイヤーを追加
		if(players.size()<playerCount){
			do{
				addPlayer(new LittleSmartComputer(this));
			}while(players.size()!=playerCount);
		}
		//順番シャッフル
		Collections.shuffle(players);
		// allPlayersで保持
		for(Player player:players){
			allPlayers.put(player.getID(),player);
		}
		
		//カードの配布
		//手札
		ArrayList<Card> deck=DeckCreater.createDeck();
		int handCount=DomemoRule.getHandCount(playerCount);
		for(Player player:players){
			Hand hand=new Hand();
			for(int i=0;i<handCount;i++){
				hand.addCardWithOrder(deck.get(0));
				deck.remove(0);
			}
			playerHands.put(player, hand);
		}
		//オープン
		openCards=new Hand();
		int openCount=DomemoRule.getOpenCount(playerCount);
		for(int i=0;i<openCount;i++){
			openCards.addCardWithOrder(deck.get(0));
			deck.remove(0);
		}
		//クローズド
		closedCards=new Hand();
		int closedCount=DomemoRule.getClosedCount(playerCount);
		for(int i=0;i<closedCount;i++){
			closedCards.addCard(deck.get(0));
			deck.remove(0);
		}
		
		printField(false);
		System.out.println("\nGame Start\n");
		
		if(isGUI) makeView();
		//ゲーム進行
		//最後の一人になるまで各プレイヤーのターンを回す
		
		while(finishPlayers.size()!=playerCount-1){
			anyPlayersturn();
			printField(false);
			if(isGUI) repaint();
		}
		gameOver();
	}
	public Result createResult(){
		Result result=new Result();
		for(Map.Entry<Integer,Player> entry : allPlayers.entrySet()) {
			result.addPlayer(entry.getValue(), getPlayerRank(entry.getValue().getID()));
			
		}
		return result;
	}
	/*
	 *  この下privateメソッド
	 *  
	 */
	
	/** GUIモードならframeにテキスト表示。違うならコンソール出力のみ */
	private void printString(String str){
		if(frame==null) System.out.println(str);
		else{
			frame.setText(str);
			System.out.println(str);
		}
	}
	/** 再スタートのための準備 */
	private void prepareForRestart(){
		players=new ArrayList<Player>();
		for(Map.Entry<Integer,Player> entry : allPlayers.entrySet()) {
			players.add(entry.getValue());
		}
		
		finishPlayers=new ArrayList<Player>();
		allPlayers=new LinkedHashMap<Integer,Player>();
		playerStatements=new ArrayList<Statement>();
		playerHands=new LinkedHashMap<Player,Hand>();
		//waitTime=new WaitTime();
		
		turnCount=0;
	}

	/**
	 * プレイヤーのターン処理
	 */
	private void anyPlayersturn(){
		Player turnPlayer=players.get((turnCount)%players.size());
		if(isGUI) frame.changeTurn(turnPlayer);
		int sayNum=turnPlayer.sayNumber();
		String str=turnPlayer + "  say :"+sayNum+"   ";
		
		boolean isHit=hitNumberProcess(sayNum,turnPlayer);
		if(isHit) str+=" ヒット";
		else str+="ミス";
		printString(str);
		playerStatements.add(new Statement(turnPlayer.getID(),sayNum,isHit));
		
		
		//カードが全部なくなったらあがり
		if(playerHands.get(turnPlayer).count()==0){
			finishPlayers.add(turnPlayer);
			players.remove(turnPlayer);
			System.out.println("finish "+turnPlayer);
		}
		if(isAutoMode){
			//2秒間待機
			try {
				Thread.sleep(waitMillis);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		else if(isGUI) waitTime.start();
		if(playerCount<4 &&isHit) {}
		else turnCount++;
		
	}
	private boolean hitNumberProcess(int cardNumber,Player player){
		Hand hand=playerHands.get(player);
		Card hitCard=hand.hasCard(cardNumber);
		if(hitCard!=null){
			System.out.println("hit");
			hand.removeCard(hitCard);
			openCards.addCardWithOrder(hitCard);
			return true;
		}
		else{
			System.out.println("miss");
			return false;
		}
	}
	private void makeView(){
		frame=new ViewFrame(this);
		
		for(Player player:players){
			if(player.getClass()==GUIHumanPlayer.class){
				frame.addNumButtonsActionListner((GUIHumanPlayer)player);
				System.out.println("add gui humanplayer");
			}
		}
		frame.addNextButtonActionListener(waitTime);
		if(isGUI) repaint();
		
		frame.setVisible(true);
	}
	private void repaint(){
		frame.drawImage(playerHands,openCards);
	}

	/**
	 *  フィールドとプレイヤーの状態を表示
	 * @param isEnd 終了したかどうか
	 */
	private void printField(boolean isEnd){
		System.out.println("\n\nturn:"+turnCount);
			for(Player player:players){
				System.out.print(player+"  ");
				if(player.isHuman()==false) System.out.println(playerHands.get(player));
				else {
					if(!isEnd){
						String str="";
						for(int i=0;i<playerHands.get(player).count();i++){ str+="[X]";  }
						System.out.println(str);
					}
					else{
						System.out.println(playerHands.get(player));
					}
				}
			}
			for(int i=0;i<finishPlayers.size();i++){
				System.out.println(finishPlayers.get(i)+"  "+(i+1)+"位");
			}
			System.out.print("open     ");
			System.out.println(openCards.toString2());
			System.out.print("closed   ");
			if(!isEnd){
			String str="";
			for(int i=0;i<closedCards.count();i++){ str+="[X]";  }
				System.out.println(str);
			}
			else{
				System.out.println(closedCards);
			}
			System.out.println();
	}
	/**
	 * ゲーム終了処理（結果表示）
	 */
	private void gameOver(){
		finishPlayers.add(players.get(0));
		if(isGUI) repaint();
		
		System.out.println("\nゲーム終了");
		printField(true);
		/*
		int rnk=1;
		for(Player player:finishPlayers){
			System.out.println(rnk+"位 "+player);
			rnk++;
		}
		*/
		
		
	}
	

}
