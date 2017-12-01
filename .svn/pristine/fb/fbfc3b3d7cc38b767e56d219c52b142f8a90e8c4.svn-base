package info.zznet.znms.base.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.GraphTree;
import info.zznet.znms.web.module.common.page.Pager;

@Repository("graphTreeMapper")
public interface GraphTreeMapper extends BaseMapper{
    int deleteByPrimaryKey(String graphTreeUuid);

    int insert(GraphTree record);

    int insertSelective(GraphTree record);

    GraphTree selectByPrimaryKey(String graphTreeUuid);

    int updateByPrimaryKeySelective(GraphTree record);

    int updateByPrimaryKey(GraphTree record);

	/**
	 * @param pager
	 * @param searchCondition
	 * @return
	 */
	List<GraphTree> findPageList(@Param("pager")Pager<GraphTree> pager,
			@Param("condition")JSONArray searchCondition);

	/**
	 * @param searchCondition
	 * @return
	 */
	Long getCount(@Param("condition")JSONArray searchCondition);

	/**
	 * @param graphTreeName
	 * @param graphTreeUuid
	 * @return
	 */
	GraphTree checkNameRepeat(@Param("graphTreeName")String graphTreeName, @Param("graphTreeUuid")String graphTreeUuid);

	/**
	 * 查询所有图形树
	 * @return
	 */
	List<GraphTree> findAll();

}