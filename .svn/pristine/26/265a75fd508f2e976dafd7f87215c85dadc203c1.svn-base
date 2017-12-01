package info.zznet.znms.base.rrd.conf;

import org.rrd4j.DsType;
import org.rrd4j.core.DsDef;

public class RrdDsDef extends DsDef{
	private String showName;
	
	public RrdDsDef(String dsName, DsType dsType, long heartbeat,
			double minValue, double maxValue) {
		super(dsName, dsType, heartbeat, minValue, maxValue);
	}
	
	public RrdDsDef(String dsName, DsType dsType, long heartbeat,
			double minValue, double maxValue, String dsShowName) {
		super(dsName, dsType, heartbeat, minValue, maxValue);
		this.showName = dsShowName;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
 
}
