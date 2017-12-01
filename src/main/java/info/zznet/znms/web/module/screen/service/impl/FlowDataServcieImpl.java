package info.zznet.znms.web.module.screen.service.impl;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import info.zznet.znms.base.dao.FlowDataMapper;
import info.zznet.znms.base.entity.FlowData;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.module.common.page.Order;
import info.zznet.znms.web.module.common.page.PageBounds;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.screen.service.FlowDataService;

@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("flowDataService")
public class FlowDataServcieImpl implements FlowDataService{

	@Autowired
	private FlowDataMapper flowDataMapper;

	@Override
	public PageBounds getPageBounds(Pager pager, List orders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pager findPagedObjects(Pager pager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pager findPagedObjects(Pager pager, Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pager findPagedObjects(Pager pager, List orders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getQueryClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByPage(PageBounds bounds, Object t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count(Object t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int findCountByPage(Pager pager) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<FlowData> findAll() {
		return flowDataMapper.findAll();
	}

	@Override
	public int addFlowData(String data) {
		FlowData flowData=new FlowData();
		flowData.setId(UUIDGenerator.getGUID());
		flowData.setRecordDate(new Date());
		JSONObject jsonObject=JSONObject.fromObject(data);
		
		JSONObject studentFlow=JSONObject.fromObject(JSONObject.fromObject(jsonObject.getString("flow")).get("学生"));
		String studentPcFlowPerDay=studentFlow.getString("pcFlowPerDay");
		String studentWirelessFlowPerDay=studentFlow.getString("wirelessFlowPerDay");
		
		flowData.setStudentPcFlow(Double.valueOf(studentPcFlowPerDay));
		flowData.setStudentWirelessFlow(Double.valueOf(studentWirelessFlowPerDay));
		
		JSONObject teacherFlow=JSONObject.fromObject(JSONObject.fromObject(jsonObject.getString("flow")).get("教师"));
		String teacherPcFlowPerDay=teacherFlow.getString("pcFlowPerDay");
		String teacherWirelessFlowPerDay=teacherFlow.getString("wirelessFlowPerDay");
		
		flowData.setTeacherPcFlow(Double.valueOf(teacherPcFlowPerDay));
		flowData.setTeacherWirelessFlow(Double.valueOf(teacherWirelessFlowPerDay));
		return flowDataMapper.insert(flowData);
	}
	
	

}
