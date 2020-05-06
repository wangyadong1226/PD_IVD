<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>无标题文档</title>
  </head>

  <body style=" min-width: 80px; min-height:70px;">
	<form action="sample" name="" id="" >
	<div class="place" style="width:150px ">
    <ul class="placeul">
    <li>批次号:${tbBatch.batchNum }</li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div id="usual1" class="usual">

		<div id="tab2" class="tabson">
			<table class="tablelist">
				<thead>
					<tr>
						<td>
							EDTA GC 偏移13:&nbsp&nbsp&nbsp&nbsp${tbBatch.edtaGcDrift13 }
						</td>
					</tr>
					<tr>
						<td>
							EDTA GC 偏移18:&nbsp&nbsp&nbsp&nbsp${tbBatch.edtaGcDrift18 }
						</td>
					</tr>
					<tr>
						<td>
							EDTA GC 偏移21:&nbsp&nbsp&nbsp&nbsp${tbBatch.edtaGcDrift21 }
						</td>
					</tr>
					<tr>
						<td>
							Streck GC 偏移13:&nbsp&nbsp&nbsp&nbsp${tbBatch.streckGcDrift13 }
						</td>
					</tr>
					<tr>
						<td>
							Streck GC 偏移18:&nbsp&nbsp&nbsp&nbsp${tbBatch.streckGcDrift18 }
						</td>
					</tr>
					<tr>
						<td>
							Streck GC 偏移21:&nbsp&nbsp&nbsp&nbsp${tbBatch.streckGcDrift21 }
						</td>
					</tr>
				</thead>
			</table>
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
