package info.zznet.znms.base.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.SystemOption;

@Repository("systemOptionMapper")
public interface SystemOptionMapper extends BaseMapper{
    int deleteByPrimaryKey(String systemOptionUuid);

    int insert(SystemOption record);

    int insertSelective(SystemOption record);

    SystemOption selectByPrimaryKey(String systemOptionUuid);
    
    int updateByPrimaryKeySelective(SystemOption record);

    int updateByPrimaryKey(SystemOption record);
    
	/**
	 * @return
	 */
	List<SystemOption> findAll();

	/**
	 * @param generateSystemOptionMap
	 * @param keyList
	 */
	void batchUpdateSystemOption(
			@Param("map")HashMap<String, String> generateSystemOptionMap,
			@Param("columnList")List<String> keyList);

}