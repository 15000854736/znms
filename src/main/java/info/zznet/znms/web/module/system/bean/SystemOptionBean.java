/**
 * 
 */
package info.zznet.znms.web.module.system.bean;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.entity.SystemOption;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.module.common.constants.SystemOptionEnums;
import info.zznet.znms.web.util.ConfigUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

/**
 * @author dell001
 *系统选项查询bean 
 */
public class SystemOptionBean implements Serializable{
	
	private static final long serialVersionUID = 3052054323627832866L;
	
	/**********************邮件strat************************/
	//smtp服务器主机名
	private String smtpServerHostName;
	
	//smtp端口
	private int smtpPort;
	
	//smtp用户名
	private String smtpUserName;
	
	//smtp密码
	private String smtpPassword;
	
	//smtp密码确认
	private String smtpPasswordConfirm;
	/**********************邮件end************************/
	
	
	/**********************报警/阀值start************************/
	//禁用所有阀值  0.否  1.是
	private boolean disableAllThresholdValue;
	
	//保留天数
	private int remainDays;
	/**********************报警/阀值end************************/
	
	
	/**********************系统日志start************************/
	//启用状态
	private boolean useStatus;

	//日志保留时间 
	private String logRemainTime;
	
	// 日志保留条数
	private long logRemainNumber;
	/**********************系统日志end************************/
	
	
	/**
	 *==========杂项start==========
	 */
	//TFTP服务器
	private String tftpServer;
	
	//备份路径
	private String backupPath;
	
	//邮箱地址
	private String emailAddress;
	
	//备份天数
	private int backupDays;
	
	// zos ip
	private String zosIp;
	// zos port
	private String zosPort;
	
	private String zlogIp;
	private String zlogPort;
	
	private String kafkaIp;
	
	private String systemVersion;
	
	
	private String point;
	
	private String radius;
	
	private String pointSize;
	
	
	//学校代码
	private String schoolCode;
	
	/**
	 *==========杂项end==========
	 */
	
	/**
	 * 图片
	 */
	private String schoolBg="SCHOOL-BG.png";
	private String homeBg="HOME-BG.png";
	private String topolBg="topo-bg.jpg";
	private String indexBg="INDEX-LOGO.png";
	
	private String schoolBgSize;
	private String homeBgSize;
	private String topolBgSize;
	private String indexBgSize;
	
	private String schoolBgModifyDate;
	private String homeBgModifyDate;
	private String topolBgModifyDate;
	private String indexBgModifyDate;
	
	
	
	public String getSchoolBgSize() {
		return schoolBgSize;
	}

	public void setSchoolBgSize(String schoolBgSize) {
		this.schoolBgSize = schoolBgSize;
	}

	public String getHomeBgSize() {
		return homeBgSize;
	}

	public void setHomeBgSize(String homeBgSize) {
		this.homeBgSize = homeBgSize;
	}

	public String getTopolBgSize() {
		return topolBgSize;
	}

	public void setTopolBgSize(String topolBgSize) {
		this.topolBgSize = topolBgSize;
	}

	public String getIndexBgSize() {
		return indexBgSize;
	}

	public void setIndexBgSize(String indexBgSize) {
		this.indexBgSize = indexBgSize;
	}
	
	public String getSchoolBgModifyDate() {
		return schoolBgModifyDate;
	}

	public void setSchoolBgModifyDate(String schoolBgModifyDate) {
		this.schoolBgModifyDate = schoolBgModifyDate;
	}

	public String getHomeBgModifyDate() {
		return homeBgModifyDate;
	}

	public void setHomeBgModifyDate(String homeBgModifyDate) {
		this.homeBgModifyDate = homeBgModifyDate;
	}

	public String getTopolBgModifyDate() {
		return topolBgModifyDate;
	}

	public void setTopolBgModifyDate(String topolBgModifyDate) {
		this.topolBgModifyDate = topolBgModifyDate;
	}

	public String getIndexBgModifyDate() {
		return indexBgModifyDate;
	}

	public void setIndexBgModifyDate(String indexBgModifyDate) {
		this.indexBgModifyDate = indexBgModifyDate;
	}

	public SystemOptionBean(){};
	
	public SystemOptionBean(List<SystemOption> systemOptionList){
		loadSystemOptionData(systemOptionList);
	};
	
	public String selectBySystemOperatorKey(String systemOperatorKey,List<SystemOption> systemOptionList){
		try {
			for (SystemOption systemOption : systemOptionList) {
				if(systemOption.getSystemOptionKey().equalsIgnoreCase(systemOperatorKey)){
					return systemOption.getSystemOptionValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void loadSystemOptionData(List<SystemOption> systemOptionList){
		String key;
		for(SystemOption systemOption : systemOptionList){
			key = systemOption.getSystemOptionKey();
			
			if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.SMTP_SERVER_HOST_NAME.getName())){
				smtpServerHostName = systemOption.getSystemOptionValue();
			} 
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.SMTP_PORT.getName())){
				smtpPort = StringUtil.strToInteger(systemOption.getSystemOptionValue());
			} 
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.SMTP_USER_NAME.getName())){
				smtpUserName = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.SMTP_PASSWORD.getName())){
				smtpPassword = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.SMTP_PASSWORD_CONFIRM.getName())){
				smtpPasswordConfirm = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.DISABLE_ALL_THRESHOLD_VALUE.getName())){
				if("1".equals(systemOption.getSystemOptionValue())){
					disableAllThresholdValue = true;
				}else{
					disableAllThresholdValue = false;
				}
			}else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.REMAIN_DAYS.getName())){
				remainDays = StringUtil.strToInteger(systemOption.getSystemOptionValue());
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.USE_STATUS.getName())){
				if("1".equals(systemOption.getSystemOptionValue())){
					useStatus = true;
				}else{
					useStatus = false;
				}
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.LOG_REMAIN_TIME.getName())){
				logRemainTime = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.LOG_REMAIN_NUMBER.getName())){
				logRemainNumber = StringUtils.isNumeric(systemOption.getSystemOptionValue())?10000000l:Long.parseLong(systemOption.getSystemOptionValue());
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.TFTP_SERVER.getName())){
				tftpServer = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.BACKUP_PATH.getName())){
				backupPath = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.EMAIL_ADDRESS.getName())){
				emailAddress = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.BACKUP_DAYS.getName())){
				backupDays = StringUtil.strToInteger(systemOption.getSystemOptionValue());
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.ZOS_IP.getName())){
				zosIp = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.ZOS_PORT.getName())){
				zosPort = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.ZLOG_IP.getName())){
				zlogIp = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.ZLOG_PORT.getName())){
				zlogPort = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.KAFKA_IP.getName())){
				kafkaIp = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.SYSTEM_VERSION.getName())){
				systemVersion = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.POINT.getName())){
				point = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.RADIUS.getName())){
				radius = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.POINT_SIZE.getName())){
				pointSize = systemOption.getSystemOptionValue();
			}
			else if(key.equals(SystemOptionEnums.SYSTEM_OPTION_KEY.SCHOOL_CODE.getName())){
				schoolCode = systemOption.getSystemOptionValue();
			}
		}
		getHeatData();
	}
	
	private void getHeatData(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BufferedImage bufferedImage = null;
		try {
			String topoImgPath=this.getClass().getClassLoader().getResource("/").getPath()
			.substring(0,this.getClass().getClassLoader().getResource("/").getPath().indexOf("WEB-INF"))+"resource/images";
			String path[]={ConfigUtil.getString("znms.image.path")+schoolBg,
					ConfigUtil.getString("znms.image.path")+homeBg,
					ConfigUtil.getString("znms.image.path")+indexBg,
					topoImgPath+File.separator+topolBg
					};
			for (int i = 0; i < path.length; i++) {
				File imageFile = new File(path[i]);
				if(!imageFile.exists() || !imageFile.isFile()) {
					return ;
				}
				Date date= new Date(imageFile.lastModified());
				bufferedImage = ImageIO.read(imageFile);
				int width = bufferedImage.getWidth();   
				int height = bufferedImage.getHeight();
				switch (i) {
				case 0:
					schoolBgModifyDate=sdf.format(date);
					schoolBgSize=width+" * "+height;
					break;
				case 1:
					homeBgModifyDate=sdf.format(date);
					homeBgSize=width+" * "+height;
					break;
				case 2:
					indexBgModifyDate=sdf.format(date);
					indexBgSize=width+" * "+height;
					break;
				case 3:
					topolBgModifyDate=sdf.format(date);
					topolBgSize=width+" * "+height;
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			ZNMSLogger.error(e);
		}   
	}
	
	/**
	 * 生成用于插入DB的键值对
	 * @return 系统配置键值对
	 */
	public HashMap<String, String> generateSystemOptionMap(){
		HashMap<String, String> systemOptionMap = new HashMap<String, String>();
		
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.SMTP_SERVER_HOST_NAME.getName(), StringUtil.getString(smtpServerHostName));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.SMTP_PORT.getName(), StringUtil.getString(smtpPort));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.SMTP_USER_NAME.getName(), StringUtil.getString(smtpUserName));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.SMTP_PASSWORD.getName(), StringUtil.getString(smtpPassword));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.SMTP_PASSWORD_CONFIRM.getName(), StringUtil.getString(smtpPasswordConfirm));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.DISABLE_ALL_THRESHOLD_VALUE.getName(), StringUtil.getString(disableAllThresholdValue));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.REMAIN_DAYS.getName(), StringUtil.getString(remainDays));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.USE_STATUS.getName(), StringUtil.getString(useStatus));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.LOG_REMAIN_TIME.getName(), StringUtil.getString(logRemainTime));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.LOG_REMAIN_NUMBER.getName(), StringUtil.getString(logRemainNumber));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.TFTP_SERVER.getName(), StringUtil.getString(tftpServer));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.BACKUP_PATH.getName(), StringUtil.getString(backupPath));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.EMAIL_ADDRESS.getName(), StringUtil.getString(emailAddress));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.BACKUP_DAYS.getName(), StringUtil.getString(backupDays));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.ZOS_IP.getName(), StringUtil.getString(zosIp));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.ZOS_PORT.getName(), StringUtil.getString(zosPort));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.ZLOG_IP.getName(), StringUtil.getString(zlogIp));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.ZLOG_PORT.getName(), StringUtil.getString(zlogPort));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.KAFKA_IP.getName(), StringUtil.getString(kafkaIp));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.SYSTEM_VERSION.getName(), StringUtil.getString(systemVersion));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.POINT.getName(), StringUtil.getString(point));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.RADIUS.getName(), StringUtil.getString(radius));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.POINT_SIZE.getName(), StringUtil.getString(pointSize));
		systemOptionMap.put(
				SystemOptionEnums.SYSTEM_OPTION_KEY.SCHOOL_CODE.getName(), StringUtil.getString(schoolCode));
		return systemOptionMap;
	}

	public String getSmtpServerHostName() {
		return smtpServerHostName;
	}

	public void setSmtpServerHostName(String smtpServerHostName) {
		this.smtpServerHostName = smtpServerHostName;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUserName() {
		return smtpUserName;
	}

	public void setSmtpUserName(String smtpUserName) {
		this.smtpUserName = smtpUserName;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public String getSmtpPasswordConfirm() {
		return smtpPasswordConfirm;
	}

	public void setSmtpPasswordConfirm(String smtpPasswordConfirm) {
		this.smtpPasswordConfirm = smtpPasswordConfirm;
	}

	public boolean isDisableAllThresholdValue() {
		return disableAllThresholdValue;
	}

	public void setDisableAllThresholdValue(boolean disableAllThresholdValue) {
		this.disableAllThresholdValue = disableAllThresholdValue;
	}

	public int getRemainDays() {
		return remainDays;
	}

	public void setRemainDays(int remainDays) {
		this.remainDays = remainDays;
	}

	public boolean isUseStatus() {
		return useStatus;
	}

	public void setUseStatus(boolean useStatus) {
		this.useStatus = useStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLogRemainTime() {
		return logRemainTime;
	}

	public void setLogRemainTime(String logRemainTime) {
		this.logRemainTime = logRemainTime;
	}

	public long getLogRemainNumber() {
		return logRemainNumber;
	}

	public void setLogRemainNumber(long logRemainNumber) {
		this.logRemainNumber = logRemainNumber;
	}

	public String getTftpServer() {
		return tftpServer;
	}

	public void setTftpServer(String tftpServer) {
		this.tftpServer = tftpServer;
	}

	public String getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getBackupDays() {
		return backupDays;
	}

	public void setBackupDays(int backupDays) {
		this.backupDays = backupDays;
	}

	public String getZosIp() {
		return zosIp;
	}

	public void setZosIp(String zosIp) {
		this.zosIp = zosIp;
	}

	public String getZosPort() {
		return zosPort;
	}

	public void setZosPort(String zosPort) {
		this.zosPort = zosPort;
	}
	
	public String getZlogIp() {
		return zlogIp;
	}

	public void setZlogIp(String zlogIp) {
		this.zlogIp = zlogIp;
	}

	public String getZlogPort() {
		return zlogPort;
	}

	public void setZlogPort(String zlogPort) {
		this.zlogPort = zlogPort;
	}

	public String getSchoolBg() {
		return schoolBg;
	}

	public void setSchoolBg(String schoolBg) {
		this.schoolBg = schoolBg;
	}

	public String getHomeBg() {
		return homeBg;
	}

	public void setHomeBg(String homeBg) {
		this.homeBg = homeBg;
	}

	public String getTopolBg() {
		return topolBg;
	}

	public void setTopolBg(String topolBg) {
		this.topolBg = topolBg;
	}

	public String getIndexBg() {
		return indexBg;
	}

	public void setIndexBg(String indexBg) {
		this.indexBg = indexBg;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public String getPointSize() {
		return pointSize;
	}

	public void setPointSize(String pointSize) {
		this.pointSize = pointSize;
	}
	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getKafkaIp() {
		return kafkaIp;
	}

	public void setKafkaIp(String kafkaIp) {
		this.kafkaIp = kafkaIp;
	}

}
