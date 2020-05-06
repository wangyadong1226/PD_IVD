<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="true">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>无标题文档</title>
	<script type="text/javascript" src="${ctx }/js/jquery-form.js"></script>
	
	</head>
	<script>
	var over = false;
	var inter;
	var si;
	function add_onClick() {  //执行添加方法
	   var str = document.getElementById("doc").value;
	   var str2 = str.split(".");
	   var str3 = str2[str2.length-1];
	   if(str3.toUpperCase()=='CSV')
	   {
	   
	   }else
	   {
	      alert('文件为空或类型不对！请使用提供的模板类型（csv）格式');
	      return false;
	   }
	    $("#buttonId").attr('disabled',true);
	    $("#buttonBack").attr('disabled',true);
	    processerbar(5000);
		var options ={
		url : "${ctx }/batch/save.html",
		data : {},  // 参数  
		type : "post",  
		cache : false,  
		dataType : "text",  //返回json数据 
	    beforeSend: LoadFunction, //加载执行方法    
	    error: erryFunction,  //错误执行方法    
	    success: succFunction //成功执行方法        
		}
		
		var form =$("#fm");//form1:表单ID  在表单界面只用这一个表单ID
	            	   
	    form.ajaxSubmit(options); 
	}
	
	   function LoadFunction() {  
	       //alert('加载中...');  
	   }  
	   function erryFunction(XMLHttpRequest, textStatus, errorThrown) {  
	   		$("#buttonId").attr('disabled',true);
	    	$("#buttonBack").attr('disabled',true);
	    	location.href="deleteBack.html";
	    	alert("未知错误，上传失败。");
	       					//alert(XMLHttpRequest.status);
	                        //alert(XMLHttpRequest.readyState);
	                        //alert(textStatus);
	   }  
	   function succFunction(data) {
		   if(data != ""){
		       alert(data);  
		       location.reload();
		       if(data !="测序号已经存在!"){
		       	 location.href="deleteBack.html";
		       }
		   	   //location.href="batch.do?o=toAdd";
		   }else{
		   		//if(document.getElementById('percent').innerHTML=="100%") {
		   	$("#line").width(200);
		   	document.getElementById('percent').innerHTML="100%"
			clearInterval(si);
			document.getElementById('msg').innerHTML="&nbsp;&nbsp;成功&nbsp;&nbsp;三秒后自动跳转!";
			setTimeout(locationHret,3000);
			//}
		   }
	   }
	   
	function locationHret() {
		location.href="${ctx }/batch.html";
	}
	
	
	function onchangecallback(data){
		alert(data); 
		document.getElementById('courseId').options.length = 0;  //清空原有的option 
		var str="<option value='00000'>请选择</option>";  
		for(var i=0;i<data.length;i++){  
		
		str+="<option value='"+data[i].id+"'>"+data[i].courseName+"</option>"  
		}  
		$("#courseId").html(str);  
	} 
	
	
	function processerbar(time){
	     document.getElementById('probar').style.display="block";
		$("#line").each(function(i,item){
			var a=parseInt($(item).attr("w"));
			$(item).animate({
				width: a+"%"
			},time);
		});
	   si = window.setInterval(
		function(){
			a=$("#line").width();
			b=(a/200*100).toFixed(0);
			if(document.getElementById('percent').innerHTML=="99%"){
				document.getElementById('percent').innerHTML="99%";
				document.getElementById('msg').innerHTML="上传中";
			}else{
				document.getElementById('percent').innerHTML=b+"%";
				document.getElementById('percent').style.left=a-12+"px";
				document.getElementById('msg').innerHTML="上传中";
			}
		},70);
	};
	
	
			function doBack(){
				var path = "${ctx}/batch.do?o=toList";  
	   			$('#fm').attr("action", path).submit(); 
			}
			
		    function doDownLoadModel(){
		    	//window.location.href = "${ctx}/downLoad/model.csv";
				var path = "${ctx}/batch.do?o=toDownLoadModel";  
	   			$('#fm').attr("action", path).submit(); 
			}
	
	</script>
	<body>
		<form action="${ctx}/batch/save.html" id="fm" enctype="multipart/form-data" method="post">
			<div class="place">
		    <span>位置：</span>
		    <ul class="placeul">
		    <li><a href="${ctx}/batch.html">首页</a></li>
		    <li><a href="${ctx}/batch.html">批次管理</a></li>
		    <li><a href="#">数据上传</a></li>
		    </ul>
		    </div>
		    
		    <div class="formbody">
		    
		    <div class="formtitle"><span>上传批次</span></div>
		    <ul class="forminfo">
		    <li><label>文件名 :</label><input type="file" name="doc" id="doc"></input><i></i></li>
		    <li><label>&nbsp;</label><input name="" type="button" class="btn" id="buttonId" onclick="javascript:add_onClick();" value="确认上传"/></li>
		    <li><label>&nbsp;</label><a href="${ctx}/downLoad/model.csv" target="_blank"><input name="" type="button" class="btn" id="buttonDownLoadId" onclick="" value="模板下载"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red" style="font-size: 20px;">*请使用另存为保存</font></li>
		    <!-- 进度条 -->
			<div  class="barline" id="probar">
				<div id="percent"></div>
				<div id="line" w="100" style="width:0px;"></div>	
				<div id="msg" style=""></div>			
			</div>
		    </ul>
		    </div>
		</form>
	</body>

</html>
