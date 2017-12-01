package info.zznet.znms.base.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("sysPartitionMapper")
public interface SysPartitionMapper extends BaseMapper {
	
	/**
	 * 执行按月分区
	 */
	void doPartitionByMonth(@Param("tableName")String tableName);
	
	/**
	 * 执行按年分区
	 */
	void doPartitionByYear(@Param("tableName")String tableName);
}