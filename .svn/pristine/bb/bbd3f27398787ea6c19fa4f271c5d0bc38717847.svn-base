/**
 * 
 */
package info.zznet.znms.web.module.system.controller;

import info.zznet.znms.base.bean.SearchListBean;
import info.zznet.znms.base.common.BaseController;
import info.zznet.znms.base.constants.PermissionConstants;
import info.zznet.znms.base.dao.GraphTreeItemMapper;
import info.zznet.znms.base.entity.GraphTree;
import info.zznet.znms.base.entity.GraphTreeItem;
import info.zznet.znms.base.entity.Host;
import info.zznet.znms.web.annotation.CheckPermission;
import info.zznet.znms.web.module.common.page.Pager;
import info.zznet.znms.web.module.system.service.GraphTreeItemService;
import info.zznet.znms.web.module.system.service.GraphTreeService;
import info.zznet.znms.web.module.system.service.HostService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 图形树controller
 * @author dell001
 *
 */
@Controller
@RequestMapping("/graphTree")
public class GraphTreeController extends BaseController{
	
	@Autowired
	private GraphTreeService graphTreeService;
	
	@Autowired
	private GraphTreeItemMapper graphTreeItemMapper;
	
	@Autowired
	private HostService hostService;
	
	@Autowired
	private GraphTreeItemService graphTreeItemService;
	
	private static final String VIEW_MAIN = "system/graphTree/graphTree_main";
	
	private static final String VIEW_DETAIL = "system/graphTree/graphTree_detail";
	
	/**
	 * 跳转到图形树主页面
	 * @return
	 */
	@RequestMapping("")
	@CheckPermission(PermissionConstants.P_GRAPH_TREE_VIEW)
	public ModelAndView init(){
		return new ModelAndView(VIEW_MAIN);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	@CheckPermission(PermissionConstants.P_GRAPH_TREE_VIEW)
	public String doSearch(Pager<GraphTree> pager,HttpServletRequest request,HttpSession session) throws JsonGenerationException, JsonMappingException, IOException {
		pager = graphTreeService.findPageList(pager);
		return JSONObject.fromObject(pager).toString();
	}
	
	/**
	 * 添加图形树
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@CheckPermission(PermissionConstants.P_GRAPH_TREE_ADD)
	public ModelAndView addGraphTree(GraphTree graphTree){
		graphTreeService.addGraphTree(graphTree);
		return new ModelAndView("redirect:/graphTree").addObject("page","add");
	}
	
	/**
     * 加载图形树条目数据
     * @return
     */
    @RequestMapping(value="/loadGraphTreeItem/{graphTreeUuid}", 
    		produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String loadGraphTreeItem(@PathVariable("graphTreeUuid")String graphTreeUuid){
    	List<GraphTreeItem> list = graphTreeItemMapper.findAllRootByGraphTreeUuid(graphTreeUuid);
    	return JSONArray.fromObject(list).toString();
    }
    
    /**
     * 跳转到图形树修改页面
     * @param graphTreeUuid
     * @return
     */
    @RequestMapping(value = "/edit/{graphTreeUuid}", method = RequestMethod.GET)
    @CheckPermission(PermissionConstants.P_GRAPH_TREE_EDIT + "||" + PermissionConstants.P_GRAPH_TREE_DETAIL)
    public ModelAndView toDetailPage(@PathVariable("graphTreeUuid")String graphTreeUuid){
    	GraphTree graphTree = graphTreeService.findByPrimaryKey(graphTreeUuid);
    	return new ModelAndView("system/graphTree/graphTree_detail").addObject(graphTree).addObject("page","edit");
    }
	
    /**
     * 编辑图形树
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
	@CheckPermission(PermissionConstants.P_GRAPH_TREE_EDIT)
    public ModelAndView editGraphTree(@ModelAttribute GraphTree graphTree){
    	graphTreeService.editGraphTree(graphTree);
    	return new ModelAndView("redirect:/graphTree");
    }
    
    /**
     * 查找图形树下所有一级节点
     * @param graphTreeUuid
     * @return
     */
    @RequestMapping(value = "/getNode/{graphTreeUuid}", method = RequestMethod.POST)
    @ResponseBody
    public String getNode(@PathVariable("graphTreeUuid")String graphTreeUuid){
    	//查找所有节点（不包含主机）
    	List<GraphTreeItem> list = graphTreeItemMapper.getNode(graphTreeUuid);
   	 	List<SearchListBean> sList = new ArrayList<SearchListBean>();
   		SearchListBean bean = new SearchListBean(graphTreeUuid, "root");
   		sList.add(bean);
   	 	for(GraphTreeItem graphTreeItem :list){
   	 		bean = new SearchListBean(graphTreeItem.getGraphTreeItemUuid(),graphTreeItem.getTitle());
   	 		sList.add(bean);
   	 	}
   		return JSONArray.fromObject(sList).toString();
    }
    
    /**
     * 查找所有主机
     * @param nodeUuid
     * @return
     */
    @RequestMapping(value = "/getHost/{nodeUuid}", method = RequestMethod.POST)
    @ResponseBody
    public String getHost(@PathVariable("nodeUuid")String nodeUuid){
    	//查找所有主机（如果主机已经存在该节点下，就排除该节点）
    	List<Host> list = hostService.findApplicableHost(nodeUuid);
   	 	List<SearchListBean> sList = new ArrayList<SearchListBean>();
   	 	for(Host host :list){
   	 		SearchListBean bean = new SearchListBean(host.getId(),host.getHostName()+"("+host.getHostIp()+")");
   	 		sList.add(bean);
   	 	}
   		return JSONArray.fromObject(sList).toString();
    }
    
    /**
     * 添加节点或主机
     * @param graphTreeItem
     * @return
     */
    @RequestMapping(value = "/addGraphTreeItem",method = RequestMethod.POST)
    @CheckPermission(PermissionConstants.P_GRAPH_TREE_ADD)
    @ResponseBody
    public String addGraphTreeItem(GraphTreeItem graphTreeItem){
    	graphTreeItemService.addGraphTreeItem(graphTreeItem);
    	return "{\"result\":true}";
    }
    
    /**
     * 修改节点标题
     * @param title
     * @param graphTreeItemUuid
     * @param parentUuid
     * @return
     */
    @RequestMapping(value = "/updateGraphTreeItem", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @CheckPermission(PermissionConstants.P_GRAPH_TREE_EDIT)
    @ResponseBody
    public String updateGraphTreeItem(@RequestParam("title")String title, @RequestParam("graphTreeItemUuid")String graphTreeItemUuid,
    		@RequestParam("parentUuid")String parentUuid){
    	GraphTreeItem item = graphTreeItemService.findItemByTitleAndParentUuid(title,parentUuid, graphTreeItemUuid);
    	if(null!=item){
    		return "{\"result\":false,\"msg\":\"标题重复\"}";
    	}else{
    		graphTreeItemService.updateGraphTreeItem(title,graphTreeItemUuid);
        	return "{\"result\":true}";
    	}
    }
    
    /**
     * 删除节点
     * @param graphTreeItemUuid
     * @return
     */
    @RequestMapping(value = "/deleteGraphTreeItem", method = RequestMethod.POST)
    @CheckPermission(PermissionConstants.P_GRAPH_TREE_DELETE)
    @ResponseBody
    public String deleteGraphTreeItem(@RequestParam("graphTreeItemUuid")String graphTreeItemUuid){
    	graphTreeItemService.deleteGraphTreeItem(graphTreeItemUuid);
    	return "{\"result\":true}";
    }
    
    /**
     * 删除图形树
     * @param jsonData
     * @return
     */
    @RequestMapping(value = "/deleteSingle",method = RequestMethod.POST)
    @CheckPermission(PermissionConstants.P_GRAPH_TREE_DELETE)
    @ResponseBody
    public String deleteSingle(@RequestParam("jsonData") String jsonData){
    	JSONObject jsonObject = JSONObject.fromObject(jsonData);
   		String id = jsonObject.getString("graphTreeUuid");
   		if("11111111111111111111111111111111".equals(id)){
   			return "{\"result\":false,\"msg\":\"系统默认图形树无法删除\"}";
   		}else{
   			graphTreeService.deleteGraphTree(id);
   	   		return "{\"result\":true}";
   		}
    }
    
    /**
   	 * 删除选中数据（复数）
   	 * @param jsonData 选中的数据
   	 * @param request
   	 * @return
   	 */
   	@RequestMapping(value = "/delete", method = RequestMethod.POST)
   	@ResponseBody
   	@CheckPermission(PermissionConstants.P_GRAPH_TREE_DELETE)
   	public String deleteMulti(@RequestParam("jsonData") String jsonData) {
   		JSONArray jsonArray = JSONArray.fromObject(jsonData);
   		List<String> uuids = new ArrayList<String>();
   		String pkId = "graphTreeUuid";
   		for(int i=0,j=jsonArray.size();i<j;i++){
   			uuids.add((String) jsonArray.getJSONObject(i).get(pkId));
   		}
   		//判断有没有系统默认图形树
   		for (String uuid : uuids) {
			if("11111111111111111111111111111111".equals(uuid)){
				return "{\"result\":false,\"msg\":\"删除的图形树列表中含有系统默认图形树,删除失败\"}";
			}
		}
   		graphTreeService.deleteByUuidList(uuids);
   		// 显示指定数据被删除后的画面
   		return JSONObject.fromObject(graphTreeService.findPageList(new Pager())).toString();
   	}
   	
   	/**
   	 * 上移下移节点
   	 * @param direction
   	 * @param graphTreeItemUuid
   	 * @return
   	 */
   	@RequestMapping(value = "/shiftGraphTreeItem", method = RequestMethod.POST)
   	@ResponseBody
   	public String shiftGraphTreeItem(@RequestParam("direction")boolean direction, 
   			@RequestParam("graphTreeItemUuid")String graphTreeItemUuid){
   		graphTreeItemService.shiftGraphTreeItem(direction, graphTreeItemUuid);
   		return "{\"result\":true}";
   	}
   	
   	/**
   	 * 查询图形树名称是否重复
   	 * @param graphTreeName
   	 * @param graphTreeUuid
   	 * @return
   	 */
   	@RequestMapping(value = "/checkNameRepeat",method = RequestMethod.POST)
   	@ResponseBody
   	public boolean checkNameRepeat(@RequestParam("graphTreeName")String graphTreeName,
   			@RequestParam("graphTreeUuid")String graphTreeUuid){
   		return graphTreeService.checkNameRepeat(graphTreeName,graphTreeUuid)==null ?false:true;
   	}

}
