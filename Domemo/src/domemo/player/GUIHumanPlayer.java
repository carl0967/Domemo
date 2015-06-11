package domemo.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.Timer;

import domemo.manager.DomemoManager;

public class GUIHumanPlayer extends Player implements ActionListener{
	private int number=-1;
	Timer timer;

	public GUIHumanPlayer(DomemoManager dm) {
		super(dm);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "人間";
	}
	private void waitTime(int millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if(number==-1) waitTime(millis);
		
	}
	@Override
	public int sayNumber() {
		// TODO 自動生成されたメソッド・スタブ
		timer=new Timer(100,this);
		
		System.out.println("1-7の数字を入力してください\n");
		number=-1;
		waitTime(100);
		return number;
	}

	@Override
	public boolean isHuman() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		JButton button=(JButton) e.getSource();
		System.out.println(button.getText());
		number=Integer.valueOf(button.getText());
		
		
	}



}
