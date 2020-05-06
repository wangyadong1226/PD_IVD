<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var local_ctx = '${ctx}';
</script>
<!-- js文件引入 -->
<!-- css文件引入 -->

<link rel="stylesheet" type="text/css" href="${ctx }/css/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/css/select.css"/>
<script language="JavaScript" src="${ctx }/js/jquery.js"></script>
<script type="text/javascript" src="${ctx }/js/select-ui.min.js"></script>
<script type="text/javascript" src="${ctx }/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx }/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>

<script type="text/javascript" src="${ctx }/js/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="${ctx }/editor/kindeditor.js"></script>
<script type="text/javascript" src="${ctx }/js/json2.js"></script>


<script type="text/javascript">
function about(){
	var dg = new $.dialog({
		title:'关于',
		id:'control',
		width:400,
		height:280,
		iconTitle:false,
		cover:true,
		maxBtn:false,
		xButton:true,
		resize:false,
		page:'${ctx}/home/about.html'
		});
  		dg.ShowDialog();
}
function StringBuffer() { 
   this.buffer = []; 
 } 
 StringBuffer.prototype.append = function append(string) { 
   this.buffer.push(string); 
   return this; 
 }; 
 StringBuffer.prototype.toString = function toString() { 
   return this.buffer.join(""); 
 }; 
</script>
