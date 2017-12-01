package info.zznet.znms.web.start;

import info.zznet.znms.web.util.ConfigUtil;

import org.apache.log4j.Logger;

public class SystemStart {
	private static Logger logger = Logger.getLogger(SystemStart.class);
	private SystemStartThread systemStartThread;
	/**
	 * Servlet初始初始化执行函数
	 */
	public void starSystem(){
		logger.info("启动Z-NMS系统 ……");
		
		//加载配置文件
		ConfigUtil.loadConfigure();
		logger.info("加载配置文件完成！");
		
		// 通过线程启动znms
		systemStartThread = new SystemStartThread();
		systemStartThread.start();
	}
	
	public void stopSystem() {
		if (systemStartThread != null) {
			systemStartThread.shutDown();
			logger.error("系统关闭清理工作");
		}
	}

}
