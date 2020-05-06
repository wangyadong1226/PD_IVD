<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<script type="text/javascript">
	function about(){
		window.parent.about();
	}
	function home(){
		window.parent.location.reload();
	}
	$(function(){	
		//顶部导航切换
		$(".nav li a").click(function(){
			$(".nav li a.selected").removeClass("selected");
			$(this).addClass("selected");
		})	
	})	
</script>
</head>
<c:if test="${sessionUserIsSuper==1}">
	<body style="background:url(${ctx }/images/topbg01.gif) repeat-x;">
     <img src="${ctx }/images/logo.png" title="系统首页" />
     <div class="topright"  style="background:url(${ctx }/images/topbg01.gif) repeat-x;">    
    <ul>
    <%--<li><span><img src="${ctx }/images/help.png" title="帮助"  class="helpimg"/></span><a href="#">帮助</a></li>
    --%>
    	<li><a href="#">工程师版</a></li>
    	<li><a href="#" onclick="home()">首页</a></li>
    <li><a href="${ctx }/logout.html" target="_parent">退出</a></li>
    </ul>
     
    <div class="user">
    <span>
    	<c:choose>
    		<c:when test="${not empty sessionScope.sessionUser }">
    			${sessionScope.sessionUser.username }
    		</c:when>
    		<c:otherwise>
    			异常用户
    		</c:otherwise>
    	</c:choose>
    </span>
    
    <%--<i onclick="parent.showMessage()" style="cursor: hand;">消息</i>
    <b onclick="parent.showMessage()" style="cursor: hand;">5</b>    
    --%></div>    
    
    </div>

</body>
</c:if>
<c:if test="${sessionUserIsSuper==0}">
	<body style="background:url(${ctx }/images/topbg.gif) repeat-x;">
	     <%--<img src="${ctx }/images/logo.png" title="系统首页" />--%>
		 <br/><font style="font-family: '幼圆';color: white; margin-left: 100px;margin-top: 50px; font-size: 35px" >食品安全风险信息管理及预警系统</font>
	     <div class="topright"  style="background:url(${ctx }/images/topbg.gif) repeat-x;">    
	    <ul>
	    <%--<li><span><img src="${ctx }/images/help.png" title="帮助"  class="helpimg"/></span><a href="#">帮助</a></li>
	    --%>
	    	<li><a href="#" onclick="home()">首页</a></li>
	    	<li><a href="#" onclick="about()">关于</a></li>
	    <li><a href="${ctx }/logout.html" target="_parent">退出</a></li>
	    </ul>
	     
	    <div class="user">
	    <span>
	    	<c:choose>
	    		<c:when test="${not empty sessionScope.sessionUser }">
	    			${sessionScope.sessionUser.username }
	    		</c:when>
	    		<c:otherwise>
	    			异常用户
	    		</c:otherwise>
	    	</c:choose>
	    </span>
	    
	    <%--<i onclick="parent.showMessage()" style="cursor: hand;">消息</i>
	    <b onclick="parent.showMessage()" style="cursor: hand;">5</b>    
	    --%></div>    
	    
	    </div>
	
	</body>  
</c:if>
</html>

