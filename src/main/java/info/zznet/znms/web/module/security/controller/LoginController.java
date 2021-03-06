package info.zznet.znms.web.module.security.controller;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.entity.SmAdmin;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.base.util.EncryptionMD5;
import info.zznet.znms.base.util.IpUtil;
import info.zznet.znms.web.module.security.bean.LoginBean;
import info.zznet.znms.web.module.security.bean.SessionBean;
import info.zznet.znms.web.module.security.service.LoginService;
import info.zznet.znms.web.module.security.service.PermissionService;
import info.zznet.znms.web.util.AuthUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 登陆
 */
@Controller
public class LoginController extends SystemConstants {
	//登录成功主页面
	private static final String MAIN = "main";
	//登录页面
    private static final String LOGIN = "login";
    //修改密码页面
    private static final String MODIFY_PWD = "modifyPwd";
    private final static String SYSTEM_STATUS = "systemStatus";	
    // 向首页重定向时传递的登录标识参数
    private static final String ATTR_LOGIN_FLAG = "loginFlag";
	
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private PermissionService permissionService;
    
    /**
     * 返回登录页面
     * @return
     */
    @RequestMapping({"/",""})
    public String index() {
        return "forward:login";
    }
    
    /**
     * 返回登录页面
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView goLoginPage() {
    	ModelAndView mav = new ModelAndView(LOGIN);
        LoginBean bean = new LoginBean();
        mav.addObject("loginBean", bean);
        return mav;
    }
    
    /**
     * 登录后台验证
     * @param loginBean  登录实体
     * @param result	登录结果
     * @param request	request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView doLogin(@ModelAttribute @Valid LoginBean loginBean, BindingResult result, HttpServletRequest request, RedirectAttributes attr) {
        ModelAndView mav = new ModelAndView(LOGIN);
        LoginBean bean = new LoginBean();
        bean.setLoginName(loginBean.getLoginName());
        mav.addObject("loginBean", bean);

        if (result.hasErrors()) {
            //return mav;
        }
        String capText = (String) request.getSession().getAttribute(SystemConstants.KEY_VALIDATE_CODE);
        request.getSession().setAttribute("firstMenuIndex","");//设置一级菜单选中状态
        request.getSession().setAttribute("secondMenuIndex","");//设置2级菜单选中状态

//        loginBean.setValidCode(capText);
        if (StringUtils.isNotEmpty(capText) && capText.equalsIgnoreCase(loginBean.getValidCode())) {
        	SmAdmin administrator = loginService.login(loginBean.getLoginName(), loginBean.getPassword(), IpUtil.getRealIp(request));
            if (null!=administrator && administrator.getStatus().intValue()!=1) {
            	AuthUtil.setMessage(SystemConstants.MESSAGE_TYPE_ERROR, administrator.getStatus().intValue()==0?SystemConstants.ERROR_LOGIN_ADMIN_NOT_ACTIVATED_WRONG:SystemConstants.ERROR_LOGIN_ADMIN_disabled_WRONG, request);
            }else if (null!=administrator) {
                // set session
                SessionBean sessionBean = new SessionBean();
                administrator.setAdminPwd("");
                sessionBean.setSmAdmin(administrator);
                sessionBean.setRoleName(loginService.getRoleName(administrator.getRoleUuid()));
                sessionBean.setHost(IpUtil.getRealIp(request));
                sessionBean.setLoginTime(DateUtil.getNowTime());
                sessionBean.setPermissionList(permissionService.getPermissionListByRole(administrator.getRoleUuid()));
                request.getSession().setAttribute(SystemConstants.SESSION_BEAN_KEY, sessionBean);
                attr.addFlashAttribute(ATTR_LOGIN_FLAG, true);
                ZNMSLogger.info("用户["+administrator.getAdminId() + "]登录系统");
                mav = new ModelAndView("redirect:/home");
            }else{
                AuthUtil.setMessage(SystemConstants.MESSAGE_TYPE_ERROR, SystemConstants.ERROR_LOGIN_NAME_PASSWORD_WRONG, request);
            }
        } else {
            setMessage(MESSAGE_TYPE_ERROR, ERROR_CAPTCHA, request);
        }
        return mav;
    }
    
	private void setMessage(String type, String messageCode,
			HttpServletRequest request) {
		Map<String, String> messageMap = (Map<String, String>) request
				.getAttribute(KEY_REQUEST_MESSAGE);
		if (messageMap == null) {
			messageMap = new HashMap<String, String>();
		}

		messageMap.put(type, messageCode);
		request.setAttribute(KEY_REQUEST_MESSAGE, messageMap);
	}
    
    /**
     * 注销登录
     * @param request  request
     * @return
     */
    @RequestMapping(value = "/logout")
    public ModelAndView doLogout(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("forward:login");
        AuthUtil.clearSession(request);
        mav.addObject(new LoginBean());
        AuthUtil.setMessage(SystemConstants.MESSAGE_TYPE_INFO, SystemConstants.INFO_LOGIN_ADMIN_LOGIN_OUT, request);
        return mav;
    }
    
    /**
     * 返回修改密码页面
     * @param request  request
     * @return
     */
    @RequestMapping(value = "/modifyPwd" , method = RequestMethod.GET)
    public ModelAndView doModifyPwd(HttpServletRequest request) {
    	
        return new ModelAndView(MODIFY_PWD);
    }
    
    /**
     * 后台修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param NewPasswordAgain  再输一次新密码
     * @param request request
     * @return
     */
    @RequestMapping(value = "/modifyPwd" , method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String doPostModifyPwd(String oldPassword,String newPassword,String NewPasswordAgain,HttpServletRequest request) {
    	
    	if(oldPassword.equals(newPassword)){
    		return "{\"result\":\"不能和原密码重复\"}";
    	}else if(!newPassword.equals(NewPasswordAgain)){
    		return "{\"result\":\"两次输入密码不一致\"}";
    	}else{
    		SessionBean sessionBean = (SessionBean) request.getSession().getAttribute(SystemConstants.SESSION_BEAN_KEY);
    		SmAdmin admin =(SmAdmin) loginService.findByUuid(sessionBean.getSmAdmin().getAdminUuid());
    		if(EncryptionMD5.getMD5(oldPassword).equals(admin.getAdminPwd())){
    			admin.setAdminPwd(EncryptionMD5.getMD5(newPassword));
    			loginService.update(admin);
    			return "{\"result\":\"修改成功\"}";
    		}else{
    			return "{\"result\":\"输入旧密码错误\"}";
    		}
    	}
    }
    
    /**
     * 返回登陆成功后的主页面,但是前提是有登录session
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView goMainPage() {
        ModelAndView mav = new ModelAndView(MAIN);
		return new ModelAndView("redirect:/home");
    }
    
    /**
     * 选中一级菜单
     * @return
     */
    @RequestMapping(value = "/changeFirstMenu/{firstMenuIndex}", method = RequestMethod.POST)
    @ResponseBody
    public String changeMenu(HttpServletRequest request,@PathVariable("firstMenuIndex")String firstMenuIndex){
        request.getSession().setAttribute("firstMenuIndex",firstMenuIndex);
        if(!"first6".equals(firstMenuIndex) && !"first7".equals(firstMenuIndex)){
        	//一级菜单下无二级菜单
        	request.getSession().setAttribute("secondMenuIndex","0");
        }
        return "{\"resultCode\":200}";
    }
    
    /**
     *选中2级菜单
     * @return
     */
    @RequestMapping(value = "/changeSecondMenu/{secondMenuIndex}", method = RequestMethod.POST)
    @ResponseBody
    public String changeSecondMenu(HttpServletRequest request,@PathVariable("secondMenuIndex")String secondMenuIndex){
        request.getSession().setAttribute("secondMenuIndex",secondMenuIndex);
        request.getSession().setAttribute("firstMenuIndex","first"+secondMenuIndex.charAt(6));
        return "{\"resultCode\":200}";
    }
    
}
