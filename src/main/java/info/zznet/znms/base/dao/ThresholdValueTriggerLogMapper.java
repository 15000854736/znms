package info.zznet.znms.base.dao;

import info.zznet.znms.base.bean.HostHealth;
import info.zznet.znms.base.entity.ThresholdValueTriggerLog;
import info.zznet.znms.base.entity.dto.SurveyDTO;
import info.zznet.znms.web.module.common.page.Pager;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("thresholdValueTriggerLogMapper")
public interface ThresholdValueTriggerLogMapper extends BaseMapper{
    int deleteByPrimaryKey(String logUuid);
    
    int deleteByThresHoldValueUUId(String thresHoldValueUuid);

    int insert(ThresholdValueTriggerLog record);

    int insertSelective(ThresholdValueTriggerLog record);

    ThresholdValueTriggerLog selectByPrimaryKey(String logUuid);

    int updateByPrimaryKeySelective(ThresholdValueTriggerLog record);

    int updateByPrimaryKey(ThresholdValueTriggerLog record);

	/**
	 * @param pager
	 * @param searchCondition
	 * @return
	 */
	List<ThresholdValueTriggerLog> findPageList(@Param("pager")Pager<ThresholdValueTriggerLog> pager, 
			@Param("condition")JSONArray searchCondition);

	/**
	 * @param searchCondition
	 * @return
	 */
	Long getCount(@Param("condition")JSONArray searchCondition);

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<SurveyDTO> findThresholdValueMap(@Param("startDate")Date startDate,@Param("endDate")Date endDate);

	long deleteExpiredLog(@Param("expireDate")Date expireDate);
	
	/**
	 * 最近一分钟的报警次数
	 * @return
	 */
	List<HostHealth> getLatestCount();
}