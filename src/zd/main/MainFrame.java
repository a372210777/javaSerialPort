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
	private JCheckBoxMenuItem[] parttionMenuArray;// 分区菜单
	private JMenuItem renameParttion;// 重命名菜单
	private JMenuItem saveSelected;

	private JMenu user;// 用户选项
	private JMenuItem changePwd;
	private JMenuItem history;

	private JMenu help;
	private JMenuItem explain;
	private JMenuItem edition;

	private JButton normal = new JButton("正常播放");
	private JButton normal2 = new JButton("正常播放2");
	private JButton pressing = new JButton("紧急播放");
	private JButton pressiing2 = new JButton("紧急播放2");
	private JButton define = new JButton("自定义播放");
	private JButton definePressing = new JButton("自定义紧急播放");
	private JButton stop = new JButton("停止");


	private JLabel currentDate;// 日期显示
	private JLabel stayTime;// 播放持续时间
	private JLabel currentStatus_label;// 播放种类

	private String configureDir;// 配置文件目录

	private SerialConnection connection;
	private SerialParameters parameters;

	static SystemTray tray = null; // 本操作系统托盘的实例
	static TrayIcon trayIcon = null; // 托盘图标
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
//		System.out.println("当前路径:"+System.getProperty("user.dir"));
		
		Utilities.loadDriver();
		
		mb = new JMenuBar();

		fileMenu = new JMenu("文件");
		openItem = new JMenuItem("打开端口");
		saveItem = new JMenuItem("关闭端口");
		connParam = new JMenuItem("连接参数配置");
		exitItem = new JMenuItem("退出");
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(connParam);
		fileMenu.add(exitItem);
		saveItem.addActionListener(this);
		openItem.addActionListener(this);
		exitItem.addActionListener(this);
		connParam.addActionListener(this);

		bootOption = new JMenu("开机");
		boot = new JMenuItem("开机");
		shutdown = new JMenuItem("关机");
		bootOption.add(boot);
		bootOption.add(shutdown);
		boot.addActionListener(this);
		shutdown.addActionListener(this);

		set = new JMenu("分区设置");
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

		// 分区菜单
//		System.out.println(":"+rootFile);
//		parttionMenuArray = Utilities.initParttionMenu(rootFile
//				+ "/parttion.properties");
		parttionMenuArray = Utilities.initParttionMenu(parttionFile);
		for (int i = 0; i < parttionMenuArray.length; i++) {
			set.add(parttionMenuArray[i]);
			parttionMenuArray[i].addActionListener(this);
		}
		renameParttion = new JMenuItem("重命名分区");
		renameParttion.addActionListener(this);
		saveSelected = new JMenuItem("保存选中状态");
		saveSelected.addActionListener(this);
		set.add(renameParttion);
		set.add(saveSelected);

		user = new JMenu("用户");
		changePwd = new JMenuItem("更改密码");
		history = new JMenuItem("查看历史记录");
		history.setEnabled(false);
		user.add(changePwd);
		user.add(history);
		changePwd.addActionListener(this);
		history.addActionListener(this);

		help = new JMenu("帮助");
		explain = new JMenuItem("使用说明");
		edition = new JMenuItem("版本说明");
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
		normal.setText("正常播放所有分区");
		normal.setBounds(48, 27, 140, 32);

		getContentPane().add(normal2);
		normal2.setText("正常播放1~3分区");
		normal2.setBounds(48, 76, 140, 32);

		getContentPane().add(pressing);
		pressing.setText("紧急播放所有分区");
		pressing.setBounds(48, 128, 140, 32);

		getContentPane().add(pressiing2);
		pressiing2.setText("紧急播放1~3分区");
		pressiing2.setBounds(48, 178, 140, 32);

		getContentPane().add(define);
		define.setText("自定义正常播放");
		define.setBounds(48, 228, 140, 32);

		getContentPane().add(definePressing);
		definePressing.setText("自定义紧急播放");
		definePressing.setBounds(48, 279, 140, 32);

		getContentPane().add(stop);
		stop.setText("停止播放");
		stop.setBounds(48, 335, 140, 32);
		{
			currentStatus_label = new JLabel();
			getContentPane().add(currentStatus_label);
			currentStatus_label.setText("\u72b6\u6001:");
			currentStatus_label.setBounds(48, 448, 219, 24);
			currentStatus_label.setForeground(new java.awt.Color(255, 0, 0));
			currentStatus_label.setFont(new java.awt.Font("宋体", 0, 12));
		}
		{
			stayTime = new JLabel();
			getContentPane().add(stayTime);
			stayTime.setText("\u6301\u7eed\u65f6\u95f4:");
			stayTime.setBounds(48, 478, 230, 30);
			stayTime.setForeground(new java.awt.Color(255, 0, 0));
			stayTime.setFont(new java.awt.Font("宋体", 0, 12));
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
		setTitle("广播播放控制程序");
		this.setFont(new java.awt.Font("Arial", 0, 10));

		// 从配置文件载入连接参数配置
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

		if (SystemTray.isSupported()) // 如果操作系统支持托盘
		{
			this.tray();
		}

		new DateThread(currentDate).start();

		timer = new Timer();
		task = new difineTimerTask(stayTime);

		stayTime.setText("持续时间:0秒");
		currentStatus_label.setText("\u72b6\u6001:\u5173\u673a");
		stayTime.setText("持续时间:0天0时0分0秒");

		ImageIcon icon=new ImageIcon(configureDir+"/images/logo.png");
		setIconImage(icon.getImage()); 
		
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		while(en.hasMoreElements()){//是否有端口
			CommPortIdentifier	portId = (CommPortIdentifier) en.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				hasProt=true;
//				defaultPort=portId.getName();
//				parameters.setPortName(defaultPort);
				break;
			}
		}
		
	}

	/************** 构造方法结束 ************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals(openItem.getText())) {// 打开端口
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
					AlertDialog ad = new AlertDialog(this, "打开端口错误!", "打开端口错误,",
							e1.getMessage() + ".", "请重新配置连接参数!再尝试一遍");
					// openButton.setEnabled(true);
					e1.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(null, "未找到串口！");
			}
		

		} else if (cmd.equals(saveItem.getText())) {// 关闭端口
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

				stayTime.setText("持续时间:0");
				currentStatus_label.setText("状态:关机");
				task.clearTime();
				task.changeModel();

			}

		} else if (cmd.equals(connParam.getText())) {// 打开连接参数配置面板
			new ConnParamDialog(this, parameters);

		} else if (cmd.equals(exitItem.getText())) {// 退出
			if (connection != null && connection.isOpen()) {
				// connection.writeData(Utilities.shutdown);
				connection.closeConnection();
			}
			System.exit(0);
		} else if (cmd.equals(boot.getText())) {// 开机
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

				stayTime.setText("持续时间:0");
				currentStatus_label.setText("状态:开机");
				task.clearTime();
			}

		} else if (cmd.equals(shutdown.getText())) {// 关机
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

				stayTime.setText("持续时间:0");
				currentStatus_label.setText("状态:关机");
				task.clearTime();
				task.changeModel();
			}
		} else if (cmd.equals(parttionMenuArray[0].getText())) { // 分区1

		} else if (cmd.equals(parttionMenuArray[1].getText())) {// 分区2

		} else if (cmd.equals(parttionMenuArray[2].getText())) {

		} else if (cmd.equals(parttionMenuArray[3].getText())) {

		} else if (cmd.equals(parttionMenuArray[4].getText())) {

		} else if (cmd.equals(parttionMenuArray[5].getText())) {

		} else if (cmd.equals(renameParttion.getText())) {// 重命名分区
			String name[] = new String[parttionMenuArray.length];
			for (int i = 0; i < parttionMenuArray.length; i++) {
				name[i] = parttionMenuArray[i].getText();
			}
			new RenameDialog(this, name, parttionMenuArray);
		} else if (cmd.equals(saveSelected.getText())) {// 保存分区选中状态
			boolean[] isSelected = new boolean[6];
			for (int i = 0; i < parttionMenuArray.length; i++) {
				if (parttionMenuArray[i].isSelected()) {
					isSelected[i] = true;
				} else {
					isSelected[i] = false;
				}
			}
			Utilities.savePartitionStatus(getParttionDir(), isSelected);
		} else if (cmd.equals(explain.getText())) {// 使用说明
			JOptionPane.showMessageDialog(null, "运行程序前请先确认当前JDK版本大于或者等于1.2");
		} else if (cmd.equals(edition.getText())) {// 版本
			JOptionPane.showMessageDialog(null, "当前版本:1.0.0 \n制造商:仲迪科技有限公司");

		} else if (cmd.equals(changePwd.getText())) {// 变更登陆密码
			new ModifyPassword(this);
		} else if (cmd.equals(history.getText())) {// 历史记录

		} else if (cmd.equals(normal.getText())) {// 正常播放
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "请先打开端口", "提示",
						JOptionPane.NO_OPTION);
				return;
			}
			currentStatus_label.setText("状态:正常播放");
			task.clearTime();
			if (!isTimerRun) {
				timer.schedule(task, 0, 1000);
				isTimerRun = true;
			} else {
			}
			connection.writeData(Utilities.normal, Constant.other);
		} else if (cmd.equals(normal2.getText())) {// 正常播放1到3区
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "请先打开端口", "提示",
						JOptionPane.NO_OPTION);
				return;
			}
			currentStatus_label.setText("状态:正常播放1到3分区");
			task.clearTime();
			if (!isTimerRun) {
				timer.schedule(task, 0, 1000);
				isTimerRun = true;
			} else {
			}
			connection.writeData(Utilities.normal2, Constant.other);
		} else if (cmd.equals(pressing.getText())) {// 紧急播放
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "请先打开端口", "提示",
						JOptionPane.NO_OPTION);
				return;
			}
			task.clearTime();
			if (!isTimerRun) {
				timer.schedule(task, 0, 1000);
				isTimerRun = true;
			} else {
			}
			currentStatus_label.setText("状态:紧急播放所有");
			connection.writeData(Utilities.pressing, Constant.other);
		} else if (cmd.equals(pressiing2.getText())) {// 紧急播放1到3区
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "请先打开端口", "提示",
						JOptionPane.NO_OPTION);
				return;
			}
			currentStatus_label.setText("状态:紧急播放所有分区");
			task.clearTime();
			if (!isTimerRun) {
				timer.schedule(task, 0, 1000);
				isTimerRun = true;
			} else {
			}
			connection.writeData(Utilities.pressing2, Constant.other);
		} else if (cmd.equals(define.getText())) {// 自定义播放
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "请先打开端口", "提示",
						JOptionPane.NO_OPTION);
				return;
			}
			currentStatus_label.setText("状态:自定义分区正常播放");
			task.clearTime();
			if (!isTimerRun) {
				timer.schedule(task, 0, 1000);
				isTimerRun = true;
			} else {
			}
			connection.writeData(getDataFromMenu(0), Constant.other);
		} else if (cmd.equals(definePressing.getText())) {// 自定义紧急播放
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "请先打开端口", "提示",
						JOptionPane.NO_OPTION);
				return;
			}
			currentStatus_label.setText("状态:自定义分区紧急播放");
			task.clearTime();
			if (!isTimerRun) {
				timer.schedule(task, 0, 1000);
				isTimerRun = true;
			} else {
			}
			connection.writeData(getDataFromMenu(1), Constant.other);

		} else if (cmd.equals(stop.getText())) {// 停止
			if (connection == null || !connection.isOpen()) {
				JOptionPane.showMessageDialog(null, "操作无效！你还没开启端口", "提示",
						JOptionPane.NO_OPTION);
				return;
			}
			stayTime.setText("持续时间:0秒");
			currentStatus_label.setText("状态:停止");
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
				// 以下自己的主窗口
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
				// 将托盘图标添加到系统的托盘实例中
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
			// connection.writeData(Utilities.shutdown);//广播关机
			connection.closeConnection();
		}

	}

	/**
	 * 返回连接参数配置文件目录
	 * */
	public String getConfigureDir() {

		 return conParamFile;
//		return rootFile+"/ConParam.properties";
//		return configureDir + "/configure/ConParam.properties";

	}

	/**
	 * 返回分区名称配置文件目录
	 * */
	public String getParttionDir() {

		 return parttionFile;
//		return rootFile+"/parttion.properties";
//		return configureDir + "/configure/parttion.properties";

	}
	
	/**
	 * 返回连接对象
	 * */
	public SerialConnection getConnection() {
		return connection;
	}

	/**
	 * 分区菜单重命名
	 * */
	public void renameMenu(String[] name) {
		for (int i = 0; i < parttionMenuArray.length; i++) {
			parttionMenuArray[i].setText(name[i]);
		}
	}

	/**
	 * 将分区菜单的选中项转换成对应的数据
	 * 
	 * 参数：紧急 正常
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
		if (flag == 0) {// 正常
			data[8] = 0x00;
		} else {// 紧急
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

		tray = SystemTray.getSystemTray(); // 获得本操作系统托盘的实例
		// ImageIcon icon = new ImageIcon(configureDir+"/images/broadcast.png");
		// // 将要显示到托盘中的图标
		ImageIcon icon = new ImageIcon(MainFrame.class.getClassLoader()
				.getResource("res/broadcast.png"));
		PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单
		MenuItem show = new MenuItem("打开程序(s)");
		MenuItem exit = new MenuItem("退出程序(x)");
		pop.add(show);
		pop.add(exit);
		trayIcon = new TrayIcon(icon.getImage(), "广播", pop);
		trayIcon.setImageAutoSize(true);
		// 添加鼠标监听器，当鼠标在托盘图标上双击时，默认显示窗口
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) // 鼠标双击
				{
					// tray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
					setExtendedState(JFrame.NORMAL);
					setVisible(true); // 显示窗口
					toFront();
				}
			}
		});
		show.addActionListener(new ActionListener() // 点击“显示窗口”菜单后将窗口显示出来
		{
			public void actionPerformed(ActionEvent e) {
				// tray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
				setExtendedState(JFrame.NORMAL);
				setVisible(true); // 显示窗口
				toFront();
			}
		});
		exit.addActionListener(new ActionListener() // 点击“退出演示”菜单后退出程序
		{
			public void actionPerformed(ActionEvent e) {
				if (connection != null && connection.isOpen()) {
					connection.closeConnection();
				}
				System.exit(0); // 退出程序
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
				continuteTime.setText("播放时间:" + formateTime(i++));
			} else {
				continuteTime.setText("播放时间:0秒");
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

			day = second / 76400;// 天数
			int mod = second % 76400;

			hour = second / 3600;// 小时数
			mod = second % 3600;

			minute = mod / 60;// 分钟数
			mod = mod % 60;

			second = mod;

			return day + "天" + hour + "时" + minute + "分" + mod + "秒";
		}

		public void changeModel() {
			this.workModel = 1;
		}
	}

};
