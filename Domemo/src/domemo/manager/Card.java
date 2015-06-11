package domemo.manager;
/**
 * ドメモのカード
 * @author info
 *
 */
public class Card {
	private int number;
	public Card(int number){
		this.number=number;
	}
	public String toString(){
		return "["+number+"]";
	}
	public int getNumber(){
		return number;
	}

}
