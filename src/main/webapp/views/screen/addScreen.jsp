<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<head>
<%
	String path=request.getContextPath();
%>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<title>卓智运维管理系统</title>
<link type="image/x-icon" href="<%=path%>/images/zos.ico"
	rel="shortcut icon">
<link id="main_css" href="<%=path%>/css/znms_style.css" rel="stylesheet"
	type="text/css">
<link id="main_css" href="<%=path%>/css/znms_style.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="<%=path %>/css/gridstack/bootstrap.min.css">
<link rel="stylesheet" href="<%=path %>/css/gridstack/gridstack.css" />
<link rel="stylesheet"
	href="<%=path %>/css/gridstack/gridstack-extra.css" />
<script src="<%=path %>/js/gridstack/jquery.min.js"></script>
<script src="<%=path %>/js/gridstack/jquery-ui.js"></script>
<script src="<%=path %>/js/gridstack/bootstrap.min.js"></script>
<script src="<%=path %>/js/gridstack/lodash.min.js"></script>
<script src="<%=path %>/js/gridstack/gridstack.js"></script>
<script src="<%=path %>/js/gridstack/gridstack.jQueryUI.js"></script>
<script src="<%=path %>/js/gridstack/anijs.js"></script>
<!-- 消息提示控件 start -->
<script src="<%=path %>/js/toastrplugins/toastr.js"></script>
<link href="<%=path %>/css/toastrplugins/toastr.min.css"
	rel="stylesheet" />
<!-- 消息提示控件 end -->
<link href="<%=path%>/css/flat-ui.min.css" rel="stylesheet" type="text/css">


<style type="text/css">
#grid1 {
	background: lightgoldenrodyellow;
}

#grid2 {
	background: lightcyan;
}

.grid-stack-item-content {
	color: #2c3e50;
	text-align: center;
	background-color: #18bc9c;
}

#grid2 .grid-stack-item-content {
	background-color: #9caabc;
}

.grid-stack-item-removing {
	opacity: 0.5;
}

.sidebar {
	background: rgba(0, 255, 0, 0.1);
	height: 150px;
	padding: 25px 0;
	text-align: center;
}

.sidebar .grid-stack-item {
	width: 200px;
	height: 100px;
	border: 2px dashed green;
	text-align: center;
	line-height: 100px;
	z-index: 10;
	background: rgba(0, 255, 0, 0.1);
	cursor: default;
	display: inline-block;
}

.sidebar .grid-stack-item .grid-stack-item-content {
	background: none;
}

.grid-stack {
	background: rgba(255, 255, 255, 0.2);
}

.grid-stack-item-content {
	color: #2c3e50;
	text-align: center;
	background-color: #18bc9c;
}

#main {
	height: 50px;
	width: 100%;
}

#left {
	float: left;
	height: 700px;
	width: 16%;
	border: medium solid #0000CC;
}

#right {
	float: left;
	height: 800px;
	width: 80%;
	border: medium solid #0000CC;
	background-color: #baf5b3;
}

.bor {
	border: 0.5px dashed #000000;
	width: 5px;
	height: 5px;
}

#grid-stack {
	display: table;
}

.css_tr {
	display: table-row;
}

.css_td {
	display: table-cell;
}
</style>

<script type="text/javascript">
    	toastr.options = {
		  "closeButton": true,
		  "debug": true,
		  "positionClass": "toast-top-full-width",
		  "showDuration": "300",
		  "hideDuration": "1000",
		  "timeOut": "10000",
		  "extendedTimeOut": "1000",
		  "showEasing": "swing",
		  "hideEasing": "linear",
		  "showMethod": "fadeIn",
		  "hideMethod": "fadeOut"
		}

    	function test(row,col){
   			var span="<span class=\"bor\">&nbsp;&nbsp;&nbsp;</span>";
   			var str='';
   			for (var i=0; i<row; i++){
   				for (var j=1; j<=col; j++){
  					//console.log(i + " :　" + j);
  					str+="<span class=\"bor\">&nbsp;&nbsp;&nbsp;</span>";
  					if(j==col){
  						str+="<br/>";
  					}
   				}
  			}
  			$("#test").append(str);
    	}
    	
    	$(document).ready(function(){
    		$("#grid-stack").css({ "width": "100%","height": "800px"}); 
    	});
    	
    	window.onload=function(){
    		$("#grid-stack").css({ "width": "100%","height": "800px"});
    		var html='';
    		for(var i=1; i<=${screenConfig.row}; i++){
    			html+="<div class=\"css_tr bor\">";
    			for (var j=1; j<=${screenConfig.col}; j++){
    				html+="<div class=\"css_td bor\" style=\"height:"+800/${screenConfig.row}+"px\"></div>";
    			}
    			html+="</div>";
    		}
    		$("#grid-stack").append(html);
    	}
    </script>
</head>
<body>
	<!-- 头部菜单 start-->
	<%@ include file="../common/header.jsp"%>
	<%@ include file="../common/leftMenu.jsp"%>
	<div class="container-fluid">
		<table border="0" cellspacing="0" cellpadding="0"
			class="nms_data_tablemod ">
			<td class="cast_bgimg">
				<div id="grid-stack" class="grid-stack "></div>
			</td>
			<td class=" wh_20b ">
				<div class="height_1kpx">
					<ul class="cast_mod_ul">
						<c:forEach var="configDataEntityList"
							items="${configDataEntityList}" varStatus="status">
							<li><img
								src="<%=path%>/images/screen/moduleimg/${configDataEntityList.spec}.png"
								width="100%" height="100%" id="add-widget${status.index}">
								<p>${configDataEntityList.moduleChineseName}</p></li>
						</c:forEach>
					</ul>

				</div>
			</td>
			</tr>
		</table>
		<div class="mod_buttom_box">
			<button type="button" class="btn btn-default" id="clear-grid">清除所有模块</button>
	 		<button type="button" class="btn btn-default" data-dismiss="modal" id="save-grid">保存</button>
		</div>
		<hr />
		<form id="detailForm">
			<input type="hidden" name="id" id="id" value="${screenConfig.id}">
			<textarea id="saved-data" name="configData" cols="100" rows="20"
				readonly="readonly" style="display:none"></textarea>
		</form>
	</div>


	<script type="text/javascript">
     	function calcWidthHeight(row){
     		if(row==1){
     			return 790;
     		}else if(row==2){
     			return 390;
     		}else if(row==3){
     			return 250;
     		}else if(row==4){
     			return 185;
     		}
     	}
     	
        $(function () {
        var options1 = {
        		disableResize:true,
				height:${screenConfig.row},
				width:12,
				cellHeight:calcWidthHeight(${screenConfig.row}),
				cellWidth:395
				
           };
          $('.grid-stack').gridstack(options1);
            
          $('.grid-stack').gridstack();
            var self = this;
            this.grid = $('.grid-stack').data('gridstack');
            $('.grid-stack').on('added', function(event, items) {
                // add anijs data to gridstack item
                for (var i = 0; i < items.length; i++) {
                    $(items[i].el[0]).attr('data-anijs', 'if: added, do: swing animated, after: $removeAnimations, on: $gridstack','data-gs-auto-position');
                }
                AniJS.run();
                self.gridstackNotifier = AniJS.getNotifier('gridstack');
                // fire added event!
                self.gridstackNotifier.dispatchEvent('added');
            });
            <c:forEach var="configDataEntityList" items="${configDataEntityList}" varStatus="status">  
            	$('#add-widget${status.index}').click(function() {
               	 addNewWidget(0,0,${configDataEntityList.width},${configDataEntityList.height},'${configDataEntityList.moduleName}');
          	   });
            </c:forEach> 

            function addNewWidget(x,y,width,height,moduleName) {
            	if(width>12){
					toastr.clear();
					toastr.error("剩余空间不足");
					return;
				}
                var grid = $('.grid-stack').data('gridstack');
                //console.log(${screenConfig.col}+"**"+height);
                if(${screenConfig.row}<height){
                	toastr.error("请创建对应行和列"); return;
                }
                
                //var x=grid.isAreaEmpty(${screenConfig.col},${screenConfig.row},8,1);
                //console.log(x);
                //console.log(width+" : "+height+" : "+moduleName);
					
                if(grid.isAreaEmpty(${screenConfig.col},${screenConfig.row},12,${screenConfig.col}==2?2:3)){
               		grid.addWidget($('<div><div class="grid-stack-item-content" style="background-image:url(<%=path%>/images/screen/'+moduleName+'-thumbnail.jpg);background-size:100% 100% ;  background-repeat:no-repeat;"></div></div>'),x, y, width,height,moduleName, true);
                }else{
                	toastr.clear();
					toastr.error("该空间已被占用");
                }
    			 $div = $('#grid-stack').children('div');
				 $div.each(
				   function(i){
				    	if($(this)[0].attributes.length==8){
						 var x=$(this)[0].attributes[0].value;
						 var y=$(this)[0].attributes[1].value;
						 var moduleName=$(this)[0].attributes[4].value;
						 if(y >(${screenConfig.row}-1)){
						 	grid.removeWidget($(this));
						 	toastr.clear();
							toastr.error("剩余空间不足");
						 }
				  		}
					});
            }
		
            var animationHelper = AniJS.getHelper();
            
           //记录拖拽前个个模块的位置信息 
           var arrayObj = new Array();
            $('.grid-stack').on('dragstart', function (event, ui) {
    			$div = $('#grid-stack').children('div');
				$div.each(
				  function(i){
				  	if($(this)[0].attributes.length==8){
						var x=$(this)[0].attributes[0].value;
						var y=$(this)[0].attributes[1].value;
						var moduleName=$(this)[0].attributes[4].value;
						arrayObj.push({x:x,y:y,moduleName:moduleName});
				  	}
				});
            });
            
  			$('.grid-stack').on('dragstop', function (event, ui) {
    			var grid = this;
    			var element = event.target;
    			//console.log(element.getAttribute("data-gs-x"));
    			var modulename=element.attributes[4].nodeValue;
    			$('.grid-stack').on('change', function (event, ui) {
    			var grid = this;
    			var element = event.target;
    			if(ui!=undefined){
				for (var i=0; i<ui.length; i++){
					
					if(modulename==ui[i].moduleName){
						if(((ui[i].lastTriedX%4)!=0)){
							ui[i].x=ui[i]._beforeDragX;
							ui[i].y=ui[i]._beforeDragY;
							element.removeAttribute("data-gs-x");
							element.removeAttribute("data-gs-y");
							element.lastChild.setAttribute("data-gs-x",ui[i]._beforeDragX);
							element.lastChild.setAttribute("data-gs-y",ui[i]._beforeDragY);
							for(var k=0;k<arrayObj.length;k++){
							$div = $('#grid-stack').children('div');
								$div.each(
			 	 						function(i){
			  							if($(this)[0].attributes.length==8){
										if(arrayObj[k].moduleName==$(this)[0].attributes[4].value){
												$(this).attr("data-gs-x",arrayObj[k].x);
											$(this).attr("data-gs-y",arrayObj[k].y);
										}
			  						}
								});
							}
					}else{
						var grid = this;
						var element = event.target;
					}
				  }		
				}
			}
    		}); 	
			});   

            //Defining removeAnimations to remove existing animations
            animationHelper.removeAnimations = function(e, animationContext){
                $('.grid-stack-item').attr('data-anijs', '');
            };
            var options = {
            		float: false,
					disableDrag:true,
					disableResize:true,
					height:3,
					width:12,
					removable:false,
					verticalMargin:0,
					cellHeight:380
            };
            $('.grid-stack').gridstack(options);
            new function () {
                //this.serializedData = [{ "x": 0,"y": 0,"width": 2,"height": 1}];
                this.grid = $('.grid-stack').data('gridstack');
                this.loadGrid = function () {
                    this.grid.removeAll();
                    var items = GridStackUI.Utils.sort(this.serializedData);
                    _.each(items, function (node,i) {
                        this.grid.addWidget($('<div><div class="grid-stack-item-content" /><div/>'),
                        		node.x, node.y, node.width, node.height);
                    }, this);
                    return false;
                }.bind(this);

                this.saveGrid = function () {
                    this.serializedData = _.map($('.grid-stack > .grid-stack-item:visible'), function (el) {
                        el = $(el);
                        var node = el.data('_gridstack_node');
                        //console.log(node);
                        return {
                        	moduleName:node.moduleName,
                            x: node.x,
                            y: node.y,
                            width: node.width,
                            height: node.height
                        };
                    }, this);
                    //console.log(JSON.stringify(this.serializedData, null, '    '));
                    $('#saved-data').val(JSON.stringify(this.serializedData, null, '    '));
                    
                    var savedData=$("#saved-data").val();
              		var id=$("#id").val();
              		$.ajax({
              			url:"${pageContext.request.contextPath}/screen/updateScreen",
              			data:$("#detailForm").serialize(),
              			type:'POST',
              			success:function(data){
							//eval("var data="+data);
							if(data.result){
								window.location="${pageContext.request.contextPath}/screen";
							}else{
								toastr.clear();
								toastr.error(data.msg);
							}
              			}
              		});	
                    return false;
                }.bind(this);
				
                this.clearGrid = function () {
                    this.grid.removeAll();
                    return false;
                }.bind(this);
				
                $('#save-grid').click(this.saveGrid);
                $('#load-grid').click(this.loadGrid);
                $('#clear-grid').click(this.clearGrid);
                this.loadGrid();
            };
        });
    </script>
</body>
</html>