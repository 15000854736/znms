package info.zznet.znms.web.aspect;

import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.common.exception.NoPermissionException;
import info.zznet.znms.web.module.security.bean.SessionBean;
import info.zznet.znms.web.util.MessageUtil;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 权限检查用切面
 */
@Component("permissionAspect")
@Aspect
public class PermissionAspect {
	Logger log = Logger.getLogger(PermissionAspect.class);

	public PermissionAspect() {
	}

	@Pointcut("@annotation(info.zznet.znms.web.annotation.CheckPermission)")
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {

		MethodSignature joinPointObject = (MethodSignature) point.getSignature();
		Class<?>[] parameterTypes = joinPointObject.getMethod()
				.getParameterTypes();
		Method method = point.getTarget().getClass()
				.getMethod(point.getSignature().getName(), parameterTypes);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		if (method.isAnnotationPresent(CheckPermission.class)) {
			SessionBean sessionBean = (SessionBean) request.getSession()
					.getAttribute(SystemConstants.SESSION_BEAN_KEY);
			if (null != sessionBean && null != sessionBean.getSmAdmin()) {
				CheckPermission funcnum = method
						.getAnnotation(CheckPermission.class);
				if (sessionBean.hasPermission(funcnum.value())) {
					return point.proceed();
				}
			}
		}
		throw new NoPermissionException(MessageUtil.getMessage("common.system.permission.error.forbidden"));	}
}
