package info.zznet.znms.spider.util;

import info.zznet.znms.base.common.ZNMSLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHTest {
	
	
	public static String exec(String host,String user,String psw,int port,String command){
		String result="";
		Session session =null;
		ChannelExec openChannel =null;
		try {
			JSch jsch=new JSch();
			session = jsch.getSession(user, host, port);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(psw);
			session.connect();
			openChannel = (ChannelExec) session.openChannel("exec");
			openChannel.setCommand(command);
			int exitStatus = openChannel.getExitStatus();
			System.out.println(exitStatus);
			openChannel.connect();  
            InputStream in = openChannel.getInputStream();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
            String buf = null;
            System.out.println(new String(reader.readLine().getBytes(),"UTF-8"));
            while ((buf = reader.readLine()) != null) {
            	result+= new String(buf.getBytes("gbk"),"UTF-8")+" \r\n";  
            }  
		} catch (JSchException | IOException e) {
			result+=e.getMessage();
            if(result.endsWith("fail")){
            	ZNMSLogger.info("用户名密码错误");
            }

		}finally{
			if(openChannel!=null&&!openChannel.isClosed()){
				openChannel.disconnect();
			}
			if(session!=null&&session.isConnected()){
				session.disconnect();
			}
		}
		return result;
	}
	
	
	
	public static void main(String args[]){
		//String exec = exec("192.168.1.90", "root", "zznet@2016", 22, "df -h; free;who;");
		String exec = exec("10.40.255.253", "admin", "admin", 22, "");
		ZNMSLogger.info(exec);	
	}
}