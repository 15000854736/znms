package info.zznet.znms.spider.util;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.entity.ApInformation;
import info.zznet.znms.web.module.screen.bean.HeatData;
import info.zznet.znms.web.util.ConfigUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class test {
	public static void main(String[] args) {
		//System.out.println(getHeatData());
		
		/*try {
			sshShell("10.40.255.253", "admin", "admin", 22, "", "");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		//insert("f:/aabb.txt", 0, "bbbb");
	}
	public static void insert(String filename,int pos,String insertContent){//pos是插入的位置
	    try {
	    	File tmp = File.createTempFile("tmp",null);
	    	tmp.deleteOnExit();
	    	
			RandomAccessFile raf = new RandomAccessFile(filename,"rw");
			FileOutputStream tmpOut = new FileOutputStream(tmp);
			FileInputStream tmpIn = new FileInputStream(tmp);
			raf.seek(pos);//首先的话是0
			byte[] buf = new byte[64];
			int hasRead = 0;
			while((hasRead = raf.read(buf))>0){
			//把原有内容读入临时文件
			tmpOut.write(buf,0,hasRead);
			
			}
			raf.seek(pos);
			raf.write(insertContent.getBytes());
			//追加临时文件的内容
			while((hasRead = tmpIn.read(buf))>0){
			raf.write(buf,0,hasRead);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 利用JSch包实现远程主机SHELL命令执行
	 * @param ip 主机IP
	 * @param user 主机登陆用户名
	 * @param psw  主机登陆密码
	 * @param port 主机ssh2登陆端口，如果取默认值，传-1
	 * @param privateKey 密钥文件路径
	 * @param passphrase 密钥的密码
	 */
	public static void sshShell(String ip, String user, String psw
			,int port ,String privateKey ,String passphrase) throws Exception{
		Session session = null;
		Channel channel = null;
		JSch jsch = new JSch();
		//设置密钥和密码
		if (privateKey != null && !"".equals(privateKey)) {
            if (passphrase != null && "".equals(passphrase)) {
            	//设置带口令的密钥
                jsch.addIdentity(privateKey, passphrase);
            } else {
            	//设置不带口令的密钥
                jsch.addIdentity(privateKey);
            }
        }
		if(port <=0){
			//连接服务器，采用默认端口
			session = jsch.getSession(user, ip);
		}else{
			//采用指定的端口连接服务器
			session = jsch.getSession(user, ip ,port);
		}
		//如果服务器连接不上，则抛出异常
		if (session == null) {
			throw new Exception("session is null");
		}
		//设置登陆主机的密码
		session.setPassword(psw);//设置密码   
		//设置第一次登陆的时候提示，可选值：(ask | yes | no)
		session.setConfig("StrictHostKeyChecking", "yes");
		//设置登陆超时时间   
		session.connect(30000);
		try {
			//创建sftp通信通道
			channel = (Channel) session.openChannel("shell");
			channel.connect(1000);

			//获取输入流和输出流
			InputStream instream = channel.getInputStream();
			OutputStream outstream = channel.getOutputStream();
			
			//发送需要执行的SHELL命令，需要用\n结尾，表示回车
			String shellCommand = "ls \n";
			outstream.write(shellCommand.getBytes());
			outstream.flush();
			//获取命令执行的结果
			if (instream.available() > 0) {
				byte[] data = new byte[instream.available()];
				int nLen = instream.read(data);
				
				if (nLen < 0) {
					throw new Exception("network error.");
				}
				//转换输出结果并打印出来
				String temp = new String(data, 0, nLen,"iso8859-1");
				System.out.println(temp);
			}
		    outstream.close();
		    instream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.disconnect();
			channel.disconnect();
		}
	}
	
	private static String getHeatData(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BufferedImage bufferedImage = null;
		try {
			File imageFile = new File("F:\\sy_60815039346.jpg");
			if(!imageFile.exists() || !imageFile.isFile()) {
				return "";
			}
			Date date= new Date(imageFile.lastModified());
			System.out.println(test.class.getClass().getResource("/").getPath() );
			bufferedImage = ImageIO.read(imageFile);
		} catch (IOException e) {
			ZNMSLogger.error(e);
			return "";
		}   
		int width = bufferedImage.getWidth();   
		int height = bufferedImage.getHeight();
		return width+" * "+height;
	}
}
