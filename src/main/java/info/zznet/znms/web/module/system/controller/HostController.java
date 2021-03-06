/**
 * 
 */
package info.zznet.znms.web.module.system.controller;

import info.zznet.znms.base.bean.SearchListBean;
import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.dao.AcOidConfigMapper;
import info.zznet.znms.base.dao.ImportResultMapper;
import info.zznet.znms.base.dao.WirelessStatisticalConfigurationMapper;
import info.zznet.znms.base.entity.AcOidConfig;
import info.zznet.znms.base.entity.GraphTreeItem;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.entity.ImportResult;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.security.bean.SessionBean;
import info.zznet.znms.web.module.system.bean.ImportTask;
import info.zznet.znms.web.module.system.service.GraphTreeItemService;
import info.zznet.znms.web.module.system.service.HostService;
import info.zznet.znms.web.util.ConfigUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author dell001
 *主机controller
 */
@Controller
@RequestMapping("/host")
public class HostController extends BaseController{
	
	@Autowired
	private HostService hostService;
	
	@Autowired
	private GraphTreeItemService graphTreeItemService;
	
	@Autowired
	private ImportResultMapper importResultMapper;
	
	@Autowired
	private AcOidConfigMapper acOidConfigMapper;
	
	@Autowired
	private WirelessStatisticalConfigurationMapper wirelessMapper;
	
	private static final String VIEW_MAIN = "system/host/host_main";
	
	private static final String VIEW_DETAIL = "system/host/host_detail";
	
	private static final String VIEW_IMPORT = "system/host/host_import";
		
	
	private static final String ATTR_AC_SOURCE = "acSource";
	
	/**
	 * 跳转到主机主页面
	 * @return
	 */
	@RequestMapping("")
	@CheckPermission(PermissionConstants.P_HOST_VIEW)
	public ModelAndView init(HttpServletRequest request,Model model){
		String hostWorkStatus=request.getParameter("hostWorkStatus");
		model.addAttribute("req_data", hostWorkStatus);
		return new ModelAndView(VIEW_MAIN);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_HOST_VIEW)
	public String doSearch(Pager<Host> pager,HttpServletRequest request,HttpSession session) throws JsonGenerationException, JsonMappingException, IOException {
		String hostWorkStatus=request.getParameter("hostWorkStatus");
		if(null!=hostWorkStatus&&hostWorkStatus.length()>0){
			pager.setSearch("[{\"key\":\"hostWorkStatus\",\"value\":\""+hostWorkStatus+"\"}]");
		}
		pager = hostService.findPageList(pager);
		return JSONObject.fromObject(pager,getJsonConfig()).toString();
	}
	
	@RequestMapping(value = "/getShutDownHostData", method = RequestMethod.POST)
	@ResponseBody
	public String getShutDownHostData(){
		List<Host> hostList=hostService.getShutDownHost(2);
		return JSONArray.fromObject(hostList).toString();
	}

	/**
	 * 分页查询的JsonConfig配置
	 * @return
	 */
	public JsonConfig getJsonConfig(){
    	JsonConfig jsonCfg = new JsonConfig();
    	jsonCfg.setIgnoreDefaultExcludes(false);
		jsonCfg.setExcludes(new String[]{"WebRuntimeData"});
		// 防止自包含引发的死循环
		jsonCfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		//转换主机状态状态
		jsonCfg.registerJsonValueProcessor(Host.class, "status", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Byte status = (Byte)o;
					int value = status.intValue();
					String returndesc;
					switch(value){
					case 0:
						returndesc = "禁用";break;
					case 1:
						returndesc = "启用";break;
					default:
						returndesc = "未知";
					}
					return returndesc;
				} else {
					return "";
				}
			}
		});
		//转换主机状态状态
		jsonCfg.registerJsonValueProcessor(Host.class, "hostWorkStatus", new JsonValueProcessor() {
			
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
					case 0:
						returndesc = "宕机";break;
					case 1:
						returndesc = "正常";break;
					default:
						returndesc = "未知";
					}
					return returndesc;
				} else {
					return "";
				}
			}
		});
		
		//转换主机可用性
		jsonCfg.registerJsonValueProcessor(Host.class, "availability", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					BigDecimal availability = (BigDecimal)o;
					return availability.toString()+"%";    
				} else {
					return "";
				}
			}
		});
		
		//转换时间
		jsonCfg.registerJsonValueProcessor(ImportResult.class, "registerTime", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Date registerTime = (Date)o;
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(registerTime);    
				} else {
					return "";
				}
			}
		});
		//转换时间
		jsonCfg.registerJsonValueProcessor(ImportTask.class, "registerTime", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Date registerTime = (Date)o;
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(registerTime);    
				} else {
					return "";
				}
			}
		});
		//转换时间
		jsonCfg.registerJsonValueProcessor(ImportResult.class, "finishTime", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Date finishTime = (Date)o;
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(finishTime);    
				} else {
					return "";
				}
			}
		});
		
		return jsonCfg;
    }
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(value= "/add", method = RequestMethod.GET)
	@CheckPermission(PermissionConstants.P_HOST_ADD)
	public ModelAndView toAddPage(){
		ModelAndView mav = new ModelAndView(VIEW_DETAIL);
		List<AcOidConfig> acOidConfigList=acOidConfigMapper.findAll();
		List<Map<String, Object>> acSource = new ArrayList<Map<String,Object>>();
		for (AcOidConfig acOidConfig : acOidConfigList) {
			Map<String, Object> textMap = new HashMap<String, Object>();
			textMap.put("text", acOidConfig.getAcBrandName());
			textMap.put("value",acOidConfig.getAcBrand());
			acSource.add(textMap);
		}
		
		return mav.addObject("host",new Host()).
				addObject("disabled",false).addObject("page","add").addObject(ATTR_AC_SOURCE, JSONArray.fromObject(acSource).toString());
	}
	
	/**
	 * 新增或者修改主机
	 * @param host
	 * @return
	 */
	@RequestMapping(value = "/merge/do", method = RequestMethod.POST)
	@CheckPermission(PermissionConstants.P_HOST_ADD + "||"
			+ PermissionConstants.P_HOST_EDIT)
	@ResponseBody
	public String mergeDo(@ModelAttribute Host host){
		//判断主机名称是否重复
		Host nameHost = hostService.checkHostName(host);
		if(null != nameHost){
			return "{\"result\":false,\"msg\":\"主机名重复\"}";
		}
		//判断ip是否重复
		Host ho = hostService.checkHostIp(host);
		if(null != ho){
			return "{\"result\":false,\"msg\":\"主机IP已存在\"}";
		}
		if(StringUtils.isEmpty(host.getId())){
			hostService.addHost(host);
		}else{
			//修改主机
			hostService.update(host);
		}
		return "{\"result\":true}";
	}
	
	/**
	 * 跳转到主机编辑页面/详情页面
	 * @return
	 */
	@RequestMapping(value= "/{action}/{id}", method = RequestMethod.GET)
	@CheckPermission(PermissionConstants.P_HOST_EDIT + "||" + PermissionConstants.P_HOST_DETAIL)
	public ModelAndView toEditPage(@PathVariable("action")String action,@PathVariable("id")String id){
		Host host = hostService.findHostById(id);
		ModelAndView mv = new ModelAndView(VIEW_DETAIL); 
		mv.addObject("host",host);
		List<AcOidConfig> acOidConfigList=acOidConfigMapper.findAll();
		List<Map<String, Object>> acSource = new ArrayList<Map<String,Object>>();
		for (AcOidConfig acOidConfig : acOidConfigList) {
			Map<String, Object> textMap = new HashMap<String, Object>();
			textMap.put("text", acOidConfig.getAcBrandName());
			textMap.put("value",acOidConfig.getAcBrand());
			acSource.add(textMap);
		}
		
		mv.addObject(ATTR_AC_SOURCE, JSONArray.fromObject(acSource).toString());
		if("edit".equals(action)){
			mv.addObject("disabled",false).addObject("page","edit");
		}else{
			mv.addObject("disabled",true).addObject("page","detail");
		}
		return mv;
	}
	
	/**
   	 * 删除单一数据
   	 * @param jsonData 即将删除的数据
   	 * @param request
   	 * @return
   	 */
   	@RequestMapping(value = "/deleteSingle", method = RequestMethod.POST)
   	@ResponseBody
   	@CheckPermission(PermissionConstants.P_HOST_DELETE)
   	public String deleteSingle(@RequestParam("jsonData") String jsonData,HttpServletRequest request){
   		JSONObject jsonObject = JSONObject.fromObject(jsonData);
   		String id = jsonObject.getString("id");
   		//判断主机能否删除
   		List<GraphTreeItem> itemList = graphTreeItemService.findByHostId(id);
   		if(null!=itemList && itemList.size()>0){
   			//判断主机是否在图形树里
   			return "{\"result\":false,\"msg\":\"图形树中使用该主机，删除失败\"}";
   		}
   		hostService.deleteByPrimaryKey(id);
   		return "{\"result\":true}";
   	}
   	
    /**
   	 * 删除选中数据（复数）
   	 * @param jsonData 选中的数据
   	 * @param request
   	 * @return
   	 */
   	@RequestMapping(value = "/delete", method = RequestMethod.POST)
   	@ResponseBody
   	@CheckPermission(PermissionConstants.P_HOST_DELETE)
   	public String deleteMulti(@RequestParam("jsonData") String jsonData,HttpServletRequest request) {
   		JSONArray jsonArray = JSONArray.fromObject(jsonData);
   		List<String> uuids = new ArrayList<String>();
   		String pkId = "id";
   		for(int i=0,j=jsonArray.size();i<j;i++){
   			uuids.add((String) jsonArray.getJSONObject(i).get(pkId));
   		}
   		return hostService.deleteByUuidList(uuids);
   	}
   	
   	/**
   	 * 启用主机
   	 * @param ids
   	 * @return
   	 */
   	@RequestMapping(value = "/enable", method = RequestMethod.POST)
   	@CheckPermission(PermissionConstants.P_HOST_EDIT)
   	@ResponseBody
   	public String enableHost(String ids){
   		List<String> uuidList = Arrays.asList(ids.split(","));
   		hostService.updateHostEnable(uuidList);
   		return  "{\"result\":true}";
   	}
   	
   	/**
   	 * 禁用主机
   	 * @param ids
   	 * @return
   	 */
   	@RequestMapping(value = "/disable", method = RequestMethod.POST)
   	@CheckPermission(PermissionConstants.P_HOST_EDIT)
   	@ResponseBody
   	public String disableHost(String ids){
   		List<String> uuidList = Arrays.asList(ids.split(","));
   		hostService.updateHostDisable(uuidList);
   		return  "{\"result\":true}";
   	}
   	
   	/**
   	 * 查找备份配置设备页面可用主机
   	 * @param deviceUuid
   	 * @return
   	 */
   	@RequestMapping(value = "/findDeviceHost", method = RequestMethod.POST)
   	@ResponseBody
   	public String findDeviceHost(@RequestParam("deviceUuid")String deviceUuid){
   		List<Host> list = hostService.findDeviceHost(deviceUuid);
   		List<SearchListBean> beanList = new ArrayList<SearchListBean>();
   		beanList.add(new SearchListBean("","请选择主机"));
   		for(Host host: list){
   			beanList.add(new SearchListBean(host.getId(),host.getHostName()));
   		}
   		return JSONArray.fromObject(beanList).toString();
   	}
   	
   	/**
   	 * 查找所有主机
   	 * @return
   	 */
   	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
   	@ResponseBody
   	public String findAllHost(){
   		List<Host> list = hostService.findAll();
   		return JSONArray.fromObject(list).toString();
   	}
   	
   	/**
   	 * 查找图形管理主页面搜索条件上的所有主机
   	 * @return
   	 */
   	@RequestMapping(value = "/findHostJson", method = RequestMethod.POST)
   	@ResponseBody
   	public String findHostJson(){
   		List<Host> list = hostService.findAll();
   		List<SearchListBean> searchBeanList = new ArrayList<SearchListBean>();
   		searchBeanList.add(new SearchListBean("-1","请选择主机"));
   		for (Host host : list) {
			SearchListBean bean = new SearchListBean(host.getId(), host.getHostName());
			searchBeanList.add(bean);
		}
   		return JSONArray.fromObject(searchBeanList).toString();
   	}
   	
   	@RequestMapping("/import")
   	@CheckPermission(PermissionConstants.P_HOST_ADD)
   	public ModelAndView toImport() {
   		ModelAndView mav = new ModelAndView(VIEW_IMPORT);
		return mav;
   	}
   	
   	@RequestMapping(value = "/import/queue", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
   	@ResponseBody
   	public String getImportTask(){
   		LinkedBlockingQueue<ImportTask> queue = WebRuntimeData.instance.getTaskQueue();
   		List<ImportTask> list = new ArrayList<ImportTask>();
   		if(WebRuntimeData.instance.getRunningTask() != null){
   			list.add(WebRuntimeData.instance.getRunningTask());
   		}
   		for(ImportTask task : queue) {
   			list.add(task);
   		}
   		return JSONArray.fromObject(list, getJsonConfig()).toString();
   	}
   	
   	@RequestMapping(value = "/import/result", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
   	@ResponseBody
   	public String getImportResult() {
		return JSONArray.fromObject(importResultMapper.findAll(), getJsonConfig()).toString();
   	}
   	
   	@RequestMapping(value = "/import/do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
   	@ResponseBody
   	public String doImport(MultipartFile importFile, HttpSession session){
   		if(session==null){
   			return null;
   		}
   		SessionBean sessionBean = (SessionBean) session.getAttribute(SystemConstants.SESSION_BEAN_KEY);
   		if(sessionBean == null){
   			return null;
   		}
   		String adminId = sessionBean.getSmAdmin().getAdminId();
   		if(importFile==null || importFile.getSize()==0){
   			return "{\"result\":false, \"msg\":\"请选择导入文件\"}"; 
   		}
   		String id = UUIDGenerator.getGUID();
		File importResultDir = new File(ConfigUtil.getString("znms.import.path"));
		if(!importResultDir.exists() || !importResultDir.isDirectory()){
			importResultDir.mkdirs();
		}
		String fileName = importFile.getOriginalFilename();
		String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
		File localFile = new File(ConfigUtil.getString("znms.import.path")+ id + fileSuffix);
		try {
			importFile.transferTo(localFile);
		} catch (IllegalStateException | IOException e) {
			ZNMSLogger.error(e);
		}
		
   		if(WebRuntimeData.instance.addImportTask(localFile, id, adminId)){
   			return "{\"result\":true}";   			
   		} else {
   			return "{\"result\":false, \"msg\":\"任务队列已满，请稍后再试\"}";
   		}
   	}
   	
   	@RequestMapping(value = "/import/deleteResult", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
   	@ResponseBody
   	public String deleteImportResult(String id) {
   		try{
   			if(importResultMapper.deleteByPrimaryKey(id) == 0){
   				return "{\"result\":false,\"msg\":\"该记录已删除\"}";
   			}
   			File file = new File(ConfigUtil.getString("znms.import.path")+id+".xlsx");
   			if(file.exists() && file.isFile()){
   				file.delete();
   			}
   			return "{\"result\":true}";
   		} catch(Exception e){
   			ZNMSLogger.error(e);
   			return "{\"result\":false,\"msg\":\""+e.getMessage()+"\"}";
   		}
   		
   	}
   	
   	/**
   	 * 查找所有接入为无线控制器的主机
   	 * @return
   	 */
   	@RequestMapping(value = "/findAcJson", method = RequestMethod.POST)
   	@ResponseBody
   	public String findAcJson(){
   		List<Host> list = hostService.findAllAc();
   		List<SearchListBean> beanList = new ArrayList<SearchListBean>();
   		if(list == null || list.size() == 0){
   			beanList.add(new SearchListBean("-1", "请选择AC"));
   		}
   		for (Host host : list) {
   			beanList.add(new SearchListBean(host.getHostIp(), host.getHostName()));
		}
   		return JSONArray.fromObject(beanList).toString();
   	}
   	
   	/**
   	 * 根据uuid查找主机
   	 * @return
   	 */
   	@RequestMapping(value = "/findByUuid", method = RequestMethod.POST)
   	@ResponseBody
   	public String findByUuid(@RequestParam("index")String index){
   		String[] indexs = StringUtils.split(index,",");
		if(null!=index&&indexs.length==2){
			Host host = hostService.findHostById((indexs[0]));
			return JSONObject.fromObject(host).toString();
		}else{
			return "";
		}
   	}

}
