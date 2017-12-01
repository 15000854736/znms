package info.zznet.znms.web.util;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.util.StringUtil;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 路径工具类
 * @author 卓智网络科技有限公司
 * @version 1.0
 * @since 1.0
 * @date: 2015-08-21
 */ 

public class PathUtil {
	private final static Logger logger = Logger
			.getLogger(PathUtil.class);
	/**
	 * 应用程的安装路径
	 */
	private static String appRootPath;
	
	/**
	 * 公用配置文件存放的目录
	 */
	private static String propertyPath;
	
	/**
	 * web运行容器路径，如tomcat
	 */
	private static String appWebPath;

	private static String aapConfPath;
	
	public static String getAppWebPath() {
		if (appWebPath == null) {
			appWebPath = System.getProperty("catalina.base");
		}
		logger.debug("appWebPath="+ appWebPath);
		return appWebPath;
	}
	
	
	/**
	 * 获取zzportal的安装目录
	 * @return
	 */
	public static String getAppRootPath() {
		if (appRootPath == null) {
			String tomcatPath = System.getProperty("catalina.base");
			if(tomcatPath.contains("tomcat")) {
				appRootPath = tomcatPath.substring(0, tomcatPath.lastIndexOf(File.separator));
			}
			else {
				appRootPath ="/tmp/";
			}
		}
		logger.debug("appRootPath="+ appRootPath);
		return appRootPath;
	}
	
	/**
	 * 获取公用属性文件的存放路径
	 * @return 公用属性文件的存放路径
	 */
	public static String getPropertyPath() {

		if (null==propertyPath){
			propertyPath = PathUtil.class.getResource("").toString().replace("info/zznet/znms/web/util/", "").replace("file:", "");
		}
		return propertyPath;
	}

	public static String getAppConfPath() {
		if (aapConfPath == null) {
			aapConfPath = "/opt/zzportal/conf/";
		}
		return aapConfPath;
	}
	
	/**
	 * 获取服务器的绑定地址
	 * @return
	 */
	public static String getServerBindAddress() {
		String serverIp = "";
		String webxml = PathUtil.getAppWebPath() + "/conf/server.xml";
		ZNMSLogger.debug("Tomcat config file path : " + webxml);
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(webxml));
			Element root = document.getRootElement();
			List<Element> elementList = root.elements();
			for (Element child : elementList) {
				if (child.getName().equals("Service")) {
					List<Element> list = child.elements();
					for (Element l : list) {
						Attribute a = l.attribute("address");
						if (l.getName().equals("Connector") && a != null) {
							serverIp = a.getStringValue();
						}
					}

				}
			}
		} catch (DocumentException e) {
			ZNMSLogger.error("Failed to get attached ip of tomcat : ", e);
		}
		if(StringUtil.isNullString(serverIp)){
			try {
				serverIp = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				ZNMSLogger.error("Failed to get local host address : ", e);
			}
		}
		return serverIp;
	}
}
