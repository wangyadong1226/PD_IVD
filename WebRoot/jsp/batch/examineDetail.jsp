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
	<form action="saveReagents.html" name="reagentsForm" id="reagentsForm" method="post">
		<div class="place">
		    <span>位置：</span>
		    <ul class="placeul">
				<li><a href="#">首页</a></li>
			    <li><a href="${ctx}/batch.html">批次信息</a></li>
			    <li><a href="#">查看分析结果</a></li>
			    <li><a href="#">样本审核</a></li>
		    </ul>
		</div>
		    <DIV id="zk_result" style="background: rgb(170, 237, 243); width: 98%; height: 20px; color: rgb(88, 126, 127); line-height: 20px; padding-left: 2%; font-weight: 600;"></DIV>
						<DIV style="width: 100%; ">
							<SPAN style="width: 98%; height: 20px; color: rgb(76, 108, 133); line-height: 20px; padding-left: 6%; font-weight: 800; margin-top: 3px; ">信息:</SPAN>
						</DIV>
						<DIV id="inbox"	style="width: 98%; padding-left: 10%;">
							<INPUT name="hq_url" id="hq_url" type="hidden">
							<TABLE class="tab_td" style="width: 100%; height: 150px;">
								<TBODY>
									<TR style="width: 100%;">
										<TD style="width: 30%; ">
											孕妇姓名:${item.name }
										</TD>
										<TD style="width: 30%; ">
											孕妇年龄:${item.age }
										</TD>
										<TD style="width: 30%; ">
											住院/门诊号:${item.outpatientNum }
										</TD>
									</TR>
									<TR>
										<TD style="">
											末次月经:${item.lastPeriod }
										</TD>
										<TD style="">
											样本类型:${item.sampleType }
										</TD>
										<TD style=" letter-spacing: 3.5px;">
											临床诊断:${item.diagnosis }
										</TD>
									</TR>
									<TR>
										<TD style=" letter-spacing: 3.5px;">
											孕产史:${item.pregHistory }
										</TD>
										<TD style="">
											样本状态:${item.statusName }
										</TD>
										<TD style=" letter-spacing: 0.2px;">
											送检单位/部门:${item.unitFrom }
										</TD>
									</TR>
									<TR>
										<TD style="">
											送检医生:${item.doctorFrom }
										</TD>
										<TD style="">
											采样日期:${item.samplingDate }
										</TD>
										<TD style="">
											接收日期:${item.recieveDate }
										</TD>
									</TR>
									<TR>
										<TD style="color: rgb(130, 151, 199);">
											送检编号:${item.sampleId }
										</TD>
										<TD style="color: rgb(130, 151, 199); letter-spacing: 3.5px;">
											文库号:${item.libId }
										</TD>
										<TD style="color: rgb(130, 151, 199);">
											index:${item.indexNum }
										</TD>
									</TR>
									<!-- 
									<TR>
										<TD style="color: rgb(130, 151, 199);">
											机器编号:${item.name }
										</TD>
										<TD style="color: rgb(130, 151, 199);">
											上机时间:${item.name }
										</TD>
										<INPUT name="yb_id" type="hidden" value="'+item.yb_id+'">
									</TR>
									 -->
								</TBODY>
							</TABLE>
						</DIV>
						<div id="zk_result" style="background:#aaedf3; height:20px;width:98%;padding-left:5%;line-height:20px;font-weight:600;  color:#000000   ">
						质控结果：${item.showPrimaryQC}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   对照品结果：${qualityControlResults}</div>
						<DIV
							style="width: 98%; height: 20px; color: #000000; line-height: 20px; padding-left: 5%; font-weight: 800; margin-top: 3px; ">
							检测结果：
						</DIV>
						<DIV id="show_info" style="height: 96px; display: none;">
							<CENTER style="padding-top: 42px;">
								未分析完成
							</CENTER>
						</DIV>
						<DIV id="hid_tab"
							style="width: 100%;">
							<TABLE id="listtable"
								style="background: rgb(242, 247, 253);  padding-left: 2%;margin: 8px auto 0px; width: 80%; text-align: center;"
								cellSpacing="0">
								<TBODY>
									<STYLE>
									.list_tr {
										margin-left: 5px;
									}
									</STYLE>

									<TR class="list_tr" id="listop"
										style="height: 20px; color: rgb(88, 126, 127); line-height: 20px;"
										bgColor="#c0e4f0">
										<TD
											style="border-right-color: rgb(255, 255, 255); border-right-width: 1px; border-right-style: solid;">
											<CENTER style="color: #000000">
												检测项目
											</CENTER>
										</TD>
										<TD
											style="border-right-color: rgb(255, 255, 255); border-right-width: 1px; border-right-style: solid;">
											<CENTER style="color: #000000">
												三体风险指数
											</CENTER>
										</TD>
										<TD
											style="border-right-color: rgb(255, 255, 255); border-right-width: 1px; border-right-style: solid;">
											<CENTER style="color: #000000">
												参考范围
											</CENTER>
										</TD>
										<TD>
											<CENTER style="color: #000000">
												胎儿三体风险
											</CENTER>
										</TD>
									</TR>
									<TR id="st_13"
										style="height: 18px; color: rgb(116, 121, 124); line-height: 18px;">
										<TD>
											13.三体
										</TD>
										<TD>
											<fmt:formatNumber value="${item.z13 }" pattern="#0.000000" />
										</TD>
										<TD>
											Z≥3 高风险；Z<3 低风险
										</TD>
										
										<c:choose>
										       <c:when test="${item.res13 == 'T13'}">
										             <td><font color=red >${item.res13 }</font></td>
										       </c:when>
										       <c:otherwise>
										              <td>${item.res13 }</td>
										       </c:otherwise>
										</c:choose>
										
									</TR>
									<TR id="st_18"
										style="height: 18px; color: rgb(116, 121, 124); line-height: 18px;">
										<TD>
											18.三体
										</TD>
										<TD><fmt:formatNumber value="${item.z18 }" pattern="#0.000000"/></TD>
										<TD>
											Z≥3 高风险；Z<3 低风险
										</TD>
										<c:choose>
										       <c:when test="${item.res18 == 'T18'}">
										             <td><font color=red >${item.res18 }</font></td>
										       </c:when>
										       <c:otherwise>
										              <td>${item.res18 }</td>
										       </c:otherwise>
										</c:choose>
									</TR>
									<TR id="st_21"
										style="height: 18px; color: rgb(116, 121, 124); line-height: 18px;">
										<TD>
											21.三体
										</TD>
										<TD><fmt:formatNumber value="${item.z21 }" pattern="#0.000000"/></TD>
										<TD>
											Z≥3 高风险；Z<3 低风险
										</TD>
										<c:choose>
										       <c:when test="${item.res21 == 'T21'}">
										             <td><font color=red >${item.res21 }</font></td>
										       </c:when>
										       <c:otherwise>
										              <td>${item.res21 }</td>
										       </c:otherwise>
										</c:choose>
									</TR>
								</TBODY>
							</TABLE>
						</DIV>
						<!-- 
						<DIV id="tishi"
							style="width: 98%; height: 15px; color: rgb(161, 174, 226); padding-left: 2%; font-weight: 600; margin-top: 7px;">
							结论：
							<SPAN>提示胎儿：12、18、21号染色体低风险</SPAN>
						</DIV>
						 -->
						 <c:if test="${item.checkStatus=='未审核'}">
							<DIV id="change_check"
								style="background: rgb(214, 231, 251); width: 98%; height: 32px; padding-left: 5%; margin-top: 13px; margin-bottom: 0px; -margin-top: 1px;">
								<DIV
									style="width: 16%; height: 20px; color: #000000; font-weight: 800; margin-top: 10px; float: left;">
									报告审核：
								</DIV>
								<DIV id="check_suc"
									style=" border-width: 0px; border-style: solid; border-radius: 11px; width: 9%; color: rgb(255, 255, 255); margin-top: 0px; float: left; ">
									<ul class="forminfo">
									    <li><input name="" type="button" style="width:137px;height:35px; background:green; no-repeat; font-size:14px;font-weight:bold;color:white; cursor:pointer;" 
									    onclick="window.location.href='updateStatus.html?tbSampleId=${item.tbSampleId}&batchId=${item.batchId }&flag=1'"  value="通   过"></li>
									</ul>
								</DIV>
								<DIV id="check_fail"
									style="border-width: 0px; border-style: solid; border-radius: 11px; width: 9%; color: rgb(255, 255, 255); margin-top: 0px; margin-left: 300px; float: left; ">
									<ul class="forminfo">
									    <li><input name="" type="button" style="width:137px;height:35px; background:red; no-repeat; font-size:14px;font-weight:bold;color:white; cursor:pointer;" 
									    onclick="window.location.href='updateStatus.html?tbSampleId=${item.tbSampleId}&batchId=${item.batchId }&flag=0'" value="不 通 过"></li>
									</ul>
								</DIV>
							</DIV>
					</c:if>
					<c:if test="${item.checkStatus!='未审核'}">
						<DIV
							style="width: 98%; height: 20px; color: #000000; line-height: 20px; padding-left: 5%; font-weight: 800; margin-top: 20px; ">
							报告审核：${item.checkStatus}
						</DIV>
					</c:if>
								
	</form>
		<script type="text/javascript">
			
			
		</script>
	</BODY>
</html>
