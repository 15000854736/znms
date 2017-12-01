package info.zznet.znms.base.entity;

import java.util.List;

/**
 *图形树条目实体类
 * @author dell001
 *
 */
public class GraphTreeItem {
	
    private String graphTreeItemUuid;

    //节点标题
    private String title;

    private String parentUuid;

    //排序
    private Integer sort;
    
    //节点对象类型  1.节点  2.主机
    private Integer graphTreeItemType;
    
    //主机id
    private String hostId;
    
    private List<GraphTreeItem> childList;
    
    //节点对象为主机的信息
    private Host host;

    public String getGraphTreeItemUuid() {
        return graphTreeItemUuid;
    }

    public void setGraphTreeItemUuid(String graphTreeItemUuid) {
        this.graphTreeItemUuid = graphTreeItemUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

	public List<GraphTreeItem> getChildList() {
		return childList;
	}

	public void setChildList(List<GraphTreeItem> childList) {
		this.childList = childList;
	}

	public Integer getGraphTreeItemType() {
		return graphTreeItemType;
	}

	public void setGraphTreeItemType(Integer graphTreeItemType) {
		this.graphTreeItemType = graphTreeItemType;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}
	
}