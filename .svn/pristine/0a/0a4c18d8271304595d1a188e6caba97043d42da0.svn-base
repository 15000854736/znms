package info.zznet.znms.web.module.screen.controller;

import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.spider.Engine;
import info.zznet.znms.spider.bean.ScanHost;
import info.zznet.znms.spider.constants.DeviceStarColorMap;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.screen.bean.HeatData;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;
import info.zznet.znms.web.util.ConfigUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 设备星光图
 * Created by shenqilei on 2016/11/30.
 */
@Controller
@RequestMapping("/screen/module/deviceStarMap")
public class ScreenDeviceStarMapController extends BaseController {

    private WebRuntimeData webRuntimeData = WebRuntimeData.instance;
    
    SystemOptionBean systemOptionBean;
    
    @RequestMapping({"","/"})
    public ModelAndView init(){
        ModelAndView mav = new ModelAndView("/screen/module/deviceStarMap/index");

        Integer[] starMapBgSize = getStarMapBgSize();
        mav.addObject("starMapBgWidth",starMapBgSize[0]);
        mav.addObject("starMapBgHeight",starMapBgSize[1]);
        mav.addObject("logoHeight",getScreenLogoSize()[1]);
        mav.addObject("schoolBg",webRuntimeData.getSystemOptionBean().getSchoolBg());

        return mav;
    }
    
    /**
     * 获取主机信息
     * @return
     */
    @RequestMapping(value = "/getDeviceData", method = RequestMethod.POST)
    @ResponseBody
    public String getHeatData(){
    	Map<String, List<HeatData>> dataMap = new LinkedHashMap<String, List<HeatData>>();
    	Map<Integer, Map<String, Long>> valueMap = new LinkedHashMap<Integer, Map<String, Long>>();
        Map<String,ScanHost> map = Engine.hosts;
        if(map != null) {
            for(String key : map.keySet()) {
        		//获取星光图右侧设备在线宕机数据
            	ScanHost host = map.get(key);
        		int type = host.getType();
        		Map<String,Long> countMap = new LinkedHashMap<String, Long>();
        		if(valueMap.containsKey(type)){
            		//计算这种类型设备在线和宕机总数
        			countMap = valueMap.get(type);
            	}
        		if(host.isReachable()){
    				//在线
    				if(null != countMap.get("up")){
    					countMap.put("up", countMap.get("up")+1);
    				}else{
    					countMap.put("up", 1l);
    				}
    			}else{
    				if(null != countMap.get("down")){
    					countMap.put("down", countMap.get("down")+1);
    				}else{
    					countMap.put("down", 1l);
    				}
    			}
        		valueMap.put(type, countMap);
        		
            	//获取星光图上点数据
            	 String axis = host.getHostAxis();
            	 if(!StringUtil.isNullString(axis)){
            		 List<HeatData> dataList = new ArrayList<HeatData>();
        			 BigDecimal absoluteX = new BigDecimal(axis.split(",")[0]);
                     BigDecimal absoluteY = new BigDecimal(axis.split(",")[1]);

                     BigDecimal x = absoluteX;
                     BigDecimal y = absoluteY;
                     HeatData heatData = new HeatData();
                     heatData.setX(x);
                     heatData.setY(y);
                     String mapkey = "";
                     if(host.isReachable()){
                    	 mapkey = DeviceStarColorMap.getColorValueByKey(host.getType());
                     }else{
                    	 //星光图上宕机设备的颜色
                    	 mapkey = DeviceStarColorMap.DOWN_COLOR;
                     }
                   	if(dataMap.containsKey(mapkey)){
                   		//包含这种颜色
                   		dataList = dataMap.get(mapkey);
                   	}
                   	dataList.add(heatData);
               		dataMap.put(mapkey, dataList);
            	 }
            }
            for(int i=1;i<=6;i++){
            	if(!valueMap.containsKey(i)){
            		Map<String, Long> typeMap = new LinkedHashMap<String, Long>();
            		typeMap.put("up", 0l);
            		typeMap.put("down", 0l);
            		valueMap.put(i, typeMap);
            	}
            }
        }else{
        	//无主机数据时，各类型设备在线和宕机数都置为0
        	for(int i=1;i<=6;i++){
        		Map<String, Long> typeMap = new LinkedHashMap<String, Long>();
        		typeMap.put("up", 0l);
        		typeMap.put("down", 0l);
        		valueMap.put(i, typeMap);
        	}
        }
        List list = new ArrayList();
        list.add(dataMap);
        list.add(valueMap);
        return JSONArray.fromObject(list).toString();
    }

    private Integer[] getStarMapBgSize(){
        Integer[] result = new Integer[2];
        BufferedImage bufferedImage = null;
        try {
            File imageFile = new File(ConfigUtil.getString("znms.image.path") + webRuntimeData.getSystemOptionBean().getSchoolBg());
            if(!imageFile.exists() || !imageFile.isFile()) {
                return result;
            }
            imageFile.lastModified();
            bufferedImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            ZNMSLogger.error(e);
            return result;
        }
        result[0] = bufferedImage.getWidth();
        result[1] = bufferedImage.getHeight();
        return result;
    }


    private Integer[] getScreenLogoSize(){
        Integer[] result = new Integer[2];
        BufferedImage bufferedImage = null;
        try {
            File imageFile = new File(ConfigUtil.getString("znms.image.path") + WebRuntimeData.instance.getSystemOptionBean().getIndexBg());
            if(!imageFile.exists() || !imageFile.isFile()) {
                return result;
            }
            imageFile.lastModified();
            bufferedImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            ZNMSLogger.error(e);
            return result;
        }
        result[0] = bufferedImage.getWidth();
        result[1] = bufferedImage.getHeight();
        return result;
    }
    
}
