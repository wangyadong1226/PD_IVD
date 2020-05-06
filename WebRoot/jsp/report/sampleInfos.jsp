<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<html lang="true">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>无标题文档</title>
</head>

<body>
<form action="reportShow.html" method="post" name="queryForm" id="queryForm">
<input type="hidden" name="batchId" value="${sample.batchId }" class="scinput" />
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="${ctx}/batch.html">首页</a></li>
    <li><a href="#">报告管理</a></li>
    <li><a href="#">报告下载</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div id="usual1" class="usual"> 
    
  	<div id="tab2" class="tabson">
  	<ul class="seachform">
	    <li><label>&nbsp;</label><input name="" type="button" class="btn" onclick="history.go(-1)" value="返回"></li>
	    <li><label>样本ID</label><input type="text" name="sampleId" value="${sample.sampleId }" class="scinput" /></li>
	    <li><label>孕妇姓名</label><input type="text" name="name" value="${sample.name }" class="scinput" /></li>
    	<ul class="toolbar">
	        <li onclick="doSubmit();"><span onclick="doSubmit();"><img src="../images/t04.png" onclick="doSubmit();" /></span>查询</li>
	        <li onclick="javascript:generateDown();"><span><img src="../images/d07.png" style="width: 24px;" /></span>报告批量下载</li> 
        </ul>
    </ul>
    <table class="tablelist" width="2000px;">
    	<thead>
    	<tr>
    		<th width='30'><input name="sltAll" id="sltAll" type="checkbox" value="" onclick="sltAllreportGeneration()"/></th>
	        <th width="40px;">编号</th>
    		<!-- <th width="80px;">接收日期</th> -->
    		<th width="100px;">样本ID</th>
    		<th width="80px;">孕妇姓名</th>
    		<th width="100px;">送检单位</th>
    		<th width="100px;">住院门诊号</th>
    		<th width="120px;">报告模板</th>
    		<th width="80px;">下载状态</th>
    		<th width="80px;">T13</th>
        	<th width="80px;">T18</th>
        	<th width="80px;">T21</th>
        	<th width="80px;">Z13</th>
        	<th width="80px;">Z18</th>
        	<th width="80px;">Z21</th>
        </tr>
        </thead>
        <tbody>
        
        <c:choose>
			<c:when test="${not empty sampleList}">
				<c:forEach items="${sampleList}" var="item" varStatus="vs">
					<tr>
						<td width='50'><input type="checkbox" name="tbSampleId" id="ids${item.tbSampleId}" value="${item.tbSampleId }"/></td>
						<td>${vs.index+1}</td>
        		<!--<td>${item.recieveDate }</td>-->
        		<c:choose>
				       <c:when test="${item.redo == '1'}">
				             <td><font color=red >${item.sampleId }</font></td>
        					 <!-- <td><font color=red >${fn:split(item.libId, "_")[0]}</font></td> -->
				       </c:when>
				       <c:otherwise>
				              <td>${item.sampleId }</td>
        					 <!--  <td>${fn:split(item.libId, "_")[0]}</td> -->
				       </c:otherwise>
				</c:choose>
        		<td>${item.name }</td>
        		<td>${item.unitFrom }</td>
        		<td>${item.outpatientNum }</td>
        		<td>
        			<select id="reportTemplate${item.tbSampleId}" name="reportTemplate" size="1" style="border: 1px solid #cbcbcb;margin-left: -14px; WIDTH: 130PX;opacity: 1;padding-right: 4px;padding-top: 0px;height: 27px;line-height: 27px;"> 
						<%--<option ${item.reportTemplate=='tonggyong.pdf'?'selected':''} value="tonggyong.pdf">通用模板-带章.pdf</option>  
						<option ${item.reportTemplate=='jiangsusheng.pdf'?'selected':''} value="jiangsusheng.pdf">江苏省人民医院.pdf</option>--%>
						<option value="tonggyong.pdf">&nbsp;&nbsp;&nbsp;&nbsp;通用模板.pdf</option>
					</select>
        		</td>
        		<c:choose>
				       <c:when test="${item.downloadStatus == '0'}">
				             <td><font color=red>未下载</font></td>
				       </c:when>
				       <c:when test="${item.downloadStatus == '1'}">
				             <td><a href="#" onclick="detail(${item.tbSampleId})"><font color="#009900">下载详情</font></a></td>
				       </c:when>
				       <c:otherwise>
				              <td>--</td>
				       </c:otherwise>
				</c:choose>
        		<td><c:if test="${item.t13Name != null  && item.t13Name=='阳性'}"> <font color=red></c:if>
        			<c:if test="${item.t13Name != null  && item.t13Name=='灰区'}"> <font color=FFCC00></c:if>
        			${item.t13Name }</font>
        		</td>
        		<td><c:if test="${item.t18Name != null  && item.t18Name=='阳性'}"> <font color=red></c:if>
        			<c:if test="${item.t18Name != null  && item.t18Name=='灰区'}"> <font color=FFCC00></c:if>
        			${item.t18Name }</font>
        		</td><td><c:if test="${item.t21Name != null  && item.t21Name=='阳性'}"> <font color=red></c:if>
        			<c:if test="${item.t21Name != null  && item.t21Name=='灰区'}"> <font color=FFCC00></c:if>
        			${item.t21Name }</font>
        		</td>
			    <td>${item.z13Name }</td>
				<td>${item.z18Name }</td>
				<td>${item.z21Name }</td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="14">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
        
        </tbody>
    </table>
    ${sample.page.pageStr }
    		</div>  
		</div> 
    </div>
</form>
<form action="generate.html" method="post" name="" id="generateForm">
	<input id="tbSampleIds" type="hidden" name="tbSampleIds" value="">
	<input id="generate_reportTemplates" type="hidden" name="reportTemplates" value="">
</form>
<script type="text/javascript"> 
      	$("#usual1 ul").idTabs(); 
    
		$('.tablelist tbody tr:odd').addClass('odd');
		function detail(sampleId){
		     var dg = new $.dialog({
				title:'下载详情',
				id:'sampleId',
				width:450,
				height:360,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'downloadDetail.html?sampleId='+sampleId
			});
		  	dg.ShowDialog();
		}
		function sltAllreportGeneration(){
			if($("#sltAll").attr("checked")){
				$("input[name='tbSampleId']").attr("checked",true);
			}else{
				$("input[name='tbSampleId']").attr("checked",false);
			}
		}
		//报告批量下载
		function generateDown(){
			var checkedCodes = "";
			var checkedReportTemps = "";
			var checkedObj = $('input:checkbox[name="tbSampleId"]:checked');
			if(checkedObj!=null && checkedObj.length > 0){
				checkedObj.each(function(index,e){
					var ckbValue=$(e).val();
					checkedCodes += ckbValue + ",";
					checkedReportTemps += $('#reportTemplate'+ckbValue).val() + ",";
				 });
			}else {
				alert("请选择一个要生成的记录");
				return;
			}	
			$("#tbSampleIds").val(checkedCodes);
			$("#generate_reportTemplates").val(checkedReportTemps);
			$("#generateForm").submit();
			return checkedObj;
		};
		function doSubmit(){
			$("#queryForm").submit();
			}
	</script>
</body>
</html>