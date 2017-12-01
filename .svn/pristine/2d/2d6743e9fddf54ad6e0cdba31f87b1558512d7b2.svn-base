package info.zznet.znms.web.util;

import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.web.module.security.bean.SessionBean;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.web.module.security.bean.SessionBean;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
/**
 * @author yuanjingtao
 * @see 工具类（request,response）
 * @return 
 */
public class AuthUtil {

	private static ThreadLocal<HttpServletRequest> request_local= new ThreadLocal<HttpServletRequest>();  
    private static ThreadLocal<HttpServletResponse> response_local= new ThreadLocal<HttpServletResponse>(); 

    public static void setRequest(HttpServletRequest request){
    	request_local.set(request);
    }
    
    public static void setResponse(HttpServletResponse response){
    	response_local.set(response);
    }
    /**
     * @return 当前线程中的request对象
     */
    public static HttpServletRequest getRequest(){
    	return request_local.get();
    }
    /**
     * @return 当前线程中的response对象
     */
    public static HttpServletResponse getResponse(){
    	return response_local.get();
    }
    /**
     * @return 当前线程中的request对象
     */
    /**
     * @return 当前线程中的session对象
     */
    public static HttpSession getSession(){
    	HttpServletRequest req = request_local.get();
    	return req == null ? null : req.getSession();
    }
    
    /**
     * 通过当前线程中的session对象,获取当前登录用户
     * @return
     */
    public static SessionBean getLoginUser(){
    	HttpSession session = getSession();
    	return (SessionBean) (session == null ? null : session.getAttribute(SystemConstants.SESSION_BEAN_KEY));
    }
    
    /**
     * 通过request获取requesturi
     * @param req
     * @return
     */
    public static String getRequestURI(HttpServletRequest req){
    	String [] paths = req.getRequestURI().split("\\/");
		String path = paths[paths.length - 1];
		return path;
    }

    /**
     * 判断当前用户是否登陆
     * @param req
     * @return
     */
	public static boolean isLogin(HttpServletRequest req){
		HttpSession session = req.getSession();
		if(session != null && AuthUtil.getLoginUser() != null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 系统注销清除session
	 * @param request
	 */
	public static void clearSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Enumeration<?> names = session.getAttributeNames();

		while (names.hasMoreElements()) {
			String attrName = (String) names.nextElement();
			session.removeAttribute(attrName);
		}
	}
	
	/**
	 * 是否登录地址请求
	 * @param request
	 * @return
	 */
	public static boolean isLoginRequest(HttpServletRequest request){
		String uri = StringUtils.substringAfter(request.getRequestURI(), request.getContextPath());
		if (StringUtils.isNotEmpty(uri)&&
				(StringUtils.startsWith(uri,"/admin-login/")||
						StringUtils.equals(uri,"/admin-login")||
						StringUtils.equals(uri,"/login")
				)){
			return true;
		}
		return false;
	}
	
	/**
	 * 想request中添加消息
	 * @param type 消息类型
	 * @param messageCode 消息码
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public static void setMessage(String type, String messageCode,
			HttpServletRequest request) {
		Map<String, String> messageMap = (Map<String, String>) request
				.getAttribute(SystemConstants.KEY_REQUEST_MESSAGE);
		if (messageMap == null) {
			messageMap = new HashMap<String, String>();
		}
		messageMap.put(type, messageCode);
		request.setAttribute(SystemConstants.KEY_REQUEST_MESSAGE, messageMap);
	}
	
	/**
     * 
     * @param req
     * @return
     */
    public static String getRequestURIWithJsp(HttpServletRequest req){
    	String [] paths = req.getRequestURI().split("\\/");
		String path = paths[paths.length - 2];
		return path;
    }
}
