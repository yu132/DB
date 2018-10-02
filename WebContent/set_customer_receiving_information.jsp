<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ page import="db.entity.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
<meta charset="UTF-8">
<title>设置收货信息</title>
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

var m = new Map();

<c:forEach var="customerReceivingInformation" items="${CustomerReceivingInformationList}" varStatus="status">
	m.set(${status.index}, ${customerReceivingInformation.customerReceivingInformationID});
</c:forEach>

function addvalueForm(){
	 var baselineTypeHtmlCol = document.getElementsByName("customerReceivingInformationId");

	 for(var i = 0; i < baselineTypeHtmlCol.length; i ++){
		 if(baselineTypeHtmlCol[i].checked){
		 	baselineTypeHtmlCol[i].value=m.get(i);
		 	
			console.log(baselineTypeHtmlCol[i].value);
		 }
	 }
	 
	 
	 return true;
}


</script>

</head>
<body>
	<div class="header">
		<div class="am-g">
			<h1>DB design</h1>
			<p>
				数据库课程设计<br />设置收货信息页面
			</p>
		</div>
		<hr />
	</div>
	<div class="am-g">
		<div class="am-u-lg-8 am-u-md-10 am-u-sm-centered">
			<form method="post" action="set_customer_receiving_information.action" onsubmit="return addvalueForm()" id="select_form">
				<input name="orderIndex" type="hidden" value="${OrderIndex}" >
				 <table class="am-table am-table-bordered">
				 	 <caption><b> <font size="5" >收货信息</font></b></caption>
					 <thead>
						 <tr>
			                <td> <b>收货人姓名</b> </td>
			                <td> <b>收货人联系电话</b> </td>
			                <td> <b>收货人地址</b> </td>
			            </tr>
		            </thead>
					<c:forEach var="customerReceivingInformation" items="${CustomerReceivingInformationList}" varStatus="status">
			            <tr>
			                <td><a href="#" class=""><font size="5">${customerReceivingInformation.customerName}</font></a></td>
			                <td><a href="#" class=""><font size="5">${customerReceivingInformation.customerPhone}</font></a></td>
			                <td><a href="#" class=""><font size="5">${customerReceivingInformation.customerAddress}</font></a></td>
			                <td>
			                	<input name="customerReceivingInformationId" type="radio" id="radio_${status.index}">
			                </td>
			            </tr>
					</c:forEach>
				 </table>
				 
				 <a class="am-fr" href="add_customer_receiving_information.html" target="_blank"><font size="3">没有当前的收货地址，想前去添加一个</font></a>
				 
				<input style="line-height: 2.0;" type="submit" value="设 置 收 货 信 息 并 去 支 付"class="am-btn am-btn-primary am-btn-block"/>
			 </form>

			<hr>
			<p>2018 DB design</p>
		</div>
	</div>
</body>
</html>