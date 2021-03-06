/**
 * 
 */
package info.zznet.znms.web.module.common.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dell001
 *
 */
public class SystemOptionEnums {

	public enum SYSTEM_OPTION_KEY {
		
		/**
		 *==========邮件start==========
		 */
		//smtp服务器主机名
		SMTP_SERVER_HOST_NAME("SMTP_SERVER_HOST_NAME"),
		
		//smtp端口
		SMTP_PORT("SMTP_PORT"),
		
		//smtp用户名
		SMTP_USER_NAME("SMTP_USER_NAME"),
		
		//smtp密码
		SMTP_PASSWORD("SMTP_PASSWORD"),
		
		//smtp密码确认
		SMTP_PASSWORD_CONFIRM("SMTP_PASSWORD_CONFIRM"),
		
		/**
		 *==========邮件end==========
		 */
		
		/**
		 *==========报警/阀值start==========
		 */
		//禁用所有阀值  0.否  1.是
		DISABLE_ALL_THRESHOLD_VALUE("DISABLE_ALL_THRESHOLD_VALUE"),
		
		//保留天数
		REMAIN_DAYS("REMAIN_DAYS"),
		
		/**
		 *==========报警/阀值end==========
		 */
		
		/**
		 *==========系统日志start==========
		 */
		//启用状态
		USE_STATUS("USE_STATUS"),
		
		//刷新周期
		FLUSH_CYCLE("FLUSH_CYCLE"),
		
		//日志保留时间 
		LOG_REMAIN_TIME("LOG_REMAIN_TIME"),
		
		//日志保留条数 
		LOG_REMAIN_NUMBER("LOG_REMAIN_NUMBER"),
		/**
		 *==========系统日志end==========
		 */
		
		/**
		 *==========杂项start==========
		 */
		//TFTP服务器
		TFTP_SERVER("TFTP_SERVER"),
		
		//备份路径
		BACKUP_PATH("BACKUP_PATH"),
		
		//邮箱地址
		EMAIL_ADDRESS("EMAIL_ADDRESS"),
		
		//备份天数
		BACKUP_DAYS("BACKUP_DAYS"),
		
		// zos ip
		ZOS_IP("ZOS_IP"),
		// zos port
		ZOS_PORT("ZOS_PORT"),
		// zlog ip
		ZLOG_IP("ZLOG_IP"),
		// zlog port
		ZLOG_PORT("ZLOG_PORT"),
		// kafka服务器
		KAFKA_IP("KAFKA_IP"),
		
		// 版本号
		SYSTEM_VERSION("SYSTEM_VERSION"),
		
		AP_MAX_USER_COUNT("AP_MAX_USER_COUNT"),

		HEAT_MAP_RADIUS("HEAT_MAP_RADIUS"),
		//学校代码
		SCHOOL_CODE("SCHOOL_CODE"),
		
		//微信后台地址
		WECHAT_BACKGROUND_ADDRESS("WECHAT_BACKGROUND_ADDRESS"),
		POINT("POINT"),
		RADIUS("RADIUS"),
		POINT_SIZE("POINT_SIZE");
		
	
		/**
		 *==========杂项end==========
		 */
		;
		
		private String name;

		private SYSTEM_OPTION_KEY(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public static List<String> getKeyList(){
			List<String> nameList = new ArrayList<String>();
			for(SYSTEM_OPTION_KEY item : SYSTEM_OPTION_KEY.values()){
				nameList.add(item.getName());
			}
			return nameList;
		}
	}
}
