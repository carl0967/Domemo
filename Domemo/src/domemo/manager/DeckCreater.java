package domemo.manager;
import java.util.ArrayList;
import java.util.Collections;


/**
 * カードを配るためのデッキを生成するクラス
 * @author info
 *
 */
public class DeckCreater {
	/**
	 * 
	 * @return シャッフルされたカードの束
	 */
	public static ArrayList<Card> createDeck(){
		ArrayList<Card> deck=new ArrayList<Card>();
		for(int i=7;i>0;i--){
			for(int j=i;j>0;j--){
				Card card=new Card(i);
				deck.add(card);
			}
		}
		//シャッフル
		Collections.shuffle(deck);
		return deck;
	}

}
