package info.zznet.znms.base.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.ThresholdValueTriggerSurvey;
import info.zznet.znms.web.module.common.page.Pager;

@Repository("surveyMapper")
public interface ThresholdValueTriggerSurveyMapper extends BaseMapper{
    int deleteByPrimaryKey(String surveyUuid);
    int deleteByThresHoldValueUUId(String thresHoldValueUuid);

    int insert(ThresholdValueTriggerSurvey record);

    int insertSelective(ThresholdValueTriggerSurvey record);

    ThresholdValueTriggerSurvey selectByPrimaryKey(String surveyUuid);

    int updateByPrimaryKeySelective(ThresholdValueTriggerSurvey record);

    int updateByPrimaryKey(ThresholdValueTriggerSurvey record);

	/**
	 * @param pager
	 * @param searchCondition
	 * @return
	 */
	List<ThresholdValueTriggerSurvey> findPageList(@Param("pager")Pager<ThresholdValueTriggerSurvey> pager, 
			@Param("condition")JSONArray searchCondition);

	/**
	 * @param searchCondition
	 * @return
	 */
	Long getCount(@Param("condition")JSONArray searchCondition);

	/**
	 * @param thresholdValueUuid
	 * @return
	 */
	ThresholdValueTriggerSurvey findByThresholdValueUuid(
			@Param("thresholdValueUuid")String thresholdValueUuid);
}