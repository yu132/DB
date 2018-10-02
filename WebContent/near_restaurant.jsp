<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ page import="db.entity.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
<meta charset="UTF-8">
<title>附近餐馆搜索结果</title>
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
				数据库课程设计<br />附近餐馆搜索结果页面
			</p>
		</div>
		<hr />
	</div>
	<div class="am-g">
		<div class="am-u-lg-10 am-u-md-12 am-u-sm-centered">
			<h3><% 
					List<Restaurant> l=(List<Restaurant>)request.getAttribute("NearRestaurant");
					out.print("我们帮您搜索到你附近5公里内共有"+l.size()+"家餐馆");
				%></h3>
			<hr>
			
			 <table class="am-table am-table-bordered">
			 	 <caption><b> <font size="5" >搜索结果:${userLocation}</font></b></caption>
				 <thead>
					 <tr>
		                <td> <b>餐馆名称</b> </td>
		                <td> <b>餐馆地址</b> </td>
		                <td></td>
		            </tr>
	            </thead>
				<c:forEach var="restaurant" items="${NearRestaurant}">
		            <tr>
		                <td><a target="_blank" href="restaurant_menu.action?id=${restaurant.restaurantID}"><font size="5">${restaurant.restaurantName}</font></a></td>
		                <td><a target="_parent" href="search.action?userLocation=${restaurant.restaurantAddress}"><font size="5">${restaurant.restaurantAddress}</font></a></td>
		            	<td><a class="am-btn am-btn-primary am-btn-block" href="get_restaurant_comment.action?restaurantId=${restaurant.restaurantID}" class="">查看商家评价</a></td>
		            </tr>
				</c:forEach>
			 </table>

			<hr>
			<p>2018 DB design</p>
		</div>
	</div>
</body>
</html>