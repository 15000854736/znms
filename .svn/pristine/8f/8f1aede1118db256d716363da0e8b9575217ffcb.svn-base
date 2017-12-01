package info.zznet.znms.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 导航标签类
 * @author yuanjingtao
 *
 */
public class NavTag extends TagSupport {
	private static Logger logger = Logger.getLogger(NavTag.class);
	private static final long serialVersionUID = -7586286213014517034L;
	private String id;
	private String titles;
	private String paths;

	@Override
	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		
		String[] titleArr = titles.split(",");
		String[] pathArr = new String[titleArr.length];
		if(StringUtils.isNotEmpty(paths)){
			pathArr = paths.split(",");
		}
		sb.append("<p class=\"bn_menu\"");
		if (StringUtils.isNotEmpty(id)){
			sb.append(" id=\"").append(id).append("\" ");
		}
		sb.append("> ");
		for(int i = 0 ;i<titleArr.length;i++){
			if(i==titleArr.length-1){
				sb.append("<span class=\"bold\">"+titleArr[i]+"</span>");
			}else if(i<pathArr.length){
				sb.append("<span>"+titleArr[i]+"</span>&gt;");
//				String path = (pathArr[i]!=null&&!"".equals(pathArr[i]))?pathArr[i]:"#";
//				sb.append("<a style= \"cursor:pointer;\" href=\""+path+"\">"+titleArr[i]+"</a>&gt;");
			}else{
				sb.append("<span>"+titleArr[i]+"</span>&gt;");
//				sb.append("<a href=\"#\">"+titleArr[i]+"</a>&gt;");
			}
		}
		
		sb.append("</p>");
		try {
			pageContext.getOut().println(sb.toString());
		} catch (IOException e) {
			logger.warn("IOException", e);
		}

		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getPaths() {
		return paths;
	}

	public void setPaths(String paths) {
		this.paths = paths;
	}

}
