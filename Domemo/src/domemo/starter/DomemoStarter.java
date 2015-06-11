package domemo.starter;
import java.util.ArrayList;

import domemo.manager.DomemoManager;
import domemo.player.GUIHumanPlayer;
import domemo.player.HumanPlayer;
import domemo.player.LittleSmartComputer;
import domemo.player.SampleComputer;


public class DomemoStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		DomemoManager dm=new DomemoManager(4,true); 
		//dm.addPlayer(new LittleSmartComputer(dm));
		//dm.addPlayer(new SampleComputer(dm));
		dm.addPlayer(new GUIHumanPlayer(dm));
		//dm.setWaitMillis(1000);
		dm.start();

	}

}

