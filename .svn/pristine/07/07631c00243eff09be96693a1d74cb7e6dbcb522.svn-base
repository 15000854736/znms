package info.zznet.znms.web.module.topology.service.impl;

import info.zznet.znms.base.dao.GraphMapper;
import info.zznet.znms.base.dao.HostMapper;
import info.zznet.znms.base.entity.Graph;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.spider.Engine;
import info.zznet.znms.spider.bean.ScanHost;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.common.service.impl.BaseServiceImpl;
import info.zznet.znms.web.module.topology.service.TopologyService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenqilei on 2016/10/26.
 */
@Service("topologyService")
@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
public class TopologyServiceImpl extends BaseServiceImpl implements TopologyService{

    @Autowired
    private HostMapper hostMapper;

    @Autowired
    private GraphMapper graphMapper;

    @Override
    public List<ScanHost> getSelectedHost(String targetIp, String sourceIp) {
        List<ScanHost> result = new ArrayList<>();


        if (!StringUtils.isEmpty(targetIp)){
            ScanHost scanHost = Engine.hosts.get(targetIp);
            if (null!=scanHost){
                result.add(scanHost);
            }
        }


        if (!StringUtils.isEmpty(sourceIp)){
            ScanHost scanHost = Engine.hosts.get(sourceIp);
            if (null!=scanHost){
                result.add(scanHost);
            }
        }


        //targetIp和sourceIp均没有对应的主机，则返回全部主机
        if (result.isEmpty()){
            result.addAll(Engine.hosts.values());
        }

        return result;
    }

    @Override
    public List<Graph> getInterfaceGraph(String hostIp) {
        List<Graph> result = new ArrayList<>();

        Pager<Host> hostPager = new Pager<>();

        JSONArray hostSearch = new JSONArray();
        JSONObject hostIpCondition = new JSONObject();
        hostIpCondition.put("key","hostIp");
        hostIpCondition.put("value",hostIp);
        hostSearch.add(hostIpCondition);

        List<Host> hosts = hostMapper.findPageList(hostPager,hostSearch);
        if (!hosts.isEmpty()){
            Host host = hosts.get(0);


            Pager<Graph> graphPager = new Pager<>();
            graphPager.setLimit(Integer.MAX_VALUE);

            JSONArray graphSearch = new JSONArray();
            JSONObject hostIdCondition = new JSONObject();
            hostIdCondition.put("key","hostUuid");
            hostIdCondition.put("value",host.getId());
            graphSearch.add(hostIdCondition);

            JSONObject graphTypeCondition = new JSONObject();
            graphTypeCondition.put("key","graphType");
            graphTypeCondition.put("value",2);
            graphSearch.add(graphTypeCondition);

            result = graphMapper.findPageList(graphPager,graphSearch);

        }

        return result;
    }
}
