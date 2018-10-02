<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ page import="db.entity.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
<meta charset="UTF-8">
<title>确认订单</title>
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
				数据库课程设计<br />确认订单页面
			</p>
		</div>
		<hr />
	</div>
	<div class="am-g">
		<div class="am-u-lg-10 am-u-md-12 am-u-sm-centered">
			<h2>餐馆：${RestaurantInformation.restaurantName} 地址：${RestaurantInformation.restaurantAddress}</h2>
			<hr>
			<form method="post" action="get_customer_receiving_information.action">
				<input name="OrderIndex" type="hidden" value="${OrderIndex}" >
				 <table class="am-table am-table-bordered">
				 	 <caption><b> <font size="5" >菜单</font></b></caption>
					 <thead>
						 <tr>
			                <td> <b>名称</b> </td>
			                <td> <b>数量</b> </td>
			            </tr>
		            </thead>
					<c:forEach var="menu" items="${menuList}" varStatus="status">
			            <tr>
			                <td><a href="#" class=""><font size="5">${menu.menuName}</font></a></td>
			                <td><a href="#" class=""><font size="5">${menu.menuNumber}</font></a></td>
			            </tr>
					</c:forEach>
					<tr><td colspan="2"><font size="5">总计金额：${TotalMoney}￥+配送费(根据收货地址确定)</font></td></tr>
				 </table>
				 
				<input style="line-height: 2.0;" type="submit" value="确 认 并 设 置 收 货 地 址" class="am-btn am-btn-primary am-btn-block"/>
			 </form>

			<hr>
			<p>2018 DB design</p>
		</div>
	</div>
</body>
</html>