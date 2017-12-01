/**
 * 
 */
package info.zznet.znms.base.bean;

/**
 * @author dell001
 *
 */
public class HostMessage {

	//主机状态
	private String status;
	
	//学校代码
	private String schoolCode;
	
	//主机ip
	private String ip;
	
	//主机名
	private String name;
	
	//主机类型
	private String type;
	
	private String time;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @param status
	 * @param schoolCode
	 * @param ip
	 * @param name
	 * @param type
	 * @param time
	 */
	public HostMessage(String status, String schoolCode, String ip,
			String name, String type, String time) {
		super();
		this.status = status;
		this.schoolCode = schoolCode;
		this.ip = ip;
		this.name = name;
		this.type = type;
		this.time = time;
	}
	
	
}
