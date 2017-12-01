package info.zznet.znms.spider.bean;

/**
 * Created by shenqilei on 2016/9/22.
 */
public class ScanResult {

    /**
     * rrd文件名称
     */
    private String rrdFile;

    /**
     * rrd文件数据源
     */
    private String rrdDS;

    /**
     * 需存储值
     */
    private String[] value;
    
    /**
     * 模板名
     */
    private String rrdTemplateName;
    
    /**
     * 数据名
     */
    private String rrdDataId;

	public String getRrdFile() {
        return rrdFile;
    }

    public void setRrdFile(String rrdFile) {
        this.rrdFile = rrdFile;
    }

    public String getRrdDS() {
        return rrdDS;
    }

    public void setRrdDS(String rrdDS) {
        this.rrdDS = rrdDS;
    }

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    public String getRrdTemplateName() {
		return rrdTemplateName;
	}

	public void setRrdTemplateName(String rrdTemplateName) {
		this.rrdTemplateName = rrdTemplateName;
	}

	public String getRrdDataId() {
		return rrdDataId;
	}

	public void setRrdDataId(String rrdDataId) {
		this.rrdDataId = rrdDataId;
	}
}
