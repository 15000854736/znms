package info.zznet.znms.base.entity;

import java.util.Date;

/**
 * ap信息实体类
 * @author dell001
 *
 */
public class ApInformation {
    private String apInformationUuid;
    
    private String apRegionUuid;

    private String apIp;

    private String apMac;

    private String acIp;

    //坐标
    private String apAxis;

    //ap在线用户数
    private Integer apUserCount;
    
    //ap名称
    private String apName;
     
    //AP最大在线人数
    private Integer apUserMax;
    
    
    //最后更新时间
    private Date lastUpdateTime;
    
    //在线人数总和
    private Integer apAllCount;
    
    public Integer getApAllCount() {
		return apAllCount;
	}

	public void setApAllCount(Integer apAllCount) {
		this.apAllCount = apAllCount;
	}

	public String getApInformationUuid() {
        return apInformationUuid;
    }

    public void setApInformationUuid(String apInformationUuid) {
        this.apInformationUuid = apInformationUuid;
    }
    
    public String getApRegionUuid() {
		return apRegionUuid;
	}

	public void setApRegionUuid(String apRegionUuid) {
		this.apRegionUuid = apRegionUuid;
	}

	public String getApIp() {
        return apIp;
    }

    public void setApIp(String apIp) {
        this.apIp = apIp;
    }

    public String getApMac() {
        return apMac;
    }

    public void setApMac(String apMac) {
        this.apMac = apMac;
    }

    public String getAcIp() {
        return acIp;
    }

    public void setAcIp(String acIp) {
        this.acIp = acIp;
    }

    public String getApAxis() {
        return apAxis;
    }

    public void setApAxis(String apAxis) {
        this.apAxis = apAxis;
    }

    public Integer getApUserCount() {
        return apUserCount;
    }

    public void setApUserCount(Integer apUserCount) {
        this.apUserCount = apUserCount;
    }

	public String getApName() {
		return apName;
	}

	public void setApName(String apName) {
		this.apName = apName;
	}

	public Integer getApUserMax() {
		return apUserMax;
	}

	public void setApUserMax(Integer apUserMax) {
		this.apUserMax = apUserMax;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
    
}