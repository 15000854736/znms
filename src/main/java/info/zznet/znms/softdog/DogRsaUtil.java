package info.zznet.znms.softdog;

import info.zznet.znms.base.util.DateUtil;
import info.zznet.znms.web.util.SystemUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.Cipher;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.codec.binary.Base64;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * RSA公钥/私钥/签名工具包
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 */
public class DogRsaUtil {

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 公钥的key[对方公钥]
     */
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQxPw02RpL3FzAefnfrmBBrE9NFSDT/OxJSoIuE2MpzarpKKCcUvMFDwbN/BqnYjV+m7Ow6u/l7iauRsnXITUDzmlsMqkAJKLn9j6wqrx47izgIHLsJkNRPIqeJy/uej7CjiWQlAkAA5Yu46TBZPgTC1MaY1uhVSiTP+v/BlrMXwIDAQAB";

    /**
     * 私钥的key 【我的私钥】
     */
    public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL9+jKpni+e5v8lmuuXna/nugFOH+GG28jk8IrdP2NMKafoBtIHaPwc7JJJmkt+OBip8YBXxYCxRIdaz6mdbo6Q7Jr+GEWx+zRBgGvvBR7dkdoQN1GHvAb7u+ZivqJD76L8R6jJE267kVC0zH/WLQKtMdKUQPFmSpa9kkPO8JiUHAgMBAAECgYEAvcR5Pb2ThbYr+ziU6pSmCm5yOVU7rgoQjOutM6ArLSja0OizqyNO5qmTmomP737dWWkcB2LGfeSA9jYhiv/jb3rLrvXiTallkAdcD9bqSDZnLZZlzohrkbQXJieAsVAjAB+623VKyZOrogzpWZFc30aEz+wZmN8u16n58NC6VmECQQDxC0td+KtHlAWOQg861OVrm3IqNjzz04sfwvhbjplfdXKA6GfWyWCL3m43KEbY7QYGbiEIJpBOQ5OFyTUtBkjvAkEAy2A230mwcuFPMoOeuivnZ5DUzUE13d6MGwyNHSqC7LHsozC3ZAryhF2557ecjc0GYG1Gayyi9VApqb/rvJN1aQJAXjU52Zps5ubVXQo4hZfbqrjwX8se+3T8dQdwpegPzZ1LiVJL80h1cXUXwnr9ZZ8NscmtI1BfKheDOtCu9c/1BQJAMpHyEwOXxHkR4FWqGlDgcXgp8f/NDSZRYEh2rlHrzD9RwCJU96NhOliz6VJWs0vP0hpZ8DxhKO6N7Y+zZmikQQJBAKASS55hs0TdghnSV0E2J9X6k6qaU0r6euYNvtd/W/NXFRipc/o254ELU2oscEvCr/Mez49z+olnMN04SXJBs3E=";

    /**
     * 我的公钥
     */
    public static final String MY_PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/foyqZ4vnub/JZrrl52v57oBTh/hhtvI5PCK3T9jTCmn6AbSB2j8HOySSZpLfjgYqfGAV8WAsUSHWs+pnW6OkOya/hhFsfs0QYBr7wUe3ZHaEDdRh7wG+7vmYr6iQ++i/EeoyRNuu5FQtMx/1i0CrTHSlEDxZkqWvZJDzvCYlBwIDAQAB";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    /**
     * 生成密钥对(公钥和私钥)
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);//初始大小
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

//    public static void main(String[] args) {
//    	/*try {
//    		Map<String, Object> s =genKeyPair();
//    		String privateKey= getPrivateKey(s);
//    		String publicKey=getPublicKey(s);
//    		System.out.println(privateKey);
//    		System.out.println(publicKey);
//    	    long startTime=new Date().getTime();
//
//
//    		for (int i = 0; i < 1; i++)
//            {
//                String p = Base64.encodeBase64String(encryptByPublicKey(
//                        "水电费v抽个烟突然让他换好几个脚后跟很舒服水电费水电费水电费等四个干豆腐干的发放的乳房大范甘迪是否".getBytes(), PUBLIC_KEY));
//                System.out.println(p);
//                byte[] decodeBytes = Base64.decodeBase64(p);
//                String ss = new String(decryptByPrivateKey(decodeBytes,
//                        PRIVATE_KEY));
//                System.out.println(ss);
//            }
//
//            long endTime=new Date().getTime();
//	         System.out.println((endTime-startTime)/1000);
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}*/
//    	try {
//			System.out.println(getKey());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * 私钥解密
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 公钥解密
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 公钥加密
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 私钥加密
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    //    /**
//     * 
//     * 获取私钥
//     * 
//     * 
//     * @param keyMap 密钥对
//     * @return
//     * @throws Exception
//     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    //
//    /**
//     * 
//     * 获取公钥
//     * 
//     * 
//     * @param keyMap 密钥对
//     * @return
//     * @throws Exception
//     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }


    /**
     * 对数据加密
     * 返回经过Base64编码的加密字符串
     *
     * @param data      需要加密的数据
     * @param publicKey 公钥
     * @return
     */
    public synchronized static String RSAEncode(String data, String publicKey) throws Exception {
        //加密后的byte数组
        byte[] encryptDataBytes = encryptByPublicKey(data.getBytes("UTF-8"), publicKey);
        //经过base64编码
        return Base64.encodeBase64String(encryptDataBytes);

    }

    public static String getKey()
            throws Exception {
        String keyFile = "";
        if (System.getProperty("os.name").startsWith("Windows")) {
            keyFile = "F:/zos/zzos-console/src/main/resources/secretKey.dat";
            //keyFile = PathUtil.getPropertyPath()+File.separator+"secretKey.dat";
        } else {
            //keyFile = SystemUtil.authFilePath;
        }
        InputStream inSream = new FileInputStream(keyFile);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inSream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inSream.close();
        return new String(data, "utf-8");
    }


    /**
     * 数据解密
     * 数据是经过Base64编码的加密字符串
     * 返回解密后的字符串
     *
     * @param version
     * @param data
     * @return
     */
    public synchronized static String RSADecode(String privateKey, String data) throws Exception {
        //加密数据进过Base64解码
        byte[] decodeBytes = Base64.decodeBase64(data);
        //通过私钥对数据解密
        byte[] decryptData = decryptByPrivateKey(decodeBytes, privateKey);
        return new String(decryptData, "utf-8");
    }


    public static void main(String args[]) {
        String ss = "eO0eeq4oD6ko8Wk2S1ALYWfhSCgT928ze+C4mg/C5t8a1UsYNvzNT2YqUcJQFFISQJsEZn04bWwwK+eg0IkZAY2gxgZ2lb4OkhsLzXuWOy86JJK1/06+v7B53vzb7XRq4ld8c1WoU3MDf9JiC6z9YDTvTTJ2bfTcraBKue6Jhuw=";

        ss = "cGVuWpkYsMy9o6Q6LD1h1DjAgXNhdsLcnNJFNdFBJ1K+TQq5ANhMqADbCmcryIqAbb/vFZhj0YG9U5QcEtjt/RZdBZvmzeWU4VCbJ/7fORKWQBzqKipsoZTkb9dKsJQFIuulWB8rSFUlE7r0HdL6y4ZgSLlT05E0SlgY0Guartw=";

        byte[] decodeBytes = Base64.decodeBase64(ss);

        try {
            byte[] returnByte = decryptByPrivateKey(decodeBytes, DogRsaUtil.PRIVATE_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String returnMsg = "{\"authCode\":\"6937-0A6D-1F64-F1E2\",\"authDate\":{\"date\":30,\"day\":5,\"hours\":0,\"minutes\":0,\"month\":11,\"seconds\":0,\"time\":1483027200000,\"timezoneOffset\":-480,\"year\":116},\"authId\":\"e10e0d938490213a12cb5b1251e9e654\",\"isOperator\":1,\"onlineMax\":62531,\"operatorAuthCode\":\"FQW9-AVHN-71VY-P5EN,VC4E-T7VK-2US7-ZB91,MBM5-IYMD-8AK8-9Y94\",\"operatorNum\":3,\"randomStr\":\"SHHCB392\",\"status\":0}";
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonObject2 = JSONObject.fromObject(returnMsg, jsonConfig);
        Iterator it2 = jsonObject2.keys();
        String key = "";
        String value = "";

        HashMap hardWareMap = new HashMap();

        Long date = (Long) ((JSONObject) jsonObject2.get("authDate")).get("time");
        Integer isOperator = (Integer) jsonObject2.get("isOperator");
        Integer onlineMax = (Integer) jsonObject2.get("onlineMax");
        new Date(date);
        while (it2.hasNext()) {
            key = (String) it2.next();
            value = jsonObject2.getString(key);
            System.out.println("***********" + value);
            if (("authCode").equals(key)) {

            } else if (("authDate").equals(key)) {

            } else if (("isOperator").equals(key)) {
            } else if (("onlineMax").equals(key)) {
            } else if (("operatorAuthCode").equals(key)) {

            } else if (("operatorNum").equals(key)) {

            } else if ("randomStr".equals(key)) {

            }
        }

    }

}