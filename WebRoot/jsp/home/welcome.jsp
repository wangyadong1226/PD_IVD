<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<script type="text/javascript">
		/**
		gvChartInit();
		jQuery(document).ready(function(){

		jQuery('#myTable5').gvChart({
				chartType: 'PieChart',
				gvSettings: {
					vAxis: {title: 'No of players'},
					hAxis: {title: 'Month'},
					width: 650,
					height: 250
					}
			});
		});
		**/
</script>
</head>


<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">工作台</a></li>
    </ul>
    </div>
    
    
    <div class="mainbox">
    
    <div class="mainleft">    
    
    <div class="listtitle"><a href="${ctx }/contract.html" class="more1">更多</a>项目详情</div>
        
   <div style="padding:0px;">
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
	        <th>合同编号</th>
	        <th>项目名称</th>
	        <th>项目类型</th>
			<th>项目启动日期</th>
			<th>项目状态</th>
			<th>项目紧急级别</th>
			<!-- 
			<th>操作</th>
			 -->
        </tr>
        </thead>
        <tbody>
        <c:choose>
			<c:when test="${not empty projectList}">
				<c:forEach items="${projectList}" var="project" varStatus="vs">
				<tr class="main_info" id="tr${project.projectId }">
				<td>${project.contractId }</td>
				<td>${project.projectName }</td>
				<td>${project.projectTypeId }</td>
				<td><fmt:formatDate value="${project.projectStartDate }" pattern="yyyy-MM-dd"/></td>
				<td>${project.projectState }</td>
				<td>${project.projectDifference }</td>
				<!-- 
				<td><a href="###" onclick="editProject(${project.projectId })">修改</a> | 
				<a href="###" onclick="delProject(${project.projectId },true)">删除</a> | 
				<a href="###" onclick="toProjectAdmin(${project.projectId },true)">管理项目</a></td>
				 -->
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="6">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
        
        </tbody>
    </table>
    </div> 
    
    <!--leftinfo end-->
    
    
    </div>
    <!--mainleft end-->
    
    
    <div class="mainright">
    
    <!-- 
    <div style="border:#d3dbde solid 1px; width:288px; float:right;">
    <div class="listtitle"><a href="#" class="more1">更多</a>待办事项</div>    
    <ul class="newlist">
    <li><a href="#" onclick="show2()">待办事项1</a></li>
    <li><a href="#">待办事项2</a></li>
    <li><a href="#">待办事项3</a></li>
    <li><a href="#">待办事项1</a></li>
    <li><a href="#">待办事项2</a></li>
    <li><a href="#">待办事项3</a></li>
    <li><a href="#">待办事项1</a></li>
    <li><a href="#">待办事项2</a></li>
    <li><a href="#">待办事项3</a></li>
    <li><a href="#">待办事项1</a></li>
    <li><a href="#">待办事项2</a></li>
    <li><a href="#">待办事项3</a></li>
    <li><a href="#">待办事项1</a></li>
    <li><a href="#">待办事项2</a></li>
    <li><a href="#">待办事项3</a></li>
    <li><a href="#">待办事项1</a></li>
    <li><a href="#">待办事项2</a></li>
    <li><a href="#">待办事项3</a></li>
    <li><a href="#">待办事项1</a></li>
    <li><a href="#">待办事项2</a></li>
    <li><a href="#">待办事项3</a></li>
    </ul>        
    </div>
     -->
    <div style="border:#d3dbde solid 1px; width:288px; float:right;">
    <div class="listtitle"><a href="#" class="more1">更多</a>待办事项</div>    
    <ul class="newlist">
    	<c:forEach items="${todoList}" var="todo" varStatus="vs">
    		<li><a href="${pageContext.request.contextPath}/${todo.url}">${todo.title }</a></li>
    	</c:forEach>
    </ul>
    </div>
    
    

    
    
    </div>
    <!--mainright end-->
    
    
    </div>



</body>
<script type="text/javascript">
	setWidth();
	$(window).resize(function(){
		setWidth();	
	});
	function setWidth(){
		var width = ($('.leftinfos').width()-12)/2;
		$('.infoleft,.inforight').width(width);
	}
</script>
</html>
