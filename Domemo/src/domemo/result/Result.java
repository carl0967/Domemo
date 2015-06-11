package domemo.result;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import domemo.player.Player;

public class Result {
	/** ターンが回ってくる順番に格納されたlinkedMap 
	 * キー：player バリュー：そのプレイヤーの順位 */
	private LinkedHashMap<Player,Integer> playerDatas;
	public Result(){
		playerDatas=new LinkedHashMap<Player,Integer>();
		
	}
	public void addPlayer(Player player,int rank){
		playerDatas.put(player, rank);
	}
	public String toString(){
		String str="";
		for(Map.Entry<Player,Integer> entry : playerDatas.entrySet()) {
			str+=entry.getKey().toString()+" "+entry.getValue()+"位\n";
		}
		return str;
	}
	public int playerCount(){
		return playerDatas.size();
	}
	public LinkedHashMap<Player,Integer> getPlayerDatas(){
		return playerDatas;
	}

}
