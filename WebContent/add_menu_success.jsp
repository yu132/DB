<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ page import="db.entity.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
<meta charset="UTF-8">
<title>添加菜单成功</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate icon" type="image/png" href="assets/i/favicon.png">
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/restaurant_menu.css" />
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
				数据库课程设计<br />添加菜单成功页面
			</p>
		</div>
		<hr />
	</div>
	
		<div class="am-g">
		<div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
			<h3>添加菜单成功</h3>
			<hr>
			<a class="am-btn am-btn-primary" href="add_menu.html" target="_parent">再添加一个</a>
			<a class="am-btn am-btn-primary" href="go_home_page.action" target="_parent">回到主页</a>
			<input id="btnClose" type="button" value="关闭本页" class="am-btn am-btn-primary" onClick="custom_close()" />
			<hr>
			<p>2018 DB design</p>
		</div>
	</div>
	
</body>
</html>