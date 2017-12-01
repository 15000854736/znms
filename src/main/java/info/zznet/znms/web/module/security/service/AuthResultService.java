package info.zznet.znms.web.module.security.service;

import info.zznet.znms.base.entity.AuthResult;
import info.zznet.znms.web.module.common.service.BaseService;
import net.sf.json.JSONObject;

/**
 */
public interface AuthResultService extends BaseService {


    /**
     * 启动前判断
     */
    public void checkOnStart();

    /**
     * 解码数据
     */
    public JSONObject decryptAuthResultByPrivateKey(AuthResult authResult);

    /**
     * 解码数据
     */
    public String decryptFileContextByPrivateKey(String fileContext);

    /**
     * 找出激活码信息
     * @return
     */
    public AuthResult findAuthCodeInfo();
    
    public int update(AuthResult authResult);
    
    public int add(AuthResult authResult);
}
