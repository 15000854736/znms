/**
 * 
 */
package info.zznet.znms.web.module.system.controller;

import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.dao.CpuTemplateMapper;
import info.zznet.znms.base.dao.ExportLinkMapper;
import info.zznet.znms.base.dao.HostMapper;
import info.zznet.znms.base.dao.MemoryTemplateMapper;
import info.zznet.znms.base.dao.WirelessStatisticalConfigurationMapper;
import info.zznet.znms.base.entity.CpuTemplate;
import info.zznet.znms.base.entity.ExportLink;
import info.zznet.znms.base.entity.MemoryTemplate;
import info.zznet.znms.base.entity.SystemOption;
import info.zznet.znms.base.entity.WirelessStatisticalConfiguration;
import info.zznet.znms.base.util.SpringContextUtil;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;
import info.zznet.znms.web.module.system.service.SystemOptionService;
import info.zznet.znms.web.module.systemLog.collector.CollectorCore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author dell001
 *系统选项controller
 */
@Controller
@RequestMapping("/systemOption")
public class SystemOptionController extends BaseController{

	@Autowired
	private SystemOptionService systemOptionService;
	
	@Autowired
	private CpuTemplateMapper cpuTemplateMapper;
	
	@Autowired
	private MemoryTemplateMapper memoryTemplateMapper;
	
	@Autowired
	private WirelessStatisticalConfigurationMapper wirelessMapper;
	
	@Autowired
	private HostMapper hostMapper;
	
	@Autowired
	private ExportLinkMapper exportLinkMapper;
	
	private static final String VIEW_MAIN = "system/systemOption_main";
	
	/**
	 * 处理系选项页面请求，返回系统选项页面数据
	 * 
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@CheckPermission(PermissionConstants.P_SYSTEM_OPTION_VIEW + "||"
			+ PermissionConstants.P_SYSTEM_OPTION_EDIT )
	public ModelAndView init(HttpServletRequest request) {
		SystemOptionController.class.getClassLoader().getResourceAsStream("");
		return new ModelAndView(VIEW_MAIN).addObject("systemOptionData", systemOptionService.getSystemOptionData());
	}	
	
	/**
	 * 修改系统选项
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@CheckPermission(PermissionConstants.P_SYSTEM_OPTION_EDIT)
	public ModelAndView updateSystemOption(@ModelAttribute SystemOptionBean bean, RedirectAttributes attr){
		
		systemOptionService.updateSystemOption(bean);
		if(!bean.getKafkaIp().equals(WebRuntimeData.instance.getSystemOptionBean().getKafkaIp())) {
			WebRuntimeData.instance.reloadSystemOptionBean();
			WebRuntimeData.instance.refreshProducer();			
		}
		

		CollectorCore collectorCore = (CollectorCore) SpringContextUtil.getBean("collectorCore");
		if(bean.isUseStatus()) {
			collectorCore.init();
		} else {
			collectorCore.shutdown();
		}
		attr.addFlashAttribute("update", "success");
		return new ModelAndView("redirect:/systemOption");
	}
	
	/**
	 * 校验cpu模板是否重复
	 * @return
	 */
	@RequestMapping(value = "/checkCpuGraphTemplateRepeat", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkCpuGraphTemplateRepeat(@RequestParam("graphTemplateUuid")String graphTemplateUuid){
		return cpuTemplateMapper.checkCpuGraphTemplateRepeat(graphTemplateUuid)==null ?false:true;
	}
	
	/**
	 * 添加cpu模板
	 * @param cpuTemplate
	 * @return
	 */
	@RequestMapping(value = "/addCpuTemplate", method = RequestMethod.POST)
	public ModelAndView addCpuTemplate(CpuTemplate cpuTemplate){
		cpuTemplate.setCpuTemplateUuid(UUIDGenerator.getGUID());
		cpuTemplate.setCreateTime(new Date());
		cpuTemplateMapper.insert(cpuTemplate);
		return new ModelAndView("redirect:/systemOption");
	}
	
	/**
	 *查询cpu模板数据
	 * @return
	 */
	@RequestMapping(value = "/getCpuTemplate", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCpuTemplate(){
		List<CpuTemplate> list = cpuTemplateMapper.findAll();
		return JSONArray.fromObject(list).toString();
	}
	
	/**
	 * 删除单个cpu模板
	 * @param cpuTemplateUuid
	 * @return
	 */
	@RequestMapping(value = "/deleteCpuTemplate",method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteCpuTemplate(@RequestParam("cpuTemplateUuid")String cpuTemplateUuid){
		cpuTemplateMapper.deleteByPrimaryKey(cpuTemplateUuid);
		return true;
	}
	
	/**
	 * 校验内存模板是否重复
	 * @return
	 */
	@RequestMapping(value = "/checkMemoryGraphTemplateRepeat", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkMemoryGraphTemplateRepeat(@RequestParam("graphTemplateUuid")String graphTemplateUuid){
		return memoryTemplateMapper.checkMemoryGraphTemplateRepeat(graphTemplateUuid)==null ?false:true;
	}
	
	/**
	 * 添加内存统计模板
	 * @param cpuTemplate
	 * @return
	 */
	@RequestMapping(value = "/addMemoryTemplate", method = RequestMethod.POST)
	public ModelAndView addMemoryTemplate(MemoryTemplate memoryTemplate){
		memoryTemplate.setMemoryTemplateUuid(UUIDGenerator.getGUID());
		memoryTemplate.setCreateTime(new Date());
		memoryTemplateMapper.insert(memoryTemplate);
		return new ModelAndView("redirect:/systemOption");
	}
	
	/**
	 *查询内存统计模板数据
	 * @return
	 */
	@RequestMapping(value = "/getMemoryTemplate", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getMemoryTemplate(){
		List<MemoryTemplate> list = memoryTemplateMapper.findAll();
		return JSONArray.fromObject(list).toString();
	}
	
	/**
	 * 删除单个内存模板
	 * @param memoryTemplateUuid
	 * @return
	 */
	@RequestMapping(value = "/deleteMemoryTemplate",method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteMemoryTemplate(@RequestParam("memoryTemplateUuid")String memoryTemplateUuid){
		memoryTemplateMapper.deleteByPrimaryKey(memoryTemplateUuid);
		return true;
	}
	
	/**
	 * 校验主机无线配置是否重复
	 * @param hostUuid
	 * @param apTemplateUuid
	 * @param userTemplateUuid
	 * @return
	 */
	@RequestMapping(value = "/checkWirelessTemplateRepeat", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkWirelessTemplateRepeat(@RequestParam("hostUuid")String hostUuid,
			@RequestParam("apTemplateUuid")String apTemplateUuid, 
			@RequestParam("userTemplateUuid")String userTemplateUuid){
		return wirelessMapper.checkWirelessTemplateRepeat(hostUuid, apTemplateUuid, userTemplateUuid)==null ?false:true;
	}
	
	/**
	 * 添加无线统计配置
	 * @param wireless
	 * @return
	 */
	@RequestMapping(value = "/addWireless", method = RequestMethod.POST)
	public ModelAndView addWireless(WirelessStatisticalConfiguration wireless){
		wireless.setWirelessStatisticalConfigurationUuid(UUIDGenerator.getGUID());
		wireless.setCreateTime(new Date());
		wirelessMapper.insert(wireless);
		return new ModelAndView("redirect:/systemOption");
	}
	
	
	/**
	 *查询无线统计配置数据
	 * @return
	 */
	@RequestMapping(value = "/getWireless", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getWireless(){
		List<WirelessStatisticalConfiguration> list = wirelessMapper.findAll();
		return JSONArray.fromObject(list).toString();
	}
	
	/**
	 * 删除单个无线统计配置
	 * @param wirelessStatisticalConfigurationUuid
	 * @return
	 */
	@RequestMapping(value = "/deleteWireless",method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteWireless(@RequestParam("wirelessStatisticalConfigurationUuid")String wirelessStatisticalConfigurationUuid){
		wirelessMapper.deleteByPrimaryKey(wirelessStatisticalConfigurationUuid);
		return true;
	}
	
	/**
	 * 判断出口链路配置是否重复
	 * @param hostUuid
	 * @param graphUuid
	 * @return
	 */
	@RequestMapping(value = "/checkExportLinkRepeat", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkExportLinkRepeat(@RequestParam("hostUuid")String hostUuid,
			@Param("graphUuid")String graphUuid){
		return exportLinkMapper.checkExportLinkRepeat(hostUuid, graphUuid)==null ?false:true;
	}
	
	/**
	 * 添加出口链路配置
	 * @param exportLink
	 * @return
	 */
	@RequestMapping(value = "/addExportLink", method = RequestMethod.POST)
	public ModelAndView addExportLink(ExportLink exportLink){
		System.out.println(exportLink.getExportLinkUuid());
		if(exportLink.getExportLinkUuid()!=null&&exportLink.getExportLinkUuid().length()>0){
			exportLink.setMaxBandWidth(StringUtil.convertNetStreamIoSpeed(exportLink.getMaxBandWidthText()));
			exportLinkMapper.insert(exportLink);
		}else{
			exportLink.setExportLinkUuid(UUIDGenerator.getGUID());
			exportLink.setCreateTime(new Date());
			exportLink.setMaxBandWidth(StringUtil.convertNetStreamIoSpeed(exportLink.getMaxBandWidthText()));
			exportLinkMapper.insert(exportLink);
		}

		return new ModelAndView("redirect:/systemOption");
	}
	
	/**
	 *查询出口链路配置数据
	 * @return
	 */
	@RequestMapping(value = "/getExportLink", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getExportLink(){
		List<ExportLink> list = exportLinkMapper.findAll();
		if(list != null) {
			for(ExportLink link : list) {
				link.setMaxBandWidthText(StringUtil.convertNetStreamIoSpeed(link.getMaxBandWidth()));
			}
		}
		return JSONArray.fromObject(list).toString();
	}
	
	/**
	 * 删除单个出口链路配置
	 * @param exportLinkUuid
	 * @return
	 */
	@RequestMapping(value = "/deleteExportLink",method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteExportLink(@RequestParam("exportLinkUuid")String exportLinkUuid){
		exportLinkMapper.deleteByPrimaryKey(exportLinkUuid);
		return true;
	}
	
	@RequestMapping(value = "/editExportLink",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String editExportLink(@RequestParam("exportLinkUuid")String exportLinkUuid){
		ExportLink  exportLink =exportLinkMapper.selectByPrimaryKey(exportLinkUuid);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		Map<String,Object> textMap=new HashMap<String,Object>();
		textMap.put("graphName",exportLink.getGraph().getGraphName());
		textMap.put("hostName",exportLink.getHost().getHostName());
		dataMap.put("select",textMap);
		dataMap.put("exportLink",exportLink);

		//exportLink.setExportLinkDescription(exportLinkDescription);
		//exportLink.setMaxBandWidth(StringUtil.convertNetStreamIoSpeed(exportLink.getMaxBandWidthText()));
		System.out.println(JSONObject.fromObject(dataMap).toString());
		return JSONObject.fromObject(dataMap).toString();
	}
	
	
	/**
	 * 上传ap地图
	 * @param image
	 * @return
	 */
	@RequestMapping(value = "/addApMap", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String addApMap(MultipartFile apMapFile,String imgName,HttpServletRequest req){
		return systemOptionService.addApMap(apMapFile,imgName,req);
	}
	
	
	private JsonConfig getJsonConfig(){
		JsonConfig jsonCfg = new JsonConfig();
    	jsonCfg.setIgnoreDefaultExcludes(false);
		jsonCfg.setExcludes(new String[]{});
		// 防止自包含引发的死循环
		jsonCfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		//转换时间
				jsonCfg.registerJsonValueProcessor(SystemOption.class, "createTime", new JsonValueProcessor() {

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
				jsonCfg.registerJsonValueProcessor(SystemOption.class, "updateTime", new JsonValueProcessor() {
					
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
		return jsonCfg;
	}
	
}
