package info.zznet.znms.web.taglib;

import info.zznet.znms.base.dao.SmPermissionMapper;
import info.zznet.znms.base.entity.SmPermission;
import info.zznet.znms.base.util.SpringContextUtil;
import info.zznet.znms.base.dao.SmPermissionMapper;
import info.zznet.znms.base.entity.SmPermission;
import info.zznet.znms.base.util.SpringContextUtil;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 权限树标签类
 */
public class TreeTag extends TagSupport {

	private static final long serialVersionUID = -2763435720671860373L;
	
	private final static String BRANCH_CLASS = "tree_menu_ul";
	private final static String NODE_CLASS = "tree_menu_hov";
	private final static String HIDE_ICON_PATH = "images/jian.png";
	private final static String CHECK_BOX_CLASS = "checkbox R";
	
	private SmPermissionMapper permissionMapper = (SmPermissionMapper) SpringContextUtil.getBean("smPermissionMapper");
	
	private String id;

	@Override
	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer(5000);
		sb.append("<ul class=\"").append(BRANCH_CLASS).append("\" id=\"").append(id).append("\">\n");
		List<SmPermission> rootPermissions = permissionMapper.findAllRoot();
		for(SmPermission permission : rootPermissions) {
			buildBranch(sb, permission);
		}
		sb.append("</ul>\n");

		try {  
            this.pageContext.getOut().write(sb.toString());
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        }
		return TagSupport.SKIP_BODY;
	}
	
	@Override  
    public int doEndTag() throws JspException {  
        return TagSupport.EVAL_PAGE;  
    }
	
	/**
	 * 生成枝权限
	 * @param sb
	 * @param permission
	 */
	private void buildBranch(StringBuffer sb, SmPermission permission){
		List<SmPermission> childList = permission.getChildPermissionList();
		Boolean hasChild = childList != null && !childList.isEmpty();
		sb.append("<li>\n")
			.append("<div class=\"").append(NODE_CLASS).append("\">\n")
			.append("<input type=\"hidden\" value=\"").append(permission.getPermissionUuid()).append("\">\n");
		if(hasChild){
			sb.append("<img src=\"").append(HIDE_ICON_PATH).append("\" width=\"11\" height=\"11\">\n");
		}
		sb.append(permission.getPermissionName()).append("\n")
			.append("<div class=\"checkbox R\">")
			.append("<span></span>")
			.append("<input class=\"").append(CHECK_BOX_CLASS).append("\" type=\"checkbox\" style=\"display:none;\">\n")
			.append("</div>")
			.append("</div>\n");
		if(hasChild){
			sb.append("<ul class=\"").append(BRANCH_CLASS).append("\">\n");
			for(SmPermission childPermission : childList){
				buildBranch(sb, childPermission);
			}
			sb.append("</ul>\n");
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
