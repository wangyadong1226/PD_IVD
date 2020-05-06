<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>无标题文档</title>
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
			
				$(".select3").uedSelect({
					width : 100
				});
			});
		</script>
	</head>
	<body>
	<form action="saveReagents.html" name="reagentsForm" id="reagentsForm" method="post">
		<div class="place">
		    <span>位置：</span>
		    <ul class="placeul">
				<li><a href="${ctx}/batch.html">首页</a></li>
			    <li><a href="${ctx}/batch.html">批次管理</a></li>
			    <li><a href="${ctx}/batch.html">批次信息</a></li>
			    <li><a href="#">试剂信息</a></li>
		    </ul>
		</div>
		    <DIV id="zk_result" style="background: rgb(170, 237, 243); width: 98%; height: 20px; color: rgb(88, 126, 127); line-height: 20px; padding-left: 2%; font-weight: 600;"></DIV>
			<div class="formtitle"><span>${item.batchNum}&nbsp;&nbsp;信息:</span></div>
			<DIV id="inbox"	style="width: 98%; padding-left: 10%;">
				<INPUT name="batchId" id="batchId" type="hidden" value="${item.batchId}"/>
				<TABLE  id="table" class="tab_td" style="width: 50%; height: 150px;">
					<TBODY>
						<TR>
						<c:if test="${item.construction eq null}">
							<TD style="width: 40%; ">
								<label>文库试剂:&nbsp;&nbsp;</label>
								<input name="construction" id="construction" class="scinput" value=""/>
							</TD>
						</c:if>
						<c:if test="${item.construction ne null}">
							<TD style="width: 40%; ">
								<label>文库试剂:&nbsp;&nbsp;</label>
								${item.construction }
							</TD>
							<TD style="width: 30%; ">
								批号 : ${reagebt[0] }
							</TD>
							<TD style="width: 30%; ">
								有效日期 : ${date[0] }
							</TD>
						</c:if>
					</TR>
					<TR>
						<c:if test="${item.purification eq null }">
							<TD>
								<label>核酸纯化:&nbsp;&nbsp;</label>
								<input name="purification" id="purification" class="scinput" value="${item.purification }"/>
							</TD>
						</c:if>
						<c:if test="${item.purification ne null}">
							<TD>
								<label>核酸纯化:&nbsp;&nbsp;</label>
								${item.purification }
							</TD>
							<TD>
								批号  : ${reagebt[1] }
							</TD>
							<TD>
								有效日期 : ${date[1] }
							</TD>
						</c:if>
						
						
					</TR>
					<TR>
						
						<c:if test="${item.extraction eq null}">
							<TD>
								<label>核酸提取:&nbsp;&nbsp;</label>
								<input name="extraction" id="extraction" class="scinput" value="${item.extraction }"/>
							</TD>
						</c:if>
						<c:if test="${item.extraction ne null }">
							<TD>
								<label>核酸提取:&nbsp;&nbsp;</label>
								${item.extraction }
							</TD>
							<TD>
								批号 : ${reagebt[2] }
							</TD>
							<TD style=" letter-spacing: 0.2px;">
								有效日期 : ${date[2] }
							</TD>
						</c:if>
					</TR>
					<TR>
						
						<c:if test="${item.sequencing eq null }">
							<TD style="">
								<label>测序试剂:&nbsp;&nbsp;</label>
								<input name="sequencing" id="sequencing" class="scinput" value="${item.sequencing }"/>
							</TD>
						</c:if>
						<c:if test="${item.sequencing ne null }">
							<TD>
								<label>测序试剂:&nbsp;&nbsp;</label>
								${item.sequencing }
							</TD>
							<TD style="">
								批号 : ${reagebt[3] }
							</TD>
							<TD>
								有效日期  : ${date[3] }
							</TD>
						</c:if>
					</TR>
				</TBODY>
			</TABLE>
		<br/>
		</DIV>
		<label style="font-weight: bold;color: green;margin-left: 30px;">&nbsp;&nbsp;文库示例&nbsp;&nbsp;:&nbsp;&nbsp;EP001170401_20180419&nbsp;或&nbsp;EP001170501_20180519</label> <br/><br/>
		<label style="font-weight: bold;color: green;margin-left: 30px;">试剂组合示例1&nbsp;&nbsp;:&nbsp;&nbsp;EP001170401_EP019170401_EP014170401_EP004170401</label><br/><br/>
		<label style="font-weight: bold;color: green;margin-left: 30px;">试剂组合示例2&nbsp;&nbsp;:&nbsp;&nbsp;EP001170501_EP019170501_EP035170604_EP004170502</label>
		<DIV id="change_check"
			style="background: rgb(214, 231, 251); width: 98%; height: 32px; padding-left: 5%; margin-top: 13px; margin-bottom: 0px; -margin-top: 1px;">
			<c:if test="${item.combinationState ne 1 }">
				<DIV id="check_suc"
					style=" border-width: 0px; border-style: solid; border-radius: 11px; width: 9%; color: rgb(255, 255, 255); margin-top: 0px; float: left; ">
					<ul class="forminfo">
					    <li><input type="button" id="" style="width:137px;height:35px; background:green; no-repeat; font-size:14px;font-weight:bold;color:white; cursor:pointer;" 
					    onclick="save()" value="保存"></li>
					</ul>
				</DIV>
			</c:if>
			<DIV id="check_fail"
				style="border-width: 0px; border-style: solid; border-radius: 11px; width: 9%; color: rgb(255, 255, 255); margin-top: 0px; margin-left: 300px; float: left; ">
				<ul class="forminfo">
				    <li><input name="" type="button" style="width:137px;height:35px; background:green; no-repeat; font-size:14px;font-weight:bold;color:white; cursor:pointer;" 
				     onclick="javaScript:history.go(-1)" value="返回"></li>
				</ul>
			</DIV>
		</DIV>
								
	</form>
	<script type="text/javascript">
			function save(){
				var batchId=$('#batchId').val();
				var construction=$.trim($('#construction').val());
				var purification=$.trim($('#purification').val());
				var extraction=$.trim($('#extraction').val());
				var sequencing=$.trim($('#sequencing').val());
				console.log(construction.length);
				if(construction.length!=20){
					alert("文库试剂格式错误");
					$('#construction').focus();
					return ;
					}
				if(purification.length!=20){
					alert("核酸纯化格式错误");
					$('#purification').focus();
					return ;
					}
				if(extraction.length!=20){
					alert("核酸提取格式错误");
					$('#extraction').focus();
					return ;
					}
				if(sequencing.length!=20){
					alert("测序试剂格式错误");
					$('#sequencing').focus();
					return ;
					}
				//$('#reagentsForm').submit();
				$.ajax({
					url:"${pageContext.request.contextPath}/batch/saveReagents.html",
					type:'post',
					async:false,
					data:{'construction':construction,'purification':purification,'extraction':extraction,'sequencing':sequencing,'batchId':batchId},
					dataType:'text',
					success:function(data){
						if(data == 'success'){
							window.location.href="${pageContext.request.contextPath}/batch.html";
						}else{
							alert(data);
							}
					}
				});
				}
			
		</script>
	</BODY>
</html>
