<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
$(document).ready(function(){
  	$(".click").click(function(){
  		$(".tip").fadeIn(200);
  	});
  	
  	$(".tiptop a").click(function(){
  		$(".tip").fadeOut(200);
	});

  	$(".sure").click(function(){
  		$(".tip").fadeOut(100);
	});

  	$(".cancel").click(function(){
  		$(".tip").fadeOut(100);
	});
});
</script>
<html>
  <head>
    <base href="<%=basePath%>">
	<style>
	  	#lhgdlg_sampleStracking_data{top:0px !important;  	}
		.form {border-collapse: collapse;width:700px; margin-bottom: 50px;border: dotted 1px #c7c7c7;height:80px;}
		<%--.form td{width:90px;padding:5px;border:0px solid green;text-align:left;}--%>
		.form th{ height:30px; line-height:40px; text-indent:11px;text-align:right;width:110px;}
		.form td{line-height:20px; text-indent:11px; border-right: dotted 1px #c7c7c7;width:130px;font-size: 13px;}
		.form label{font-size: 13px}
		.scinput{width:110px;}
  	</style>
  </head>
  
  <body style="min-width:80px !important;">
  <form action="save.html" id="excelForm" name="excelForm" method="post">
  <input type="hidden" name="sampleId" id="sampleId" value="${sample.tbSampleId }"/>
	  <div class="rightinfo">
		<div class="formtitle"><span>样本信息</span></div>
	    <table class="form">
    		<tr>	 	
		  	 	<th><label>孕妇姓名 :</label></th>
		  	 	<td>
		  	 		<input type="text" name="name" id="name" value="${sample.name}" class="scinput"/>
		  	 	</td>
		  	 	<th><label>住院门诊号 :</label></th>
		  	 	<td>
		  	 		<input type="text" name="outpatientNum" id="outpatientNum" value="${sample.outpatientNum}" class="scinput"/>
		  	 	</td>
		  	 	<th><label>样本编号 :</label></th><td>AB${sample.sampleId }</td>
	    	 </tr>
		  	 <tr>
	    		<th><label>孕妇年龄 :</label></th>
				<td>
		  	 		<input type="text" name="age" id="age" value="${sample.age}" class="scinput"/>
		  	 	</td>
		  	 	<th><label>末次月经 :</label></th>
		  	 	<td>
		  	 		<input type="text" name="lastPeriod" id="lastPeriod" value="${sample.lastPeriod}" class="scinput" onclick="WdatePicker()"/>
		  	 	</td>
	    		<th><label>样本类型 :</label></th>
	    		<td>
		  	 		<input type="text" name="sampleType" id="sampleType" value="${sample.sampleType }" class="scinput"/>
		  	 	</td>
		  	 </tr>
		  	 <tr>
	    		<th><label>孕周 :</label></th>
	    		<td>
		  	 		<input type="text" name="gesWeek" id="gesWeek" value="${sample.gesWeek }" class="scinput"/>
		  	 	</td>
		  	 	<th><label>孕产史 :</label></th>
		  	 	<td>
		  	 		<input type="text" name="pregHistory" id="pregHistory" value="${sample.pregHistory}" class="scinput"/>
		  	 	</td>
		  	 	<th><label>样本状态 :</label></th><td>正常</td>
	    	 </tr>
		  	 <tr>
		  	 </tr>
		  	 <tr>
	    		<th><label>IVF-ET妊娠 :</label></th>
	    		<td>
		  	 		<input type="text" name="ivfGestation" id="ivfGestation" value="${sample.ivfGestation}"  class="scinput" style="width:110px"/>
		  	 	</td>
		  	 	<th><label>双胎/多胎妊娠 :</label></th>
		  	 	<td>
		  	 		<select name="fetusNum" id="fetusNum" class="scinput">
		  	 			<option value="单胎" <c:if test="${sample.fetusNum=='单胎'}"> selected="selected"</c:if>>单胎</option>
		  	 			<option value="多胎" <c:if test="${sample.fetusNum=='多胎'}"> selected="selected"</c:if>>多胎</option>
		  	 		</select>
		  	 	</td>
		  	 	<th><label>采样日期 :</label></th>
		  	 	<td>
		  	 		<input type="text" name="samplingDate" id="samplingDate" value="${sample.samplingDate}" class="scinput" onclick="WdatePicker()"/>
		  	 	</td>
	    	 </tr>
		  	 <tr>
	    		<th><label>临床诊断 :</label></th>
	    		<td>
		  	 		<input type="text" name="diagnosis" id="diagnosis" value="${sample.diagnosis}" class="scinput" />
		  	 	</td>
	    		<th><label>送检医生 :</label></th>
	    		<td>
		  	 		<input type="text" name="doctorFrom" id="doctorFrom" value="${sample.doctorFrom}" class="scinput"/>
		  	 	</td>
	    		<th><label>接收日期 :</label></th>
	    		<td>
		  	 		<input type="text" name="recieveDate" id="recieveDate" value="${sample.recieveDate }" class="scinput" onclick="WdatePicker()"/>
		  	 	</td>
		  	 </tr>
    	</table>
	  </div>
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
			dg.addBtn('ok','保存',function(){
				var sampleId=$('#sampleId').val();
				var name=$.trim($('#name').val());
				var age=$.trim($('#age').val());
				var outpatientNum=$.trim($('#outpatientNum').val());
				var gesWeek=$.trim($('#gesWeek').val());
				var sampleType=$.trim($('#sampleType').val());recieveDate
				var recieveDate=$.trim($('#recieveDate').val());
				var doctorFrom=$.trim($('#doctorFrom').val());
				var diagnosis=$.trim($('#diagnosis').val());
				var samplingDate=$.trim($('#samplingDate').val());
				var fetusNum=$.trim($('#fetusNum').val());
				var ivfGestation=$.trim($('#ivfGestation').val());
				var pregHistory=$.trim($('#pregHistory').val());
				var lastPeriod=$.trim($('#lastPeriod').val());
				<%--
				if(name==''){
					alert("请输入孕妇姓名");
					$('#name').focus();
					return;
					}
				if(age==''){
					alert("请输入年龄");
					$('#age').focus();
					return;
					}else if(isNaN(age)){
						alert("年龄格式错误");
						$('#age').focus();
						return;
						}
				if(outpatientNum==''){
					alert("请输入门诊号");
					$('#outpatientNum').focus();
					return;
					}
				if(gesWeek==''){
					alert("请输入孕周");
					$('#gesWeek').focus();
					return;
					}
				if(sampleType==''){
					alert("请输入样本类型");
					$('#sampleType').focus();
					return;
					}
				if(lastPeriod==''){
					alert("请输入末次月经");
					$('#lastPeriod').focus();
					return;
					}
				if(pregHistory==''){
					alert("请输入孕产史");
					$('#pregHistory').focus();
					return;
					}else if(pregHistory.length!=6){
						alert("孕产史格式为'孕N次产N次'");
						$('#pregHistory').focus();
						return;
						}
				if(ivfGestation==''){
					alert("请输入IVF-ET妊娠");
					$('#ivfGestation').focus();
					return;
					}
				if(fetusNum==''){
					alert("请选择单胎/多胎");
					$('#fetusNum').focus();
					return;
					}
				if(samplingDate==''){
					alert("请输入采样日期");
					$('#samplingDate').focus();
					return;
					}
				if(diagnosis==''){
					alert("请输入临床诊断");
					$('#diagnosis').focus();
					return;
					}
				if(doctorFrom==''){
					alert("请输入送检医生");
					$('#doctorFrom').focus();
					return;
					}
				if(recieveDate==''){
					alert("请输入接收日期");
					$('#recieveDate').focus();
					return;
					}
				--%>
				$.ajax({
					url:"${pageContext.request.contextPath}/clinicalInfo/save.html",
					type:'post',
					async:false,
					data:{'sampleId':sampleId,'name':name,'age':age,'outpatientNum':outpatientNum,'gesWeek':gesWeek,'sampleType':sampleType,
						'lastPeriod':lastPeriod,'pregHistory':pregHistory,'ivfGestation':ivfGestation,'fetusNum':fetusNum,
						'samplingDate':samplingDate,'diagnosis':diagnosis,'doctorFrom':doctorFrom,'recieveDate':recieveDate},
					dataType:'text',
					success:function(data){
						if(data == 'success'){
							window.parent.location.href="${pageContext.request.contextPath}/clinicalInfo.html";
						}else if(data == 'fail'){
							alert("请确认是否改动信息！");
						}else {
							alert(data);
							}
					}
				});
				//$('#excelForm').submit();
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
