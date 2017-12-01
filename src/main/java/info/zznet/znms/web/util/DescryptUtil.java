package info.zznet.znms.web.util;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.sun.crypto.provider.SunJCE;


/**
 * 字符加密解密
 * @author dell001
 *
 */
public class DescryptUtil {

	private static final String ENCRYPT_TYPE = "DES";


	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;
	
	/**
	 * 默认构造方法，使用默认密钥
	 * @throws Exception
	 */
	public DescryptUtil() throws Exception {
		this(ConfigUtil.getString("wx.auth.esc.key"));
	}
	
	/**
	 * 指定密钥构造方法
	 * @param strKey
	 * 			指定的密钥
	 * @throws Exception
	 */
	
	public DescryptUtil(String strKey) throws Exception {
		Security.addProvider(new SunJCE());
		Key key = this.getKey(strKey.getBytes());
		encryptCipher = Cipher.getInstance(ENCRYPT_TYPE);
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		decryptCipher = Cipher.getInstance(ENCRYPT_TYPE);
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}
	
	/**
	 * 加密字节数组
	 * @param arr 需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] arr) throws Exception {
		return encryptCipher.doFinal(arr);
	}
	
	/**
	 * 加密字符串
	 * @param strIn 需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public String encrypt(String strIn) throws Exception {
		return this.byteArrHexStr(this.encrypt(strIn.getBytes()));
	}
	
	/**
	 * 解密字节数组
	 * @param arr 需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] arr) throws Exception {
		return decryptCipher.doFinal(arr);
	}
	
	/**
	 * 解密字符串
	 * @param strIn 需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public String decrypt(String strIn) throws Exception {
		return new String(this.decrypt(this.hexStrByteArr(strIn)));
	}
	
	/**
	 * 从指定字符串生成密钥
	 * 密钥所需的字节数组长度为8位，不足8位时后面补0， 超过8位时只取前8位
	 * @param arrTemp 构成该字符串的字节数组
	 * @return
	 */
	private Key getKey(byte[] arrTemp) {
		// 创建一个空的8位字节数组
		byte[] arrKey = new byte[8];
		for (int i = 0; i <arrTemp.length && i < arrKey.length; i++) {
			arrKey[i] = arrTemp[i];
		}
		return new SecretKeySpec(arrKey, ENCRYPT_TYPE);
	}
	
	
	/**
	 * 将byte数组转换为表示16进制值的字符串 例如 : byte[]{8, 18} 转化为 0813
	 * @param arr 需要转换的byte数组
	 * @return 转换后的字符串
	 */
	public static String byteArrHexStr(byte[] arr) {
		int iLen = arr.length;
		/**
		 * 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		 */
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTemp = arr[i];
			while (intTemp < 0) {
				intTemp += 256;
			}
			/**
			 * 小于0F的数需要在前面补0
			 */
			if (intTemp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTemp, 16));
		}
		return sb.toString();
	}
	
	/**
	 * 将表示16进制值的字符串转换为byte数组
	 * @param strIn
	 * 			16进制值的字符串
	 * @return
	 * 			byte数组
	 */
	public static byte[] hexStrByteArr(String strIn) {
		byte[] arr = strIn.getBytes();
		int iLen = arr.length;
		/**
		 * 两个字符表示一个字节，所以字节数组的长度是字符串长度的一半
		 */
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTemp = new String(arr, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTemp, 16);
		}
		return arrOut;
	}	
	public static void main(String[] args) throws Exception {
		DescryptUtil desc = new DescryptUtil();
		System.out.println(desc.encrypt("zos"));
		System.out.println(desc.decrypt(desc.encrypt("zos")));
		
	}
}