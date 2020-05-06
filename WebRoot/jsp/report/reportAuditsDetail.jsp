<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%--<script src="${ctx }/validate.js" ></script>
<script src="${ctx }/icommon.js" ></script>--%>
<html lang="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${ctx }/css/styleForybcx.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx }/css/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/css/common.css"/>
<script type="text/javascript" src="${ctx }/js/jquery.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="${ctx }/js/select-ui.min.js"></script>
<script type="text/javascript" src="${ctx }/editor/kindeditor.js"></script>
<script type="text/javascript" src="${ctx }/js/showdate.js"></script>

	<script>
		
		function doSubmit(){
			$("#queryForm").submit();
		}
		
		function doSubmitForExcel(){
			var path = "${ctx}/sample/exportExcelForBatch.html";  
   			$('#queryForm').attr("action", path).submit(); 
		}
		
		
	</script>
	<style type="text/css">
		.table-head{background-color:#999;color:#000;}
		.table-body{width:100%;}
		.table-head table,.table-body table{width:100%;}
		.table-body table tr:nth-child(2n+1){background-color:#f2f2f2;}
		.tablebodyparent{max-height:unset;}
		.seachform{
		    display: flex;
    		justify-content: flex-start;
    		align-items: center;
    		flex-wrap: wrap;
    		height: unset;
		}
		.seachform li {
    		margin-bottom: 10px;
		}
	</style>
</head>

<body>
<form action="auditDetailList.html" method="post" name="queryForm" id="queryForm">
<input type="hidden" name="id" value="${sample.batchId }" class="scinput" />
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="${ctx}/batch.html">首页</a></li>
    <li><a href="#">报告管理</a></li>
    <li><a href="#">报告审核</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    
    <div id="usual1" class="usual"> 
    
  	<div id="tab2" class="tabson">
  	<ul class="seachform">
	    <li><input name="" type="button" class="btn" onclick="window.location.href='${ctx }/reportAudit.html'" value="返回"></li>
	    <li><label>样本ID</label><input type="text" name="sampleId" value="${sample.sampleId }" class="scinput" /></li>
	    <li><label>孕妇姓名</label><input type="text" name="name" value="${sample.name }" class="scinput" /></li>
	    <li><label>T13</label>
	    	<select name="tbRes13" id="tbRes13" class="scinput" style="width:90px" >
		    	<option value="">全部</option>
		    	<option value="low_risk" <c:if test="${sample.tbRes13=='low_risk'}"> selected="selected"</c:if>>低风险</option>
	    		<option value="T13" <c:if test="${sample.tbRes13=='T13'}"> selected="selected"</c:if>>高风险</option>
		    </select>  
		</li>
		<li><label>T18</label>
	    	<select name="tbRes18" id="tbRes18" class="scinput" style="width:90px" >
		    	<option value="">全部</option>
		    	<option value="low_risk" <c:if test="${sample.tbRes18=='low_risk'}"> selected="selected"</c:if>>低风险</option>
	    		<option value="T18" <c:if test="${sample.tbRes18=='T18'}"> selected="selected"</c:if>>高风险</option>
		    </select>  
		</li>
		<li><label>T13</label>
	    	<select name="tbRes21" id="tbRes21" class="scinput" style="width:90px" >
		    	<option value="">全部</option>
		    	<option value="low_risk" <c:if test="${sample.tbRes21=='low_risk'}"> selected="selected"</c:if>>低风险</option>
	    		<option value="T21" <c:if test="${sample.tbRes21=='T21'}"> selected="selected"</c:if>>高风险</option>
		    </select>  
		</li>
    	<ul class="toolbar">
	        <li onclick="doSubmit();"><span onclick="doSubmit();"><img src="../images/t04.png" onclick="doSubmit();" /></span>查询</li>
	        <li onclick="javascript:auditBatch();"><span><img src="../images/d07.png" style="width: 24px;" /></span>批量审核</li> 
        </ul>
        <!-- <ul class="toolbar">
   	     	<li onclick="doSubmitForExcel()"><span onclick="doSubmitForExcel()"><img src="../images/t05.png" onclick="doSubmitForExcel()" /></span>导出Excel</li>
    	</ul> -->
    </ul>

		<div style="width: 100%">

			<div class="table-head">
				<table  class="tablelist tablelisthead">
					<thead >
					<tr>
						<th >编号</th>
						<!-- <th width="80px;">接收日期</th> -->
						<th width="12%">样本ID</th>
						<th >孕妇姓名</th>
						<th >送检单位</th>
						<th >住院门诊号</th>
						<th >T13</th>
						<th >T18</th>
						<th >T21</th>
						<th >Z13</th>
						<th >Z18</th>
						<th >Z21</th>
						<th >操作</th>
						<th class="collast">审核状态</th>
					</tr>
					</thead>

				</table>
			</div>

			<div class="table-body tablebodyparent">
				<table class="tablelist tablelistbody">
					<c:choose>
						<c:when test="${not empty sampleList}">
							<c:forEach items="${sampleList}" var="item" varStatus="vs">
								<tr>
									<td >${vs.index+1}</td>
									<!--<td>${item.recieveDate }</td>-->
									<c:choose>
										<c:when test="${item.redo == '1'}">
											<td width="12%"><font color=red >${item.sampleId }</font></td>
											<!-- <td><font color=red >${fn:split(item.libId, "_")[0]}</font></td> -->
										</c:when>
										<c:otherwise>
											<td width="12%">${item.sampleId }</td>
											<!--  <td>${fn:split(item.libId, "_")[0]}</td> -->
										</c:otherwise>
									</c:choose>

									<td>${item.name }</td>
									<td>${item.unitFrom }</td>
									<td>${item.outpatientNum }</td>
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
									<c:choose>
										<c:when test="${item.auditStatus == '0'}">
											<td> <a href="javascript:detailsAudit('${item.tbSampleId}');" class="tablelink">审核</a></td>
											<td><font color=red>未审核</font></td>
										</c:when>
										<c:when test="${item.auditStatus == '1'}">
											<td><a href="javascript:detailsAudit('${item.tbSampleId}');" class="tablelink">详情</a></td>
											<td><font color="#009900">已审核</font></td>
										</c:when>
										<c:otherwise>
											<td><a href="javascript:detailsAudit('${item.tbSampleId}');" class="tablelink">详情</a></td>
											<td>--</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="13">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>

				</table>
			</div>

			</div>



   <%-- <table class="tablelist" width="2000px;">
    	<thead >
    	<tr>
    		<th >编号</th>
    		<!-- <th width="80px;">接收日期</th> -->
    		<th width="90px;">样本ID</th>
    		<th width="80px;">孕妇姓名</th>
    		<th width="80px;">送检单位</th>
    		<th width="90px;">住院门诊号</th>
    		<th width="60px;">T13</th>
        	<th width="60px;">T18</th>
        	<th width="60px;">T21</th>
        	<th width="80px;">Z13</th>
        	<th width="80px;">Z18</th>
        	<th width="80px;">Z21</th>
        	<th width="90px;">操作</th>
    		<th width="80px;">审核状态</th>
        </tr>
        </thead>
        <tbody>
        
        <c:choose>
			<c:when test="${not empty sampleList}">
				<c:forEach items="${sampleList}" var="item" varStatus="vs">
				<tr>
				<td >${vs.index+1}</td>
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
        		<c:choose>
				       <c:when test="${item.auditStatus == '0'}">
				             <td> <a href="javascript:detailsAudit('${item.tbSampleId}');" class="tablelink">审核</a></td>
				             <td><font color=red>未审核</font></td>
				       </c:when>
				       <c:when test="${item.auditStatus == '1'}">
				             <td><a href="javascript:detailsAudit('${item.tbSampleId}');" class="tablelink">详情</a></td>
				             <td><font color="#009900">已审核</font></td>
				       </c:when>
				       <c:otherwise>
				              <td><a href="javascript:detailsAudit('${item.tbSampleId}');" class="tablelink">详情</a></td>
				              <td>--</td>
				       </c:otherwise>
				</c:choose>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="13">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
        
        </tbody>
    </table>--%>
    ${sample.page.pageStr }
    </div>  
       
	</div> 
	<script type="text/javascript"> 
      	$("#usual1 ul").idTabs(); 
    
		$('.tablelist tbody tr:odd').addClass('odd');

		//详情完成审核
		function detailsAudit(sampleId){
			window.location.href="detailsAudit.html?tbSampleId="+sampleId+"&currentPage=${sample.page.currentPage }";
		}
		//批量审核
		function auditBatch(){
			$.ajax({
		        type: "POST",
		        url: "auditStatusVerify.html",
		        async: false,
		        data: {"batchId":${sample.batchId }},
		        dataType: "html",
		        success: function (msg) {
			        if(msg=="success"){
						//执行批量审核
			        	if(confirm("是否确定进行批量审核全部报告？")){
			    			$.ajax({
			    		        type: "POST",
			    		        url: "batchReview.html",
			    		        data: {"batchId":${sample.batchId }},
			    		        dataType: "html",
			    		        success: function (msg) {
			    			        if(msg=="success"){
			    			        	//alert("批量审核完成！");
			    			        	window.location.reload();
			    			        }else{
			    		            	alert("存在以下问题："+msg);
			    				    }
			    		        }
			    		   });
			    		}
			        }else if(msg=="gerror"){
			        	alert("高风险必须全部审核完成，才可以进行批量审核，请审核！");
				    }else if(msg=="derror"){
				    	alert("低风险必须审核一条，才可以进行批量审核，请审核！");
				    }else{
		            	alert("存在以下问题："+msg);
				    }
		        }
		    });
		}
	</script>
    </div>
</form>
</body>
</html>