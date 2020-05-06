<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>布局</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">	
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<link href="../toolbar/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="../toolbar/css/Toolbar.css" rel="stylesheet" type="text/css"/>
    <script src="../toolbar/js/jquery.js" type="text/javascript"></script>
    <script src="../toolbar/js/Toolbar.js" type="text/javascript"></script>
    <script src="../js/json2.js" type="text/javascript"></script>
    <script type="text/javascript" src="../js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<script type="text/javascript">
    var toolbar;
    //js的注释与html的注释放开,再看一下效果
    $(document).ready(function(){
        
		var state = '${state}';
		//var reportTemplate = '${reportTemplate}';
		//alert(reportTemplate)
		if(state == 1){
	      toolbar = new Toolbar({
	        renderTo : 'toolbar',
	        items : [{
	            type : 'button',
	            text : '返回',
	            bodyStyle : 'edit',
	            useable : 'T',
	            handler : function(){
	          		window.location.href = 'javascript:history.back(-1)';
	          		
	            }
	          }],
	        active : 'ALL'//激活哪个
	      });
		}else{
			toolbar = new Toolbar({
		        renderTo : 'toolbar',
				//border: 'top',
		        items : [{
		          type : 'button',
		          text : '审核通过',
		          bodyStyle : 'save',
		          useable : 'T',
		          handler : auditPass
		        }/*,{
		          type : 'button',
		          text : '驳回',
		          bodyStyle : 'edit',
		          useable : 'T',
		          handler : refusal
		        }*/,'-',{
		            type : 'button',
		            text : '返回',
		            bodyStyle : 'edit',
		            useable : 'T',
		            handler : backtrack
		          }],
		        active : 'ALL'//激活哪个
		      });
		}
		toolbar.render();
		toolbar.genAZ();
    });
</script>
</head>
<input type="hidden" id="sampleId" name="sampleId" value="${sampleId}"/>
<input type="hidden" id="batchId" name="batchId" value="${batchId}"/>
<input type="hidden" id="batchId" name="currentPage" value="${currentPage}"/>
<body class="easyui-layout">
	<div region="north" title="" split="false" style="height:28px;overflow: hidden;">
		<div id="toolbar"></div>
	</div>
	<div id="abc" data-options="region:'west',split:true,title:'展示PDF报告',href:'${pageContext.request.contextPath}/reportAudit/pdfReport.html?sampleId=${sampleId }'" style="width:560px;overflow: hidden;padding: 5px;">	
	</div>
	<div data-options="region:'center',title:'样本信息'" href="" style="overflow: hidden;">
		<iframe src="${pageContext.request.contextPath}/reportAudit/clinicalInfo.html?sampleId=${sampleId }" frameborder="0" id="main" name="main" height="100%" width="100%" scrolling="auto"></iframe>
	</div>
</body>	
<script type="text/javascript">
  	
	//审核通过
	function auditPass(){
		$.ajax({
	        type: "POST",
	        url: "nbAuditPass.html",
	        data: {"tbSampleId":${sampleId }},
	        dataType: "html",
	        success: function (msg) {
		        if(msg=="success"){
		        	//alert("审核完成！");
		        	//window.location.reload();
		        	window.location.href = '${pageContext.request.contextPath}/reportAudit/auditDetailList.html?id=${batchId }&page.currentPage=${currentPage}';
		        }else{
	            	alert("存在以下问题："+msg);
			    }
	        }
	    });
	}

  	function piliangEdit(){
		var dg = new $.dialog({
			title:'驳回原因',
			id:'data',
			width:550,
			height:450,
			iconTitle:false,
			cover:true,
			maxBtn:false,
			xButton:true,
			resize:false,
			page:'addData.html?id='+pId.value
		});
	  	dg.ShowDialog();
	}
	
	//驳回 
	function refusal(){
    	if(confirm("是否确定进行对此报告驳回操作？")){
    		piliangEdit();
		}
	}
	
	//审核完成
	/*function finishAudit(){
		alert("审核完成！");
	}*/

	//返回上一级
	function backtrack(){
		window.history.go(-1);
		//window.location.reload();
	}
	 
</script>
</html>
