/**
 * 
 */
package info.zznet.znms.base.job;

import info.zznet.znms.base.dao.SystemLogStatisticsMapper;
import info.zznet.znms.base.entity.SystemLogStatistics;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.web.module.systemLog.service.SystemLogService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 日志统计任务  每天 23:59进行操作
 * @author dell001
 *
 */
@Service
@DisallowConcurrentExecution
public class SystemLogStatisticsJob {
	
	@Autowired
	private SystemLogService logService;
	
	@Autowired
	private SystemLogStatisticsMapper systemLogStatisticsMapper;

	@Scheduled(cron = "0 0 0 * * ?")
	public void triggerSystemLogStatistics(){
		Date date = new Date();
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long startTime = DateUtil.getDayStart(new Date()).getTime();
		long endTime = DateUtil.getDayEnd(date).getTime();
		String startDate = sdf.format(startTime);
		String endDate = sdf.format(endTime);
		//按主机id、日志级别、日志类型分组查询
		List<SystemLogStatistics> list = logService.findLogStatisticsList(startDate, endDate);
		for (SystemLogStatistics systemLogStatistics : list) {
			systemLogStatistics.setInsertTime(date);
			systemLogStatisticsMapper.insert(systemLogStatistics);
		}
	}
}
