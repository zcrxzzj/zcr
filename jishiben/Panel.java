package my0519jishiben;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Panel extends JPanel {
PopupMenu popupMenu =new PopupMenu();
MenuItem menuitem1 =new MenuItem();
MenuItem menuitem2 =new MenuItem();
MenuItem menuitem3 =new MenuItem();
public  Panel(){
	try{
		jbInit();
	}catch(Exception ex){
		ex.printStackTrace();
	}
}
private void jbInit() {
	this.setLayout(null);
	 menuitem1.setLabel("1");
	 menuitem1.addActionListener(new java.awt.event.ActionListener(){
		
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			 menrItem1_actionperformed(e);
		}
	 });
	 this.setLayout(null);
	 menuitem2.setLabel("2");
	 menuitem3.setLabel("3");
	 this.addMouseListener(new java.awt.event.MouseAdapter(){
		 public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				 this_mousePressed(e);
			}
	 }
	 );
	 popupMenu.add(menuitem1);
	 popupMenu.add(menuitem2);
	 popupMenu.add(menuitem3);
	 add(popupMenu);
	 
		 
	 }
void this_mousePressed(MouseEvent e){
		 int mods=e.getModifiers();
		 if((mods&InputEvent.BUTTON3_MASK)!=0){
			 popupMenu.show(this, e.getX(), e.getY());
		 }
}
	 

void menrItem1_actionperformed(ActionEvent e){
	



}


}