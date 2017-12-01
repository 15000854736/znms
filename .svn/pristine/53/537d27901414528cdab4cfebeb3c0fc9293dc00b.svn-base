package info.zznet.znms.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.GraphTemplate;

@Repository("graphTemplateMapper")
public interface GraphTemplateMapper extends BaseMapper{
    int deleteByPrimaryKey(String graphTemplateUuid);

    int insert(GraphTemplate record);

    int insertSelective(GraphTemplate record);

    GraphTemplate selectByPrimaryKey(String graphTemplateUuid);

    int updateByPrimaryKeySelective(GraphTemplate record);

    int updateByPrimaryKey(GraphTemplate record);

	/**
	 * 查找所有图形模板
	 * @return
	 */
	List<GraphTemplate> findAll();

	/**
	 * 查找主机可用的图形模板
	 * @param hostUuid
	 * @return
	 */
	List<GraphTemplate> findApplicableGraphTemplateByHost(@Param("hostUuid")String hostUuid);
}