package my0519jishiben;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class JFrameUtil {
	
	public static Point calcFrameCentrPoint(int width,int height){
		Toolkit t=Toolkit.getDefaultToolkit();
		Dimension d=t.getScreenSize();
		int x=(int)((d.getWidth()-width)/2);
		int y=(int)((d.getHeight()-height)/2);
		return new Point(x,y);
	
	}
	
	
	
		
}
