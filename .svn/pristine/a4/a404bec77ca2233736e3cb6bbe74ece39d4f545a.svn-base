package info.zznet.znms.softdog;

import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.entity.AuthResult;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.base.util.SpringContextUtil;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.module.security.controller.AuthResultController;
import info.zznet.znms.web.module.security.service.AuthResultService;
import info.zznet.znms.web.util.ConfigUtil;

import java.util.Date;

import net.sf.json.JSONObject;

public class DogDecryptAuthData {

	
	private static AuthResultService authResultService = null;
	
	public void run() {
		if(authResultService == null){
			authResultService = (AuthResultService) SpringContextUtil.getBean("authResultService");
		}
		try {
			if (!checkAuth())
				System.exit(0);
			ZNMSLogger.info("授权校验通过");
		} catch (Exception e) {
			ZNMSLogger.info("授权校验失败，退出系统."+e);
			System.exit(0);
		}
	}

	/**
	 * 判断是否通过校验
	 * @return
	 */
	private boolean checkAuth() {
		AuthResult authResult = authResultService.findAuthCodeInfo();
		if(authResult!=null){
			JSONObject jsonObject2 = authResultService.decryptAuthResultByPrivateKey(authResult);
			
			Long   dateLong = (Long)((JSONObject) jsonObject2.get("authDate")).get("time");
			Date endDate = new Date(dateLong);
			if(authResult.getExpiryDate()!=null){
				endDate = endDate.after(authResult.getExpiryDate())?authResult.getExpiryDate():endDate;
			}
			if(endDate.before(new Date())){//过期了
				ZNMSLogger.info("授权校验失败,授权码过期了!");
				return  false;
			}
			//判断mac地址
			String serverMac = (String)jsonObject2.get("serverMac");
			if(StringUtil.isNotEmpty(serverMac)){
				if(!serverMac.equals(new AuthResultController().getNetworkMac().get(SystemConstants.MAC_KEY))){
					ZNMSLogger.info("授权校验失败,授权码与服务器MAC不匹配!");
					return false;
				}
			}
			ConfigUtil.put(SystemConstants.SYS_EFFECTIVE_TIME, DateUtil.dateToStr(endDate, DateUtil.DF_yyyyMMddHHmmss));
		}else{
			ZNMSLogger.info("授权校验失败,系统未激活或者授权码信息不存在!");
			return  false;
		}
		return true;
	}
	
}

