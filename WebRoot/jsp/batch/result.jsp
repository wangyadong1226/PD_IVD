<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
	<link href="${ctx }/css/styleForybcx.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${ctx }/css/style.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui1_5/themes/metro/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui1_5/themes/icon.css">
	<script type="text/javascript" src="${ctx}/easyui1_5/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui1_5/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui1_5/datagrid-filter.js"></script>

<script type="text/javascript">
		$(document).ready(function(e) {
			//下拉列表赋值
			$("#status").val(${sample.status});  //样本状态
			$("#checkStatus").val(${sample.checkStatus});  //审核状态
			
		});
		var data = ${json};
		var rows = data.rows;
		console.log(rows);
		$(function(){
			var sortFlag = false;
		    $('#ee').datagrid({
		    	title:'分析结果信息',
		        fitColumns:false,
		        singleSelect:true,
		        remoteSort: false,
		        loadMsg: "数据加载中，请稍后...",
		        frozenColumns:[[
						{field:'id',title:'编号',width:60,align:'center',sortable:true,resizable:true,  
	                        //添加超级链 
	                        formatter:function(value,rowData,rowIndex){
	                            //function里面的三个参数代表当前字段值，当前行数据对象，行号（行号从0开始）
	                            return rowIndex+1;  
	                       } }, 
		                {field:'recieveDate',title:'接收日期',width:100,align:'center',sortable:true,resizable:true,frozen:true},
						{field:'sampleId',title:'样本ID',width:100,align:'center',sortable:true,resizable:true},
						{field:'libId',title:'文库ID',width:150,align:'center',sortable:true,resizable:true},
						{field:'name',title:'孕妇姓名',width:100,align:'center',sortable:true,resizable:true}
		                	]],
		        columns:[[
						{field:'outpatientNum',title:'住院门诊号',width:80,align:'center',sortable:true,resizable:true},
						{field:'statusName',title:'样本状态',width:80,align:'center',sortable:true,resizable:true},
						{field:'doctorFrom',title:'送检医生',width:80,align:'center',sortable:true,resizable:true},

						{field:'t13Name',title:'T13',width:80,align:'center',sortable:true,resizable:true,
							formatter:function(value,rowData,rowIndex){
								//function里面的三个参数代表当前字段值，当前行数据对象，行号（行号从0开始）
								if(value == '高风险'){
									return "<span style='color:red'>"+value+"</span>";
								}else if(value == '灰区'){
									return "<span style='color:yellow'>"+value+"</span>";
								}else
									return value;

							}
						},
						{field:'t18Name',title:'T18',width:80,align:'center',sortable:true,resizable:true,
							formatter:function(value,rowData,rowIndex){
								//function里面的三个参数代表当前字段值，当前行数据对象，行号（行号从0开始）
								if(value == '高风险'){
									return "<span style='color:red'>"+value+"</span>";
								}else if(value == '灰区'){
									return "<span style='color:yellow'>"+value+"</span>";
								}else
									return value;

							}},
						{field:'t21Name',title:'T21',width:150,align:'center',sortable:true,resizable:true,
							formatter:function(value,rowData,rowIndex){
								//function里面的三个参数代表当前字段值，当前行数据对象，行号（行号从0开始）
								if(value == '高风险'){
									return "<span style='color:red'>"+value+"</span>";
								}else if(value == '灰区'){
									return "<span style='color:yellow'>"+value+"</span>";
								}else
									return value;

							}},
						{field:'z13Name',title:'Z13',width:150,align:'center',sortable:true,resizable:true},
						{field:'z18Name',title:'Z18',width:150,align:'center',sortable:true,resizable:true},
						{field:'z21Name',title:'Z21',width:150,align:'center',sortable:true,resizable:true},
						{field:'rawQCName',title:'质控结果',width:100,align:'center',sortable:true,resizable:true},
						{field:'age',title:'孕妇年龄',width:120,align:'center',sortable:true,resizable:true,
							formatter:function(value,rowData,rowIndex){
								if(value == null || value == 0){
									return "";
								}else 
									return value;
		               		} },
						{field:'gesWeek',title:'孕妇孕周',width:120,align:'center',sortable:true,resizable:true},
						{field:'sampleType',title:'样本类型',width:80,align:'center',sortable:true,resizable:true},
						{field:'unitFrom',title:'送检单位/部门',width:80,align:'center',sortable:true,resizable:true},
						{field:'samplingDate',title:'采样日期',width:80,align:'center',sortable:true,resizable:true},
						{field:'diagnosis',title:'临床诊断',width:80,align:'center',sortable:true,resizable:true},
						{field:'lastPeriod',title:'末次月经',width:80,align:'center',sortable:true,resizable:true},
						{field:'pregHistory',title:'孕产史',width:80,align:'center',sortable:true,resizable:true},
						{field:'ivfGestation',title:'妊娠类型',width:80,align:'center',sortable:true,resizable:true},
						{field:'NT',title:'NT值',width:80,align:'center',sortable:true,resizable:true},
						{field:'downRes',title:'唐筛结果',width:80,align:'center',sortable:true,resizable:true},
						{field:'downRatio',title:'唐筛比例',width:80,align:'center',sortable:true,resizable:true},
						{field:'specialInfo',title:'特殊情况说明',width:80,align:'center',sortable:true,resizable:true},
						{field:'bloodId',title:'全血ID',width:80,align:'center',sortable:true,resizable:true},
						{field:'fetusNum',title:'胎数',width:80,align:'center',sortable:true,resizable:true},
						{field:'hospitalCode',title:'医院编码',width:80,align:'center',sortable:true,resizable:true},
						{field:'indexNum',title:'index',width:80,align:'center',sortable:true,resizable:true},
						{field:'rawDataName',title:'原始数据量（M）',width:80,align:'center',sortable:true,resizable:true},
						{field:'q30Name',title:'Q30',width:80,align:'center',sortable:true,resizable:true},
						{field:'alignRateName',title:'比对率',width:80,align:'center',sortable:true,resizable:true},
						{field:'dupRateName',title:'Duplication 率',width:80,align:'center',sortable:true,resizable:true},
						{field:'urNumName',title:'Unique reads数量(M)',width:80,align:'center',sortable:true,resizable:true},
						{field:'GCName',title:'样本GC含量',width:80,align:'center',sortable:true,resizable:true}
						
		        ]],
		        onHeaderContextMenu: function(e, field){
	                e.preventDefault();
	                if (!cmenu){
	                    createColumnMenu();
	                }
	                cmenu.menu('show', {
	                    left:e.pageX,
	                    top:e.pageY
	                });
            	},
		       onSortColumn:function(sort, order){
		        //alert("sort:"+sort+" ;order="+order);
		            sortFlag = true;
		        }
		    }).datagrid('loadData', data);
		    //$('#ee').datagrid('enableFilter');
		    <%-- $('#ee').datagrid('enableFilter');
		   <%-- $('#ee').datagrid('enableFilter', [{
				field:'statusName',
				type:'combobox',
				options:{
				 	valueField : 'value',
		            textField : 'name',
		            data: [{'name':'未分析','value':'未分析'},{'name':'分析中','value':'分析中'},{'name':'已分析','value':'已分析'}],
			        editable: true,
		            multiple: false,
		            onChange: function(value){
		                if (value == ''){
		                	$('#ee').datagrid('removeFilterRule', 'geneId');
		                } else {
			                	$('#ee').datagrid('addFilterRule', {
			                        field: 'geneId',
			                        op: 'equal',
			                        value: value
				                    });
			                }
		                $('#ee').datagrid('doFilter');
		            },
					panelHeight:'auto'
				}
			}]);--%>
		});
		var cmenu = null;
        function createColumnMenu(){
            cmenu = $('#dg').appendTo('body');
            cmenu.menu({
                onClick: function(item){
                    if (item.iconCls == 'icon-ok'){
                        $('#ee').datagrid('hideColumn', item.name);
                        cmenu.menu('setIcon', {
                            target: item.target,
                            iconCls: 'icon-empty'
                        });
                    } else {
                        $('#ee').datagrid('showColumn', item.name);
                        cmenu.menu('setIcon', {
                            target: item.target,
                            iconCls: 'icon-ok'
                        });
                    }
                }
            });
            var fields = $('#ee').datagrid('getColumnFields');
            for(var i=0; i<fields.length; i++){
                var field = fields[i];
                var col = $('#ee').datagrid('getColumnOption', field);
                cmenu.menu('appendItem', {
                    text: col.title,
                    name: field,
                    iconCls: 'icon-ok'
                });
            }
        }
		function doSubmit(){
			var path = "${ctx}/sample/detailList.html";  
   			$('#queryForm').attr("action", path).submit(); 
		}
		
		function doSubmitForExcel(){
			var path = "${ctx}/sample/exportExcelForBatch.html";  
   			$('#queryForm').attr("action", path).submit(); 
		}
		
		
	</script>
</head>

<body>
<form action="" method="post" name="queryForm" id="queryForm">
<input type="hidden" name="batchId" value="${batchId }" style="scinput" />
<input type="hidden" name="id" value="${batchId }" style="scinput" />
<input type="hidden" name="flag" value="2" style="scinput" />
<input type="hidden" name="currentPage" id="currentPage" value="${currentPage }"/>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="${ctx}/batch.html">首页</a></li>
    <li><a href="${ctx}/batch.html">批次管理</a></li>
    <li><a href="${ctx}/batch.html">批次信息</a></li>
    <li><a href="#">查看分析结果</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    
    <div id="usual1" class="usual"> 
    
  	<div id="tab2" class="tabson">
  		<ul class="seachform">
	         <%--<c:if test="${roleId == '3'}">
			    <li><select name="" id="checkStatus" class="scinput" style="width:90px" >
			    	<option value="-1">请选择</option>
			    	<option value="1">通过</option>
		    		<option value="2">不通过</option>
			    </select>  </li>
			<li>
			    <label style="border-width: 0px; border-style: solid; border-radius: 11px; width: 70px; background-color:red; color: white;
			     margin-top: -1px; margin-left: 5px; float: center;cursor: pointer " 
			     onclick="javascript:updateStatusForBatch()" >&nbsp;&nbsp;&nbsp;&nbsp;批量审核</label>
			    </li>
			<li><label>&nbsp;</label></li>
	        <li><label>&nbsp;</label></li>
	        <li><label>&nbsp;</label></li>
		    </c:if> --%>
	        <li><input name="" type="button" class="btn" onclick="window.location.href='${ctx }/batch.html?page.currentPage=${currentPage }'" value="返回">&nbsp;&nbsp;</li>
	        <li>
	        	<label><font style="font-weight:bold;font-size: 20px">对照品结果：</font>
	       	
			        <c:choose>
					       <c:when test="${ batch.qualityControlResults != '' && batch.qualityControlResults == '通过' && batch.statusName == '已分析'}">
					             <td><font style="font-weight:bold;font-size: 20px" color="#009900">${batch.qualityControlResults }</font></td>
					       </c:when>
					       <c:when test="${batch.qualityControlResults != '' && batch.qualityControlResults == '不通过' && batch.statusName == '已分析'}">
					             <td><font style="font-weight:bold;font-size: 20px" color=red>${batch.qualityControlResults }</font></td>
					       </c:when>
					       <c:otherwise>
					              <td><font></font></td>
					       </c:otherwise>
					</c:choose>
				</label>
	        </li>
	        <li><label>&nbsp;</label></li>
	        <li><label>&nbsp;</label></li>
	        <li><label>&nbsp;</label></li>
    </ul>
    <ul class="seachform" style="width: 100%">
    <li><label>&nbsp;&nbsp;样本ID&nbsp;</label><input type="text" name="sampleId" value="${sample.sampleId }" class="scinput" style="width:100px;"/></li>
    <li><label>孕妇姓名</label><input type="text" name="name" value="${sample.name }" class="scinput" style="width:100px;"/></li>
    <li><label>送检医生</label><input type="text" name="doctorFrom" value="${sample.doctorFrom }" class="scinput" style="width:100px;" /></li>
    <li><label>样本状态</label>
    <select name="status" id="status" class="scinput" style="width:90px">
    	<option value="-1">所有</option>
    	<option value="0">未分析</option>
    	<option value="1">分析中</option>
    	<option value="2">已分析</option>
    </select>
    </li>
    	<ul class="toolbar">
	        <li onclick="doSubmit();"><span><img src="../images/t04.png"/></span>查询</li>
        </ul>
        <ul class="toolbar">
   	     	<li onclick="doSubmitForExcel()"><span><img src="../images/t05.png"/></span>导出Excel</li>
    	</ul>
    </ul>
      <div id="dg"></div>
      <table id="ee" style="height:auto;width:100%;"></table>
    ${sample.page.pageStr }
   <br/>
   	<ul>
   		<li><label style="color: red;font-weight:bold;font-size: 16px;">温馨提示：</label></li>
   		<br/>
   		<li><label>1、 右键表头，可实现列显示/隐藏</label></li>
   		<li><label>2、 单击表头，可实现列排序</label></li>
   		<li><label>3、 拖动表头分割线，可实现列宽度调节</label></li>
   	</ul>
  
    
    </div>  
       
	</div> 
	<script type="text/javascript"> 
      $("#usual1 ul").idTabs(); 
    </script>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
		function sltAllEntity(){
			if($("#sltAll").attr("checked")){
					$("input[name='sampleIds']:enabled").each(function(){
						$(this).attr("checked",true);
						});
				}else{
					$("input[name='sampleIds']").attr("checked",false);
				}			
			}
		function  updateStatusForBatch(){
			var ids="";
			var checkStatus=$("#checkStatus").val();
			$("input[name='sampleIds']:checked").each(function(i){
				if(ids){
					ids+=",";
					}
				ids+=$(this).val();
				});
			if(ids==''){
				alert("请选择样本");
				return;
				}
			if(checkStatus=='-1'){
				alert("请选择审核意见");
				return;
				}
			if(ids && checkStatus!='-1'){
				$.post("${ctx}/sample/updateStatusForBatch.html",
						{"ids":ids,"checkStatus":checkStatus,"batchId":${batchId }},
						function(data){
							if(data=='success'){
								window.location.reload();
								}else{
								alert("操作失败，请联系管理员");
								}
							}
						,"text");
				}
			}
		function editTips(id){
			var tips=$("#tips_"+id).val();
			if(tips=='5'){
				return;
				}else{
					$.ajax({
						url:"${pageContext.request.contextPath}/tipsEdit.html",
						type:'post',
						async:false,
						data:{'tbSampleId':id,'tips':tips,'tipsState':1},
						dataType:'text',
						success:function(data){
								if(data=='success'){
									alert("操作成功");
									window.location.reload();
									}
							}
						});
					}
			}
	</script>
    
    
    
    
    
    </div>

</form>
</body>

</html>
