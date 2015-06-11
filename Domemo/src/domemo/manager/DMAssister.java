package domemo.manager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import domemo.player.Player;
/**
 * 　処理が簡単になるようDMをアシストするクラス
 *   基本的にこのクラスを使ってAIを実装する
 * @author info
 *
 */

public class DMAssister {
	private DomemoManager dm;
	private Player me;
	public DMAssister(DomemoManager dm,Player me){
		this.dm=dm;
		this.me=me;
	}
	/**
	 * 一致したプレイヤーの発言を返す
	 * @param player
	 * @return
	 */
	public ArrayList<Statement> getPlayerStatements(int playerID){
		ArrayList<Statement> statements=new ArrayList<Statement>();
		for(Statement statement:dm.getPlayerStatements()){
			if(statement.getSpeakerID()==playerID) statements.add(statement);
		}
		return statements;
	}
	/**
	 *  自分視点存在する可能性がある数字の配列を返す
	 * @return 
	 */
	public ArrayList<Integer> existableNumbers(){
		int seeableCount[]=new int[8];
		dm.getOpenCards();
		countCards(seeableCount,dm.getOpenCards());
		for(Map.Entry<Integer,Hand> entry :dm.getOtherPlayerHands(me).entrySet() ) {
			countCards(seeableCount,entry.getValue());
		}
		
		ArrayList<Integer> existableNumbers=new ArrayList<Integer>();
		for(int i=1;i<8;i++){
			if(seeableCount[i]!=i)  existableNumbers.add(i);
		}
		return existableNumbers;
	}
	/**
	 * 
	 * @return 自分が添え字の数字を宣言してヒットしなかった場合、その配列の添え字の中身はtrue,それ以外はfalse
	 */
	public boolean[] missNumbers(){
		boolean[] numbers=new boolean[8];
		for(Statement statement:getPlayerStatements(me.getID())){
			if(statement.isHit()==false) numbers[statement.getTargetNumber()]=true;
		}
		
		return numbers;
	}
	/**
	 * もっとも残っている枚数が多い数字を返す（同じ確率の場合はランダム）
	 * 宣言して外したのは候補から外す
	 * @return 
	 */
	public int mostHighProbabilityNumber(){
		int seeableCount[]=new int[8];
		dm.getOpenCards();
		countCards(seeableCount,dm.getOpenCards());
		for(Map.Entry<Integer,Hand> entry :dm.getOtherPlayerHands(me).entrySet() ) {
			countCards(seeableCount,entry.getValue());
		}
		
		ArrayList<Integer> numbers=new ArrayList<Integer>();
		int maxSheet=0; //枚数
		boolean[] missNumbers=missNumbers();
		for(int i=1;i<8;i++){
			//自分視点全てなくなってるのと、１回外したことがあるものはパス
			if(seeableCount[i]!=i && missNumbers[i]==false){
				int sheet=calcHitCount(i,seeableCount[i]);
				//System.out.println(i+" "+"枚数="+sheet);
				if(maxSheet<sheet){
					numbers.clear();
					numbers.add(i);
					maxSheet=sheet;
					
				}
				else if(maxSheet==sheet){
					numbers.add(i);
				}
			}
		}
		Random rnd=new Random();
		return numbers.get((rnd.nextInt(numbers.size())));
	}
	public int calcHitCount(int number,int seeableCount){
		return  number-seeableCount;
	}
	
	private void countCards(int array[],Hand hand){
		for(Card card:hand.getCards()){
			array[card.getNumber()]++;
		}
	}

}
