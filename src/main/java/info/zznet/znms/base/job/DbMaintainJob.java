package info.zznet.znms.base.job;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.dao.OnlineUserAnalysisMapper;
import info.zznet.znms.base.dao.SystemLogMapper;
import info.zznet.znms.base.dao.ThresholdValueTriggerLogMapper;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.web.WebRuntimeData;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * db维护任务
 * @author dell001
 *
 */
@Service
@DisallowConcurrentExecution
public class DbMaintainJob {
	@Autowired
	private SystemLogMapper syslogMapper;
	@Autowired
	private ThresholdValueTriggerLogMapper tvtlogMapper;
	@Autowired
	private OnlineUserAnalysisMapper onlineUserMapper;
	
	@Scheduled(cron = "0 0 1 * * ?")
//	@Scheduled(fixedDelay=1000)
	public void deleteSystemLogWhenOverSize(){
		try{
			long count = syslogMapper.getCount(null);
			if(count > WebRuntimeData.instance.getSystemOptionBean().getLogRemainNumber()) {
				long deleteNumber = count - WebRuntimeData.instance.getSystemOptionBean().getLogRemainNumber();
				ZNMSLogger.info("delete "+syslogMapper.deleteOldLog(deleteNumber)+" old syslog for over size");
			}
		} catch(Throwable e) {
			ZNMSLogger.error(e);
		}
	}
	
	@Scheduled(cron = "0 0 2 * * ?")
//	@Scheduled(fixedDelay=1000)
	public void deleteSystemLogWhenExpired(){
		try {
			String expiredTimeStr = WebRuntimeData.instance.getSystemOptionBean().getLogRemainTime();
			long expiredTime = 60 * 60 * 24 * 7l;
			if(StringUtils.containsIgnoreCase(expiredTimeStr, "周")) {
				expiredTime = Long.parseLong(StringUtils.remove(expiredTimeStr, "周"));
				expiredTime *= 60 * 60 * 24 * 7l;
			} else if(StringUtils.containsIgnoreCase(expiredTimeStr, "天")) {
				expiredTime = Long.parseLong(StringUtils.remove(expiredTimeStr, "天"));
				expiredTime *= 60 * 60 * 24l;
			} else if(StringUtils.containsIgnoreCase(expiredTimeStr, "月")){
				expiredTime = Long.parseLong(StringUtils.remove(expiredTimeStr, "月"));
				expiredTime *= 60 * 60 * 24 * 31l;
			} else if(StringUtils.containsIgnoreCase(expiredTimeStr, "年")){
				expiredTime = Long.parseLong(StringUtils.remove(expiredTimeStr, "年"));
				expiredTime *= 60 * 60 * 24 * 366l;
			} else {
				expiredTime *= 60 * 60 * 24l;
			}
			Date today = DateUtil.getDayStart(new Date());
			Date expireDate = new Date(today.getTime() - expiredTime * 1000);
			long deleted = syslogMapper.deleteExpiredLog(expireDate);
			ZNMSLogger.info("delete "+deleted+" expired syslog before " +new SimpleDateFormat("yyyyMMdd").format(expireDate));
		} catch(Throwable e) {
			ZNMSLogger.error(e);
		}
	}
	
	@Scheduled(cron = "0 0 2 * * ?")
//	@Scheduled(fixedDelay=1000)
	public void deleteThresholdLogWhenExpired(){
		try {
			long expireTime = WebRuntimeData.instance.getSystemOptionBean().getRemainDays() * 60 * 60 * 24 * 1000l;
			Date today = DateUtil.getDayStart(new Date());
			Date expireDate = new Date(today.getTime() - Math.abs(expireTime));
			long deleted = tvtlogMapper.deleteExpiredLog(expireDate);
			ZNMSLogger.info("delete "+deleted+" expired tvtlog before " +new SimpleDateFormat("yyyyMMdd").format(expireDate));
		} catch(Throwable e) {
			ZNMSLogger.error(e);
		}
	}
	
	@Scheduled(cron = "0 0 4 * * ?")
//	@Scheduled(fixedDelay=1000)
	public void doPartition(){
		try {
			syslogMapper.doPartitionByMonth("SYSTEM_LOG");
			ZNMSLogger.info("system log partition maked");
		} catch (Throwable e) {
			ZNMSLogger.error(e);
		}
	}
	
	@Scheduled(cron = "0 0 3 * * ?")
//	@Scheduled(fixedDelay=1000)
	public void deleteExpiredOnlineUserRecord(){
		try {
			onlineUserMapper.deleteExpiredData(null);
			ZNMSLogger.info("delete expired online user analysis record successed");
		} catch(Throwable e) {
			ZNMSLogger.error(e);
		}
	}
}
