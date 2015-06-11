package domemo.player;

import java.util.ArrayList;

import domemo.manager.Card;
import domemo.manager.DomemoManager;
import domemo.manager.Hand;

/**
 * ドメモのプレイヤークラス
 * これを継承したクラスを作って参加させる
 * @author info
 *
 */
public abstract class Player {
	DomemoManager dm;
	protected String name;
	/**　ハンド(手札)  */
	protected Hand hand;
	protected int id;
	protected boolean isHuman;
	public Player(DomemoManager dm){
		this.dm=dm;
		this.name=getName();
		this.id=dm.getNextPlayerID();
		this.isHuman=isHuman();
		hand=new Hand();
	}
	
	/**
	 * カードをハンドに追加
	 * @param card
	 */
	/*
	public void addHand(Card card){
		hand.addCard(card);
	}
	*/
	/**
	 * 
	 * @return ハンドの数
	 */
	public int handCount(){
		return hand.count();
	}
	/**
	 * 指定された番号のカードがある場合、それを１枚handから消す
	 * @param cardNumber 消したいカードの番号
	 * @return 消したカード
	 */
	/*
	public Card hasCardNumber(int cardNumber){
		for(Card handCard:hand){
			if(handCard.getNumber()==cardNumber){
				hand.remove(handCard);
				return handCard;
			}
		}
		return null;
	}
	*/
	public String toString(){
		return name+id;
	}
	public int getID(){
		return id;
	}
	public abstract String getName();
	public abstract boolean isHuman();
	/**
	 * 
	 * @return 宣言するカードの番号
	 */
	public abstract int sayNumber();
	

}
