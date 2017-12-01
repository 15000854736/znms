package info.zznet.znms.web.module.systemLog.collector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * Purpose:公用类
 * 
 * @author alexis
 * @See
 * @since 1.0
 */

public class CollectorUtil {
    static Logger logger=Logger.getLogger(CollectorUtil.class);
    
     private static byte[] HEX_DECODE_CHAR = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
         0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0, 0, 0, 0, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0,
         0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
         0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
         0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
         0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };

     
     static StringBuffer sbf=new StringBuffer();
     public static void test1(String str,int i,int j){
        if(str.length()>=j){
            sbf.append(str.substring(i, j));
            sbf.append(",");
            test1(str,i+2,j+2);
        }
     }
     
     public static void main(String[] args)
     {
         
         byte s[]={25 ,34 ,54 ,101 ,51 ,55 ,48 ,57 ,55 ,97 ,48 ,56 ,97 ,54 ,49 ,49 ,101 ,54 ,56 ,97 ,48 ,49 ,98 ,48 ,56 ,51 ,102 ,101 ,98 ,52 ,49 ,53 ,50 ,97 ,26 ,58 ,0 ,0 ,1 ,55 ,16 ,52 ,-117 ,56 ,-78 ,-12 ,32 ,-80 ,19 ,-84 ,4 ,-44 ,-79 ,-121 ,-11 ,-102 ,-7 ,4 ,114 ,-59 ,9 ,23 ,-86 ,-94 ,67 ,-92 ,-34 ,-111 ,-76 ,23 ,-8 ,-85 ,91 ,55 ,116 ,18 ,-57 ,-13 ,-108 ,-24 ,98 ,37 ,-11 ,-69 ,11 ,27 ,-68 ,-97 ,10 ,93 ,-120 ,-42 ,26 ,58 ,0 ,0 ,1 ,55 ,17 ,52 ,-126 ,26 ,-22 ,38 ,66 ,-5 ,86 ,25 ,-27 ,67 ,-122 ,28 ,-92 ,-101 ,89 ,-75 ,72 ,66 ,109 ,-12 ,8 ,9 ,-100 ,60 ,106 ,-96 ,123 ,-92 ,25 ,-15 ,81 ,-7 ,71 ,46 ,124 ,-100 ,8 ,-84 ,119 ,93 ,-95 ,86 ,-63 ,-58 ,67 ,22 ,57 ,-11 ,-43 ,-103 ,26 ,12 ,0 ,0 ,19 ,17 ,1 ,6 ,0 ,0 ,0 ,80 ,26 ,12 ,0 ,0 ,19 ,17 ,4 ,6 ,0 ,0 ,0 ,20 ,26 ,12 ,0 ,0 ,19 ,17 ,16 ,6 ,0 ,0 ,0 ,80 ,27 ,6 ,0 ,0 ,1 ,44 ,79 ,6 ,3 ,11 ,0 ,4 ,80 ,18 ,-96 ,98 ,41 ,-104 ,-17 ,16 ,-96 ,19 ,50 ,-53 ,31 ,-117 ,-88 ,42 ,-8 ,-105 ,88 ,14 ,49 ,57 ,50 ,46 ,49 ,54 ,56 ,46 ,49 ,46 ,49 ,48};
         System.out.println(byteArrayToHEXString(s));
         String str="08021258192237333363383633323039383031316536386130316230383366656234313532611A0C000013110106000000401A0C000013111006000000401B0600E4B5F94F06030200045012DB56EEF591AF1719009469482C7358FC";
         //SystemPrintln.println(HexStringTobytes(str), "");
        // String str="02a000dafa42d8336e2e6e70441ed8cf075641ec192234303238383138633531643837373263303135316438376334346637303031641a3a0000013710348b38a6c4bfae6dbdcc2cc3a06dc072a5aeaf3140119a820cd9aa7db87e643bc97170fbc7256d78bd279e9fe5db73f9063dec1a3a000001371134821a589a1bfdc751baeb2a2b607a1a260845db1c43e94665aad29958712e4d35db1bebc3731a18a5a4e14397902876a2ba261a0c0000131104060000012c1b06013c68004f06030b00045012de331261038c4b86e79d60dad91ab2ef550600000384";
        // System.out.println(HexStringTobytes(str));
         //test1(str,0,2);
         //System.out.println(sbf.toString());
        //byte[] b={2,58,0,-96,-68,-66,35,23,-126,80,86,-51,-46,-60,-103,12,109,-43,15,-101,26,58,0,0,1,55,16,52,-117,56,-102,-92,59,-64,104,-60,38,-32,96,-50,-9,-110,91,26,-55,93,-15,-117,72,70,-94,-5,-68,8,-107,-58,48,14,104,-104,-42,-4,-14,0,102,-66,36,81,122,126,-94,89,111,95,18,-58,55,-80,26,58,0,0,1,55,17,52,-126,26,10,-25,93,14,0,-86,-83,34,43,19,90,41,3,36,-1,59,66,-58,-56,-108,78,-46,73,41,109,43,-48,93,19,-1,12,67,72,90,-33,94,114,26,-81,72,-45,45,-113,10,-40,-105,-9,-2,79,6,3,11,0,4,80,18,6,59,94,-127,16,-75,-23,115,-22,56,91,-6,-86,39,81,48};
        //System.out.println(PublicFuns.byteArrayToHEXString(b));  
     }

    /**
     * 填充字符串
     * 
     * @param iniStr
     * @param fillStr
     * @param newStrLen
     * @param isLeft
     * @return
     * @date:2008-6-24
     */
    public static String fillString(String iniStr, String fillStr,
            int fillPostion, int newStrLen, boolean isLeft) {
        StringBuffer strBuf = new StringBuffer(iniStr);
        int iniStrlen = iniStr.getBytes().length;
        while (iniStrlen < newStrLen) {
            if (isLeft) {
                strBuf.insert(fillPostion, fillStr);
            } else {
                strBuf.append(fillStr);
            }
            iniStrlen++;
        }
        return strBuf.toString();
    }

    /**
     * 填充字符串
     * 
     * @param iniStr
     * @param fillStr
     * @param newStrLen
     * @param isLeft
     * @return
     * @date:2008-6-24
     */
    public static String fillString(String iniStr, String fillStr,
            int newStrLen, boolean isLeft) {
        return fillString(iniStr, fillStr, 0, newStrLen, isLeft);
    }

    /**
     * 左填充数据
     * 
     * @param iniStr
     * @param fillStr
     * @param newStrLen
     * @return
     * @date:2008-6-24
     */
    public static String leftFillStr(String iniStr, String fillStr,
            int newStrLen) {
        return fillString(iniStr, fillStr, newStrLen, true);
    }

    /**
     * 右填充数据
     * 
     * @param iniStr
     * @param fillStr
     * @param newStrLen
     * @return
     * @date:2008-6-24
     */
    public static String rightFillStr(String iniStr, String fillStr,
            int newStrLen) {
        return fillString(iniStr, fillStr, newStrLen, false);
    }

    /**
     * To formate the date and to return the new String
     * 
     * @param date
     * @param formateStr
     *            "yyyy/MM/dd" or "yyyyMMdd" and so on...
     * @return
     */
    public static String formatDate(Date date, String formateStr) {
        formateStr = formateStr.replaceAll("h", "H");
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat(formateStr);
        return simpleDateFormate.format(date);
    }

    /**
     * To translate the string of date to the object of date
     * 
     * @param date
     * @param separator
     *            "/","-" and so on...
     * @return
     */
    public static Date strToDate(String dateStr, String separator) {
        try {
            String[] dateArray = new String[3];
            if (separator != null) {
                dateArray = dateStr.split(separator);
            } else {
                dateArray[0] = dateStr.substring(0, 4);
                dateArray[1] = dateStr.substring(4, 6);
                dateArray[2] = dateStr.substring(6, 8);
            }
            Date date = new Date(dateArray[0] + "/" + dateArray[1] + "/"
                    + dateArray[2]);
            return date;
        } catch (Exception e) {
            logger.error("日期字符串转换错误:" + e.getMessage());
            return null;
        }
        // return new Date(new Integer(dateArray[0]),new
        // Integer(dateArray[1]),new Integer(dateArray[2])) ;
    }

    /**
     * 获取几天前的日期
     * 
     * @param days
     * @return
     * @date:Nov 20, 2008
     */
    public static Date getAgoDate(int days) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, -days);
        return now.getTime();
    }

    // 234.0的格式转换为234
    public static String strKillPoint(String str) {
        String str2;
        if (str.indexOf(".") == -1) {
            str2 = str;
        } else {
            int j = str.indexOf(".");
            str2 = str.substring(0, j);
        }
        return str2;
    }

    /**
     * 
     * hash：对byte数组进行哈希运算
     * 
     * @param data：待哈希的数据
     * @return：哈希结果
     * @throws NoSuchAlgorithmException
     * 
     */
    public static byte[] hash(byte[] data) throws NoSuchAlgorithmException {
        byte[] digest = null;

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(data);
        digest = md.digest();

        return digest;
    }

    public static byte[] hashFile(String file) throws Exception {
        try {
            File fp = new File(file);

            int fileLen = (int) fp.length();
            byte[] buffTemp = new byte[fileLen];
            FileInputStream in = new FileInputStream(file);
            int iFileLeftLen = fileLen;
            int offset = 0;
            byte[] buff = new byte[2048];

            while (iFileLeftLen > 0) {
                int iRead = in.read(buff);
                System.arraycopy(buff, 0, buffTemp, offset, iRead);
                offset += iRead;
                iFileLeftLen -= iRead;
            }

            in.close();
            byte[] fileHash = hash(buffTemp);

            return fileHash;
        } catch (Exception e) {
            throw new Exception("HASH文件失败", e);
        }
    }

    /**
     * 格式化交易金额为报文传输格式
     * 
     * @param dealMoney
     * @return
     * @date:Nov 18, 2008
     */
    public static String formateDealMoney(Double dealMoney) {
        String formateMoney = dealMoney.intValue() + "";
        formateMoney = formateMoney.substring(0, formateMoney.length() - 2)
                + "." + formateMoney.substring(formateMoney.length() - 2);
        return CollectorUtil.rightFillStr(formateMoney, " ", 15);
    }

    /**
     * 整型转为BYTE数据，不足长度时前补0
     * 
     * @param intdata
     * @param len
     * @return
     * @date:Nov 25, 2008
     */
    public static byte[] intToByte(int intdata, int len) {
        byte[] data = new byte[len];
        for (int i = 0; i < len; i++) {
            data[i] = (byte) ((intdata >> (len - 1 - i) * 8) & 0xFF);
        }
        return data;
    }

    /**
     * byteArrayToString: 将byte数组转换成16进制字符串
     * 
     * @param value：待转换的byte数组
     * @return String: 转换后的字符串
     * 
     * @see java.lang.Object#toString()
     */
    public static String byteArrayToHEXString(byte[] value) {
        StringBuffer buffer;
        int iLen;

        if (null == value) {
            return null;
        }

        iLen = value.length;

        buffer = new StringBuffer();
        for (int i = 0; i < iLen; i++) {
            buffer.append(byteToString(value[i]));
        }

        return buffer.toString();
    }

     public static final String bytesToHexString(byte[] bArray) {
            StringBuffer sb = new StringBuffer(bArray.length);
            String sTemp;
            for (int i = 0; i < bArray.length; i++) {
                sTemp = Integer.toHexString(0xFF & bArray[i]);
                if (sTemp.length() < 2)
                    sb.append(0);
                sb.append(sTemp.toUpperCase());
            }
            return sb.toString();
        }
     
     
        public static final byte[] HexStringTobytes(String hex)
        {
            byte[] bHex = hex.getBytes();
            byte[] b = new byte[bHex.length / 2];
            for (int i = 0; i < b.length; i++)
            {
                b[i] = (byte) (HEX_DECODE_CHAR[bHex[i * 2]] * 16 + HEX_DECODE_CHAR[bHex[i * 2 + 1]]);
            }
            return b;
        }
        
        
    /**
     * 
     * byteToStr：将byte转换成16进制表示的字符串
     * 
     * @param value：待转换的byte
     * @return String 转换后的字符串
     * 
     * @see <参见的内容>
     */
    public static String byteToString(byte value) {
        return ("" + "0123456789ABCDEF".charAt(0xF & value >> 4) + "0123456789ABCDEF"
                .charAt(value & 0xF));
    }

    /**
     * 转换十六进制BYTE数据组为INT
     * 
     * @param data
     * @param begin
     * @param len
     * @return
     * @throws Exception
     * @date:Nov 25, 2008
     */
    public static int parseInt(byte[] data, int begin, int len)
            throws Exception {
        try {
            byte[] buffer;
            buffer = new byte[len];
            System.arraycopy(data, begin, buffer, 0, len);
            return (new BigInteger(1, buffer)).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("byte数组转换成整型失败！", e);
        }
    }

    public static int byteToInt2(byte[] b) {

        int mask = 0xff;
        int temp = 0;
        int n = 0;
        for (int i = 0; i < b.length; i++) {
            n <<= 8;
            temp = b[i] & mask;
            n |= temp;
        }
        return n;
    }

    public static String trace(byte[] b) {
        StringBuffer c = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hexString = Integer.toHexString(b[i] & 0XFF).toUpperCase();
            if (hexString.length() == 1) {
                c.append("0");
                c.append(hexString);
            } else {
                c.append(hexString);
            }
            c.append(" ");
        }
        return c.toString();
    }
    
     /**
     * 运行外部进程
     * @param cmdStr
     * @return
     */
    public static int runOutProcess(String cmdStr,String... path) throws Exception{
        logger.info("执行命令："+cmdStr);
        String[] listCmd = cmdStr.split(" ");
        ProcessBuilder pb = new ProcessBuilder(listCmd);
        if (path != null && path.length>0){
            pb.directory(new File(path[0])) ;
        }
        pb.redirectErrorStream(true) ;
        final Process process = pb.start();  

        logger.info("============外部程序开始执行==================");
        
        new Thread(new Runnable() {
             public void run() {
                 BufferedReader br = new BufferedReader(
                         new InputStreamReader(process.getInputStream())); 
                 try
                {
                     String line = null ;
                    while((line = br.readLine()) != null)
                        logger.error("INPUT================="+line);
                } catch (IOException e){
                    logger.error("读取输出流错误："+e.getMessage());
                    e.printStackTrace();
                }
                 }
            }).start(); // 启动单独的线程来清空
        /*
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getErrorStream()));
        String line = null;
        
        while ((line = reader.readLine()) != null && !"".equals(line.trim())) {
            LogProcess.info(ServersServiceImpl.class,"ERROR-INPUT================="+line);
        }
        reader.close() ;*/
        logger.info("============外部程序执行结束==================");
        process.waitFor() ;
        return process.exitValue() ;
    }
    
    public static String toHexString(String s) {
        return bytesToHexString(s.getBytes()) ;
    }
    
    public static boolean isEmpty(String input) {
        boolean ret = true;
        if (input != null && !"null".equals(input)  && input.trim().length() > 0) {
            ret = false;
        }
        return ret;
    }
    /**
     * 计算长度
     * 
     * @param str
     * @return
     * @throws Exception
     */
    public static String conutLength(String str) throws Exception {
        int length = str.getBytes("gbk").length;
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < 8 - String.valueOf(length).length(); i++) {
            sbf.append("0");
        }
        sbf.append(length);
        return sbf.toString();
    }
    
    /**
     *  PRI部分由尖括号包含的一个数字构成，这个数字包含了程序模块（Facility）、严重性（Severity），这个数字是由Facility乘以 8，然后加上Severity得来。不知道他们为什么发明了这么一种不直观的表示方式。
		也就是说这个数字如果换成2进制的话，低位的3个bit表示Severity，剩下的高位的部分右移3位，就是表示Facility的值。
     * @param pri
     * @return
     */
    public static Facility parseFacility(int pri) {
    	return Facility.valueOf(pri >> 3);
    }
    
    public static Severity parseSeverity(int pri) {
    	String binaryPri = Integer.toBinaryString(pri);
    	return Severity.valueOf(Integer.parseInt(StringUtils.right(binaryPri, 3), 2));
    }
}