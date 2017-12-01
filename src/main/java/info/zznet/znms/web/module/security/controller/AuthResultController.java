package info.zznet.znms.web.module.security.controller;

import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.entity.AuthResult;
import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.base.util.HttpClientUtil;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.UUIDGenerator;
import info.zznet.znms.softdog.DogRsaUtil;
import info.zznet.znms.web.module.security.service.AuthResultService;
import info.zznet.znms.web.util.ConfigUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 授权控制器
 */
@Controller
@RequestMapping(value = "/authResult")
public class AuthResultController {
	
    @Autowired
    private AuthResultService authResultService;
    
    public static Map<String,String> macMap=new HashMap<String, String>();
    
    
    @RequestMapping(value = "/check/serialNumber", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String authSerialNumber(@RequestParam("serialNumber") String serialNumber, HttpServletResponse response, HttpServletRequest request) {
        String mac = getNetworkMac().get(SystemConstants.MAC_KEY);
        String macEncrpt = null;
        if(mac!=null){
            try {
                byte[] encryptDataBytes  = DogRsaUtil.encryptByPublicKey(mac.getBytes("UTF-8"), DogRsaUtil.PUBLIC_KEY);
                macEncrpt = Base64.encodeBase64String(encryptDataBytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String data = null;
        try {
            byte[] encryptDataBytes  = DogRsaUtil.encryptByPublicKey(serialNumber.getBytes("UTF-8"), DogRsaUtil.PUBLIC_KEY);
            data = Base64.encodeBase64String(encryptDataBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            Map<String,String> paramMap = new HashMap<String, String>();
            paramMap.put("authData",data);
            paramMap.put("serverMac",macEncrpt);
            
            String AUTH_URL ="http://"+ ConfigUtil.getString("authorize.ip")+":"+ConfigUtil.getString("authorize.port")+"/soft-dog/rest/authCheck";
            //
            String  returnMsg = HttpClientUtil.getPostResponse(AUTH_URL, paramMap, null);
            if(returnMsg==null){
            	Map<String, String> textMap=new HashMap<String, String>();
            	textMap.put("false", "激活接口响应超时!");
                return  JSONObject.fromObject(textMap).toString();
            }
            JSONObject jsonObject = JSONObject.fromObject(returnMsg);
            Iterator it = jsonObject.keys();
            String key = "";
            String value = "";
            while (it.hasNext()) {
                key = (String) it.next();
                value = jsonObject.getString(key);
            }
            if(key.equals("200")){//激活成功
                AuthResult authResult = authResultService.findAuthCodeInfo();
                if(authResult!=null){
                    authResult.setResultType((byte)0);
                    authResult.setSerialNumber(serialNumber);
                    authResult.setReturnMsg(value);
                    authResult.setExpiryDate(null);
                    authResultService.update(authResult);
                }else{
	                authResult = new AuthResult();
	                authResult.setAuthResultUuid(UUIDGenerator.getGUID());
	                authResult.setResultType((byte)0);
	                authResult.setSerialNumber(serialNumber);
	                authResult.setReturnMsg(value);
	                authResultService.add(authResult);
                }
                byte[] decodeBytes = Base64.decodeBase64(value);
                try {
                    byte[] returnByte = DogRsaUtil.decryptByPrivateKey(decodeBytes, DogRsaUtil.PRIVATE_KEY);
                    String desc = new String(returnByte,"UTF-8");
                    JSONObject jsonObject2 = JSONObject.fromObject(desc);

                    Long   dateLong = (Long)((JSONObject) jsonObject2.get("authDate")).get("time");
                    Date endDate = new Date(dateLong);
                    ConfigUtil.put(SystemConstants.SYS_ACTIVATION_STATE, "true");
                    ConfigUtil.put(SystemConstants.SYS_EFFECTIVE_TIME, DateUtil.dateToStr(endDate,DateUtil.DF_yyyyMMddHHmmss));
                    return  "{\"result\":true}";
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }else{//激活失败 返回报错信息
                byte[] decodeBytes = Base64.decodeBase64(value);
                try {
                    byte[] returnByte = DogRsaUtil.decryptByPrivateKey(decodeBytes, DogRsaUtil.PRIVATE_KEY);
                    String desc = new String(returnByte,"UTF-8");
                	Map<String, String> textMap=new HashMap<String, String>();
                	textMap.put("false", desc);
                    return  JSONObject.fromObject(textMap).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            Map<String, String> textMap=new HashMap<String, String>();
            textMap.put("false", "调用激活接口失败!");
            return  JSONObject.fromObject(textMap).toString();
        }
        return  "{\"result\":false}";
    }


    /**
     * 解绑序列号
     * @param unbindDays
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/unbind", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String unbindDevice(@RequestParam("unbindHours") String unbindHours, HttpServletResponse response, HttpServletRequest request) {
        AuthResult authResult = authResultService.findAuthCodeInfo();
        if(authResult!=null){
            try{
                JSONObject jsonObject2 = authResultService.decryptAuthResultByPrivateKey(authResult);
                String authCode = (String)jsonObject2.get("authCode") ;//找出激活码
                String data = null;
                try {
                    byte[] encryptDataBytes  = DogRsaUtil.encryptByPublicKey(authCode.getBytes("UTF-8"), DogRsaUtil.PUBLIC_KEY);
                    data = Base64.encodeBase64String(encryptDataBytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Map<String,String> paramMap = new HashMap<String, String>();
                paramMap.put("serialNumber",data);

                String UNBIND_SN_URL ="http://"+ ConfigUtil.getString("authorize.ip")+":"+ConfigUtil.getString("authorize.port")+"/soft-dog/rest/unBindSerialNumber";

                String  returnMsg = HttpClientUtil.getPostResponse(UNBIND_SN_URL, paramMap, null);
                if(returnMsg==null){
                	 Map<String, String> textMap=new HashMap<String, String>();
                     textMap.put("false", "解绑接口响应超时!");
                    return  JSONObject.fromObject(textMap).toString();
                }
                JSONObject jsonObject = JSONObject.fromObject(returnMsg);
                Iterator it = jsonObject.keys();
                String key = "";
                String value = "";
                while (it.hasNext()) {
                    key = (String) it.next();
                    value = jsonObject.getString(key);
                }

                if(StringUtil.isNotEmpty(key) && key.equals("200")){
                    //解绑成功-设置失效时间
                    Date expriyDate = DateUtil.getDateMOPHour(new Date(),Integer.valueOf(unbindHours));
                    authResult.setExpiryDate(expriyDate);
                    authResultService.update(authResult);

                    //更新失效时间
                    Long   dateLong = (Long)((JSONObject) jsonObject2.get("authDate")).get("time");
                    Date endDate = new Date(dateLong);
                    if(authResult.getExpiryDate()!=null){
                        endDate = endDate.after(authResult.getExpiryDate())?authResult.getExpiryDate():endDate;
                    }
                    ConfigUtil.put(SystemConstants.SYS_ACTIVATION_STATE, "false");
                    ConfigUtil.put(SystemConstants.SYS_EFFECTIVE_TIME, DateUtil.dateToStr(endDate, DateUtil.DF_yyyyMMddHHmmss));

//                    return  "1";
                    return  "{\"result\":true}";

                }else {
                    if(StringUtil.isEmptyString(key)){
                    	 Map<String, String> textMap=new HashMap<String, String>();
                         textMap.put("false", "解绑请求校验失败!");
                        return  JSONObject.fromObject(textMap).toString();
                    }else {
                        byte[] decodeBytes = Base64.decodeBase64(value);
                        try {
                            byte[] returnByte = DogRsaUtil.decryptByPrivateKey(decodeBytes, DogRsaUtil.PRIVATE_KEY);
                            String desc = new String(returnByte,"UTF-8");
                            Map<String, String> textMap=new HashMap<String, String>();
                            textMap.put("false", desc);
                            return  JSONObject.fromObject(textMap).toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }
            }catch (Exception e){
                e.printStackTrace();
                Map<String, String> textMap=new HashMap<String, String>();
                textMap.put("false", "调用解绑接口失败!");
                return  JSONObject.fromObject(textMap).toString();
            }


        }
        return  "{\"result\":false}";
    }

    public static Map<String,String> getNetworkMac(){
    	 try {
    		 if(macMap.size()<=0){
    			 Sigar sigar = new Sigar();
    			 String[] ifaces = sigar.getNetInterfaceList();
    			 for (int i = 0; i < ifaces.length; i++) {
    				 NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
    				 if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
    						 || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
    						 || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())||(cfg.getFlags() !=2115)) {
    					 continue;
    				 }
    				 macMap.put(SystemConstants.MAC_KEY, cfg.getHwaddr());
    				 return macMap;
    			 }
    		 }
 		} catch (SigarException e) {
 			e.printStackTrace();
 		}
    	 return macMap;
    }

}
