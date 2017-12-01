package info.zznet.znms.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.ExportLink;
import info.zznet.znms.base.entity.MemoryTemplate;

@Repository("exportLinkMapper")
public interface ExportLinkMapper extends BaseMapper{
    int deleteByPrimaryKey(String exportLinkUuid);

    int insert(ExportLink record);

    int insertSelective(ExportLink record);

    ExportLink selectByPrimaryKey(String exportLinkUuid);

    int updateByPrimaryKeySelective(ExportLink record);

    int updateByPrimaryKey(ExportLink record);

	/**
	 * @param hostUuid
	 * @param graphUuid
	 * @return
	 */
	ExportLink checkExportLinkRepeat(@Param("hostUuid")String hostUuid, @Param("graphUuid")String graphUuid);

	/**
	 * @return
	 */
	List<ExportLink> findAll();

	/**
	 * 根据主机UUID删除出口链路配置
	 * @param hostUuid
	 */
	void deleteByHostUuid(@Param("hostUuid")String hostUuid);

	/**
	 * 根据图形uuid删除出口链路配置
	 * @param graphUuid
	 */
	void deleteByGraphUuid(@Param("graphUuid")String graphUuid);
	
	/**
	 * 查询最新的三个
	 * @return
	 */
	List<ExportLink> findLatestThree();

}