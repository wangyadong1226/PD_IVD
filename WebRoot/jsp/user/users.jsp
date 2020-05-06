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
    <li>系统管理</li>
    <li>用户管理</li>
    </ul>
    </div>

    <div class="rightinfo">
    
    
    
    <div class="tools">
    
    	<ul class="seachform">
    	
    		<li><label>用户名：</label><input type="text" name="loginname" value="${user.loginname }" class="scinput" /></li>
		    <li><label>角色：</label>  
		    <div class="vocation">
		    <select name="roleId" id="roleId" class="select3" style="margin-left:3px;">
			    <option value="">请选择</option>
				<c:forEach items="${roleList}" var="role">
				<option value="${role.roleId }" <c:if test="${user.roleId==role.roleId}">selected</c:if>>${role.roleName }</option>
				</c:forEach>
			</select>
		    </div>
		    </li>
		   <li><label> 登录日期：</label><input type="text" name="lastLoginStart" id="lastLoginStart" class="scinput" value="<fmt:formatDate value="${user.lastLoginStart}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'lastLoginEnd\')}' })" readonly="readonly"/>-
		   								<input type="text" name="lastLoginEnd" id="lastLoginEnd" class="scinput" value="<fmt:formatDate value="${user.lastLoginEnd}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate:'#F{$dp.$D(\'lastLoginStart\')}' })" readonly="readonly" /></li>
    
	    	<ul class="toolbar">
	    	<!-- 
	        <li class="click"><span><img src="images/t01.png" /></span>添加</li>
	        <li class="click"><span><img src="images/t02.png" /></span>修改</li>
	        <li><span><img src="images/t03.png" /></span>删除</li>
	         -->
	        <li onclick="javascript:search();"><span><img src="images/t04.png" /></span>查询</li>
	        <li onclick="javascript:addUser();"><span><img src="images/t01.png" /></span>添加</li><%--
	        <li onclick="javascript:exportUser();"><span><img src="images/icon/office/excelOut.png" /></span>导出</li>
	        --%></ul>
	        
	        <!--  
	        <ul class="toolbar1">
	        <li><span><img src="images/t05.png" /></span>设置</li>
	        </ul>
	        -->
    	</ul>
    </div>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
	        <th><input name="sltAll" id="sltAll" type="checkbox" value="" onclick="sltAllUser()"/></th>
	        <th>编号<i class="sort"><img src="images/px.gif" /></i></th>
	        <th>用户名</th>
			<th>名称</th>
			<th>邮箱</th>
			<th>角色</th>
			<th>最近登录</th>
			<th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
			<c:when test="${not empty userList}">
				<c:forEach items="${userList}" var="user" varStatus="vs">
				<tr>
				<td><input type="checkbox" name="userIds" id="userIds${user.userId }" value="${user.userId }"/></td>
				<td>${vs.index+1}</td>
				<td>${user.loginname }</td>
				<td>${user.username }</td>
				<td>${user.email }</td>
				<td>${user.role.roleName }</td>
				<td><fmt:formatDate value="${user.lastLogin}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><a href="javascript:editUser(${user.userId });">修改</a> | <a href="javascript:delUser(${user.userId });">删除</a> </td>	
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="7">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
        
        </tbody>
    </table>
    ${user.page.pageStr }
   <!-- 
    <div class="pagin">
    	<div class="message">共<i class="blue">1256</i>条记录，当前显示第&nbsp;<i class="blue">2&nbsp;</i>页</div>
        <ul class="paginList">
        <li class="paginItem"><a href="javascript:;"><span class="pagepre"></span></a></li>
        <li class="paginItem"><a href="javascript:;">1</a></li>
        <li class="paginItem current"><a href="javascript:;">2</a></li>
        <li class="paginItem"><a href="javascript:;">3</a></li>
        <li class="paginItem"><a href="javascript:;">4</a></li>
        <li class="paginItem"><a href="javascript:;">5</a></li>
        <li class="paginItem more"><a href="javascript:;">...</a></li>
        <li class="paginItem"><a href="javascript:;">10</a></li>
        <li class="paginItem"><a href="javascript:;"><span class="pagenxt"></span></a></li>
        </ul>
    </div>
    --> 
    
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
        
      <div class="tipinfo">
        <span><img src="images/ticon.png" /></span>
        <div class="tipright">
        <p>是否确认对信息的修改 ？</p>
        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
        </div>
        </div>
        
        <div class="tipbtn">
        <input name="" type="button"  class="sure" value="确定" />&nbsp;
        <input name="" type="button"  class="cancel" value="取消" />
        </div>
    
    </div>
    
    
    
    
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
	
	function editUser(userId){
	var dg = new $.dialog({
		title:'修改用户',
		id:'user_edit',
		width:330,
		height:350,
		iconTitle:false,
		cover:true,
		maxBtn:false,
		resize:false,
		page:'user/edit.html?userId='+userId
		});
  		dg.ShowDialog();
	}
	
	function delUser(userId){
	if(confirm("确定要删除该记录？")){
		var url = "user/delete.html?userId="+userId;
		$.get(url,function(data){
			if(data=="success"){
				document.location.reload();
			}
		});
		}
	}
	
	function addUser(){
	//$(".shadow").show();
	var dg = new $.dialog({
		title:'新增用户',
		id:'user_new',
		width:330,
		height:350,
		iconTitle:false,
		cover:true,
		maxBtn:false,
		xButton:true,
		resize:false,
		page:'user/add.html'
		});
  		dg.ShowDialog();
	}
	
	function exportUser(){
		document.location = "user/excel.html";
	}
	</script>

</body>

</html>
