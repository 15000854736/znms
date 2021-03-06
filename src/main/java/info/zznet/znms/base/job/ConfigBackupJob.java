package info.zznet.znms.base.job;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.dao.BackupConfigurationAccountPasswordMapper;
import info.zznet.znms.base.dao.BackupConfigurationDeviceMapper;
import info.zznet.znms.base.dao.ConfigBackupRecordMapper;
import info.zznet.znms.base.entity.BackupConfigurationAccountPassword;
import info.zznet.znms.base.entity.BackupConfigurationDevice;
import info.zznet.znms.base.entity.ConfigBackupRecord;
import info.zznet.znms.base.util.MailUtil;
import info.zznet.znms.base.util.TelnetUtil;
import info.zznet.znms.base.util.TelnetUtil.TelnetResult;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;
import info.zznet.znms.web.util.ConfigUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 设备配置备份任务
 */
@Service
@DisallowConcurrentExecution
public class ConfigBackupJob {
	
	@Autowired
	private BackupConfigurationDeviceMapper backupConfDeviceMapper;
	
	@Autowired
	private BackupConfigurationAccountPasswordMapper backupConfigurationAccountPasswordMapper;
	
	@Autowired
	private ConfigBackupRecordMapper configBackupRecordMapper;
	
	private WebRuntimeData webRuntimeData = WebRuntimeData.instance;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
	
	SystemOptionBean systemOptionBean;
	
	long week = 1000*60*60*24*7;
	long month = 1000*60*60*24*31;
			
	@Scheduled(cron = "0 0 0 * * ?")
//	@Scheduled(fixedDelay = 60000)
	public void doBackup(){
		systemOptionBean = webRuntimeData.instance.getSystemOptionBean();
		Pager pager = new Pager();
		pager.setLimit(Integer.MAX_VALUE);
		pager.setOffset(0);
		List<BackupConfigurationDevice> list = backupConfDeviceMapper.findPageList(pager, null);
		List<String> badHostList = new ArrayList<String>();
		if(list != null){
			boolean result = true;
			for(BackupConfigurationDevice deviceConf : list){
				if(deviceConf.isUseStatus()){
					ConfigBackupRecord latestRecord = configBackupRecordMapper.findLatestSuccess(deviceConf.getHostUuid());
					// 每天备份
					if(deviceConf.getBackupCycle().compareTo(1)==0){
						boolean _result = doBackup(deviceConf);
						if(!_result && deviceConf.getHost() != null) {
							badHostList.add(deviceConf.getHost().getHostName());
						}
						result = result && _result;						
					} 
					// 每周备份
					else if(deviceConf.getBackupCycle().compareTo(2)==0){
						if(latestRecord == null || (System.currentTimeMillis() - latestRecord.getBackupTime().getTime()>=week)){
							boolean _result = doBackup(deviceConf);
							if(!_result && deviceConf.getHost() != null) {
								badHostList.add(deviceConf.getHost().getHostName());
							}
						}
					}
					// 每月备份
					else if(deviceConf.getBackupCycle().compareTo(3)==0){
						if(latestRecord == null || (System.currentTimeMillis() - latestRecord.getBackupTime().getTime()>=month)){
							boolean _result = doBackup(deviceConf);
							if(!_result && deviceConf.getHost() != null) {
								badHostList.add(deviceConf.getHost().getHostName());
							}
						}
					}
				}
			}
			MailUtil.sendMail("设备配置备份失败","存在未完成配置备份的设备：" + StringUtils.join(badHostList, ","));
		}
	}
	
	private boolean doBackup(BackupConfigurationDevice deviceConf){
		BackupConfigurationAccountPassword account = backupConfigurationAccountPasswordMapper.selectByPrimaryKey(deviceConf.getAccountPasswordUuid());
		if(account == null){
			ZNMSLogger.error("由于未找到对应的账号密码，无法对["+deviceConf.getHost().getHostIp()+"]进行备份");
			return false;
		}
		
		String backupPath = systemOptionBean.getBackupPath() + File.separator + deviceConf.getContent();
		File backupDir = new File(backupPath);
		if(!backupDir.exists() || !backupDir.isDirectory()){
			backupDir.mkdirs();
		}
		
		ConfigBackupRecord record = new ConfigBackupRecord();
		record.setId(UUIDGenerator.getGUID());
		record.setActivator("system");
		record.setBackupTime(new Date());
		record.setHostUuid(deviceConf.getHostUuid());
		
		TelnetUtil telnet = new TelnetUtil();
		String msg = "";
		boolean retVal = false;
		String tmpFileName = deviceConf.getHost().getHostIp()+"_"+System.currentTimeMillis()+".txt";
		
		TelnetResult result = null;
		
		// 认证
		try {
			result = telnet.login(deviceConf.getHost().getHostIp(), 23, account.getCertificateName(), account.getPassword());
			msg += result.getMsg();
			if(result.getResult()){
				// 启用
				result = telnet.enable(account.getEnablePassword());
				msg += result.getMsg();
				if(result.getResult()){
					// 开始备份
					result = telnet.startCopy(ConfigUtil.getString("znms.ip"), tmpFileName);
					msg += result.getMsg();
					retVal = result.getResult();
				}
			}
		} catch (Exception e) {
			ZNMSLogger.error("无法对["+deviceConf.getHost().getHostIp()+"]进行备份, 原因:", e);
		}
		record.setDebugInfo(msg);
		
		File tmpFile = new File(SystemConstants.TFTP_PATH + File.separator+ tmpFileName);
		if(!tmpFile.exists() || !tmpFile.isFile()){
			retVal = false;
			ZNMSLogger.error("无法对["+deviceConf.getHost().getHostIp()+"]进行备份, 原因："+tmpFile.getAbsolutePath()+"不存在");
		} else {
			String newName = deviceConf.getHost().getHostName()+"-"+sdf.format(new Date())+".txt";
			// 移动至指定存放点并重命名
			tmpFile.renameTo(new File(backupPath+File.separator+newName));
			record.setFileName(newName);			
		}
		record.setSuccess(retVal);
		configBackupRecordMapper.insert(record);
		
		return retVal;
	}
}
