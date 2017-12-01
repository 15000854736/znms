package info.zznet.znms.base.entity;

import java.util.Date;

/**
 * 图形树实体
 * @author dell001
 *
 */
public class GraphTree {
    private String graphTreeUuid;

    private String graphTreeName;

    private Date createTime;

    public String getGraphTreeUuid() {
        return graphTreeUuid;
    }

    public void setGraphTreeUuid(String graphTreeUuid) {
        this.graphTreeUuid = graphTreeUuid;
    }

    public String getGraphTreeName() {
        return graphTreeName;
    }

    public void setGraphTreeName(String graphTreeName) {
        this.graphTreeName = graphTreeName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}