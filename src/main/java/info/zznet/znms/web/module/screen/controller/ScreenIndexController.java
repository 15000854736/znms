package info.zznet.znms.web.module.screen.controller;

import info.zznet.znms.base.bean.ZlogSystemStatus;
import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.dao.ExportLinkMapper;
import info.zznet.znms.base.entity.ApInformation;
import info.zznet.znms.base.entity.ExportLink;
import info.zznet.znms.base.job.ApInfomationJob;
import info.zznet.znms.base.rrd.bean.Graph;
import info.zznet.znms.base.rrd.bean.SubItem;
import info.zznet.znms.base.rrd.core.RrdFetcher;
import info.zznet.znms.base.rrd.exception.RrdQueryException;
import info.zznet.znms.base.util.EncryptionMD5;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.screen.bean.ExportStreamInfo;
import info.zznet.znms.web.module.screen.bean.HeatData;
import info.zznet.znms.web.module.screen.bean.OnlineUserInfo;
import info.zznet.znms.web.module.screen.bean.UrlRanking;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;
import info.zznet.znms.web.util.ApiClientUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页大屏
 * Created by shenqilei on 2016/11/30.
 */
@Controller
@RequestMapping("/screen/module/index")
public class ScreenIndexController extends BaseController {

    @Autowired
    private ExportLinkMapper exportLinkMapper;

    private WebRuntimeData webRuntimeData = WebRuntimeData.instance;

    @RequestMapping({"","/"})
    @CheckPermission(PermissionConstants.P_SCREEN_VIEW)
    public ModelAndView init(){
        ModelAndView mav = new ModelAndView("/screen/module/index/index");

        mav.addObject("leftUp", getOnlineUserCount());
        try{
            mav.addObject("centerUp", JSONArray.fromObject(getHeatData(webRuntimeData.getSystemOptionBean().getSchoolBg())).toString());
        } catch(Exception e) {
            ZNMSLogger.error(e);
        }
        mav.addObject("rightUp", getNetHealthPoint());
        List<ExportStreamInfo> exportStreamInfoList = getExportStreamInfo();
        mav.addObject("leftDown", exportStreamInfoList);
        Map<String, Object> rightDownMap = getRightDownAreaData();
        mav.addObject("rightDown", rightDownMap);

        mav.addObject("mapImage", webRuntimeData.getSystemOptionBean().getSchoolBg());
        mav.addObject("logoImage", webRuntimeData.getSystemOptionBean().getIndexBg());
        mav.addObject("bgImage", webRuntimeData.getSystemOptionBean().getHomeBg());

        return mav;
    }

    @RequestMapping(value = "/getLeftUpAreaData", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public OnlineUserInfo getLeftUpAreaData(){
        return getOnlineUserCount();
    }

    @RequestMapping(value = "/getRightUpAreaData", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public int getRightUpAreaData(){
        return getNetHealthPoint();
    }

    @RequestMapping(value = "/getCenterUpAreaData", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<HeatData> getCenterUpAreaData(){
        try {
            String mapImage =  new SystemOptionBean().getSchoolBg();
            return getHeatData(mapImage);
        } catch(Exception e) {
            ZNMSLogger.error(e);
            return new ArrayList<HeatData>();
        }
    }

    @RequestMapping(value = "/getRightDownAreaData", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> getRightDownAreaData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("urlRankingData", getUrlRanking());

        ConcurrentLinkedQueue<ZlogSystemStatus> zlogSystemStatusData = webRuntimeData.getZlogSystemStatusList();
        List<String> zlogSystemStatusXaxis = getXaxis(0, 5, 12, "yyyy-MM-dd HH:mm");
        map.put("systemStatusData", zlogSystemStatusData);
        map.put("systemStatusXaxis", zlogSystemStatusXaxis);

        List<Long> natLogCountData = webRuntimeData.getZlogNatlogCountList();
        List<Long> urlLogCountData = webRuntimeData.getZlogUrlLogCountList();
        List<String> zlogLogCountXaxis = new ArrayList<String>();
        for(int i=8;i>0;i--){
            zlogLogCountXaxis.add(i+"小时前");
        }
        List<Long> _natLogCountData = new ArrayList<Long>();
        List<Long> _urlLogCountData = new ArrayList<Long>();
        if(natLogCountData != null && urlLogCountData != null) {
            for(int i=7;i>=0;i--){
                _natLogCountData.add(natLogCountData.get(i));
                _urlLogCountData.add(urlLogCountData.get(i));
            }
        }

        map.put("natLogCountData", _natLogCountData);
        map.put("urlLogCountData", _urlLogCountData);
        map.put("logCountXaxis", zlogLogCountXaxis);

        return map;
    }

    @RequestMapping(value = "/getLeftDownAreaData", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<ExportStreamInfo> getLeftDownAreaData() {
        return getExportStreamInfo();
    }
    
    @RequestMapping(value = "/getFlowData", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getFlowData(){
    	try {
   
			/*Map<String,String> paramMap=new HashMap<String, String>();
			paramMap.put("size", "10");
			paramMap.put("interval", "1");
			String zlogIp=webRuntimeData.getSystemOptionBean().getZlogIp();
			String zlogPort=webRuntimeData.getSystemOptionBean().getZlogPort();
			
			ApiClientUtil apiClientUtil=new ApiClientUtil("http://"+zlogIp+":"+zlogPort+"/z-log/api/rest/getFlowData");
			String zlogresponse=apiClientUtil.post(paramMap);*/
			
			String zlogresponse="{\"wirelessFlow\":6.53,\"wireFlow\":4.98,\"wirelessFlowTrend\":{\"1481520840000\":{\"count\":0,\"fCount\":100,\"time\":\"2016-12-12 13:34\",\"timestamp\":1481520840000},\"1481519280000\":{\"count\":0,\"fCount\":6,\"time\":\"2016-12-12 13:08\",\"timestamp\":1481519280000},\"1481519880000\":{\"count\":9,\"fCount\":0,\"time\":\"2016-12-12 13:18\",\"timestamp\":1481519880000},\"1481519700000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:15\",\"timestamp\":1481519700000},\"1481522100000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:55\",\"timestamp\":1481522100000},\"1481518980000\":{\"count\":0,\"fCount\":12,\"time\":\"2016-12-12 13:03\",\"timestamp\":1481518980000},\"1481521560000\":{\"count\":0,\"fCount\":12.5,\"time\":\"2016-12-12 13:46\",\"timestamp\":1481521560000},\"1481520240000\":{\"count\":0,\"fCount\":13,\"time\":\"2016-12-12 13:24\",\"timestamp\":1481520240000},\"1481519460000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:11\",\"timestamp\":1481519460000},\"1481519520000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:12\",\"timestamp\":1481519520000},\"1481521800000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:50\",\"timestamp\":1481521800000},\"1481521980000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:53\",\"timestamp\":1481521980000},\"1481520660000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:31\",\"timestamp\":1481520660000},\"1481518800000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:00\",\"timestamp\":1481518800000},\"1481521380000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:43\",\"timestamp\":1481521380000},\"1481520060000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:21\",\"timestamp\":1481520060000},\"1481521200000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:40\",\"timestamp\":1481521200000},\"1481520600000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:30\",\"timestamp\":1481520600000},\"1481518620000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 12:57\",\"timestamp\":1481518620000},\"1481520900000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:35\",\"timestamp\":1481520900000},\"1481519340000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:09\",\"timestamp\":1481519340000},\"1481520000000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:20\",\"timestamp\":1481520000000},\"1481518920000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:02\",\"timestamp\":1481518920000},\"1481522040000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:54\",\"timestamp\":1481522040000},\"1481519040000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:04\",\"timestamp\":1481519040000},\"1481520300000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:25\",\"timestamp\":1481520300000},\"1481518680000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 12:58\",\"timestamp\":1481518680000},\"1481520720000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:32\",\"timestamp\":1481520720000},\"1481519220000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:07\",\"timestamp\":1481519220000},\"1481518740000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 12:59\",\"timestamp\":1481518740000},\"1481520420000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:27\",\"timestamp\":1481520420000},\"1481522160000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:56\",\"timestamp\":1481522160000},\"1481520120000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:22\",\"timestamp\":1481520120000},\"1481518860000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:01\",\"timestamp\":1481518860000},\"1481519100000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:05\",\"timestamp\":1481519100000},\"1481520540000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:29\",\"timestamp\":1481520540000},\"1481519400000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:10\",\"timestamp\":1481519400000},\"1481521860000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:51\",\"timestamp\":1481521860000},\"1481521260000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:41\",\"timestamp\":1481521260000},\"1481519580000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:13\",\"timestamp\":1481519580000},\"1481520960000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:36\",\"timestamp\":1481520960000},\"1481519760000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:16\",\"timestamp\":1481519760000},\"1481521500000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:45\",\"timestamp\":1481521500000},\"1481519160000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:06\",\"timestamp\":1481519160000},\"1481521680000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:48\",\"timestamp\":1481521680000},\"1481519820000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:17\",\"timestamp\":1481519820000},\"1481520360000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:26\",\"timestamp\":1481520360000},\"1481519640000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:14\",\"timestamp\":1481519640000},\"1481522220000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:57\",\"timestamp\":1481522220000},\"1481519940000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:19\",\"timestamp\":1481519940000},\"1481520480000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:28\",\"timestamp\":1481520480000},\"1481521320000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:42\",\"timestamp\":1481521320000},\"1481521920000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:52\",\"timestamp\":1481521920000},\"1481520180000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:23\",\"timestamp\":1481520180000},\"1481521620000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:47\",\"timestamp\":1481521620000},\"1481521080000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:38\",\"timestamp\":1481521080000},\"1481521020000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:37\",\"timestamp\":1481521020000},\"1481520780000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:33\",\"timestamp\":1481520780000},\"1481521740000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:49\",\"timestamp\":1481521740000},\"1481521140000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:39\",\"timestamp\":1481521140000},\"1481521440000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:44\",\"timestamp\":1481521440000}},\"pcFlowTrend\":{\"1481520840000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:34\",\"timestamp\":1481520840000},\"1481519280000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:08\",\"timestamp\":1481519280000},\"1481519880000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:18\",\"timestamp\":1481519880000},\"1481519700000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:15\",\"timestamp\":1481519700000},\"1481522100000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:55\",\"timestamp\":1481522100000},\"1481518980000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:03\",\"timestamp\":1481518980000},\"1481521560000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:46\",\"timestamp\":1481521560000},\"1481520240000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:24\",\"timestamp\":1481520240000},\"1481519460000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:11\",\"timestamp\":1481519460000},\"1481519520000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:12\",\"timestamp\":1481519520000},\"1481521800000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:50\",\"timestamp\":1481521800000},\"1481521980000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:53\",\"timestamp\":1481521980000},\"1481520660000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:31\",\"timestamp\":1481520660000},\"1481518800000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:00\",\"timestamp\":1481518800000},\"1481521380000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:43\",\"timestamp\":1481521380000},\"1481520060000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:21\",\"timestamp\":1481520060000},\"1481521200000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:40\",\"timestamp\":1481521200000},\"1481520600000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:30\",\"timestamp\":1481520600000},\"1481518620000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 12:57\",\"timestamp\":1481518620000},\"1481520900000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:35\",\"timestamp\":1481520900000},\"1481519340000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:09\",\"timestamp\":1481519340000},\"1481520000000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:20\",\"timestamp\":1481520000000},\"1481518920000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:02\",\"timestamp\":1481518920000},\"1481522040000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:54\",\"timestamp\":1481522040000},\"1481519040000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:04\",\"timestamp\":1481519040000},\"1481520300000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:25\",\"timestamp\":1481520300000},\"1481518680000\":{\"count\":0,\"fCount\":3,\"time\":\"2016-12-12 12:58\",\"timestamp\":1481518680000},\"1481520720000\":{\"count\":0,\"fCount\":7,\"time\":\"2016-12-12 13:32\",\"timestamp\":1481520720000},\"1481519220000\":{\"count\":0,\"fCount\":8,\"time\":\"2016-12-12 13:07\",\"timestamp\":1481519220000},\"1481518740000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 12:59\",\"timestamp\":1481518740000},\"1481520420000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:27\",\"timestamp\":1481520420000},\"1481522160000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:56\",\"timestamp\":1481522160000},\"1481520120000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:22\",\"timestamp\":1481520120000},\"1481518860000\":{\"count\":0,\"fCount\":11,\"time\":\"2016-12-12 13:01\",\"timestamp\":1481518860000},\"1481519100000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:05\",\"timestamp\":1481519100000},\"1481520540000\":{\"count\":0,\"fCount\":7.6,\"time\":\"2016-12-12 13:29\",\"timestamp\":1481520540000},\"1481519400000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:10\",\"timestamp\":1481519400000},\"1481521860000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:51\",\"timestamp\":1481521860000},\"1481521260000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:41\",\"timestamp\":1481521260000},\"1481519580000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:13\",\"timestamp\":1481519580000},\"1481520960000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:36\",\"timestamp\":1481520960000},\"1481519760000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:16\",\"timestamp\":1481519760000},\"1481521500000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:45\",\"timestamp\":1481521500000},\"1481519160000\":{\"count\":0,\"fCount\":5,\"time\":\"2016-12-12 13:06\",\"timestamp\":1481519160000},\"1481521680000\":{\"count\":0,\"fCount\":10,\"time\":\"2016-12-12 13:48\",\"timestamp\":1481521680000},\"1481519820000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:17\",\"timestamp\":1481519820000},\"1481520360000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:26\",\"timestamp\":1481520360000},\"1481519640000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:14\",\"timestamp\":1481519640000},\"1481522220000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:57\",\"timestamp\":1481522220000},\"1481519940000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:19\",\"timestamp\":1481519940000},\"1481520480000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:28\",\"timestamp\":1481520480000},\"1481521320000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:42\",\"timestamp\":1481521320000},\"1481521920000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:52\",\"timestamp\":1481521920000},\"1481520180000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:23\",\"timestamp\":1481520180000},\"1481521620000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:47\",\"timestamp\":1481521620000},\"1481521080000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:38\",\"timestamp\":1481521080000},\"1481521020000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:37\",\"timestamp\":1481521020000},\"1481520780000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:33\",\"timestamp\":1481520780000},\"1481521740000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:49\",\"timestamp\":1481521740000},\"1481521140000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:39\",\"timestamp\":1481521140000},\"1481521440000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:44\",\"timestamp\":1481521440000}},\"terminalType\":{\"-1\":0},\"userGroupCount\":{\"root\":0,\"学生组\":0,\"老师组\":0,\"游客组\":0,\"管理员组\":0,\"大一组\":0,\"大二组\":0,\"大三组\":0,\"大四组\":0}}";
//			String zlogresponse="{\"wirelessFlow\":6.53,\"wireFlow\":4.98,\"wirelessFlowTrend\":{\"1481520840000\":{\"count\":0,\"fCount\":100,\"time\":\"2016-12-12 13:34\",\"timestamp\":1481520840000},\"1481519280000\":{\"count\":0,\"fCount\":6,\"time\":\"2016-12-12 13:08\",\"timestamp\":1481519280000},\"1481519880000\":{\"count\":9,\"fCount\":0,\"time\":\"2016-12-12 13:18\",\"timestamp\":1481519880000},\"1481519700000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:15\",\"timestamp\":1481519700000},\"1481522100000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:55\",\"timestamp\":1481522100000},\"1481518980000\":{\"count\":0,\"fCount\":12,\"time\":\"2016-12-12 13:03\",\"timestamp\":1481518980000},\"1481521560000\":{\"count\":0,\"fCount\":12.5,\"time\":\"2016-12-12 13:46\",\"timestamp\":1481521560000},\"1481520240000\":{\"count\":0,\"fCount\":13,\"time\":\"2016-12-12 13:24\",\"timestamp\":1481520240000},\"1481519460000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:11\",\"timestamp\":1481519460000},\"1481519520000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:12\",\"timestamp\":1481519520000},\"1481521800000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:50\",\"timestamp\":1481521800000},\"1481521980000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:53\",\"timestamp\":1481521980000},\"1481520660000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:31\",\"timestamp\":1481520660000},\"1481518800000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:00\",\"timestamp\":1481518800000},\"1481521380000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:43\",\"timestamp\":1481521380000},\"1481520060000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:21\",\"timestamp\":1481520060000},\"1481521200000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:40\",\"timestamp\":1481521200000},\"1481520600000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:30\",\"timestamp\":1481520600000},\"1481518620000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 12:57\",\"timestamp\":1481518620000},\"1481520900000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:35\",\"timestamp\":1481520900000},\"1481519340000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:09\",\"timestamp\":1481519340000},\"1481520000000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:20\",\"timestamp\":1481520000000},\"1481518920000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:02\",\"timestamp\":1481518920000},\"1481522040000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:54\",\"timestamp\":1481522040000},\"1481519040000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:04\",\"timestamp\":1481519040000},\"1481520300000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:25\",\"timestamp\":1481520300000},\"1481518680000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 12:58\",\"timestamp\":1481518680000},\"1481520720000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:32\",\"timestamp\":1481520720000},\"1481519220000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:07\",\"timestamp\":1481519220000},\"1481518740000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 12:59\",\"timestamp\":1481518740000},\"1481520420000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:27\",\"timestamp\":1481520420000},\"1481522160000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:56\",\"timestamp\":1481522160000},\"1481520120000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:22\",\"timestamp\":1481520120000},\"1481518860000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:01\",\"timestamp\":1481518860000},\"1481519100000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:05\",\"timestamp\":1481519100000},\"1481520540000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:29\",\"timestamp\":1481520540000},\"1481519400000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:10\",\"timestamp\":1481519400000},\"1481521860000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:51\",\"timestamp\":1481521860000},\"1481521260000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:41\",\"timestamp\":1481521260000},\"1481519580000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:13\",\"timestamp\":1481519580000},\"1481520960000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:36\",\"timestamp\":1481520960000},\"1481519760000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:16\",\"timestamp\":1481519760000},\"1481521500000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:45\",\"timestamp\":1481521500000},\"1481519160000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:06\",\"timestamp\":1481519160000},\"1481521680000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:48\",\"timestamp\":1481521680000},\"1481519820000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:17\",\"timestamp\":1481519820000},\"1481520360000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:26\",\"timestamp\":1481520360000},\"1481519640000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:14\",\"timestamp\":1481519640000},\"1481522220000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:57\",\"timestamp\":1481522220000},\"1481519940000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:19\",\"timestamp\":1481519940000},\"1481520480000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:28\",\"timestamp\":1481520480000},\"1481521320000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:42\",\"timestamp\":1481521320000},\"1481521920000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:52\",\"timestamp\":1481521920000},\"1481520180000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:23\",\"timestamp\":1481520180000},\"1481521620000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:47\",\"timestamp\":1481521620000},\"1481521080000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:38\",\"timestamp\":1481521080000},\"1481521020000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:37\",\"timestamp\":1481521020000},\"1481520780000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:33\",\"timestamp\":1481520780000},\"1481521740000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:49\",\"timestamp\":1481521740000},\"1481521140000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:39\",\"timestamp\":1481521140000},\"1481521440000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:44\",\"timestamp\":1481521440000}},\"pcFlowTrend\":{\"1481520840000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:34\",\"timestamp\":1481520840000},\"1481519280000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:08\",\"timestamp\":1481519280000},\"1481519880000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:18\",\"timestamp\":1481519880000},\"1481519700000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:15\",\"timestamp\":1481519700000},\"1481522100000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:55\",\"timestamp\":1481522100000},\"1481518980000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:03\",\"timestamp\":1481518980000},\"1481521560000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:46\",\"timestamp\":1481521560000},\"1481520240000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:24\",\"timestamp\":1481520240000},\"1481519460000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:11\",\"timestamp\":1481519460000},\"1481519520000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:12\",\"timestamp\":1481519520000},\"1481521800000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:50\",\"timestamp\":1481521800000},\"1481521980000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:53\",\"timestamp\":1481521980000},\"1481520660000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:31\",\"timestamp\":1481520660000},\"1481518800000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:00\",\"timestamp\":1481518800000},\"1481521380000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:43\",\"timestamp\":1481521380000},\"1481520060000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:21\",\"timestamp\":1481520060000},\"1481521200000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:40\",\"timestamp\":1481521200000},\"1481520600000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:30\",\"timestamp\":1481520600000},\"1481518620000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 12:57\",\"timestamp\":1481518620000},\"1481520900000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:35\",\"timestamp\":1481520900000},\"1481519340000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:09\",\"timestamp\":1481519340000},\"1481520000000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:20\",\"timestamp\":1481520000000},\"1481518920000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:02\",\"timestamp\":1481518920000},\"1481522040000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:54\",\"timestamp\":1481522040000},\"1481519040000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:04\",\"timestamp\":1481519040000},\"1481520300000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:25\",\"timestamp\":1481520300000},\"1481518680000\":{\"count\":0,\"fCount\":3,\"time\":\"2016-12-12 12:58\",\"timestamp\":1481518680000},\"1481520720000\":{\"count\":0,\"fCount\":7,\"time\":\"2016-12-12 13:32\",\"timestamp\":1481520720000},\"1481519220000\":{\"count\":0,\"fCount\":8,\"time\":\"2016-12-12 13:07\",\"timestamp\":1481519220000},\"1481518740000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 12:59\",\"timestamp\":1481518740000},\"1481520420000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:27\",\"timestamp\":1481520420000},\"1481522160000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:56\",\"timestamp\":1481522160000},\"1481520120000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:22\",\"timestamp\":1481520120000},\"1481518860000\":{\"count\":0,\"fCount\":11,\"time\":\"2016-12-12 13:01\",\"timestamp\":1481518860000},\"1481519100000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:05\",\"timestamp\":1481519100000},\"1481520540000\":{\"count\":0,\"fCount\":7.6,\"time\":\"2016-12-12 13:29\",\"timestamp\":1481520540000},\"1481519400000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:10\",\"timestamp\":1481519400000},\"1481521860000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:51\",\"timestamp\":1481521860000},\"1481521260000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:41\",\"timestamp\":1481521260000},\"1481519580000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:13\",\"timestamp\":1481519580000},\"1481520960000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:36\",\"timestamp\":1481520960000},\"1481519760000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:16\",\"timestamp\":1481519760000},\"1481521500000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:45\",\"timestamp\":1481521500000},\"1481519160000\":{\"count\":0,\"fCount\":5,\"time\":\"2016-12-12 13:06\",\"timestamp\":1481519160000},\"1481521680000\":{\"count\":0,\"fCount\":10,\"time\":\"2016-12-12 13:48\",\"timestamp\":1481521680000},\"1481519820000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:17\",\"timestamp\":1481519820000},\"1481520360000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:26\",\"timestamp\":1481520360000},\"1481519640000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:14\",\"timestamp\":1481519640000},\"1481522220000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:57\",\"timestamp\":1481522220000},\"1481519940000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:19\",\"timestamp\":1481519940000},\"1481520480000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:28\",\"timestamp\":1481520480000},\"1481521320000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:42\",\"timestamp\":1481521320000},\"1481521920000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:52\",\"timestamp\":1481521920000},\"1481520180000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:23\",\"timestamp\":1481520180000},\"1481521620000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:47\",\"timestamp\":1481521620000},\"1481521080000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:38\",\"timestamp\":1481521080000},\"1481521020000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:37\",\"timestamp\":1481521020000},\"1481520780000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:33\",\"timestamp\":1481520780000},\"1481521740000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:49\",\"timestamp\":1481521740000},\"1481521140000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:39\",\"timestamp\":1481521140000},\"1481521440000\":{\"count\":0,\"fCount\":0,\"time\":\"2016-12-12 13:44\",\"timestamp\":1481521440000}},\"terminalType\":{\"-1\":0},\"userGroupCount\":{\"root\":0}}";
			
    		//String zlogresponse=webRuntimeData.getFlowData();
			JSONObject zlogJsonObject=JSONObject.fromObject(zlogresponse);
			Map<String, Object> textMap = new HashMap<String, Object>();
			
			textMap.put("wireFlow", zlogJsonObject.get("wireFlow"));
			textMap.put("wirelessFlow", zlogJsonObject.get("wirelessFlow"));
			
			double wirelessFlow=0; 
			JSONObject wirelessFlowTrend=JSONObject.fromObject(zlogJsonObject.get("wirelessFlowTrend"));
			Iterator wirelessFlowIt=wirelessFlowTrend.keys();
			List<Map.Entry<String,String>> wirelessFlowList = null; 
			List timeAxisList=new ArrayList();
			Map<String,String> map=new TreeMap<String, String>();
			while(wirelessFlowIt.hasNext()){
				String key=(String)wirelessFlowIt.next();
				String value=wirelessFlowTrend.getString(key);
				JSONObject datas=JSONObject.fromObject(value);
				timeAxisList.add(datas.getString("time"));
				wirelessFlow+=datas.getDouble("fCount");
				map.put(datas.getString("time"), datas.getString("fCount"));
			}
			 Collections.sort(timeAxisList);
			wirelessFlowList = new ArrayList<Map.Entry<String,String>>(map.entrySet()); 

			Collections.sort(wirelessFlowList, new Comparator<Map.Entry<String,String>>(){ 
			public int compare(Map.Entry<String,String> mapping1,Map.Entry<String,String> mapping2){ 
			   return mapping1.getKey().compareTo(mapping2.getKey()); 
			  } 
			}); 
			
			double wireFlow=0;
			JSONObject pcFlowTrend=JSONObject.fromObject(zlogJsonObject.get("pcFlowTrend"));
			Iterator pcFlowIt=pcFlowTrend.keys();
			List<Map.Entry<String,String>> pcFlowList = null; 
			Map<String,String> pcMap=new TreeMap<String, String>();
			while(pcFlowIt.hasNext()){
				String key=(String)pcFlowIt.next();
				String value=pcFlowTrend.getString(key);
				JSONObject datas=JSONObject.fromObject(value);
				wireFlow+=datas.getDouble("fCount");
				pcMap.put(datas.getString("time"), datas.getString("fCount"));
			}
			
			pcFlowList = new ArrayList<Map.Entry<String,String>>(pcMap.entrySet()); 
			
			Collections.sort(pcFlowList, new Comparator<Map.Entry<String,String>>(){ 
			public int compare(Map.Entry<String,String> mapping1,Map.Entry<String,String> mapping2){ 
			   return mapping1.getKey().compareTo(mapping2.getKey()); 
			  } 
			}); 
			
			textMap.put("wirelessFlowTrend", JSONArray.fromObject(wirelessFlowList));
			textMap.put("pcFlowTrend", JSONArray.fromObject(pcFlowList));
			
			textMap.put("wireFlow", wirelessFlow);
			textMap.put("wirelessFlow", wireFlow);
			
			double flowCount=wirelessFlow+wireFlow;
			textMap.put("flowCount", flowCount);
			
			JSONObject userGroupFlow=JSONObject.fromObject(zlogJsonObject.get("userGroupCount"));
			textMap.put("userGroupFlow", userGroupFlow);
			
			textMap.put("timeAxis", JSONArray.fromObject(timeAxisList));
			
			double userGroupFlowCount=0;
			Iterator userGroupIterator = userGroupFlow.keys();
			while (userGroupIterator.hasNext()) {
			  String key = (String) userGroupIterator.next();
			  double value = userGroupFlow.getDouble(key); 
			  userGroupFlowCount+=value;
			}
			textMap.put("userGroupFlowCount", userGroupFlowCount);
			return JSONArray.fromObject(textMap).toString();
		} catch (Exception e) {
			ZNMSLogger.error("Failed to get data from zos,"+e.getMessage());
		}
    	return null;
    }
    
    
    private Map<String, Object> getUrlRanking(){
        List<UrlRanking> urlRankingList = webRuntimeData.getUrlRankingList();
        List<UrlRanking> _urlRankingList = new ArrayList<UrlRanking>();
        long total = 0l;
        if(urlRankingList != null) {
            int i=0;
            for(UrlRanking urlRanking : urlRankingList) {
                if(i>=5){
                    break;
                }
                total += urlRanking.getCount();
                _urlRankingList.add(urlRanking);
                i++;
            }
        }
        Map<String, Object> retVal = new HashMap<String, Object>();
        retVal.put("urlRankingList", _urlRankingList);
        retVal.put("total", total);
        return retVal;
    }

    /**
     * 获取网络健康指数
     * @return
     */
    private int getNetHealthPoint(){
        return webRuntimeData.getNetworkHealth();
    }

    /**
     * 获取在线数
     * @return
     */
    private OnlineUserInfo getOnlineUserCount(){
        return webRuntimeData.getOnlineUserInfo();
    }

    private List<HeatData> getHeatData(String mapFileName){
        ZNMSLogger.debug("begin get heat data");
        List<HeatData> list = new ArrayList<HeatData>();
        Map<String, ApInformation> apInfoMap = ApInfomationJob.apInfomationMap;
        ZNMSLogger.debug("apMap size:"+apInfoMap.size());
        if(apInfoMap != null) {

            for(String key : apInfoMap.keySet()) {
                ApInformation apInfo = apInfoMap.get(key);
                String axis = apInfo.getApAxis();
                //ZNMSLogger.debug("ap:"+apInfo.getApMac()+",axis:"+axis);
                if(!StringUtils.contains(axis, ",")) {
                    continue;
                }
                BigDecimal absoluteX = new BigDecimal(axis.split(",")[0]);
                BigDecimal absoluteY = new BigDecimal(axis.split(",")[1]);
                //ZNMSLogger.debug("absoluteX:"+absoluteX+", absoluteY:"+absoluteY);
				/*BigDecimal x = absoluteX.divide(new BigDecimal(width), 8, BigDecimal.ROUND_HALF_UP);
				BigDecimal y = absoluteY.divide(new BigDecimal(height), 8, BigDecimal.ROUND_HALF_UP);*/

                BigDecimal x = absoluteX;
                BigDecimal y = absoluteY;
                HeatData heatData = new HeatData();
                heatData.setValue(new BigDecimal(apInfo.getApUserCount()));
                heatData.setX(x);
                heatData.setY(y);
                //ZNMSLogger.debug("heat data:("+x+","+y+")"+heatData.getValue());
                if(x.compareTo(BigDecimal.ONE)>0) {
                    //ZNMSLogger.warn("heat data x value larger than 1" + x);
                }
                if(y.compareTo(BigDecimal.ONE)>0) {
                    //ZNMSLogger.warn("heat data y value larger than 1" + y);
                }
                list.add(heatData);
            }
        }
        return list;
    }

    /**
     * 获取出口流量信息
     * @return
     */
    private List<ExportStreamInfo> getExportStreamInfo(){
        List<ExportStreamInfo> exportStreamInfoList = new ArrayList<ExportStreamInfo>();

        List<ExportLink> exportLinkList = exportLinkMapper.findLatestThree();
        if(exportLinkList == null) {
            return exportStreamInfoList;
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
                if(graph == null) {
                    continue;
                }
                ExportStreamInfo exportStreamInfo = new ExportStreamInfo();
                exportStreamInfo.setDivId(EncryptionMD5.getMD5(exportLink.getExportLinkDescription()));
                exportStreamInfo.setGraph(graph);
                exportStreamInfo.setName(exportLink.getExportLinkDescription());
                exportStreamInfo.setMaxBandWidth(exportLink.getMaxBandWidth());
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

                exportStreamInfoList.add(exportStreamInfo);
            } catch (RrdQueryException e) {
                ZNMSLogger.error(e);
                continue;
            }
        }
        return exportStreamInfoList;
    }

    /**
     * 获取时间轴
     * @param offset 起始偏移量
     * @param seperate 间隔（分钟）
     * @param total 总数
     * @param format 格式
     * @return
     */
    private List<String> getXaxis(int offset, int seperate, int total, String format){
        List<String> xAxisList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.MINUTE, (-seperate * total) + (-offset * seperate));
        for(int i = 0;i<total;i++){
            calendar.add(Calendar.MINUTE, seperate);
            xAxisList.add(sdf.format(calendar.getTime()));
        }
        return xAxisList;
    }
    
    
}
