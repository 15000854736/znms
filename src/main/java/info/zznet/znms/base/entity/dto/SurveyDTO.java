/**
 * 
 */
package info.zznet.znms.base.entity.dto;

/**
 * @author dell001
 *
 */
public class SurveyDTO {

	//阀值主键
	private String thresholdValueUuid;
	
	//触发次数
	private Integer count;

	public String getThresholdValueUuid() {
		return thresholdValueUuid;
	}

	public void setThresholdValueUuid(String thresholdValueUuid) {
		this.thresholdValueUuid = thresholdValueUuid;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
