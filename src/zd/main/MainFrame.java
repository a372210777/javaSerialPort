package zd.main;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import javax.comm.CommDriver;
import javax.comm.CommPortIdentifier;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;

import zd.help.ConnParamDialog;
import zd.help.Constant;
import zd.help.SerialConnection;
import zd.help.SerialConnectionException;
import zd.help.SerialParameters;

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
public class MainFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final int HEIGHT = 600;
	final int WIDTH = 250;

	private JMenuBar mb;
	private JMenu fileMenu;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem connParam;
	private JMenuItem exitItem;

	private JMenu bootOption;
	private JMenuItem boot;
	private JMenuItem shutdown;

	private JMenu set;
	private JCheckBoxMenuItem[] parttionMenuArray;// �����˵�
	private JMenuItem renameParttion;// �������˵�
	private JMenuItem saveSelected;

	private JMenu user;// �û�ѡ��
	private JMenuItem changePwd;
	private JMenuItem history;

	private JMenu help;
	private JMenuItem explain;
	private JMenuItem edition;

	private JButton normal = new JButton("��������");
	private JButton normal2 = new JButton("��������2");
	private JButton pressing = new JButton("��������");
	private JButton pressiing2 = new JButton("��������2");
	private JButton define = new JButton("�Զ��岥��");
	private JButton definePressing = new JButton("�Զ����������");
	private JButton stop = new JButton("ֹͣ");


	private JLabel currentDate;// ������ʾ
	private JLabel stayTime;// ���ų���ʱ��
	private JLabel currentStatus_label;// ��������

	private String configureDir;// �����ļ�Ŀ¼

	private SerialConnection connection;
	private SerialParameters parameters;

	static SystemTray tray = null; // ������ϵͳ���̵�ʵ��
	static TrayIcon trayIcon = null; // ����ͼ��
	private boolean isAddTrayIcon = false;

	private Timer timer;
	private difineTimerTask task;
	private boolean isTimerRun = false;

	private String conParamFile;
	private String parttionFile;
	private String userLoginFile;

	private String rootFile;
	
	private boolean hasProt=false;
	private String defaultPort="";
	public MainFrame() {
//		System.out.println("��ǰ·��:"+System.getProperty("user.dir"));
		
		Utilities.loadDriver();
		
		mb = new JMenuBar();

		fileMenu = new JMenu("�ļ�");
		openItem = new JMenuItem("�򿪶˿�");
		saveItem = new JMenuItem("�رն˿�");
		connParam = new JMenuItem("���Ӳ�������");
		exitItem = new JMenuItem("�˳�");
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(connParam);
		fileMenu.add(exitItem);
		saveItem.addActionListener(this);
		openItem.addActionListener(this);
		exitItem.addActionListener(this);
		connParam.addActionListener(this);

		bootOption = new JMenu("����");
		boot = new JMenuItem("����");
		shutdown = new JMenuItem("�ػ�");
		bootOption.add(boot);
		bootOption.add(shutdown);
		boot.addActionListener(this);
		shutdown.addActionListener(this);

		set = new JMenu("��������");
		String dir = System.getProperty("user.dir");
		char newChar = '\\';
		char oldChar = '/';
		String newDir = dir.replace(newChar, oldChar);
		configureDir = newDir;
		
		conParamFile=configureDir+"/configure/ConParam.properties";
		parttionFile=configureDir+"/configure/parttion.properties";
		userLoginFile=configureDir+"/configure/userlogin.properties";
		
//		rootFile=this.getClass().getResource("").toString().substring(6);
		rootFile=this.getClass().getResource("/configure").toString().substring(6);

		// �����˵�
//		System.out.println(":"+rootFile);
//		parttionMenuArray = Utilities.initParttionMenu(rootFile
//				+ "/parttion.properties");
		parttionMenuArray = Utilities.initParttionMenu(parttionFile);
		for (int i = 0; i < parttionMenuArray.length; i++) {
			set.add(parttionMenuArray[i]);
			parttionMenuArray[i].addActionListener(this);
		}
		renameParttion = new JMenuItem("����������");
		renameParttion.addActionListener(this);
		saveSelected = new JMenuItem("����ѡ��״̬");
		saveSelected.addActionListener(this);
		set.add(renameParttion);
		set.add(saveSelected);

		user = new JMenu("�û�");
		changePwd = new JMenuItem("��������");
		history = new JMenuItem("�鿴��ʷ��¼");
		history.setEnabled(false);
		user.add(changePwd);
		user.add(history);
		changePwd.addActionListener(this);
		history.addActionListener(this);

		help = new JMenu("����");
		explain = new JMenuItem("ʹ��˵��");
		edition = new JMenuItem("�汾˵��");
		help.add(explain);
		help.add(edition);
		explain.addActionListener(this);
		edition.addActionListener(this);

		mb.add(fileMenu);
		mb.add(bootOption);
		mb.add(set);
		mb.add(user);
		mb.add(help);

		getContentPane().setLayout(null);
		getContentPane().add(normal);
		normal.setText("�����������з���");
		normal.setBounds(48, 27, 140, 32);

		getContentPane().add(normal2);
		normal2.setText("��������1~3����");
		normal2.setBounds(48, 76, 140, 32);

		getContentPane().add(pressing);
		pressing.setText("�����������з���");
		pressing.setBounds(48, 128, 140, 32);

		getContentPane().add(pressiing2);
		pressiing2.setText("��������1~3����");
		pressiing2.setBounds(48, 178, 140, 32);

		getContentPane().add(define);
		define.setText("�Զ�����������");
		define.setBounds(48, 228, 140, 32);

		getContentPane().add(definePressing);
		definePressing.setText("�Զ����������");
		definePressing.setBounds(48, 279, 140, 32);

		getContentPane().add(stop);
		stop.setText("ֹͣ����");
		stop.setBounds(48, 335, 140, 32);
		{
			currentStatus_label = new JLabel();
			getContentPane().add(currentStatus_label);
			currentStatus_label.setText("\u72b6\u6001:");
			currentStatus_label.setBounds(48, 448, 219, 24);
			currentStatus_label.setForeground(new java.awt.Color(255, 0, 0));
			currentStatus_label.setFont(new java.awt.Font("����", 0, 12));
		}
		{
			stayTime = new JLabel();
			getContentPane().add(stayTime);
			stayTime.setText("\u6301\u7eed\u65f6\u95f4:");
			stayTime.setBounds(48, 478, 230, 30);
			stayTime.setForeground(new java.awt.Color(255, 0, 0));
			stayTime.setFont(new java.awt.Font("����", 0, 12));
		}
		{
			currentDate = new JLabel();
			getContentPane().add(currentDate);
			currentDate.setText("\u5f53\u524d\u65f6\u95f4");
			currentDate.setBounds(48, 514, 140, 15);
		}

		normal.addActionListener(this);
		normal2.addActionListener(this);
		pressing.addActionListener(this);
		pressiing2.addActionListener(this);
		define.addActionListener(this);
		definePressing.addActionListener(this);
		stop.addActionListener(this);

		setJMenuBar(mb);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width / 2 - WIDTH / 2, screenSize.height / 2
				- HEIGHT / 2);
		this.addWindowListener(new winAdapter());
		setTitle("�㲥���ſ��Ƴ���");
		this.setFont(new java.awt.Font("Arial", 0, 10));

		// �������ļ��������Ӳ�������
		parameters = new SerialParameters();

		Utilities.initConParam(conParamFile,
				parameters);
//		Utilities.initConParam(rootFile + "/ConParam.properties",
//				parameters);
		connection = new SerialConnection(this, parameters, null, null);

		saveItem.setEnabled(false);
		boot.setEnabled(false);
		shutdown.setEnabled(false);

		normal.setEnabled(false);
		normal2.setEnabled(false);
		pressing.setEnabled(false);
		pressiing2.setEnabled(false);
		define.setEnabled(false);
		definePressing.setEnabled(false);
		stop.setEnabled(false);

		if (SystemTray.isSupported()) // �������ϵͳ֧������
		{
			this.tray();
		}

		new DateThread(currentDate).start();

		timer = new Timer();
		task = new difineTimerTask(stayTime);

		stayTime.setText("����ʱ��:0��");
		currentStatus_label.setText("\u72b6\u6001:\u5173\u673a");
		stayTime.setText("����ʱ��:0��0ʱ0��0��");

		ImageIcon icon=new ImageIcon(configureDir+"/images/logo.png");
		setIconImage(icon.getImage()); 
		
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		while(en.hasMoreElements()){//�Ƿ��ж˿�
			CommPortIdentifier	portId = (CommPortIdentifier) en.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				hasProt=true;
//				defaultPort=portId.getName();
//				parameters.setPortName(defaultPort);
				break;
			}
		}
		
	}

	/************** ���췽������ ************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals(openItem.getText())) {// �򿪶˿�
			if(hasProt){
				try {
					if (connection != null && !connection.isOpen()) {
						connection.openConnection();
						openItem.setEnabled(false);
						saveItem.setEnabled(true);

						boot.setEnabled(true);
						shutdown.setEnabled(false);
						normal.setEnabled(false);
						normal2.setEnabled(false);
						pressing.setEnabled(false);
						pressiing2.setEnabled(false);
						define.setEnabled(false);
						definePressing.setEnabled(false);
						stop.setEnabled(false);
					}

				} catch (SerialConnectionException e1) {
					AlertDialog ad = new AlertDialog(this, "�򿪶˿ڴ���!", "�򿪶˿ڴ���,",
							e1.getMessage() + ".", "�������������Ӳ���!�ٳ���һ��");
					// openButton.setEnabled(true);
					e1.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(null, "δ�ҵ����ڣ�");
			}
		

		} else if (cmd.equals(saveItem.getText())) {// �رն˿�
			if (connection != null && connection.isOpen()) {

				saveItem.setEnabled(false);
				openItem.setEnabled(true);

				boot.setEnabled(false);
				shutdown.setEnabled(false);
				normal.setEnabled(false);
				normal2.setEnabled(false);
				pressing.setEnabled(false);
				pressiing2.setEnabled(false);
				define.setEnabled(false);
				definePressing.setEnabled(false);
				stop.setEnabled(false);

				portClosed();

				stayTime.setText("����ʱ��:0");
				currentStatus_label.setText("״̬:�ػ�");
				task.clearTime();
				task.changeModel();

			}

		} else if (cmd.equals(connParam.getText())) {// �����Ӳ����������
			new ConnParamDialog(this, parameters);

		} else if (cmd.equals(exitItem.getText())) {// �˳�
			if (connection != null && connection.isOpen()) {
				// connection.writeData(Utilities.shutdown);
				connection.closeConnection();
			}
			System.exit(0);
		} else if (cmd.equals(boot.getText())) {// ����
			if (connection != null && connection.isOpen()) {
				boot.setEnabled(false);
				shutdown.setEnabled(true);

				normal.setEnabled(true);
				normal2.setEnabled(true);
				pressing.setEnabled(true);
				pressiing2.setEnabled(true);
				define.setEnabled(true);
				definePressing.setEnabled(true);
				stop.setEnabled(true);

				connection.writeData(Utilities.boot, Constant.boot);

				stayTime.setText("����ʱ��:0");
				currentStatus_label.setText("״̬:����");
				task.clearTime();
			}

		} else if (cmd.equals(shutdown.getText())) {// �ػ�
			if (connection != null && connection.isOpen()) {
				boot.setEnabled(true);
				shutdown.setEnabled(false);

				normal.setEnabled(false);
				normal2.setEnabled(false);
				pressing.setEnabled(false);
				pressiing2.setEnabled(false);
				define.setEnabled(false);
				definePressing.setEnabled(false);
				stop.setEnabled(false);

				connection.writeData(Utilities.shutdown, Constant.shutdown);

				stayTime.setText("����ʱ��:0");
				currentStatus_label.setText("״̬:�ػ�");
				task.clearTime();
				task.changeModel();
			}
		} else if (cmd.equals(parttionMenuArray[0].getText())) { // ����1

		} else if (cmd.equals(parttionMenuArray[1].getText())) {// ����2

		} else if (cmd.equals(parttionMenuArray[2].getText())) {

		} else if (cmd.equals(parttionMenuArray[3].getText())) {

		} else if (cmd.equals(parttionMenuArray[4].getText())) {

		} else if (cmd.equals(parttionMenuArray[5].getText())) {

		} else if (cmd.equals(renameParttion.getText())) {// ����������
			String name[] = new String[parttionMenuArray.length];
			for (int i = 0; i < parttionMenuArray.length; i++) {
				name[i] = parttionMenuArray[i].getText();
			}
			new RenameDialog(this, name, parttionMenuArray);
		} else if (cmd.equals(saveSelected.getText())) {// �������ѡ��״̬
			boolean[] isSelected = new boolean[6];
			for (int i = 0; i < parttionMenuArray.length; i++) {
				if (parttionMenuArray[i].isSelected()) {
					isSelected[i] = true;
				} else {
					isSelected[i] = false;
				}
			}
			Utilities.savePartitionStatus(getParttionDir(), isSelected);
		} else if (cmd.equals(explain.getText())) {// ʹ��˵��
			JOptionPane.showMessageDialog(null, "���г���ǰ����ȷ�ϵ�ǰJDK�汾���ڻ��ߵ���1.2");
		} else if (cmd.equals(edition.getText())) {// �汾
			JOptionPane.showMessageDialog(null, "��ǰ�汾:1.0.0 \n������:�ٵϿƼ����޹�˾");

		} else if (cmd.equals(changePwd.getText())) {// �����½����
			new ModifyPassword(this);
		} else if (cmd.equals(history.getText())) {// ��ʷ��¼

		} else if (cmd.equals(normal.getText())) {// ��������
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "���ȴ򿪶˿�", "��ʾ",
						JOptionPane.NO_OPTION);
				return;
			}
			currentStatus_label.setText("״̬:��������");
			task.clearTime();
			if (!isTimerRun) {
				timer.schedule(task, 0, 1000);
				isTimerRun = true;
			} else {
			}
			connection.writeData(Utilities.normal, Constant.other);
		} else if (cmd.equals(normal2.getText())) {// ��������1��3��
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "���ȴ򿪶˿�", "��ʾ",
						JOptionPane.NO_OPTION);
				return;
			}
			currentStatus_label.setText("״̬:��������1��3����");
			task.clearTime();
			if (!isTimerRun) {
				timer.schedule(task, 0, 1000);
				isTimerRun = true;
			} else {
			}
			connection.writeData(Utilities.normal2, Constant.other);
		} else if (cmd.equals(pressing.getText())) {// ��������
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "���ȴ򿪶˿�", "��ʾ",
						JOptionPane.NO_OPTION);
				return;
			}
			task.clearTime();
			if (!isTimerRun) {
				timer.schedule(task, 0, 1000);
				isTimerRun = true;
			} else {
			}
			currentStatus_label.setText("״̬:������������");
			connection.writeData(Utilities.pressing, Constant.other);
		} else if (cmd.equals(pressiing2.getText())) {// ��������1��3��
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "���ȴ򿪶˿�", "��ʾ",
						JOptionPane.NO_OPTION);
				return;
			}
			currentStatus_label.setText("״̬:�����������з���");
			task.clearTime();
			if (!isTimerRun) {
				timer.schedule(task, 0, 1000);
				isTimerRun = true;
			} else {
			}
			connection.writeData(Utilities.pressing2, Constant.other);
		} else if (cmd.equals(define.getText())) {// �Զ��岥��
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "���ȴ򿪶˿�", "��ʾ",
						JOptionPane.NO_OPTION);
				return;
			}
			currentStatus_label.setText("״̬:�Զ��������������");
			task.clearTime();
			if (!isTimerRun) {
				timer.schedule(task, 0, 1000);
				isTimerRun = true;
			} else {
			}
			connection.writeData(getDataFromMenu(0), Constant.other);
		} else if (cmd.equals(definePressing.getText())) {// �Զ����������
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "���ȴ򿪶˿�", "��ʾ",
						JOptionPane.NO_OPTION);
				return;
			}
			currentStatus_label.setText("״̬:�Զ��������������");
			task.clearTime();
			if (!isTimerRun) {
				timer.schedule(task, 0, 1000);
				isTimerRun = true;
			} else {
			}
			connection.writeData(getDataFromMenu(1), Constant.other);

		} else if (cmd.equals(stop.getText())) {// ֹͣ
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "������Ч���㻹û�����˿�", "��ʾ",
						JOptionPane.NO_OPTION);
				return;
			}
			stayTime.setText("����ʱ��:0��");
			currentStatus_label.setText("״̬:ֹͣ");
			task.changeModel();
			connection.writeData(Utilities.boot, Constant.other);
		}

	}

	public static void main(String[] args) {

		JFrame.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new SubstanceLookAndFeel());
				} catch (Exception e) {
					System.out
							.println("Substance Raven Graphite failed to initialize");
				}
				// �����Լ���������
//				new MainFrame();
				new LoginFrame();
			}
		});

		// new LoginFrame();
		// new HistoryFrame();
	}

	class winAdapter extends WindowAdapter {

		public void windowClosing(WindowEvent windowEvent) {
			if (!isAddTrayIcon) {
				try {
					tray.add(trayIcon);
					isAddTrayIcon = true;
					dispose();
				} catch (AWTException e) {
					e.printStackTrace();
				}
			} else {
				dispose();
			}
		}

		@Override
		public void windowIconified(WindowEvent e) {
			super.windowIconified(e);
			try {
				// ������ͼ����ӵ�ϵͳ������ʵ����
				if (!isAddTrayIcon) {
					tray.add(trayIcon);
					isAddTrayIcon = true;
					dispose();
				} else {
					dispose();
				}

			} catch (AWTException e1) {
				e1.printStackTrace();
			}

		}

	}

	public void portClosed() {
		if (connection != null && connection.isOpen()) {
			// connection.writeData(Utilities.shutdown);//�㲥�ػ�
			connection.closeConnection();
		}

	}

	/**
	 * �������Ӳ��������ļ�Ŀ¼
	 * */
	public String getConfigureDir() {

		 return conParamFile;
//		return rootFile+"/ConParam.properties";
//		return configureDir + "/configure/ConParam.properties";

	}

	/**
	 * ���ط������������ļ�Ŀ¼
	 * */
	public String getParttionDir() {

		 return parttionFile;
//		return rootFile+"/parttion.properties";
//		return configureDir + "/configure/parttion.properties";

	}
	
	/**
	 * �������Ӷ���
	 * */
	public SerialConnection getConnection() {
		return connection;
	}

	/**
	 * �����˵�������
	 * */
	public void renameMenu(String[] name) {
		for (int i = 0; i < parttionMenuArray.length; i++) {
			parttionMenuArray[i].setText(name[i]);
		}
	}

	/**
	 * �������˵���ѡ����ת���ɶ�Ӧ������
	 * 
	 * ���������� ����
	 * */
	public int[] getDataFromMenu(int flag) {
		boolean[] isSelected = new boolean[6];
		for (int i = 0; i < parttionMenuArray.length; i++) {
			if (parttionMenuArray[i].isSelected()) {
				isSelected[i] = true;
			} else {
				isSelected[i] = false;
			}
		}
		int[] data = new int[10];
		data[0] = 0x0f;
		data[7] = 0x00;
		if (flag == 0) {// ����
			data[8] = 0x00;
		} else {// ����
			data[8] = 0xff;
		}

		data[9] = 0xf0;
		for (int i = 0; i < isSelected.length; i++) {
			if (isSelected[i]) {
				switch (i + 1) {
				case 1:
					data[1] = 0x01;
					break;
				case 2:
					data[2] = 0x02;
					break;
				case 3:
					data[3] = 0x03;
					break;
				case 4:
					data[4] = 0x04;
					break;
				case 5:
					data[5] = 0x05;
					break;
				case 6:
					data[6] = 0x06;
					break;
				}
			} else {
				switch (i + 1) {
				case 1:
					data[1] = 0xff;
					break;
				case 2:
					data[2] = 0xff;
					break;
				case 3:
					data[3] = 0xff;
					break;
				case 4:
					data[4] = 0xff;
					break;
				case 5:
					data[5] = 0xff;
					break;
				case 6:
					data[6] = 0xff;
					break;
				}
			}
		}
		return data;
	}

	void tray() {
//		System.out.println(MainFrame.class.getClassLoader()
//				.getResource("res/broadcast.png").toString());

		tray = SystemTray.getSystemTray(); // ��ñ�����ϵͳ���̵�ʵ��
		// ImageIcon icon = new ImageIcon(configureDir+"/images/broadcast.png");
		// // ��Ҫ��ʾ�������е�ͼ��
		ImageIcon icon = new ImageIcon(MainFrame.class.getClassLoader()
				.getResource("res/broadcast.png"));
		PopupMenu pop = new PopupMenu(); // ����һ���Ҽ�����ʽ�˵�
		MenuItem show = new MenuItem("�򿪳���(s)");
		MenuItem exit = new MenuItem("�˳�����(x)");
		pop.add(show);
		pop.add(exit);
		trayIcon = new TrayIcon(icon.getImage(), "�㲥", pop);
		trayIcon.setImageAutoSize(true);
		// ������������������������ͼ����˫��ʱ��Ĭ����ʾ����
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) // ���˫��
				{
					// tray.remove(trayIcon); // ��ϵͳ������ʵ�����Ƴ�����ͼ��
					setExtendedState(JFrame.NORMAL);
					setVisible(true); // ��ʾ����
					toFront();
				}
			}
		});
		show.addActionListener(new ActionListener() // �������ʾ���ڡ��˵��󽫴�����ʾ����
		{
			public void actionPerformed(ActionEvent e) {
				// tray.remove(trayIcon); // ��ϵͳ������ʵ�����Ƴ�����ͼ��
				setExtendedState(JFrame.NORMAL);
				setVisible(true); // ��ʾ����
				toFront();
			}
		});
		exit.addActionListener(new ActionListener() // ������˳���ʾ���˵����˳�����
		{
			public void actionPerformed(ActionEvent e) {
				if (connection != null && connection.isOpen()) {
					connection.closeConnection();
				}
				System.exit(0); // �˳�����
			}
		});
	}

	class DateThread extends Thread {

		private JLabel time_label;

		public DateThread(JLabel label) {
			time_label = label;
		}

		@Override
		public void run() {
			while (true) {
				time_label.setText(Utilities.getCurrentDate());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	class difineTimerTask extends TimerTask {

		private JLabel continuteTime;
		private int i = 0;
		private int workModel = 0;

		public difineTimerTask(JLabel continuteTime) {
			this.continuteTime = continuteTime;
		}

		@Override
		public void run() {
			if (workModel == 0) {
				continuteTime.setText("����ʱ��:" + formateTime(i++));
			} else {
				continuteTime.setText("����ʱ��:0��");
			}

		}

		public void clearTime() {
			workModel = 0;
			i = 0;
		}

		public String formateTime(int second) {
			int day = 0;
			int hour = 0;
			int minute = 0;

			day = second / 76400;// ����
			int mod = second % 76400;

			hour = second / 3600;// Сʱ��
			mod = second % 3600;

			minute = mod / 60;// ������
			mod = mod % 60;

			second = mod;

			return day + "��" + hour + "ʱ" + minute + "��" + mod + "��";
		}

		public void changeModel() {
			this.workModel = 1;
		}
	}

};
