package info.zznet.znms.base.common;

import info.zznet.znms.base.util.DateUtil;

import org.apache.logging.log4j.LogManager;

/**
 * @author Chenwd
 * @date 2015年11月26日
 */
public class ZNMSLogger {

	    private static void error(Class clazz, Throwable e) {
	    	LogManager.getLogger(clazz).error(e);
	    }

	    public static void error(Throwable e) {
	    	LogManager.getLogger(getTargetClazz()).error(e.getMessage(), e);
	    }

	    public static void error(String s) {
	    	LogManager.getLogger(getTargetClazz()).error(s);
	    }

	    public static void error(String s, Throwable e) {
	    	LogManager.getLogger(getTargetClazz()).error(s, e);
	    }
	    
	    public static void warn(String s){
	    	LogManager.getLogger(getTargetClazz()).warn(s);
	    }

	    private static void info(Class clazz, String s) {
	    	LogManager.getLogger(clazz).info(s);
	    }

	    public static void info(String s) {
	    	LogManager.getLogger(getTargetClazz()).info(s);
	    }

	    public static void debug(String s) {
	    	LogManager.getLogger(getTargetClazz()).debug(s);
	    }

	    private static String getTargetClazz() {
	        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	        int idx = 3;
	        String result = stackTrace[idx++].getClassName();
	        if (result.equals(ZNMSLogger.class.getName())) {
	            result = stackTrace[idx++].getClassName();
	        }

	        if (result.equals(ZNMSLogger.class.getName()))
	            result = stackTrace[idx++].getClassName();

	        return result;
	    }

	    /**
	     * 在控制台上输出日志
	     * @param content
	     */
	    public static void showOnConsole(String content) {
	    	info(content);
	        System.out.println(DateUtil.getLogTime() + "  " + content);
	    }
}
