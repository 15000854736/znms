/**
 * 
 */
package info.zznet.znms.web.module.system.service;

import java.util.List;

import info.zznet.znms.base.entity.SystemLogDeleteRule;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.BaseService;

/**
 * @author dell001
 *
 */
public interface SystemLogDeleteRuleService extends BaseService{

	/**
	 * @param pager
	 * @return
	 */
	Pager<SystemLogDeleteRule> findPageList(Pager<SystemLogDeleteRule> pager);

	/**
	 * @param rule
	 */
	void addRule(SystemLogDeleteRule rule);

	/**
	 * @param rule
	 */
	void updateRule(SystemLogDeleteRule rule);

	/**
	 * @param uuid
	 * @return
	 */
	SystemLogDeleteRule findRuleByUuid(String uuid);

	/**
	 * @param uuid
	 */
	void deleteByPrimaryKey(String uuid);

	/**
	 * @param uuids
	 */
	void deleteUuidList(List<String> uuids);

	/**
	 * @param uuidList
	 */
	void updateRuleEnable(List<String> uuidList);

	/**
	 * @param uuidList
	 */
	void updateRuleDisable(List<String> uuidList);

	/**
	 * @param systemLogDeleteRuleUuid
	 * @param ruleName
	 * @return
	 */
	SystemLogDeleteRule checkRuleRepeat(String systemLogDeleteRuleUuid, String ruleName);

}
