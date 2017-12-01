<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="../../common/include.jsp"%>
<script src="<%=path %>/js/jquery.form.min.js"></script>
<style type="text/css">
.myLabel{font-weight:bold;margin-bottom:10px;padding: 10px;word-wrap: break-word;word-break: break-all;white-space: nowrap;color: #09d;text-align: left;font-size: 14px;border-bottom: 1px solid #09d;}
#resultTable i{background: url(<%=path%>/images/bgimg.png) no-repeat;width: 20px;height: 20px;background-position: -115px -42px;}
[queue]{display:none;}
</style>

<script type="text/javascript">
$(document).ready(function(){
	refresh();
	$("#importBtn").click(function(){
		$("#importForm").ajaxSubmit({
            type: 'post',
            url: "<%=path%>/host/import/do",
            success: function(data) {
           		toastr.clear();
            	if(data.result) {
            		toastr.success("导入任务已提交");
            	} else {
            		toastr.error(data.msg);
            	}
            }  
       });
	})
	
	setInterval(function(){
		refresh();
	}, 3000);
})

function refresh(){
	$.ajax({
		type:"post",
		url:"<%=path%>/host/import/queue",
		success:function(data) {
			$("#queueTable").find("[dataId]").remove();
			for(var i=0;i<data.length;i++){
				var task = data[i];
				var content = "";
				content += '<tr dataId="'+task.id+'" style="line-height:30px;height:30px;">';
					content += '<td style="border:1px solid #ddd">'+task.registerTime+'</td>';
				if(task.running) {
					content += '<td style="border:1px solid #ddd">导入中</td>';
				} else {
					content += '<td style="border:1px solid #ddd">队列中</td>';
				}
				content += '<td style="border:1px solid #ddd">'+task.adminName+'</td>';
				if(task.running) {
					content += '<td style="border:1px solid #ddd">'+task.imported+'/'+task.total +'</td>';					
				} else {
					content += '<td style="border:1px solid #ddd">-</td>';
				}
				content += '</tr>';
				$(content).appendTo($("#queueTable"));
			}
			if(data.length>0){
				$("[queue]").fadeIn(200);
			} else {
				$("[queue]").fadeOut(200);
			}
		}
	});
	
	$.ajax({
		type:"post",
		url:"<%=path%>/host/import/result",
		success:function(data) {
			$("#resultTable").find("[dataId]").remove();
			for(var i=0;i<data.length;i++){
				var task = data[i];
				var content = "";
				content += '<tr dataId="'+task.id+'" style="line-height:30px;height:30px;">';
					content += '<td style="border:1px solid #ddd">'+task.finishTime+'</td>';
					if(task.result) {
						content += '<td style="border:1px solid #ddd">正常</td>';						
						content += '<td style="border:1px solid #ddd"><a href="<%=path%>/files/'+task.id+'.xlsx" style="color:green;">详情</a></td>';
					} else {
						content += '<td style="border:1px solid #ddd">异常</td>';
						content += '<td style="border:1px solid #ddd"><a href="<%=path%>/files/'+task.id+'.xlsx" style="color:red;">详情</a></td>';
					}
					content += '<td style="border:1px solid #ddd">'+task.adminName+'</td>';
					content += '<td style="border:1px solid #ddd"><a href="javascript:deleteResult(\''+task.id+'\');"><i></i></a></td>';
				content += '</tr>';
				$(content).appendTo($("#resultTable"));
			}
		}
	});
}

function deleteResult(id){
	$.ajax({
		type:"post",
		url:"<%=path%>/host/import/deleteResult",
		data:{"id":id},
		success:function(data){
			toastr.clear();
			if(data.result){
				$("[dataId='"+id+"']").remove();
				toastr.success("已删除");
			} else {
				toastr.error(data.msg);
			}
		}
	});
}
</script>
</head>

<body>
	<%@ include file="../../common/header.jsp"%>
	<%@ include file="../../common/leftMenu.jsp"%>
	
	<div class="znms_box">
		<p class="page_top_menu">设置&gt; <span>主机管理</span>
			<span class="bold" >&gt;导入</span>
		</p>
		
		<div class="myLabel" queue>任务队列</div>
		<table style="width:100%;text-align:center;border:1px solid #c1c1c1;border-collapse:collapse;" id="queueTable" queue>
	     	<tr style="background:#e5e5e5;line-height:30px;height:30px;font-weight:bold;font-size:15px;">
	     		<td style="border:1px solid #ddd;width:25%;">注册时间</td>
		     	<td style="border:1px solid #ddd;width:25%;">状态</td>
		     	<td style="border:1px solid #ddd;width:25%;">用户ID</td>
		     	<td style="border:1px solid #ddd;width:25%;">完成度</td>
     		</tr>
	     </table>
		
		<form id="importForm" enctype="multipart/form-data">
			<table border="0" cellspacing="0" cellpadding="0" class="school_table_box no_bg">
				<tr>
					<th colspan="6">导入</th>
				</tr>
				<tr>
					<td class="table_td_bg">导入文件</td>
					<td align="left" class="table_td_txtleft" colspan="2">
						<input type="file" name="importFile" />
					</td>
					<td class="table_td_bg"></td>
					<td align="left" class="table_td_txtleft" colspan="2"></td>
				</tr>
				<tr>
					<td class="table_td_bg">导入模板</td>
					<td align="left" class="table_td_txtleft" colspan="4">
						<a href="<%=path%>/innerFile/主机导入模板.xlsx">下载</a>
					</td>
				</tr>
			</table>
		</form>
		
		<div class="but_mod_box">
            <button class="but_mod" type="button" onclick="history.back();">取消</button>
           	<button class="but_mod" type="button" id="importBtn">导入</button>
	     </div>
	     
	     <div class="myLabel">导入结果</div>
	     <table style="width:100%;text-align:center;border:1px solid #c1c1c1;border-collapse:collapse;" id="resultTable">
	     	<tr style="background:#e5e5e5;line-height:30px;height:30px;font-weight:bold;font-size:15px;">
	     		<td style="border:1px solid #ddd;width:20%;">完成时间</td>
		     	<td style="border:1px solid #ddd;width:20%;">导入结果</td>
		     	<td style="border:1px solid #ddd;width:20%;">结果详情</td>
		     	<td style="border:1px solid #ddd;width:20%;">用户ID</td>
		     	<td style="border:1px solid #ddd;width:20%;">操作</td>	     	
     		</tr>
	     </table>
		
		<%@ include file="../../common/footer.jsp"%>
	</div>
</body>
</html>
