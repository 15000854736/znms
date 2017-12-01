package info.zznet.znms.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.telnet.TelnetClient;

public class TelnetUtil {
	private TelnetClient telnet;
	private InputStream in; // 输入流,接收返回信息
	private PrintStream out; // 向服务器写入 命令

	public TelnetUtil(String termtype) {
		telnet = new TelnetClient(termtype);
	}

	public TelnetUtil() {
		telnet = new TelnetClient();
	}

	/**
	 * 登录
	 * 
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @throws Exception 
	 */
	public TelnetResult login(String ip, int port, String username, String password) throws Exception {		
		telnet.connect(ip, port);
		in = telnet.getInputStream();
		out = new PrintStream(telnet.getOutputStream());
		String msg = "";
		msg += (readUntil(".*(Username:|Password:|>|#).*"));
		if(msg.endsWith("Username:")){
			write(username);			
		} else {
			write(password);
		}
		while ((msg += readUntil(".*(Password:|>|#).*")).endsWith("Password:")) {
			write(password);
		}
		
		if (StringUtils.isEmpty(msg)) {
			return new TelnetResult(false, msg);
		}
		return new TelnetResult(true, msg);
	}

	/**
	 * 启用
	 * 
	 * @param password
	 */
	public TelnetResult enable(String password) {
		write("en");
		String msg = "";
		while ((msg += readUntil(".*(Password:|>|#).*"))	.endsWith("Password:")) {
			write(password);
		}
		if (StringUtils.isEmpty(msg) || msg.endsWith(">")) {
			return new TelnetResult(false, msg);
		}
		return new TelnetResult(true, msg);
	}

	public TelnetResult startCopy(String myHostIp, String tmpFileName) {
		String msg = "";
		write("copy start tftp");
		msg += readUntil(".*(\\?|#)");
		if(msg.endsWith("#")){
			return new TelnetResult(false, msg);
		}
		// 后面两个不应发生
		write(myHostIp);
		msg += readUntil(".*(\\?|#)");
		write(tmpFileName);
		msg += readUntil(".*(#|failure)");
		if(msg.endsWith("failure")){
			return new TelnetResult(false, msg);			
		}
		return new TelnetResult(true, msg);	
	}

	/**
	 * 读取分析结果
	 * 
	 * @param pattern
	 *            匹配到该字符串时返回结果
	 * @return
	 */
	public String readUntil(String pattern) {
		StringBuffer sb = new StringBuffer();
		try {
			char ch;
			int code = -1;
			while ((code = in.read()) != -1) {
				ch = (char) code;
				sb.append(ch);

				String[] strs = sb.toString().split("\\n");
				String str = strs[strs.length - 1];
				// 匹配到结束标识时返回结果
				if (str.matches(pattern)) {
					return sb.toString();
				}

				// 登录失败时返回结果
				if (sb.toString().contains("Bad passwords")) {
					return sb.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 发送命令
	 * 
	 * @param value
	 */
	public void write(String value) {
		try {
			out.println(value);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭连接
	 */
	public void distinct() {
		try {
			if (telnet != null && !telnet.isConnected())
				telnet.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class TelnetResult{
		private boolean result;
		private String msg;
		
		public TelnetResult(){}
		public TelnetResult(boolean result, String msg){
			this.result = result;
			this.msg = msg;
		}
		public boolean getResult() {
			return result;
		}
		public void setResult(boolean result) {
			this.result = result;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}
