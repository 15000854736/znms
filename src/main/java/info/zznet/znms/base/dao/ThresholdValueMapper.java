package info.zznet.znms.base.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.ThresholdValue;
import info.zznet.znms.web.module.common.page.Pager;

@Repository("thresholdValueMapper")
public interface ThresholdValueMapper extends BaseMapper{
    int deleteByPrimaryKey(String thresholdValueUuid);

    int insert(ThresholdValue record);

    int insertSelective(ThresholdValue record);

    ThresholdValue selectByPrimaryKey(String thresholdValueUuid);

    int updateByPrimaryKeySelective(ThresholdValue record);

    int updateByPrimaryKey(ThresholdValue record);

	/**
	 * @param pager
	 * @param searchCondition
	 * @return
	 */
	List<ThresholdValue> findPageList(@Param("pager")Pager<ThresholdValue> pager,
			@Param("condition")JSONArray searchCondition);

	/**
	 * @param searchCondition
	 * @return
	 */
	Long getCount(@Param("condition")JSONArray searchCondition);

	/**
	 * @param uuidList
	 */
	void updateThresholdValueEnable(@Param("uuidList")List<String> uuidList);

	/**
	 * @param uuidList
	 */
	void updateThresholdValueDisable(@Param("uuidList")List<String> uuidList);

	/**
	 * 根据主机uuid删除阀值
	 * @param hostUuid
	 */
	void deleteByHostUuid(@Param("hostUuid")String hostUuid);

	/**
	 * 根据图形uuid删除阀值
	 * @param graphUuid
	 */
	void deleteByGraphUuid(@Param("graphUuid")String graphUuid);

	/**
	 * @return
	 */
	List<ThresholdValue> findAll();

	/**
	 * @param thresholdValueUuid
	 * @param thresholdValueName
	 * @return
	 */
	ThresholdValue checkThresholdValueName(@Param("thresholdValueUuid")String thresholdValueUuid,
			@Param("thresholdValueName")String thresholdValueName);

	/**
	 * 根据主机uuid、图形uuid和流向判断该种阀值是否存在
	 * @param hostUuid
	 * @param gragphUuid
	 * @param flowDirection
	 * @return
	 */
	ThresholdValue chekcIsExistValue(@Param("hostUuid")String hostUuid, @Param("graphUuid")String gragphUuid,
			@Param("flowDirection")int flowDirection);

	/**
	 * @param graphUuid
	 * @return
	 */
	List<ThresholdValue> findByGrapUuid(@Param("graphUuid")String graphUuid);
	
	List<ThresholdValue> findByTime(@Param("startDate")String startDate,
			@Param("endDate")String endDate);
	

}