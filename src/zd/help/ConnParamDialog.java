package zd.help;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import zd.main.MainFrame;
import zd.main.Utilities;

public class ConnParamDialog extends Dialog implements ActionListener {
	private MainFrame parent;
	private SerialParameters parameters;
	private ConfigurationPanel configurationPanel;

	public ConnParamDialog(Frame owner, SerialParameters parameters) {
		super(owner);
		this.parent = (MainFrame) owner;
		this.parameters = parameters;

		configurationPanel = new ConfigurationPanel(parent);

		add(configurationPanel, "Center");

		Panel buttonPanel = new Panel();
		Button yesButton = new Button("确定");
		yesButton.addActionListener(this);
		buttonPanel.add(yesButton);
		Button noButton = new Button("取消");
		noButton.addActionListener(this);
		buttonPanel.add(noButton);
		add(buttonPanel, "South");
		
		
		setSize(300, 350);
		setLocation(parent.getLocationOnScreen().x + 30,
				parent.getLocationOnScreen().y + 30);
		setVisible(true);
		ImageIcon icon=new ImageIcon(System.getProperty("user.dir")+"/images/logo.png");
		setIconImage(icon.getImage()); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("确定")) {
			int select=JOptionPane.showConfirmDialog(null, "确定保存配置参数并生效?", "提示", JOptionPane .YES_NO_OPTION);
			switch(select){
			case 0://确定
				String portName=this.configurationPanel.portChoice.getSelectedItem();
				String baudRate=this.configurationPanel.baudChoice.getSelectedItem();
				String tempflowControlIn=this.configurationPanel.flowChoiceIn.getSelectedItem();
				String tempflowControlOut=this.configurationPanel.flowChoiceOut.getSelectedItem();
				String dataBit=this.configurationPanel.databitsChoice.getSelectedItem();
				String stopBit=this.configurationPanel.stopbitsChoice.getSelectedItem();
				String tempParity=this.configurationPanel.parityChoice.getSelectedItem();
				String parity=null;
				if(tempParity.equals("None")){
					parity="0";
				}else if(tempParity.equals("Even")){
					parity="2";
				}else{
					parity="1";
				}
				//--------------
				String flowControlIn=null;
				String flowControlOut=null;;
				if(tempflowControlIn.equals("None")){
					flowControlIn="0";
				}else if(tempflowControlIn.equals("Xon/Xoff In")){
					flowControlIn="4";
				}else{
					flowControlIn="1";
				}
				//-------
				
				if(tempflowControlOut.equals("None")){
					flowControlOut="0";
				}else if(tempflowControlOut.equals("Xon/Xoff Out")){
					flowControlOut="8";
				}else{
					flowControlOut="2";
				}
				
				//写入配置文件
				String[] params={portName,baudRate,flowControlIn,flowControlOut,
						dataBit,stopBit,parity	};
				Utilities.saveConnParam(parent.getConfigureDir(), params);
				//端口重新实例化 和重启
				SerialConnection connection=parent.getConnection();
				if(connection!=null && connection.isOpen()){
					connection.closeConnection();
				}
				parameters.setFlowControlIn(flowControlIn);
				parameters.setFlowControlOut(flowControlOut);
				parameters.setPortName(portName);
				parameters.setBaudRate(baudRate);
				parameters.setDatabits(dataBit);
				parameters.setParity(parity);
				parameters.setStopbits(stopBit);
				
				
				setVisible(false);
				dispose();
				break;
			case 1://取消
				break;
			}
			
		} else if (cmd.equals("取消")) {
			setVisible(false);
			dispose();
		}
	}

	class ConfigurationPanel extends Panel implements ItemListener {

		private Frame parent;

		private Label portNameLabel;
		private Choice portChoice;

		private Label baudLabel;
		private Choice baudChoice;

		private Label flowControlInLabel;
		private Choice flowChoiceIn;

		private Label flowControlOutLabel;
		private Choice flowChoiceOut;

		private Label databitsLabel;
		private Choice databitsChoice;

		private Label stopbitsLabel;
		private Choice stopbitsChoice;

		private Label parityLabel;
		private Choice parityChoice;

		/**
		 * Creates and initilizes the configuration panel. The initial settings
		 * are from the parameters object.
		 */
		public ConfigurationPanel(Frame parent) {
			this.parent = parent;

			setLayout(new GridLayout(4, 4));

			portNameLabel = new Label("串口名:", Label.LEFT);
			add(portNameLabel);

			portChoice = new Choice();
			portChoice.addItemListener(this);
			add(portChoice);
			listPortChoices();
//			portChoice.select(parameters.getPortName());

			baudLabel = new Label("波特率:", Label.LEFT);
			add(baudLabel);

			baudChoice = new Choice();
			baudChoice.addItem("300");
			baudChoice.addItem("2400");
			baudChoice.addItem("9600");
			baudChoice.addItem("14400");
			baudChoice.addItem("28800");
			baudChoice.addItem("38400");
			baudChoice.addItem("57600");
			baudChoice.addItem("152000");
			baudChoice.select(Integer.toString(parameters.getBaudRate()));
			baudChoice.addItemListener(this);
			add(baudChoice);

			flowControlInLabel = new Label("浮动控制输入:", Label.LEFT);
			add(flowControlInLabel);

			flowChoiceIn = new Choice();
			flowChoiceIn.addItem("None");
			flowChoiceIn.addItem("Xon/Xoff In");
			flowChoiceIn.addItem("RTS/CTS In");
			flowChoiceIn.select(parameters.getFlowControlInString());
			flowChoiceIn.addItemListener(this);
			add(flowChoiceIn);

			flowControlOutLabel = new Label("浮动控制输出:", Label.LEFT);
			add(flowControlOutLabel);

			flowChoiceOut = new Choice();
			flowChoiceOut.addItem("None");
			flowChoiceOut.addItem("Xon/Xoff Out");
			flowChoiceOut.addItem("RTS/CTS Out");
			flowChoiceOut.select(parameters.getFlowControlOutString());
			flowChoiceOut.addItemListener(this);
			add(flowChoiceOut);

			databitsLabel = new Label("数据位:", Label.LEFT);
			add(databitsLabel);

			databitsChoice = new Choice();
			databitsChoice.addItem("5");
			databitsChoice.addItem("6");
			databitsChoice.addItem("7");
			databitsChoice.addItem("8");
			databitsChoice.select(parameters.getDatabitsString());
			databitsChoice.addItemListener(this);
			add(databitsChoice);

			stopbitsLabel = new Label("停止位:", Label.LEFT);
			add(stopbitsLabel);

			stopbitsChoice = new Choice();
			stopbitsChoice.addItem("1");
			stopbitsChoice.addItem("1.5");
			stopbitsChoice.addItem("2");
			stopbitsChoice.select(parameters.getStopbitsString());
			stopbitsChoice.addItemListener(this);
			add(stopbitsChoice);

			parityLabel = new Label("奇偶位:", Label.LEFT);
			add(parityLabel);

			parityChoice = new Choice();
			parityChoice.addItem("None");
			parityChoice.addItem("Even");
			parityChoice.addItem("Odd");
			parityChoice.select("None");
			parityChoice.select(parameters.getParityString());
			parityChoice.addItemListener(this);
			add(parityChoice);

			setConfigurationPanel();
		}

		void listPortChoices() {
			CommPortIdentifier portId;

			Enumeration en = CommPortIdentifier.getPortIdentifiers();

			// iterate through the ports.
			while (en.hasMoreElements()) {
				portId = (CommPortIdentifier) en.nextElement();
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					portChoice.addItem(portId.getName());
				}
			}
			portChoice.select(parameters.getPortName());
		}

		public void setConfigurationPanel() {
			portChoice.select(parameters.getPortName());
			baudChoice.select(parameters.getBaudRateString());
			
			String flowIn=parameters.getFlowControlInString();
			String fowOut=parameters.getFlowControlOutString();
			
			flowChoiceIn.select(parameters.getFlowControlInString());
			flowChoiceOut.select(parameters.getFlowControlOutString());
			databitsChoice.select(parameters.getDatabitsString());
			stopbitsChoice.select(parameters.getStopbitsString());
			
			
			String parity=parameters.getParityString();
			parityChoice.select(parameters.getParityString());
//			System.out.println(flowIn+" "+fowOut+" "+parity);
		}

		@Override
		public void itemStateChanged(ItemEvent e) {

		}
	}

}
