<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域编辑页面</title>
<style type="text/css">
.input_txt{width:160px;height:30px;line-height:20px;border: 1px solid #CCCCCC;}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$(".select3").uedSelect({
		width : 100
	});
});
</script>
</head>
<body style="width:100% !important;min-width:200px !important;">
	<form action="excel.html" name="excelForm" id="excelForm" target="result" method="post" onsubmit="return checkInfo();">
		<table border="0" cellpadding="0" cellspacing="0">
			 <ul class="forminfo" style="padding-top: 20px;"> 
			    <li><label>样本编号:</label>
			   		 <input type="text" name="sampleId" id="sampleId" class="input_txt" />
				</li>
		    </ul>
		    <ul class="forminfo">
			    <li><label>送检日期：</label>
			   		 <input type="text" name="sRecieveDate" id="sRecieveDate" class="input_txt" style="width:107px;"  onclick="WdatePicker()" readonly="readonly"/>
			   		 <input type="text" name="eRecieveDate" id="eRecieveDate" class="input_txt" style="width:107px;"  onclick="WdatePicker()" readonly="readonly"/>
				</li>
		    </ul>		
			<ul class="forminfo">
			    <li><label>报告日期：</label>
			   		 <input type="text" name="sReportDate" id="sReportDate" class="input_txt" style="width:107px;" onclick="WdatePicker()" readonly="readonly"/>
			   		 <input type="text" name="eReportDate" id="eReportDate" class="input_txt" style="width:107px;" onclick="WdatePicker()" readonly="readonly"/>
				</li>
		    </ul>		
		</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>	
	<script type="text/javascript">
		var dg;
		document.onkeydown = function(event){
			var e = event || window.event || arguments.callee.caller.arguments[0];
			if(e && e.keyCode==27){ // Esc 
				dg.cancel();
			}else if(e && e.keyCode==13){ // Enter
				document.forms[0].submit();
			}
		}
		
		$(document).ready(function(){			
			dg = frameElement.lhgDG;
			dg.addBtn('ok','导出',function(){
				$('#excelForm').submit();
			});
		});
		
		function success(){
			if(dg.curWin.document.forms[0]){
				dg.curWin.document.forms[0].action = dg.curWin.location+"";
				dg.curWin.document.forms[0].submit();
			}else{
				dg.curWin.location.reload();
			}
			dg.cancel();
		}

		function failed(){
			if(dg.curWin.document.forms[0]){
				dg.curWin.location.reload();
			}
			dg.cancel();
		}
	</script>
</body>
</html>