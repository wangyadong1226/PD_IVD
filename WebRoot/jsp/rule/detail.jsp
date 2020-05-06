<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>

<script type="text/javascript">
    KE.show({
        id : 'content7',
        cssPath : './index.css'
    });
  </script>
  	<link rel="stylesheet" type="text/css" href="${ctx}/easyui1_5/themes/metro/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui1_5/themes/icon.css">
	<script type="text/javascript" src="${ctx}/easyui1_5/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui1_5/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui1_5/datagrid-filter.js"></script>
<script type="text/javascript">
var data = ${json};
var rows = data.rows;
console.log(rows);
$(function(){
	var sortFlag = false;
    $('#ee').datagrid({
    	title:'样本信息',
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
				{field:'name',title:'孕妇姓名',width:100,align:'center',sortable:true,resizable:true},
				{field:'sampleId',title:'样本ID',width:100,align:'center',sortable:true,resizable:true},
				{field:'libId',title:'文库ID',width:150,align:'center',sortable:true,resizable:true},
				{field:'indexNum',title:'index',width:100,align:'center',sortable:true,resizable:true}
                	]],
        columns:[[
				{field:'bloodId',title:'全血ID',width:100,align:'center',sortable:true,resizable:true},
				{field:'age',title:'孕妇年龄',width:120,align:'center',sortable:true,resizable:true,
					formatter:function(value,rowData,rowIndex){
						if(value == null || value == 0){
							return "";
						}else
							return value;
               		} },
				{field:'gesWeek',title:'孕妇孕周',width:120,align:'center',sortable:true,resizable:true},
				{field:'fetusNum',title:'胎数',width:100,align:'center',sortable:true,resizable:true},
				{field:'NT',title:'NT值',width:100,align:'center',sortable:true,resizable:true},
				{field:'downRes',title:'唐筛结果',width:100,align:'center',sortable:true,resizable:true},
				{field:'downRatio',title:'唐筛比例',width:100,align:'center',sortable:true,resizable:true},
				{field:'specialInfo',title:'特殊情况说明',width:120,align:'center',sortable:true,resizable:true}

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


</script>
	<script>
		function doDel(id){
			if ( window.confirm("确定删除？") ){
				window.location.href = "?o=doDel&id=" + id;
			}
		}
		function doBack(){
			var path = "${ctx}/batch.html";
   			$('#queryForm').attr("action", path).submit();
		}
	</script>
</head>

<body>
<form action="detailList.html"  method="post" name="queryForm" id="queryForm">
<input type="hidden" name="id" value="${batchId }" style="scinput" />
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="${ctx}/batch.html">首页</a></li>
    <li><a href="${ctx}/batch.html">信息管理</a></li>
    <li><a href="${ctx}/batch.html">批次信息</a></li>
    <li><a href="#">查看详情</a></li>
    </ul>
    </div>

    <div class="formbody">


    <div id="usual1" class="usual">

  	<div id="tab2" class="tabson">
		<ul class="seachform">
			<li><label>样本ID</label><input type="text" name="sampleId" id="sampleId" class="scinput" value="" style="width:100px;"/></li>
			<li><label>孕妇姓名</label><input type="text" name="name" id="name" class="scinput" value="" style="width:100px;"/></li>
			</li>
			<ul class="toolbar">
				<li onclick="javascript:search();"><span><img src="../images/t04.png"/></span>查询</li>
			</ul>
		</ul>


		<ul class="seachform">
	        <li><label>&nbsp;</label><input name="" type="button" class="btn" onclick="doBack()" value="返回"></li>
    </ul>
		<table class="tablelist">
			<thead>
			<tr>
				<th width="50px">编号<i class="sort"></i></th>
				<th width="100px">食品次亚类</th>
				<th width="100px">包装分类</th>
				<th width="100px">区域类型</th>
				<th width="100px">抽样地点</th>
				<th width="100px">抽样环节</th>
				<th width="100px">抽样省</th>
				<th width="100px">样品状态</th>
				<th width="100px">样品类型</th>
				<th width="100px">生产省</th>
				<th width="100px">经济地带</th>
				<th width="100px">抽样季度</th>
				<th width="100px">生产季度</th>
				<th width="100px"> 保质率</th>
				<th width="100px">年销售额</th>
				<th width="100px">山梨酸</th>
				<th width="100px">亚硝酸盐</th>
				<th width="100px">铅</th>
				<th width="100px">铬</th>
				<th width="100px">镉</th>

			</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${not empty sampleList}">
					<c:forEach items="${sampleList}" var="sample" varStatus="vs">
						<tr>
							<td>${vs.index+1}</td>
						<td>${sample.foodSubclass}</td>
						<td>${sample.packing}</td>
							<td>${sample.area}</td>
							<td>${sample.location}</td>
							<td>${sample.link}</td>
							<td>${sample.province}</td>
									<td>${sample.sampleState}</td>
							<td>${sample.sampeType}</td>
							<td>${sample.productionProvince}</td>
							<td>${sample.zone}</td>
							<td>${sample.sampleQuarter}</td>
							<td>${sample.productionQuarter}</td>
							<td>${sample.guaranteeQualityRate}</td>
							<td>${sample.salesVolume}</td>
							<td>${sample.sorbicAcid}</td>
							<td>${sample.nitrite}</td>
							<td>${sample.lead}</td>
							<td>${sample.chromium}</td>
							<td>${sample.cadmium}</td>

						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="20">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>

			</tbody>
		</table>
    ${sample.page.pageStr }
     <br/>
    </div>

	</div>
	<script type="text/javascript">
      $("#usual1 ul").idTabs();
    </script>

    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>





    </div>

</form>
</body>

</html>
