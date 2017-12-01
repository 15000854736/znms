package info.zznet.znms.base.entity;

public class RrdDataInfo {
    private String rrdDataId;
    
    private String rrdDataName;

    private String rrdTemplateName;

    public String getRrdDataId() {
        return rrdDataId;
    }
    public void setRrdDataId(String rrdDataId) {
        this.rrdDataId = rrdDataId;
    }
    public String getRrdDataName() {
		return rrdDataName;
	}
	public void setRrdDataName(String rrdDataName) {
		this.rrdDataName = rrdDataName;
	}
    public String getRrdTemplateName() {
        return rrdTemplateName;
    }
    public void setRrdTemplateName(String rrdTemplateName) {
        this.rrdTemplateName = rrdTemplateName;
    }

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rrdDataId == null) ? 0 : rrdDataId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RrdDataInfo other = (RrdDataInfo) obj;
		if (rrdDataId == null) {
			if (other.rrdDataId != null)
				return false;
		} else if (!rrdDataId.equals(other.rrdDataId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RrdData [rrdDataId=" + rrdDataId + ", rrdTemplateName="
				+ rrdTemplateName + "]";
	}
}