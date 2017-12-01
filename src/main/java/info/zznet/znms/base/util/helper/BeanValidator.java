package info.zznet.znms.base.util.helper;

import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.annotation.ValidPassword;
import info.zznet.znms.base.annotation.ValidPhone;
import info.zznet.znms.base.annotation.ValidUserName;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.common.ValidResult;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.annotation.PropKey;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * bean校验工具
 * @author Chenwd
 * @date 2016年1月14日
 */
public class BeanValidator {
	/**
	 * 校验所有注解项
	 * @param obj
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static ValidResult valid(Object obj){		
		boolean result = true;
		List<String> messages = new ArrayList<String>();
		
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				ZNMSLogger.error("Failed to access field", e);
			}
			PropKey fieldNameP = field.getAnnotation(PropKey.class);
			String fieldName = "";
			if(fieldNameP == null){
				continue;
			} else {
				fieldName = fieldNameP.value();
			}
			if(field.getAnnotation(Email.class) != null){
				if(!StringUtil.isNullString(StringUtil.getString(value))){
					if(!StringUtil.isEmail(value.toString())){
						result = false;
						messages.add(fieldName+"格式错误");
					}
				}
			}
			if(field.getAnnotation(ValidPhone.class) != null){
				if(!StringUtil.isNullString(StringUtil.getString(value))){
					if(!StringUtil.isPhoneNumber(value.toString())){
						result = false;
						messages.add(fieldName+"格式错误");
					}
				}
			}
			if(field.getAnnotation(ValidUserName.class) != null){
				if(!StringUtil.isNullString(StringUtil.getString(value))){
					if(!StringUtil.checkUserName(value.toString())){
						result = false;
						messages.add(fieldName+"格式错误");
					}
				}
			}
			if(field.getAnnotation(ValidPassword.class) != null){
				if(!StringUtil.isNullString(StringUtil.getString(value))){
					if(!StringUtil.checkPassword(value.toString())){
						result = false;
						messages.add(fieldName+"格式错误");
					}
				}
			}
			if(field.getAnnotation(Size.class) != null){
				int maxLength = field.getAnnotation(Size.class).max();
				int minLength = field.getAnnotation(Size.class).min();
				if(!StringUtil.isNullString(StringUtil.getString(value))){
					if(StringUtil.getString(value).length() > maxLength){
						result = false;
						messages.add(fieldName+"长度不能大于"+maxLength+"位");
					}
					if(StringUtil.getString(value).length() < minLength){
						result = false;
						messages.add(fieldName+"长度不能小于"+minLength+"位");
					}
				}
			}
			if(field.getAnnotation(NotNull.class) != null){
				if(value == null){
					result = false;
					messages.add(fieldName+"不能为空");
				}
			}
			if(field.getAnnotation(NotEmpty.class) != null){
				if(value == null || StringUtil.isNullString(value+"")){
					result = false;
					messages.add(fieldName+"不能为空或空白字符");
				}
			}
		}
		return new ValidResult(result, messages);
	}
	
	/**
	 * 按属性名校验
	 * @param propertyName
	 * @return
	 */
	public static ValidResult valid(Object obj, String... propertyName){
		boolean result = true;
		List<String> messages = new ArrayList<String>();
		
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			if(!field.getName().equals(propertyName)){
				continue;
			}
			Object value = null;
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				ZNMSLogger.error("Failed to access field", e);
			}
			PropKey fieldNameP = field.getAnnotation(PropKey.class);
			String fieldName = "";
			if(fieldNameP == null){
				continue;
			} else {
				fieldName = fieldNameP.value();
			}
			if(field.getAnnotation(Email.class) != null){
				if(!StringUtil.isNullString(StringUtil.getString(value))){
					if(!StringUtil.isEmail(value.toString())){
						result = false;
						messages.add(fieldName+"格式错误");
					}
				}
			}
			if(field.getAnnotation(ValidPhone.class) != null){
				if(!StringUtil.isNullString(StringUtil.getString(value))){
					if(!StringUtil.isPhoneNumber(value.toString())){
						result = false;
						messages.add(fieldName+"格式错误");
					}
				}
			}
			if(field.getAnnotation(ValidUserName.class) != null){
				if(!StringUtil.isNullString(StringUtil.getString(value))){
					if(!StringUtil.checkUserName(value.toString())){
						result = false;
						messages.add(fieldName+"格式错误");
					}
				}
			}
			if(field.getAnnotation(ValidPassword.class) != null){
				if(!StringUtil.isNullString(StringUtil.getString(value))){
					if(!StringUtil.checkPassword(value.toString())){
						result = false;
						messages.add(fieldName+"格式错误");
					}
				}
			}
			if(field.getAnnotation(Size.class) != null){
				int maxLength = field.getAnnotation(Size.class).max();
				int minLength = field.getAnnotation(Size.class).min();
				if(!StringUtil.isNullString(StringUtil.getString(value))){
					if(StringUtil.getString(value).length() > maxLength){
						result = false;
						messages.add(fieldName+"长度不能大于"+maxLength+"位");
					}
					if(StringUtil.getString(value).length() < minLength){
						result = false;
						messages.add(fieldName+"长度不能小于"+minLength+"位");
					}
				}
			}
			if(field.getAnnotation(NotNull.class) != null){
				if(value == null){
					result = false;
					messages.add(fieldName+"不能为空");
				}
			}
			if(field.getAnnotation(NotEmpty.class) != null){
				if(value == null || StringUtil.isNullString(value+"")){
					result = false;
					messages.add(fieldName+"不能为空或空白字符");
				}
			}
		}
		return new ValidResult(result, messages);
	}
	
	/**
	 * 拼接错误信息（Ice）
	 * @param messages
	 * @return
	 */
	public static String appendMessagesForIce(List<String> messages){
		StringBuffer sb = new StringBuffer();
		for(String message:messages){
			sb.append(message).append("&&");
		}
		return sb.substring(0, sb.length()-2);
	}
}
