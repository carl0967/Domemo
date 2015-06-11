package domemo.player;

import domemo.manager.DMAssister;
import domemo.manager.DomemoManager;

public class LittleSmartComputer extends Player {
	private DMAssister dma;
	public LittleSmartComputer(DomemoManager dm) {
		super(dm);
		dma=new DMAssister(dm,this);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "Smart";
	}

	@Override
	public int sayNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return dma.mostHighProbabilityNumber();
	}

	@Override
	public boolean isHuman() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
