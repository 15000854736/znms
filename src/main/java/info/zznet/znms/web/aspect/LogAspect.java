package info.zznet.znms.web.aspect;

import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.entity.SmAdmin;
import info.zznet.znms.web.annotation.DoLog;
import info.zznet.znms.web.module.security.bean.SessionBean;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 操作Log记录用切面
 */
@Component("logAspect")
@Aspect
public class LogAspect {

	Logger log = Logger.getLogger(LogAspect.class);
	
	public LogAspect() {
	}

	@Pointcut("@annotation(info.zznet.znms.web.annotation.DoLog)")
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint point) {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SmAdmin admin = ((SessionBean) request.getSession().getAttribute(
				SystemConstants.SESSION_BEAN_KEY)).getSmAdmin();
		String adminName = admin == null ? "未知用户": admin.getAdminId();
		String describe = "";
		Object[] logParam = null;
		try {
			logParam = getActionName(point);
			describe = "执行[" + logParam[0] + "]操作。";
		} catch (Exception e) {
			ZNMSLogger.error("获取操作名称失败", e);
		}
		Object retVal = null;
		try {
			retVal = point.proceed();
			ZNMSLogger.info(adminName+describe);
		} catch (Throwable e) {
			ZNMSLogger.error("[" + describe + "]的日志记录操作失败", e);
		}
		return retVal;
	}

	// 获取日志类型和操作名称
	@SuppressWarnings("rawtypes")
	private Object[] getActionName(ProceedingJoinPoint joinPoint)
			throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();

		Class<?> targetClass = Class.forName(targetName);
		Method[] method = targetClass.getMethods();
		Object[] result = new Object[2];
		for (Method m : method) {
			if (m.getName().equals(methodName)) {
				Class[] tmpCs = m.getParameterTypes();
				if (tmpCs.length == arguments.length) {
					DoLog methodCache = m.getAnnotation(DoLog.class);
					if (methodCache != null) {
						result[0] = methodCache.value();
					}
					break;
				}
			}
		}
		return result;
	}
}
