<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ page import="db.entity.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
<meta charset="UTF-8">
<title>查看菜单信息</title>
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


</script>

</head>
<body>
	<div class="header">
		<div class="am-g">
			<h1>DB design</h1>
			<p>
				数据库课程设计<br/>查看菜单信息页面
			</p>
		</div>
		<hr />
	</div>
	
		<div class="am-g">
		<div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
			<h3>查看菜单信息</h3>
			<hr>

				<table class="am-table am-table-bordered">
				 	 <caption><b> <font size="5" >菜单信息</font></b></caption>
					 <thead>
						 <tr>
			                <td> <b>菜单名称</b> </td>
			                <td> <b>菜单价格</b> </td>
			                <td> <b>菜单折扣</b> </td>
			                <td></td>
			            </tr>
		            </thead>
					<c:forEach var="menu" items="${MenuList}" varStatus="status">
			            <tr>
			                <td><a href="#" class=""><font size="5">${menu.menuName}</font></a></td>
			                <td><a href="#" class=""><font size="5">${menu.menuPrice}</font></a></td>
			                <td><a href="#" class=""><font size="5">${menu.menuDiscount}</font></a></td>
			                <td>
			                	<a href="delete_menu.action?menuID=${menu.menuID}" target="_parent"><font size="5">删除</font></a>
			                </td>
			            </tr>
					</c:forEach>
				 </table>
				 
				 <a class="am-fr" href="add_menu.html" target="_blank"><font size="3">添加一个菜单</font></a>
				
				<br/>
				
				<a class="am-btn am-btn-primary am-btn-block" href="go_home_page.action" target="_parent">回到主页</a>

			<hr>
			<p>2018 DB design</p>
		</div>
	</div>
	
</body>
</html>