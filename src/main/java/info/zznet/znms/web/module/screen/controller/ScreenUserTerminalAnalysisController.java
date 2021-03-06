package info.zznet.znms.web.module.screen.controller;

import java.util.HashMap;
import java.util.Map;

import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.util.HttpClientUtil;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.screen.bean.OnlineUserInfo;
import info.zznet.znms.web.util.ApiClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/screen/module/userTerminalAnalysis")
public class ScreenUserTerminalAnalysisController {
	
	private WebRuntimeData webRuntimeData = WebRuntimeData.instance;
	
 	@RequestMapping({"","/"})
    @CheckPermission(PermissionConstants.P_SCREEN_VIEW)
    public ModelAndView init(){
        ModelAndView mav = new ModelAndView("/screen/module/userTerminalAnalysis/index");

        return mav;
    }
 	  
 	
 	@RequestMapping(value = "/getUserTerminal", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
 	@ResponseBody
 	public String getUserTerminal(){
 		//String zlogResponse=webRuntimeData.getUserTerminalData();
// 		String zlogResponse="{\"教师\":{\"ios\":2300,\"windows\":4200,\"android\":3210,\"其他\":3012},\"学生\":{\"ios\":21532,\"windows\":35622,\"android\":42622,\"其他\":38600}}";
 		String zlogResponse="{\"教师\":{\"ios\":2300,\"windows\":4200,\"android\":3210,\"其他\":3012},\"学生\":{\"ios\":21532,\"windows\":35622,\"android\":42622,\"其他\":38600}}";
 		return JSONObject.fromObject(zlogResponse).toString();
 	}
 	
    
}
