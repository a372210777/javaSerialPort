package zd.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ModifyPassword extends JFrame implements ActionListener{
	private JLabel jLabel1;
	private JPasswordField old_pwd_tf;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JButton commit_btn;
	private JButton reset_btn;
	private JPasswordField confirm_pwd_tf;
	private JPasswordField new_pwd_tf;

	private JFrame parent;
	public ModifyPassword(JFrame parent){
		this.parent=parent;
		initGUI();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand().trim();
		if(cmd.equals("确认")){
			if(old_pwd_tf.getText()==null || old_pwd_tf.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "请输入旧密码 !");
				return;
			}
			if(new_pwd_tf.getText()==null || new_pwd_tf.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "请输入新密码 !");
				return;
			}
			if(confirm_pwd_tf.getText()==null || confirm_pwd_tf.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "请再次输入新密码 !");
				return;
			}
			if(!old_pwd_tf.getText().equals(Utilities.getOldPassword("userlogin.properties"))){
				JOptionPane.showMessageDialog(null, "输入旧密码错误");
				return;
			}
			if(! new_pwd_tf.getText().equals(confirm_pwd_tf.getText())){
				JOptionPane.showMessageDialog(null, "新旧密码不一致!");
				return;
			}
			
			if(Utilities.modifyPassword(new_pwd_tf.getText())){
				JOptionPane.showMessageDialog(null, "修改密码成功！");
				this.setVisible(false);
				this.dispose();
			}else{
				JOptionPane.showMessageDialog(null, "修改密码失败！", "提示", JOptionPane.OK_OPTION);
			}
			
		}else if(cmd.equals("重置")){
			old_pwd_tf.setText("");
			new_pwd_tf.setText("");
			confirm_pwd_tf.setText("");
		}
	}
	
	private void initGUI() {
	
		try {
			{
				
				getContentPane().setLayout(null);
				{
					jLabel1 = new JLabel();
					getContentPane().add(jLabel1);
					jLabel1.setText("\u65e7\u5bc6\u7801:");
					jLabel1.setBounds(51, 32, 42, 15);
				}
				{
					old_pwd_tf = new JPasswordField();
					getContentPane().add(old_pwd_tf);
					old_pwd_tf.setBounds(99, 29, 103, 22);
				}
				{
					jLabel2 = new JLabel();
					getContentPane().add(jLabel2);
					jLabel2.setText("\u65b0\u5bc6\u7801:");
					jLabel2.setBounds(51, 60, 42, 15);
				}
				{
					new_pwd_tf = new JPasswordField();
					getContentPane().add(new_pwd_tf);
					new_pwd_tf.setBounds(99, 57, 103, 22);
				}
				{
					jLabel3 = new JLabel();
					getContentPane().add(jLabel3);
					jLabel3.setText("\u786e\u8ba4:");
					jLabel3.setBounds(51, 87, 30, 15);
				}
				{
					confirm_pwd_tf = new JPasswordField();
					getContentPane().add(confirm_pwd_tf);
					confirm_pwd_tf.setBounds(99, 84, 103, 22);
				}
				{
					commit_btn = new JButton();
					getContentPane().add(commit_btn);
					commit_btn.setText("\u786e\u8ba4");
					commit_btn.setBounds(63, 123, 55, 22);
					commit_btn.addActionListener(this);
				}
				{
					reset_btn = new JButton();
					getContentPane().add(reset_btn);
					reset_btn.setText("\u91cd\u7f6e");
					reset_btn.setBounds(152, 123, 55, 22);
					reset_btn.addActionListener(this);
				}
			}
			{
				ImageIcon icon=new ImageIcon(System.getProperty("user.dir")+"/images/logo.png");
				setIconImage(icon.getImage()); 
				setTitle("修改密码");
				this.setSize(265, 195);
				setVisible(true);
				setLocation(parent.getLocationOnScreen().x + 30,
						parent.getLocationOnScreen().y + 30);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
