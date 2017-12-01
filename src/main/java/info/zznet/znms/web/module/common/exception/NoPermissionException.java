package info.zznet.znms.web.module.common.exception;

public class NoPermissionException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4793396319666916565L;

	public NoPermissionException(){
		super();
	}
	
	public NoPermissionException(String msg){
		super(msg);
	}
}
