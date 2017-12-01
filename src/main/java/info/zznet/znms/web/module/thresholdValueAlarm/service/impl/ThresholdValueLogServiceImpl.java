/**
 * 
 */
package info.zznet.znms.web.module.thresholdValueAlarm.service.impl;

import info.zznet.znms.base.dao.ThresholdValueTriggerLogMapper;
import info.zznet.znms.base.entity.ThresholdValueTriggerLog;
import info.zznet.znms.base.entity.dto.SurveyDTO;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.thresholdValueAlarm.service.ThresholdValueLogService;

import java.util.Date;
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
@Service("thresholdValueLogService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class ThresholdValueLogServiceImpl extends BaseServiceImpl implements ThresholdValueLogService{

	@Autowired
	private ThresholdValueTriggerLogMapper logMapper;

	@Override
	public Pager<ThresholdValueTriggerLog> findPageList(
			Pager<ThresholdValueTriggerLog> pager) {
		JSONArray searchCondition = null;
		if (pager != null && !StringUtil.isNullString(pager.getSearch())) {
			searchCondition = JSONArray.fromObject(pager.getSearch());
		}
		List<ThresholdValueTriggerLog> list = logMapper.findPageList(pager,
				searchCondition);
		pager.setRows(list);
		pager.setTotal(logMapper.getCount(searchCondition));
		return pager;
	}

	@Override
	public void deleteByPrimaryKey(String logUuid) {
		logMapper.deleteByPrimaryKey(logUuid);
	}
	
	@Override
	public void deleteByThresHoldValueUUId(String thresHoldValueUuid){
		logMapper.deleteByThresHoldValueUUId(thresHoldValueUuid);
	}
	
	@Override
	public void deleteByUuidList(List<String> uuids) {
		for (String uuid : uuids) {
			deleteByPrimaryKey(uuid);
		}
	}

	@Override
	public List<SurveyDTO> findThresholdValueMap(Date startDate, Date endDate) {
		return logMapper.findThresholdValueMap(startDate, endDate);
	}
}
