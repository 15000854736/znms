/**
 * 
 */
package info.zznet.znms.web.module.system.service.impl;

import info.zznet.znms.base.dao.SystemLogDeleteRuleMapper;
import info.zznet.znms.base.entity.SystemLogDeleteRule;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.system.service.SystemLogDeleteRuleService;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dell001
 *
 */
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("systemLogDeleteRuleService")
public class SystemLogDeleteRuleServiceImpl extends BaseServiceImpl implements SystemLogDeleteRuleService{

	@Autowired
	private SystemLogDeleteRuleMapper ruleMapper;

	public Pager<SystemLogDeleteRule> findPageList(
			Pager<SystemLogDeleteRule> pager) {
		JSONArray searchCondition = null;
		if (pager != null && !StringUtil.isNullString(pager.getSearch())) {
			searchCondition = JSONArray.fromObject(pager.getSearch());
		}
		List<SystemLogDeleteRule> list = ruleMapper.findPageList(pager,
				searchCondition);
		pager.setRows(list);
		pager.setTotal(ruleMapper.getCount(searchCondition));
		return pager;
	}

	@Override
	public void addRule(SystemLogDeleteRule rule) {
		rule.setCreateTime(new Date());
		rule.setSystemLogDeleteRuleUuid(UUIDGenerator.getGUID());
		ruleMapper.insert(rule);
		WebRuntimeData.instance.saveRule(rule);
	}

	@Override
	public void updateRule(SystemLogDeleteRule rule) {
		ruleMapper.updateByPrimaryKey(rule);
		WebRuntimeData.instance.saveRule(rule);
	}

	@Override
	public SystemLogDeleteRule findRuleByUuid(String uuid) {
		return ruleMapper.selectByPrimaryKey(uuid);
	}

	@Override
	public void deleteByPrimaryKey(String uuid) {
		ruleMapper.deleteByPrimaryKey(uuid);
		WebRuntimeData.instance.deleteRule(uuid);
	}

	@Override
	public void deleteUuidList(List<String> uuids) {
		for (String uuid : uuids) {
			ruleMapper.deleteByPrimaryKey(uuid);
			WebRuntimeData.instance.deleteRule(uuid);
		}
	}

	@Override
	public void updateRuleEnable(List<String> uuidList) {
		ruleMapper.updateRuleEnable(uuidList);
		if(uuidList != null){
			for(String id : uuidList){
				WebRuntimeData.instance.toggleRule(true, id);
			}
		}
	}

	@Override
	public void updateRuleDisable(List<String> uuidList) {
		 ruleMapper.updateRuleDisable(uuidList);
		 if(uuidList != null){
			for(String id : uuidList){
				WebRuntimeData.instance.toggleRule(false, id);
			}
		}
	}

	@Override
	public SystemLogDeleteRule checkRuleRepeat(String systemLogDeleteRuleUuid,
			String ruleName) {
		return ruleMapper.checkRuleRepeat(systemLogDeleteRuleUuid, ruleName);
	}
}
