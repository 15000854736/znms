package info.zznet.znms.base.dao;

import info.zznet.znms.base.bean.HostHealth;
import info.zznet.znms.base.entity.SystemLog;
import info.zznet.znms.base.entity.SystemLogStatistics;
import info.zznet.znms.web.module.common.page.Pager;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("systemLogMapper")
public interface SystemLogMapper extends BaseMapper{
    int deleteByPrimaryKey(Long seq);

    int insert(SystemLog record);

    int insertSelective(SystemLog record);

    SystemLog selectByPrimaryKey(Long seq);

    int updateByPrimaryKeySelective(SystemLog record);

    int updateByPrimaryKey(SystemLog record);

	/**
	 * 查询系统日志显示数据
	 * @param pager
	 * @param searchCondition
	 * @return
	 */
	List<SystemLog> findPageList(@Param("pager")Pager pager, @Param("condition")JSONArray searchCondition);

	/**
	 * 查询系统日志显示数据条数
	 * @param searchCondition
	 * @return
	 */
	Long getCount(@Param("condition")JSONArray searchCondition);
	
	/**
	 * 删除旧数据
	 * @param num
	 * @return
	 */
	long deleteOldLog(@Param("num")long num);
	
	/**
	 * 删除过期数据
	 * @param expireDate
	 * @return
	 */
	long deleteExpiredLog(@Param("expireDate")Date expireDate);
	
	/**
	 * 分区
	 */
	void doPartitionByMonth(@Param("tableName")String tableName);

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<SystemLogStatistics> findLogStatisticsList(@Param("startDate")String startDate,
			@Param("endDate")String endDate);

	/**
	 * 查询首页当天各级别告警数
	 * @param emergyArray
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	long getNetHeathData(@Param("priorityIdArray")int[] emergyArray, 
			@Param("startDate")String startDate, @Param("endDate")String endDate);
	
	/**
	 * 按严重度获取最近1分钟内的日志数
	 * @param severity
	 * @return
	 */
	List<HostHealth> getCountBySeverity(@Param("severity")Integer severity);
}