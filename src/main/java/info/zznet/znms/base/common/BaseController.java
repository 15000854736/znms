/**
 * 
 */
package info.zznet.znms.base.common;

import info.zznet.znms.web.module.common.exception.NoPermissionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author dell001
 *
 */
public class BaseController {

	@ExceptionHandler(value = Exception.class)
	 public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		ModelAndView mav = new ModelAndView();
		if(e instanceof NoPermissionException){
			 mav.addObject("exception", e);
			 mav.addObject("url", req.getRequestURL());
			mav.setViewName("error/nopermissions");
			return mav;
		}else{
			mav.setViewName("error/404");
		}
		return mav;
	}
}
