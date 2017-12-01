package info.zznet.znms.web.module.common.page.interceptor;

import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.web.module.security.bean.SessionBean;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *  自动注入更新时间和创建时间
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}) })
public class MyBatisInterceptor implements Interceptor {
	
	private HttpServletRequest request;
	
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
    	ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	
    	
    	MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        Field[] fields = parameter.getClass().getDeclaredFields();
        if(SqlCommandType.UPDATE==sqlCommandType) {
            for (Field field : fields) {
                if (field.getName().equals("updateAdmin") && servletRequestAttributes!=null) {
                	request = servletRequestAttributes.getRequest();
                	SessionBean sessionBean = (SessionBean) request.getSession().getAttribute(SystemConstants.SESSION_BEAN_KEY);
                    field.setAccessible(true);
                    field.set(parameter,sessionBean.getSmAdmin().getAdminId());
                    field.setAccessible(false);
                }
//                if (field.getName().equals("updateTime")) {
//                    field.setAccessible(true);
//                    field.set(parameter,new Timestamp(System.currentTimeMillis()));
//                    field.setAccessible(false);
//                }
            }
        } else if(SqlCommandType.INSERT==sqlCommandType){
            for (Field field : fields) {
                if (field.getName().equals("createAdmin") && servletRequestAttributes!=null) {
                	request = servletRequestAttributes.getRequest();
                	SessionBean sessionBean = (SessionBean) request.getSession().getAttribute(SystemConstants.SESSION_BEAN_KEY);
                	request = servletRequestAttributes.getRequest();
                    field.setAccessible(true);
                    field.set(parameter,sessionBean.getSmAdmin().getAdminId());
                    field.setAccessible(false);
                }
                if (field.getName().equals("updateAdmin") && servletRequestAttributes!=null) {
                	request = servletRequestAttributes.getRequest();
                	SessionBean sessionBean = (SessionBean) request.getSession().getAttribute(SystemConstants.SESSION_BEAN_KEY);
                    field.setAccessible(true);
                    field.set(parameter,sessionBean.getSmAdmin().getAdminId());
                    field.setAccessible(false);
                }
//                if (field.getName().equals("createTime")) {
//                    field.setAccessible(true);
//                    field.set(parameter,new Timestamp(System.currentTimeMillis()));
//                    field.setAccessible(false);
//                }
//                if (field.getName().equals("updateTime")) {
//                    field.setAccessible(true);
//                    field.set(parameter,new Timestamp(System.currentTimeMillis()));
//                    field.setAccessible(false);
//                }
            }
        }
        return invocation.proceed();
    }
 
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }
 
    @Override
    public void setProperties(Properties properties) {
 
    }
}