<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ page import="db.entity.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
<meta charset="UTF-8">
<title>骑手订单</title>
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
				数据库课程设计<br />骑手订单页面
			</p>
		</div>
		<hr />
	</div>
	<div class="am-g">
		<div class="am-u-lg-12 am-u-md-12 am-u-sm-centered">
			
			 <table class="am-table am-table-bordered">
			 
			 	 <caption><b> <font size="5" >订单</font></b></caption>
			 	 
				 <thead>
					 <tr>
		                <td> <b>订单编号</b> </td>
		                <td> <b>订单下单时间</b> </td>
		                <td> <b>餐馆名称</b> </td>
		                <td> <b>订单价格</b> </td>
		                <td> <b>订单状态</b> </td>
		                <td> <b>客户姓名</b> </td>
		                <td> <b>客户地址</b> </td>
		                <td> <b>客户联系电话</b> </td>
		                <td></td>
		            </tr>
	            </thead>
	            
	            <% int index=0; %>
	            
				<c:forEach var="order" items="${OrderList}" varStatus="status">
		            <tr>
		               <td><a href="#" class=""><font size="5">${order.orderID}</font></a></td>
		               <td><a href="#" class=""><font size="5">
		               		<fmt:formatDate value='<%= new Date(((Order)(((List)(request.getAttribute("OrderList"))).get(index++))).getOrderTime())%>' pattern="yyyy年MM月dd日 hh时mm分ss秒" type="date" dateStyle="long" />
		                </font></a></td>
		               <td><a href="#" class=""><font size="5">${order.restaurant.restaurantName}</font></a></td>
		               <td><a href="#" class=""><font size="5">${order.orderPrice}</font></a></td>
		               <td><a href="#" class=""><font size="5">${order.orderState}</font></a></td>
		               <td><a href="#" class=""><font size="5">${order.customerReceivingInformation.customerName}</font></a></td>
		               <td><a href="#" class=""><font size="5">${order.customerReceivingInformation.customerPhone}</font></a></td>
		               <td><a href="#" class=""><font size="5">${order.customerReceivingInformation.customerAddress}</font></a></td>
		                <c:choose>  
	  
						    <c:when test="${order.orderState eq '送餐人员正在赶往商家'}">
						   
						    	 <td><a class="am-btn am-btn-primary" href="change_order_state_to_get_dishes.action?orderId=${order.orderID}" class="">已取到餐</a></td>
						   		
						    </c:when>  
						    
						       <c:when test="${order.orderState eq '送餐人员已经取到餐'}">
						   
						    	 <td><a class="am-btn am-btn-primary" href="change_order_state_to_finish.action?orderId=${order.orderID}" class="">客户已收货</a></td>
						   		
						    </c:when>  
						     
						   <c:when test="${order.orderState eq '送餐完毕'}">
		            			<td><a class="am-btn am-btn-primary" href="get_comment_to_customer.action?customerId=${order.customerReceivingInformation.customer.customerID}" class="">评价该客户</a></td>
						   
						    </c:when>
	   					</c:choose>
		            </tr>
				</c:forEach>
				
			 </table>
			 
			 <input id="btnClose" type="button" value="关 闭 本 页" class="am-btn am-btn-primary am-btn-block" onClick="custom_close()" />

			<hr>
			<p>2018 DB design</p>
		</div>
	</div>
</body>
</html>