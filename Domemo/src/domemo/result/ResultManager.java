package domemo.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import domemo.player.Player;

public class ResultManager {
	ArrayList<Result> results;
	public ResultManager(){
		results=new ArrayList<Result>();
	}
	public void addResult(Result result){
		results.add(result);
	}
	/** 全てのresultのtoStringを表示 */
	public void printAll(){
		for(Result result:results){
			System.out.println(result);
		}
	}
	/** 各プレイヤーの勝率を計算して表示 */
	public void printWinningRate(){
		if(results.size()==0) return;
		// [プレイヤー][順位]
		HashMap<Player,int[]> playerMap=new HashMap<Player,int[]>();
		for(Map.Entry<Player,Integer> entry : results.get(0).getPlayerDatas().entrySet()) {
			playerMap.put(entry.getKey(), new int[results.get(0).playerCount()+1]);
		}
		
		for(Result result:results){
			for(Map.Entry<Player,Integer> entry : result.getPlayerDatas().entrySet()) {
				int[] tmp=playerMap.get(entry.getKey());
				tmp[entry.getValue()]++;
			}
		}
		for(Map.Entry<Player,int[]> entry : playerMap.entrySet()) {
			Player player=entry.getKey();
			System.out.println(player);
			int[] tmp=entry.getValue();
			for(int i=1;i<tmp.length;i++){
				System.out.print(i+"位:"+(double)tmp[i]/results.size()+" ");
			}
			System.out.println("\n");
		}
		

		
		
	}

}
