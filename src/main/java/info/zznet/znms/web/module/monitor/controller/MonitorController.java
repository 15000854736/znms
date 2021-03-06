package info.zznet.znms.web.module.monitor.controller;

import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.dao.GraphExtendMapper;
import info.zznet.znms.base.dao.HostMapper;
import info.zznet.znms.base.entity.GraphTree;
import info.zznet.znms.base.rrd.bean.Graph;
import info.zznet.znms.base.rrd.core.RrdFetcher;
import info.zznet.znms.base.rrd.exception.RrdQueryException;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.monitor.bean.GraphItem;
import info.zznet.znms.web.module.monitor.bean.GraphTreeItemBean;
import info.zznet.znms.web.module.monitor.bean.MonitorQueryBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/monitor")
public class MonitorController extends BaseController{
	
	@Autowired
	private GraphExtendMapper graphMapper;
	
	@Autowired
	private HostMapper hostMapper;
		
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	@RequestMapping({"","/"})
	@CheckPermission(PermissionConstants.P_GRAPH_MONITOR_VIEW)
	public ModelAndView init(){
		List<GraphTree> treeList = graphMapper.findAllTrees();
		
		String firstNode = "";
		String secondNode = "";
		String thirdNode = "";
		if(treeList != null) {
			out:for(GraphTree root:treeList) {
				List<GraphTreeItemBean> branchList = graphMapper.findByParentId(root.getGraphTreeUuid());
				if(branchList == null) {
					continue;
				}
				in:for(GraphTreeItemBean branch : branchList) {
					if(branch.getGraphTreeItemType().compareTo(2)==0){
						firstNode = root.getGraphTreeUuid();
						secondNode = branch.getGraphTreeItemUuid();
						break out;
					} else {
						List<GraphTreeItemBean> leafList = graphMapper.findByParentId(branch.getGraphTreeItemUuid());
						if(leafList == null){
							continue in;
						}
						for(GraphTreeItemBean leaf : leafList){
							firstNode = root.getGraphTreeUuid();
							secondNode = branch.getGraphTreeItemUuid();
							thirdNode = leaf.getGraphTreeItemUuid();
							break out;
						}
					}
				}
			}
		}
		ModelAndView mav = new ModelAndView("monitor/deviceMonitor");
		mav.addObject("treeList", treeList);
		mav.addObject("firstNode", firstNode);
		mav.addObject("secondNode", secondNode);
		mav.addObject("thirdNode", thirdNode);
		return mav;
	}
	
	@RequestMapping(value="/query", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(MonitorQueryBean queryBean){
		
		List<GraphItem> graphItemList = graphMapper.findPagedData(queryBean);
		for(GraphItem graphItem : graphItemList) {
			// 上下行流量特殊处理
			if(graphItem.getGraphType().compareTo(2)==0){
				graphItem.setGraphSimpleName(SystemConstants.TEMPLATE_NAME_NET_STREAM);
			}
			Graph graphData = null;
			try {
				try {
					graphData = RrdFetcher.fetchDataAndConvertToGraph(graphItem.getGraphSimpleName(), graphItem.getGraphUuid(), sdf.parse(queryBean.getFromTime()), sdf.parse(queryBean.getToTime()));
				} catch (ParseException e) {
					ZNMSLogger.error(e);
				}
				graphItem.setGraphData(graphData);
			} catch (RrdQueryException e) {
				ZNMSLogger.error(e);
			}
		}
		return JSONArray.fromObject(graphItemList).toString();
	}
	
	@RequestMapping(value="/getSubHost", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getSubHost(String id){
		List<GraphTreeItemBean> list = graphMapper.findByParentId(id);
		return JSONArray.fromObject(list).toString();
	}
}
