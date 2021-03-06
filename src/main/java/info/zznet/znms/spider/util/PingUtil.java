package info.zznet.znms.spider.util;

import info.zznet.znms.base.common.ZNMSLogger;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingUtil {
    
    public static boolean isReachable(String ipAddress) throws Exception {
        int  timeOut =  1000 ;  //超时时间
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);    // 当返回值是true时，说明host是可用的，false则不可。
        return status;
    }
    
    public static boolean ping(String ipAddress) throws Exception {
        String line = null;
        BufferedReader buf =null;
        try {
            String commond = "ping " + ipAddress;
            if(System.getProperty("os.name").startsWith("Windows")) {
                // For Windows
                commond += " -n 3";
            } else {
                // For Linux and OSX
                commond += " -c 3 -w 3";
            }
            Process pro = Runtime.getRuntime().exec(commond);
            pro.waitFor();

            boolean flag=false;
            if(pro.exitValue() == 0) {
                flag = true;
            } else {
                flag = false;
            }
            /*buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));

            int i=0;
            while ((line = buf.readLine()) != null){
            	  flag = getCheckResult(line);   
            	  if(flag||i==3){
            		 break;
            	  }
            	  i++;
            }*/
            return flag;
        } catch (Exception ex) {
        	ZNMSLogger.error(ex.getMessage());
            return false;  
        } finally {   
            try {
                if(null!=buf){
                    buf.close();
                }
            } catch (IOException e) {
            	ZNMSLogger.error(e); 
            }  
        }
    }
    //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
    private static boolean getCheckResult(String line) {  // System.out.println("控制台输出的结果为:"+line);  
        boolean flag=line.indexOf("ttl")>0||line.indexOf("TTL")>0;
        return flag;
    }
    
    public static void main(String[] args) throws Exception {
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("请输入IP");
    	String ip=scanner.next();
    	System.out.println(ping(ip)); 	//192.168.1.16 的回复: 字节=32 时间<1ms TTL=64
    	//String str="64 bytes from 192.168.1.30: icmp_seq=2 ttl=64 time=0.389 ms";
    	//boolean a=str.indexOf("ttl")>0||str.indexOf("TTL")>0;
    	//System.out.println(a+"   "+getCheckResult("64 bytes from 192.168.1.30: icmp_seq=2 ttl=64 time=0.389 ms"));
	}
}