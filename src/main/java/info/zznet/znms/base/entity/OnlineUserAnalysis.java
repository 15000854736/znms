package info.zznet.znms.base.entity;

import java.util.Date;

/**
 * 在线用户统计分析表实体
 * @author dell001
 *
 */
public class OnlineUserAnalysis {
	
	public static final Integer WIRE_TYPE = 1;
	
	public static final Integer WIRELESS_TYPE = 2;
	
	public static final Integer OTHER_TYPE = 3;
	
    private String onlineUserAnalysisUuid;

    //用户类型   1：电脑；2：移动 ；3：其他
    private Integer userType;

    //用户数
    private Integer userCount;

    private Date createTime;

    public String getOnlineUserAnalysisUuid() {
        return onlineUserAnalysisUuid;
    }

    public void setOnlineUserAnalysisUuid(String onlineUserAnalysisUuid) {
        this.onlineUserAnalysisUuid = onlineUserAnalysisUuid;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}