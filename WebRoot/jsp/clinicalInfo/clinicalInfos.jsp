<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="${ctx }/css/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/css/common.css"/>
	<style type="text/css">
		.table-head{background-color:#999;color:#000;}
		.table-body{width:100%;}
		.table-head table,.table-body table{width:101%;}
		.table-body table tr:nth-child(2n+1){background-color:#f2f2f2;}
	</style>
</head>
<body>
<form action="${ctx}/clinicalInfo.html" method="post" name="reportGenerationForm" id="reportGenerationForm">
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="${ctx}/batch.html">首页</a></li>
    <li><a href="#">报告管理</a></li>
    <li><a href="#">样本信息</a></li>
    </ul>
    </div>
    <div class="rightinfo">
    <div class="tools">
    	<ul class="seachform">
    		<li><label>样本编号：</label><input type="text" id='sampleId' name="sampleId" value="${sample.sampleId }" class="scinput" /></li>
	    	<ul class="toolbar">
		        <li onclick="javascript:search();"><span><img src="images/t04.png" /></span>查询</li> 
		    </ul>
    	</ul>
    </div>

		<div style="width: 100%">
			<div class="table-head">
				<table  class="tablelist tablelisthead">
					<thead>
					<tr>
						<th width='50'>序号</th>
						<th>送检日期</th>
						<th>样本编号</th>
						<th>孕妇姓名</th>
						<th>年龄</th>
						<th>门诊号/住院号</th>
						<th>孕周</th>
						<th>样本类型</th>
						<th>报告生成日期</th>
						<th>操作</th>
					</tr>
					</thead>
				</table>
			</div>


			<div class="table-body tablebodyparent">
				<table class="tablelist tablelistbody">
					<c:choose>
						<c:when test="${not empty sampleList}">
							<c:forEach items="${sampleList}" var="sample" varStatus="vs">
								<tr class="main_info" id="tr${sample.tbSampleId}">
									<td width='50'>${vs.index+1}</td>
									<td>${sample.recieveDate}</td>
									<td>AB${sample.sampleId}</td>
									<td>${sample.name}</td>
									<td>${sample.age}</td>
									<td>${sample.outpatientNum}</td>
									<td>${sample.gesWeek}</td>
									<td>${sample.sampleType}</td>
									<td>${sample.reportDate}</td>
									<td><a href="#" onclick="edit(${sample.tbSampleId})"><font color="#009900">修改</font></a>&nbsp;|&nbsp;
										<a href="#" onclick="window.location.href='${ctx}/clinicalInfo/editDetail.html?sampleId=${sample.tbSampleId}'"><font color="#009900">详情</font></a></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="12">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</div>

		</div>



   <%-- <table class="tablelist">
    	<thead>
    	<tr>
	        <th width='50'>序号<i class="sort"><img src="images/px.gif" /></i></th>
	        <th>送检日期</th>
	        <th>样本编号</th>
	        <th>孕妇姓名</th>
	        <th>年龄</th>
	        <th>门诊号/住院号</th>
	        <th>孕周</th>
	        <th>样本类型</th>
	        <th>报告生成日期</th>
	        <th>操作</th>
        </tr>
        </thead>
        <tbody>
	        <c:choose>
				<c:when test="${not empty sampleList}">
					<c:forEach items="${sampleList}" var="sample" varStatus="vs">
					<tr class="main_info" id="tr${sample.tbSampleId}">
						<td width='50'>${vs.index+1}</td> 
						<td>${sample.recieveDate}</td> 
						<td>AB${sample.sampleId}</td>
						<td>${sample.name}</td>
						<td>${sample.age}</td>
						<td>${sample.outpatientNum}</td>
						<td>${sample.gesWeek}</td>
						<td>${sample.sampleType}</td>
						<td>${sample.reportDate}</td>
				       <td><a href="#" onclick="edit(${sample.tbSampleId})"><font color="#009900">修改</font></a>&nbsp;|&nbsp;
						   <a href="#" onclick="window.location.href='${ctx}/clinicalInfo/editDetail.html?sampleId=${sample.tbSampleId}'"><font color="#009900">详情</font></a></td>
					</tr>
					</c:forEach>
				</c:when>
			<c:otherwise>
				<tr>
					<td colspan="12">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
        </tbody>
    </table>--%>
    ${sample.page.pageStr}
    </div>
 </form>   
<script type="text/javascript">
   	$('.tablelist tbody tr:odd').addClass('odd');
   	//查询
   	function search(){
		$("#reportGenerationForm").submit();
	}
	$(document).ready(function(){
		$(".main_info:even").addClass("main_table_even");
	});
	
	function sltAllreportGeneration(){
		if($("#sltAll").attr("checked")){
			$("input[name='id']").attr("checked",true);
		}else{
			$("input[name='id']").attr("checked",false);
		}
	}
	function edit(sampleId){
		
	     var dg = new $.dialog({
			title:'样本信息详情',
			id:'info',
			width:1000,
			height:400,
			iconTitle:false,
			cover:true,
			maxBtn:false,
			xButton:true,
			resize:false,
			page:'${ctx}/clinicalInfo/edit.html?sampleId='+sampleId
		});
	  	dg.ShowDialog();
	}
	//导出
	function exportReport(){
		var dg = new $.dialog({
			title:'报告结果跟踪表导出',
			id:'reportGeneration_export',
			width:400,
			height:360,
			iconTitle:false,
			cover:true,
			maxBtn:false,
			xButton:true,
			resize:false,
			page:'reportGeneration/getExcel.html'
		});
	  	dg.ShowDialog();
	}
</script>
</body>
</html>