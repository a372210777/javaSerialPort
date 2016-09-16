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
	 * �������Ӳ����������ļ�
	 * */
	public static void saveConnParam(String filePath, String[] params) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			prop.load(fis);
			OutputStream fos = new FileOutputStream(filePath);

			prop.setProperty("�˿���", params[0]);
			prop.setProperty("������", params[1]);
			prop.setProperty("������������", params[2]);
			prop.setProperty("�����������", params[3]);
			prop.setProperty("����λ", params[4]);
			prop.setProperty("ֹͣλ", params[5]);
			prop.setProperty("��żУ��", params[6]);

			prop.store(fos, "");
			fis.close();
			fos.close();
		} catch (IOException e) {
		}
	}

	/**
	 * int����ת�ֽ����� int����ռ4���ֽڣ�һ���ֽ�=8λ byte����ռһ���ֽ�=8λ һ��int=4��byte
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
	 * ������������������
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
	 * ��½��֤
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd \n HH:mm:ss");//�������ڸ�ʽ
		return df.format(new Date());
	}
	
	/**
	 * �����˵���ʼ��
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
	 * ���Ӳ�����ʼ��
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
				param.setPortName(props.getProperty("�˿���"));
				param.setBaudRate(props.getProperty("������"));
				param.setFlowControlIn(props.getProperty("������������"));
				param.setFlowControlOut(props.getProperty("�����������"));
				param.setDatabits(props.getProperty("����λ"));
				param.setStopbits(props.getProperty("ֹͣλ"));
				param.setParity(props.getProperty("��żУ��"));

				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����Զ������ò��ŵĹ㲥����
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
	 *  ���������ļ�Ŀ¼
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
	 * ��ȡ����
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
	 * �޸�����
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
