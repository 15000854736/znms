package info.zznet.znms.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.MemoryTemplate;

@Repository("memoryTemplateMapper")
public interface MemoryTemplateMapper extends BaseMapper{
    int deleteByPrimaryKey(String memoryTemplateUuid);

    int insert(MemoryTemplate record);

    int insertSelective(MemoryTemplate record);

    MemoryTemplate selectByPrimaryKey(String memoryTemplateUuid);

    int updateByPrimaryKeySelective(MemoryTemplate record);

    int updateByPrimaryKey(MemoryTemplate record);

	/**
	 * @param memoryTemplateName
	 * @return
	 */
	MemoryTemplate findByMemoryTemplateName(@Param("memoryTemplateName")String memoryTemplateName);

	/**
	 * @return
	 */
	List<MemoryTemplate> findAll();

	/**
	 * @param graphTemplateUuid
	 * @return
	 */
	MemoryTemplate checkMemoryGraphTemplateRepeat(@Param("graphTemplateUuid")String graphTemplateUuid);
}