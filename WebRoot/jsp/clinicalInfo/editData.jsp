<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
	<style type="text/css">
		.form {border-collapse: collapse;width:100%; margin-bottom: 20px;border: dotted 1px #c7c7c7;}
		<%--.form td{width:90px;padding:5px;border:0px solid green;text-align:left;}--%>
		.form td{line-height:20px; text-indent:11px; border-right: dotted 1px #c7c7c7;width:200px;font-size: 13px}
		.form th{ height:30px; line-height:20px; text-indent:11px;text-align:right;width:150px;}
		.form label{font-size: 13px}
	</style>
</head>
  <body>
  
  <div class="rightinfo">
	    <div class="formtitle"><span>样本信息详情</span></div>
	    <table class="form" >
		  	 <tr>	 	
		  	 	<th><label>样本编号 :</label></th><td>AB${sample.sampleId }</td>
	    		<th><label>送检单位 :</label></th><td>${sample.unitFrom }</td>
		  	 	<th><label>住院门诊号 :</label></th><td>${sample.outpatientNum }</td>
		  	 	<th><label>送检医生 :</label></th><td>${sample.doctorFrom}</td>
	    		<th><label>样本类型 :</label></th><td>${sample.sampleType }</td>
		  	 </tr>
		  	 <tr>
		  	 	<th><label>孕妇姓名 :</label></th><td>${sample.name}</td>
	    		<th><label>孕妇年龄 :</label></th><td>${sample.age }</td>
		  	 	<th><label>孕产史 :</label></th><td>${sample.pregHistory}</td>
		  	 	<th><label>末次月经 :</label></th><td>${sample.lastPeriod}</td>
	    		<th><label>孕妇孕周 :</label></th><td>${sample.gesWeek }</td>
		  	 </tr>
		  	 <tr>
		  		<th><label>唐筛结果 :</label></th><td>${sample.downRes}</td>
		  	 	<th><label>唐筛比值 :</label></th><td>${sample.downRatio}</td>
		  	 	<th><label>NT值 :</label></th><td>${sample.NT}</td>
	    		<th><label>妊娠类型 :</label></th><td>${sample.ivfGestation }</td>
		  	 	<th><label>胎数 :</label></th><td>${sample.fetusNum }</td>
	    	 </tr>
		  	 <tr>
	    		<th><label>临床诊断 :</label></th><td>${sample.diagnosis }</td>
	    		<th><label>送检医生 :</label></th><td>${sample.doctorFrom}</td>
		  	 	<th><label>采样日期 :</label></th><td>${sample.samplingDate }</td>
	    		<th><label>接收日期 :</label></th><td>${sample.recieveDate }</td>
		  	 	<th><label>特殊情况说明 :</label></th><td>${sample.specialInfo}</td>
		  	 </tr>
		  	 <tr>
	    		<th><label>样本状态 :</label></th><td>正常</td>
	    		<th><label>医院编号:</label></th><td colspan=7>${sample.hospitalCode}</td>
	    	</tr>
	  	 </table>
  <div class="formtitle"><span>修改记录详情</span></div>
    <table class="tablelist" style="margin-bottom:20px;">
    	<thead>
    	<tr>
	        <th width=700px>修改内容</th>
	        <th width=80px>修改人</th>
	        <th width=120px>修改时间</th>
        </tr>
        </thead>
        <tbody>
             <c:choose>
                 <c:when test="${not empty list}">
                     <c:forEach items="${list}" var="clinicalEdit" varStatus="vs">
                      <tr class="main_info" >
						<td>
							<c:if test="${clinicalEdit.name!=''}">孕妇姓名 ：${clinicalEdit.name} &nbsp;</c:if>
							<c:if test="${clinicalEdit.age!=null and clinicalEdit.age!=''}">年龄：${clinicalEdit.age} &nbsp;&nbsp;&nbsp;</c:if>
							<c:if test="${clinicalEdit.outpatientNum!=''}">门诊号：${clinicalEdit.outpatientNum} &nbsp;&nbsp;&nbsp;</c:if>
							<c:if test="${clinicalEdit.gesWeek!=''}">孕周：${clinicalEdit.gesWeek} &nbsp;&nbsp;&nbsp;</c:if>
							<c:if test="${clinicalEdit.sampleType!=''}">样本类型：${clinicalEdit.sampleType} &nbsp;&nbsp;&nbsp;</c:if>
							<c:if test="${clinicalEdit.lastPeriod!=''}">末次月经：${clinicalEdit.lastPeriod} &nbsp;&nbsp;&nbsp;</c:if>
							<c:if test="${clinicalEdit.pregHistory!=''}">孕产史：${clinicalEdit.pregHistory} &nbsp;&nbsp;&nbsp;</c:if>
							<c:if test="${clinicalEdit.ivfGestation!=''}">妊娠类型：${clinicalEdit.ivfGestation} &nbsp;&nbsp;&nbsp;</c:if>
							<c:if test="${clinicalEdit.fetusNum!=''}">胎数：${clinicalEdit.fetusNum} &nbsp;&nbsp;&nbsp;</c:if>
							<c:if test="${clinicalEdit.diagnosis!=''}">临床诊断：${clinicalEdit.diagnosis} &nbsp;&nbsp;&nbsp;</c:if>
							<c:if test="${clinicalEdit.doctorFrom!=''}">送检医生：${clinicalEdit.doctorFrom} &nbsp;&nbsp;&nbsp;</c:if>
							<c:if test="${clinicalEdit.samplingDate!=''}">采样日期：${clinicalEdit.samplingDate} &nbsp;&nbsp;&nbsp;</c:if>
							<c:if test="${clinicalEdit.recieveDate!=''}">接收日期：${clinicalEdit.recieveDate} &nbsp;&nbsp;&nbsp;</c:if>
						</td>
						<td>${clinicalEdit.editor}</td> 
						<td><fmt:formatDate value="${clinicalEdit.editTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
    <input type="button" name="" id="" value="返回" onclick="history.go(-1)" class="btn"/>
  </div>
  
  </body>
</html>
