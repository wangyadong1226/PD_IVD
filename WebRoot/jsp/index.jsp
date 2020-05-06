<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
<logic:empty name="USER" scope="session">
<script>
	window.location.href = "login.jsp?error=2";
</script>
</logic:empty>
 -->
<html lang="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>食品安全风险信息管理及预警系统</title>
	<script type="text/javascript">
		function about(){
			var child = document.getElementById('rightFrame').contentWindow;
			child.about();
		}
	</script>
</head>
<frameset rows="88,*,60" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="home/header.html" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="140,*" frameborder="no" border="0" framespacing="0">
    <frame src="home/left.html" width="140" name="leftFrame" scrolling="No"  id="leftFrame" title="leftFrame" />
    <c:if test="${sessionUserIsSuper==0}">
    	<frame src="batch.html" name="rightFrame" id="rightFrame" title="rightFrame" />
    </c:if>
    <c:if test="${sessionUserIsSuper==1}">
    	<frame src="batchp.html" name="rightFrame" id="rightFrame" title="rightFrame" />
    </c:if>
  </frameset>
  <frame src="home/footer.html" name="bottomFrame" scrolling="No" noresize="noresize" id="bottomFrame" title="bottomFrame" />
</frameset>
<noframes>
	<body>
		您所使用的浏览器不支持本系统，请升级浏览器
	</body>
</noframes>
</html>
