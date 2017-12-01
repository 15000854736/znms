package info.zznet.znms.web.module.common.filter;

import info.zznet.znms.web.module.common.exception.NoPermissionException;
import info.zznet.znms.web.module.common.exception.NoPermissionException;
import info.zznet.znms.web.util.AuthUtil;
import info.zznet.znms.web.util.RequestUtil;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;



/**
 * @see 过滤器
 * @return 
 */
public class AuthFilter implements Filter{
	
	// 不拦截的资源类型
	private String[] ignoreTypes;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		setServletObject(req, resp);
		if(filterExcude(req)){
			try{
				chain.doFilter(req, resp);
			} catch (NoPermissionException e) {
				if(e.getCause() instanceof NoPermissionException){
					req.getRequestDispatcher("/views/error/noPermission.jsp").forward(req, resp);
				}		
			}
		}else{
			if(AuthUtil.isLogin(req)){
				if (req.getMethod().equals("GET") && "login".equals(AuthUtil.getRequestURI(req)) || (req.getMethod().equals("GET") && req.getContextPath().equals(AuthUtil.getRequestURI(req)))) {
					resp.sendRedirect(getBaseIndexPath(req));
				}else{
					try{
						chain.doFilter(req, resp);
					} catch (Exception e) {
						if(e.getCause() instanceof NoPermissionException){
							req.getRequestDispatcher("/views/error/noPermission.jsp").forward(req, resp);
						}
					}
				}
			} else if ((req.getMethod().equals("GET") && req.getContextPath().replace("/", "").equals(AuthUtil.getRequestURI(req))) || ("login".equals(AuthUtil.getRequestURI(req)))) {
				try{
					chain.doFilter(req, resp);
				} catch (NoPermissionException e) {
					if(e.getCause() instanceof NoPermissionException){
						req.getRequestDispatcher("/views/error/noPermission.jsp").forward(req, resp);
					}
				}
			}else{
				resp.sendRedirect(getBasePath(req));
			}
		}
	}
	
	public String getBasePath(HttpServletRequest req){
		return req.getScheme() + "://"+ req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/";
	}
	
	public String getBaseIndexPath(HttpServletRequest req){
		return req.getScheme() + "://"+ req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/"+"index" ;
	}

	private boolean filterExcude(HttpServletRequest req){
		String path = AuthUtil.getRequestURI(req);
		if(ignoreTypes!=null){
			for (int i = 0; i < ignoreTypes.length; i++) {
				if (path.endsWith("." + ignoreTypes[i].trim())) {
					return true;
				}
			}
		}
		if(RequestUtil.fromAjax(req)){
			return true;
		}
		if(path.endsWith("captcha-image")){
			return true;
		}
		if(isAPI(req)){
			return true;
		}
		if(req.getRequestURI().indexOf("druid") >= 0){
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unused")
	private String getRealPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		String ignoreTypes = arg0.getInitParameter("ignoreTypes");
		if(ignoreTypes != null && !ignoreTypes.trim().equals("")){
			this.ignoreTypes = ignoreTypes.split(",");
		}
	}
	
	/*
	 * 保存servlet 作用域对象至线程(request,response)
	 */
	private void setServletObject(HttpServletRequest request,HttpServletResponse response){
		AuthUtil.setRequest(request);
		AuthUtil.setResponse(response);
	}
	
	/**
	 * 是否是API请求
	 * @param request
	 * @return
	 */
	public static boolean isAPI(HttpServletRequest request){
		String uri = StringUtils.substringAfter(request.getRequestURI(), request.getContextPath());
		if(StringUtils.isNotEmpty(uri)&&
				StringUtils.startsWith(uri,"/rest")){
			return true;
		}
		return false;
	}

}
