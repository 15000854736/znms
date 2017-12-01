<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../../common/include.jsp"%>
<script src="<%=path %>/js/vendor/flat-ui.min.js"></script>
<script src="<%=path %>/js/vendor/video.js"></script>
<script src="<%=path %>/js/prettify.js"></script>
<script src="<%=path %>/js/application.js"></script>
<link href="<%=path%>/css/znms_style_new.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" href="<%=path%>/css/codemirror/docs.css">
<link rel="stylesheet" href="<%=path%>/css/codemirror/codemirror.css">
<link rel="stylesheet" href="<%=path%>/css/codemirror/merge.css">
<script src="<%=path%>/js/codemirror/codemirror.js"></script>
<script src="<%=path%>/js/codemirror/xml.js"></script>
<script src="<%=path%>/js/codemirror/css.js"></script>
<script src="<%=path%>/js/codemirror/javascript.js"></script>
<script src="<%=path%>/js/codemirror/htmlmixed.js"></script>
<script src="<%=path%>/js/codemirror/diff_match_patch.js"></script>
<script src="<%=path%>/js/codemirror/merge.js"></script>
<style type="text/css">
.CodeMirror-merge-pane-rightmost{position:initial;}
.CodeMirror-merge-pane{width:49% !important;}
.CodeMirror-merge-gap{width:2% !important;}
.CodeMirror-merge-copy{right:35% !important;}
.CodeMirror-line {text-align:left;}
</style>
<script>
	$(document).ready(function(){
		$('[data-toggle="select"]').select2({dropdownCssClass: 'select-inverse-dropdown'});
		$('#base_host').on("change", function(e){
			refreshCascadeList("base_host", "base_file");
		});
		$('#compare_host').on("change", function(e){
			refreshCascadeList("compare_host", "compare_file");
		});
		
		$("#base_file,#compare_file").on("change", function(e) {
			var baseId = $("#base_file").val();
			var compareId = $("#compare_file").val();
			if(baseId!="" && compareId !=""){
				var baseFilePath = $("#base_file").find("[uuid='"+baseId+"']").attr("path");
				var compareFilePath = $("#compare_file").find("[uuid='"+compareId+"']").attr("path");
				$.ajax({
					type:"POST",
					url:"<%=path%>/backupRecord/compare/getFileContent",
					data:{basePath:baseFilePath, comparePath:compareFilePath},
					success:function(data){
						doCompare(data.baseContent, data.compareContent);
					}
				});
			} else {
				$("#view").empty();
			}
		});
	});
	
	function refreshCascadeList(parent,child){
		$("#view").empty();
		$.ajax({
			url : "<%=path%>/backupRecord/compare/getBackupFileList",
			type : "POST",
			data : {"id":$("#"+parent).val()},
			success : function(data) {
				$("#"+child+" option[value!='']").remove();
				$("#"+child).select2("val","");
				if(data.length>0){
					for(var i=0;i<data.length;i++){
						var file = data[i];
						$("#"+child).append("<option path='"+file.fullFilePath+"' uuid='"+file.id+"' value='"+file.id+"'>"+file.fileName+"</option>");
					}
				}
			}
		});
	}
	
	function doCompare(baseContent, compareContent) {
		value=baseContent;
	    orig1=baseContent;
	    orig2=compareContent;
	    initUI();
	}
</script>
</head>

<body>
	<%@ include file="../../common/header.jsp"%>
	<%@ include file="../../common/leftMenu.jsp"%>

	<div class="nms_box_center">
		<p class="page_top_menu">设置&gt; 配置备份</p>

		<ul class="menu_mod_box">
			<li id="1"><a href="${pageContext.request.contextPath }/backupConfiguration">账户&密码</a></li>
			<li id="2"><a href="${pageContext.request.contextPath }/backupConfiguration/device">设备</a></li>
			<li id="3"><a href="${pageContext.request.contextPath }/backupRecord">备份</a></li>
			<li id="4" class="tab_hov"><a href="javascript:void(0)">比较</a></li>
		</ul>

		<table class="config_comparison_box">
			<tr>
				<th>
					<select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="base_host">
						<option value="">请选择主机</option>
						<c:forEach items="${hostList}" var="host">
							<option value="${host.id}">${host.hostName}</option>
						</c:forEach>
					</select>
					<select data-toggle="select" name="inverse-dropdown" class="form-control select select-default mrs mbm" id="base_file">
						<option value="">请选择文件</option>
					</select>
				</th>
				<th>
					<select data-toggle="select" name="inverse-dropdown2" class="form-control select select-default mrs mbm" id="compare_host">
						<option value="">请选择主机</option>
						<c:forEach items="${hostList}" var="host">
							<option value="${host.id}">${host.hostName}</option>
						</c:forEach>
					</select> 
					<select data-toggle="select" name="inverse-dropdown2" class="form-control select select-default mrs mbm" id="compare_file">
						<option value="">请选择文件</option>
					</select>
				</th>
			</tr>
			<tr>
				<td colspan="2">
					<div id="view" style="max-width:1635px;"></div>
				</td>
			</tr>
		</table>
	</div>

<script type="text/javascript">
var value, orig1, orig2, dv, panes = 2, highlight = true, connect = null, collapse = false;
function initUI() {
  if (value == null) return;
  var target = document.getElementById("view");
  target.innerHTML = "";
  dv = CodeMirror.MergeView(target, {
    value: value,
    origLeft: panes == 3 && !collapse && !connect ? orig1 : null,
    orig: orig2,
    lineNumbers: true,
    mode: "text/html",
    highlightDifferences: highlight,
    connect: connect,
    collapseIdentical: collapse
  });
}

function toggleDifferences() {
  dv.setShowDifferences(highlight = !highlight);
}


function mergeViewHeight(mergeView) {
  function editorHeight(editor) {
    if (!editor) return 0;
    return editor.getScrollInfo().height;
  }
  return Math.max(editorHeight(mergeView.leftOriginal()),
                  editorHeight(mergeView.editor()),
                  editorHeight(mergeView.rightOriginal()));
}

function resize(mergeView) {
  var height = mergeViewHeight(mergeView);
  for(;;) {
    if (mergeView.leftOriginal())
      mergeView.leftOriginal().setSize(null, height);
    mergeView.editor().setSize(null, height);
    if (mergeView.rightOriginal())
      mergeView.rightOriginal().setSize(null, height);

    var newHeight = mergeViewHeight(mergeView);
    if (newHeight >= height) break;
    else height = newHeight;
  }
  mergeView.wrap.style.height = height + "px";
}
</script>
</body>
</html>
