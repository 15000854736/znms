package info.zznet.znms.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.GraphOid;

@Repository("graphOidMapper")
public interface GraphOidMapper extends BaseMapper{
    int deleteByPrimaryKey(String graphOidUuid);

    int insert(GraphOid record);

    int insertSelective(GraphOid record);

    GraphOid selectByPrimaryKey(String graphOidUuid);

    int updateByPrimaryKeySelective(GraphOid record);

    int updateByPrimaryKey(GraphOid record);

	/**
	 * @param graphUuid
	 */
	void deleteByGraphUuid(@Param("graphUuid")String graphUuid);

	/**
	 * 根据主机uuid删除graphoid
	 * @param hostUuid
	 */
	void deleteGraphByHostUuid(@Param("hostUuid")String hostUuid);

	/**
	 * 根据主机uuid和oid查找graphoid
	 * @param hostUuid
	 * @param oid
	 * @return
	 */
	GraphOid findGraphOidByInterface(@Param("hostUuid")String hostUuid, @Param("oid")String oid);

	/**
	 * @param hostUuid
	 * @return
	 */
	List<GraphOid> findByHostUuid(@Param("hostUuid")String hostUuid);
}