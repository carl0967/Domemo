package domemo.manager;

import domemo.player.Player;
/**
 * 　発言クラス
 * 　発言者と発言内容、結果をまとめておく
 * @author info
 *
 */

public class Statement {
	/** 発言者 */
	private int speakerID;
	/** 言った番号  */
	private int targetNumber;
	/**  当たったかどうか */
	private boolean isHit;
	
	public Statement(int speakerID,int targetNumber,boolean isHit){
		this.speakerID=speakerID;
		this.targetNumber=targetNumber;
		this.isHit=isHit;
	}
	public int getSpeakerID(){
		return speakerID;
	}
	public int getTargetNumber(){
		return targetNumber;
	}
	public boolean isHit(){
		return isHit;
	}

}
