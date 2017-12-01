package info.zznet.znms.web.taglib;

import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.module.security.bean.SessionBean;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 泛用元素标签类
 */
public class PlainTag extends TagSupport {

	private static final long serialVersionUID = 4824750057532038629L;

	// 元素类别
	private String category;

	// 元素id
	private String id;

	// 元素样式
	private String _class;

	// 输入类型
	private String type;

	// 权限表达式
	private String permissionExp;
	
	// 是否有权限(显示此元素)
	private Boolean hasPermission;

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		SessionBean sessionBean = (SessionBean) request.getSession().getAttribute(SystemConstants.SESSION_BEAN_KEY);
		hasPermission = sessionBean.hasPermission(permissionExp);
		StringBuffer sb = new StringBuffer();
		if(hasPermission){
			sb.append("<").append(category);
			if(!StringUtil.isNullString(id)){
				sb.append(" id=\"").append(id).append("\"");
			}
			if(!StringUtil.isNullString(_class)){
				sb.append(" class=\"").append(_class).append("\"");
			}
			if(!StringUtil.isNullString(type)){
				sb.append(" type=\"").append(type).append("\"");
			}
			sb.append("/>");
		} else {
			return TagSupport.SKIP_BODY;
		}
		try {  
            this.pageContext.getOut().write(sb.toString());
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        }
		return TagSupport.EVAL_BODY_INCLUDE;
	}
	
	@Override  
    public int doEndTag() throws JspException {  
		StringBuffer sb = new StringBuffer();
		if(hasPermission){
			sb.append("</").append(category).append(">");
		}
		try {  
            this.pageContext.getOut().write(sb.toString());
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        }
        return TagSupport.EVAL_PAGE;  
    }
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPermissionExp() {
		return permissionExp;
	}

	public void setPermissionExp(String permissionExp) {
		this.permissionExp = permissionExp;
	}
}
