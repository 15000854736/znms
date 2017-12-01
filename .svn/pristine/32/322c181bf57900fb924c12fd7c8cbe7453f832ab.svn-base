/**
 * 
 */
package info.zznet.znms.web.module.system.controller;

import info.zznet.znms.base.bean.SearchListBean;
import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.entity.BackupConfigurationAccountPassword;
import info.zznet.znms.base.entity.BackupConfigurationDevice;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.system.service.BackupAccountPasswordService;
import info.zznet.znms.web.module.system.service.BackupDeviceService;
import info.zznet.znms.web.module.system.service.HostService;
import info.zznet.znms.web.util.PinyinTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author dell001
 *	配置备份controller
 */
@Controller
@RequestMapping("/backupConfiguration")
public class BackupConfigurationController extends BaseController{
	
	@Autowired
	private BackupAccountPasswordService accountPasswordService;
	
	@Autowired
	private BackupDeviceService deviceService;
	
	 private WebRuntimeData webRuntimeData = WebRuntimeData.instance;
	@Autowired
	private HostService hostService;
	private static final String ACCOUNT_PASSWORD_VIEW_MAIN = "system/backupConfiguration/backupConfiguration_ap";
	
	private static final String DEVICE_VIEW_MAIN = "system/backupConfiguration/backupConfiguration_device";
	
	/**
	 * 跳转到账户&密码主页面
	 * @return
	 */
	@RequestMapping("")
	@CheckPermission(PermissionConstants.P_BACKUP_CONFIGURATION_AP_VIEW)
	public ModelAndView init(){
		return new ModelAndView(ACCOUNT_PASSWORD_VIEW_MAIN).addObject("accountPassword", 
				new BackupConfigurationAccountPassword());
	}
	
	/**
	 * 查找账户&密码数据
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/accountPassword/search", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_BACKUP_CONFIGURATION_AP_VIEW)
	public String doSearchAccountPassword(Pager pager){
		pager = accountPasswordService.findPageList(pager);
		return JSONObject.fromObject(pager).toString();
	}
	
	/**
	 * 校验账户&密码名称是否重复
	 * @param apName
	 * @param accountPasswordUuid
	 * @return
	 */
	@RequestMapping(value = "checkApName", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkApName(@RequestParam("apName")String apName,
			@RequestParam("accountPasswordUuid")String accountPasswordUuid){
		return accountPasswordService.findByApName(apName, accountPasswordUuid)==null ?true:false;
	}
	
	/**
	 * 添加/修改账户&密码
	 * @param accountPassword
	 * @return
	 */
	@RequestMapping(value = "/mergeAccountPassword", method = RequestMethod.POST)
	@CheckPermission(PermissionConstants.P_BACKUP_CONFIGURATION_AP_ADD)
	public ModelAndView addAccountPassword(@ModelAttribute BackupConfigurationAccountPassword accountPassword){
		if(StringUtil.isNullString(accountPassword.getAccountPasswordUuid())){
			accountPasswordService.addAccountPassword(accountPassword);
		}else{
			accountPasswordService.updateAccountPassword(accountPassword);
		}
		
		return new ModelAndView("redirect:/backupConfiguration");
	}
	
	/**
	 * 根据uuid查找账户&密码
	 * @param accountPasswordUuid
	 * @return
	 */
	@RequestMapping(value = "findAPByUuid", method = RequestMethod.POST)
	@ResponseBody
	public String findAPByUuid(@RequestParam("accountPasswordUuid")String accountPasswordUuid){
		BackupConfigurationAccountPassword ap = accountPasswordService.findAPByUuid(accountPasswordUuid);
		return JSONObject.fromObject(ap).toString();
	}
	
	/**
	 * 删除单个账户&密码
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/accountPassword/deleteSingle", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_BACKUP_CONFIGURATION_AP_DELETE)
	public String deleteSingleAP(@RequestParam("jsonData") String jsonData){
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
   		String uuid = jsonObject.getString("accountPasswordUuid");
   		accountPasswordService.deleteByPrimaryKey(uuid);
		return "{\"result\":true}";
	}
	
	/**
	 * 删除多个账户&密码
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/accountPassword/delete", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_BACKUP_CONFIGURATION_AP_DELETE)
	public String deleteApList(@RequestParam("jsonData") String jsonData){
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
   		List<String> uuids = new ArrayList<String>();
   		String pkId = "accountPasswordUuid";
   		for(int i=0,j=jsonArray.size();i<j;i++){
   			uuids.add((String) jsonArray.getJSONObject(i).get(pkId));
   		}
   		return accountPasswordService.deleteByUuidList(uuids);
	}
	
	/**
	 * 跳转到设备主页面
	 * @return
	 */
	@RequestMapping("/device")
	@CheckPermission(PermissionConstants.P_BACKUP_CONFIGURATION_DEVICE_VIEW)
	public ModelAndView toDevicePage(){
		return new ModelAndView(DEVICE_VIEW_MAIN).addObject("device", 
				new BackupConfigurationDevice());
	}
	
	/**
	 * 查找设备数据
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/device/search", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_BACKUP_CONFIGURATION_DEVICE_VIEW)
	public String doSearchDevice(Pager<BackupConfigurationDevice> pager){
		pager = deviceService.findPageList(pager);
		return JSONObject.fromObject(pager, getJsonConfig()).toString();
	}
	
	public JsonConfig getJsonConfig(){
		JsonConfig jsonCfg = new JsonConfig();
    	jsonCfg.setIgnoreDefaultExcludes(false);
		jsonCfg.setExcludes(new String[]{});
		// 防止自包含引发的死循环
		jsonCfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		//转换启用状态
		jsonCfg.registerJsonValueProcessor("useStatus", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					boolean useStatus = (boolean)o;;
					String returndesc;
					if(useStatus){
						returndesc = "启用";
					}else{
						returndesc = "禁用";
					}
					return returndesc;
				} else {
					return "";
				}
			}
		});
		
		//转换备份周期
		jsonCfg.registerJsonValueProcessor("backupCycle", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Integer status = (Integer)o;
					int value = status.intValue();
					String returndesc;
					switch(value){
					case 1:
						returndesc = "每天";break;
					case 2:
						returndesc = "每周";break;
					case 3:
						returndesc = "每月";break;
					default:
						returndesc = "未知";
					}
					return returndesc;
				} else {
					return "";
				}
			}
		});
		
		//转换账户&密码
		jsonCfg.registerJsonValueProcessor("accountPasswordUuid", new JsonValueProcessor() {

	        @Override
	        public Object processArrayValue(Object o, JsonConfig jsonConfig) {
	            return null;
	        }

	        @Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					String accountPasswordUuid = (String)o;
					if(!StringUtil.isNullString(accountPasswordUuid)){
						return accountPasswordService.findAPByUuid(accountPasswordUuid).getApName();
					}else{
						return "无";
					}
				}else {
					return "无";
				}
			}
	    });
		return jsonCfg;
	}
	
	/**
	 * 查找所有账户&密码
	 * @return
	 */
	@RequestMapping(value = "/findAllAccountPassword", method = RequestMethod.POST)
	@ResponseBody
	public String findAllAccountPassword(){
		List<BackupConfigurationAccountPassword> list= accountPasswordService.findAll();
		List<SearchListBean> beanList = new ArrayList<SearchListBean>();
		beanList.add(new SearchListBean("","无"));
		for (BackupConfigurationAccountPassword ap : list) {
			beanList.add(new SearchListBean(ap.getAccountPasswordUuid(),ap.getApName()));
		}
		return JSONArray.fromObject(beanList).toString();
	}
	
	/**
	 *添加/修改设备
	 * @return
	 */
	@RequestMapping(value = "/mergeDevice", method = RequestMethod.POST)
	public ModelAndView mergeDevice(@ModelAttribute BackupConfigurationDevice device){
		if(StringUtil.isNullString(device.getDeviceUuid())){
			//添加设备
			deviceService.addDevice(device);
		}else{
			//修改设备
			deviceService.update(device);
		}
		return new ModelAndView("redirect:/backupConfiguration/device");
	}
	
	/**
	 * 根据设备uuid查找设备
	 * @param deviceUuid
	 * @return
	 */
	@RequestMapping(value = "/findDeviceByUuid", method = RequestMethod.POST)
	@ResponseBody
	public String findDeviceByUuid(@RequestParam("deviceUuid")String deviceUuid){
		BackupConfigurationDevice device = deviceService.selectByPrimaryKey(deviceUuid);
		return JSONObject.fromObject(device).toString();
	}
	
	/**
	 * 删除单个设备
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/device/deleteSingle", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_BACKUP_CONFIGURATION_DEVICE_DELETE)
	public String deleteSingleDevice(@RequestParam("jsonData")String jsonData){
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
   		String uuid = jsonObject.getString("deviceUuid");
   		deviceService.deleteByPrimaryKey(uuid);
		return "{\"result\":true}";
	}
	
	/**
	 * 删除多个设备
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/device/delete", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_BACKUP_CONFIGURATION_DEVICE_DELETE)
	public String deleteDeviceList(@RequestParam("jsonData") String jsonData){
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
   		List<String> uuids = new ArrayList<String>();
   		String pkId = "deviceUuid";
   		for(int i=0,j=jsonArray.size();i<j;i++){
   			uuids.add((String) jsonArray.getJSONObject(i).get(pkId));
   		}
   		deviceService.deleteByUuidList(uuids);
   		return JSONObject.fromObject(deviceService.findPageList(new Pager())).toString();
	}
	
    @RequestMapping(value = "/getBackRoute", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
	public String getBackRoute(HttpServletRequest request){
    	StringBuilder sb=new StringBuilder();
    	Map<String, Object> textMap = new HashMap<String, Object>();
    	try {
    		String backPath=webRuntimeData.getSystemOptionBean().getBackupPath();
    		sb.append(backPath);
			Host  host =hostService.findHostById(request.getParameter("hostUuid"));
			if(host!=null){
				PinyinTool pinyinTool=new PinyinTool();
				sb.append(pinyinTool.toPinYin(host.getHostName()));
				textMap.put("backRoute", sb.toString());
			}
		} catch (Exception e) {
			ZNMSLogger.error("转换失败");
		}
    	return JSONObject.fromObject(textMap).toString();
    }
}
