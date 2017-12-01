package info.zznet.znms.web.module.systemLog.collector;


public enum Severity {
	/** System is unusable. */
    EMERG(0, "紧急"),
    /** Action must be taken immediately. */
    ALERT(1, "警报"),
    /** Critical conditions. */
    CRITICAL(2, "严重"),
    /** Error conditions. */
    ERROR(3, "错误"),
    /** Warning conditions. */
    WARNING(4, "警告"),
    /** Normal but significant conditions. */
    NOTICE(5, "通知"),
    /** Informational messages. */
    INFO(6, "信息"),
    /** Debug level messages. */
    DEBUG(7, "调试");

    private final int code;
    private final String text;
    
    private Severity(final int code, final String text) {
        this.code = code;
        this.text = text;
    }
    
    public static Severity valueOf(int code){
    	for(Severity s : Severity.values()){
    		if(s.getCode() == code){
    			return s;
    		}
    	}
    	return null;
    }

    /**
     * Returns the severity code.
     * @return The numeric value associated with the Severity.
     */
    public int getCode() {
        return this.code;
    }
    public String getText(){
    	return this.text;
    }

    /**
     * Determine if the name matches this Severity.
     * @param name the name to match.
     * @return true if the name matches, false otherwise.
     */
    public boolean isEqual(final String name) {
        return this.name().equalsIgnoreCase(name);
    }
}
