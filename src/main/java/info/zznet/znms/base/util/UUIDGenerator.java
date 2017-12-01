package info.zznet.znms.base.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class UUIDGenerator {

    /** 
     * 获取32位GUID 
     * 
     * @return 
     */ 
    public static String getGUID() { 
        try { 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            UUID uuid = UUID.randomUUID(); 
            String guidStr = uuid.toString(); 
            md.update(guidStr.getBytes(), 0, guidStr.length()); 
            return new BigInteger(1, md.digest()).toString(16); 
        } catch (NoSuchAlgorithmException e) { 
            return null; 
        } 
    }

  public static void main(String[] args) {
	  System.out.println(getGUID());
}
}  
