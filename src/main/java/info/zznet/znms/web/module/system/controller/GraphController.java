/**
 * 
 */
package info.zznet.znms.web.module.system.controller;

import info.zznet.znms.base.bean.SearchListBean;
import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.dao.GraphTemplateMapper;
import info.zznet.znms.base.entity.Graph;
import info.zznet.znms.base.entity.GraphTemplate;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.spider.bean.Interface;
import info.zznet.znms.spider.bean.ScanHost;
import info.zznet.znms.spider.util.SnmpUtil;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.system.service.GraphService;
import info.zznet.znms.web.module.system.service.HostService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author dell001
 *
 */
@Controller
@RequestMapping("/graph")
public class GraphController extends BaseController{

	@Autowired
	private GraphService graphService;
	
	@Autowired
	private GraphTemplateMapper graphTemplateMapper;
	
	@Autowired
	private HostService hostService;
	
	private static final String VIEW_MAIN = "system/graph/graph_main";
	
	private static final String VIEW_ADD = "system/graph/graph_add";
	
	/**
	 * 跳转到图形管理主页面
	 * @return
	 */
	@RequestMapping("")
	@CheckPermission(PermissionConstants.P_GRAPH_VIEW)
	public ModelAndView init(){
		return new ModelAndView(VIEW_MAIN);
	}
	
	/**
	 * 查找图形列表
	 * @param pager
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_HOST_VIEW)
	public String doSearch(Pager<Graph> pager) throws JsonGenerationException, JsonMappingException, IOException {
		pager = graphService.findPageList(pager);
		return JSONObject.fromObject(pager,getJsonConfig()).toString();
	}
	
	/**
	 * 分页查询的JsonConfig配置
	 * @return
	 */
	public JsonConfig getJsonConfig(){
    	JsonConfig jsonCfg = new JsonConfig();
    	jsonCfg.setIgnoreDefaultExcludes(false);
		jsonCfg.setExcludes(new String[]{});
		// 防止自包含引发的死循环
		jsonCfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		//转换图形类型
		jsonCfg.registerJsonValueProcessor(Graph.class, "graphType", new JsonValueProcessor() {

			@Override
			public Object processArrayValue(Object o, JsonConfig jsonConfig) {
				return null;
			}

			@Override
			public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
				if (null != o) {
					Integer graphType = (Integer)o;
					int value = graphType.intValue();
					String returndesc;
					switch(value){
					case 1:
						returndesc = "基本图形";break;
					case 2:
						returndesc = "接口图形";break;
					default:
						returndesc = "未知";
					}
					return returndesc;
				} else {
					return "";
				}
			}
		});
		
		return jsonCfg;
    }
	
	/**
	 * 查找所有图形模板
	 * @return
	 */
	@RequestMapping(value = "/findGraphTemplateJson", method = RequestMethod.POST)
	@ResponseBody
	public String findGraphTemplateJson(){
		List<GraphTemplate> templateList = graphTemplateMapper.findAll();
		List<SearchListBean> beanList = new ArrayList<SearchListBean>();
		beanList.add(new SearchListBean("-1","请选择图形模板"));
		for (GraphTemplate template : templateList) {
			SearchListBean bean = new SearchListBean(template.getGraphTemplateId(), template.getGraphTemplateName());
			beanList.add(bean);
		}
		return JSONArray.fromObject(beanList).toString();
	}
	
	/**
	 * 跳转到添加图形页面
	 * @return
	 */
	@RequestMapping(value = "/add")
	@CheckPermission(PermissionConstants.P_GRAPH_ADD)
	public ModelAndView toAddPage(){
		return new ModelAndView(VIEW_ADD);
	}
	
	/**
	 * 查找设备接口信息
	 * @param hostUuid
	 * @return
	 */
	@RequestMapping(value = "/findInterfaceList", method = RequestMethod.POST)
	public ModelAndView findInterfaceList(@RequestParam("hostUuid")String hostUuid, 
			@RequestParam("current")Integer current, Pager<Interface> pager){
		Host host = hostService.findHostById(hostUuid);
		if(null != host){
			ScanHost scanHost = new ScanHost();
			scanHost.setIp(host.getHostIp());
			scanHost.setSnmpVersion(String.valueOf(host.getSnmpVersion()));
			scanHost.setStatus(String.valueOf(host.getStatus()));
			scanHost.setCommunity(host.getSnmpCommunity());
			List<Interface> results = SnmpUtil.getInterfaces(scanHost);
			//计算需要显示的结果数据
			List<Interface> interfaceNewList = new ArrayList<Interface>();
			int total = results.size();
			pager.setCurrent(current);
			int pageHeader = (pager.getCurrent()-1+pager.getOffset()/10)*10+1;
			int pageTail = pageHeader+9;
			if(pageHeader>0){
				for(int i= pageHeader;i<=pageTail && i<=total;i++){
					results.get(i-1).setStatus("1".equals(results.get(i-1).getStatus()) ?"正常":"宕机");
					interfaceNewList.add(results.get(i-1));	
				}
			}
			//查询总数据并重新组装pager对象
			pager.setTotal((long)total);
			pager.setRows(interfaceNewList);
		}
		ModelAndView mav = new ModelAndView("system/graph/interface_html");
		mav.addObject("pager",pager);
		return mav;
	}
	
	/**
	 * 添加图形
	 * @param graph
	 * @return
	 */
	@RequestMapping(value = "/addGraph", method = RequestMethod.POST)
	@CheckPermission(PermissionConstants.P_GRAPH_ADD)
	@ResponseBody
	public String addGraph(Graph graph){
		//判断该主机的这种类型图形是否存在
		boolean boo = graphService.findGraphByCondition(graph);
		if(!boo){
			return "{\"result\":false,\"msg\":\"该主机已存在这种类型图形\"}";
		}
		graphService.addGraph(graph);
		return "{\"result\":true}";
	}
	
	/**
	 * 删除单个图形
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/deleteSingle", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_GRAPH_DELETE)
	public String deleteSingle(@RequestParam("jsonData") String jsonData){
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
   		String graphUuid = jsonObject.getString("graphUuid");
		graphService.deleteByPrimaryKey(graphUuid);
		return "{\"result\":true}";
	}
	
	/**
	 * 删除多个图形
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_GRAPH_DELETE)
	public String deleteMulti(@RequestParam("jsonData") String jsonData){
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
   		List<String> uuids = new ArrayList<String>();
   		String pkId = "graphUuid";
   		for(int i=0,j=jsonArray.size();i<j;i++){
   			uuids.add((String) jsonArray.getJSONObject(i).get(pkId));
   		}
   		graphService.deleteGraphList(uuids);
   		return JSONObject.fromObject(graphService.findPageList(new Pager())).toString();
	}
	
	/**
	 * 查找所有图形
	 * @return
	 */
	@RequestMapping(value = "/findGraphJson", method = RequestMethod.POST)
	@ResponseBody
	public String findGraphJson(){
		List<Graph> graphList = graphService.findAll();
		List<SearchListBean> beanList = new ArrayList<SearchListBean>(); 
		beanList.add(new SearchListBean("-1", "请选择图形"));
		for (Graph graph : graphList) {
			SearchListBean bean = new SearchListBean(graph.getGraphUuid(), graph.getGraphName());
			beanList.add(bean);
		}
		return JSONArray.fromObject(beanList).toString();
	}
	
	/**
	 * 根据主机查找图形
	 * @param hostUuid
	 * @return
	 */
	@RequestMapping(value = "findGraphByHost", method = RequestMethod.POST)
	@ResponseBody
	public String findGraphByHost(@RequestParam("hostUuid")String hostUuid){
		List<Graph> graphList = graphService.findGraphByHost(hostUuid);
		List<SearchListBean> beanList = new ArrayList<SearchListBean>(); 
		beanList.add(new SearchListBean("-1", "请选择图形"));
		for (Graph graph : graphList) {
			SearchListBean bean = new SearchListBean(graph.getGraphUuid(), graph.getGraphName());
			beanList.add(bean);
		}
		return JSONArray.fromObject(beanList).toString();
	}

	/**
	 * 查询图形类型
	 * @param graphUuid
	 * @return
	 */
	@RequestMapping(value = "/checkGraphType", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkGraphType(@RequestParam("graphUuid")String graphUuid){
		return graphService.checkGraphType(graphUuid)==1 ?true:false;
	}
}
