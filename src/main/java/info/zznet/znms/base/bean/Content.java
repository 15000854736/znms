/**
 * 
 */
package info.zznet.znms.base.bean;

/**
 * 主机状态发送变化时通过接口发送信息的bean
 * @author dell001
 *
 */
public class Content {

	private String type;
	
	private HostMessage msg;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HostMessage getMsg() {
		return msg;
	}

	public void setMsg(HostMessage msg) {
		this.msg = msg;
	}

	/**
	 * @param type
	 * @param msg
	 */
	public Content(String type, HostMessage msg) {
		super();
		this.type = type;
		this.msg = msg;
	}
	
	
}
