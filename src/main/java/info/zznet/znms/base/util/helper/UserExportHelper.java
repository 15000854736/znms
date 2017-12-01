
package info.zznet.znms.base.util.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.entity.SystemLog;
import info.zznet.znms.web.module.common.constants.SystemLogFacility;
import info.zznet.znms.web.module.common.constants.SystemLogPriority;
import info.zznet.znms.web.module.system.bean.SmAdminBean;
import info.zznet.znms.web.util.MessageUtil;


/**
 * 导出辅助类
 * 
 * @author dell001
 *
 */
public class UserExportHelper {
	
	/**
	 * 导出int类型转换 或其他数值转换帮助
	 * 作用：解决状态
	 * @param clazz
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public synchronized static String getConvertValue(Object clazz,String fieldName,Object fieldValue){
		String value = null;
		if(clazz instanceof SmAdminBean){
			value = getStatusNameValue(fieldName, fieldValue);
		}
		if(clazz instanceof SystemLog){
			value = getSystemLogNameValue(fieldName, fieldValue);
		}
		return value;
	}
	
	/**
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	private static String getSystemLogNameValue(String fieldName,
			Object fieldValue) {
		if(fieldName.equals("priorityId")){
			if(fieldValue==null) return "未知";
			Integer priorityId = (Integer)fieldValue;
			return SystemLogPriority.findValueByKey(priorityId);
		}else if(fieldName.equals("facilityId")){
			if(fieldValue==null) return "未知";
			Integer facilityId = (Integer)fieldValue;
			return SystemLogFacility.findValueByKey(facilityId);
		}else if(fieldName.equals("logTime")){
			if(fieldValue==null) return ""; 
			Date time = (Date)fieldValue;
			SimpleDateFormat formatter=new SimpleDateFormat(SystemConstants.COMMON_DATE_FORMAT);
			return formatter.format(time);
		}
		return null;
	}

	/**
	 * 角色名称转换
	 */
	private static String getStatusNameValue(String fieldName, Object fieldValue) {
		
		final String notActivated = MessageUtil.getMessage("smAdmin.option.status.notActivated");
		final String activated = MessageUtil.getMessage("smAdmin.option.status.activated");
		final String disabled = MessageUtil.getMessage("smAdmin.option.status.disabled");
		if(fieldName.equals("status")){
			if(fieldValue==null) return "未知"; 
			Integer status = (Integer)fieldValue;
			Integer value = status.intValue();
			String returndesc;
			switch(value){
			case 0:
				returndesc = notActivated;break;
			case 1:
				returndesc = activated;break;
			case 2:
				returndesc = disabled;break;
			default:
				returndesc = "";
			}
			return returndesc;
		}
		return fieldValue == null ? "" : fieldValue.toString();
	}
}
