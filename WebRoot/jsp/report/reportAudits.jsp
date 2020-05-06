<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>报告审核</title>
	<script type="text/javascript" src="js/jquery.alerts.js"></script>
	<style type="text/css">
		#popup_container {font-family: Arial, sans-serif;font-size: 12px;min-width: 300px;max-width: 600px;background:#FFF;border:solid 5px #a2d7d4;color:#000;-moz-border-radius:5px;-webkit-border-radius: 5px;border-radius: 5px;}
		#popup_title {font-size:12px;font-weight:bold;text-align:center;line-height:1.75em;color:#f5f5f5;background: #75c3bc url(/images/eg/title.gif) top repeat-x;border: solid 1px #FFF;border-bottom: solid 1px #999;cursor: default;padding:0em;margin:0em auto;}
		#popup_content {background:16px 16px no-repeat url(/images/eg/info.gif);padding: 3em 1.75em;margin: 0em;}
		#popup_content.alert {background-image: url(/images/eg/info.gif);}
		#popup_content.confirm {background-image: url(/images/eg/important.gif);}
		#popup_content.prompt {background-image: url(/images/eg/help.gif);}
		#popup_message {padding-left: 48px;font-family: sans-serif;color: #75c3bc;font-size: 20px;}
		#popup_panel {text-align: center;margin:1em 0em 0em 1em;}
		#popup_prompt {margin:.5em 0em;}
		#popup_ok  {  
		    display: inline-block;  
		    zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */  
		    *display: inline;  
		    vertical-align: baseline;  
		    margin: 0 2px;  
		    outline: none;  
		    cursor: pointer;  
		    text-align: center;  
		    text-decoration: none; 
		    background: #75c3bc url(/images/eg/title.gif)top repeat-x; 
		    font: 14px/100% Arial, Helvetica, sans-serif;  
		    color:#ffffff;
		    padding: .5em 2em .55em;  
		    text-shadow: 0 1px 1px rgba(0,0,0,.3);  
		    -webkit-border-radius: .5em;   
		    -moz-border-radius: .5em;  
		    border-radius: .5em;  
		    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);  
		    -moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);  
		    box-shadow: 0 1px 2px rgba(0,0,0,.2);  
		}  
	</style>
  </head>

  <body>
	<form action="reportAudit.html" method="post" name="batchForm" id="batchForm">
		<div class="place">
	    <span>位置：</span>
	    <ul class="placeul">
	    <li><a href="${ctx}/batch.html">首页</a></li>
	    <li><a href="#">报告管理</a></li>
	    <li><a href="#">报告审核</a></li>
	    </ul>
	    </div>
	
	    <div class="rightinfo">
	    <div class="tools">
	    	<ul class="seachform">
	    		<li><label>批次号：</label><input type="text" name="batchNum" value="${batch.batchNum }" class="scinput" /></li>
		    	<ul class="toolbar">
		        	<li onclick="javascript:search();"><span><img src="images/t04.png" /></span>查询</li>
		        </ul>
	    	</ul>
	    </div>
	    <table class="tablelist">
	    	<thead>
	    	<tr>
		        <th width="10%">编号<i class="sort"><img src="images/px.gif" /></i></th>
		        <th>批次号</th>
		        <th>测序日期</th>
		        <th>批次状态</th>
		       	<th>审核状态</th>
		        <th>操作</th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:choose>
				<c:when test="${not empty batchList}">
					<c:forEach items="${batchList}" var="batch" varStatus="vs">
					<tr>
					<td>${vs.index+1}</td>
					<td>${batch.batchNum }</td>
			        <td><fmt:formatDate value="${batch.seqDate }" pattern="yyyy-MM-dd"/> </td>
			        <c:choose>
					       <c:when test="${batch.statusName == '测序未完成，等待中'}">
					             <td><font color=red>${batch.statusName }</font></td>
					       </c:when>
					       <c:otherwise>
					              <td><font>${batch.statusName }</font></td>
					       </c:otherwise>
					</c:choose>
					 <c:choose>
					       <c:when test="${batch.auditStatus==0}">
					             <td><font color=red>未审核</font></td>
					             <td><a href="#" class="tablelink" onclick="window.location.href='reportAudit/auditDetailList.html?id=${batch.batchId}'">审核</a></td>
					       </c:when>
					        <c:when test="${batch.auditStatus==1}">
					             <td><font color=red>部分审核</font></td>
					             <td><a href="#" class="tablelink" onclick="window.location.href='reportAudit/auditDetailList.html?id=${batch.batchId}'">审核</a></td>
					       </c:when>
					       <c:otherwise>
					              <td><font color="#009900">已审核</font></td>
					              <td><a href="#" class="tablelink" onclick="window.location.href='reportAudit/auditDetailList.html?id=${batch.batchId}'">详情</a></td>
					       </c:otherwise>
					</c:choose>
					</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="5">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
	        
	        </tbody>
	    </table>
	    ${batch.page.pageStr }
	    
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
			$("#batchForm").attr("action", "reportAudit.html");
			$("#batchForm").submit();
		}

		</script>
	</body>
</html>
