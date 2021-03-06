package info.zznet.znms.web.module.screen.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.entity.FlowData;
import info.zznet.znms.base.util.HttpClientUtil;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.screen.service.FlowDataService;
import info.zznet.znms.web.util.ApiClientUtil;
import info.zznet.znms.web.util.DateJsonValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/screen/module/accessTimeAnalysis")
public class ScreenAccessTimeAnalysisController {
	
	private WebRuntimeData webRuntimeData = WebRuntimeData.instance;
	
	
	
 	@RequestMapping({"","/"})
    @CheckPermission(PermissionConstants.P_SCREEN_VIEW)
    public ModelAndView init(){
        ModelAndView mav = new ModelAndView("/screen/module/accessTimeAnalysis/index");

        return mav;
    }
 	
 	
 	@RequestMapping(value = "/getAccessTime", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
 	@ResponseBody
 	public String getAccessTime(){
 	   
		try {
			//String zlogresponse=webRuntimeData.getScreenData();
			Map<String, Object> textMap = new HashMap<String, Object>();
			
			String zlogresponse="{\"onlineDetail\":{\"教师\":{\"etcOnlineTimePerDay\":0,\"etcOnlineTimePerRecord\":0,\"pcOnlineTimePerDay\":108.92,\"pcOnlineTimePerRecord\":2181.30,\"wirelessOnlineTimePerDay\":219.14,\"wirelessOnlineTimePerRecord\":15.716666},\"学生\":{\"etcOnlineTimePerDay\":0,\"etcOnlineTimePerRecord\":0,\"pcOnlineTimePerDay\":587.080268,\"pcOnlineTimePerRecord\":6.076185,\"wirelessOnlineTimePerDay\":23.575,\"wirelessOnlineTimePerRecord\":15.716666}},\"flow\":{\"教师\":{\"etcFlowPerDay\":0,\"etcFlowPerRecord\":0,\"pcFlowPerDay\":0,\"pcFlowPerRecord\":0,\"wirelessFlowPerDay\":0,\"wirelessFlowPerRecord\":0},\"学生\":{\"etcFlowPerDay\":0,\"etcFlowPerRecord\":0,\"pcFlowPerDay\":0,\"pcFlowPerRecord\":0,\"wirelessFlowPerDay\":0,\"wirelessFlowPerRecord\":0}}}";
			

//			String zlogresponse="{\"onlineDetail\":{\"教师\":{\"etcOnlineTimePerDay\":0,\"etcOnlineTimePerRecord\":0,\"pcOnlineTimePerDay\":30.080268,\"pcOnlineTimePerRecord\":6.076185,\"wirelessOnlineTimePerDay\":23.575,\"wirelessOnlineTimePerRecord\":15.716666},\"学生\":{\"etcOnlineTimePerDay\":0,\"etcOnlineTimePerRecord\":0,\"pcOnlineTimePerDay\":7.080268,\"pcOnlineTimePerRecord\":6.076185,\"wirelessOnlineTimePerDay\":23.575,\"wirelessOnlineTimePerRecord\":15.716666}},\"flow\":{\"教师\":{\"etcFlowPerDay\":0,\"etcFlowPerRecord\":0,\"pcFlowPerDay\":0,\"pcFlowPerRecord\":0,\"wirelessFlowPerDay\":0,\"wirelessFlowPerRecord\":0},\"学生\":{\"etcFlowPerDay\":0,\"etcFlowPerRecord\":0,\"pcFlowPerDay\":0,\"pcFlowPerRecord\":0,\"wirelessFlowPerDay\":0,\"wirelessFlowPerRecord\":0}}}";

			JSONObject zlogJsonObject=JSONObject.fromObject(zlogresponse);
			Map<String, Object> teacherMap = new HashMap<String, Object>();
			double totalCountOnline=0;
			JSONObject teacherOnline=JSONObject.fromObject(JSONObject.fromObject(zlogJsonObject.getString("onlineDetail")).get("教师"));
			if(teacherOnline!=null&&!teacherOnline.equals("null")){
				double teacher_etcOnlineTimePerDay=teacherOnline.getDouble("etcOnlineTimePerDay");
				double teacher_etcOnlineTimePerRecord=teacherOnline.getDouble("etcOnlineTimePerRecord");
				double teacher_pcOnlineTimePerDay=teacherOnline.getDouble("pcOnlineTimePerDay");
				double teacher_pcOnlineTimePerRecord=teacherOnline.getDouble("pcOnlineTimePerRecord");
				double teacher_wirelessOnlineTimePerDay=teacherOnline.getDouble("wirelessOnlineTimePerDay");
				double teacher_wirelessOnlineTimePerRecord=teacherOnline.getDouble("wirelessOnlineTimePerRecord");
				teacherMap.put("etcOnlineTimePerDay", teacher_etcOnlineTimePerDay);
				teacherMap.put("etcOnlineTimePerRecord", teacher_etcOnlineTimePerRecord);
				teacherMap.put("pcOnlineTimePerDay", teacher_pcOnlineTimePerDay);
				teacherMap.put("pcOnlineTimePerRecord", teacher_pcOnlineTimePerRecord);
				teacherMap.put("wirelessOnlineTimePerDay", teacher_wirelessOnlineTimePerDay);
				teacherMap.put("wirelessOnlineTimePerRecord", teacher_wirelessOnlineTimePerRecord);
				
				totalCountOnline+=teacher_pcOnlineTimePerDay+
								 teacher_wirelessOnlineTimePerDay;
			}else{
				teacherMap.put("etcOnlineTimePerDay", 0);
				teacherMap.put("etcOnlineTimePerRecord", 0);
				teacherMap.put("pcOnlineTimePerDay", 0);
				teacherMap.put("pcOnlineTimePerRecord", 0);
				teacherMap.put("wirelessOnlineTimePerDay", 0);
				teacherMap.put("wirelessOnlineTimePerRecord", 0);
				totalCountOnline=0;
			}
			
			
			Map<String, Object> studentMap = new HashMap<String, Object>();
			JSONObject studentOnline=JSONObject.fromObject(JSONObject.fromObject(zlogJsonObject.getString("onlineDetail")).get("学生"));
			if(studentOnline!=null&&!studentOnline.equals("null")){
				double student_etcOnlineTimePerDay=studentOnline.getDouble("etcOnlineTimePerDay");
				double student_etcOnlineTimePerRecord=studentOnline.getDouble("etcOnlineTimePerRecord");
				double student_pcOnlineTimePerDay=studentOnline.getDouble("pcOnlineTimePerDay");
				double student_pcOnlineTimePerRecord=studentOnline.getDouble("pcOnlineTimePerRecord");
				double student_wirelessOnlineTimePerDay=studentOnline.getDouble("wirelessOnlineTimePerDay");
				double student_wirelessOnlineTimePerRecord=studentOnline.getDouble("wirelessOnlineTimePerRecord");
				studentMap.put("etcOnlineTimePerDay", student_etcOnlineTimePerDay);
				studentMap.put("etcOnlineTimePerRecord", student_etcOnlineTimePerRecord);
				studentMap.put("pcOnlineTimePerDay", student_pcOnlineTimePerDay);
				studentMap.put("pcOnlineTimePerRecord", student_pcOnlineTimePerRecord);
				studentMap.put("wirelessOnlineTimePerDay", student_wirelessOnlineTimePerDay);
				studentMap.put("wirelessOnlineTimePerRecord", student_wirelessOnlineTimePerRecord);
				totalCountOnline+=student_pcOnlineTimePerDay+
								 student_wirelessOnlineTimePerDay;
			}else{
				studentMap.put("etcOnlineTimePerDay", 0);
				studentMap.put("etcOnlineTimePerRecord", 0);
				teacherMap.put("pcOnlineTimePerDay", 0);
				studentMap.put("pcOnlineTimePerRecord", 0);
				studentMap.put("wirelessOnlineTimePerDay", 0);
				studentMap.put("wirelessOnlineTimePerRecord", 0);
				totalCountOnline=0;
			}
			
			textMap.put("totalCountOnline", totalCountOnline);
			textMap.put("teacher", teacherMap);
			textMap.put("student", studentMap);
			
			return JSONObject.fromObject(textMap).toString();
			
		} catch (Exception e) {
			ZNMSLogger.error("Failed to get data from zos,"+e.getMessage());
		}
		return null;
 	}
}
