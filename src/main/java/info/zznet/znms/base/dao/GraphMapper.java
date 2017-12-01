package info.zznet.znms.base.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.Graph;
import info.zznet.znms.base.entity.dto.GraphDTO;
import info.zznet.znms.web.module.common.page.Pager;

@Repository("graphMapper")
public interface GraphMapper extends BaseMapper{
    int deleteByPrimaryKey(String graphUuid);

    int insert(Graph record);

    int insertSelective(Graph record);

    Graph selectByPrimaryKey(String graphUuid);

    int updateByPrimaryKeySelective(Graph record);

    int updateByPrimaryKey(Graph record);

	/**
	 * @param pager
	 * @param searchCondition
	 * @return
	 */
	List<Graph> findPageList(@Param("pager")Pager<Graph> pager, @Param("condition")JSONArray searchCondition);

	/**
	 * @param searchCondition
	 * @return
	 */
	Long getCount(@Param("condition")JSONArray searchCondition);

	/**根据主机uuid删除图形
	 * @param hostUuid
	 */
	void deleteGraphByHostUuid(@Param("hostUuid")String hostUuid);

	/**
	 * 根据主机uuid、图形类型和图形模板id查找图形
	 * @param hostUuid
	 * @param graphType
	 * @param graphTemplateId
	 * @return
	 */
	Graph findGraphByBasci(@Param("hostUuid")String hostUuid, @Param("graphType")int graphType,
			@Param("graphTemplateId")String graphTemplateId);

	/**
	 * @return
	 */
	List<Graph> findAll();

	/**
	 * @param hostUuid
	 * @return
	 */
	List<Graph> findGraphByHost(@Param("hostUuid")String hostUuid);

	/**
	 * @param graphUuid
	 * @return
	 */
	int checkGraphType(@Param("graphUuid")String graphUuid);

	/**
	 * @param id
	 * @param basicGraphType
	 * @param cpuTypeId
	 * @return
	 */
	GraphDTO findGraphDTO(@Param("hostUuid")String id, @Param("basicGraphType")Integer basicGraphType, @Param("cpuTypeId")String cpuTypeId);

}