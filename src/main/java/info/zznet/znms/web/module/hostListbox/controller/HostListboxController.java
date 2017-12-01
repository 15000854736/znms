/**
 * 
 */
package info.zznet.znms.web.module.hostListbox.controller;

import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.spider.Engine;
import info.zznet.znms.spider.bean.ScanHost;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.hostListbox.bean.HostListboxBean;
import info.zznet.znms.web.module.hostListbox.service.HostListboxService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 主机列表controller
 * @author dell001
 *
 */
@RequestMapping("/hostListbox")
@Controller
public class HostListboxController extends BaseController{
	
	@Autowired
	private HostListboxService hostListboxService;
	
	private static List<HostListboxBean> beanList = new ArrayList<HostListboxBean>();
	
	/**
	 * 跳转到主页面
	 * @return
	 */
	@RequestMapping("")
	@CheckPermission(PermissionConstants.P_HOST_LISTBOX_VIEW)
	public ModelAndView init(){
		//查询页面数据
		beanList = hostListboxService.findHostListbox();
		return new ModelAndView("hostListbox/hostListbox_main").addObject("hostListboxBeanList", beanList);
	}
	
	/**
	 * 查询各个图形树下的主机状态
	 * @return
	 */
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	@ResponseBody
	public String refresh(){
		Map<String,ScanHost> map = Engine.hosts;
		for(HostListboxBean bean:beanList){
			List<Host> hostList = bean.getHostList();
			for(Host host:hostList){
				if(map.containsKey(host.getHostIp())){
					ScanHost scanHost = map.get(host.getHostIp());
					host.setHostWorkStatus(scanHost.isReachable()?1:0);
				}
			}
		}
		return JSONArray.fromObject(beanList, getJsonConfig()).toString();
	}

	/**
	 * @return
	 */
	private JsonConfig getJsonConfig() {
		JsonConfig jsonCfg = new JsonConfig();
    	jsonCfg.setIgnoreDefaultExcludes(false);
		jsonCfg.setExcludes(new String[]{});
		// 防止自包含引发的死循环
		jsonCfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		//转换最后宕机时间
		jsonCfg.registerJsonValueProcessor(Host.class, "lastShutDownTime", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Date lastShutDownTime = (Date)o;
					return DateUtil.dateToStr(lastShutDownTime, DateUtil.DF_yyyyMMddHHmmss);
				} else {
					return "";
				}
			}
		});
		return jsonCfg;
	}
	
}
