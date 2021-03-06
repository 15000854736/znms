package info.zznet.znms.base.util;

import info.zznet.znms.base.constants.SystemConstants;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.StringUtils;


/**
 * <p>Title: 常见的字符串操作和函数封装类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: 2011</p>
 *
 * <p>Company: </p>
 *
 * @author phc
 *
 * @date 2011-3-4
 *
 * @version 1.0
 **/
public class StringUtil {
	
	/**
	 * 16进制字符串包含的字符
	 */
	private final static char[] HEX_DIGIT = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	/** 
     * 判断字符串是否为空 
     * @param str 
     * null、“ ”、“null”都返回true 
     * @return 
     */  
    public static boolean isNullString(String str) {  
        return (null == str ||  str.length() <= 0 || str.trim()=="" ||str.trim().equals("") || "null".equals(str.trim().toLowerCase())) ? true : false;  
    } 
	/**
	 * 去除字符串两端的空格，如果为null，则返回空字符串
	 * @param str 要去除空格的字符串
	 * @return 去掉两端空格的字符串
	 */
	public static String trim(String str){
		return (str == null ? "":str.trim()); 
	}
	/**
     * 取得系统当前日期的字符串形式
     * @param format 日期字符串的格式
     * @return 指定格式的日期字符串
     */
    public static String getCurrentDate(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String strNowDate = dateFormat.format(Calendar.getInstance().getTime());
		return strNowDate;
	}
	 /**
	 * 解析日期格式yyyy-MM-dd为yyyyMMdd，如转化日期2008-08-08为20080808
	 * @param strDate 格式为yyyy-MM-dd的日期字符串
	 * @return 格式为yyyyMMdd的日期字符串
	 */
	public static String parseDate(String strDate) {
		strDate = trim(strDate);
		
		if(strDate.length() == 10) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(strDate.substring(0, 4));
			buffer.append(strDate.substring(5, 7));
			buffer.append(strDate.substring(8, 10));
			strDate = buffer.toString();
		}
		
		return strDate;
    }
	/**
     * 解析时间格式HH:mm:ss为HHmmss，如转化时间08:08:08为080808
     * @param strTime 格式为HH:mm:ss的时间字符串
     * @return 格式为HHmmss的时间字符串
     */
    public static String parseTime(String strTime) {
        strTime = trim(strTime);
        if(strTime.length() == 8) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(strTime.substring(0, 2));
			buffer.append(strTime.substring(3, 5));
			buffer.append(strTime.substring(6, 8));
			strTime = buffer.toString();
		}
		return strTime;
    }
	
    /**
	 * 转化日期格式yyyyMMdd为yyyy-MM-dd，如转化日期20080808为2008-08-08
	 * @param strDate 格式为yyyyMMdd的日期字符串
	 * @return 格式为yyyy-MM-dd的日期字符串
	 */
	public static String parseDateEx(String strDate) {
		strDate = trim(strDate);
		
		if(strDate.length() == 8) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(strDate.substring(0, 4));
			buffer.append("-");
			buffer.append(strDate.substring(4, 6));
			buffer.append("-");
			buffer.append(strDate.substring(6, 8));
			
			strDate = buffer.toString();
		}
		
		return strDate;
	}
	   /**
	 * 转化日期格式yyyyMM为yyyy-MM，如转化日期200808为2008-08
	 * @param strDate 格式为yyyyMM的日期字符串
	 * @return 格式为yyyy-MM的日期字符串
	 */
	public static String parseyyyyMM(String strDate) {
		strDate = trim(strDate);
		
		if(strDate.length() == 8) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(strDate.substring(0, 4));
			buffer.append("-");
			buffer.append(strDate.substring(4, 6));
			
			strDate = buffer.toString();
		}
		
		return strDate;
	}
	
	/**
     * 转化时间格式HHmmss为HH:mm:ss，如转化时间080808为08:08:08
     * @param strTime 格式为HHmmss的时间字符串
     * @return 格式为HH:mm:ss的时间字符串
     */
    public static String parseTimeEx(String strTime) {
        strTime = trim(strTime);
		if(strTime.length() == 6) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(strTime.substring(0, 2));
			buffer.append(":");
			buffer.append(strTime.substring(2, 4));
			buffer.append(":");
			buffer.append(strTime.substring(4, 6));
			
			strTime = buffer.toString();
		}
		
		return strTime;
    }
    /**
	 * 转化日期格式yyyyMMddHHmmss为yyyy-MM-dd HH:mm:ss，如转化日期20080808080808为2008-08-08 08:08:08
	 * @param strDate 格式为yyyyMMddHHmmss的日期字符串
	 * @return 格式为yyyy-MM-dd  HH:mm:ss的日期字符串
	 */
	public static String parseFullDateEx(String strDate) {
		strDate = trim(strDate);
		
		if(strDate.length() == 14) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(parseDateEx(strDate.substring(0, 8)));
			buffer.append(" ");
			buffer.append(parseTimeEx(strDate.substring(8, 14)));
			
			strDate = buffer.toString();
		}
		
		return strDate;
	}
	/**
	 * 转化日期格式yyyy-MM-dd HH:mm:ss为yyyyMMddHHmmss，如转化日期2008-08-08 08:08:08为20080808080808
	 * @param strDate
	 * @return
	 */
	public static String parseFullDate(String strDate){
		strDate=trim(strDate);
		if(strDate.length()==19){
			StringBuffer buffer=new StringBuffer("");
			buffer.append(parseDate(strDate.substring(0, 10)));
			buffer.append(parseTime(strDate.substring(11)));
			
			strDate=buffer.toString();
		}
		return strDate;
	}
    /**
     * 转换Double数据类型精度(四舍五入)
     * @param value 要转换的数据
     * @param precision 小数点后的位数
     * @return double 转换的数据
     */
    public static double convertPrecision(double value, int precision) {
    	String s = "0."+getFillZero("", precision);
    	DecimalFormat df = new DecimalFormat(s);
    	return Double.parseDouble(df.format(value));
    }
    /**
	 * 将字符串中的小写字母转换为大写字母
	 * 
	 * @param s
	 *            输入字符串
	 * @return 返回转换为大写字母后的字符串
	 */
	public static byte[] byteUpper(byte[] s) {
		return (new String(s)).toUpperCase().getBytes();
	}
	
	/**
	 * 将字符串中的大写字母转换为小写字母
	 * 
	 * @param s
	 *            输入字符串
	 * @return 返回转换为小写字母后的字符串
	 */
	public static byte[] byteLower(byte[] s) {
		return (new String(s)).toLowerCase().getBytes();
	}
	/**
	 * 计算字符串包括尾部的空格和制表符后的实际长度
	 * 
	 * @param s
	 *            输入字符串
	 * @return 直接返回实际的长度
	 */
	public static long realLen(byte[] s) {
		if (s == null)
			return 0;
		int i = s.length - 1;
		while (0 <= i && (32 == s[i] || 9 == s[i]))
			i--;
		return i + 1;
	}

	/**
	 * 计算字符串包括尾部的空格和制表符后的实际长度
	 * @param s输入字符串
	 * @return 直接返回实际的长度
	 */
	public static long realLen(String s) {
		return s == null ? 0 : realLen(s.getBytes());
	}
	
	/**
	 * 计算字符串的实际长度，不包括尾部的文件换行符
	 * 
	 * @param s
	 *            输入字符串
	 * @return 直接返回实际的长度
	 */
	public static long fileLineLen(byte[] s) {
		if (s == null)
			return 0;
		int i = s.length - 1;
		while (0 <= i && (10 == s[i] || 13 == s[i]))
			i--;
		return i + 1;
	}
	/**
	 * 计算字符串的实际长度，不包括尾部的文件换行符
	 * 
	 * @param s
	 *            输入字符串
	 * @return 直接返回实际的长度
	 */
	public static long fileLineLen(String s) {
		return s == null ? 0 : fileLineLen(s.getBytes());
	}
	/**
	 * 去掉输入字符串尾部的换行符以及空格和制表符
	 * 
	 * @param s
	 *            输入字符串
	 * @return 直接返回去掉尾部空格、制表符、换行符后的结果串
	 */
	public static String trimCRLF(String s) {
		if (s == null)
			return null;
		char[] ch = s.toCharArray();
		int i = ch.length - 1;
		while (0 <= i
				&& (10 == ch[i] || 13 == ch[i] || 32 == ch[i] || 9 == ch[i]))
			i--;
		return s.substring(0, i + 1);
	}
	
	/**
	 * 去掉串尾部的指定字符
	 * 
	 * @param s
	 *            输入字符串
	 * @param c
	 *            指定删除的字符
	 * @return 返回去掉串尾部的指定字符
	 */
	public static String trimRightChar(String s, char c) {
		if (s == null)
			return null;
		char[] chr = s.toCharArray();
		int i = chr.length - 1;
		while (0 <= i && c == chr[i])
			i--;
		return new String(chr, 0, i + 1);
	}

	/**
	 * 去掉输入字符串尾部的空格和制表符的字符串
	 * 
	 * @param s
	 *            输入字符串
	 * @return 返回去掉串尾部的空格和制表符的字符串
	 */
	public static String trimRight(String s) {
		if (s == null)
			return null;
		char[] ch = s.toCharArray();
		int i = ch.length - 1;
		while (0 <= i && (32 == ch[i] || 9 == ch[i]))
			i--;
		return s.substring(0, i + 1);
	}

	/**
	 * 去掉输入字符串头部的空格和制表符的字符串
	 * 
	 * @param s
	 *            输入字符串
	 * @return 返回去掉串头部的空格和制表符的字符串
	 */
	public static String trimLeft(String s) {
		if (s == null)
			return null;
		char[] ch = s.toCharArray();
		int i = 0;
		while (i <= ch.length - 1 && (32 == ch[i] || 9 == ch[i]))
			i++;
		return s.substring(i);
	}

	/**
	 * 根据长度左补0
	 * @param str 原字符串
	 * @param length 需要长度
	 * @return 
	 */
	public static String getFillZero(String str,int length){
		str=trim(str);
		while(str.length()<length){
			str="0"+str;
		}
		return str;
	}
	/**
	 * 根据长度右补0
	 * @param str原字符串
	 * @param length需要长度
	 * @return
	 */
	public static String getFillZeroRight(String str,int length){
		str=trim(str);
		while(str.length()<length){
			str=str+"0";
		}
		return str;
	}
	/**
	 * 根据长度右补空格
	 * @param str原字符串
	 * @param length需要长度
	 * @return
	 */
	public static String getFillBlank(String str,int length){
		str=trim(str);
		while(str.length()<length){
			str=str+" ";
		}
		return str;
	}
	/**
	 * 将数字输入返回指定长度的16进制字符串，长度小于转换后字符串的返回-1
	 * @param i 输入的数字
	 * @param size 需要的长度
	 * @return
	 */
	public static String toHexString(int i,int size){
		String str = Integer.toHexString(i);
		return size<str.length()?"-1":getFillZero(str, size);
	}
	/**
	 * 将数字输入返回指定长度的16进制字符串，长度小于转换后字符串的返回-1
	 * @param i 输入的数字
	 * @param size 需要的长度
	 * @return
	 */
	public static String toHexString(long i,int size){
		String str = Long.toHexString(i);
		return size<str.length()?"-1":getFillZero(str, size);
	}
	
	/**
	* 生成随机密码
	* @param passLenth 生成的密码长度
	* @return 随机密码
	*/
	public static String getPass(int passLenth) {

	   StringBuffer buffer = new StringBuffer(
	     "0123456789abcdefghijklmnopqrstuvwxyz");
	   StringBuffer sb = new StringBuffer();
	   Random r = new Random();
	   int range = buffer.length();
	   for (int i = 0; i < passLenth; i++) {
	    //生成指定范围类的随机数0—字符串长度(包括0、不包括字符串长度)
	    sb.append(buffer.charAt(r.nextInt(range)));
	   }
	   return sb.toString();
	}
	/**
	 * 取随机数
	 * @param i
	 * @return
	 */
	public static String sj(int i){
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int j = 0; j < i; j++) {
			sb.append(r.nextInt(10));
		}
		return sb.toString();
	}
	/**
	 * @功能: BCD码转为10进制串(阿拉伯数据)
	 * @参数: BCD码
	 * @结果: 10进制串
	 */
	public static String bcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
				.toString().substring(1) : temp.toString();
	}
	/**
	 * @功能: 10进制串转为BCD码
	 * @参数: 10进制串
	 * @结果: BCD码
	 */
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;
		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}
		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;
		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}
			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}
			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}
	/**
	 * Hex编码
	 * @param input
	 * @return
	 */
	/*public static byte[] hexEncode(byte[] input){
		Hex hex = new Hex();
		byte[] result = hex.encode(input);
		return result;
	}*/
	
	/**
	 * Hex解码
	 * @param input
	 * @return
	 */
	/*public static byte[] hexDecode(byte[] input){
		Hex hex = new Hex();
		byte[] result = null;
		try {
			result = hex.decode(input);
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}*/
	
	/**
	 * DES算法  ECB模式不补位
	 * @param key
	 * @param buffer
	 * @param type  1加密 2解密
	 * @return
	 */
	public static byte[] des(byte[] key,byte[] buffer,int type){
		try {
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			if(type == 1 ){
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			}else{
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
			}
			return cipher.doFinal(buffer);//des后的值
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 采用x9.9算法计算MAC (Count MAC by ANSI-x9.9).
	 *
	 * @param	tKey	密钥数据
	 * @param	tBuffer	待计算的缓冲区
	 * @return	MAC值(null -- 无效)
	 */
	public static byte[] tCountMACx9_9(byte[] tKey, byte[] tBuffer){
		int bufferSize = tBuffer.length%8==0?tBuffer.length:tBuffer.length+(8-tBuffer.length%8);
		byte[] ucMacTmp = new byte[8];
		byte[] ucTmp = new byte[8];
		for(int i=0; i<bufferSize/8; i++){
			for(int j=0; j<8; j++){
				ucTmp[j] = (byte) (ucMacTmp[j]^tBuffer[i*8+j]);
			}
			ucMacTmp = des(tKey, ucTmp,1);
		}
		return ucMacTmp;
	}
	
	/**
	 * 将金额补齐相应的长度，保留2位小数并放在最后
	 * @param money
	 * @param size
	 * @return
	 */
	public static String money2Bcd(String money,int size){
		DecimalFormat df = new DecimalFormat("#.00");
		String dfMoney = df.format(Double.parseDouble(money));
		String[] moneys = dfMoney.split("[.]");
		StringBuffer rssb = new StringBuffer(getFillZero(moneys[0], (size*2)-2));
		rssb.append(moneys[1]);
		return rssb.toString();
	}
	
	/**
	 * 将BCD表示的金额表示为2位小数形式
	 * @param bcd
	 * @return
	 */
	public static String bcd2Money(String bcd){
		StringBuffer rsSb = new StringBuffer();
		String begin = bcd.substring(0, bcd.length()-2);
		String end = bcd.substring(bcd.length()-2);
		rsSb.append(Integer.valueOf(begin));
		rsSb.append(".");
		rsSb.append(end);
		return rsSb.toString();
	}
	/**
	 * 将数字补齐相应长度，保留3位小数
	 * @param num
	 * @param size
	 * @return
	 */
	public static String num2Bcd(String num,int size){
		DecimalFormat df = new DecimalFormat("#.000");
		String dfNum = df.format(Double.parseDouble(num));
		String[] nums = dfNum.split("[.]");
		StringBuffer rssb = new StringBuffer(getFillZero(nums[0], (size*2)-3));
		rssb.append(nums[1]);
		return rssb.toString();
	}
	
	/**
	 * 将传入的string转换成列名的形式
	 * 例如：myName->MY_NAME
	 * @param str 欲转换的字符串
	 * @return
	 */
	public static String transToUnderLineStyle(String str){
        List record =new ArrayList();
        for(int i=0;i<str.length();i++)
        {
            char tmp =str.charAt(i);
             
            if((tmp<='Z')&&(tmp>='A'))
            {
                record.add(i);//记录每个大写字母的位置
            }
             
        }
        str= str.toUpperCase();
        char[] charofstr = str.toCharArray();
        String[] t =new String[record.size()];
        for(int i=0;i<record.size();i++)
        {
            t[i]="_"+charofstr[(int)record.get(i)];//加“_”
        }
        String result ="";
        int flag=0;
        for(int i=0;i<str.length();i++)
        {
            if((flag<record.size())&&(i==(int)record.get(flag))){
                result+=t[flag];
                flag++;
            }
            else
                result+=charofstr[i];
        }
        return result;
    }
	
	public static void main(String[] args) {
		String str = "0021";
		ByteBuffer bb = ByteBuffer.allocate(100);
		bb.put(str.getBytes());
		System.out.println(bb.position());
	}
	
	/**
	 * 将对象转换成字符串
	 * @param o 需要转换的对象
	 * @return
	 */
	public static String getString(Object object) {
		if (object == null) {
			return null;
		}
		
		if (object instanceof Integer) {
			return Integer.toString((Integer) object);
		} 
		else if (object instanceof Boolean) {
			return (Boolean)object ? "1":"0";
		} 
		else {
			return String.valueOf(object);
		}
	}
	
	/**
	 * 将字符串转成Integer类型
	 * @param str 要转换的字符串
	 * @return
	 */
	public static Integer strToInteger(String str) {
		if(isNullString(str)) {
			throw new NullPointerException();
		}
		return Integer.parseInt(str);
	}
	
	/**
	 * 将字符串转换成Boolean类型
	 * @param str
	 * @return
	 */
	public static Boolean getBoolean(String str) {
		if(isNullString(str)) {
			return false;
		}
		return (strToInteger(str) != 0);
	}
	
	/**
	 * 返回每个字符的ascii码的16进制值
	 * @param str
	 * @return
	 */
	public static String strToHexStr(String str) {
		try {
			String re = "";
			int c;
			for (int i = 0; i < str.length(); i++) {
				c = str.charAt(i);
				re += Integer.toHexString(c);
			}
			return re;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 将字符串转换成double
	 * @param str 要转换成字符串
	 * @return
	 */
	public static Double strToDouble(String str) {
		if (isNullString(str)) {
			throw new NullPointerException();
		}
		return Double.parseDouble(str);
	}
	
	public static boolean isArrayContainString(String a,String[] b){
	    if(a==null||b==null)return false;    
	    
	    for(String temp : b){
	    	if(temp.trim().equals(a.trim())){
	    		return true;
	    	}
	    }
	    return false;
	}
	
	/**
	 * 判断是否是手机号
	 * @return
	 */
	public static boolean isPhoneNumber(String str){
		Pattern p = Pattern.compile("1\\d{10}");  
		Matcher m = p.matcher(str);  
		return m.find();
	}
	
	/**
	 * 将byte数组转化为16进制的数值表示的形式（小写）
	 * @param ba 需要转化的byte数组
	 * @param prefix  可选的形式是0X,0x,""
	 * @return String 16进制表示的字符串
	 */
	public static String bytesToHexLowerCase(byte[] ba, String prefix, boolean withSpace) {
		if ((ba == null) || (prefix == null)) {
			throw new NullPointerException();
		}

		StringBuffer sb = new StringBuffer();
		sb.append(prefix);
		for (int i = 0; i < ba.length; i++) {
			int vi = ba[i];
			if (vi < 0) {
				vi += 256;
			}
			if (vi < 0x10) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(vi));
			if (withSpace)
				sb.append(' ');
		}
		return sb.toString();
	}
	
	/**
	 * 将byte数组转换成16 进制字符串
	 * @param bytes 需要转化的byte数组
	 * @return String 16进制表示的字符串
	 */
	public static String bytesToHexLowerCase(byte[] bytes) {
		return bytesToHexLowerCase(bytes, "", false);
	}
	
	/**
	 * 通过16进制的字符串表示转换成byte数组
	 * @param hexStr 16进制的字符串
	 * @param prefix 字符串前缀，可以为0X,0x,""
	 * @return byte[] byte数组
	 */
	public static byte[] hexStrToBytes(String hexStr, String prefix) {
		if ((hexStr == null) || (prefix == null)) {
			throw new NullPointerException();
		}
		
		String myHexStr = hexStr.trim();
		if (myHexStr.startsWith(prefix)) {
			myHexStr = myHexStr.substring(prefix.length());
		}
		int myHexStrLen = myHexStr.length();
		byte[] ba = new byte[myHexStrLen / 2];
		for (int i = 0; i < myHexStrLen; i += 2) {
			int vi = Integer.parseInt(myHexStr.substring(i, i + 2), 16);
			if (vi > 128) {
				vi -= 256;
			}
			ba[i / 2] = (byte) vi;
		}
		return ba;
	}
	
	/**
	 * 将16进制字符串转换成字节
	 * @param hexStr 16进制字符串
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexStr) {
		return hexStrToBytes(hexStr, "");
	}
	
	/**
	 * 判断是否为邮箱
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str){
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * 检查用户名是否符合格式
	 * @return
	 */
	public static boolean checkUserName(String str){
		if(StringUtils.isNumeric(str)){
			return false;
		}
		if(str.trim().length() < 6 || str.trim().length() > 64){
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9a-zA-Z_]+$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * 检查密码是否符合格式
	 * @param str
	 * @return
	 */
	public static boolean checkPassword(String str){
		if(str.trim().length() < 6 || str.trim().length() > 64){
			return false;
		}
		return true;
	}
	
	/**
	 * 获取标准格式日期
	 * @param date
	 * @return
	 */
	public static String getStandardDate(Date date){
		return new SimpleDateFormat(SystemConstants.COMMON_DATE_FORMAT).format(date);
	}
	
	/**
	 * 获取今天0点时间（yyyy-MM-dd HH:mm:ss）
	 * @return
	 */
	public static String getTodayStartDate(){
		Calendar todayStart = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));  
        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formater.format(todayStart.getTime());
	}
	
	/**
	 * 获取今天23:59:59时间（yyyy-MM-dd HH:mm:ss）
	 * @return
	 */
	public static String getTodayEndDate(){
		Calendar todayEnd = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));  
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);  
        todayEnd.set(Calendar.MINUTE, 59);  
        todayEnd.set(Calendar.SECOND, 59);  
        todayEnd.set(Calendar.MILLISECOND, 999);  
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formater.format(todayEnd.getTime());
	}
	
	
	public static String convertNetStreamIoSpeed(long bps){
		BigDecimal _bps = new BigDecimal(bps);
		if(_bps.divide(new BigDecimal(1024*1024*1024), 2, BigDecimal.ROUND_HALF_UP).compareTo(BigDecimal.ONE) >= 0) {
			return _bps.divide(new BigDecimal(1024*1024*1024), 2, BigDecimal.ROUND_HALF_UP)+ "G";
		} else if(_bps.divide(new BigDecimal(1024*1024), 2, BigDecimal.ROUND_HALF_UP).compareTo(BigDecimal.ONE) >= 0) {
			return _bps.divide(new BigDecimal(1024*1024), 2, BigDecimal.ROUND_HALF_UP) + "M";
		} else if(_bps.divide(new BigDecimal(1024), 2, BigDecimal.ROUND_HALF_UP).compareTo(BigDecimal.ONE) >= 0){
			return _bps.divide(new BigDecimal(1024), 2, BigDecimal.ROUND_HALF_UP) + "K";
		} else {
			return _bps.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}
	}
	
	public static long convertNetStreamIoSpeed(String speed) {
		long ratio = 1l;
		if(StringUtils.containsIgnoreCase(speed, "g")) {
			ratio = 1024l * 1024l * 1024l;
		} else if(StringUtils.containsIgnoreCase(speed, "m")) {
			ratio = 1024l * 1024l;
		} else if(StringUtils.containsIgnoreCase(speed, "k")) {
			ratio = 1024l;
		}
		BigDecimal _speed = new BigDecimal(speed.replaceAll("[^\\d|^\\.]", ""));
		return _speed.multiply(new BigDecimal(ratio)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
	}
	
	public static boolean isNotEmpty(String str) {
		return ! isEmptyString(str);
	}
	public static boolean isEmptyString(String str) {
		return str == null || str.length() == 0;
	}
}
