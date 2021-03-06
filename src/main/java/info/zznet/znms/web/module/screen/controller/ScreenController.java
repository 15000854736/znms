package info.zznet.znms.web.module.screen.controller;

import info.zznet.znms.base.bean.ZlogSystemStatus;
import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.dao.ApInformationMapper;
import info.zznet.znms.base.dao.ExportLinkMapper;
import info.zznet.znms.base.dao.ScreenMapper;
import info.zznet.znms.base.entity.ApInformation;
import info.zznet.znms.base.entity.ExportLink;
import info.zznet.znms.base.entity.Screen;
import info.zznet.znms.base.job.ApInfomationJob;
import info.zznet.znms.base.rrd.bean.Graph;
import info.zznet.znms.base.rrd.bean.SubItem;
import info.zznet.znms.base.rrd.core.RrdFetcher;
import info.zznet.znms.base.rrd.exception.RrdQueryException;
import info.zznet.znms.base.util.EncryptionMD5;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.screen.bean.ExportStreamInfo;
import info.zznet.znms.web.module.screen.bean.HeatData;
import info.zznet.znms.web.module.screen.bean.OnlineUserInfo;
import info.zznet.znms.web.module.screen.bean.ReadFile;
import info.zznet.znms.web.module.screen.bean.ScreenModuleBean;
import info.zznet.znms.web.module.screen.bean.UrlRanking;
import info.zznet.znms.web.module.screen.service.ScreenService;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;
import info.zznet.znms.web.util.ConfigUtil;
import info.zznet.znms.web.util.PinyinTool;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Controller
@RequestMapping("/screen")
public class ScreenController extends BaseController{
	private static final String VIEW = "/screen/screen";
	private static final String ADD_SCREEN_MODULE_VIEW = "/screen/addScreen";
	
	@Autowired
	private ScreenService screenService;

	private WebRuntimeData webRuntimeData = WebRuntimeData.instance;
	
	
	@RequestMapping({"","/"})
	@CheckPermission(PermissionConstants.P_SCREEN_VIEW)
	public ModelAndView init(){
		SystemOptionBean config = webRuntimeData.getSystemOptionBean();
		ModelAndView mav = new ModelAndView(VIEW);

		List<Screen> screens = screenService.findAll();

		mav.addObject(screens);
		mav.addObject("logoImage", config.getIndexBg());
		mav.addObject("bgImage", config.getHomeBg());
		mav.addObject("logoHeight",getScreenLogoSize()[1]);


		return mav;
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
	
	@RequestMapping(value = "/merge/do", method = RequestMethod.POST, produces ="text/json;charset=UTF-8")
	@ResponseBody
	public String mergeDo(@ModelAttribute Screen screen,HttpServletRequest req){
		try {
			screen.setId(UUIDGenerator.getGUID());
			screen.setRow(2);
			screen.setCol(3);
			if(screenService.findByName(screen.getName()).size()>0){
				return "{\"result\":false,\"msg\":\"名称不能重复\"}";
			}
			//名称转拼音
			PinyinTool py=new PinyinTool();
			screen.setCode(py.toPinYin(screen.getName()));
			
			screen.setCreatetime(new Date());
			screenService.addScreen(screen);
		} catch (Exception e) {
			return "{\"result\":false,\"msg\":\"添加失败\"}";
		}
		return "{\"result\":\"true\",\"id\":\""+screen.getId()+"\"}";
	} 
	
	@RequestMapping(value= "/toAddScreenModule", method = RequestMethod.GET)
	public ModelAndView toAddPage(HttpServletRequest request){
		ModelAndView mav = new ModelAndView(ADD_SCREEN_MODULE_VIEW);
		Screen screenConfig=screenService.findById(request.getParameter("id"));
		String strDirPath = request.getSession().getServletContext().getRealPath("/")+"views/screen/module";
		List<ScreenModuleBean> configDataEntityList=ReadFile.getConfigData(strDirPath,screenConfig.getRow(),screenConfig.getCol());
		return mav.addObject("screenConfig",screenConfig).addObject("configDataEntityList",configDataEntityList);
	}
	
	@RequestMapping(value = "/updateScreen", method = RequestMethod.POST, produces ="text/json;charset=UTF-8")
	@ResponseBody
	public String updateConfig(@ModelAttribute Screen screen,HttpServletRequest request){
		try {
			screen.setUpdatetime(new Date());
			screenService.updateByIdSelective(screen);
		} catch (Exception e) {
			return "{\"result\":false,\"msg\":\"添加失败\"}";
		}
		return "{\"result\":\"true\"}";
	}
	@RequestMapping(value = "/uploadThumbnailFile", method = RequestMethod.POST, produces ="text/json;charset=UTF-8")
	@ResponseBody
	public String uploadThumbnailFile(@ModelAttribute Screen screen,HttpServletRequest request,MultipartFile import_file){
		try {
			String id=request.getParameter("id");
			if(null != import_file){
				//String strDirPath = request.getSession().getServletContext().getRealPath("/")+"image/thumbnail/";
				String strDirPath = ConfigUtil.getString("znms.image.path")+File.separator+"thumbnail";
				String fileName = import_file.getOriginalFilename();
				String suffix = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
				File file = new File(strDirPath);
				if(file.exists() && file.isFile()){
					file.delete();
				}
				File imageFileFolder = new File(ConfigUtil.getString("znms.image.path")+File.separator+"thumbnail");
				if(!imageFileFolder.exists() || !imageFileFolder.isDirectory()){
					imageFileFolder.mkdirs();
				}
				fileName=id+"."+suffix;
				File thumbnailFile = new File(strDirPath+File.separator+fileName);
				thumbnailFile.createNewFile();
				import_file.transferTo(thumbnailFile);
				screen.setThumbnail(fileName);
			}
			screen.setUpdatetime(new Date());
			screenService.updateByIdSelective(screen);
		} catch (Exception e) {
			return "{\"result\":false,\"msg\":\"添加失败\"}";
		}
		return "{\"result\":\"true\"}";
	}
	
	@RequestMapping(value = "/deleteScreen", method = RequestMethod.POST, produces ="text/json;charset=UTF-8")
	@ResponseBody
	public String deleteScreen(@ModelAttribute Screen screen,HttpServletRequest request){
		try {
			String strDirPath = ConfigUtil.getString("znms.image.path")+File.separator+"thumbnail";
			screen=screenService.findById(screen.getId());
			File thumbnailFile = new File(strDirPath+File.separator+screen.getThumbnail());
			if(thumbnailFile.exists() && thumbnailFile.isFile()){
				thumbnailFile.delete();
			}
			screenService.deleteById(screen.getId());
		} catch (Exception e) {
			return "{\"result\":false,\"msg\":\"删除失败\"}";
		}
		return "{\"result\":\"true\"}";
	}
	
}
