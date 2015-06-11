package domemo.manager;
/**
 *  ドメモのルール
 *  参加人数に対する手札、オープンカード、クローズドカードの枚数
 * @author info
 *
 */

public class DomemoRule {
	public static int getHandCount(int playerCount){
		switch(playerCount){
		case 2:
			return 7;
		case 3:
			return 7;
		case 4:
			return 5;
		case 5:
			return 4;
		default :
			return -1;
		}
	}
	public static int getOpenCount(int playerCount){
		switch(playerCount){
		case 2:
			return 7;
		case 3:
			return 0;
		case 4:
			return 4;
		case 5:
			return 4;
		default :
			return -1;
		}
	}
	public static int getClosedCount(int playerCount){
		switch(playerCount){
		case 2:
			return 7;
		case 3:
			return 7;
		case 4:
			return 4;
		case 5:
			return 4;
		default :
			return -1;
		}
	}

}
