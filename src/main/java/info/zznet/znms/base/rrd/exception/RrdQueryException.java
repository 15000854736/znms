package info.zznet.znms.base.rrd.exception;

public class RrdQueryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -258672626077727446L;

	public RrdQueryException(){
		super();
	}
	
	public RrdQueryException(String msg){
		super(msg);
	}
}