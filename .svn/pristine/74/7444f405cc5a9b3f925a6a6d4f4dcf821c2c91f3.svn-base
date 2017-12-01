package info.zznet.znms.web.taglib;

import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.web.module.security.bean.SessionBean;
import info.zznet.znms.web.util.MessageUtil;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * table标签类
 * @author yuanjingtao
 *
 */
public class TableTag extends TagSupport {
	
	private static Logger logger = Logger.getLogger(TableTag.class);

	private static final long serialVersionUID = -6454496159523051685L;

	/**
	 * Table的Id
	 */
	private String id;
	
	/**
	 * Unique ID(一般用PK)
	 */
	private String uniqueId;
	
	/**
	 * 实体类属性名
	 */
	private String ids;

	/**
	 * 显示在画面的列名
	 */
	private String columns;
	
	/**
	 * 列的默认显示
	 */
	private String columnShows;
	
	/**
	 * 列显示内容的最大宽度
	 */
	private String maxLengths;
	
	/**
	 * 是否对列进行裁减
	 */
	private String isSubStrings;
	
	/**
	 * 是否排序
	 */
	private String sortable;
	
	/**
	 * 数据来源
	 */
	private String dataUrl;

	/**
	 * 是否显示首列复选框
	 */
	private Boolean hasCheckBox = true;

	/**
	 * 每页显示数据数
	 */
	private int pageSize = 10;

	/**
	 * 是否显示搜索框
	 */
	private Boolean showSearch=true;
	
	/**
	 * 是否显示补偿按钮
	 */
	private Boolean showCompensatory =true;
	private String compensatoryPrivilege="";
	private String compensatoryUrl="";
	private Boolean singleEditAdminPrivilege = false;
	private Boolean showSingleEditBtn=true;
	private Boolean showSingleDeleteBtn=true;
	private Boolean showSingleDetailBtn = true;



	/**
	 * 编辑按钮对应url
	 */
	private String singleEditUrl;
	
	/**
	 * 删除按钮对应url
	 */
	private String singleDeleteUrl;
	
	/**
	 * 通过按钮对应url
	 */
	private String pass;
	
	/**
	 * 拒绝按钮对应url
	 */
	private String refuse;
	
	/**
	 * 详细按钮对应url
	 */
	private String singleDetailUrl;
	
	/**
	 * 是否显示工具栏的添加按钮
	 */
	private Boolean showAdd = true;

	/**
	 * add所需权限
	 */
	private String addPrivilege = "";
	
	/**
	 * 添加按钮对应的url
	 */
	private String addUrl = "";
	
	/**
	 * 添加按钮标题
	 */
	private String addTitle = "";
	
	/**
	 * 是否显示工具栏的删除所选按钮
	 */
	private Boolean showMultiDelete = true;

	/**
	 * delete所需权限
	 */
	private String multiDeletePrivilege = "";
	
	/**
	 * 删除所选按钮对应的url
	 */
	private String multiDeleteUrl = "";
	
	/**
	 * 是否显示导出
	 */
	private Boolean showExport = true;

	/**
	 * 导出权限
	 */
	private String exportPrivilege = "";

	/**
	 * 导出按钮对应的url
	 */
	private String exportUrl = "";
	
	/**
	 * 导出按钮标题
	 */
	private String exportTitle = "";

	/**
	 * 是否自动载入
	 */
	private Boolean autoInit = false;
	
	/**
	 * 可选操作1 title
	 */
	private String singleOptionOperateTitle_1="";

	/**
	 * 可选操作1 背景图片class
	 */
	private String singleOptionOperateImgClass_1="";

	/**
	 * 可选操作1 跳转url
	 */
	private String singleOptionOperateUrl_1="";



	/**
	 * 可选操作2 title
	 */
	private String singleOptionOperateTitle_2="";

	/**
	 * 可选操作1 跳转url
	 */
	private String singleOptionOperateUrl_2="";
	
	private String singleOptionOperateImgClass_2="";

	/**
	 * 可选操作3 title
	 */
	private String singleOptionOperateTitle_3="";

	private String singleOptionOperateImgClass_3="";
	
	/**
	 * 可选操作1 跳转url
	 */
	private String singleOptionOperateUrl_3="";

	/**
	 * 可选操作4 title
	 */
	private String singleOptionOperateTitle_4="";

	/**
	 * 可选操作4 跳转url
	 */
	private String singleOptionOperateUrl_4="";
	
	private String singleOptionOperateImgClass_4="";

	/**
	 * 可选操作5 title
	 */
	private String singleOptionOperateTitle_5="";

	/**
	 * 可选操作5 跳转url
	 */
	private String singleOptionOperateUrl_5="";


	private String singleEditPrivilege="";
	private String singleDeletePrivilege="";
	private String singleDetailPrivilege="";
	private String passPrivilege="";
	private String refusePrivilege="";


	private String singleOptionOperatePrivilege_1="";
	private String singleOptionOperatePrivilege_2="";
	private String singleOptionOperatePrivilege_3="";
	private String singleOptionOperatePrivilege_4="";
	private String singleOptionOperatePrivilege_5="";





	/**
	 * 可选按钮1 title
	 */
	private String optionBtnTitle_1="";
	/**
	 * 可选按钮1 name
	 */
	private String optionBtnName_1="";
	/**
	 * 可选按钮1 url
	 */
	private String optionBtnUrl_1="";

	/**
	 * 可选按钮1 是否支持多选
	 */
	private Boolean optionBtnAllowMulti_1=true;

	/**
	 * 当不支持多选时，选多项时的提示信息
	 */
	private String optionBtnMultiWarnMsg_1="";

	/**
	 * 可选按钮1 打开方式
	 * _blank:新页面跳转
	 * ajax:ajax请求
	 */
	private String optionBtnOpenType_1="_blank";

	/**
	 * 可选按钮1 跳转是否带参数
	 */
	private Boolean optionBtnWithParam_1=false;
	
	/**
	 *  可选按钮1  自定义js方法名称
	 */
	private String optionBtnCustomName_1="";

	/**
	 * 可选按钮2 title
	 */
	private String optionBtnTitle_2="";
	/**
	 * 可选按钮2 name
	 */
	private String optionBtnName_2="";
	/**
	 * 可选按钮2 url
	 */
	private String optionBtnUrl_2="";

	/**
	 * 可选按钮2 是否支持多选
	 */
	private Boolean optionBtnAllowMulti_2=true;

	/**
	 * 当不支持多选时，选多项时的提示信息
	 */
	private String optionBtnMultiWarnMsg_2="";

	/**
	 * 可选按钮2 打开方式
	 * _blank:新页面跳转
	 * ajax:ajax请求
	 */
	private String optionBtnOpenType_2="_blank";

	/**
	 * 可选按钮2 跳转是否带参数
	 */
	private Boolean optionBtnWithParam_2=false;
	
	/**
	 *  可选按钮2  自定义js方法名称
	 */
	private String optionBtnCustomName_2="";

	/**
	 * 可选按钮3 title
	 */
	private String optionBtnTitle_3="";
	/**
	 * 可选按钮3 name
	 */
	private String optionBtnName_3="";
	/**
	 * 可选按钮3 url
	 */
	private String optionBtnUrl_3="";

	/**
	 * 可选按钮3 是否支持多选
	 */
	private Boolean optionBtnAllowMulti_3=true;

	/**
	 * 当不支持多选时，选多项时的提示信息
	 */
	private String optionBtnMultiWarnMsg_3="";

	/**
	 * 可选按钮3 打开方式
	 * _blank:新页面跳转
	 * ajax:ajax请求
	 */
	private String optionBtnOpenType_3="_blank";

	/**
	 * 可选按钮3 跳转是否带参数
	 */
	private Boolean optionBtnWithParam_3=false;
	
	/**
	 *  可选按钮3  自定义js方法名称
	 */
	private String optionBtnCustomName_3="";

	/**
	 * 可选按钮4 title
	 */
	private String optionBtnTitle_4="";
	/**
	 * 可选按钮4 name
	 */
	private String optionBtnName_4="";
	/**
	 * 可选按钮4 url
	 */
	private String optionBtnUrl_4="";

	/**
	 * 可选按钮4 是否支持多选
	 */
	private Boolean optionBtnAllowMulti_4=true;

	/**
	 * 当不支持多选时，选多项时的提示信息
	 */
	private String optionBtnMultiWarnMsg_4="";

	/**
	 * 可选按钮4 打开方式
	 * _blank:新页面跳转
	 * ajax:ajax请求
	 */
	private String optionBtnOpenType_4="_blank";

	/**
	 * 可选按钮4 跳转是否带参数
	 */
	private Boolean optionBtnWithParam_4=false;
	
	/**
	 *  可选按钮4  自定义js方法名称
	 */
	private String optionBtnCustomName_4="";

	/**
	 * 可选按钮5 title
	 */
	private String optionBtnTitle_5="";
	/**
	 * 可选按钮5 name
	 */
	private String optionBtnName_5="";
	/**
	 * 可选按钮5 url
	 */
	private String optionBtnUrl_5="";

	/**
	 * 可选按钮5 是否支持多选
	 */
	private Boolean optionBtnAllowMulti_5=true;

	/**
	 * 当不支持多选时，选多项时的提示信息
	 */
	private String optionBtnMultiWarnMsg_5="";

	/**
	 * 可选按钮5 打开方式
	 * _blank:新页面跳转
	 * ajax:ajax请求
	 */
	private String optionBtnOpenType_5="_blank";

	/**
	 * 可选按钮5 跳转是否带参数
	 */
	private Boolean optionBtnWithParam_5=false;
	
	/**
	 *  可选按钮5  自定义js方法名称
	 */
	private String optionBtnCustomName_5="";

	/**
	 * 可选按钮6 title
	 */
	private String optionBtnTitle_6="";
	/**
	 * 可选按钮6 name
	 */
	private String optionBtnName_6="";
	/**
	 * 可选按钮6 url
	 */
	private String optionBtnUrl_6="";

	/**
	 * 可选按钮6 是否支持多选
	 */
	private Boolean optionBtnAllowMulti_6=true;

	/**
	 * 当不支持多选时，选多项时的提示信息
	 */
	private String optionBtnMultiWarnMsg_6="";

	/**
	 * 可选按钮6 打开方式
	 * _blank:新页面跳转
	 * ajax:ajax请求
	 */
	private String optionBtnOpenType_6="_blank";

	/**
	 * 可选按钮6 跳转是否带参数
	 */
	private Boolean optionBtnWithParam_6=false;
	
	/**
	 *  可选按钮6  自定义js方法名称
	 */
	private String optionBtnCustomName_6="";

	/**
	 * 可选按钮7 title
	 */
	private String optionBtnTitle_7="";
	/**
	 * 可选按钮7 name
	 */
	private String optionBtnName_7="";
	/**
	 * 可选按钮7 url
	 */
	private String optionBtnUrl_7="";

	/**
	 * 可选按钮7 是否支持多选
	 */
	private Boolean optionBtnAllowMulti_7=true;

	/**
	 * 当不支持多选时，选多项时的提示信息
	 */
	private String optionBtnMultiWarnMsg_7="";

	/**
	 * 可选按钮7 打开方式
	 * _blank:新页面跳转
	 * ajax:ajax请求
	 */
	private String optionBtnOpenType_7="_blank";

	/**
	 * 可选按钮7 跳转是否带参数
	 */
	private Boolean optionBtnWithParam_7=false;
	
	/**
	 *  可选按钮7  自定义js方法名称
	 */
	private String optionBtnCustomName_7="";

	/**
	 * 可选按钮8 title
	 */
	private String optionBtnTitle_8="";
	/**
	 * 可选按钮8 name
	 */
	private String optionBtnName_8="";
	/**
	 * 可选按钮8 url
	 */
	private String optionBtnUrl_8="";

	/**
	 * 可选按钮8 是否支持多选
	 */
	private Boolean optionBtnAllowMulti_8=true;

	/**
	 * 当不支持多选时，选多项时的提示信息
	 */
	private String optionBtnMultiWarnMsg_8="";

	/**
	 * 可选按钮8 打开方式
	 * _blank:新页面跳转
	 * ajax:ajax请求
	 */
	private String optionBtnOpenType_8="_blank";

	/**
	 * 可选按钮8 跳转是否带参数
	 */
	private Boolean optionBtnWithParam_8=false;
	
	/**
	 *  可选按钮8  自定义js方法名称
	 */
	private String optionBtnCustomName_8="";

	/**
	 * 可选按钮9 title
	 */
	private String optionBtnTitle_9="";
	/**
	 * 可选按钮9 name
	 */
	private String optionBtnName_9="";
	/**
	 * 可选按钮9 url
	 */
	private String optionBtnUrl_9="";

	/**
	 * 可选按钮9 是否支持多选
	 */
	private Boolean optionBtnAllowMulti_9=true;

	/**
	 * 当不支持多选时，选多项时的提示信息
	 */
	private String optionBtnMultiWarnMsg_9="";

	/**
	 * 可选按钮9 打开方式
	 * _blank:新页面跳转
	 * ajax:ajax请求
	 */
	private String optionBtnOpenType_9="_blank";

	/**
	 * 可选按钮9 跳转是否带参数
	 */
	private Boolean optionBtnWithParam_9=false;
	
	/**
	 *  可选按钮9 自定义js方法名称
	 */
	private String optionBtnCustomName_9="";

	/**
	 * 按钮1所需权限
	 */
	private String optionBtnPrivilege_1="";
	/**
	 * 按钮2所需权限
	 */
	private String optionBtnPrivilege_2="";
	/**
	 * 按钮3所需权限
	 */
	private String optionBtnPrivilege_3="";
	/**
	 * 按钮4所需权限
	 */
	private String optionBtnPrivilege_4="";
	/**
	 * 按钮5所需权限
	 */
	private String optionBtnPrivilege_5="";
	/**
	 * 按钮6所需权限
	 */
	private String optionBtnPrivilege_6="";
	/**
	 * 按钮7所需权限
	 */
	private String optionBtnPrivilege_7="";
	/**
	 * 按钮8所需权限
	 */
	private String optionBtnPrivilege_8="";
	/**
	 * 按钮9所需权限
	 */
	private String optionBtnPrivilege_9="";

	/**
	 * 是否只能单选
	 */
	private String singleSelect;


	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		SessionBean sessionBean = (SessionBean) request.getSession().getAttribute(SystemConstants.SESSION_BEAN_KEY);
		int size = dataUrl.split("\\/").length;
		String tableName = dataUrl.split("\\/")[size - 2];
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()); 
		/*CustomizeShowColumnsServiceImpl bean = (CustomizeShowColumnsServiceImpl) webApplicationContext.getBean("customizeShowColumnsService");
		CustomizeShowColumns csc = bean.findCustomizeShowColumnsByAdminIdAndTableName(sessionBean.getSmAdmin().getAdminId(), tableName);*/
		String visibleColumns = "";
		Object csc = null;
		/*if(csc!=null && !StringUtil.isNullString(csc.getVisibleColumns())){
			visibleColumns = csc.getVisibleColumns();
		}*/
		StringBuffer sb = new StringBuffer();
		sb.append("<table id=\"").append(id).append("\"></table>");
		
		sb.append("<div class=\"modal fade\" id=\"deleteDialog\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		sb.append("<div class=\"modal-dialog\" style=\"width:350px;\">");
		sb.append("<div class=\"modal-content\">");
		sb.append("<div class=\"modal-header\">");
		sb.append("<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\"></button>");
		sb.append("<h4 class=\"modal-title\" id=\"myModalLabel\">"+ MessageUtil.getMessage("view.hint.system")+"</h4>");
		sb.append("</div>");
		sb.append("<div class=\"modal-body\">");
		sb.append(MessageUtil.getMessage("view.warn.delete"));
		sb.append("</div>");
		sb.append("<div class=\"modal-footer\">");
		sb.append("<button type=\"button\" class=\"btn btn-primary\">"+MessageUtil.getMessage("view.button.confirm")+"</button>");
		sb.append("<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">"+MessageUtil.getMessage("view.button.back")+"</button>");
		sb.append("</div></div></div></div>\r\n");
		sb.append("<div class=\"modal fade\" id=\"compensatoryDialog\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">");
		sb.append("<div class=\"modal-dialog\" style=\"width:350px;\">");
		sb.append("<div class=\"modal-content\">");
		sb.append("<div class=\"modal-header\">");
		sb.append("<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\"></button>");
		sb.append("<h4 class=\"modal-title\" id=\"myModalLabel\">"+MessageUtil.getMessage("view.hint.system")+"</h4>");
		sb.append("</div>");
		sb.append("<div class=\"modal-body\">");
		sb.append(MessageUtil.getMessage("view.warn.compensatory"));
		sb.append("</div>");
		sb.append("<div class=\"modal-footer\">");
		sb.append("<button type=\"button\" class=\"btn btn-primary\">"+MessageUtil.getMessage("view.button.confirm")+"</button>");
		sb.append("<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">"+MessageUtil.getMessage("view.button.back")+"</button>");
		sb.append("</div></div></div></div>\r\n");
		sb.append("<script type=\"text/javascript\">\r\n")
			.append("$(document).ready(function() {")
			.append("toastr.options = {\"closeButton\": true,\"debug\": true,\"positionClass\": \"toast-top-full-width\",\"showDuration\": \"300\",\"hideDuration\": \"1000\",\"timeOut\": \"3000\",")
			.append("\"extendedTimeOut\": \"1000\",\"showEasing\": \"swing\",\"hideEasing\": \"linear\",\"showMethod\": \"fadeIn\",\"hideMethod\": \"fadeOut\"};")
			.append("$('#").append(id).append("').bootstrapTable({")
			.append("method:'post',")
			.append("url:'").append(dataUrl).append("',")
			.append("sidePagination:'server',")
			.append("contentType:'application/x-www-form-urlencoded',")
			.append("height: $(window).height() - 200,")
			.append("striped: false,")
			.append("dataType: 'json',")
			.append("pagination: true,")
			.append("showAdd: ").append(showAdd &&sessionBean.hasPermission(addPrivilege)&& StringUtils.isNotEmpty(addUrl)).append(",")
			.append("addUrl: '").append(addUrl).append("',")
			.append("addTitle: '").append((addTitle!=null&&!"".equals(addTitle))?addTitle:MessageUtil.getMessage("view.button.add")).append("',")
			.append("showMultiDelete: ").append(showMultiDelete &&sessionBean.hasPermission(multiDeletePrivilege) && StringUtils.isNotEmpty(multiDeleteUrl)).append(",")
			.append("multiDeleteUrl: '").append(multiDeleteUrl).append("',")
			.append("multiDeleteTitle: '").append(MessageUtil.getMessage("view.button.deletemany")).append("',")
			.append("multiDeleteWarn: '").append(MessageUtil.getMessage("view.warn.delete")).append("',")
			.append("showExport: ").append(showExport && sessionBean.hasPermission(exportPrivilege) && StringUtils.isNotEmpty(exportUrl)).append(",")
			.append("exportUrl: '").append(exportUrl).append("',")
			.append("exportTitle: '").append((exportTitle!=null&&!"".equals(exportTitle))?exportTitle:MessageUtil.getMessage("view.button.export")).append("',")
			.append("searchAlign: 'left',")
			.append("singleSelect:").append(StringUtils.isEmpty(singleSelect)||"false".equals(singleSelect)?"false":"true").append(",")
			.append("pageSize:").append(pageSize).append(",")
			.append("pageNumber:1,")
			.append("autoInit:").append(autoInit?"true":"false").append(",")
			.append("queryParamsType: 'limit',")
			.append("queryParams:queryParams,")
			.append("uniqueId:'").append(uniqueId).append("',");
			if (sessionBean.hasPermission(optionBtnPrivilege_1)){
				sb.append("optionBtnTitle_1:'").append(optionBtnTitle_1).append("',")
						.append("optionBtnName_1:'").append(optionBtnName_1).append("',")
						.append("optionBtnUrl_1:'").append(optionBtnUrl_1).append("',")
						.append("optionBtnAllowMulti_1:").append(optionBtnAllowMulti_1).append(",")
						.append("optionBtnMultiWarnMsg_1:'").append(optionBtnMultiWarnMsg_1).append("',")
						.append("optionBtnOpenType_1:'").append(optionBtnOpenType_1).append("',")
						.append("optionBtnWithParam_1:").append(optionBtnWithParam_1).append(",")
						.append("optionBtnCustomName_1:'").append(optionBtnCustomName_1).append("',");
			}
			if(sessionBean.hasPermission(optionBtnPrivilege_2)){
				sb.append("optionBtnTitle_2:'").append(optionBtnTitle_2).append("',")
						.append("optionBtnName_2:'").append(optionBtnName_2).append("',")
						.append("optionBtnUrl_2:'").append(optionBtnUrl_2).append("',")
						.append("optionBtnAllowMulti_2:").append(optionBtnAllowMulti_2).append(",")
						.append("optionBtnMultiWarnMsg_2:'").append(optionBtnMultiWarnMsg_2).append("',")
						.append("optionBtnOpenType_2:'").append(optionBtnOpenType_2).append("',")
						.append("optionBtnWithParam_2:").append(optionBtnWithParam_2).append(",")
						.append("optionBtnCustomName_2:'").append(optionBtnCustomName_2).append("',");
			}
			if (sessionBean.hasPermission(optionBtnPrivilege_3)){
				sb.append("optionBtnTitle_3:'").append(optionBtnTitle_3).append("',")
						.append("optionBtnName_3:'").append(optionBtnName_3).append("',")
						.append("optionBtnUrl_3:'").append(optionBtnUrl_3).append("',")
						.append("optionBtnAllowMulti_3:").append(optionBtnAllowMulti_3).append(",")
						.append("optionBtnMultiWarnMsg_3:'").append(optionBtnMultiWarnMsg_3).append("',")
						.append("optionBtnOpenType_3:'").append(optionBtnOpenType_3).append("',")
						.append("optionBtnWithParam_3:").append(optionBtnWithParam_3).append(",")
						.append("optionBtnCustomName_3:'").append(optionBtnCustomName_3).append("',");
			}
			if (sessionBean.hasPermission(optionBtnPrivilege_4)){
				sb.append("optionBtnTitle_4:'").append(optionBtnTitle_4).append("',")
						.append("optionBtnName_4:'").append(optionBtnName_4).append("',")
						.append("optionBtnUrl_4:'").append(optionBtnUrl_4).append("',")
						.append("optionBtnAllowMulti_4:").append(optionBtnAllowMulti_4).append(",")
						.append("optionBtnMultiWarnMsg_4:'").append(optionBtnMultiWarnMsg_4).append("',")
						.append("optionBtnOpenType_4:'").append(optionBtnOpenType_4).append("',")
						.append("optionBtnWithParam_4:").append(optionBtnWithParam_4).append(",")
						.append("optionBtnCustomName_4:'").append(optionBtnCustomName_4).append("',");
			}
			if (sessionBean.hasPermission(optionBtnPrivilege_5)){
				sb.append("optionBtnTitle_5:'").append(optionBtnTitle_5).append("',")
						.append("optionBtnName_5:'").append(optionBtnName_5).append("',")
						.append("optionBtnUrl_5:'").append(optionBtnUrl_5).append("',")
						.append("optionBtnAllowMulti_5:").append(optionBtnAllowMulti_5).append(",")
						.append("optionBtnMultiWarnMsg_5:'").append(optionBtnMultiWarnMsg_5).append("',")
						.append("optionBtnOpenType_5:'").append(optionBtnOpenType_5).append("',")
						.append("optionBtnWithParam_5:").append(optionBtnWithParam_5).append(",")
						.append("optionBtnCustomName_5:'").append(optionBtnCustomName_5).append("',");
			}
			if (sessionBean.hasPermission(optionBtnPrivilege_6)){
				sb.append("optionBtnTitle_6:'").append(optionBtnTitle_6).append("',")
						.append("optionBtnName_6:'").append(optionBtnName_6).append("',")
						.append("optionBtnUrl_6:'").append(optionBtnUrl_6).append("',")
						.append("optionBtnAllowMulti_6:").append(optionBtnAllowMulti_6).append(",")
						.append("optionBtnMultiWarnMsg_6:'").append(optionBtnMultiWarnMsg_6).append("',")
						.append("optionBtnOpenType_6:'").append(optionBtnOpenType_6).append("',")
						.append("optionBtnWithParam_6:").append(optionBtnWithParam_6).append(",")
						.append("optionBtnCustomName_6:'").append(optionBtnCustomName_6).append("',");
			}
			if(sessionBean.hasPermission(optionBtnPrivilege_7)){
				sb.append("optionBtnTitle_7:'").append(optionBtnTitle_7).append("',")
						.append("optionBtnName_7:'").append(optionBtnName_7).append("',")
						.append("optionBtnUrl_7:'").append(optionBtnUrl_7).append("',")
						.append("optionBtnAllowMulti_7:").append(optionBtnAllowMulti_7).append(",")
						.append("optionBtnMultiWarnMsg_7:'").append(optionBtnMultiWarnMsg_7).append("',")
						.append("optionBtnOpenType_7:'").append(optionBtnOpenType_7).append("',")
						.append("optionBtnWithParam_7:").append(optionBtnWithParam_7).append(",")
						.append("optionBtnCustomName_7:'").append(optionBtnCustomName_7).append("',");
			}
			if(sessionBean.hasPermission(optionBtnPrivilege_8)){
				sb.append("optionBtnTitle_8:'").append(optionBtnTitle_8).append("',")
						.append("optionBtnName_8:'").append(optionBtnName_8).append("',")
						.append("optionBtnUrl_8:'").append(optionBtnUrl_8).append("',")
						.append("optionBtnAllowMulti_8:").append(optionBtnAllowMulti_8).append(",")
						.append("optionBtnMultiWarnMsg_8:'").append(optionBtnMultiWarnMsg_8).append("',")
						.append("optionBtnOpenType_8:'").append(optionBtnOpenType_8).append("',")
						.append("optionBtnWithParam_8:").append(optionBtnWithParam_8).append(",")
						.append("optionBtnCustomName_8:'").append(optionBtnCustomName_8).append("',");
			}
			if (sessionBean.hasPermission(optionBtnPrivilege_9)){
				sb.append("optionBtnTitle_9:'").append(optionBtnTitle_9).append("',")
						.append("optionBtnName_9:'").append(optionBtnName_9).append("',")
						.append("optionBtnUrl_9:'").append(optionBtnUrl_9).append("',")
						.append("optionBtnAllowMulti_9:").append(optionBtnAllowMulti_9).append(",")
						.append("optionBtnMultiWarnMsg_9:'").append(optionBtnMultiWarnMsg_9).append("',")
						.append("optionBtnOpenType_9:'").append(optionBtnOpenType_9).append("',")
						.append("optionBtnWithParam_9:").append(optionBtnWithParam_9).append(",")
						.append("optionBtnCustomName_9:'").append(optionBtnCustomName_9).append("',");
			}

		if (showSearch){
			sb.append("search: ").append(showSearch).append(",")
					.append("basicQueryItems:basicQueryItems,");
		}
		sb.append("showColumns: false,")
			.append("customTitle: '").append(MessageUtil.getMessage("view.button.custom")).append("',")
			.append("columns:[");
		if (hasCheckBox) {
			sb.append("{field:'state',checkbox:true},{field : 'controlBtn',button : true,onclick : 'save',visible: false}");
		}

		String[] ids_arr = ids.split(",");
		String[] columns_arr = columns.split(",");
		String[] columnShows_arr = columnShows.split(",");
		String[] maxLength_arr = null;
		String[] isSubString_arr = null;
		String[] sortable_arr = null;
		if(!StringUtil.isNullString(maxLengths)){
			maxLength_arr = maxLengths.split(",");
		}
		if(!StringUtil.isNullString(isSubStrings)){
			isSubString_arr = isSubStrings.split(",");
		}
		if(!StringUtil.isNullString(isSubStrings)){
			sortable_arr = sortable.split(",");
		}
		for (int i = 0, j = columns.split(",").length; i < j; i++) {
			sb.append(",{field: '");
			sb.append(ids_arr[i]);
			sb.append("',title: '");
			sb.append(columns_arr[i]+"'");
			
			if(maxLength_arr!=null){
				sb.append(",maxLength: '");
				sb.append(maxLength_arr[i]+"'");
			}
			if(isSubString_arr!=null){
				sb.append(",isSubString: ");
				sb.append(!"0".equals(isSubString_arr[i]) && !"false".equalsIgnoreCase(isSubString_arr[i]));
			}
			if(sortable_arr!=null){
				sb.append(",sortable: ");
				sb.append(!"0".equals(sortable_arr[i]) && !"false".equalsIgnoreCase(sortable_arr[i]));
			}
			
			sb.append(",visible: ");
			if(!StringUtil.isNullString(visibleColumns)){
				String[] dbShowColumn_arr = visibleColumns.split(",");
				sb.append(StringUtil.isArrayContainString(columns_arr[i], dbShowColumn_arr)?true:false);
			}else{
				sb.append(!"0".equals(columnShows_arr[i]) && !"false".equalsIgnoreCase(columnShows_arr[i]));
			}
			sb.append(",align:'center',valign:'middle'}");
		}
		Boolean showOperateColumn = showSingleEditBtn || showSingleDeleteBtn || showSingleDetailBtn;
		if(showOperateColumn){
			String[] dbShowColumn_arr = visibleColumns.split(",");
			if(StringUtil.isArrayContainString("操作", dbShowColumn_arr) || csc==null){
				sb.append(",{field:'operateColumn',formatter:operateFormatter,width:100,events:operateEvents,title:'");
				sb.append(MessageUtil.getMessage("view.button.operate"));
				sb.append("',align:'center',valign:'middle','class':'tab_btn'}");
			}else{
				sb.append(",{field:'operateColumn',formatter:operateFormatter,events:operateEvents,title:'");
				sb.append(MessageUtil.getMessage("view.button.operate"));
				sb.append("',align:'center',valign:'middle','class':'tab_btn','visible':false}");
			}
		}
		sb.append("]});");

		sb.append("});");
		
		sb.append("function save(){var visibleColumns='';")
		.append("$('.xlcd_mod label').each(function(i){")
		.append("$(this).children(\"input:checkbox:checked\").each(function () {")
		.append("visibleColumns += $(this).parent().text()+',';})")
		.append("});")
		.append("if(visibleColumns!=''){")
		.append("visibleColumns = visibleColumns.substr(0, visibleColumns.length-1);")
		.append("$.ajax({")
		.append("type: \"POST\",   ")
		.append("url: \""+request.getContextPath()+"\" + \"/customizeShowColumns/add/do\", ")
		.append("data: \"tableName=").append(tableName).append("&visibleColumns=\" + visibleColumns, ")
		.append("success: function (data) {")
		.append("toastr.clear();")
		.append("toastr.success(\""+MessageUtil.getMessage("view.success.save")+"\");$(\".xlcd_mod\")[0].style.display = 'none'; ")
		
		.append("},")
		.append("error: function(XMLHttpRequest, textStatus, errorThrown){")
		.append("toastr.clear();")
		.append("toastr.error(\""+MessageUtil.getMessage("view.error.save")+"\");$(\".xlcd_mod\")[0].style.display = 'none'; ")
		.append("}")
		.append("});")
		.append("}")
		.append("}");
		
		if(showOperateColumn){
			int showOperateColumnText = 0;
			if(showSingleEditBtn&&sessionBean.hasPermission(singleEditPrivilege)&&(!singleEditAdminPrivilege||(singleEditAdminPrivilege&&sessionBean.isAdministrator()))){
				showOperateColumnText+=MessageUtil.getMessage("view.button.edit").length()+1;
			}
			if(showSingleDeleteBtn&&sessionBean.hasPermission(singleDeletePrivilege)){
				showOperateColumnText+=MessageUtil.getMessage("view.button.deleteone").length()+1;
			}
			if(showSingleDetailBtn&&sessionBean.hasPermission(singleDetailPrivilege)){
				showOperateColumnText+=MessageUtil.getMessage("view.button.detail").length()+1;
			}
			if (sessionBean.hasPermission(singleOptionOperatePrivilege_1)&&StringUtils.isNotEmpty(singleOptionOperateTitle_1)&&StringUtils.isNotEmpty(singleOptionOperateUrl_1)){
				showOperateColumnText+=MessageUtil.getMessage(singleOptionOperateTitle_1).length()+1;
			}
			if (sessionBean.hasPermission(singleOptionOperatePrivilege_2)&&StringUtils.isNotEmpty(singleOptionOperateTitle_2)&&StringUtils.isNotEmpty(singleOptionOperateUrl_2)){
				showOperateColumnText+=MessageUtil.getMessage(singleOptionOperateTitle_2).length()+1;
			}
			if (sessionBean.hasPermission(singleOptionOperatePrivilege_3)&&StringUtils.isNotEmpty(singleOptionOperateTitle_3)&&StringUtils.isNotEmpty(singleOptionOperateUrl_3)){
				showOperateColumnText+=MessageUtil.getMessage(singleOptionOperateTitle_3).length()+1;
			}
			if (sessionBean.hasPermission(singleOptionOperatePrivilege_4)&&StringUtils.isNotEmpty(singleOptionOperateTitle_4)&&StringUtils.isNotEmpty(singleOptionOperateUrl_4)){
				showOperateColumnText+=MessageUtil.getMessage(singleOptionOperateTitle_4).length()+1;
			}
			if(showOperateColumnText<=0){
				sb.append("function operateFormatter(value,row,index){")
				.append("return [   '<ul class=\"table_icon\">',");
			}else{
				sb.append("function operateFormatter(value,row,index){")
				.append("return [   '<ul class=\"table_icon\" style=\"width:"+showOperateColumnText*20+"px\">',");
			}
			
			if(showSingleEditBtn&&sessionBean.hasPermission(singleEditPrivilege)&&(!singleEditAdminPrivilege||(singleEditAdminPrivilege&&sessionBean.isAdministrator()))){
				sb.append("'<li class=\"table_icon_a\"><a href=\"#\"><i></i>"+MessageUtil.getMessage("view.button.edit")+"',")
					.append("'</a></li>',");
			}
			if(showSingleDeleteBtn&&sessionBean.hasPermission(singleDeletePrivilege)){
				sb.append("'<li class=\"table_icon_b\"><a href=\"#\"><i></i>"+MessageUtil.getMessage("view.button.deleteone")+"',")
				.append("'</a></li>',");
			}
			
			if(showSingleDetailBtn&&sessionBean.hasPermission(singleDetailPrivilege)){
				sb.append("'<li class=\"table_icon_c\"><a href=\"#\"><i></i>"+MessageUtil.getMessage("view.button.detail")+"',")
				.append("'</a></li>',");
			}
			

			if (sessionBean.hasPermission(singleOptionOperatePrivilege_1)&&StringUtils.isNotEmpty(singleOptionOperateTitle_1)&&StringUtils.isNotEmpty(singleOptionOperateUrl_1)){
				sb.append("'<li class=\""+singleOptionOperateImgClass_1+"\"><a href=\"#\"><i></i>"+MessageUtil.getMessage(singleOptionOperateTitle_1)+"',")
				.append("'</a></li>',");
			}
			if (sessionBean.hasPermission(passPrivilege)&&StringUtils.isNotEmpty(pass)){
				sb.append("'<a class=\"by_tab_btn\" href=\"javascript:void(0)\" title=\"")
				.append(MessageUtil.getMessage("view.button.pass"))
				.append("\">',")
				.append("'</a>',");
			}
			if (sessionBean.hasPermission(refusePrivilege)&&StringUtils.isNotEmpty(refuse)){
				sb.append("'<a class=\"refuse_tab_btn\" href=\"javascript:void(0)\" title=\"")
				.append(MessageUtil.getMessage("view.button.refuse"))
				.append("\">',")
				.append("'</a>',");
			}

			if (sessionBean.hasPermission(singleOptionOperatePrivilege_2)&&StringUtils.isNotEmpty(singleOptionOperateTitle_2)&&StringUtils.isNotEmpty(singleOptionOperateUrl_2)){
				sb.append("'<li class=\"")
					.append(singleOptionOperateImgClass_2)
					.append(" \"><a href=\"#\" title=\"")
					.append(MessageUtil.getMessage(singleOptionOperateTitle_2))
					.append("\"><i></i>")
					.append(MessageUtil.getMessage(singleOptionOperateTitle_2))
					.append("',")
					.append("'</a></li>',");
			}

			if (sessionBean.hasPermission(singleOptionOperatePrivilege_3)&&StringUtils.isNotEmpty(singleOptionOperateTitle_3)&&StringUtils.isNotEmpty(singleOptionOperateUrl_3)){
				sb.append("'<li class=\"")
					.append(singleOptionOperateImgClass_3)
					.append(" \"><a href=\"#\" title=\"")
					.append(MessageUtil.getMessage(singleOptionOperateTitle_3))
					.append("\"><i></i>")
					.append(MessageUtil.getMessage(singleOptionOperateTitle_3))
					.append("',")
					.append("'</a></li>',");
			}

			if (sessionBean.hasPermission(singleOptionOperatePrivilege_4)&&StringUtils.isNotEmpty(singleOptionOperateTitle_4)&&StringUtils.isNotEmpty(singleOptionOperateUrl_4)){
				sb.append("'<li class=\"")
					.append(singleOptionOperateImgClass_4)
					.append(" \"><a href=\"#\" title=\"")
					.append(MessageUtil.getMessage(singleOptionOperateTitle_4))
					.append("\"><i></i>")
					.append(MessageUtil.getMessage(singleOptionOperateTitle_4))
					.append("',")
					.append("'</a></li>',");
			}

			if (sessionBean.hasPermission(singleOptionOperatePrivilege_5)&&StringUtils.isNotEmpty(singleOptionOperateTitle_5)&&StringUtils.isNotEmpty(singleOptionOperateUrl_5)){
				sb.append("'<a class=\"soo_tab_btn_5\" href=\"javascript:void(0)\" title=\"")
						.append(MessageUtil.getMessage(singleOptionOperateTitle_5))
						.append("\">',");
				sb.append("'</a>',");
			}
			sb.append("'</ul>',");

			sb.deleteCharAt(sb.length() - 1);
			sb.append("].join('');}");
			appendAjaxPart(sb,sessionBean);
		}
		sb.append("</script>");

		try {
			pageContext.getOut().println(sb.toString());
		} catch (IOException e) {
			logger.warn("IOException", e);
		}

		return SKIP_BODY;
	}

	private void appendAjaxPart(StringBuffer sb,SessionBean sessionBean){
		sb.append("window.operateEvents = {");
		if(showSingleEditBtn&&sessionBean.hasPermission(singleEditPrivilege)){
			sb.append("'click .table_icon_a': function (e, value, row, index) {")
				.append(singleEditUrl).append("(row['").append(uniqueId).append("']);")
				.append("},");
		}
		if(showSingleDeleteBtn&&sessionBean.hasPermission(singleDeletePrivilege)){
			sb.append("'click .table_icon_b': function (e, value, row, index) {")
				.append("$('#").append(id).append("').bootstrapTable({search:'sdsd'});")
				.append("$('#deleteDialog').modal({")
				.append("	  keyboard: true")
				.append("});")
				.append("$('#deleteDialog').modal('show');")
				.append("$('.btn-primary').off('click').on('click', function () { ")
				.append("toastr.clear();")
				.append("$.ajax({")
				.append("	url : '"+getSingleDeleteUrl()+"',")
				.append("	data : {")
				.append("		\"jsonData\" : JSON.stringify(row)")
				.append("		},")
				.append("		type : \"POST\",")
				.append("		contentType : \"application/x-www-form-urlencoded\",")
				.append("		dataType : \"json\",")
				.append("		success : function(result) { ")
				.append("			$('#deleteDialog').modal('hide');")
				.append("				toastr.clear();")
				.append("				if(result.result){")
				.append("			toastr.success(\""+MessageUtil.getMessage("view.success.delete")+"\");")
				.append("				}else{")
				.append("                    if(result.msg!='' && result.msg!=null){toastr.error(result.msg);}else{ toastr.error(\""+MessageUtil.getMessage("view.error.delete")+"\");}")
				.append("}")
				.append("			var params = {")
				.append("				silent : true,")
				.append("				refresh : '/zzos-console/accessControl/search'")
				.append("			};")
				.append("			$('#ZZOS_Common_Table').bootstrapTable('refresh', params);")
				.append("		},")
				.append("error: function(XMLHttpRequest, textStatus, errorThrown){")
				.append("	toastr.clear();")
				.append("	toastr.error(\""+MessageUtil.getMessage("view.error.delete")+"\");  ")
				.append("   }")
				.append("});")
				.append("});")
				.append("},");
		}
		
		if(showSingleDetailBtn&&sessionBean.hasPermission(singleDetailPrivilege)){
			sb.append("'click .table_icon_c': function (e, value, row, index) {")
				.append(singleDetailUrl).append("(row['").append(uniqueId).append("']);")
				.append("},");
		}
		
		if (sessionBean.hasPermission(singleOptionOperatePrivilege_1)&&StringUtils.isNotEmpty(singleOptionOperateTitle_1)&&StringUtils.isNotEmpty(singleOptionOperateUrl_1)){
			sb.append("'click ."+singleOptionOperateImgClass_1+"': function (e, value, row, index) {")
			.append(singleOptionOperateUrl_1).append("(row['").append(uniqueId).append("']);")
			.append("},");
		}
		
		if (sessionBean.hasPermission(passPrivilege)&&StringUtils.isNotEmpty(pass)){
			sb.append("'click .by_tab_btn': function (e, value, row, index) {")
			.append("$('#").append(id).append("').bootstrapTable({search:'sdsd'});")
			.append("if(confirm('").append(MessageUtil.getMessage("view.warn.pass")).append("')){")
			.append("$.ajax({")
			.append("url: '").append(pass).append("',")
			.append("data: {\"jsonData\":JSON.stringify(row)},")
			.append("type:\"POST\",")
			.append("contentType: \"application/x-www-form-urlencoded\",")
			.append("dataType: \"json\",")
			.append("success:function(data){")
			.append("var params = {silent:true,refresh:'")
			.append(dataUrl)
			.append("'};")
			.append("$('#").append(id).append("').bootstrapTable('refresh',params);")
			.append("}")
			.append("});")
			.append("}")
			.append("},");
		}
		
		if (sessionBean.hasPermission(refusePrivilege)&&StringUtils.isNotEmpty(refuse)){
			sb.append("'click .refuse_tab_btn': function (e, value, row, index) {")
			.append("$('#").append(id).append("').bootstrapTable({search:'sdsd'});")
			.append("if(confirm('").append(MessageUtil.getMessage("view.warn.refuse")).append("')){")
			.append("$.ajax({")
			.append("url: '").append(refuse).append("',")
			.append("data: {\"jsonData\":JSON.stringify(row)},")
			.append("type:\"POST\",")
			.append("contentType: \"application/x-www-form-urlencoded\",")
			.append("dataType: \"json\",")
			.append("success:function(data){")
			.append("var params = {silent:true,refresh:'")
			.append(dataUrl)
			.append("'};")
			.append("$('#").append(id).append("').bootstrapTable('refresh',params);")
			.append("}")
			.append("});")
			.append("}")
			.append("},");
		}

		if (sessionBean.hasPermission(singleOptionOperatePrivilege_2)&&StringUtils.isNotEmpty(singleOptionOperateTitle_2)&&StringUtils.isNotEmpty(singleOptionOperateUrl_2)){
			sb.append("'click ."+singleOptionOperateImgClass_2+"': function (e, value, row, index) {")
					.append(singleOptionOperateUrl_2).append("(row['").append(uniqueId).append("']);")
					.append("},");
		}

		if (sessionBean.hasPermission(singleOptionOperatePrivilege_3)&&StringUtils.isNotEmpty(singleOptionOperateTitle_3)&&StringUtils.isNotEmpty(singleOptionOperateUrl_3)){
			sb.append("'click ."+singleOptionOperateImgClass_3+"': function (e, value, row, index) {")
					.append(singleOptionOperateUrl_3).append("(row['").append(uniqueId).append("']);")
					.append("},");
		}

		if (sessionBean.hasPermission(singleOptionOperatePrivilege_4)&&StringUtils.isNotEmpty(singleOptionOperateTitle_4)&&StringUtils.isNotEmpty(singleOptionOperateUrl_4)){
			sb.append("'click ."+singleOptionOperateImgClass_4+"': function (e, value, row, index) {")
					.append(singleOptionOperateUrl_4).append("(row['").append(uniqueId).append("']);")
					.append("},");
		}

		if (sessionBean.hasPermission(singleOptionOperatePrivilege_5)&&StringUtils.isNotEmpty(singleOptionOperateTitle_5)&&StringUtils.isNotEmpty(singleOptionOperateUrl_5)){
			sb.append("'click .soo_tab_btn_5': function (e, value, row, index) {")
					.append("var targetUrl = '").append(singleOptionOperateUrl_5).append("/'+row['").append(uniqueId).append("'];")
					.append("location.href = targetUrl;")
					.append("},");
		}

		if (sb.charAt(sb.length()-1) == ','){
			sb.deleteCharAt(sb.length() - 1);
		}

		sb.append("};");
		sb.append("</script>");
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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}
	
	public String getColumnShows() {
		return columnShows;
	}

	public void setColumnShows(String columnShows) {
		this.columnShows = columnShows;
	}

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	
	public Boolean getHasCheckBox() {
		return hasCheckBox;
	}

	public void setHasCheckBox(Boolean hasCheckBox) {
		this.hasCheckBox = hasCheckBox;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public Boolean getShowAdd() {
		return showAdd;
	}

	public void setShowAdd(Boolean showAdd) {
		this.showAdd = showAdd;
	}

	public Boolean getShowMultiDelete() {
		return showMultiDelete;
	}

	public void setShowMultiDelete(Boolean showMultiDelete) {
		this.showMultiDelete = showMultiDelete;
	}

	public String getAddUrl() {
		return addUrl;
	}

	public void setAddUrl(String addUrl) {
		this.addUrl = addUrl;
	}

	public String getMultiDeleteUrl() {
		return multiDeleteUrl;
	}

	public void setMultiDeleteUrl(String multiDeleteUrl) {
		this.multiDeleteUrl = multiDeleteUrl;
	}
	
	public Boolean getShowExport() {
		return showExport;
	}

	public void setShowExport(Boolean showExport) {
		this.showExport = showExport;
	}

	public String getExportUrl() {
		return exportUrl;
	}

	public void setExportUrl(String exportUrl) {
		this.exportUrl = exportUrl;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Boolean getShowSearch() {
		return showSearch;
	}

	public void setShowCompensatory(Boolean showCompensatory) {
		this.showCompensatory = showCompensatory;
	}
	
	public Boolean getShowCompensatory() {
		return showCompensatory;
	}
	
	public String getCompensatoryPrivilege() {
		return compensatoryPrivilege;
	}

	public void setCompensatoryPrivilege(String compensatoryPrivilege) {
		this.compensatoryPrivilege = compensatoryPrivilege;
	}

	public String getCompensatoryUrl() {
		return compensatoryUrl;
	}

	public void setCompensatoryUrl(String compensatoryUrl) {
		this.compensatoryUrl = compensatoryUrl;
	}

	public void setShowSearch(Boolean showSearch) {
		this.showSearch = showSearch;
	}

	public String getSingleOptionOperateTitle_1() {
		return singleOptionOperateTitle_1;
	}

	public void setSingleOptionOperateTitle_1(String singleOptionOperateTitle_1) {
		this.singleOptionOperateTitle_1 = singleOptionOperateTitle_1;
	}

	public String getSingleOptionOperateUrl_1() {
		return singleOptionOperateUrl_1;
	}

	public void setSingleOptionOperateUrl_1(String singleOptionOperateUrl_1) {
		this.singleOptionOperateUrl_1 = singleOptionOperateUrl_1;
	}

	public String getSingleOptionOperateTitle_2() {
		return singleOptionOperateTitle_2;
	}

	public void setSingleOptionOperateTitle_2(String singleOptionOperateTitle_2) {
		this.singleOptionOperateTitle_2 = singleOptionOperateTitle_2;
	}

	public String getSingleOptionOperateUrl_2() {
		return singleOptionOperateUrl_2;
	}

	public void setSingleOptionOperateUrl_2(String singleOptionOperateUrl_2) {
		this.singleOptionOperateUrl_2 = singleOptionOperateUrl_2;
	}

	public String getSingleOptionOperateTitle_3() {
		return singleOptionOperateTitle_3;
	}

	public void setSingleOptionOperateTitle_3(String singleOptionOperateTitle_3) {
		this.singleOptionOperateTitle_3 = singleOptionOperateTitle_3;
	}

	public String getSingleOptionOperateUrl_3() {
		return singleOptionOperateUrl_3;
	}

	public void setSingleOptionOperateUrl_3(String singleOptionOperateUrl_3) {
		this.singleOptionOperateUrl_3 = singleOptionOperateUrl_3;
	}

	public String getSingleOptionOperateTitle_4() {
		return singleOptionOperateTitle_4;
	}

	public void setSingleOptionOperateTitle_4(String singleOptionOperateTitle_4) {
		this.singleOptionOperateTitle_4 = singleOptionOperateTitle_4;
	}

	public String getSingleOptionOperateUrl_4() {
		return singleOptionOperateUrl_4;
	}

	public void setSingleOptionOperateUrl_4(String singleOptionOperateUrl_4) {
		this.singleOptionOperateUrl_4 = singleOptionOperateUrl_4;
	}

	public String getSingleOptionOperateTitle_5() {
		return singleOptionOperateTitle_5;
	}

	public void setSingleOptionOperateTitle_5(String singleOptionOperateTitle_5) {
		this.singleOptionOperateTitle_5 = singleOptionOperateTitle_5;
	}

	public String getSingleOptionOperateUrl_5() {
		return singleOptionOperateUrl_5;
	}

	public void setSingleOptionOperateUrl_5(String singleOptionOperateUrl_5) {
		this.singleOptionOperateUrl_5 = singleOptionOperateUrl_5;
	}

	public String getSingleOptionOperateImgClass_1() {
		return singleOptionOperateImgClass_1;
	}

	public void setSingleOptionOperateImgClass_1(String singleOptionOperateImgClass_1) {
		this.singleOptionOperateImgClass_1 = singleOptionOperateImgClass_1;
	}
	
	public String getSingleOptionOperateImgClass_2() {
		return singleOptionOperateImgClass_2;
	}

	public void setSingleOptionOperateImgClass_2(
			String singleOptionOperateImgClass_2) {
		this.singleOptionOperateImgClass_2 = singleOptionOperateImgClass_2;
	}

	public String getOptionBtnTitle_1() {
		return optionBtnTitle_1;
	}

	public void setOptionBtnTitle_1(String optionBtnTitle_1) {
		this.optionBtnTitle_1 = optionBtnTitle_1;
	}

	public String getOptionBtnName_1() {
		return optionBtnName_1;
	}

	public void setOptionBtnName_1(String optionBtnName_1) {
		this.optionBtnName_1 = optionBtnName_1;
	}

	public String getOptionBtnUrl_1() {
		return optionBtnUrl_1;
	}

	public void setOptionBtnUrl_1(String optionBtnUrl_1) {
		this.optionBtnUrl_1 = optionBtnUrl_1;
	}

	public Boolean getOptionBtnAllowMulti_1() {
		return optionBtnAllowMulti_1;
	}

	public void setOptionBtnAllowMulti_1(Boolean optionBtnAllowMulti_1) {
		this.optionBtnAllowMulti_1 = optionBtnAllowMulti_1;
	}

	public String getOptionBtnMultiWarnMsg_1() {
		return optionBtnMultiWarnMsg_1;
	}

	public void setOptionBtnMultiWarnMsg_1(String optionBtnMultiWarnMsg_1) {
		this.optionBtnMultiWarnMsg_1 = optionBtnMultiWarnMsg_1;
	}

	public String getOptionBtnOpenType_1() {
		return optionBtnOpenType_1;
	}

	public void setOptionBtnOpenType_1(String optionBtnOpenType_1) {
		this.optionBtnOpenType_1 = optionBtnOpenType_1;
	}

	public String getOptionBtnTitle_2() {
		return optionBtnTitle_2;
	}

	public void setOptionBtnTitle_2(String optionBtnTitle_2) {
		this.optionBtnTitle_2 = optionBtnTitle_2;
	}

	public String getOptionBtnName_2() {
		return optionBtnName_2;
	}

	public void setOptionBtnName_2(String optionBtnName_2) {
		this.optionBtnName_2 = optionBtnName_2;
	}

	public String getOptionBtnUrl_2() {
		return optionBtnUrl_2;
	}

	public void setOptionBtnUrl_2(String optionBtnUrl_2) {
		this.optionBtnUrl_2 = optionBtnUrl_2;
	}

	public Boolean getOptionBtnAllowMulti_2() {
		return optionBtnAllowMulti_2;
	}

	public void setOptionBtnAllowMulti_2(Boolean optionBtnAllowMulti_2) {
		this.optionBtnAllowMulti_2 = optionBtnAllowMulti_2;
	}

	public String getOptionBtnMultiWarnMsg_2() {
		return optionBtnMultiWarnMsg_2;
	}

	public void setOptionBtnMultiWarnMsg_2(String optionBtnMultiWarnMsg_2) {
		this.optionBtnMultiWarnMsg_2 = optionBtnMultiWarnMsg_2;
	}

	public String getOptionBtnOpenType_2() {
		return optionBtnOpenType_2;
	}

	public void setOptionBtnOpenType_2(String optionBtnOpenType_2) {
		this.optionBtnOpenType_2 = optionBtnOpenType_2;
	}

	public String getOptionBtnTitle_3() {
		return optionBtnTitle_3;
	}

	public void setOptionBtnTitle_3(String optionBtnTitle_3) {
		this.optionBtnTitle_3 = optionBtnTitle_3;
	}

	public String getOptionBtnName_3() {
		return optionBtnName_3;
	}

	public void setOptionBtnName_3(String optionBtnName_3) {
		this.optionBtnName_3 = optionBtnName_3;
	}

	public String getOptionBtnUrl_3() {
		return optionBtnUrl_3;
	}

	public void setOptionBtnUrl_3(String optionBtnUrl_3) {
		this.optionBtnUrl_3 = optionBtnUrl_3;
	}

	public Boolean getOptionBtnAllowMulti_3() {
		return optionBtnAllowMulti_3;
	}

	public void setOptionBtnAllowMulti_3(Boolean optionBtnAllowMulti_3) {
		this.optionBtnAllowMulti_3 = optionBtnAllowMulti_3;
	}

	public String getOptionBtnMultiWarnMsg_3() {
		return optionBtnMultiWarnMsg_3;
	}

	public void setOptionBtnMultiWarnMsg_3(String optionBtnMultiWarnMsg_3) {
		this.optionBtnMultiWarnMsg_3 = optionBtnMultiWarnMsg_3;
	}

	public String getOptionBtnOpenType_3() {
		return optionBtnOpenType_3;
	}

	public void setOptionBtnOpenType_3(String optionBtnOpenType_3) {
		this.optionBtnOpenType_3 = optionBtnOpenType_3;
	}

	public String getOptionBtnTitle_4() {
		return optionBtnTitle_4;
	}

	public void setOptionBtnTitle_4(String optionBtnTitle_4) {
		this.optionBtnTitle_4 = optionBtnTitle_4;
	}

	public String getOptionBtnName_4() {
		return optionBtnName_4;
	}

	public void setOptionBtnName_4(String optionBtnName_4) {
		this.optionBtnName_4 = optionBtnName_4;
	}

	public String getOptionBtnUrl_4() {
		return optionBtnUrl_4;
	}

	public void setOptionBtnUrl_4(String optionBtnUrl_4) {
		this.optionBtnUrl_4 = optionBtnUrl_4;
	}

	public Boolean getOptionBtnAllowMulti_4() {
		return optionBtnAllowMulti_4;
	}

	public void setOptionBtnAllowMulti_4(Boolean optionBtnAllowMulti_4) {
		this.optionBtnAllowMulti_4 = optionBtnAllowMulti_4;
	}

	public String getOptionBtnMultiWarnMsg_4() {
		return optionBtnMultiWarnMsg_4;
	}

	public void setOptionBtnMultiWarnMsg_4(String optionBtnMultiWarnMsg_4) {
		this.optionBtnMultiWarnMsg_4 = optionBtnMultiWarnMsg_4;
	}

	public String getOptionBtnOpenType_4() {
		return optionBtnOpenType_4;
	}

	public void setOptionBtnOpenType_4(String optionBtnOpenType_4) {
		this.optionBtnOpenType_4 = optionBtnOpenType_4;
	}

	public String getOptionBtnTitle_5() {
		return optionBtnTitle_5;
	}

	public void setOptionBtnTitle_5(String optionBtnTitle_5) {
		this.optionBtnTitle_5 = optionBtnTitle_5;
	}

	public String getOptionBtnName_5() {
		return optionBtnName_5;
	}

	public void setOptionBtnName_5(String optionBtnName_5) {
		this.optionBtnName_5 = optionBtnName_5;
	}

	public String getOptionBtnUrl_5() {
		return optionBtnUrl_5;
	}

	public void setOptionBtnUrl_5(String optionBtnUrl_5) {
		this.optionBtnUrl_5 = optionBtnUrl_5;
	}

	public Boolean getOptionBtnAllowMulti_5() {
		return optionBtnAllowMulti_5;
	}

	public void setOptionBtnAllowMulti_5(Boolean optionBtnAllowMulti_5) {
		this.optionBtnAllowMulti_5 = optionBtnAllowMulti_5;
	}

	public String getOptionBtnMultiWarnMsg_5() {
		return optionBtnMultiWarnMsg_5;
	}

	public void setOptionBtnMultiWarnMsg_5(String optionBtnMultiWarnMsg_5) {
		this.optionBtnMultiWarnMsg_5 = optionBtnMultiWarnMsg_5;
	}

	public String getOptionBtnOpenType_5() {
		return optionBtnOpenType_5;
	}

	public void setOptionBtnOpenType_5(String optionBtnOpenType_5) {
		this.optionBtnOpenType_5 = optionBtnOpenType_5;
	}

	public String getOptionBtnTitle_6() {
		return optionBtnTitle_6;
	}

	public void setOptionBtnTitle_6(String optionBtnTitle_6) {
		this.optionBtnTitle_6 = optionBtnTitle_6;
	}

	public String getOptionBtnName_6() {
		return optionBtnName_6;
	}

	public void setOptionBtnName_6(String optionBtnName_6) {
		this.optionBtnName_6 = optionBtnName_6;
	}

	public String getOptionBtnUrl_6() {
		return optionBtnUrl_6;
	}

	public void setOptionBtnUrl_6(String optionBtnUrl_6) {
		this.optionBtnUrl_6 = optionBtnUrl_6;
	}

	public Boolean getOptionBtnAllowMulti_6() {
		return optionBtnAllowMulti_6;
	}

	public void setOptionBtnAllowMulti_6(Boolean optionBtnAllowMulti_6) {
		this.optionBtnAllowMulti_6 = optionBtnAllowMulti_6;
	}

	public String getOptionBtnMultiWarnMsg_6() {
		return optionBtnMultiWarnMsg_6;
	}

	public void setOptionBtnMultiWarnMsg_6(String optionBtnMultiWarnMsg_6) {
		this.optionBtnMultiWarnMsg_6 = optionBtnMultiWarnMsg_6;
	}

	public String getOptionBtnOpenType_6() {
		return optionBtnOpenType_6;
	}

	public void setOptionBtnOpenType_6(String optionBtnOpenType_6) {
		this.optionBtnOpenType_6 = optionBtnOpenType_6;
	}

	public String getOptionBtnTitle_7() {
		return optionBtnTitle_7;
	}

	public void setOptionBtnTitle_7(String optionBtnTitle_7) {
		this.optionBtnTitle_7 = optionBtnTitle_7;
	}

	public String getOptionBtnName_7() {
		return optionBtnName_7;
	}

	public void setOptionBtnName_7(String optionBtnName_7) {
		this.optionBtnName_7 = optionBtnName_7;
	}

	public String getOptionBtnUrl_7() {
		return optionBtnUrl_7;
	}

	public void setOptionBtnUrl_7(String optionBtnUrl_7) {
		this.optionBtnUrl_7 = optionBtnUrl_7;
	}

	public Boolean getOptionBtnAllowMulti_7() {
		return optionBtnAllowMulti_7;
	}

	public void setOptionBtnAllowMulti_7(Boolean optionBtnAllowMulti_7) {
		this.optionBtnAllowMulti_7 = optionBtnAllowMulti_7;
	}

	public String getOptionBtnMultiWarnMsg_7() {
		return optionBtnMultiWarnMsg_7;
	}

	public void setOptionBtnMultiWarnMsg_7(String optionBtnMultiWarnMsg_7) {
		this.optionBtnMultiWarnMsg_7 = optionBtnMultiWarnMsg_7;
	}

	public String getOptionBtnOpenType_7() {
		return optionBtnOpenType_7;
	}

	public void setOptionBtnOpenType_7(String optionBtnOpenType_7) {
		this.optionBtnOpenType_7 = optionBtnOpenType_7;
	}

	public String getOptionBtnTitle_8() {
		return optionBtnTitle_8;
	}

	public void setOptionBtnTitle_8(String optionBtnTitle_8) {
		this.optionBtnTitle_8 = optionBtnTitle_8;
	}

	public String getOptionBtnName_8() {
		return optionBtnName_8;
	}

	public void setOptionBtnName_8(String optionBtnName_8) {
		this.optionBtnName_8 = optionBtnName_8;
	}

	public String getOptionBtnUrl_8() {
		return optionBtnUrl_8;
	}

	public void setOptionBtnUrl_8(String optionBtnUrl_8) {
		this.optionBtnUrl_8 = optionBtnUrl_8;
	}

	public Boolean getOptionBtnAllowMulti_8() {
		return optionBtnAllowMulti_8;
	}

	public void setOptionBtnAllowMulti_8(Boolean optionBtnAllowMulti_8) {
		this.optionBtnAllowMulti_8 = optionBtnAllowMulti_8;
	}

	public String getOptionBtnMultiWarnMsg_8() {
		return optionBtnMultiWarnMsg_8;
	}

	public void setOptionBtnMultiWarnMsg_8(String optionBtnMultiWarnMsg_8) {
		this.optionBtnMultiWarnMsg_8 = optionBtnMultiWarnMsg_8;
	}

	public String getOptionBtnOpenType_8() {
		return optionBtnOpenType_8;
	}

	public void setOptionBtnOpenType_8(String optionBtnOpenType_8) {
		this.optionBtnOpenType_8 = optionBtnOpenType_8;
	}

	public String getOptionBtnTitle_9() {
		return optionBtnTitle_9;
	}

	public void setOptionBtnTitle_9(String optionBtnTitle_9) {
		this.optionBtnTitle_9 = optionBtnTitle_9;
	}

	public String getOptionBtnName_9() {
		return optionBtnName_9;
	}

	public void setOptionBtnName_9(String optionBtnName_9) {
		this.optionBtnName_9 = optionBtnName_9;
	}

	public String getOptionBtnUrl_9() {
		return optionBtnUrl_9;
	}

	public void setOptionBtnUrl_9(String optionBtnUrl_9) {
		this.optionBtnUrl_9 = optionBtnUrl_9;
	}

	public Boolean getOptionBtnAllowMulti_9() {
		return optionBtnAllowMulti_9;
	}

	public void setOptionBtnAllowMulti_9(Boolean optionBtnAllowMulti_9) {
		this.optionBtnAllowMulti_9 = optionBtnAllowMulti_9;
	}

	public String getOptionBtnMultiWarnMsg_9() {
		return optionBtnMultiWarnMsg_9;
	}

	public void setOptionBtnMultiWarnMsg_9(String optionBtnMultiWarnMsg_9) {
		this.optionBtnMultiWarnMsg_9 = optionBtnMultiWarnMsg_9;
	}

	public String getOptionBtnOpenType_9() {
		return optionBtnOpenType_9;
	}

	public void setOptionBtnOpenType_9(String optionBtnOpenType_9) {
		this.optionBtnOpenType_9 = optionBtnOpenType_9;
	}

	public Boolean getOptionBtnWithParam_1() {
		return optionBtnWithParam_1;
	}

	public void setOptionBtnWithParam_1(Boolean optionBtnWithParam_1) {
		this.optionBtnWithParam_1 = optionBtnWithParam_1;
	}

	public Boolean getOptionBtnWithParam_2() {
		return optionBtnWithParam_2;
	}

	public void setOptionBtnWithParam_2(Boolean optionBtnWithParam_2) {
		this.optionBtnWithParam_2 = optionBtnWithParam_2;
	}

	public Boolean getOptionBtnWithParam_3() {
		return optionBtnWithParam_3;
	}

	public void setOptionBtnWithParam_3(Boolean optionBtnWithParam_3) {
		this.optionBtnWithParam_3 = optionBtnWithParam_3;
	}

	public Boolean getOptionBtnWithParam_4() {
		return optionBtnWithParam_4;
	}

	public void setOptionBtnWithParam_4(Boolean optionBtnWithParam_4) {
		this.optionBtnWithParam_4 = optionBtnWithParam_4;
	}

	public Boolean getOptionBtnWithParam_5() {
		return optionBtnWithParam_5;
	}

	public void setOptionBtnWithParam_5(Boolean optionBtnWithParam_5) {
		this.optionBtnWithParam_5 = optionBtnWithParam_5;
	}

	public Boolean getOptionBtnWithParam_6() {
		return optionBtnWithParam_6;
	}

	public void setOptionBtnWithParam_6(Boolean optionBtnWithParam_6) {
		this.optionBtnWithParam_6 = optionBtnWithParam_6;
	}

	public Boolean getOptionBtnWithParam_7() {
		return optionBtnWithParam_7;
	}

	public void setOptionBtnWithParam_7(Boolean optionBtnWithParam_7) {
		this.optionBtnWithParam_7 = optionBtnWithParam_7;
	}

	public Boolean getOptionBtnWithParam_8() {
		return optionBtnWithParam_8;
	}

	public void setOptionBtnWithParam_8(Boolean optionBtnWithParam_8) {
		this.optionBtnWithParam_8 = optionBtnWithParam_8;
	}

	public Boolean getOptionBtnWithParam_9() {
		return optionBtnWithParam_9;
	}

	public void setOptionBtnWithParam_9(Boolean optionBtnWithParam_9) {
		this.optionBtnWithParam_9 = optionBtnWithParam_9;
	}

	public String getOptionBtnCustomName_3() {
		return optionBtnCustomName_3;
	}

	public void setOptionBtnCustomName_3(String optionBtnCustomName_3) {
		this.optionBtnCustomName_3 = optionBtnCustomName_3;
	}

	public String getOptionBtnCustomName_1() {
		return optionBtnCustomName_1;
	}

	public void setOptionBtnCustomName_1(String optionBtnCustomName_1) {
		this.optionBtnCustomName_1 = optionBtnCustomName_1;
	}

	public String getOptionBtnCustomName_2() {
		return optionBtnCustomName_2;
	}

	public void setOptionBtnCustomName_2(String optionBtnCustomName_2) {
		this.optionBtnCustomName_2 = optionBtnCustomName_2;
	}

	public String getOptionBtnCustomName_4() {
		return optionBtnCustomName_4;
	}

	public void setOptionBtnCustomName_4(String optionBtnCustomName_4) {
		this.optionBtnCustomName_4 = optionBtnCustomName_4;
	}

	public String getOptionBtnCustomName_5() {
		return optionBtnCustomName_5;
	}

	public void setOptionBtnCustomName_5(String optionBtnCustomName_5) {
		this.optionBtnCustomName_5 = optionBtnCustomName_5;
	}

	public String getOptionBtnCustomName_6() {
		return optionBtnCustomName_6;
	}

	public void setOptionBtnCustomName_6(String optionBtnCustomName_6) {
		this.optionBtnCustomName_6 = optionBtnCustomName_6;
	}

	public String getOptionBtnCustomName_7() {
		return optionBtnCustomName_7;
	}

	public void setOptionBtnCustomName_7(String optionBtnCustomName_7) {
		this.optionBtnCustomName_7 = optionBtnCustomName_7;
	}

	public String getOptionBtnCustomName_8() {
		return optionBtnCustomName_8;
	}

	public void setOptionBtnCustomName_8(String optionBtnCustomName_8) {
		this.optionBtnCustomName_8 = optionBtnCustomName_8;
	}

	public String getOptionBtnCustomName_9() {
		return optionBtnCustomName_9;
	}

	public void setOptionBtnCustomName_9(String optionBtnCustomName_9) {
		this.optionBtnCustomName_9 = optionBtnCustomName_9;
	}

	public String getAddPrivilege() {
		return addPrivilege;
	}

	public void setAddPrivilege(String addPrivilege) {
		this.addPrivilege = addPrivilege;
	}

	public String getMultiDeletePrivilege() {
		return multiDeletePrivilege;
	}

	public void setMultiDeletePrivilege(String multiDeletePrivilege) {
		this.multiDeletePrivilege = multiDeletePrivilege;
	}

	public String getExportPrivilege() {
		return exportPrivilege;
	}

	public void setExportPrivilege(String exportPrivilege) {
		this.exportPrivilege = exportPrivilege;
	}

	public String getOptionBtnPrivilege_1() {
		return optionBtnPrivilege_1;
	}

	public void setOptionBtnPrivilege_1(String optionBtnPrivilege_1) {
		this.optionBtnPrivilege_1 = optionBtnPrivilege_1;
	}

	public String getOptionBtnPrivilege_2() {
		return optionBtnPrivilege_2;
	}

	public void setOptionBtnPrivilege_2(String optionBtnPrivilege_2) {
		this.optionBtnPrivilege_2 = optionBtnPrivilege_2;
	}

	public String getOptionBtnPrivilege_3() {
		return optionBtnPrivilege_3;
	}

	public void setOptionBtnPrivilege_3(String optionBtnPrivilege_3) {
		this.optionBtnPrivilege_3 = optionBtnPrivilege_3;
	}

	public String getOptionBtnPrivilege_4() {
		return optionBtnPrivilege_4;
	}

	public void setOptionBtnPrivilege_4(String optionBtnPrivilege_4) {
		this.optionBtnPrivilege_4 = optionBtnPrivilege_4;
	}

	public String getOptionBtnPrivilege_5() {
		return optionBtnPrivilege_5;
	}

	public void setOptionBtnPrivilege_5(String optionBtnPrivilege_5) {
		this.optionBtnPrivilege_5 = optionBtnPrivilege_5;
	}

	public String getOptionBtnPrivilege_6() {
		return optionBtnPrivilege_6;
	}

	public void setOptionBtnPrivilege_6(String optionBtnPrivilege_6) {
		this.optionBtnPrivilege_6 = optionBtnPrivilege_6;
	}

	public String getOptionBtnPrivilege_7() {
		return optionBtnPrivilege_7;
	}

	public void setOptionBtnPrivilege_7(String optionBtnPrivilege_7) {
		this.optionBtnPrivilege_7 = optionBtnPrivilege_7;
	}

	public String getOptionBtnPrivilege_8() {
		return optionBtnPrivilege_8;
	}

	public void setOptionBtnPrivilege_8(String optionBtnPrivilege_8) {
		this.optionBtnPrivilege_8 = optionBtnPrivilege_8;
	}

	public String getOptionBtnPrivilege_9() {
		return optionBtnPrivilege_9;
	}

	public void setOptionBtnPrivilege_9(String optionBtnPrivilege_9) {
		this.optionBtnPrivilege_9 = optionBtnPrivilege_9;
	}

	public Boolean getShowSingleEditBtn() {
		return showSingleEditBtn;
	}

	public void setShowSingleEditBtn(Boolean showSingleEditBtn) {
		this.showSingleEditBtn = showSingleEditBtn;
	}

	public Boolean getShowSingleDeleteBtn() {
		return showSingleDeleteBtn;
	}

	public void setShowSingleDeleteBtn(Boolean showSingleDeleteBtn) {
		this.showSingleDeleteBtn = showSingleDeleteBtn;
	}

	public Boolean getShowSingleDetailBtn() {
		return showSingleDetailBtn;
	}

	public void setShowSingleDetailBtn(Boolean showSingleDetailBtn) {
		this.showSingleDetailBtn = showSingleDetailBtn;
	}

	public String getSingleEditUrl() {
		return singleEditUrl;
	}

	public void setSingleEditUrl(String singleEditUrl) {
		this.singleEditUrl = singleEditUrl;
	}

	public String getSingleDeleteUrl() {
		return singleDeleteUrl;
	}

	public void setSingleDeleteUrl(String singleDeleteUrl) {
		this.singleDeleteUrl = singleDeleteUrl;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSingleDetailUrl() {
		return singleDetailUrl;
	}

	public void setSingleDetailUrl(String singleDetailUrl) {
		this.singleDetailUrl = singleDetailUrl;
	}

	public String getSingleEditPrivilege() {
		return singleEditPrivilege;
	}

	public void setSingleEditPrivilege(String singleEditPrivilege) {
		this.singleEditPrivilege = singleEditPrivilege;
	}

	public String getSingleDeletePrivilege() {
		return singleDeletePrivilege;
	}

	public void setSingleDeletePrivilege(String singleDeletePrivilege) {
		this.singleDeletePrivilege = singleDeletePrivilege;
	}

	public String getSingleDetailPrivilege() {
		return singleDetailPrivilege;
	}

	public void setSingleDetailPrivilege(String singleDetailPrivilege) {
		this.singleDetailPrivilege = singleDetailPrivilege;
	}

	public String getSingleOptionOperatePrivilege_1() {
		return singleOptionOperatePrivilege_1;
	}

	public void setSingleOptionOperatePrivilege_1(String singleOptionOperatePrivilege_1) {
		this.singleOptionOperatePrivilege_1 = singleOptionOperatePrivilege_1;
	}

	public String getSingleOptionOperatePrivilege_2() {
		return singleOptionOperatePrivilege_2;
	}

	public void setSingleOptionOperatePrivilege_2(String singleOptionOperatePrivilege_2) {
		this.singleOptionOperatePrivilege_2 = singleOptionOperatePrivilege_2;
	}

	public String getSingleOptionOperatePrivilege_3() {
		return singleOptionOperatePrivilege_3;
	}

	public void setSingleOptionOperatePrivilege_3(String singleOptionOperatePrivilege_3) {
		this.singleOptionOperatePrivilege_3 = singleOptionOperatePrivilege_3;
	}

	public String getSingleOptionOperateImgClass_3() {
		return singleOptionOperateImgClass_3;
	}

	public void setSingleOptionOperateImgClass_3(
			String singleOptionOperateImgClass_3) {
		this.singleOptionOperateImgClass_3 = singleOptionOperateImgClass_3;
	}

	public String getSingleOptionOperatePrivilege_4() {
		return singleOptionOperatePrivilege_4;
	}

	public void setSingleOptionOperatePrivilege_4(String singleOptionOperatePrivilege_4) {
		this.singleOptionOperatePrivilege_4 = singleOptionOperatePrivilege_4;
	}

	public String getSingleOptionOperateImgClass_4() {
		return singleOptionOperateImgClass_4;
	}

	public void setSingleOptionOperateImgClass_4(
			String singleOptionOperateImgClass_4) {
		this.singleOptionOperateImgClass_4 = singleOptionOperateImgClass_4;
	}

	public String getSingleOptionOperatePrivilege_5() {
		return singleOptionOperatePrivilege_5;
	}

	public void setSingleOptionOperatePrivilege_5(String singleOptionOperatePrivilege_5) {
		this.singleOptionOperatePrivilege_5 = singleOptionOperatePrivilege_5;
	}

	public String getSingleSelect() {
		return singleSelect;
	}

	public void setSingleSelect(String singleSelect) {
		this.singleSelect = singleSelect;
	}
	
	
	public String getPassPrivilege() {
		return passPrivilege;
	}

	public void setPassPrivilege(String passPrivilege) {
		this.passPrivilege = passPrivilege;
	}

	public String getRefuse() {
		return refuse;
	}

	public void setRefuse(String refuse) {
		this.refuse = refuse;
	}

	public String getRefusePrivilege() {
		return refusePrivilege;
	}

	public void setRefusePrivilege(String refusePrivilege) {
		this.refusePrivilege = refusePrivilege;
	}

	public String getAddTitle() {
		return addTitle;
	}

	public void setAddTitle(String addTitle) {
		this.addTitle = addTitle;
	}

	public String getExportTitle() {
		return exportTitle;
	}

	public void setExportTitle(String exportTitle) {
		this.exportTitle = exportTitle;
	}

	public String getMaxLengths() {
		return maxLengths;
	}

	public void setMaxLengths(String maxLengths) {
		this.maxLengths = maxLengths;
	}

	public String getIsSubStrings() {
		return isSubStrings;
	}

	public void setIsSubStrings(String isSubStrings) {
		this.isSubStrings = isSubStrings;
	}

	public String getSortable() {
		return sortable;
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	public Boolean getSingleEditAdminPrivilege() {
		return singleEditAdminPrivilege;
	}

	public void setSingleEditAdminPrivilege(Boolean singleEditAdminPrivilege) {
		this.singleEditAdminPrivilege = singleEditAdminPrivilege;
	}

	public Boolean getAutoInit() {
		return autoInit;
	}

	public void setAutoInit(Boolean autoInit) {
		this.autoInit = autoInit;
	}
}
