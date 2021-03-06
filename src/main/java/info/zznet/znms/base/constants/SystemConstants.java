package info.zznet.znms.base.constants;


public class SystemConstants {

	/**
	 * SessionBean的key
	 */
	public static final String SESSION_BEAN_KEY = "SESSION_BEAN";
	
	/**
	 * 通用message
	 */
	public static final String KEY_REQUEST_MESSAGE = "r_message";
	
	/**
	 * loginbean的message
	 */
	public static final String ERROR_LOGIN_NAME_REQUIRED = "{err.login.name.required}";
	public static final String ERROR_LOGIN_PASSWORD_REQUIRED = "{err.login.password.required}";
	public static final String ERROR_LOGIN_NAME_LENGTH = "{err.login.name.length}";
	public static final String ERROR_LOGIN_PASSWORD_LENGTH = "{err.login.password.length}";
	public static final String ERROR_LOGIN_NAME_PASSWORD_WRONG = "common.login.name.password.wrong";
	public static final String ERROR_LOGIN_ADMIN_NOT_ACTIVATED_WRONG = "common.login.admin.not.activated.wrong";
	public static final String ERROR_LOGIN_ADMIN_disabled_WRONG = "common.login.admin.disabled.wrong";
	public static final String INFO_LOGIN_ADMIN_LOGIN_OUT = "common.login.login.out.info";
	public static final String ERROR_CAPTCHA = "err.captcha";
	/**
	 * kaptcha验证码
	 */
	public static final String KEY_VALIDATE_CODE = "KAPTCHA"; // 验证码
	
	/**
	 * 用户登录信息
	 */
	public static final String MESSAGE_TYPE_SUCCESS = "success";
	public static final String MESSAGE_TYPE_ERROR = "error";
	public static final String MESSAGE_TYPE_INFO = "info";
	/**
	 * 用户进行数据操作结果信息
	 */
	public static final String MESSAGE_TYPE_RESULT_MSG = "result_msg";
	public static final String MESSAGE_TYPE_SAVE_SUCCESS = "save_success";
	public static final String MESSAGE_TYPE_UPDATE_SUCCESS = "update_success";
	public static final String MESSAGE_TYPE_SAVE_FAILED = "save_failed";
	public static final String MESSAGE_TYPE_UPDATE_FAILED = "update_failed";
	public static final String COMMON_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
	/**
	 * 配置文件路径
	 */
	public static final String DEPLOY_PATH = "/opt/znms/conf";
	
	/**
	 * 最大的excel导出数量
	 */
	public static final int MAX_EXPORT_NUM = 10000;
	
	/**
	 * 导出数据的session_key
	 */
	public static final String EXPORT_DATA_SESSION_KEY = "exportDataSessionKey";
	
	/**
	 * tftp安装路径
	 */
	public static final String TFTP_PATH = "/opt/tftp";
	
	/**
	 * 网关流量模板名
	 */
	public static final String TEMPLATE_NAME_NET_STREAM = "netStream";
	/**
	 * 上行流量数据源名
	 */
	public static final String DATA_SOURCE_NAME_UP = "upStream";
	/**
	 * 下行流量数据源名
	 */
	public static final String DATA_SOURCE_NAME_DOWN = "downStream";
	
	/**
	 * ap地图格式
	 */
	public static final String AP_MAP_SUFFIX = "jpg|png|JPG|PNG";

	public static final String SYSTEM_LOG_TOPIC = "znms_system_log";
	
	public final static String MAC_KEY="LOCAL_MAC";
    public  static final String SYS_EFFECTIVE_TIME="SYS_EFFECTIVE_TIME";
    public  static final String SYS_ACTIVATION_STATE="SYS_ACTIVATION_STATE";
	
}
