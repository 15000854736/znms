/**
 * 
 */
package info.zznet.znms.web.module.apInformation.controller;

import info.zznet.znms.base.bean.SearchListBean;
import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.entity.ApInformation;
import info.zznet.znms.base.entity.ApRegion;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.apInformation.bean.DeviceSearchBean;
import info.zznet.znms.web.module.apInformation.service.ApInformationService;
import info.zznet.znms.web.module.apInformation.service.ApRegionService;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;
import info.zznet.znms.web.module.system.service.HostService;
import info.zznet.znms.web.module.system.service.SystemOptionService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * AP信息controller
 * @author dell001
 *
 */
@RequestMapping("/apInformation")
@Controller
public class ApInformationController extends BaseController{

	@Autowired
	private ApInformationService apInformationService;
	
	@Autowired
	private SystemOptionService systemOptionService;
	
	@Autowired
	private ApRegionService apRegionService;
	
	@Autowired
	private HostService hostService;
	
	private static final String VIEW_MAIN = "apInformation/apInformation_main";
	
	private static final String VIEW_REGION = "apInformation/region_html";
	
	/**
	 * 跳转到主页面
	 * @return
	 */
	@RequestMapping("")
	@CheckPermission(PermissionConstants.P_AP_INFORMATION_VIEW)
	public ModelAndView init(HttpServletRequest request){
		String type = request.getParameter("type");
		return new ModelAndView(VIEW_MAIN).addObject("apMapPath", new SystemOptionBean().getSchoolBg()).addObject("type", type);
	}
	
	/**
	 * 根据条件查找ap或者主机
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = "/findApByCondition", method = RequestMethod.POST)
	@ResponseBody
	public String findApByCondition(DeviceSearchBean bean){
		List list = new ArrayList();
		if("ap".equals(bean.getDeviceType())){
			list = apInformationService.findApByCondition(bean);
		}else{
			list = hostService.findHostByCondition(bean);
		}
		return JSONArray.fromObject(list).toString();
	}
	
	/**
	 * 直接设置ap位置
	 * @param apInformationUuid
	 * @param apAxis
	 * @return
	 */
	@RequestMapping(value = "/changePosition", method = RequestMethod.POST)
	@ResponseBody
	public String changePosition(@RequestParam("indexes") String indexes,
			@RequestParam("apAxis")String apAxis, @RequestParam("deviceType")String deviceType){
		if(!StringUtils.isEmpty(indexes)) {
			if("ap".equals(deviceType)){
				//设置ap坐标
				apInformationService.update(indexes.split("_"), apAxis);
			}else{
				//设置主机坐标
				hostService.updateHostAxis(indexes.split("_"), apAxis);
			}
		}
		return "{\"reslu\":true}";
	}
	
	/**
	 * 通过区域设置ap位置
	 * @param apInformationUuid
	 * @param apAxis
	 * @return
	 */
	@RequestMapping(value = "/changePositionByRegion", method = RequestMethod.POST)
	@ResponseBody
	public String changePositionByRegion(@RequestParam("indexes") String indexes,
			@RequestParam("apRegionUuid")String apRegionUuid, @RequestParam("deviceType")String deviceType){
		if(!StringUtils.isEmpty(indexes)) {
			if("ap".equals(deviceType)){
				apInformationService.updateByRegion(indexes.split("_"), apRegionUuid);
			}else{
				hostService.updateHostAxisByRegion(indexes.split("_"), apRegionUuid);
			}
		}
		return "{\"reslu\":true}";
	}
	
	/**
	 * 查找ap信息
	 * @param apInformationUuid
	 * @return
	 */
	@RequestMapping(value = "/findApAxis", method = RequestMethod.POST)
	@ResponseBody
	public String findApAxis(@RequestParam("index")String index){
		String[] indexs = StringUtils.split(index,",");
		if(null!=index&&indexs.length==2){
			ApInformation apInformation = apInformationService.selectByPrimaryKey(indexs[0]);
			return JSONObject.fromObject(apInformation).toString();
		}else{
			return "";
		}

	}
	
	/**
	 * 查找所有区域
	 * @return
	 */
	@RequestMapping(value = "/findApRegion", method = RequestMethod.POST)
	@ResponseBody
	public String findApRegion(){
		List<SearchListBean> beanList = new ArrayList<SearchListBean>();
		beanList.add(new SearchListBean("-1", "请选择区域"));
		List<ApRegion> list = apRegionService.findAllApRegion();
		for (ApRegion apRegion : list) {
			beanList.add(new SearchListBean(apRegion.getApRegionUuid(), apRegion.getApRegionName()));
		}
		return JSONArray.fromObject(beanList).toString();
	}
	
	/**
	 * 检查区域名称是否重复
	 * @param apRegionName
	 * @return
	 */
	@RequestMapping(value = "/checkRegionNameRepeat", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkRegionNameRepeat(@RequestParam("apRegionName")String apRegionName){
		return null!=apRegionService.checkRegionNameRepeat(apRegionName)? true:false;
	}
	
	/**
	 * 新建区域
	 * @param apRegionCoordinate
	 * @param regionName
	 * @return
	 */
	@RequestMapping(value = "/addRegion", method = RequestMethod.POST)
	@ResponseBody
	public String addRegion(@RequestParam("apRegionCoordinate")String apRegionCoordinate, 
			@RequestParam("apRegionName")String apRegionName){
		ApRegion apRegion = new ApRegion();
		apRegion.setApRegionCoordinate(apRegionCoordinate);
		apRegion.setApRegionName(apRegionName);
		apRegionService.add(apRegion);
		return "{\"result\":true}";
	}
	
	/**
	 * 查找区域
	 * @param current
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/findRegion", method = RequestMethod.POST)
	public ModelAndView findRegion(@RequestParam("current")Integer current, 
			@RequestParam("apRegionName")String apRegionName ,Pager<ApRegion> pager){
		return selectRegion(current, apRegionName, pager);
	}
	
	/**
	 * 删除区域
	 * @param apRegionUuid
	 * @param apRegionName
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/deleteRegion", method = RequestMethod.POST)
	public ModelAndView deleteRegion(@RequestParam("apRegionUuid")String apRegionUuid, 
			@RequestParam("apRegionName")String apRegionName, 
			@RequestParam("isCascadeDelete")Integer isCascadeDelete,Pager<ApRegion> pager){
		apRegionService.deleteRegion(apRegionUuid, isCascadeDelete);
		return selectRegion(1, apRegionName, pager);
	}
	
	private ModelAndView selectRegion(Integer current, String apRegionName, Pager<ApRegion> pager){
		pager.setLimit(5);
		ModelAndView mav = new ModelAndView(VIEW_REGION);
		pager.setOffset((current-1)*5);
		List<ApRegion> regionList = apRegionService.findRegion(pager, apRegionName);
		long count = apRegionService.getCount(apRegionName);
		pager.setRows(regionList);
		pager.setTotal(count);
		mav.addObject("pager",pager);
		return mav;
	}
	
	/**
	 * 回显ap区域范围
	 * @param apRegionUuid
	 * @return
	 */
	@RequestMapping(value = "/drawApRegion", method = RequestMethod.POST)
	@ResponseBody
	public String drawApRegion(@RequestParam("apRegionUuid")String apRegionUuid){
		ApRegion apRegion = apRegionService.selectByPrimaryKey(apRegionUuid);
		return JSONObject.fromObject(apRegion).toString();
	}
}
