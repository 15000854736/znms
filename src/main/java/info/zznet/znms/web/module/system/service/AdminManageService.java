package info.zznet.znms.web.module.system.service;

import info.zznet.znms.base.entity.SmAdmin;
import info.zznet.znms.base.entity.SmRole;
import info.zznet.znms.web.module.common.page.PageBounds;
import info.zznet.znms.web.module.common.service.BaseService;
import info.zznet.znms.web.module.system.bean.SmAdminBean;

import java.util.List;
/**
 * 管理员管理service类
 * @author yuanjingtao
 *
 * @param <T1> 分页实体
 * @param <T2> 查询实体
 */
public interface AdminManageService<T1, T2> extends BaseService<T1, T2> {
	public int deleteByPrimaryKey(String adminUuid);
	public int insert(SmAdmin record);
	public int insertSelective(SmAdmin record);
	public SmAdmin findByPrimaryKey(String adminUuid);
	public int updateByPrimaryKeySelective(SmAdmin record);
	public int updateByPrimaryKey(SmAdmin record);
	public void deleteByUuidList(List<String> uuids);
	/**
	 * 分页查询方法
	 * @param bounds 传入分页实体
	 * @param smAdminBean
	 * @return
	 */
	List<SmAdmin> findAdminInfos(PageBounds bounds, SmAdminBean smAdminBean);
	int count(T2 t);
	/**
	 * 通过id获取管理员信息
	 * @param id
	 * @return
	 */
	public SmAdmin findById(String id);
	/**
	 * 查找所有的角色list
	 * @return
	 */
	public List<SmRole> findRoleList();
	/**
	 * 获取指定的管理员的账号的管理员数量
	 * @param adminId 管理员账号
	 * @return
	 */
	public int findByAdminId(String adminId);
	
}
