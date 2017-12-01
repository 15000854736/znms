package info.zznet.znms.web.module.topology.service;

import info.zznet.znms.base.entity.Graph;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.spider.bean.ScanHost;
import info.zznet.znms.web.module.common.service.BaseService;

import java.util.List;

/**
 * Created by shenqilei on 2016/10/26.
 */
public interface TopologyService extends BaseService {

    /**
     * 根据链接线两端节点ip，返回适合该链接线关联流量信息的主机
     * @param targetIp
     * @param sourceIp
     * @return
     */
    public List<ScanHost> getSelectedHost(String targetIp, String sourceIp);


    /**
     * 获取属于指定主机的接口图形信息
     * @param hostIp
     * @return
     */
    public List<Graph> getInterfaceGraph(String hostIp);

}
