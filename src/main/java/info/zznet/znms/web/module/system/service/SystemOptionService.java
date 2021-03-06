/**
 * 
 */
package info.zznet.znms.web.module.system.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import info.zznet.znms.base.entity.SystemOption;
import info.zznet.znms.web.module.common.service.BaseService;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;

/**
 * @author dell001
 *
 */
public interface SystemOptionService extends BaseService{

	/**
	 * @return
	 */
	SystemOptionBean getSystemOptionData();

	/**
	 * @param bean
	 */
	void updateSystemOption(SystemOptionBean bean);

	/**
	 * @param image
	 * @return
	 */
	String addApMap(MultipartFile image,String imgName,HttpServletRequest req);
	
}
