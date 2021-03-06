package info.zznet.znms.base.util;

import info.zznet.znms.base.common.ZNMSLogger;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 */
public class EncryptionMD5 {
    private static final Logger log = Logger.getLogger(EncryptionMD5.class);

    /**
     * @throws
     * @Title: getMD5
     * @Description: 生成MD5密文
     * @return: String
     */
    public static String getMD5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            log.warn("NoSuchAlgorithmException caught!" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
        	log.warn("UnsupportedEncodingException caught!" + e.getMessage());
		}

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        int length = byteArray.length;

        for (int i = 0; i < length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1){
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            }else{
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString();
    }
    
    public static MessageDigest getMessageDigest() {
    	 MessageDigest messageDigest = null;
         try {
             messageDigest = MessageDigest.getInstance("MD5");
         } catch (NoSuchAlgorithmException e) {
             log.warn("NoSuchAlgorithmException caught!" + e.getMessage());
         }
         return messageDigest;
    }
    
    public static String getPlainMd5(String sourceStr){
    	 String result = "";
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(sourceStr.getBytes());
	            byte b[] = md.digest();
	            int i;
	            StringBuffer buf = new StringBuffer("");
	            for (int offset = 0; offset < b.length; offset++) {
	                i = b[offset];
	                if (i < 0)
	                    i += 256;
	                if (i < 16)
	                    buf.append("0");
	                buf.append(Integer.toHexString(i));
	            }
	            result = buf.toString();
	        } catch (NoSuchAlgorithmException e) {
	            ZNMSLogger.error(e);
	        }
	        return result;
    }
}
