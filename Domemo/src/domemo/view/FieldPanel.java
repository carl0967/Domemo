package domemo.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

/**    
 *  オープン、クローズになっているカード及び、メッセージの表示をするパネル
 * 
 * @author info
 *
 */
public class FieldPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel openPanel;
	private JLabel messageLabel;
	GridBagLayout openLayout;
	public FieldPanel(){
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		//setPreferredSize(new Dimension(500,500));
		
		openPanel=new JPanel();
		openPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, Color.black));
		openLayout = new GridBagLayout();
		openPanel.setLayout(openLayout);
		
		messageLabel=new JLabel("   ");
		messageLabel.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, Color.black));
		//コンポーネントの追加
		addComponent(gbl,this,messageLabel,0,0,1,1);
		addComponent(gbl,this,openPanel,0,1,1,5);
		//openPanel.setLayout(new GridLayout(3,4));
		
		
		
	}
	private void addComponent(GridBagLayout gbl,JPanel targetPanel,JComponent comp, int x, int y, int w, int h) {
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.fill = GridBagConstraints.BOTH;
	        gbc.gridx = x;
	        gbc.gridy = y;
	        gbc.gridwidth = w;
	        gbc.gridheight = h;
	        gbl.setConstraints(comp, gbc);
	        targetPanel.add(comp);
	    }
	public void setMessage(String message){
		messageLabel.setText(message);
	}
	public void removeAllOpenPanel(){
		openPanel.removeAll();
	}
	public void addCard(ImageIcon ii){
		int maxW=6; //横に何個まで並べるか
		int count=openPanel.getComponentCount();
		int x=count+1;
		int y=1;
		while(x>maxW){
			x-=maxW;
			y++;
		}
		//System.out.println("count:"+count+" x:"+x+" y:"+y);
		JLabel label=new JLabel(ii);
		//label.setPreferredSize(new Dimension(30,30));
		addComponent(openLayout,openPanel,label,x,y,1,1);
	}

}
