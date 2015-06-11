package domemo.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** ボタンが押されるまでwhile待機するクラス   */
public class WaitTime implements ActionListener{
	private boolean end=false;
	/** ループ間隔(ミリ秒) */
	private int millis=100;
	
	public WaitTime(int millis){
		this.millis=millis;
	}
	public WaitTime(){
		this(100);
	}
	public void start(){
		end=false;
		waitTime();
	}
	public void waitTime(){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if(!end) waitTime();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		end=true;
	}

}
