<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>无标题文档</title>
	<script type="text/javascript" src="js/jquery.alerts.js"></script>
	<script type="text/javascript">
		function about(){
			var dg = new $.dialog({
				title:'关于',
				id:'control',
				width:470,
				height:380,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'${ctx}/home/about.html'
				});
		  		dg.ShowDialog();
		}
	</script>
	<style type="text/css">
		#popup_container {font-family: Arial, sans-serif;font-size: 12px;min-width: 300px;max-width: 600px;background:#FFF;border:solid 5px #a2d7d4;color:#000;-moz-border-radius:5px;-webkit-border-radius: 5px;border-radius: 5px;}
		#popup_title {font-size:12px;font-weight:bold;text-align:center;line-height:1.75em;color:#f5f5f5;background: #75c3bc /*url(/images/eg/title.gif)*/ top repeat-x;border: solid 1px #FFF;border-bottom: solid 1px #999;cursor: default;padding:0em;margin:0em auto;}
		/*#popup_content {background:16px 16px no-repeat url(/images/eg/info.gif);padding: 3em 1.75em;margin: 0em;}
		#popup_content.alert {background-image: url(/images/eg/info.gif);}
		#popup_content.confirm {background-image: url(/images/eg/important.gif);}
		#popup_content.prompt {background-image: url(/images/eg/help.gif);}*/
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
		    background: #75c3bc /*url(/images/eg/title.gif)*/ top repeat-x;
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
	<form action="batch.html" method="post" name="batchForm" id="batchForm">
		<div class="place">
	    <span>位置：</span>
	    <ul class="placeul">
	    <li><a href="#">首页</a></li>
	    <li><a href="#">批次管理</a></li>
	    <li><a href="#">批次信息</a></li>
	    </ul>
	    </div>
	
	    <div class="rightinfo">
	    
	    
	    
	    <div class="tools">
	    
	    	<ul class="seachform">
	    	
	    		<li><label>批次号：</label><input type="text" name="batchNum" value="${batch.batchNum }" class="scinput" /></li>
		    	<ul class="toolbar">
		        <li onclick="javascript:search();"><span><img src="images/t04.png" /></span>查询</li>
		        <li onclick="javascript:addBatch();"><span><img src="images/t01.png" /></span>添加</li>
		        </ul>
		        
	    	</ul>
	    </div>
	    
	    
	    <table class="tablelist">
	    	<thead>
	    	<tr>
		        <th width="50px">编号<i class="sort"><img src="images/px.gif" /></i></th>
		        <th width="100px">批次号</th>
		        <th width="100px">上传时间</th>
				<th width="100px">上传人</th>
		        <th style="width: 100px">操作</th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:choose>
				<c:when test="${not empty batchList}">
					<c:forEach items="${batchList}" var="batch" varStatus="vs">
					<tr>
					<td>${vs.index+1}</td>
					<td>${batch.batchNum }</td>
			        <td>${batch.uploadTime }</td>
					<td>${batch.uploadMan }</td>
					<td align="left">
			        	<a href="#" class="tablelink" onclick="window.location.href='sample/detailList.html?batchId=${batch.batchId}'">查看详情</a><label>&nbsp;</label><label>&nbsp;</label><label>&nbsp;</label><label>&nbsp;</label>
						&nbsp;&nbsp;<a href="#" class="tablelink" onclick="doDel(${batch.batchId })"> 删除</a><label>&nbsp;</label><label>&nbsp;</label><label>&nbsp;</label><label>&nbsp;</label>
			        </td>


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
			$("#batchForm").attr("action", "batch.html");
			$("#batchForm").submit();
		}
		
		function searchOther(){
			$("#batchForm").attr("action", "batch.html?sql=true ");
			$("#batchForm").submit();
		}
		function addBatch(){
			$("#batchForm").attr("action", "batch/add.html");
			$("#batchForm").submit();
		}
		
		function doDel(batchId){
				if ( window.confirm("确定删除？") ){
					$("#batchForm").attr("action", "batch/delete.html?id=" + batchId);
					$("#batchForm").submit();
				}
		}
		function reagents(batchId){
			$("#batchForm").attr("action", "batch/reagents.html?id=" + batchId);
			$("#batchForm").submit();
		}
		function doSendAnalysis(batchId,combinationState){
			if(combinationState!=1){
				jAlert('请先添加试剂信息', '温馨提示');
				}else if (confirm("是否开始分析？")){
					$("#ksfx").removeAttr('onclick');//去掉a标签中的onclick事件
					$("#batchForm").attr("action", "batch/toSendAnalysis.html?id="+batchId);
					$("#batchForm").submit();
				}
		}
		function analysis(id){
			window.location.href='sample/detailList.html?id='+id+'&flag=2&currentPage=${batch.page.currentPage }';
			}
		function findDetails(batchId){
		var dg = new $.dialog({
			title:'',
			id:'batch_details',
			width:500,
			height:400,
			iconTitle:false,
			cover:true,
			maxBtn:false,
			resize:false,
			page:'batch/findDetails.html?batchId='+batchId
			});
	  		dg.ShowDialog();
		}
		</script>
	
	</body>

</html>
