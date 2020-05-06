<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>展示PDF报告</title>
</head>
<body>
    <embed id="images1" name="images1" src="pdf.html?pathname=${pdfPath}" border="0" height="100%" width="100%" style="position: relative; left: 0px; top: 0px;">
</body>
</html>
