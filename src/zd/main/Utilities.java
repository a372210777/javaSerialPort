package zd.main;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.comm.CommDriver;
import javax.swing.JCheckBoxMenuItem;

import zd.help.SerialParameters;

public class Utilities {

	public static final int[] normal = { 0x0f, 0x01, 0x02, 0x03, 0x04, 0x05,
			0x06, 0x00, 0x00, 0xf0 };
	public static final int[] normal2 = { 0x0f, 0x01, 0x02, 0x03, 0xff, 0xff,
			0xff, 0x00, 0x00, 0xf0 };

	public static final int[] pressing = { 0x0f, 0x01, 0x02, 0x03, 0x04, 0x05,
			0x06, 0x00, 0xff, 0xf0 };

	public static final int[] pressing2 = { 0x0f, 0x01, 0x02, 0x03, 0xff, 0xff,
			0xff, 0x00, 0xff, 0xf0 };

	public static final int[] shutdown = { 0x0f, 0xff, 0xff, 0xff, 0xff, 0xff,
			0xff, 0xff, 0x00, 0xf0 };

	public static final int[] boot = { 0x0f, 0xff, 0xff, 0xff, 0xff, 0xff,
			0xff, 0x00, 0x00, 0xf0 };

	/**
	 * 保存连接参数到配置文件
	 * */
	public static void saveConnParam(String filePath, String[] params) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			prop.load(fis);
			OutputStream fos = new FileOutputStream(filePath);

			prop.setProperty("端口名", params[0]);
			prop.setProperty("波特率", params[1]);
			prop.setProperty("浮动控制输入", params[2]);
			prop.setProperty("浮动控制输出", params[3]);
			prop.setProperty("数据位", params[4]);
			prop.setProperty("停止位", params[5]);
			prop.setProperty("奇偶校验", params[6]);

			prop.store(fos, "");
			fis.close();
			fos.close();
		} catch (IOException e) {
		}
	}

	/**
	 * int数组转字节数组 int类型占4个字节，一个字节=8位 byte类型占一个字节=8位 一个int=4个byte
	 * */
	public static byte[] intArrayToByteArray(int[] array) {
		byte[] b = new byte[array.length * 4];
		for (int i = 0; i < array.length; i++) {
			ByteArrayOutputStream boutput = new ByteArrayOutputStream();
			DataOutputStream doutput = new DataOutputStream(boutput);
			try {
				doutput.writeInt(array[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] temp = boutput.toByteArray();

			for (int j = 0; j < temp.length; j++) {
				b[i * 4 + j] = temp[j];
			}

		}

		return b;
	}

	/**
	 * 保存重命名分区名字
	 * */
	public static void saveName(String filePath, String[] name) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
//			InputStream fis=cs.getResourceAsStream("parttion.properties");
			prop.load(fis);
			OutputStream fos = new FileOutputStream(filePath);
			for (int i = 0; i < name.length; i++) {
				prop.setProperty("parttion" + (i + 1) + "_name", name[i]);
			}

			prop.store(fos, "");
		} catch (IOException e) {
		}
	}

	/**
	 * 登陆验证
	 * */
	public static boolean login(String filePath, String userName, String pwd) {

		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			try {
				props.load(in);
				if (props.getProperty("userName").equals(userName)
						&& props.getProperty("password").equals(pwd)) {
					in.close();
					return true;
				} else {
					in.close();
					return false;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;

	}

	public static String getCurrentDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd \n HH:mm:ss");//设置日期格式
		return df.format(new Date());
	}
	
	/**
	 * 分区菜单初始化
	 * */
	public static JCheckBoxMenuItem[] initParttionMenu(String filePath) {
		Properties props = new Properties();
		JCheckBoxMenuItem[] array = new JCheckBoxMenuItem[6];
		
//		InputStream is=cs.getResourceAsStream("parttion.properties");
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
//			InputStream in = new BufferedInputStream(is);
			try {
				props.load(in);
				for (int i = 0; i < array.length; i++) {
					boolean isSelect = Boolean.parseBoolean(props
							.getProperty("parttion" + (i + 1) + "_select"));
					String name = props.getProperty("parttion" + (i + 1)
							+ "_name");
					array[i] = new JCheckBoxMenuItem(name, isSelect);

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
	/**
	 * 连接参数初始化
	 * */
	public static void initConParam(String filePath, SerialParameters param) {
		Properties props = new Properties();
		try {
//			InputStream is=cs.getResourceAsStream("ConParam.properties");
//			InputStream in = new BufferedInputStream(is);
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			try {
				props.load(in);
				param.setPortName(props.getProperty("端口名"));
				param.setBaudRate(props.getProperty("波特率"));
				param.setFlowControlIn(props.getProperty("浮动控制输入"));
				param.setFlowControlOut(props.getProperty("浮动控制输出"));
				param.setDatabits(props.getProperty("数据位"));
				param.setStopbits(props.getProperty("停止位"));
				param.setParity(props.getProperty("奇偶校验"));

				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存自定义设置播放的广播分区
	 * */
	public static void savePartitionStatus(String filePath, boolean[] select) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			prop.load(fis);
			OutputStream fos = new FileOutputStream(filePath);
			for (int i = 0; i < select.length; i++) {
				prop.setProperty("parttion" + (i + 1) + "_select", select[i]
						+ "");
			}

			prop.store(fos, "");
		} catch (IOException e) {
		}
	}
	
	public static void loadDriver(){
		String driverName = "com.sun.comm.Win32Driver";
		CommDriver driver = null;
		 
		try {
		System.loadLibrary("win32com");
		System.out.println("Win32Com Library Loaded");

		driver = (CommDriver) Class.forName(driverName)
		.newInstance();
		driver.initialize();
		System.out.println("Win32Driver Initialized");
		} catch (InstantiationException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		} catch (IllegalAccessException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		}
	}
	
	/**
	 *  返回配置文件目录
	 * */
	public static String getPath(String fileName){
		String dir = System.getProperty("user.dir");
		char newChar = '\\';
		char oldChar = '/';
		String newDir = dir.replace(newChar, oldChar);
		String filePath=newDir+"/configure/"+fileName;
		return filePath;
	}
	
	/**
	 * 获取密码
	 * */
	public static String getOldPassword(String fileName){
		Properties props = new Properties();
		String filePath=getPath(fileName);
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			try {
				props.load(in);
				String password=props.getProperty("password");
				if(password!=null){
					return password;
				}
				return "admin";
			} catch (IOException e) {
				e.printStackTrace();
				return "admin";
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "admin";
		}


	}
	
	/**
	 * 修改密码
	 * */
	public  static boolean modifyPassword(String pwd){
		Properties prop = new Properties();
		String filePath=getPath("userlogin.properties");
		try {
			InputStream fis = new FileInputStream(filePath);
			prop.load(fis);
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty("password",pwd);
			prop.store(fos, "");
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
