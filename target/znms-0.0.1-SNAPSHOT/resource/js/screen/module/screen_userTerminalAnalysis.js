/**
 * Created by shenqilei on 2016/11/30.
 */
function refreshUserTerminalAnalysis(url){
	$.ajax({
        type:'POST',
        url:url,
        cache:false,
        success:function(data){
        	var  teacherios=data.教师.ios;
        	var  teacherandroid=data.教师.android;
        	var  teacherwindows=data.教师.windows;
        	var  teacherother=data.教师.其他;
        	var  totalCountTeacher=teacherios+teacherandroid+teacherwindows+teacherother;
        	
        	var str = totalCountTeacher+"".split("");//字符串转化为数组
        	var teacher_online_terminal="";
        	for(var i=0;i< str.length;i++){
        		teacher_online_terminal+="<li>"+str[i]+"</li>";
        	}
        	
        	var  studentios=data.学生.ios;
        	var  studentandroid=data.学生.android;
        	var  studentwindows=data.学生.windows;
        	var  studentother=data.学生.其他;
        	var totalCountStudent=studentios+studentandroid+studentwindows+studentother;
        	var studentStr = totalCountStudent+"".split("");//字符串转化为数组
        	var student_online_terminal="";
        	for(var i=0;i< studentStr.length;i++){
        		student_online_terminal+="<li>"+studentStr[i]+"</li>";
        	}
        	
        	$("#teacher_online_terminal").html(teacher_online_terminal);
        	$("#iso_teacher_terminal").html(teacherios);
        	$("#android_teacher_terminal").html(teacherandroid);
        	$("#windows_teacher_terminal").html(teacherwindows);
        	$("#other_teacher_terminal").html(teacherother);
        	
        	$("#student_online_terminal").html(student_online_terminal);
        	$("#iso_student_terminal").html(data.学生.ios);
        	$("#android_student_terminal").html(data.学生.android);
        	$("#windows_student_terminal").html(data.学生.windows);
        	$("#other_student_terminal").html(data.学生.其他);
        	teacherPieChart(teacherios,teacherandroid,teacherwindows,teacherother,totalCountTeacher);
        	studentPieChart(studentios,studentandroid,studentwindows,studentother,totalCountStudent);
        }
	});
}
function teacherPieChart(teacherios,teacherandroid,teacherwindows,teacherother,totalCountTeacher){
 var iosPer=teacherios/totalCountTeacher;
 var androidPer=teacherandroid/totalCountTeacher;
 var windowsPer=teacherwindows/totalCountTeacher;
 var otherPer=teacherother/totalCountTeacher;
 var winWidth  = window.screen.width;
	var winHeight = window.screen.height;
	var width;
	var height;
	if(winWidth==1920 && winHeight==1080){
		width='300';
		height='282';
	}else if(winWidth==1600 && winHeight==900){
		width='225';
		height='250';
	}else if(winWidth==1366 && winHeight==768){
		width='200';
		height='140';
	}else if(winWidth==1280 && winHeight==1024){
		width='200';
		height='180';
	}
	var points=[];
	if(teacherios>0){
		points.push({ x: "IOS", text: iosPer+"%", y: teacherios,fill:"#00D0A8" });
	}
	if(teacherandroid>0){
		points.push({ x: "Android", text: androidPer+"%", y: teacherandroid,fill:"#0071BC" });
	}
	if(teacherwindows>0){
		points.push({ x: "Windows", text: windowsPer+"%", y: teacherwindows,fill:"#8DE908" });
	}
	if(teacherother>0){
		points.push( { x: "Other", text: otherPer+"%", y: teacherother ,fill:"#FBB03B"});
	}
	if(points.length>0){
		points.push( { x: "暂无数据", text: "0%", y: "0.1" });
	}
	
 $("#teacher_terminal").ejChart({   
		//Initializing Common Properties for all the series     		
        commonSeriesOptions: 
		{
            labelPosition: 'outside',                          
            tooltip: { visible: true, format: "#point.x# : #point.y#%" },
            marker: 
			{
				dataLabel: 
				{ 
					shape: 'none', 
					visible: false, 
					textPosition: 'top', 
					border: { width: 1}, 
					connectorLine: { height: 70, stroke:"black" } 
				}
			}
        },
        
		//Initializing Series
		series: 
		[
            {
                points: [{ x: "IOS", text: iosPer+"%", y: teacherios,fill:"#00D0A8" }, 
						 { x: "Android", text: androidPer+"%", y: teacherandroid,fill:"#0071BC" }, 
						 { x: "Windows", text: windowsPer+"%", y: teacherwindows,fill:"#8DE908" }, 
						 { x: "Other", text: otherPer+"%", y: teacherother ,fill:"#FBB03B"}], 
				
				//explodeIndex: 0,                              
                border: { width: 2, color: 'white' },
                type: 'pie', 
				labelPosition: 'outside', 
				startAngle: 145
            }
        ],
		
        //Enabling 3D Chart			
        enable3D: true,
		enableRotation:true,
        depth: 30,
        tilt: -70,
        rotation: 0,
        perspectiveAngle: 90,                       
        canResize: false,
		load: "onchartload",                      
        size: { height: height,width:width},                     
        legend: { visible: false }
    });
}
function studentPieChart(studentios,studentandroid,studentwindows,studentother,totalCountStudent){
 var iosPer=studentios/totalCountStudent;
 var androidPer=studentandroid/totalCountStudent;
 var windowsPer=studentwindows/totalCountStudent;
 var otherPer=studentother/totalCountStudent;
 var winWidth  = window.screen.width;
	var winHeight = window.screen.height;
	var width;
	var height;
	if(winWidth==1920 && winHeight==1080){
		width='300';
		height='282';
	}else if(winWidth==1600 && winHeight==900){
		width='225';
		height='250';
	}else if(winWidth==1366 && winHeight==768){
		width='200';
		height='140';
	}else if(winWidth==1280 && winHeight==1024){
		width='200';
		height='180';
	}
 $("#student_terminal").ejChart({   
		//Initializing Common Properties for all the series     		
        commonSeriesOptions: 
		{
            labelPosition: 'outside',                          
            tooltip: { visible: true, format: "#point.x# : #point.y#%" },
            marker: 
			{
				dataLabel: 
				{ 
					shape: 'none', 
					visible: false, 
					textPosition: 'top', 
					border: { width: 1}, 
					connectorLine: { height: 70, stroke:"black" } 
				}
			}
        },
        
		//Initializing Series
        series: 
    		[
                {
                    points: [{ x: "Windows", text: iosPer+"%", y: totalCountStudent,fill:"#8DE908" }, 
    						 { x: "Android", text: androidPer+"%", y: studentandroid,fill:"#0071BC" }, 
    						 { x: "IOS", text: windowsPer+"%", y: studentios,fill:"#00D0A8" }, 
    						 { x: "Other", text: otherPer+"%", y: studentother ,fill:"#FBB03B"}], 
    				
    				//explodeIndex: 0,                              
                    border: { width: 2, color: 'white' },
                    type: 'pie', 
    				labelPosition: 'outside', 
    				startAngle: 145
                }
            ],
		
        //Enabling 3D Chart			
        enable3D: true,
		enableRotation:true,
        depth: 30,
        tilt: -70,
        rotation: 0,
        perspectiveAngle: 90,                       
        canResize: false,
		load: "onchartload",                      
		size: { height: height,width:width},                     
        legend: { visible: false }
    });
}