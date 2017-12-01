package info.zznet.znms.spider.bean;

/**
 * 扫描项
 * Created by shenqilei on 2016/9/21.
 */
public class ScanItem {

    /**
     * 对应rrd文件名
     */
    private String rrdFile;

    /**
     * rrd数据源名
     */
    private String rrdDS;

    /**
     * 采集oid
     */
    private String oid;

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

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
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
