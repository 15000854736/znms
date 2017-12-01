/**
 * 
 */
package info.zznet.znms.web.module.systemLog.service.impl;

import info.zznet.znms.base.dao.SystemLogMapper;
import info.zznet.znms.base.entity.SystemLog;
import info.zznet.znms.base.entity.SystemLogStatistics;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.systemLog.service.SystemLogService;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dell001
 *
 */
@Service("systemLogService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class SystemLogServiceImpl extends BaseServiceImpl implements SystemLogService{

	@Autowired
	private SystemLogMapper systemLogMapper;

	@Override
	public Pager findPageList(Pager pager) {
		JSONArray searchCondition = null;
		if (pager != null && !StringUtil.isNullString(pager.getSearch())) {
			searchCondition = JSONArray.fromObject(pager.getSearch());
		}
		List<SystemLog> list = systemLogMapper.findPageList(pager,
				searchCondition);
		pager.setRows(list);
		pager.setTotal(systemLogMapper.getCount(searchCondition));
		return pager;
	}

	@Override
	public void deleteByPrimaryKey(Long seq) {
		systemLogMapper.deleteByPrimaryKey(seq);
	}

	@Override
	public void deleteUuidList(List<Long> uuids) {
		for (Long seq : uuids) {
			deleteByPrimaryKey(seq);
		}
	}

	@Override
	public List<SystemLogStatistics> findLogStatisticsList(String startDate,
			String endDate) {
		return systemLogMapper.findLogStatisticsList(startDate, endDate);
	}

	@Override
	public long getNetHeathData(int[] emergyArray, String startDate,
			String endDate) {
		return systemLogMapper.getNetHeathData(emergyArray, startDate, endDate);
	}
	
}
