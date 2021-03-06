package info.zznet.znms.base.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
	/**
	 * 处理了所有问题的ip获取方法
	 * @param request
	 * @return
	 */
	public static String getRealIp(HttpServletRequest request){  
        String ipAddress = request.getHeader("x-forwarded-for");  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getRemoteAddr();  
                if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
                    //根据网卡取本机配置的IP  
                    InetAddress inet=null;  
                    try {  
                        inet = InetAddress.getLocalHost();  
                    } catch (UnknownHostException e) {  
                        e.printStackTrace();  
                    }  
                    ipAddress= inet.getHostAddress();  
                }  
            }  
            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
            if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
                if(ipAddress.indexOf(",")>0){  
                    ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
                }  
            }  
            return ipAddress;   
    }  
	
	/**
	 * 比较传入ip是否在起始ip和终止ip之间
	 * @param ip 要比较的ip
	 * @param ipStarting  比较的起始ip
	 * @param ipEnding   比较的终止ip
	 * @return
	 */
	public static boolean isIpValid(String ip,String ipStarting,String ipEnding) {
		String[] ipStartingSeg = ipStarting.split(",");
		String[] ipEndingSeg = ipEnding.split(",");
		
		String[] ipSeg = ip.split("\\.");
		return (Integer.parseInt(ipSeg[0])>=Integer.parseInt(ipStartingSeg[0]) && Integer.parseInt(ipSeg[0])<=Integer.parseInt(ipEndingSeg[0]) 
				&& Integer.parseInt(ipSeg[1])>=Integer.parseInt(ipStartingSeg[1]) && Integer.parseInt(ipSeg[1])<=Integer.parseInt(ipEndingSeg[1]) 
				&& Integer.parseInt(ipSeg[2])>=Integer.parseInt(ipStartingSeg[2]) && Integer.parseInt(ipSeg[2])<=Integer.parseInt(ipEndingSeg[2]) 
				&& Integer.parseInt(ipSeg[3])>=Integer.parseInt(ipStartingSeg[3]) && Integer.parseInt(ipSeg[3])<=Integer.parseInt(ipEndingSeg[3]) );
	} 
	
	/**
	 * 判断是否为ip
	 * @param addr
	 * @return
	 */
	public static boolean isIP(String addr) {
		if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
			return false;
		}
		/**
		 * 判断IP格式和范围
		 */
		String rexp = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

		Pattern pat = Pattern.compile(rexp);

		Matcher mat = pat.matcher(addr);

		boolean ipAddress = mat.find();

		return ipAddress;
	}
}
