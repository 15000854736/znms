package info.zznet.znms.web.module.systemLog.collector;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.dao.SystemLogMapper;
import info.zznet.znms.base.entity.SystemLog;
import info.zznet.znms.base.util.SpringContextUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 系统日志持久化
 * @author dell001
 *
 */
public enum LogPersister {
	
	instance;
	
	private LogPersister(){
		queue = new LinkedBlockingQueue<SystemLog>(100000);
		executorService = Executors.newFixedThreadPool(4);
		logMapper = (SystemLogMapper) SpringContextUtil.getBean("systemLogMapper");
		executorService.submit(new Executor());
	}
	private LinkedBlockingQueue<SystemLog> queue;
	private SystemLogMapper logMapper;
	private ExecutorService executorService;
	
	public void addLog(SystemLog systemLog){
		queue.add(systemLog);
	}
	
	private class Executor implements Runnable {

		@Override
		public void run() {
			while(true){
				SystemLog log = null;
				try {
					log = queue.take();
					logMapper.insert(log);
				} catch (Throwable e){
					ZNMSLogger.error(e);
					continue;
				}
			}
		}
	}
}
