package domemo.player;

import java.util.ArrayList;
import java.util.Random;

import domemo.manager.DMAssister;
import domemo.manager.DomemoManager;


/**
 * サンプル用のAI
 * @author info
 *
 */
public class SampleComputer extends Player {
	private DMAssister dma;

	public SampleComputer(DomemoManager dm) {
		super(dm);
		dma=new DMAssister(dm,this);	
		}

	@Override
	public int sayNumber() {
		//1~7番をランダムで発言(存在する可能性があるものだけ)
		Random rnd=new Random();
		ArrayList<Integer> existableNumbers=dma.existableNumbers();
		return existableNumbers.get(rnd.nextInt(existableNumbers.size()));
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "Sample";
	}

	@Override
	public boolean isHuman() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
