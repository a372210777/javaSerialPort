package zd.main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class LoginFrame extends JFrame implements ActionListener,KeyListener {

	private JLabel userName_label;
	private JLabel tip_label;
	private JLabel password_label;

	private JTextField userName_tf;
	private JPasswordField password_tf;

	private JButton reset_btn;
	private JButton login_btn;

	public LoginFrame() {
		setTitle("登陆");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width / 2 - 150, screenSize.height / 2 - 100);
		getContentPane().setLayout(null);
		getContentPane().setForeground(new java.awt.Color(0, 255, 255));
		{
			userName_label = new JLabel();
			getContentPane().add(userName_label);
			userName_label.setText("\u7528\u6237\u540d:");
			userName_label.setBounds(76, 28, 42, 15);
		}
		{
			userName_tf = new JTextField();
			getContentPane().add(userName_tf);
			userName_tf.setBounds(139, 25, 84, 22);
		}
		{
			password_label = new JLabel();
			getContentPane().add(password_label);
			password_label.setText("\u5bc6\u7801:");
			password_label.setBounds(76, 62, 30, 15);
		}
		{
			password_tf = new JPasswordField();
			getContentPane().add(password_tf);
			password_tf.setBounds(139, 59, 84, 22);
		}
		{
			tip_label = new JLabel();
			getContentPane().add(tip_label);
			tip_label
					.setText("\u9996\u6b21\u767b\u9646\u7528\u6237\u540d:admin\u5bc6\u7801:admin ");
			tip_label.setBounds(67, 93, 186, 15);
			tip_label.setForeground(new java.awt.Color(255, 0, 0));
		}
		{
			login_btn = new JButton();
			getContentPane().add(login_btn);
			login_btn.setText("\u767b\u9646");
			login_btn.setBounds(90, 125, 50, 22);
		}
		{
			reset_btn = new JButton();
			getContentPane().add(reset_btn);
			reset_btn.setText("\u91cd\u7f6e");
			reset_btn.setBounds(150, 125, 50, 22);
		}
		reset_btn.addActionListener(this);
		login_btn.addActionListener(this);
		
		this.addKeyListener(this);
		login_btn.addKeyListener(this);
		reset_btn.addKeyListener(this);
		userName_tf.addKeyListener(this);
		password_tf.addKeyListener(this);
		
		ImageIcon icon=new ImageIcon(System.getProperty("user.dir")+"/images/logo.png");
		setIconImage(icon.getImage()); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("登陆")) {
			check();
		} else if (cmd.equals("重置")) {
			userName_tf.setText("");
			password_tf.setText("");
		}
	}

	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//回车键
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			check();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void check(){
		if (userName_tf.getText() == null
				|| userName_tf.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "用户名不能为空");
			return;
		}
		if (password_tf.getText() == null
				|| password_tf.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "密码不能为空");
			return;
		}

		String dir = System.getProperty("user.dir");
		char newChar = '\\';
		char oldChar = '/';
		String newDir = dir.replace(newChar, oldChar);
		String filePath = newDir + "/configure/userlogin.properties";
		if (Utilities.login(filePath, userName_tf.getText().trim(),
				password_tf.getText().trim())) {
			this.setVisible(false);
			this.dispose();
			new MainFrame();
		} else {
			JOptionPane.showMessageDialog(null, "用户名或密码错误");
		}
	}
}
