package my0519jishiben;

import my0519jishiben.JFrameUtil;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

public class MenuDemo extends JFrame{
	public static final int HEIGHT=500;
	public static final int WIDTH=889;
	public static final String MAINTITLE="记事本";
	private JTextArea content=null;
	private File currentFile=null;
	private boolean modify=false;
	protected UndoManager um=null;
	public void init(){
		setSize(new Dimension(WIDTH, HEIGHT));
		setLocation(JFrameUtil.calcFrameCentrPoint(WIDTH, HEIGHT));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(MAINTITLE);
		Image icon = Toolkit.getDefaultToolkit().getImage("d:\\1.jpg");	//引号内为图片所在位置
		setIconImage(icon);//图标
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		setVisible(true);
	}
	private void newfil() {
		if (modify){
			int cmd = 0;
			String popupTitle = null;
			if (currentFile == null){
				popupTitle =  "是否将改变保存到 无标题 ？";	
			}else{
				popupTitle = "是否将改变保存到" + currentFile.getPath();
			}
			cmd = JOptionPane.showOptionDialog(MenuDemo.this, popupTitle, "记事本",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ,null,
					new String[]{"保存","不保存","取消"}, null);
			System.out.println(cmd);
			if (cmd == JOptionPane.YES_OPTION){
				popupDialog("保存(S)");
				currentFile=null;
				content.setText("");
			}else if (cmd == JOptionPane.CANCEL_OPTION){
				currentFile=null;
				content.setText("");
				return;
			}
		}else
		currentFile=null;
		content.setText("");
	}
	private void exit() {
		if (modify){
			int cmd = 0;
			String popupTitle = null;
			if (currentFile == null){
				popupTitle =  "是否将改变保存到 无标题 ？";	
			}else{
				popupTitle = "是否将改变保存到" + currentFile.getPath();
			}
			cmd = JOptionPane.showOptionDialog(MenuDemo.this, popupTitle, "记事本",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ,null,
					new String[]{"保存","不保存","取消"}, null);
			System.out.println(cmd);
			if (cmd == JOptionPane.YES_OPTION){
				popupDialog("保存(S)");
				System.exit(0);

			}else if (cmd == JOptionPane.NO_OPTION){
				System.exit(0);
			}
		}else
			System.exit(0);
	}
	public void popupDialog(String menuName){
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("文本文档(*.txt)","txt"));
		fc.setDialogTitle(menuName.substring(0,menuName.indexOf("(")));
		if ("新建(N)".equals(menuName)){
			newfil();
		}
		if ("保存(S)".equals(menuName)){
			if (currentFile != null)
				saveFile(currentFile, content.getText());
			else{
				fc.showSaveDialog(MenuDemo.this);
				if (fc.getSelectedFile() != null)
					saveFile(fc.getSelectedFile(), content.getText());
				currentFile = fc.getSelectedFile();
			}
		}else if("打开(O)".equals(menuName)){
			fc.showOpenDialog(MenuDemo.this);
			if (fc.getSelectedFile() != null){
				String s = readFile(fc.getSelectedFile());
				content.setText(s);
				currentFile = fc.getSelectedFile();
			}	
		}else if ("另存为(A)".equals(menuName)){
			fc.showSaveDialog(MenuDemo.this);
			if (fc.getSelectedFile() != null)
				saveFile(fc.getSelectedFile(), content.getText());	
			currentFile = fc.getSelectedFile();
		}
		if (currentFile!=null)
			setTitle(MAINTITLE + currentFile.getPath());
		modify=false;
	}
	private void saveFile(File File, String content) {
		FileOutputStream as=null;
		try{
			as=new FileOutputStream(File);
			OutputStreamWriter ad=new OutputStreamWriter(as);
			BufferedWriter ds =new BufferedWriter(ad);
			ds.write(content);
			ds.close();
		}catch(IOException e1){
			e1.printStackTrace();
		}	
	}
	private String readFile(File file) {
		FileInputStream is= null;
		InputStreamReader isr=null;
		BufferedReader br=null;
		StringBuilder sb=new StringBuilder();
		try{
			is= new FileInputStream(file);
			isr=new InputStreamReader(is);
			br=new BufferedReader(isr);
			String str =null;
			while ((str=br.readLine())!=null){
				sb.append(str).append("\r\n");
			}
		}catch(IOException e1){
			e1.printStackTrace();
		}finally{
			if(br!=null)
				try{
					br.close();
				}catch(IOException e1){
					e1.printStackTrace();
				}
		}
		return 	sb.toString();
	}
	public void drawMenu(){
		JMenu fileMenu=new JMenu("文件(F)");
		JMenuItem  document=new JMenuItem("新建(N)");
		JMenuItem open=new JMenuItem("打开(O)");
		JMenuItem save=new JMenuItem("保存(S)");
		JMenuItem saveas=new JMenuItem("另存为(A)");
		JMenuItem pagesetup=new JMenuItem("页面设置(U)");
		JMenuItem print=new JMenuItem("打印(P)");
		JMenuItem quit=new JMenuItem("退出(X)");
		ActionListener al = new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				Object o = e.getSource();
				if (o instanceof JMenuItem){
					popupDialog(((JMenuItem)o).getText());
				}
			}
		};
		quit.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		document.addActionListener(al);
		fileMenu.add(document);
		open.addActionListener(al);
		fileMenu.add(open);
		save.addActionListener(al);	
		fileMenu.add(save);	
		saveas.addActionListener(al);
		fileMenu.add(saveas);
		fileMenu.addSeparator();//分割线
		fileMenu.add(pagesetup);
		fileMenu.add(print);
		fileMenu.addSeparator();//分割线
		fileMenu.add(quit);
		JMenu compile=new JMenu("编辑(E)");
		JMenuItem revocation=new JMenuItem("撤销(U)");
		final JMenuItem cut=new JMenuItem("剪切(T)");
		final JMenuItem copy=new JMenuItem("复制(C)");
		final JMenuItem paste=new JMenuItem("粘贴(P)");
		final JMenuItem del=new JMenuItem("删除(L)");
		JMenuItem find=new JMenuItem("查找(F)");
		JMenuItem findnext=new JMenuItem("查找下一个(N)");
		JMenuItem replace=new JMenuItem("替换(R)");
		JMenuItem goto1=new JMenuItem("转到(G)");
		JMenuItem checkall=new JMenuItem("全选(A)");
		JMenuItem TimeDate=new JMenuItem("时间/日期(D)");
		final Clipboard clipboard=getToolkit().getSystemClipboard();
		ActionListener com = new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()==copy){
					String str=content.getSelectedText();
					StringSelection text=new StringSelection(str);
					clipboard.setContents(text,null);
				}else	if (e.getSource()==paste){
					Transferable contents=clipboard.getContents(this);
					DataFlavor flavor=DataFlavor.stringFlavor;
					String str;
					if(contents.isDataFlavorSupported(flavor))
						try {
							str = (String)contents.getTransferData(flavor);
							String hualeString=content.getText();
							int start=content.getSelectionStart();
							int end=content.getSelectionEnd();
							if((end-start)>0){
								if(str==null){
									content.setText(hualeString.substring(0,start)+str + hualeString.substring(end));	
								}else {
								}
							}else if((end-start)==0){
								if(str==null){
									content.setText(hualeString.substring(0,end)+str + hualeString.substring(end));	
								}else {
								}
							}
						}  catch (Exception e1) {
							e1.printStackTrace();
						}
				}else if(e.getSource()==cut){
					String tmp=content.getSelectedText();
					StringSelection text=new StringSelection(tmp);
					clipboard.setContents(text,null);
					int start=content.getSelectionStart();
					int end=content.getSelectionEnd();
					content.replaceRange("", start, end);

				}else if(e.getSource()==del){
					int start=content.getSelectionStart();
					int end=content.getSelectionEnd();
					content.replaceRange("", start, end);
				}
			}
		};
		revocation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					um.undo();
				}catch(javax.swing.undo.CannotUndoException e1){
				}	
			}
		});
		copy.addActionListener(com);
		paste.addActionListener(com);
		cut.addActionListener(com);
		del.addActionListener(com);
		compile.add(revocation);
		compile.addSeparator();//分割线
		compile.add(cut);
		compile.add(copy);
		compile.add(paste);
		compile.add(del);
		compile.addSeparator();//分割线
		compile.add(find);
		compile.add(findnext);
		compile.add(replace);
		compile.add(goto1);
		compile.addSeparator();//分割线
		compile.add(checkall);
		compile.add(TimeDate);
		JMenu form=new JMenu("格式(O)");
		JMenuItem wordwrap=new JMenuItem("自动换行(W)");
		JMenuItem typeface=new JMenuItem("字体(F)....");
		form.add(wordwrap);
		form.add(typeface);
		JMenu check=new JMenu("查看(V)");
		JMenuItem statusbar=new JMenuItem("状态栏(S)");
		check.add(statusbar);
		JMenu help=new JMenu("帮助(H)");
		JMenuItem view=new JMenuItem("查看帮助(H)");
		JMenuItem aboutnotepad=new JMenuItem("关于记事本(A)");
		help.add(view);
		help.addSeparator();
		help.add(aboutnotepad);
		aboutnotepad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new aboutnotepad1();
			}
		});
		//快捷键 document新建  open打开 save保存 print打印
		document.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
		//快捷键 del删除 findnext查找下一个  TimeDate时间/日期
		del.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
		findnext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0));
		TimeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
		//快捷键 revocation撤销 cut剪切 copy复制 paste粘贴
		// find 查找replace替换 goto1转到 checkall全选
		revocation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_MASK));
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK));
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,InputEvent.CTRL_MASK));
		replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,InputEvent.CTRL_MASK));
		goto1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,InputEvent.CTRL_MASK));
		checkall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
		JMenuBar bar=new JMenuBar();
		bar.add(fileMenu);
		bar.add(compile);
		bar.add(form);
		bar.add(check);
		bar.add(help);
		setJMenuBar(bar);
	}	
	public void initComponent(){
		content = new JTextArea();
		um=new UndoManager();
		content.getDocument().addUndoableEditListener(new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				um.addEdit(e.getEdit());
			}
		});
		content.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				modify = true;
			}
		});
		JScrollPane jsp = new JScrollPane(content);
		add(jsp); 
		drawPopupMenu();
	}
	private void drawPopupMenu() {
		final JPopupMenu right=new JPopupMenu();
		JMenuItem rightrevocation=new JMenuItem("撤销(U)");
		final JMenuItem rightcut=new JMenuItem("剪切(T)");
		final JMenuItem rightcopy=new JMenuItem("复制(C)");
		final JMenuItem rightpaste=new JMenuItem("粘贴(P)");
		final JMenuItem rightdel=new JMenuItem("删除(L)");
		JMenuItem rightcheckall=new JMenuItem("全选(A)");
		JMenuItem rightread=new JMenuItem("从右到左的阅读顺序(R)");
		JMenuItem rightshow=new JMenuItem("显示Unicode控制字符(S)");
		JMenuItem rightinsert=new JMenuItem("插入Unicode控制字符(I)");
		JMenuItem rightclose=new JMenuItem("关闭IME(O)");
		JMenuItem rightrepeatl=new JMenuItem("汉字重选(R)");
		right.add(rightrevocation);
		right.addSeparator();
		right.add(rightcut);
		right.add(rightcopy);
		right.add(rightpaste);
		right.add(rightdel);
		right.addSeparator();
		right.add(rightcheckall);
		right.addSeparator();
		right.add(rightread);
		right.add(rightshow);
		right.add(rightinsert);
		right.addSeparator();
		right.add(rightclose);
		right.add(rightrepeatl);
		content.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON3){
					right.show(content,e.getX(),e.getY());
				}	
			}
		});
		final Clipboard clipboard=getToolkit().getSystemClipboard();
		ActionListener right2 = new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()==rightcopy){
					String str=content.getSelectedText();
					StringSelection text=new StringSelection(str);
					clipboard.setContents(text,null);
				}else	if (e.getSource()==rightpaste){
					Transferable contents=clipboard.getContents(this);
					DataFlavor flavor=DataFlavor.stringFlavor;
					String str;
					if(contents.isDataFlavorSupported(flavor))
						try {
							str = (String)contents.getTransferData(flavor);
							String hualeString=content.getText();
							int start=content.getSelectionStart();
							int end=content.getSelectionEnd();
							if((end-start)>0){
								if(str !=null){
									content.setText(hualeString.substring(0,start)+str + hualeString.substring(end));
								}else{
								}
							}else if((end-start)==0){
								if(str !=null){
									content.setText(hualeString.substring(0,end)+str + hualeString.substring(end));	
								}else{
								}
							}
						}  catch (Exception e1) {
							e1.printStackTrace();
						}
				}else if(e.getSource()==rightcut){
					String tmp=content.getSelectedText();
					StringSelection text=new StringSelection(tmp);
					clipboard.setContents(text,null);
					int start=content.getSelectionStart();
					int end=content.getSelectionEnd();
					content.replaceRange("", start, end);
				}else if(e.getSource()==rightdel){
					int start=content.getSelectionStart();
					int end=content.getSelectionEnd();
					content.replaceRange("", start, end);
				}
			}
		};
		rightrevocation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					um.undo();
				}catch(javax.swing.undo.CannotUndoException ex){
				}	
			}
		});
		rightcopy.addActionListener(right2);
		rightpaste.addActionListener(right2);
		rightcut.addActionListener(right2);
		rightdel.addActionListener(right2);
	}
	public MenuDemo() throws HeadlessException {
		drawMenu();	
		initComponent();
		init();
	}
	public static void main(String[] args) {
		new MenuDemo();
	}
}
