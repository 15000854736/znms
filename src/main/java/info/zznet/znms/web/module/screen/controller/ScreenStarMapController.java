package info.zznet.znms.web.module.screen.controller;

import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.entity.ApInformation;
import info.zznet.znms.base.job.ApInfomationJob;
import info.zznet.znms.base.util.HttpClientUtil;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.screen.bean.HeatData;
import info.zznet.znms.web.module.screen.bean.OnlineUserInfo;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;
import info.zznet.znms.web.util.ConfigUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 星光图
 * Created by shenqilei on 2016/11/30.
 */
@Controller
@RequestMapping("/screen/module/starMap")
public class ScreenStarMapController extends BaseController {

    private WebRuntimeData webRuntimeData = WebRuntimeData.instance;
    
    SystemOptionBean systemOptionBean;
    
    @RequestMapping({"","/"})
    @CheckPermission(PermissionConstants.P_SCREEN_VIEW)
    public ModelAndView init(){
        ModelAndView mav = new ModelAndView("/screen/module/starMap/index");

        Integer[] starMapBgSize = getStarMapBgSize();
        mav.addObject("starMapBgWidth",starMapBgSize[0]);
        mav.addObject("starMapBgHeight",starMapBgSize[1]);
        mav.addObject("radius",webRuntimeData.getSystemOptionBean().getRadius());
        mav.addObject("countScale",webRuntimeData.getSystemOptionBean().getPoint());
        mav.addObject("pointSize",webRuntimeData.getSystemOptionBean().getPointSize());
        mav.addObject("logoHeight",getScreenLogoSize()[1]);
        mav.addObject("schoolBg",webRuntimeData.getSystemOptionBean().getSchoolBg());

        return mav;
    }
    
    @RequestMapping(value = "/getApData", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<HeatData> getHeatData(String mapFileName){
        List<HeatData> list = new ArrayList<HeatData>();
        Map<String, ApInformation> apInfoMap = ApInfomationJob.apInfomationMap;
        if(apInfoMap != null) {

            for(String key : apInfoMap.keySet()) {
                ApInformation apInfo = apInfoMap.get(key);
                String axis = apInfo.getApAxis();
                if(!StringUtils.contains(axis, ",")) {
                    continue;
                }
                BigDecimal absoluteX = new BigDecimal(axis.split(",")[0]);
                BigDecimal absoluteY = new BigDecimal(axis.split(",")[1]);

                BigDecimal x = absoluteX;
                BigDecimal y = absoluteY;
                HeatData heatData = new HeatData();
                heatData.setValue(new BigDecimal(apInfo.getApUserCount()));
                heatData.setX(x);
                heatData.setY(y);
                if(x.compareTo(BigDecimal.ONE)>0) {
                }
                if(y.compareTo(BigDecimal.ONE)>0) {
                }
                list.add(heatData);
            }
        }
        return list;
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
    
    
    @RequestMapping(value = "/getRightUpAreaData", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getRightUpAreaData(){
    	OnlineUserInfo onlineUserInfo =getOnlineUserCount();

    	Map<String, Object> textMap = new HashMap<String, Object>();
    	textMap.put("apTotalOnlineNumber", webRuntimeData.getApTotalOnlineNumber());
    	textMap.put("apOnlineCount", webRuntimeData.getApCount());
    	if(onlineUserInfo!=null){
    		textMap.put("totalOnlineUserCount", onlineUserInfo.getTotalOnlineUserCount());
    		textMap.put("wirelessUserCount", onlineUserInfo.getWirelessUserCount());
    		textMap.put("wireUserCount", onlineUserInfo.getWireUserCount());
    		textMap.put("wireless1X", onlineUserInfo.getWireless1X());
			textMap.put("wirelessPortal",onlineUserInfo.getWirelessPortal());
			textMap.put("ios", onlineUserInfo.getIos());
			textMap.put("pc", onlineUserInfo.getPc());
			textMap.put("android", onlineUserInfo.getAndroid());
    	}else{
    		textMap.put("totalOnlineUserCount", 0);
    		textMap.put("wirelessUserCount", 1);
    		textMap.put("wireUserCount", 0);
    		textMap.put("wireless1X", 0);
			textMap.put("wirelessPortal",0);
			textMap.put("ios", 0);
			textMap.put("pc", 0);
			textMap.put("android", 0);
    	}
        return JSONArray.fromObject(textMap).toString();
    }
    
    /**
     * 获取在线数
     * @return
     */
    private OnlineUserInfo getOnlineUserCount(){
        return webRuntimeData.getOnlineUserInfo();
    }
}
