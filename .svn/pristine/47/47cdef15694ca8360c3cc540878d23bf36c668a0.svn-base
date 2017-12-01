package info.zznet.znms.web.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * IO操作工具类
 * @author hsl
 * @version 1.0
 * @since 1.0
 * @date: 2015-07-23
 */
public class IOUtil {
	/**
	 * 关闭IO接口，忽略抛出的任何异常
	 * @param closeable
	 */
	public static void closeQuietly(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Throwable e) {
				// ignore
			}
		}
	}

	/**
	 * 读取资源文件
	 * @param cls
	 * @param resourceName
	 * @return
	 * @throws IOException
	 */
	public static byte[] readResource(Class<?> cls, String resourceName) throws IOException {
		InputStream in = cls.getResourceAsStream(resourceName);
		if (in == null) {
			return null;
		} else {
			try {
				ByteArrayOutputStream buf = new ByteArrayOutputStream();
				byte[] tmp = new byte[4096];
				int readSize = 0;
				while ((readSize = in.read(tmp)) != -1) {
					buf.write(tmp, 0, readSize);
				}
				return buf.toByteArray();
			}
			finally {
				closeQuietly(in);
			}
		}
	}
}
