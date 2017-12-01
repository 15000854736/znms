package info.zznet.znms.base.job;

import info.zznet.znms.base.bean.HostHealth;
import info.zznet.znms.base.bean.HostPingResult;
import info.zznet.znms.base.bean.ZlogSystemStatus;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.HostWeight;
import info.zznet.znms.base.dao.OnlineUserAnalysisMapper;
import info.zznet.znms.base.dao.SystemLogMapper;
import info.zznet.znms.base.dao.ThresholdValueTriggerLogMapper;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.entity.OnlineUserAnalysis;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.screen.bean.OnlineUserInfo;
import info.zznet.znms.web.module.screen.bean.UrlRanking;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;
import info.zznet.znms.web.module.systemLog.collector.Severity;
import info.zznet.znms.web.util.ApiClientUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.zznet.znms.web.util.ConfigUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


/**
 * 投屏页面数据刷新任务
 * @author dell001
 *
 */
@Service
@DisallowConcurrentExecution
public class ScreenDataRefreshJob {
		
	WebRuntimeData webRuntimeData = WebRuntimeData.instance;
	
	@Autowired
	private OnlineUserAnalysisMapper onlineUserInfoMapper;
	@Autowired
	private SystemLogMapper logMapper;
	@Autowired
	private ThresholdValueTriggerLogMapper tvtLogMapper;
	
	/**
	 * 系统日志密度，影响系统日志得分
	 */
	private static final int SYSLOG_DENSITY = 2;
	/**
	 * 阈值日志密度，影响阈值报警得分
	 */
	private static final int TVTLOG_DENSITY = 4;
	
	/**
	 * 从zos获取在线用户信息
	 */
	@Scheduled(fixedDelay=60000)
	public void refreshOnlineUserInfo(){
		String zosIp = WebRuntimeData.instance.getSystemOptionBean().getZosIp();
		String zosPort = WebRuntimeData.instance.getSystemOptionBean().getZosPort();
		if(zosIp!=null&&!zosPort.equals("")){
			if(StringUtils.isEmpty(zosIp) || StringUtils.isEmpty(zosPort)) {
//			ZNMSLogger.warn("Zos ip/port not set, cannot fetch data");
				return;
			}
			OnlineUserInfo onlineUserInfo = new OnlineUserInfo();
			String zosContext = ConfigUtil.getString("zos.context.path","z-os");
			String url = "http://" + zosIp + ":" + zosPort + "/"+zosContext+"/api/v1/noEncrypt/znms/getAnalysicsInfo";
			String _url = "http://" + zosIp + ":" + zosPort + "/"+zosContext+"/api/v1/noEncrypt/znms/getUserTerminalInfo";
			try {
				ApiClientUtil terminalInfoApi = new ApiClientUtil(_url);
				String terminalInfo=terminalInfoApi.post(null);
				JSONObject jsonObject=JSONObject.fromObject(terminalInfo);
				onlineUserInfo.setWireless1X(jsonObject.getInt("wireless1x"));
				onlineUserInfo.setWirelessPortal(jsonObject.getInt("wirelessPortal"));
				onlineUserInfo.setAndroid(jsonObject.getInt("android"));
				onlineUserInfo.setIos(jsonObject.getInt("ios"));
				onlineUserInfo.setPc(jsonObject.getInt("windows"));
			} catch (IOException e1) {
				ZNMSLogger.error("Failed to get  terminal data from zos,"+e1.getMessage());
			}
			
			ApiClientUtil api = new ApiClientUtil(url);
			try {
				String json = api.post(null);
				if(!StringUtils.isEmpty(json)) {
					ZNMSLogger.debug(json);
					JSONObject jsonObj = JSONObject.fromObject(json);
					onlineUserInfo.setTotalOnlineUserCount(jsonObj.getInt("onlineUserAmount"));
					onlineUserInfo.setRegisterUserCount(jsonObj.getInt("signUserAmount"));
					onlineUserInfo.setActiveUserCount(jsonObj.getInt("activeUserAmount"));
					onlineUserInfo.setFreeUserCount(jsonObj.getInt("freeUserAmount"));
					onlineUserInfo.setLostUserCount(jsonObj.getInt("lostUserAmount"));
					onlineUserInfo.setWireUserCount(jsonObj.getInt("pcOnlineAmount"));
					onlineUserInfo.setWirelessUserCount(jsonObj.getInt("wtOnlineAmount"));
					onlineUserInfo.setEtcUserCount(jsonObj.getInt("otherOnlineAmount"));
					webRuntimeData.setOnlineUserInfo(onlineUserInfo);
					persistOnlineUserInfo(onlineUserInfo);
				}
			} catch (Exception e) {
				ZNMSLogger.error("Failed to get data from zos,"+e.getMessage());
			}
		}
	}
	
	private void persistOnlineUserInfo(OnlineUserInfo onlineUserInfo){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		
		
		OnlineUserAnalysis pcUserCount = new OnlineUserAnalysis();
		pcUserCount.setOnlineUserAnalysisUuid(UUIDGenerator.getGUID());
		pcUserCount.setUserType(1);
		pcUserCount.setUserCount(onlineUserInfo.getWireUserCount());
		pcUserCount.setCreateTime(cal.getTime());
		if(onlineUserInfoMapper.findByTypeAndTime(pcUserCount.getUserType(), cal.getTime()) == 0) {
			onlineUserInfoMapper.insert(pcUserCount);			
		}
		
		OnlineUserAnalysis phoneUserCount = new OnlineUserAnalysis();
		phoneUserCount.setOnlineUserAnalysisUuid(UUIDGenerator.getGUID());
		phoneUserCount.setUserType(2);
		phoneUserCount.setUserCount(onlineUserInfo.getWirelessUserCount());
		phoneUserCount.setCreateTime(cal.getTime());
		if(onlineUserInfoMapper.findByTypeAndTime(phoneUserCount.getUserType(), cal.getTime()) == 0) {
			onlineUserInfoMapper.insert(phoneUserCount);
		}
		
		OnlineUserAnalysis etcUserCount = new OnlineUserAnalysis();
		etcUserCount.setOnlineUserAnalysisUuid(UUIDGenerator.getGUID());
		etcUserCount.setUserType(3);
		etcUserCount.setUserCount(onlineUserInfo.getEtcUserCount());
		etcUserCount.setCreateTime(cal.getTime());
		if(onlineUserInfoMapper.findByTypeAndTime(etcUserCount.getUserType(), cal.getTime()) == 0) {
			onlineUserInfoMapper.insert(etcUserCount);
		}
	}
	
	/**
	 * 从z-log获取信息
	 */
	@Scheduled(cron="0 */5 * * * ?")
	public void refreshZlogData(){
		String zlogIp = WebRuntimeData.instance.getSystemOptionBean().getZlogIp();
		String zlogPort = WebRuntimeData.instance.getSystemOptionBean().getZlogPort();
		String zdasContext = ConfigUtil.getString("zdas.context.path","z-das");
		if(StringUtils.isEmpty(zlogIp) || StringUtils.isEmpty(zlogPort)) {
//			ZNMSLogger.warn("Zlog ip/port not set, cannot fetch data");
			return;
		}
		
		// 获取url访问量排名
		ApiClientUtil api = new ApiClientUtil("http://" + zlogIp + ":" + zlogPort + "/"+zdasContext+"/api/rest/getUrlRanking");
		List<UrlRanking> urlRankingList = new ArrayList<UrlRanking>();
		try {
			String json = api.post(null);
			if(!StringUtils.isEmpty(json)) {
				ZNMSLogger.debug(json);
				JSONArray jsonArr = JSONArray.fromObject(json);
				for(int i=0;i<jsonArr.size();i++) {
					JSONObject jsonObj = jsonArr.getJSONObject(i);
					UrlRanking urlRanking = new UrlRanking();
					urlRanking.setUrl(jsonObj.getString("url"));
					urlRanking.setCount(jsonObj.getLong("num"));
					urlRankingList.add(urlRanking);
				}
			}
		} catch (Exception e) {
			ZNMSLogger.error("Failed to get url ranking from zlog");
		}
		webRuntimeData.setUrlRankingList(urlRankingList);
		
		// 获取zlog运行状态
		ZlogSystemStatus systemStatus = new ZlogSystemStatus();

		api = new ApiClientUtil("http://" + zlogIp + ":" + zlogPort + "/"+zdasContext+"/api/rest/getSystemStatus");
		try {
			String json = api.post(null);
			if(!StringUtils.isEmpty(json)) {
				ZNMSLogger.debug(json);
				systemStatus = (ZlogSystemStatus) JSONObject.toBean(JSONObject.fromObject(json), ZlogSystemStatus.class);
				webRuntimeData.pushZlogSystemStatus(systemStatus);
			}
		} catch (Exception e) {
			ZNMSLogger.error("Failed to get running status from zlog");
		}
		
		// 获取zlog最近八小时日志量
		api = new ApiClientUtil("http://" + zlogIp + ":" + zlogPort + "/"+zdasContext+"/api/rest/getLatestLogCount");
		try {
			String json = api.post(null);
			if(!StringUtils.isEmpty(json)) {
				ZNMSLogger.debug(json);
				JSONObject jsonObj = JSONObject.fromObject(json);
				JSONArray natLogArr = jsonObj.getJSONArray("natLogCount");
				JSONArray urlLogArr = jsonObj.getJSONArray("urlLogCount");
				List<Long> natLogList = new ArrayList<Long>();
				List<Long> urlLogList = new ArrayList<Long>();
				for(int i=0;i<8;i++){
					natLogList.add(natLogArr.getLong(i));
					urlLogList.add(urlLogArr.getLong(i));
				}
				webRuntimeData.setZlogNatlogCountList(natLogList);
				webRuntimeData.setZlogUrlLogCountList(urlLogList);
			}
		} catch (Exception e) {
			ZNMSLogger.error("Failed to get latest log count from zlog,"+e.getMessage());
		}
	}
	
	/**
	 * 计算网络健康度
	 */
	@Scheduled(fixedDelay=60000)
	public void refreshNetworkHealthPoint(){
		BigDecimal syslogPoint = calSyslogHealth();
		BigDecimal pingPoint = calPingPoint();
		BigDecimal tvtlogPoint = calThresholdValueLogPoint();
		webRuntimeData.setNetworkHealth((syslogPoint.intValue()+pingPoint.intValue()+tvtlogPoint.intValue())/3);
//		System.out.println("健康度:"+(syslogPoint.intValue()+pingPoint.intValue()+tvtlogPoint.intValue())/3);
	}
	
	/**
	 * 系统日志得分(百分制)
	 * @return
	 */
	private BigDecimal calSyslogHealth(){
		// 系统日志部分--start		
		Map<String, HostHealth> hostHealthMap = new HashMap<String, HostHealth>();
		
		for(Severity s : Severity.values()){
			if(s==Severity.WARNING) {
				break;
			}
			List<HostHealth> list = logMapper.getCountBySeverity(s.getCode());
			if(list != null) {
				for(HostHealth health : list) {
					HostHealth _health = hostHealthMap.get(health.getHostId());
					if(_health == null) {
						hostHealthMap.put(health.getHostId(), health);
						_health = health;
					}
					long point = _health.getPoint();
					long count = health.getLogCount();
					long badPoint = 0;
					switch(s) {
						case EMERG:
							badPoint = 4 * count;
							break;
						case ALERT:
							badPoint = 3 * count;
							break;
						case CRITICAL:
							badPoint = 2 * count;
							break;
						case ERROR:
							badPoint = 1 * count;
							break;
						default:
							break;
					}
					point -= (badPoint * SYSLOG_DENSITY);
					_health.setPoint(point);
				}
			}
		}
		
		long totalPoint = 0l;
		long truePoint = 0l;
		for(HostHealth hw : hostHealthMap.values()){
			int weight = HostWeight.getWeight(hw.getWeight());
			totalPoint += weight * HostHealth.INITIAL_POINT;
			truePoint += (weight * hw.getPoint() < 0l?0l:weight * hw.getPoint());
		}
		
		return new BigDecimal(truePoint).multiply(new BigDecimal(HostHealth.INITIAL_POINT)).divide(new BigDecimal(totalPoint), BigDecimal.ROUND_HALF_UP);
		// 系统日志部分 --end
	}
	
	/**
	 * 可用度得分(百分制)
	 * @return
	 */
	private BigDecimal calPingPoint(){
		Map<String, HostPingResult> resultMap = webRuntimeData.getPingResult();
		BigDecimal totalPoint = BigDecimal.ZERO;
		BigDecimal truePoint = BigDecimal.ZERO;
		for(String hostIp : resultMap.keySet()) {
			Host host = webRuntimeData.getHost(hostIp);
			if(host == null){
				continue;
			}
			int weight = HostWeight.getWeight(host.getType());
			totalPoint = totalPoint.add(new BigDecimal(weight * HostHealth.INITIAL_POINT));
			HostPingResult result = resultMap.get(hostIp);
			int success = result.getSuccess();
			int fail = result.getFail();
			int total = success + fail;
			BigDecimal _point = BigDecimal.ZERO;
			if(total == 0) {
				_point = new BigDecimal(HostHealth.INITIAL_POINT);
			} else {
				_point = new BigDecimal(success).multiply(new BigDecimal(HostHealth.INITIAL_POINT)).divide(new BigDecimal(total), BigDecimal.ROUND_HALF_UP);
			}
			truePoint = truePoint.add(_point.multiply(new BigDecimal(weight)));
//			if(success > 0 || _point.compareTo(BigDecimal.ZERO)>0) {
//				System.out.println(hostIp + ":"+_point);
//			}
		}
		if(totalPoint.compareTo(BigDecimal.ZERO)==0){
			return new BigDecimal(HostHealth.INITIAL_POINT);
		}
		return truePoint.multiply(new BigDecimal(HostHealth.INITIAL_POINT)).divide(totalPoint, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 阈值报警得分(百分制)
	 * @return
	 */
	private BigDecimal calThresholdValueLogPoint(){
		Map<String, HostHealth> hostHealthMap = new HashMap<String, HostHealth>();
		
		List<HostHealth> list = tvtLogMapper.getLatestCount();
		if(list != null) {
			for(HostHealth health : list) {
				HostHealth _health = hostHealthMap.get(health.getHostId());
				if(_health == null) {
					hostHealthMap.put(health.getHostId(), health);
					_health = health;
				}
				long point = _health.getPoint();
				long count = health.getLogCount();
				
				point -= count * TVTLOG_DENSITY;
				_health.setPoint(point);
			}
		}
		
		long totalPoint = 0l;
		long truePoint = 0l;
		for(HostHealth hw : hostHealthMap.values()){
			int weight = HostWeight.getWeight(hw.getWeight());
			totalPoint += weight * HostHealth.INITIAL_POINT;
			truePoint += (weight * hw.getPoint() < 0l?0l:weight * hw.getPoint());
		}
		
		return new BigDecimal(truePoint).multiply(new BigDecimal(HostHealth.INITIAL_POINT)).divide(new BigDecimal(totalPoint), BigDecimal.ROUND_HALF_UP);
	}
}
