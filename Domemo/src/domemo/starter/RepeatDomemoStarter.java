package domemo.starter;

import java.util.ArrayList;
import domemo.manager.DomemoManager;
import domemo.player.GUIHumanPlayer;
import domemo.player.SampleComputer;
import domemo.result.Result;
import domemo.result.ResultManager;

/**
 *  何回も連続してドメモを動かす場合
 *  主にAIを作成した時に試すのに使う
 *  勝率などの表示にはResultManagerを使う
 * @author info
 *
 */
public class RepeatDomemoStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		
		ResultManager rm=new ResultManager();
		DomemoManager dm=new DomemoManager(4,false);
		//dm.addPlayer(new LittleSmartComputer(dm));
		dm.addPlayer(new SampleComputer(dm));
		dm.addPlayer(new SampleComputer(dm));
		for(int i=0;i<100;i++){
			dm.start();
			rm.addResult(dm.createResult());
		}
		//rm.printAll();
		rm.printWinningRate();
		
	}

}
