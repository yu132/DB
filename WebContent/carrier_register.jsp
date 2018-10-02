<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
<meta charset="UTF-8">
<title>送餐人员注册</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate icon" type="image/png" href="assets/i/favicon.png">
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<style>
.header {
	text-align: center;
}

.header h1 {
	font-size: 200%;
	color: #333;
	margin-top: 30px;
}

.header p {
	font-size: 14px;
}
</style>
</head>
<body>
	<div class="header">
		<div class="am-g">
			<h1>DB design</h1>
			<p>
				数据库课程设计<br />送餐人员注册页面
			</p>
		</div>
		<hr />
	</div>
	<div class="am-g">
		<div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
			<h3>注册</h3>
			<hr>

			<form method="post" class="am-form" action="carrier_register.action">

				<label for="account">账号:</label> 
				<input class="am-form-field" type="input" name="username" id="account" value=""> <br> 
				
				<label for="password">密码:</label>
				<input type="password" name="password" id="password" value=""> <br>
				
				<label for="account">昵称:</label> 
				<input class="am-form-field" type="input" name="nickname" id="account" value=""> <br> 
				
				<label for="account">邮箱:</label> 
				<input class="am-form-field" type="input" name="eMailAddress" id="account" value=""> <br> 
				
				<label for="account">姓名:</label> 
				<input class="am-form-field" type="input" name="name" id="account" value=""> <br> 
				
				<label for="account">手机号码:</label> 
				<input class="am-form-field" type="input" name="phone" id="account" value=""> <br> 
				
				<br />
				
				<div class="am-cf">
					<input type="submit" name="" value="注 册"
						class="am-btn am-btn-primary am-btn-sm am-fl"> 
				</div>

			</form>

			<hr>
			<p>2018 DB design</p>
		</div>
	</div>
</body>
</html>