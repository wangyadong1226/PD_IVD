<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
});
</script>
<html>
  <head>
    <base href="<%=basePath%>">
	<style>
  	#lhgdlg_sampleStracking_data{top:0px !important;  	}
  	</style>
  </head>
  
  <body style="min-width:80px !important;">
  
  <div>
  <div class="formtitle"><span>下载详情</span></div>
    <table class="tablelist" style="text-align: center;">
    	<thead>
    	<tr>
	        <th>下载人</th>
	        <th>下载时间</th>
        </tr>
        </thead>
        <tbody>
             <c:choose>
                 <c:when test="${not empty list}">
                     <c:forEach items="${list}" var="download" varStatus="vs">
                      <tr class="main_info" >
						<td>${download.downloader}</td> 
						<td><fmt:formatDate value="${download.downloadTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
                     </c:forEach>
                 </c:when>
                  <c:otherwise>
                  <tr>
					<td colspan="2">没有相关数据</td>
				  </tr>
                  </c:otherwise>
             </c:choose>
        
        </tbody>
    </table>
  
  
  </div>
  
  </body>
</html>
