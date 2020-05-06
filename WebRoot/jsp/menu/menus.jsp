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
<form action="menu.html" method="post" name="menuForm" id="menuForm">
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li>首页</li>
    <li>系统管理</li>
    <li>菜单管理</li>
    </ul>
    </div>

    <div class="rightinfo">
    
    
    
    <div class="tools">
    
    	<ul class="seachform">
    	
    		<li><label>菜单名：</label><input type="text" name="menuName" value="${menu.menuName }" class="scinput" /></li>
	    	<ul class="toolbar">
	       <!--  <li onclick="javascript:search();"><span><img src="images/t04.png" /></span>查询</li>--> 
	        <li onclick="javascript:addmenu();"><span><img src="images/t01.png" /></span>添加</li>
	        </ul>
    	</ul>
    </div>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
	        <th><input name="sltAll" id="sltAll" type="checkbox" value="" onclick="sltAllMenu()"/></th>
	        <th>序号<i class="sort"><img src="images/px.gif" /></i></th>
	        <th>菜单名称</th>
	        <th>资源路径</th>
			<th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
			<c:when test="${not empty menuList}">
				<c:forEach items="${menuList}" var="menu" varStatus="vs">
				<tr class="main_info" id="tr${menu.menuId }">
				<td><input type="checkbox" name="menuIds" id="menuIds${menu.menuId }" value="${menu.menuId }"/></td>
				<td>${vs.index+1}</td>
				<td>${menu.menuName }</td>
				<td>${menu.menuUrl }</td>
				<td><a href="###" onclick="openClose(${menu.menuId },this,${vs.index },${menu.isSuper})">展开</a> | 
				<a href="###" onclick="editmenu(${menu.menuId })">修改</a> | 
				<a href="###" onclick="delmenu(${menu.menuId },true)">删除</a></td>
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
    ${menu.page.pageStr }
    </div>
 </form>   
   <script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function search(){
			$("#menuForm").submit();
		}
		
		function addmenu(){
			var dg = new $.dialog({
				title:'新增菜单',
				id:'menu_new',
				width:330,
				height:250,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'menu/add.html'
				});
    		dg.ShowDialog();
		}
		
		function editmenu(menuId){
			var dg = new $.dialog({
				title:'修改菜单',
				id:'menu_edit',
				width:330,
				height:220,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'menu/edit.html?menuId='+menuId
				});
    		dg.ShowDialog();
		}
		
		function delmenu(menuId,isParent){
			var flag = false;
			if(isParent){
				if(confirm("确定要删除该菜单吗？其下子菜单将一并删除！")){
					flag = true;
				}
			}else{
				if(confirm("确定要删除该菜单吗？")){
					flag = true;
				}
			}
			if(flag){
				var url = "menu/del.html?menuId="+menuId;
				$.get(url,function(data){
					document.location.reload();
				});
			}
		}
		
		function openClose(menuId,curObj,trIndex,isSuper){
			var txt = $(curObj).text();
			if(txt=="展开"){
				$(curObj).text("折叠");
				$("#tr"+menuId).after("<tr class='main_info' id='tempTr"+menuId+"'><td colspan='4'>数据载入中</td></tr>");
				if(trIndex%2==0){
					$("#tempTr"+menuId).addClass("main_table_even");
				}
				var url = "menu/sub.html?parentId="+menuId+"&isSuper="+isSuper;
				$.get(url,function(data){
					//alert(data);
					if(data.length>0){
						var html = "";
						$.each(data,function(i){
							html = "<tr style='height:24px;line-height:24px;' name='subTr"+menuId+"'>";
							html += "<td></td>";
							html += "<td></td>";
							html += "<td><span style='width:80px;display:inline-block;'></span>";
							html += "<span style='width:100px;text-align:left;display:inline-block;'>"+this.menuName+"</span>";
							html += "</td>";
							html += "<td>"+this.menuUrl+"</td>";
							html += "<td><a href='###' onclick='editmenu("+this.menuId+")'>修改</a> | <a href='###' onclick='delmenu("+this.menuId+",false)'>删除</a></td>";
							html += "</tr>";
							$("#tempTr"+menuId).before(html);
						});
						$("#tempTr"+menuId).remove();
						if(trIndex%2==0){
							$("tr[name='subTr"+menuId+"']").addClass("main_table_even");
						}
						//alert($(".main_table").html());
					}else{
						$("#tempTr"+menuId+" > td").html("没有相关数据");
					}
				},"json");
			}else{
				$("#tempTr"+menuId).remove();
				$("tr[name='subTr"+menuId+"']").remove();
				$(curObj).text("展开");
			}
		}
		
	function sltAllMenu(){
		if($("#sltAll").attr("checked")){
			$("input[name='menuIds']").attr("checked",true);
		}else{
			$("input[name='menuIds']").attr("checked",false);
		}
	}
		
	</script>	

</body>

</html>
