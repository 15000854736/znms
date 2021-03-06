/**
 * 
 */
package info.zznet.znms.web.module.systemLog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import info.zznet.znms.base.dao.SystemLogStatisticsMapper;
import info.zznet.znms.base.entity.SystemLogStatistics;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.systemLog.service.SystemLogStatisticsService;

/**
 * @author dell001
 *
 */
@Service("systemLogStatisticsService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class SystemLogStatisticsServiceImpl extends BaseServiceImpl implements SystemLogStatisticsService{

	@Autowired
	private SystemLogStatisticsMapper logStatisticsMapper;

	@Override
	public List<SystemLogStatistics> findPieData(String id,
			String hostId, String insertTimeFrom, String insertTimeTo, Integer type) {
		return logStatisticsMapper.findPieData(id, hostId, insertTimeFrom, insertTimeTo, type);
	}

	@Override
	public List<SystemLogStatistics> findLineData(String priorityId,
			String facilityId, String hostId, String startTime, String endTime) {
		return logStatisticsMapper.findLineData(priorityId, facilityId, hostId, startTime, endTime);
	}
}
