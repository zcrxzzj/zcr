package my0519jishiben;

import java.awt.Dimension;

import javax.swing.JFrame;

public class  aboutnotepad1 extends JFrame{
		
	public  aboutnotepad1 (){
	int HEIGHT=300;
	int WIDTH=350;
	setSize(new Dimension(WIDTH, HEIGHT));
	setLocation(JFrameUtil.calcFrameCentrPoint(WIDTH, HEIGHT));
	setVisible(true);
	}
}
