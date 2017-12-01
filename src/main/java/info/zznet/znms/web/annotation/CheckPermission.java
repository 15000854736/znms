package info.zznet.znms.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限检查
 */
@Target(ElementType.METHOD)   
@Retention(RetentionPolicy.RUNTIME)    
@Documented
public @interface CheckPermission {
	public String value();
}