<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title> 食品安全风险信息管理及预警系统</title>
		<style>
		input:focus {
			border:0px;
		}
		
		input:-webkit-autofill { 
		-webkit-box-shadow: 0 0 0px 1000px white inset; 
		}
		</style>
			<script type="text/javascript">
				window.onload = function(){
					var errInfo = '${errInfo}';
					if(errInfo != null &&  errInfo != ''){
						alert(errInfo);
						}
				};
			</script>
		<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	</head>
	<body>
<div style="width:90%;background:#eef8f7;   margin: 30px auto 0 auto ; ">
	<div style="width:100%; height:95px; padding-top:25px;background:#fff; margin:0 auto;border-bottom:0px solid #50b9e8; ">
        <div style=" width:90%;height:65px; margin:0 auto;">
             <a href="" style=" display:block; float:left; 30%; height:65px;margin-right:1%;">
                  <%--<img class="check_img" src="./images/1.png" style=" height:50px; border:0px;margin-top:5px; ">--%>
             </a>
			<!--<div style=" height:65px; color:#002872; font-weight:lighter; line-height:65px; text-align:left; background:url(&#39;./images/dljm.png&#39;) no-repeat 150px 0px;">-->
			&nbsp;<font style="font-family: '幼圆';color: #75c3bc;  font-size: 45px">食品安全风险信息管理及预警系统</font>
             </div>
         </div>
    </div>
    <!--yishangweiborder-->

	<div style="width:85%; height:330px; margin:0 auto;     "><!--background-color:#eff8f6;-->
       <!--<div style=" width:95%; height:230px; background:#eef8f7;margin:0 auto; padding-top:50px;padding-bottom:50px; background:url('http://pa.annoroad.com/img/tu.png') no-repeat">-->
		<div style=" width:95%; height:230px; background:#eef8f7;margin:0 auto; padding-top:50px;padding-bottom:50px;">
		<div style="  width:85%; margin:0 auto;">
			<div style=" float:left; width:45%;height:250px; background:url(&#39;./images/tu.png&#39;) no-repeat 0 -1px;"> 
            </div>
			<div style=" width:49%; padding-top:25px; float:right;  "> 
				<form name="form" id="signup_form" method="post" action="login.html" enctype="multipart/form-data">
				<input type="hidden" name="errInfo" id="errInfo" value="${errInfo}"/>
					<table style="width:100%;font-size:14px;font-weight:800; margin:0;padding:0;border:0;">	
						<tbody><tr style="height:50px; ">
							<td style=" width:23%; height:30px; line-height:30px; text-align:left;"><font style="font-family: '幼圆';font-size: 20px;font-weight: bold;">用户名：</font></td>
							<td>
							    <input type="text" name="loginname" id="loginname"  placeholder="请输入用户名"  data-required="required"  maxlength="20" style="height:28px; line-height:28px;width: 50%;border-radius: 18px;padding-left:20px;"/></td>
						</tr>
						 
						<tr style=" height:50px;">
							<td style="width:23%; height:30px; line-height:30px; text-align:left;"><font style="font-family: '幼圆';font-size: 20px;font-weight: bold;">密&nbsp;码：</font></td>
							<td>
								<input type="password" name="password" id="password"  placeholder="请输入密码" maxlength="15" style="height:28px; line-height:28px;width: 50%;border-radius: 18px;padding-left:20px;"/></td>
						</tr>
					</tbody></table>
			
					<div style="width:66%;margin-top:20px;margin-left:5px;height:30px;">
						<input type="image" src="./images/deng.png" style="width:100%; outline: none;"  id="login_r" name="" value="登录" class="coolb" onclick="javascript:check()"/>
					</div>
				</form>
			</div>
		</div>
        </div>
	</div>
   
 </div>
 <div style="margin-top:80px;">
  	<table align="center">
			<tr>
				<td style="vertical-align:middle; text-align:center;">
					<font style="font-family: '幼圆';font-size: 14px;" color="#777777">© 2019 北京林业大学&nbsp;&nbsp;版权所有</font>
				</td>
			</tr>
			<tr>
				<td style="vertical-align:middle; text-align:center;width: 800px">
					<font style="font-family: '幼圆';font-size: 14px;" color="#777777">版本号：1.0</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<font style="font-family: '幼圆';font-size: 14px;" color="#777777">联系电话：13301283667</font>
				</td>
			</tr>
		<br>
		<tr>
			<td style="vertical-align:middle; text-align:center;width: 800px">
				<font style="font-family: '幼圆';font-size: 14px;" color="#777777">公司地址：北京市海淀区清华东路35号</font>
			</td>
		</tr>
		</table>
 </div>

	<script type="text/javascript">
	
			$(function(){
				$('#loginname').focus();
			});
	
			document.onkeydown = function(event){
				var e = event || window.event || arguments.callee.caller.arguments[0];
				if(e && e.keyCode==13){ // Enter
					check();
				}
			}
	
	
			function check(){
				if($("#loginname").val()==""){
					//$("#nameerr").show();
					//$("#nameerr").html("用户名不得为空！");
					alert("用户名不得为空！");
					$("#loginname").focus();
					return false;
				}
				if($("#password").val()==""){
					//$("#pwderr").show();
					//$("#pwderr").html("密码不得为空！");
					alert("密码不得为空！");
					$("#password").focus();
					return false;
				}
				document.form.submit()
			}
	//解决iframe下系统超时无法跳出iframe框架的问题
		if (window != top)
		top.location.href = location.href;
	
	</script>
</body>

</html>
   