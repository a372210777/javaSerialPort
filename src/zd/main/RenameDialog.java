package zd.main;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RenameDialog  extends Dialog implements ActionListener{

	private MainFrame parent;
	
	private JCheckBoxMenuItem[] menuArray;
	private String[] menuName;
	private Label partLabel1=new Label("分区1:");
	private Label partLabel2=new Label("分区2:");
	private Label partLabel3=new Label("分区3:");
	private Label partLabel4=new Label("分区4:");
	private Label partLabel5=new Label("分区5:");
	private Label partLabel6=new Label("分区6:");
	
	private JTextField text1=new JTextField();
	private JTextField text2=new JTextField();
	private JTextField text3=new JTextField();
	private JTextField text4=new JTextField();
	private JTextField text5=new JTextField();
	private JTextField text6=new JTextField();
	
	public RenameDialog(Frame owner,String name[],JCheckBoxMenuItem[] menuArray) {
		super(owner);
		this.parent=(MainFrame) owner;
		this.menuName=name;
		this.menuArray=menuArray;
		
		text1.setText(menuName[0]);
		text2.setText(menuName[1]);
		text3.setText(menuName[2]);
		text4.setText(menuName[3]);
		text5.setText(menuName[4]);
		text6.setText(menuName[5]);
		
		Panel partitionPanel = new Panel();
		partitionPanel.setLayout(new GridLayout(3, 2));
		partitionPanel.add(partLabel1);
		partitionPanel.add(text1);
		partitionPanel.add(partLabel2);
		partitionPanel.add(text2);
		partitionPanel.add(partLabel3);
		partitionPanel.add(text3);
		partitionPanel.add(partLabel4);
		partitionPanel.add(text4);
		partitionPanel.add(partLabel5);
		partitionPanel.add(text5);
		partitionPanel.add(partLabel6);
		partitionPanel.add(text6);
		
		add(partitionPanel,"Center");
		
		Panel buttonPanel = new Panel();
		Button yesButton = new Button("确定");
		yesButton.addActionListener(this);
		buttonPanel.add(yesButton);
		Button noButton = new Button("取消");
		noButton.addActionListener(this);
		buttonPanel.add(noButton);
		add(buttonPanel, "South");
		
		setSize(250, 150);
		setLocation(parent.getLocationOnScreen().x + 30,
				parent.getLocationOnScreen().y + 30);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();
		if(cmd.equals("确定")){
			if(text1.getText()==null || text1.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "分区名字不能为空");
				return;
			}
			if(text2.getText()==null || text2.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "分区名字不能为空");
				return;
			}
			if(text3.getText()==null || text3.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "分区名字不能为空");
				return;
			}
			if(text4.getText()==null || text4.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "分区名字不能为空");
				return;
			}
			if(text5.getText()==null || text5.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "分区名字不能为空");
				return;
			}
			if(text6.getText()==null || text6.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "分区名字不能为空");
				return;
			}
			
			int select=JOptionPane.showConfirmDialog(null, "确定保存名字?","提示",JOptionPane.YES_NO_OPTION);
			switch(select){
			case 0:
				String ptext1=text1.getText();
				String ptext2=text2.getText();
				String ptext3=text3.getText();
				String ptext4=text4.getText();
				String ptext5=text5.getText();
				String ptext6=text6.getText();
				String[] name={ptext1,ptext2, ptext3,ptext4,ptext5,ptext6};
				Utilities.saveName(parent.getParttionDir(), name);
				
				menuArray[0].setText(ptext1);
				menuArray[1].setText(ptext2);
				menuArray[2].setText(ptext3);
				menuArray[3].setText(ptext4);
				menuArray[4].setText(ptext5);
				menuArray[5].setText(ptext6);
				//parent.renameMenu(name);
				
				setVisible(false);
				dispose();
				break;
			case 1:
				break;
			}
			
			
		}else if(cmd.equals("取消")){
			setVisible( false);
			dispose();
		}
	}

}
