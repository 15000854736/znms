/**
 * 
 */
package info.zznet.znms.web.module.thresholdValueAlarm.service.impl;

import info.zznet.znms.base.dao.ThresholdValueTriggerSurveyMapper;
import info.zznet.znms.base.entity.ThresholdValueTriggerSurvey;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.thresholdValueAlarm.service.ThresholdValueTriggerSurveyService;

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
@Service("surveyService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class ThresholdValueTriggerSurveyServiceImpl extends BaseServiceImpl implements ThresholdValueTriggerSurveyService{

	@Autowired
	private ThresholdValueTriggerSurveyMapper surveyMapper;

	@Override
	public Pager<ThresholdValueTriggerSurvey> findPageList(
			Pager<ThresholdValueTriggerSurvey> pager) {
		JSONArray searchCondition = null;
		if (pager != null && !StringUtil.isNullString(pager.getSearch())) {
			searchCondition = JSONArray.fromObject(pager.getSearch());
		}
		List<ThresholdValueTriggerSurvey> list = surveyMapper.findPageList(pager,
				searchCondition);
		pager.setRows(list);
		pager.setTotal(surveyMapper.getCount(searchCondition));
		return pager;
	}

	@Override
	public void deleteByPrimaryKey(String surveyUuid) {
		surveyMapper.deleteByPrimaryKey(surveyUuid);
	}
	
	@Override
	public void deleteByThresHoldValueUUId(String thresHoldValueUuid){
		surveyMapper.deleteByThresHoldValueUUId(thresHoldValueUuid);
	}

	@Override
	public void deleteByUuidList(List<String> uuids) {
		for (String surveyUuid : uuids) {
			deleteByPrimaryKey(surveyUuid);
		}
	}

	@Override
	public ThresholdValueTriggerSurvey findByThresholdValueUuid(
			String thresholdValueUuid) {
		return surveyMapper.findByThresholdValueUuid(thresholdValueUuid);
	}

	@Override
	public void add(ThresholdValueTriggerSurvey tvts) {
		surveyMapper.insert(tvts);
	}

	@Override
	public void update(ThresholdValueTriggerSurvey survey) {
		surveyMapper.updateByPrimaryKey(survey);
	}
}
