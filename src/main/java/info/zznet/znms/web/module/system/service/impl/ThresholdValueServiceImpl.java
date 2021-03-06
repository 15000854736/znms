/**
 * 
 */
package info.zznet.znms.web.module.system.service.impl;

import info.zznet.znms.base.dao.ThresholdValueMapper;
import info.zznet.znms.base.entity.ThresholdValue;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.system.service.ThresholdValueService;
import info.zznet.znms.web.start.SystemStartThread;
import info.zznet.znms.web.start.ThresholdValueThread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dell001
 *
 */
@Service("thresholdVlaueService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class ThresholdValueServiceImpl extends BaseServiceImpl implements ThresholdValueService{

	@Autowired
	private ThresholdValueMapper thredsholdValueMapper;

	@Override
	public Pager<ThresholdValue> findPageList(Pager<ThresholdValue> pager) {
		JSONArray searchCondition = null;
		if (pager != null && !StringUtil.isNullString(pager.getSearch())) {
			searchCondition = JSONArray.fromObject(pager.getSearch());
		}
		List<ThresholdValue> list = thredsholdValueMapper.findPageList(pager,
				searchCondition);
		pager.setRows(list);
		pager.setTotal(thredsholdValueMapper.getCount(searchCondition));
		return pager;
	}
	
	@Override
	public List<ThresholdValue> findByTime(String startDate,
			String endDate){
		return thredsholdValueMapper.findByTime(startDate, endDate);
	}
	
	@Override
	public String add(ThresholdValue thresholdValue) {
		//校验这类阀值是否已经存在
		String hostUuid = thresholdValue.getHostUuid();
		String gragphUuid = thresholdValue.getGraphUuid();
		int flowDirection = thresholdValue.getFlowDirection();
		ThresholdValue thre = thredsholdValueMapper.chekcIsExistValue(hostUuid, gragphUuid, flowDirection);
		if(null!=thre){
			return "{\"result\":false,\"msg\":\"已存在这种阀值\"}";
		}
		thresholdValue.setThresholdValueUuid(UUIDGenerator.getGUID());
		thresholdValue.setCreateTime(new Date());
		thredsholdValueMapper.insert(thresholdValue);
		return "{\"result\":true}";
	}
	

	@Override
	public void update(ThresholdValue thresholdValue) {
		thredsholdValueMapper.updateByPrimaryKey(thresholdValue);
	}

	@Override
	public ThresholdValue selectByPrimaryKey(String thresholdValueUuid) {
		return thredsholdValueMapper.selectByPrimaryKey(thresholdValueUuid);
	}

	@Override
	public void deleteByPrimaryKey(String thresholdValueUuid) {
		ThresholdValue value = thredsholdValueMapper.selectByPrimaryKey(thresholdValueUuid);
		thredsholdValueMapper.deleteByPrimaryKey(thresholdValueUuid);
	}

	@Override
	public void deleteThresholdValueList(List<String> uuids) {
		for (String thresholdValueUuid : uuids) {
			deleteByPrimaryKey(thresholdValueUuid);
		}
	}

	@Override
	public void updateThresholdValueEnable(List<String> uuidList) {
		thredsholdValueMapper.updateThresholdValueEnable(uuidList);
	}

	@Override
	public void updateThresholdValueDisable(List<String> uuidList) {
		thredsholdValueMapper.updateThresholdValueDisable(uuidList);
	}

	@Override
	public List<ThresholdValue> findAll() {
		return thredsholdValueMapper.findAll();
	}

	@Override
	public ThresholdValue checkThresholdValueName(String thresholdValueUuid,
			String thresholdValueName) {
		return thredsholdValueMapper.checkThresholdValueName(thresholdValueUuid, thresholdValueName);
	}

}
