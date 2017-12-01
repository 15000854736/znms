package info.zznet.znms.softdog;

import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class DogThreeDes {

	private static byte[] HEX_DECODE_CHAR = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6,
			7, 8, 9, 0, 0, 0, 0, 0, 0, 0, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, };

	private final static DogThreeDes single = new DogThreeDes();

	private DogThreeDes() {

	}

	public static DogThreeDes getInstance() {
		return single;
	}

	final static byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10,
			0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD, 0x55,
			0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2,
			0x1A, 0x1B, 0x1C, 0x1D, 0x0E, 0x0F, (byte) 0xAB, (byte) 0xAC,
			(byte) 0xAD, (byte) 0xAE, (byte) 0xAF, (byte) 0x0A, (byte) 0xAF,
			(byte) 0xDF, (byte) 0xEA, (byte) 0xEF };

	private Key getKey() {
		Key key = null;
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			// _generator.init(new SecureRandom(keyBytes));
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(keyBytes);
			_generator.init(56, secureRandom);

			key = _generator.generateKey();
			_generator = null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return key;
	}

	/**
	 * 获得一次3DES加密后的密文
	 * 
	 * @param
	 * @return strMi
	 */
	public String getEncString(String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		Key key = getKey();
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("utf-8");
			byteMi = getEncCode(byteMing, key);
			strMi = encoder.encode(byteMi);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			encoder = null;
			byteMi = null;
			byteMing = null;
		}
		return strMi;
	}

	/**
	 * 获得一次3DES解密后的明文
	 * 
	 * @param
	 * @return strMing
	 */
	public String getDecString(String strMi) {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		Key key = getKey();
		try {
			byteMi = base64Decoder.decodeBuffer(strMi);
			byteMing = getDecCode(byteMi, key);
			strMing = new String(byteMing, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			base64Decoder = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 * 获得一次3DES加密后的密文
	 * 
	 * @param byts
	 * @return
	 */
	private byte[] getEncCode(byte[] byts, Key key) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byts);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 获得一次3DES解密后的明文
	 * 
	 * @param bytd
	 * @return
	 */
	private byte[] getDecCode(byte[] bytd, Key key) {
		byte[] byteFina = null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(bytd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * Created on 2016年5月5日
	 * <p>
	 * Discription:[加密byte数组]
	 * </p>
	 * 
	 * @author:[]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return String .
	 */
	public String getEncByteArray(byte[] byteMing) {
		byte[] byteMi = null;
		String strMi = "";
		Key key = getKey();
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			byteMi = getEncCode(byteMing, key);
			strMi = encoder.encode(byteMi);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			encoder = null;
			byteMi = null;
			byteMing = null;
		}
		return strMi;
	}

	/**
	 * Created on 2016年5月5日
	 * <p>
	 * Discription:[解密byte数组]
	 * </p>
	 * 
	 * @author:[]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return byte[] .
	 */
	public byte[] getDecByteArray(String strMi) {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		byte[] strMing = null;
		Key key = getKey();
		try {
			byteMi = base64Decoder.decodeBuffer(strMi);
			byteMing = getDecCode(byteMi, key);
			strMing = byteMing;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			base64Decoder = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	public static void main(String[] args) {
		/*
		 * long startTime=new Date().getTime();
		 * 
		 * for (int i = 0; i < 100000; i++) { String encyStr1 =
		 * ThreeDes.getInstance().getEncString(
		 * "水电费v抽个烟突然让他换好几个脚后跟很舒服水电费水电费水电费等四个干豆腐干的发放的乳房大范甘迪是否");
		 * System.out.println("加密后的密文是:" + encyStr1); String str = new
		 * String(encyStr1.getBytes()); String decyStr1 =
		 * ThreeDes.getInstance().getDecString(str);
		 * System.out.println("解密后的明文是:" + decyStr1); } long endTime=new
		 * Date().getTime(); System.out.println((endTime-startTime)/1000);
		 */

		String encyStr = DogThreeDes.getInstance().getEncString(
				DogThreeDes.getInstance().getEncString("什邡市*^&*23"));
		System.out.println("二次加密后的密文是:" + encyStr);
		String decyStr = DogThreeDes.getInstance().getDecString(
				DogThreeDes.getInstance().getDecString(encyStr));
		System.out.println("二次解密后的明文是:" + decyStr);

		/*
		 * byte b[]={(byte)0xA1,(byte)0xB0,0x03,0x04,(byte)0x5F}; String
		 * encyStr1 = ThreeDes.getInstance().getEncByteArray(b);
		 * System.out.println(encyStr1);
		 * 
		 * byte[] b1= ThreeDes.getInstance().getDecByteArray(encyStr1);
		 * //SystemPrintln.println(b1, "***********");
		 * 
		 * System.out.println(PublicFuns.byteToString(b1[4]));
		 */
	}

}