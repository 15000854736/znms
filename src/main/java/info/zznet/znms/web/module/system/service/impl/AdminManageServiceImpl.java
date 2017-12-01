package info.zznet.znms.web.module.system.service.impl;

import info.zznet.znms.base.dao.SmAdminMapper;
import info.zznet.znms.base.dao.SmRoleMapper;
import info.zznet.znms.base.entity.SmAdmin;
import info.zznet.znms.base.entity.SmRole;
import info.zznet.znms.web.module.common.page.PageBounds;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.system.bean.SmAdminBean;
import info.zznet.znms.web.module.system.service.AdminManageService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * @author yuanjingtao
 *
 * @param <T1> 分页实体
 * @param <T2> 查询实体
 */
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
@SuppressWarnings("unchecked")
@Service("adminManageService")
public class AdminManageServiceImpl <T1, T2> extends BaseServiceImpl<T1, T2> implements AdminManageService<T1, T2> {

	@Autowired
	private SmAdminMapper<T1,T2> smAdminMapper;
	@Autowired
	private SmRoleMapper smRoleMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return smAdminMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByUuidList(List<String> uuidList) {
		smAdminMapper.deleteByUuidList(uuidList);
	}

	@Override
	public List<SmAdmin> findAdminInfos(PageBounds bounds, SmAdminBean smAdminBean){
		return smAdminMapper.findAdminInfos(bounds,smAdminBean);
	}

	@Override
	public SmAdmin findById(String id) {
		return smAdminMapper.findByPrimaryKey(id);
	}

	@Override
	public List<SmRole> findRoleList() {
		return smRoleMapper.findAll();
	}

	@Override
	public int findByAdminId(String adminId) {
		List<SmAdmin> admin = smAdminMapper.findByAdminId(adminId);
		return admin== null ? 0 : admin.size() ;
	}

	@Override
	public int insert(SmAdmin record) {
		// TODO Auto-generated method stub
		return smAdminMapper.insert(record);
	}

	@Override
	public int insertSelective(SmAdmin record) {
		// TODO Auto-generated method stub
		return smAdminMapper.insertSelective(record);
	}

	@Override
	public SmAdmin findByPrimaryKey(String adminUuid) {
		// TODO Auto-generated method stub
		return smAdminMapper.findByPrimaryKey(adminUuid);
	}

	@Override
	public int updateByPrimaryKeySelective(SmAdmin record) {
		// TODO Auto-generated method stub
		return smAdminMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SmAdmin record) {
		// TODO Auto-generated method stub
		return smAdminMapper.updateByPrimaryKey(record);
	}
	
	@Override
	public Class<SmAdminBean> getQueryClass() {
		return SmAdminBean.class;
	}
	
	public List<T1> findByPage(PageBounds bounds, T2 bean) {
		return (List<T1>) smAdminMapper.findAdminInfos(bounds, (SmAdminBean)bean);
	}

	@Override
	public int count(T2 t) {
		// TODO Auto-generated method stub
		return smAdminMapper.count(t);
	}
}
