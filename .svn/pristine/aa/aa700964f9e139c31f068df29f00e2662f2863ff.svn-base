package info.zznet.znms.base.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import info.zznet.znms.base.entity.SystemLogDeleteRule;
import info.zznet.znms.web.module.common.page.Pager;

@Repository("systemLogDeleteRuleMapper")
public interface SystemLogDeleteRuleMapper extends BaseMapper{
    int deleteByPrimaryKey(String systemLogDeleteRuleUuid);

    int insert(SystemLogDeleteRule record);

    int insertSelective(SystemLogDeleteRule record);

    SystemLogDeleteRule selectByPrimaryKey(String systemLogDeleteRuleUuid);

    int updateByPrimaryKeySelective(SystemLogDeleteRule record);

    int updateByPrimaryKey(SystemLogDeleteRule record);

	/**
	 * @param pager
	 * @param searchCondition
	 * @return
	 */
	List<SystemLogDeleteRule> findPageList(@Param("pager")Pager<SystemLogDeleteRule> pager,
			@Param("condition")JSONArray searchCondition);

	/**
	 * @param searchCondition
	 * @return
	 */
	Long getCount(@Param("condition")JSONArray searchCondition);

	/**
	 * @param uuidList
	 */
	void updateRuleEnable(@Param("uuidList")List<String> uuidList);

	/**
	 * @param uuidList
	 */
	void updateRuleDisable(@Param("uuidList")List<String> uuidList);

	/**
	 * @param systemLogDeleteRuleUuid
	 * @param ruleName
	 * @return
	 */
	SystemLogDeleteRule checkRuleRepeat(@Param("systemLogDeleteRuleUuid")String systemLogDeleteRuleUuid,
			@Param("ruleName")String ruleName);
}