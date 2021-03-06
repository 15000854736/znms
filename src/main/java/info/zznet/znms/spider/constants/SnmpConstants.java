package info.zznet.znms.spider.constants;

/**
 * Created by shenqilei on 2016/9/22.
 */
public class SnmpConstants {
	
	/**
     * 不使用SNMP协议版本
     */
    public static final String SNMP_VERSION_0 = "0";

    /**
     * SNMP协议版本 1
     */
    public static final String SNMP_VERSION_1 = "1";
    /**
     * SNMP协议版本 2c
     */
    public static final String SNMP_VERSION_2 = "2";
    /**
     * SNMP协议版本 3
     */
    public static final String SNMP_VERSION_3 = "3";


    /**
     * 主机状态，正常
     */
    public static final String HOST_STATUS_UP = "1";

    /**
     * 主机状态，宕机
     */
    public static final String HOST_STATUS_DOWN = "0";


    /**
     * SNMP 重试次数
     */
    public static final int SNMP_RETRIES = 2;


    /**
     * SNMP 超时，单位毫秒
     */
    public static final int SNMP_TIMEOUT = 1000;

    /**
     * 默认端口
     */
    public static final String SNMP_DEFAULT_PORT = "161";
    /**
     * 默认团体名
     */
    public static final String SNMP_DEFAULT_COMMUNITY = "public";

    /**
     * engine 队列大小
     */
    public static final int ENGINE_QUEUE_CAPACITY = 50000;

}
