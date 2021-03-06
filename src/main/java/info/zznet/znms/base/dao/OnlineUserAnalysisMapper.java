package info.zznet.znms.base.dao;

import info.zznet.znms.base.entity.OnlineUserAnalysis;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("onlineUserAnalysisMapper")
public interface OnlineUserAnalysisMapper extends BaseMapper{
    int deleteByPrimaryKey(String onlineUserAnalysisUuid);

    int insert(OnlineUserAnalysis record);

    int insertSelective(OnlineUserAnalysis record);

    OnlineUserAnalysis selectByPrimaryKey(String onlineUserAnalysisUuid);

    int updateByPrimaryKeySelective(OnlineUserAnalysis record);

    int updateByPrimaryKey(OnlineUserAnalysis record);

	/**
	 * @param startTime
	 * @param endTime
	 * @param userType
	 * @return
	 */
    Integer findUserCountByCondition(@Param("startTime")String startTime, 
			@Param("endTime")String endTime, @Param("userType")Integer userType);
    
    int findByTypeAndTime(@Param("type")Integer type, @Param("time")Date time);
    
    void deleteExpiredData(@Param("expireDate")Date expireDate);
}