package info.zznet.znms.web.module.screen.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.entity.Screen;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.screen.service.ScreenService;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;
import info.zznet.znms.web.util.ConfigUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/screen/module/fullPage")
public class FullPageController {
	
	@Autowired
	private ScreenService screenService;

	private WebRuntimeData webRuntimeData = WebRuntimeData.instance;
	
	@RequestMapping({"","/"})
    @CheckPermission(PermissionConstants.P_SCREEN_VIEW)
    public ModelAndView init(){
        SystemOptionBean config = webRuntimeData.getSystemOptionBean();
        //String path="/screen/fullPage";
        String path="/screen/module/fullPage/index";
        ModelAndView mav = new ModelAndView(path);
		/*List<Screen> screens = screenService.findAll();
		mav.addObject(screens);
		mav.addObject("logoImage", config.getIndexBg());
		mav.addObject("bgImage", config.getHomeBg());
		mav.addObject("logoHeight",getScreenLogoSize()[1]);*/
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
}
