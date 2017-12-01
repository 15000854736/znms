package info.zznet.znms.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.CpuTemplate;

@Repository("cpuTemplateMapper")
public interface CpuTemplateMapper extends BaseMapper{
    int deleteByPrimaryKey(String cpuTemplateUuid);

    int insert(CpuTemplate record);

    int insertSelective(CpuTemplate record);

    CpuTemplate selectByPrimaryKey(String cpuTemplateUuid);

    int updateByPrimaryKeySelective(CpuTemplate record);

    int updateByPrimaryKey(CpuTemplate record);

	/**
	 * @param cpuTemplate
	 */
	void addCpuTemplate(CpuTemplate cpuTemplate);

	/**
	 * @return
	 */
	List<CpuTemplate> findAll();

	/**
	 * 查询该种cpu模板是否存在
	 * @param graphTemplateUuid
	 * @return
	 */
	CpuTemplate checkCpuGraphTemplateRepeat(@Param("graphTemplateUuid")String graphTemplateUuid);
}