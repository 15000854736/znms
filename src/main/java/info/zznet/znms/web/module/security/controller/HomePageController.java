package info.zznet.znms.web.module.security.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.dao.ExportLinkMapper;
import info.zznet.znms.base.entity.ExportLink;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.entity.OnlineUserAnalysis;
import info.zznet.znms.base.rrd.bean.Graph;
import info.zznet.znms.base.rrd.bean.SubItem;
import info.zznet.znms.base.rrd.core.RrdFetcher;
import info.zznet.znms.base.rrd.exception.RrdQueryException;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.screen.bean.ExportStreamInfo;
import info.zznet.znms.web.module.screen.bean.OnlineUserInfo;
import info.zznet.znms.web.module.security.bean.OnlineUserChartBean;
import info.zznet.znms.web.module.security.service.HomePageService;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
public class HomePageController extends BaseController{
	
	@Autowired
	private HomePageService homePageService;
	
	@Autowired
	private ExportLinkMapper exportLinkMapper;
	
	private static final String VIEW_MAIN  = "main";
	
	/**
	 * 初始化
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView init(HttpSession session){
		ModelAndView mv = new ModelAndView(VIEW_MAIN);
		return mv;
	}
	
	/**
	 * 查找出口利用率数据
	 * @return
	 */
	@RequestMapping(value = "/getExportFlowData", method = RequestMethod.POST)
	@ResponseBody
	public String getExportFlowData(){
		List<ExportStreamInfo> exportStreamInfoList = new ArrayList<ExportStreamInfo>();
		List<ExportLink> exportLinkList = exportLinkMapper.findAll();
		if(exportLinkList == null) {
			return JSONArray.fromObject(exportStreamInfoList).toString();
		}
		
		Calendar now = Calendar.getInstance();
		Calendar oneHourAgo = Calendar.getInstance();
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		oneHourAgo.set(Calendar.SECOND, 0);
		oneHourAgo.set(Calendar.MILLISECOND, 0);
		oneHourAgo.add(Calendar.MINUTE, -60);
		
		for(ExportLink exportLink : exportLinkList){
			try {
				Graph graph = RrdFetcher.fetchDataAndConvertToGraph(
						SystemConstants.TEMPLATE_NAME_NET_STREAM,
						exportLink.getGraphUuid(), oneHourAgo.getTimeInMillis(),
						now.getTimeInMillis());
				ExportStreamInfo exportStreamInfo = new ExportStreamInfo();
				exportStreamInfo.setDivId(UUIDGenerator.getGUID());
				exportStreamInfo.setGraph(graph);
				exportStreamInfo.setName(exportLink.getExportLinkDescription());
				exportStreamInfo.setMaxBandWidth(exportLink.getMaxBandWidth());
				if(null!=graph){
					List<SubItem> subItems = graph.getData();
					if(subItems == null) {
						exportStreamInfo.setUpStream(0d);
						exportStreamInfo.setDownStream(0d);
					} else {
						SubItem upStream = subItems.get(0);
						List<String> data = upStream.getData();
						if(data == null) {
							exportStreamInfo.setUpStream(0d);
						} else {
							exportStreamInfo.setUpStream(Double.parseDouble(data.get(data.size() - 1)));
						}
						SubItem downStream = subItems.get(1);
						data = downStream.getData();
						if(data == null) {
							exportStreamInfo.setDownStream(0d);
						} else {
							exportStreamInfo.setDownStream(Double.parseDouble(data.get(data.size() - 1)));
						}
					}
				}else{
					exportStreamInfo.setUpStream(0d);
					exportStreamInfo.setDownStream(0d);
				}
				
				exportStreamInfoList.add(exportStreamInfo);
			} catch (RrdQueryException e) {
				ZNMSLogger.error(e);
				continue;
			}
		}
		return JSONArray.fromObject(exportStreamInfoList).toString();
	}
	
	/**
	 * 查找现在用户统计图数据
	 * @return
	 */
	@RequestMapping(value = "/getOnlineUserData", method = RequestMethod.POST)
	@ResponseBody
	public String getOnlineUserData(){
		OnlineUserInfo userInfo = WebRuntimeData.instance.getOnlineUserInfo();
		return JSONObject.fromObject(userInfo).toString();
	}
	
	/**
	 * 查找电脑在线用户数、无线用户数、其他在线用户数
	 * @return
	 */
	@RequestMapping(value = "/getOnlineUserDataByCategory", method = RequestMethod.POST)
	@ResponseBody
	public String getOnlineUserDataByCategory(){
		//查找最近12小时在线用户数据,每小时为间隔
		List<String> timeList = getDateList();
		OnlineUserChartBean bean = new OnlineUserChartBean();
		List<List<Integer>> userCountList = new ArrayList<List<Integer>>();
		List<Integer> wireCountList = new ArrayList<Integer>();
		List<Integer> wirelessCountList = new ArrayList<Integer>();
		List<Integer> otherCountList = new ArrayList<Integer>();
		for(int i=0;i<timeList.size()-1;i++){
			String startTime = timeList.get(i);
			String endTime = timeList.get(i+1);
			Integer wireCount = homePageService.findUserCountByCondition(startTime, endTime, OnlineUserAnalysis.WIRE_TYPE);
			Integer wirelessCount = homePageService.findUserCountByCondition(startTime, endTime, OnlineUserAnalysis.WIRELESS_TYPE);
			Integer otherCount = homePageService.findUserCountByCondition(startTime, endTime, OnlineUserAnalysis.OTHER_TYPE);
			wireCountList.add(wireCount==null ?0:wireCount.intValue());
			wirelessCountList.add(wirelessCount==null ?0:wirelessCount.intValue());
			otherCountList.add(otherCount==null ?0:otherCount.intValue());
		}
		userCountList.add(wireCountList);
		userCountList.add(wirelessCountList);
		userCountList.add(otherCountList);
		bean.setTimeList(timeList);
		bean.setUserCountList(userCountList);
		return JSONObject.fromObject(bean).toString();
	}
	
	/**
	 * 获取最近12小时时间，每次间隔1小时
	 * @return
	 */
	private List<String> getDateList(){
		List<String> timeList = new ArrayList<String>();
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0;i<=12;i++){
			Long time = new Long(System.currentTimeMillis()-3600*1000*(12-i));
			String strDate = sdf.format(time);
			timeList.add(strDate);
		}
	    return timeList;
	}
	
	/**
	 * 获取设备统计数据
	 * @return
	 */
	@RequestMapping(value = "/getDeviceData", method = RequestMethod.POST)
	@ResponseBody
	public String getDeviceData(){
		List<List<Integer>> list = homePageService.getDeviceData();
		return JSONArray.fromObject(list).toString();
	}
	
	/**
	 * 获取网络主要设备监控
	 * @return
	 */
	@RequestMapping(value = "/getMainDeviceMonitorData", method = RequestMethod.POST)
	@ResponseBody
	public String getMainDeviceMonitorData(){
		//查找网络主要设备
		List<Host> hostList = homePageService.getMainDeviceMonitorData();
		return JSONArray.fromObject(hostList).toString();
	}
	
	/**
	 * 获取网络健康数据
	 * @return
	 */
	@RequestMapping(value = "/getNetHeathData", method = RequestMethod.POST)
	@ResponseBody
	public String getNetHeathData(){
		List<Long> list = homePageService.getNetHeathData();
		list.add((long) WebRuntimeData.instance.getNetworkHealth());
		return JSONArray.fromObject(list).toString();
	}
	
	/**
	 * 获取宕机主机数
	 * @return
	 */
	@RequestMapping(value = "/getBadStatusHostCount", method = RequestMethod.POST)
	@ResponseBody
	public long getBadStatusHostCount(){
		return WebRuntimeData.instance.getBadStatusHostCount();
	}
	
	@RequestMapping(value = "/getSystemVersion", method = RequestMethod.POST)
	@ResponseBody
	public String getSystemVersion(){
		return WebRuntimeData.instance.getSystemOptionBean().getSystemVersion();
	}
}
