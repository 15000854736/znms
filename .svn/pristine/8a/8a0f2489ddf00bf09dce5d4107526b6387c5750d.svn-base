package info.zznet.znms.web.util;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.dao.ConcurrencyFailureException;

/**
 * Created by sql on 2015/6/28.
 */
public class SystemUtil {

    /**
     * 设置系统是否退出标志
     */
    private static boolean isExit = false;

    /**
     * 是否停止系统中的定时器
     */
    private static Boolean isStopTimer;


	/**
	 * 程序的异常处理
	 */
	public static void defaultExceptionHandle(Throwable e, Logger log) {
		checkDeadLock(e, log);
		log.error("", e);
	}


	/**
	 * 检测是否死锁异常
	 */
	private static void checkDeadLock(Throwable e, Logger log) {
		Throwable cause = e;
		while (cause != null) {
			//  在英文操作系统下面死锁的判断是否准确
			if (cause instanceof ConcurrencyFailureException || (cause.getMessage() != null && cause.getMessage().contains("死锁"))) {
				dumpThreadStack(log);
				break;
			}
			cause = ExceptionUtils.getCause(cause);
		}
	}

	
	/**
	 * 打印堆栈
	 */
	public static void dumpThreadStack(Logger log) {
		String lineSeparator = System.getProperty( "line.separator" );
		// ThreadMXBean管理JVM虚拟机的线程系统
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		// 获取JAVA运行时的对象
		Runtime rt = Runtime.getRuntime();
		StringBuilder sb = new StringBuilder("dump: totalmem=");
		sb.append(rt.totalMemory());
		sb.append(", freemem=");
		sb.append(rt.freeMemory());
		sb.append(", threadcount=");
		// 获取系统最大的线程数
		sb.append(threadMXBean.getPeakThreadCount());
		sb.append(lineSeparator);

		int depth = Integer.MAX_VALUE;
		// 获取系统线程信息数组
		ThreadInfo threadInfos[] = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), depth);
		for (int j=0; j<threadInfos.length; j++) {
			ThreadInfo ti = threadInfos[j];
			sb.append("Thread: ").append(ti.getThreadName()).append(", State: ").append(ti.getThreadState()).append(lineSeparator);
			StackTraceElement[] st = ti.getStackTrace();
			for (int i=0; i<st.length; i++) {
				StackTraceElement ste = st[i];
				sb.append("\tat ");
				sb.append(ste.getClassName()).append('.').append(ste.getMethodName());
				sb.append('(').append(ste.getFileName()).append(':').append(ste.getLineNumber()).append(")\r\n");
			}
			sb.append(lineSeparator);
		}
		// 获取log的级别
		Level oldLevel = log.getLevel();
		log.setLevel(Level.DEBUG);
		log.debug(sb);
		log.setLevel(oldLevel);
	}
	
	/**
	 * 
	 * 查看当前是否停止所有的定时任务，
	 * 只需要在 服务的安装路径下新建一个stop-timer的文件即可 
	 * 这个主要是用户开发期间分析生成SQL语句
	 */
	public static boolean isStopTimer() {
		if (isStopTimer == null) {
			File file = new File(PathUtil.getAppRootPath() + File.separator
					+ "stop-timer");
			if (file.exists()) {
				isStopTimer = true;
			} else {
				isStopTimer = false;
			}
		}
		return isStopTimer;
	}

	/**
	 * 设置系统是否退出标志
	 */
	public static void setIsExit(boolean isExiting) {
		isExit = isExiting;
	}
	
	/**
	 * 获取系统是否退出标志
	 */
	public static boolean isExit() {
		return isExit;
	}
	
	
}
