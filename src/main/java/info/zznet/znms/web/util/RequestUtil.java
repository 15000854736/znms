package info.zznet.znms.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * request操作工具类
 * @author yuanjingtao
 *
 */
public class RequestUtil {
	
	/**
	 * 判断此次请求是否通过ajax发送
	 * @see
	 * @param req
	 * @return
	 */
	public static boolean  fromAjax(HttpServletRequest  req){
		String requestType = req.getHeader("X-Requested-With"); 
		return requestType != null;
		
	}
}
