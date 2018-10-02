<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ page import="db.entity.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
<meta charset="UTF-8">
<title>餐馆详细订单信息</title>
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
				数据库课程设计<br />餐馆详细订单信息页面
			</p>
		</div>
		<hr />
	</div>
	<div class="am-g">
		<div class="am-u-lg-8 am-u-md-10 am-u-sm-centered">
			<h1 align="center">订单详情</h1>
		
			<h2>餐馆：${Order.restaurant.restaurantName} &emsp; &emsp; 订单状态：${Order.orderState} &emsp; &emsp; 送餐人员：${Order.carrier.carrierName}</h2>
			<hr>
			
			 <table class="am-table am-table-bordered">
			 	 <caption><b> <font size="5" >购买清单</font></b></caption>
				 <thead>
					 <tr>
		                <td> <b>名称</b> </td>
		                <td> <b>数量</b> </td>
		            </tr>
	            </thead>
				<c:forEach var="menuOrder" items="${Order.dishes}" varStatus="status">
		            <tr>
		                <td><a href="#" class=""><font size="5">${menuOrder.menu.menuName}</font></a></td>
		                <td><a href="#" class=""><font size="5">${menuOrder.number}</font></a></td>
		            </tr>
				</c:forEach>
				<tr><td colspan="2"><font size="5">总计金额：${Order.orderPrice}￥</font></td></tr>
			 </table>
			 
			 <hr>
			
			<a class="am-btn am-btn-primary am-btn-block" href="check_order_restaurant.action" target="_parent">返 回</a>

			<hr>
			<p>2018 DB design</p>
		</div>
	</div>
</body>
</html>