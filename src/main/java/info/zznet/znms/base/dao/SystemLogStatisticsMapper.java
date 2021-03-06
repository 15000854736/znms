package info.zznet.znms.base.dao;

import info.zznet.znms.base.entity.SystemLogStatistics;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("systemLogStatisticsMapper")
public interface SystemLogStatisticsMapper extends BaseMapper{

	/**
	 * @param id
	 * @param hostId
	 * @param insertTimeFrom
	 * @param insertTimeTo
	 * @return
	 */
	List<SystemLogStatistics> findPieData(@Param("id")String id,
			@Param("hostId")String hostId, @Param("insertTimeFrom")String insertTimeFrom, 
			@Param("insertTimeTo")String insertTimeTo, @Param("type")Integer type);

	/**
	 * @param priorityId
	 * @param facilityId
	 * @param hostId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<SystemLogStatistics> findLineData(@Param("priorityId")String priorityId,
			@Param("facilityId")String facilityId, @Param("hostId")String hostId,
			@Param("startTime")String startTime, @Param("endTime")String endTime);
	
	int insert(SystemLogStatistics record);

}