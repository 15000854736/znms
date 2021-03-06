package info.zznet.znms.web.module.systemLog.collector;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.entity.SystemLog;
import info.zznet.znms.base.entity.SystemLogDeleteRule;
import info.zznet.znms.web.WebRuntimeData;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("logRuler")
public class LogRuler {
	
	static WebRuntimeData webRuntimeData = WebRuntimeData.instance;
	
	/**
	 * 过滤
	 * @param log
	 * @return
	 */
	public static boolean doFilter(SystemLog log){		
		if(log == null || log.getHost()==null){
			return false;
		}
		String message = log.getMessage();
		String hostName = log.getHost().getHostName();
		String priorityId = log.getPriorityName();
		String type = log.getFacilityName();
		int typeCode = log.getFacilityId();
		if(StringUtils.isEmpty(message)){
			return false;
		}
		
		List<SystemLogDeleteRule> ruleList = webRuntimeData.getRules();
		
		for(SystemLogDeleteRule rule : ruleList){
			if(rule.getRuleStatus().compareTo(0)==0){
				continue;
			}
			switch(rule.getMatchType()) {
				// 以什么开始
				case 1:
					if(StringUtils.startsWithIgnoreCase(message, rule.getMatchString())){
						ZNMSLogger.debug(log+" will be discard because of " + rule);
						return false;
					}
					break;
				// 包含
				case 2:
					if(StringUtils.containsIgnoreCase(message, rule.getMatchString())){
						ZNMSLogger.debug(log+" will be discard because of " + rule);
						return false;
					}
					break;
				// 以什么结束
				case 3:
					if(StringUtils.endsWithIgnoreCase(message, rule.getMatchString())){
						ZNMSLogger.debug(log+" will be discard because of " + rule);
						return false;
					}
					break;
				// 主机名是
				case 4:
					if(StringUtils.equalsIgnoreCase(hostName, rule.getMatchString())) {
						ZNMSLogger.debug(log+" will be discard because of " + rule);
						return false;
					}
					break;
				// 功能是
				case 5:
					// TODO
//					ZNMSLogger.debug(log+" will be discard because of " + rule);
					break;
				// 日志类型
				case 7:
					if(StringUtils.isNumeric(rule.getMatchString()) && Integer.parseInt(rule.getMatchString()) == typeCode){
						ZNMSLogger.debug(log+" will be discard because of " + rule);
						return false;
					}
					if(StringUtils.equalsIgnoreCase(type, rule.getMatchString())){
						ZNMSLogger.debug(log+" will be discard because of " + rule);
						return false;
					}
					break;
				// 日志级别
				case 8:
					if(StringUtils.equalsIgnoreCase(priorityId, rule.getMatchString())){
						ZNMSLogger.debug(log+" will be discard because of " + rule);
						return false;
					}
				default:
					break;
			}
		}
		return true;
	}	
}
