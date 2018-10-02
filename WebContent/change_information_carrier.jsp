<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ page import="db.entity.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
<meta charset="UTF-8">
<title>修改骑手信息</title>
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
				数据库课程设计<br />修改骑手信息页面
			</p>
		</div>
		<hr />
	</div>
	
	<div class="am-g">
		<div class="am-u-lg-6 am-u-md-12 am-u-sm-centered">
			<div><h1 align="center">修改餐馆信息</h1></div>
			
			<div>
			
				<form method="post" class="am-form" action="change_information_carrier.action">
	
					<label for="restaurantName">骑手联系电话:</label> 
					<input type="text" name="carrierPhone" value="${Carrier.carrierPhone}"> <br/> 
					
					<input type="submit" value="修 改 骑 手 信 息" class="am-btn am-btn-primary am-btn-block"> 
				</form>

			</div>
			
			<br/>
			
			<hr>
			<p>2018 DB design</p>
		</div>
	</div>
	
</body>
</html>