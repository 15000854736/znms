package info.zznet.znms.web.module.security.service.impl;

import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.dao.AuthResultMapper;
import info.zznet.znms.base.entity.AuthResult;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.softdog.DogDecryptAuthData;
import info.zznet.znms.softdog.DogRsaUtil;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.security.controller.AuthResultController;
import info.zznet.znms.web.module.security.service.AuthResultService;
import info.zznet.znms.web.util.ConfigUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("authResultService")
public class AuthResultServiceImpl extends BaseServiceImpl  implements AuthResultService {
	
	@Autowired
	private AuthResultMapper authResultMapper;
	
	@Override
	public void checkOnStart() {
		List<AuthResult> authResultList= authResultMapper.findAll();
        AuthResult authResult = null;
        if (authResultList!=null && authResultList.size()>0){
            authResult = authResultList.get(0);
        }
        if (authResult!=null){
	        JSONObject jsonObject2 = decryptAuthResultByPrivateKey(authResult);
	        Long   dateLong = (Long)((JSONObject) jsonObject2.get("authDate")).get("time");
	        Date endDate = new Date(dateLong);
	        if(authResult.getExpiryDate()!=null){
	            endDate = endDate.after(authResult.getExpiryDate())?authResult.getExpiryDate():endDate;
	        }
	        ConfigUtil.put(SystemConstants.SYS_EFFECTIVE_TIME, DateUtil.dateToStr(endDate, DateUtil.DF_yyyyMMddHHmmss));
	        if(authResult.getExpiryDate()==null){
	        	ConfigUtil.put(SystemConstants.SYS_ACTIVATION_STATE, "true");
	        }else{
	        	ConfigUtil.put(SystemConstants.SYS_ACTIVATION_STATE, "false");
	        }
        }
	}

	  @Override
	  public JSONObject decryptAuthResultByPrivateKey(AuthResult authResult) {
	        String desc = decryptFileContextByPrivateKey(authResult.getReturnMsg());
	        JSONObject jsonObject2 = JSONObject.fromObject(desc);
	        return jsonObject2;
	    }

	    @Override
	    public String decryptFileContextByPrivateKey(String fileContext) {
	        byte[] decodeBytes = Base64.decodeBase64(fileContext);
	        byte[] returnByte = new byte[0];
	        try {
	            returnByte = DogRsaUtil.decryptByPrivateKey(decodeBytes, DogRsaUtil.PRIVATE_KEY);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return new String(returnByte);
	    }

	@Override
	public AuthResult findAuthCodeInfo() {
	  List<AuthResult> authResultList= authResultMapper.findAll();
        AuthResult authResult = null;
        if (authResultList!=null && authResultList.size()>0){
            authResult = authResultList.get(0);
        }
        return authResult;
	}

	@Override
	public int update(AuthResult authResult) {
		return authResultMapper.updateByPrimaryKey(authResult);
	}

	@Override
	public int add(AuthResult authResult) {
		return authResultMapper.insert(authResult);
	}

}
