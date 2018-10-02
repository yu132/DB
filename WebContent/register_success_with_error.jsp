<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>注册异常</title>
	<meta name="description" content="这是一个404页面">
	<meta name="keywords" content="404">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="renderer" content="webkit">
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<link rel="icon" type="image/png" href="assets/i/favicon.png">
	<link rel="apple-touch-icon-precomposed"
		href="assets/i/app-icon72x72@2x.png">
	<meta name="apple-mobile-web-app-title" content="Amaze UI" />
	<link rel="stylesheet" href="assets/css/amazeui.min.css" />
	<link rel="stylesheet" href="assets/css/admin.css">
	
	<script type="text/javascript">
		//这个脚本是 ie6和ie7 通用的脚本
		function custom_close(){
		 if(confirm("您确定要关闭本页吗？")){
		     window.opener=null;
		     window.open('','_self');
		     window.close();
		 }
		 else{
		 }
		}
	</script>
</head>
<body>
	<div class="header">
		<div class="am-g">
			<h1>DB design</h1>
			<p>
				数据库课程设计<br />注册异常页面
			</p>
		</div>
		<hr />
	</div>
	<div class="am-g">
		<div class="am-u-lg-8 am-u-md-10 am-u-sm-centered">
			<h1 align="center">注册成功</h1>
			
			<p>但是我们并不能成功的将邮件发往您的邮箱，请确认您的邮箱是否正确<p>
			
			<input id="btnClose" type="button" value="关闭本页" class="am-btn am-btn-primary am-btn-block" onClick="custom_close()" />

			<hr>
			<p>2018 DB design</p>
		</div>
	</div>

</body>
</html>