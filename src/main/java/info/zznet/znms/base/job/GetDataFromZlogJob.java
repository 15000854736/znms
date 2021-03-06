/**
 * 
 */
package info.zznet.znms.base.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.screen.service.FlowDataService;
import info.zznet.znms.web.util.ApiClientUtil;

import info.zznet.znms.web.util.ConfigUtil;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author dell001
 *
 */
@Service
@DisallowConcurrentExecution
public class GetDataFromZlogJob {
	private WebRuntimeData webRuntimeData = WebRuntimeData.instance;
	
	@Autowired
	FlowDataService flowDataService;
	/**
	 * 每隔一分钟从zlog获取数据存到内存中
	 */
	@Scheduled(fixedDelay = 60*1000)
	public void getScreenDataFromZlog(){
		try{
			String zlogIp=webRuntimeData.getSystemOptionBean().getZlogIp();
			String zlogPort=webRuntimeData.getSystemOptionBean().getZlogPort();
			if(zlogIp!=null&&!zlogIp.equals("")){
				String zdasContext = ConfigUtil.getString("zdas.context.path","z-das");
				Map<String,String> paramMap=new HashMap<String, String>();
				paramMap.put("from", String.valueOf(DateUtil.getDayTime(new Date())));
				paramMap.put("to", String.valueOf(System.currentTimeMillis()));
				ApiClientUtil apiClientUtil=new ApiClientUtil("http://"+zlogIp+":"+zlogPort+"/"+zdasContext+"/api/rest/getOnlineAnalyzeResult");
				String data = apiClientUtil.post(paramMap);
				webRuntimeData.setScreenData(data);
			}
		} catch(Throwable e) {
			e.printStackTrace();
			ZNMSLogger.error(e);
		}
	}
	
	@Scheduled(fixedDelay = 60*1000)
	public void getUserTerminalDataFromZlog(){
		try{
			String zlogIp=webRuntimeData.getSystemOptionBean().getZlogIp();
			String zlogPort=webRuntimeData.getSystemOptionBean().getZlogPort();
			if(zlogIp!=null&&!zlogIp.equals("")){
				String zdasContext = ConfigUtil.getString("zdas.context.path","z-das");
				ApiClientUtil apiClientUtil=new ApiClientUtil("http://"+zlogIp+":"+zlogPort+"/"+zdasContext+"/api/rest/getCurrentOnlineData");
				String data = apiClientUtil.post(null);
				webRuntimeData.setUserTerminalData(data);
			}
		} catch(Throwable e) {
			e.printStackTrace();
			ZNMSLogger.error(e);
		}
	}
	
	@Scheduled(fixedDelay = 60*1000)
	public void getFlowDataFromZlog(){
		try{
			String zlogIp=webRuntimeData.getSystemOptionBean().getZlogIp();
			String zlogPort=webRuntimeData.getSystemOptionBean().getZlogPort();
			if(zlogIp!=null&&!zlogIp.equals("")){
				String zdasContext = ConfigUtil.getString("zdas.context.path","z-das");
				Map<String,String> paramMap=new HashMap<String, String>();
				paramMap.put("size", "10");
				paramMap.put("interval", "1");
				ApiClientUtil apiClientUtil=new ApiClientUtil("http://"+zlogIp+":"+zlogPort+"/"+zdasContext+"/api/rest/getFlowData");
				String data = apiClientUtil.post(paramMap);
				webRuntimeData.setFlowData(data);
			}
		} catch(Throwable e) {
			e.printStackTrace();
			ZNMSLogger.error(e);
		}
	}
	
	@Scheduled(cron = "0 0 0 * * *") 
	public void saveFlowData(){
		try{
			String zlogIp=webRuntimeData.getSystemOptionBean().getZlogIp();
			String zlogPort=webRuntimeData.getSystemOptionBean().getZlogPort();
			if(zlogIp!=null&&!zlogIp.equals("")){
				Map<String,String> paramMap=new HashMap<String, String>();
				paramMap.put("from", String.valueOf(DateUtil.getYesterdayBegin()));
				paramMap.put("to", String.valueOf(DateUtil.getYesterdayEnd()));
				ApiClientUtil apiClientUtil=new ApiClientUtil("http://"+zlogIp+":"+zlogPort+"/z-log/api/rest/getOnlineAnalyzeResult");
				String data = apiClientUtil.post(paramMap);
				flowDataService.addFlowData(data);
			}
		} catch(Throwable e) {
			e.printStackTrace();
			ZNMSLogger.error(e);
		}
	}
}
