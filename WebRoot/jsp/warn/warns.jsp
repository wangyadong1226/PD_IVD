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
<form action="user.html" method="post" name="userForm" id="userForm">
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li>首页</li>
    <li>预警管理</li>
    <li>预警处理</li>
    </ul>
    </div>

    <div class="rightinfo">
    
    
    
    <div class="tools">
    
    	<ul class="seachform">
    	
    		<li><label>预警级别：</label>
				<div class="vocation">
					<select name="level" class="select3" style="margin-left:3px;">
						<option value="">请选择</option>
						<option value="${warn.level}" >黄色</option>
						<option value="${warn.level}" >橙色</option>
						<option value="${warn.level}" >红色</option>
					</select>
				</div>
			</li>
			<li><label>处理状态：</label>
				<div class="vocation">
					<select name="status" class="select3" style="margin-left:3px;">
						<option value="">请选择</option>
						<option value="${warn.status }" <c:if test="${warn.status==0}">selected</c:if>>未处理</option>
						<option value="${warn.status }" <c:if test="${warn.status==1}">selected</c:if>>已上报</option>
						<option value="${warn.status }" <c:if test="${warn.status==2}">selected</c:if>>已忽略</option>
					</select>
				</div>
			</li>
		   <li>
			   <label> 处理时间：</label>
			   <input type="text" name="lastLoginStart" id="lastLoginStart" class="scinput" value="<fmt:formatDate value="${user.lastLoginStart}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'lastLoginEnd\')}' })" readonly="readonly"/>-
			   <input type="text" name="lastLoginEnd" id="lastLoginEnd" class="scinput" value="<fmt:formatDate value="${user.lastLoginEnd}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate:'#F{$dp.$D(\'lastLoginStart\')}' })" readonly="readonly" />
		   </li>
    
	    	<ul class="toolbar">
	        	<li onclick="javascript:search();"><span><img src="images/t04.png" /></span>查询</li>
	        </ul>
	        
    	</ul>
    </div>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
	        <th width="50"><input name="sltAll" id="sltAll" type="checkbox" value="" onclick="sltAllUser()"/></th>
	        <th width="50">编号<i class="sort"><img src="images/px.gif" /></i></th>
	        <th width="250">预警名称</th>
			<th width="100">阈值</th>
			<th width="100">不合格率</th>
			<th width="100">预警级别</th>
			<th width="100">处理状态</th>
			<th width="100">创建时间</th>
			<th width="100">处理时间</th>
			<th width="100">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
			<c:when test="${not empty warnList}">
				<c:forEach items="${warnList}" var="warn" varStatus="vs">
				<tr>
				<td><input type="checkbox" name="userIds" id="userIds${warn.id }" value="${warn.id }"/></td>
				<td>${vs.index+1}</td>
				<td>${warn.name }</td>
				<td>${warn.propotion }</td>
				<td>${warn.rate }</td>
				<td>${warn.level}</td>
				<td>
					<c:if test="${warn.status==0 }">未处理</c:if>
					<c:if test="${warn.status==1 }">已预警</c:if>
					<c:if test="${warn.status==2 }">已忽略</c:if>
				</td>
				<td><fmt:formatDate value="${warn.createTime}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${warn.dealTime}" pattern="yyyy-MM-dd"/></td>
				<td>
					<a href="#" class="tablelink" onclick="updateWarn(${warn.id },1)"> 预警</a> |
					<a href="#" class="tablelink" onclick="updateWarn(${warn.id },2)"> 忽略</a>
				</td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="9">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
        
        </tbody>
    </table>
    ${warn.page.pageStr }


    
    
    
    </div>
 </form>   
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	
	
	function search(){
		$("#userForm").submit();
	}
	
	function sltAllUser(){
		if($("#sltAll").attr("checked")){
			$("input[name='userIds']").attr("checked",true);
		}else{
			$("input[name='userIds']").attr("checked",false);
		}
	}
	

	function updateWarn(id,status){
	    if(status==1){
            if(confirm("确定要发出预警？")){
                var url = "warn/updateWarn.html?status=1&id="+id;
                $.get(url,function(data){
                    if(data=="success"){
                        document.location.reload();
                    }
                });
            }
		}else if(status==2){
            if(confirm("确定要忽略该记录？")){
                var url = "warn/updateWarn.html?status=2&id="+id;
                $.get(url,function(data){
                    if(data=="success"){
                        document.location.reload();
                    }
                });
            }
		}

	}
	
	</script>

</body>

</html>
