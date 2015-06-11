package domemo.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

import domemo.manager.Hand;
/**
 *  プレイヤーの名前、順位、手札を表示するためのパネル
 * @author info
 *
 */

public class PlayerPanel extends JPanel {
	private JLabel nameLabel;
	private JLabel rankLabel;
	private JPanel handPanel;
	public PlayerPanel(String name){
		//setLayout(new BorderLayout());
		nameLabel=new JLabel(name);
		add(nameLabel,BorderLayout.NORTH);
		rankLabel=new JLabel();
		add(rankLabel);
		handPanel=new JPanel();
		add(handPanel,BorderLayout.CENTER);
		
		//nameLabel.setForeground(Color.RED);
	}
	public void setRankLabelText(String str){
		rankLabel.setText(str);
	}
	public void removeAllCard(){
		handPanel.removeAll();
	}
	public void addCard(ImageIcon ii){
		JLabel label=new JLabel(ii);
		handPanel.add(label);
	}
	public void setName(String name){
		nameLabel.setText(name);
	}
	public void changeColor(Color c){
		nameLabel.setForeground(c);
	}
	public String getName(){
		return nameLabel.getText();
	}

}
