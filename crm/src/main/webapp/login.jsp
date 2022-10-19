<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script>
		$(function (){
			if(window.top!=window){
				window.top.location=window.location;
			}
			//清空文本框内容
			$("#loginAct").val("");
			$("#loginPwd").val("");
			//页面加载完毕后 用户文本框自动获得焦点
			$("#loginAct").focus();

			//为登录按钮绑定事件，执行登录操作
			$("#submitbtn").click(function (){
				login();
			})
			//为当前页面绑定键盘事件
			//event参数可以取得键盘输入的值
			$(window).keydown(function (event){
				if (13==event.keyCode){
					login();
				}

			})

		})
		function login(){
			// alert("登录操作");
			//验证账号密码不能为空
			//去掉文本左右空格
			var loginAct=$.trim($("#loginAct").val());
			var loginPwd=$.trim($("#loginPwd").val());
			if (loginAct==""||loginPwd==""){
				$("#msg").html("账号密码不能为空");
				return false;
			}
			$.ajax({
				url:"settings/user/login.do",
				data:{
					"loginAct":loginAct,
					"loginPwd":loginPwd
				},
				type:"post",
				dataType:"json",
				success: function (data) {


					if (data.success) {
						window.location.href = "workbench/index.jsp"
					} else {
						//alert(data.msg);
						$("#msg").html(data.msg);
					}
				}
			})
		}

		//后台验证登录
	</script>

</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/preview1.jpg" style="width: 80%; height:30%; position: relative; top: 50px;">

	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">

		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">&nbsp<span style="font-size: 12px;">刘小安</span></div>
	</div>

	<div b style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">

		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.jsp" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="loginAct">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">

							<span id="msg" style="color: red"></span>

					</div>
					<button type="button" id="submitbtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>

	</div>
<div>
	<p class="1h">
	</p>
</div>

</body>
<footer style="position: fixed ;margin-left: 800px; bottom: 0px;">
	<img src="http://www.beian.gov.cn/img/new/gongan.png">
 	<a class="text-color" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11010502050982" target="_blank">京公网安备11010502050982号</a>&nbsp&nbsp京ICP备2022028401号

</footer>
</html>