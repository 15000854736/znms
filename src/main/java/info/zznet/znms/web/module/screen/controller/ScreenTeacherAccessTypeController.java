package info.zznet.znms.web.module.screen.controller;

import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.screen.service.ScreenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 学生接入方式
 * Created by shenqilei on 2016/12/9.
 */
@Controller
@RequestMapping("/screen/module/teacherAccessType")
public class ScreenTeacherAccessTypeController {
	
	@Autowired
	private ScreenService screenService;

    @RequestMapping({"","/"})
    @CheckPermission(PermissionConstants.P_SCREEN_VIEW)
    public ModelAndView init(){
        ModelAndView mav = new ModelAndView("/screen/module/teacherAccessType/index");

        return mav;
    }

    /**
     * 获取教师接入方式数据
     * @return
     */
    @RequestMapping(value = "/getTeacherType", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
 	@ResponseBody
 	public String getAccessTime(){
    	String type = "教师";
    	String data = screenService.findStudentTeacherAccessTypeData(type);
 	   return data;
 	}

}
