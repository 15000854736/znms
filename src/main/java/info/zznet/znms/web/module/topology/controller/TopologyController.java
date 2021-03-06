package info.zznet.znms.web.module.topology.controller;

import com.alibaba.druid.filter.config.ConfigTools;
import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.entity.Graph;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.base.rrd.core.RrdFetcher;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.spider.Engine;
import info.zznet.znms.spider.bean.ScanHost;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.monitor.bean.MonitorQueryBean;
import info.zznet.znms.web.module.topology.service.TopologyService;
import info.zznet.znms.web.start.SystemStartThread;
import info.zznet.znms.web.util.ConfigUtil;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static info.zznet.znms.spider.Engine.hosts;

/**
 * Created by shenqilei on 2016/10/8.
 */
@Controller
@RequestMapping("/topology")
public class TopologyController extends BaseController{

    private static final String VIEW_MAIN = "topology/topology_main";

    @Autowired
    private TopologyService topologyService;

    /**
     * 初始化系统日志页面
     * @return
     */
    @RequestMapping("")
    @CheckPermission(PermissionConstants.P_TOPOLOGY_VIEW)
    public ModelAndView init(){
        return new ModelAndView(VIEW_MAIN);
    }

    @RequestMapping(value="/hostMap", method= RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getHostMap(){
        JSONObject result = new JSONObject();

        Map<String,ScanHost> hosts = Engine.hosts;

        Iterator<Map.Entry<String,ScanHost>> it = hosts.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,ScanHost> host = it.next();
            result.put(host.getKey(),host.getValue().getName());
        }

        return result.toString();
    }

    @RequestMapping(value="/iconMap", method= RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getIconMap(){
        JSONObject result = new JSONObject();

        result.put("ac","无线控制器");
        result.put("ap","AP设备");
        result.put("cache","缓存服务器");
        result.put("elog","eLog系统");
        result.put("eportal","ePortal系统");
        result.put("log","Z-LOG系统");
        result.put("nms","Z-NMS系统");
        result.put("os","Z-OS系统");
        //result.put("poe","POE接入交换机");
        result.put("portal","Z-PORTAL系统");
        result.put("riil","RIIL系统");
        result.put("sam","SAM系统");
        result.put("sam-self","SAM自助端");
        result.put("switch","交换机");
        result.put("vpn","VPN");
        result.put("access-switch","接入交换机");
        result.put("access-router","接入路由器");
        result.put("edu","教育网");
        result.put("core-switch","核心交换机");
        result.put("core-router","核心路由器");
        result.put("gray-cloud","灰云");
        result.put("green-cloud","绿云");
        result.put("gateway","网关");
        result.put("ctc","电信");
        result.put("cmcc","移动");
        result.put("cuc","联通");
        result.put("blue-cloud","蓝云");
        result.put("virtual-core-switch","虚拟核心交换机");
        result.put("virtual-core-router","虚拟核心路由器");
        result.put("firewall","防火墙");
        result.put("virtual-point","虚拟点");


        return result.toString();
    }

    @RequestMapping(value="/trafficHostMap", method= RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getTrafficHostMap(@RequestParam String targetIp,@RequestParam String sourceIp){
        JSONObject result = new JSONObject();

        List<ScanHost> hosts = topologyService.getSelectedHost(targetIp,sourceIp);

        result.put("","无");
        for (ScanHost scanHost:hosts){
            result.put(scanHost.getIp(),scanHost.getName());
        }

        return result.toString();
    }

    @RequestMapping(value="/trafficMap", method= RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getTrafficMap(@RequestParam String hostIp){
        JSONObject result = new JSONObject();

        List<Graph> graphs = topologyService.getInterfaceGraph(hostIp);

        result.put("","无");
        for (Graph graph:graphs){
            result.put(graph.getGraphUuid(),graph.getGraphName());
        }

        return result.toString();
    }


    @RequestMapping(value="/save", method= RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(@RequestParam String topoData){
        String result = "failed";
        try{
            String topoFilePath = ConfigUtil.getString("znms.topo.path");
            File file = new File(topoFilePath);
            FileUtils.write(file,topoData,false);
            result = "ok";
        }catch (Exception e){
            ZNMSLogger.error(e);
        }finally {
            result = "failed";
        }

        return result;
    }

    @RequestMapping(value="/get", method= RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String get(){
        String result = "";
        try{
            String topoFilePath = ConfigUtil.getString("znms.topo.path");
            File file = new File(topoFilePath);
            if(file.exists()){
                result = FileUtils.readFileToString(file);
            }else{
                JSONObject jsonObject = new JSONObject();
                JSONArray nodes = new JSONArray();
                JSONArray links = new JSONArray();
                JSONArray rects = new JSONArray();
                JSONObject node = new JSONObject();
                node.put("id",0);
                node.put("x",0);
                node.put("y",0);
                node.put("icon","nms");
                node.put("hostIp","127.0.0.1");
                nodes.add(node);
                jsonObject.put("nodes",nodes);
                jsonObject.put("links",links);
                jsonObject.put("rects",rects);
                result = jsonObject.toString();
                FileUtils.write(file,result);
            }
        }catch (Exception e){
            result = "";
            ZNMSLogger.error(e);
        }

        return result;
    }

    @RequestMapping(value="/sync", method= RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String sync(){
        JSONObject result = new JSONObject();
        StringBuilder downIp = new StringBuilder();
        StringBuilder yellowLink = new StringBuilder();
        StringBuilder redLink = new StringBuilder();


        try{
            //主机状态
            Iterator<Map.Entry<String, ScanHost>> it = Engine.hosts.entrySet().iterator();

            while (it.hasNext()){
                Map.Entry<String, ScanHost> entry = it.next();

                if(!entry.getValue().isReachable()){
                    //down
                    downIp.append(entry.getKey()).append(",");
                }
            }

            //链接线状态
            File topoFile = new File(ConfigUtil.getString("znms.topo.path"));
            JSONObject jsonObject = JSONObject.fromObject(FileUtils.readFileToString(topoFile));
            JSONArray links = jsonObject.getJSONArray("links");
            if (null!=links){
                int length = links.size();
                for(int i=0;i<length;i++){
                    JSONObject link = links.getJSONObject(i);
                    if(link.containsKey("graphId")&&link.containsKey("maxBandwidth")){
                        String graphId = link.getString("graphId");
                        String maxBandwidthStr = link.getString("maxBandwidth");

                        double maxBandwidth = transfromTraffic(maxBandwidthStr);
                        if (maxBandwidth>0){
                            double[] traffic = RrdFetcher.fetchLatestData(SystemConstants.TEMPLATE_NAME_NET_STREAM,graphId);

                            double maxValue = 0;
                            int itemLength = traffic.length;
                            for (int j=0;j<itemLength;j++){
                                if (traffic[j]>maxValue){
                                    maxValue = traffic[j];
                                }
                            }

                            if(maxValue>maxBandwidth*0.8){
                                //已超过设定最大值的80%
                                redLink.append(graphId).append("-").append(maxBandwidthStr).append(",");
                            }else if(maxValue>maxBandwidth*0.6){
                                //已超过设定最大值的60%
                                yellowLink.append(graphId).append("-").append(maxBandwidthStr).append(",");
                            }

                        }
                    }
                }
            }
        }catch (Exception e){
            ZNMSLogger.error(e);
        }
        result.put("downIp",downIp.toString());
        result.put("yellowLink",yellowLink.toString());
        result.put("redLink",redLink.toString());
        return result.toString();
    }


    private double transfromTraffic(String maxBandwidth){
        double result = 0;

        try{

            if (StringUtils.isNotEmpty(maxBandwidth)){

                int multiple  = 1;
                String bandwidth = "";
                int length = StringUtils.length(maxBandwidth);
                if (StringUtils.endsWithIgnoreCase(maxBandwidth,"k")){
                    multiple = 1024;
                    bandwidth = StringUtils.substring(maxBandwidth,0,length-1);
                }else if(StringUtils.endsWithIgnoreCase(maxBandwidth,"m")){
                    multiple = 1024*1024;
                    bandwidth = StringUtils.substring(maxBandwidth,0,length-1);
                }else if(StringUtils.endsWithIgnoreCase(maxBandwidth,"g")){
                    multiple = 1024*1024*1024;
                    bandwidth = StringUtils.substring(maxBandwidth,0,length-1);
                }else{
                    bandwidth = maxBandwidth;
                }

                result = Double.parseDouble(bandwidth)*multiple;

            }
        }catch (Exception e){
            result = 0;
            //ignore
        }

        return result;
    }


}
