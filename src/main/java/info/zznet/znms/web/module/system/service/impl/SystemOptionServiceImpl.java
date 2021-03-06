/**
 * 
 */
package info.zznet.znms.web.module.system.service.impl;

import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.dao.SystemOptionMapper;
import info.zznet.znms.base.entity.SystemOption;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.web.WebRuntimeData;
import info.zznet.znms.web.module.common.constants.SystemOptionEnums;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.system.bean.SystemOptionBean;
import info.zznet.znms.web.module.system.service.SystemOptionService;
import info.zznet.znms.web.util.ConfigUtil;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dell001
 *
 */
@Service("systemOptionService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class SystemOptionServiceImpl extends BaseServiceImpl implements SystemOptionService{

	@Autowired
	private SystemOptionMapper systemOptionMapper;
	

	@Override
	public SystemOptionBean getSystemOptionData() {
		List<SystemOption> list = systemOptionMapper.findAll();
		return new SystemOptionBean(list);
	}

	@Override
	public void updateSystemOption(SystemOptionBean bean) {
		systemOptionMapper.batchUpdateSystemOption(bean.generateSystemOptionMap(), SystemOptionEnums.SYSTEM_OPTION_KEY.getKeyList());
	}
	
	@Override
	public String addApMap(MultipartFile image,String imgName,HttpServletRequest request) {
		try {
			if(null != image){
				String strDirPath = request.getSession().getServletContext().getRealPath("/")+"resource/images/";
				String fileName = image.getOriginalFilename();
				if(fileName.lastIndexOf(".") <0){
					return "{\"result\":false,\"msg\":\"只允许格式为[jpg,png]其中之一的文件\"}";
				}
				String suffix = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
				if(!StringUtils.containsIgnoreCase(SystemConstants.AP_MAP_SUFFIX, suffix)){
					return "{\"result\":false,\"msg\":\"只允许格式为[jpg,png]其中之一的文件\"}";
				}
				
				if(imgName.equalsIgnoreCase("HOME-BG")||imgName.equalsIgnoreCase("INDEX-LOGO")||imgName.equalsIgnoreCase("SCHOOL-BG")){
					if(!StringUtils.containsIgnoreCase("png|PNG", suffix)){
						return "{\"result\":false,\"msg\":\"背景图只允许格式为[png]的文件\"}";
					}
				}
				if(imgName.equalsIgnoreCase("TOPO-BG")){
					if(!StringUtils.containsIgnoreCase("jpg|JPG", suffix)){
						return "{\"result\":false,\"msg\":\"背景图只允许格式为[jpg]的文件\"}";
					}
				}
				if(image.getSize() > ConfigUtil.getInt("school.map.max.size") * 1024){
					return "{\"result\":false,\"msg\":\"地图大小不能超过"+ConfigUtil.getInt("school.map.max.size")+"KB\"}";
				}
				String apMapName = imgName+"."+suffix;
				if(imgName.equalsIgnoreCase("TOPO-BG")){
					//覆盖原来的apMap
					File map = new File(strDirPath+apMapName);
					if(map.exists() && map.isFile()){
						map.delete();
					}
				}else{
					//覆盖原来的apMap
					File map = new File(ConfigUtil.getString("znms.image.path")+apMapName);
					if(map.exists() && map.isFile()){
						map.delete();
					}
				}
				
				if(imgName.equalsIgnoreCase("TOPO-BG")){
					//如果文件夹不存在则创建    
					File imageFileFolder = new File(strDirPath);
					if(!imageFileFolder.exists() || !imageFileFolder.isDirectory()){
						imageFileFolder.mkdirs();
					}
					File mapFile = new File(strDirPath+apMapName);
					mapFile.createNewFile();
					image.transferTo(mapFile);
				}else{
					//如果文件夹不存在则创建    
					File imageFileFolder = new File(ConfigUtil.getString("znms.image.path"));
					if(!imageFileFolder.exists() || !imageFileFolder.isDirectory()){
						imageFileFolder.mkdirs();
					}
					File mapFile = new File(ConfigUtil.getString("znms.image.path")+apMapName);
					mapFile.createNewFile();
					image.transferTo(mapFile);
				}
				return "{\"result\":true}";
			}else{
				return "{\"result\":false,\"msg\":\"地图不能为空\"}";
			}
		} catch (Exception e) {
			return "{\"result\":false,\"msg\":\""+e.getMessage()+"\"}";
		} 
	}
}
