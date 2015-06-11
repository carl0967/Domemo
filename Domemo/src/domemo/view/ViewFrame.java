package domemo.view;

import java.awt.BorderLayout;
import java.net.URL;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import domemo.manager.Card;
import domemo.manager.DomemoManager;
import domemo.manager.Hand;
import domemo.player.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.util.ArrayList;
import java.util.Map;
/**
 * GUIの描画系をいろいろ司るJFrame
 * ViewControllerっぽい感じ
 * @author info
 *
 */
public class ViewFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	Timer timer;
	private static final long serialVersionUID = 1L;
	private ImageIcon[] imageIcons;
	private ArrayList<PlayerPanel> playerPanels;
	private FieldPanel fieldPanel;
	private DomemoManager dm;
	private JButton[] numButtons;
	private JButton autoButton;
	
	private Player turnPlayer;
	
	public ViewFrame(DomemoManager dm){
		setTitle("DOMEMO");
		this.dm=dm;
	    setBounds(100, 100, 800, 500);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Container contentPane = getContentPane();
	    contentPane.add(createImagePanel(), BorderLayout.CENTER);

	    contentPane.add(createButtonPanel(),BorderLayout.SOUTH);
	    
	    makeImageIcons();
	    
	}
	public void setText(String str){
		fieldPanel.setMessage(str);
	}
	/** プレイヤーのターンが進行したことをViewのほうに通知 */
	public void changeTurn(Player nextPlayer){
		if(turnPlayer!=null) getPlayerPanel(turnPlayer).changeColor(Color.BLACK);
		getPlayerPanel(nextPlayer).changeColor(Color.RED);
		turnPlayer=nextPlayer;
		
		
	}
	/** 画像の描画(更新) */
	public void drawImage(Map<Player,Hand> playerHands,Hand openHand){
		int humanNum=0;
		int tmp=0;
		for(Map.Entry<Player,Hand> entry : playerHands.entrySet()) {
			if(entry.getKey().isHuman()) humanNum=tmp;
			tmp++;
		}
		//System.out.println("hn:"+humanNum);
		int i=0;
		//各プレイヤーごとに
		for(Map.Entry<Player,Hand> entry : playerHands.entrySet()) {
			Player player=entry.getKey();
			int num=(i+3-humanNum)%dm.getPlayerCount();
			//System.out.println("num:"+num+"  "+player);
			PlayerPanel p=playerPanels.get(num);
			p.removeAllCard();
			p.setName(player.toString());
			Hand hand=entry.getValue();
			i++;
			//手札のカード画像追加
			int rnk=dm.getPlayerRank(player.getID());
			if(rnk!=-1) { p.setRankLabelText(rnk+"位"); }
			for(Card card:hand.getCards()){
				if(!entry.getKey().isHuman()) p.addCard(imageIcons[card.getNumber()]);
				else p.addCard(imageIcons[0]);
			}
		}
		//フィールド描画
		fieldPanel.removeAllOpenPanel();
		for(Card card:openHand.getCards()){
			fieldPanel.addCard(imageIcons[card.getNumber()]);
		}
	}
	/** 1-7ボタンのアクションを受け取る先のリスナーを追加 */
	public void addNumButtonsActionListner(ActionListener al){
	    for(int i=1;i<8;i++){
	    	numButtons[i].addActionListener(al);
	    }
	}
	public void addNextButtonActionListener(ActionListener al){
		numButtons[0].addActionListener(al);
	}
	private void makeImageIcons(){
		//ClassLoader cl = this.getClass().getClassLoader(); 
		//ImageIcon icon = new ImageIcon(cl.getResource("1.jpg"));
		
		
		//イメージ生成
		
	   // String filePath="/Users/info/Documents/workspace/Domemo/src/domemo/view/image/";
		//String filePath="./src/img/";
	    imageIcons=new ImageIcon[8];
	    for(int i=0;i<8;i++){
	    	//imageIcons[i]=new ImageIcon(filePath+i+".png");
	    	imageIcons[i]=new ImageIcon(this.getClass().getClassLoader().getResource(""+i+".png"));
	    }
	    
	    
	}
	private JPanel createImagePanel(){
		playerPanels=new ArrayList<PlayerPanel>();
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		String[] layouts={BorderLayout.WEST,BorderLayout.NORTH,BorderLayout.EAST,BorderLayout.SOUTH};
		for(int i=0;i<dm.getPlayerCount();i++){
			PlayerPanel pp=new PlayerPanel("");
			p.add(pp,layouts[i]);
			playerPanels.add(pp);
		}
		fieldPanel=new FieldPanel();
		p.add(fieldPanel,BorderLayout.CENTER);
	    
	    return p;
	}
	private JPanel createButtonPanel(){
	    //ボタンPanelの追加
	    JPanel buttonPanel=new JPanel();
	    numButtons=new JButton[8];
	    for(int i=1;i<8;i++){
	    	JButton btn=new JButton(""+i);
	    	numButtons[i]=btn;
	    	btn.setPreferredSize(new Dimension(30,30));
	    	buttonPanel.add(btn);
	    	
	    }
	    numButtons[0]=new JButton("next");
	    numButtons[0].setPreferredSize(new Dimension(100,50));
	    buttonPanel.add(numButtons[0]);
	    
	    autoButton=new JButton("Auto");
	    autoButton.addActionListener(this);
	    autoButton.setPreferredSize(new Dimension(100,50));
	    buttonPanel.add(autoButton);
	    return buttonPanel;
	}
	private PlayerPanel getPlayerPanel(Player player){
		for(int i=0;i<playerPanels.size();i++){
			if(playerPanels.get(i).getName().equals(player.toString())){
				return playerPanels.get(i);
			}
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == autoButton) {
			boolean autoMode=dm.isAutoMode();
			if(autoMode){
				autoButton.setText("Auto");
				dm.setAutoMode(false);
			}
			else{
				autoButton.setText("off Auto");
				dm.setAutoMode(true);
			}
			
		} 
		
	}

}
