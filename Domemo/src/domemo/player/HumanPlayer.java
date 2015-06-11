package domemo.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import domemo.manager.DomemoManager;

public class HumanPlayer extends Player{

	public HumanPlayer(DomemoManager dm) {
		super(dm);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "人間";
	}

	@Override
	public int sayNumber() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("1-7の数字を入力してください\n");
		Scanner scan = new Scanner(System.in);
		int val;
		do{
			val = scan.nextInt();
		}while(val<0 || val>7);
		return val;
	}

	@Override
	public boolean isHuman() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}



}
