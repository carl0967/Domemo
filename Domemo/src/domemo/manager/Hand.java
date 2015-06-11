package domemo.manager;

import java.util.ArrayList;

/**
 * 手札。というかカードの束としての役割
 * @author info
 *
 */
public class Hand {
	private ArrayList<Card> cards;
	public Hand(){
		cards=new ArrayList<Card>();
	}
	public void addCard(Card card){
		cards.add(card);
	}
	/** 並び替えしながらカードを追加 */
	public void addCardWithOrder(Card card){
		for(int i=0;i<cards.size();i++){
			if(cards.get(i).getNumber()>card.getNumber()){
				cards.add(i, card);
				return;
			}
		}
		cards.add(card);
	}
	public void removeCard(Card card){
		cards.remove(card);
	}
	public ArrayList<Card> getCards(){
		return cards;
	}
	public int count(){
		return cards.size();
	}
	public Card hasCard(int cardNumber){
		for(Card mycard:cards){
			if(mycard.getNumber()==cardNumber){
				return mycard;
			}
		}
		return null;
	}
	public String toString2(){
		String str="";
		int cardCount[]=new int[8];
		for(Card card:cards){
			//str+=card.toString();
			cardCount[card.getNumber()]++;
		}
		
		 
		 //２枚以上ある場合は *2のようにして表示
		for(int i=1;i<8;i++){
			if(cardCount[i]!=0) str+="["+i+"]";
			if(cardCount[i]>1) str+="*"+cardCount[i];
		}
		return str;
	}
	public String toString(){
		String str="";
		int cardCount[]=new int[8];
		for(Card card:cards){
			//str+=card.toString();
			cardCount[card.getNumber()]++;
		}
		/*
		 *
		 //２枚以上ある場合は *2のようにして表示
		for(int i=1;i<8;i++){
			if(cardCount[i]!=0) str+="["+i+"]";
			if(cardCount[i]>1) str+="*"+cardCount[i];
		}
		*/
		//並べ替えて表示
		for(int i=1;i<8;i++){
			for(int j=0;j<cardCount[i];j++){
				str+="["+i+"]";
			}
		}
		return str;
	}

}
