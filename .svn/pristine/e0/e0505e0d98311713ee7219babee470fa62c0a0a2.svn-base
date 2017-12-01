/**
 * 
 */
package info.zznet.znms.web.module.security.service.impl;

import info.zznet.znms.base.dao.OnlineUserAnalysisMapper;
import info.zznet.znms.base.entity.ApInformation;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.job.ApInfomationJob;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.security.service.HomePageService;
import info.zznet.znms.web.module.system.service.HostService;
import info.zznet.znms.web.module.systemLog.service.SystemLogService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dell001
 *
 */
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("homePageService")
public class HomePageServiceImpl extends BaseServiceImpl implements HomePageService{
	
	@Autowired
	private OnlineUserAnalysisMapper onlineUserAnalysisMapper;
	
	@Autowired
	private HostService hostService;
	
	@Autowired
	private SystemLogService systemLogService;

	@Override
	public Integer findUserCountByCondition(String startTime, String endTime, Integer userType) {
		return onlineUserAnalysisMapper.findUserCountByCondition(startTime, endTime, userType);
	}

	@Override
	public List<List<Integer>> getDeviceData() {
		List<List<Integer>> deviceList = new ArrayList<List<Integer>>();
		for(int i=0;i<3;i++){
			List<Integer> deviceDataList = new ArrayList<Integer>();
			int[] deviceTypeArray = {};
			switch (i) {
			case 0:
				deviceTypeArray = Host.EXCHANGE_DEVICE_TYPE;
				break;
			case 1:
				deviceTypeArray = Host.GATEWAY_DEVICE_TYPE;
				break;
			case 2:
				deviceTypeArray = Host.WIRELESS_DEVICE_TYPE;
				break;
			default:
				break;
			}
			for(int hostWorkStatus=-1;hostWorkStatus<2;hostWorkStatus++){
				int deviceCount = hostService.getDeviceCountByCondition(deviceTypeArray, hostWorkStatus);
				deviceDataList.add(deviceCount);
			}
			deviceList.add(deviceDataList);
		}
		//设置ap信息
		List<Integer> deviceDataList = new ArrayList<Integer>();
		Integer apCount = WebRuntimeData.instance.getApCount();
		deviceDataList.add(apCount);
		deviceDataList.add(0);
		deviceDataList.add(apCount);
		deviceList.add(deviceDataList);
		return deviceList;
	}

	@Override
	public List<Host> getMainDeviceMonitorData() {
		int[] defaultAccessArray = Host.MAIN_DEVICE_TYPE;
		return hostService.getMainDeviceMonitorData(defaultAccessArray);
	}

	@Override
	public List<Long> getNetHeathData() {
		//查询首页网络健康数据
		int[] emergyArray = {0};
		int[] highGradeArray = {1,2,3};
		int[] middleGradeArray = {4};
		int[] lowGradeArray = {5,6,7};
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time = DateUtil.getDayStart(new Date()).getTime();
		String startDate = sdf.format(time);
		String endDate = sdf.format(new Date().getTime());
		//查询紧急告警  priorityId
		long emergyCount = systemLogService.getNetHeathData(emergyArray, startDate, endDate);
		//查询高级告警  
		long highCount = systemLogService.getNetHeathData(highGradeArray, startDate, endDate);
		//查询中级告警  
		long middelCount = systemLogService.getNetHeathData(middleGradeArray, startDate, endDate);
		//查询低级告警  
		long lowCount = systemLogService.getNetHeathData(lowGradeArray, startDate, endDate);
		List<Long> list = new ArrayList<Long>();
		list.add(emergyCount);
		list.add(highCount);
		list.add(middelCount);
		list.add(lowCount);
		return list;
	}
	
}
