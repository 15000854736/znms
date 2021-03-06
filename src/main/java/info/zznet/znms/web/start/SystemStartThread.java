package info.zznet.znms.web.start;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.dao.HostMapper;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.job.ApInfomationJob;
import info.zznet.znms.base.job.ScreenDataRefreshJob;
import info.zznet.znms.base.rrd.core.RrdCore;
import info.zznet.znms.base.util.SpringContextUtil;
import info.zznet.znms.spider.Engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.zznet.znms.web.module.security.service.AuthResultService;
import info.zznet.znms.web.module.systemLog.collector.CollectorCore;

import org.apache.log4j.Logger;

public class SystemStartThread extends Thread {
	private static Logger logger = Logger.getLogger(SystemStartThread.class);
	
	public void run(){
		try{

			RrdCore rrdCore = (RrdCore) SpringContextUtil.getBean("rrdCore");
			rrdCore.init();
			ZNMSLogger.info("初始化RRD文件");

			CollectorCore collectorCore = (CollectorCore) SpringContextUtil.getBean("collectorCore");
			collectorCore.init();
			ZNMSLogger.info("启动SYSLOG收集器");
			
			//启动阀值触发线程
			ThresholdValueThread valueThread = new ThresholdValueThread();
			valueThread.start();
			ZNMSLogger.info("启动阀值处理器");

			ApInfomationJob apInfoJob = (ApInfomationJob) SpringContextUtil.getBean("ApInfomationJob");
			apInfoJob.init();
			
			AuthResultService authResultService = (AuthResultService) SpringContextUtil.getBean("authResultService");
			authResultService.checkOnStart();
			ZNMSLogger.info("授权校验完成！");
			
			ZNMSLogger.showOnConsole("=====Z-NMS Started=====");
			ZNMSLogger.info("启动Z-NMS成功！");
		}
		catch(Throwable t) {
			ZNMSLogger.error(t);
			ZNMSLogger.warn("Z-NMS系统启动失败,推出系统！");
			System.exit(0);
		}
	}
	
	public void shutDown() {

	}
}
