<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
</head>


<body>
<form action="role.html" method="post" name=""roleForm"" id="roleForm">
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li>首页</li>
    <li>系统管理</li>
    <li>角色管理</li>
    </ul>
    </div>

    <div class="rightinfo">
    
    
    
    <div class="tools">
    
    	<ul class="seachform">
    	
    		<li><label>角色名：</label><input type="text" name="roleName" value="${role.roleName }" class="scinput" /></li>
	    	<ul class="toolbar">
	       		<li onclick="javascript:search();"><span><img src="images/t04.png" /></span>查询</li>
	       		<li onclick="javascript:addRole();"><span><img src="images/t01.png" /></span>添加</li>
	        </ul>
    	</ul>
    </div>
    
    
    <table class="tablelist" style="width: 80%; margin-top: 20px; margin-left: 20px;">
    	<thead>
    	<tr>
	        <th width="100px">序号<i class="sort"><img src="images/px.gif" /></i></th>
	        <th width="200px">角色名称</th>
			<th width="200px">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
			<c:when test="${not empty roleList}">
				<c:forEach items="${roleList}" var="role" varStatus="vs">
				<tr>
				<td>${vs.index+1}</td>
				<td id="roleNameTd${role.roleId }">${role.roleName }</td>
				<td><a href="javascript:editRole(${role.roleId });">修改</a><%-- | <a href="javascript:delRole(${role.roleId });">删除</a>--%> | <a href="javascript:editRights(${role.roleId });">权限</a></td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="3">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
        
        </tbody>
    </table>
    ${role.page.pageStr }
    </div>
 </form>   
    <script type="text/javascript">
    	function search(){
			$("#roleForm").submit();
		}
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function delRole(roleId){
			if(confirm('确定要执行删除操作吗?')){
				$.ajax({
					url:'${pageContext.request.contextPath}/role/delete.html',
					type:'post',
					async:false,
					data:{'roleId':roleId},
					dataType:'html',
					success:function(data){
						if(data == 'success'){
							window.location.href='${pageContext.request.contextPath}/role.html';
						}
					}
				});
			}
		}
		
		function addRole(){
			var dg = new $.dialog({
				title:'新增角色',
				id:'role_new',
				width:300,
				height:130,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				html:'<div style="width:100%;height:40px;line-height:40px;text-align:center;"><span style="color: #4f4f4f;font-size: 13px;font-weight: bolder;display:inline-block;vertical-align:middle;">角色名称：</span><input type="text" name="roleName" id="roleName" class="scinput"/></div>'
				});
    		dg.ShowDialog();
    		dg.addBtn('ok','保存',function(){
    			var url = "role/save.html";
    			var postData = {roleName:$("#roleName").val(),unitFromId:2,isSuper:0};
    			$.post(url,postData,function(data){
    				if(data=='success'){
    					dg.curWin.location.reload();
    					dg.cancel();
    				}else{
    					alert('角色名重复，保存失败！');
    					$("#roleName").focus();
    					$("#roleName").select();
    				}
    			});
    		});
		}
		
		function editRole(roleId){
			var roleName = $("#roleNameTd"+roleId).text();
			var dg = new $.dialog({
				title:'修改角色',
				id:'role_edit',
				width:300,
				height:130,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				html:'<div style="height:40px;line-height:40px;text-align:center;"><span style="color: #4f4f4f;font-size: 13px;font-weight: bold;display:inline-block;vertical-align:middle;">角色名称：</span><input type="text" name="roleName" id="roleName" value="'+roleName+'" class="scinput"/></div>'
				});
    		dg.ShowDialog();
    		dg.addBtn('ok','保存',function(){
    			var url = "role/save.html";
    			var postData = {roleId:roleId,roleName:$("#roleName").val(),unitFromId:2};
    			if($("#roleName").val()!="" && $("#roleName").val()!=null){
					$.post(url,postData,function(data){
						if(data=='success'){
							dg.curWin.location.reload();
							dg.cancel();
						}else{
							alert('角色名重复，保存失败！');
							$("#roleName").focus();
							$("#roleName").select();
						}
					});
				}else{
					alert("角色名不能为空!");
				}

    		});
		}
		
		function editRights(roleId){
			var dg = new $.dialog({
				title:'角色授权',
				id:'auth',
				width:280,
				height:370,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				resize:false,
				page:'role/auth.html?roleId='+roleId
				});
    		dg.ShowDialog();
		}
		
	function sltAllRole(){
		if($("#sltAll").attr("checked")){
			$("input[name='roleIds']").attr("checked",true);
		}else{
			$("input[name='roleIds']").attr("checked",false);
		}
	}
	</script>

</body>

</html>
