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
<title>餐馆菜单</title>
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
function add1(id)
{
	var input = document.getElementById(id);
	
	if(!isNotANumber(parseInt(input.value)))
		input.value = input.value.replace(/[^0-9]/ig,"");
	
	if(!isNotANumber(parseInt(input.value))){
		input.value = 0;
	}
	
	input.value=parseInt(input.value)+1;
}

function minus1(id)
{
	var input = document.getElementById(id);
	
	if(!isNotANumber(parseInt(input.value)))
		input.value = input.value.replace(/[^0-9]/ig,"");
	
	if(!isNotANumber(parseInt(input.value))){
		input.value = 0;
	}

	if(parseInt(input.value)>0)
		input.value=parseInt(input.value)-1;
}

function isNotANumber(inputData) {
　　if (parseFloat(inputData).toString() == "NaN") 
　　　　return false;
　	else 
　　　　return true;
　　
}

</script>

</head>
<body>

	<div class="header">
		<div class="am-g">
			<h1>DB design</h1>
			<p>
				数据库课程设计<br />餐馆菜单页面
			</p>
		</div>
		<hr />
	</div>
	
	<div class="am-g">
		<div class="am-u-lg-10 am-u-md-12 am-u-sm-centered">
			<h2>餐馆：${RestaurantInformation.restaurantName} 地址：${RestaurantInformation.restaurantAddress}</h2>
			<hr>
			<form method="post" action="confirm_order.action">
			
				<input name="restaurantId" type="hidden" value="${RestaurantInformation.restaurantID}" >
			
				 <table class="am-table am-table-bordered">
				 
				 	 <caption><b> <font size="5" >菜单</font></b></caption>
				 	 
					 <thead>
						 <tr>
			                <td> <b>名称</b> </td>
			                <td> <b>价格</b> </td>
			                <td> <b>所需数量</b> </td>
			            </tr>
		            </thead>
		            
					<c:forEach var="menu" items="${RestaurantMenu}" varStatus="status">
			            <tr>
			                <td><a href="#" class=""><font size="5">${menu.menuName}</font></a></td>
			                <td class="">
			                 	<a href="#">
					                <c:choose>
					                	<c:when test="${menu.menuDiscount==1.0}"><font size="5"> ${menu.menuPrice}￥</font></c:when>  
					                	<c:otherwise>
											<font size="5">
												<fmt:formatNumber value="${menu.menuPrice*menu.menuDiscount}" type="currency" pattern="#0.00￥"/>
													
													<span style="text-decoration:line-through">
														<font size="3">
															<fmt:formatNumber value="${menu.menuPrice}" type="currency" pattern="#0.00￥"/>
														</font>
													</span>
												&nbsp;${menu.menuDiscount*10}折
											</font>
										</c:otherwise> 
					                </c:choose>
					               
				               </a>
			                </td>
			                
			                
			                
			                <td style="width:120px">
			                <div   class="am-input-group" >
			                	<input name="menuList[${status.index}].menuId" type="hidden" value="${menu.menuID}" >
			                	<input name="menuList[${status.index}].menuName" type="hidden" value="${menu.menuName}" >
				                <input name="menuList[${status.index}].menuNumber" style="width:60px" class="am-form-field" type="text" id="input_${status.index+1}" value="0"/>
			               
						      <span class="am-input-group-btn">
						        <input  type="button" value="+1"class="am-btn am-btn-primary am-btn-sm" onclick="add1('input_${status.index+1}')" ></input>
				                <input  type="button" value="-1"class="am-btn am-btn-primary am-btn-sm" onclick="minus1('input_${status.index+1}')"></input>
						      </span>
			               </div>
			                </td>
			            </tr>
					</c:forEach>
					
				 </table>
				<input style="line-height: 2.0;" type="submit" value="购 买"class="am-btn am-btn-primary am-btn-block"/>
			 </form>

			<hr>
			<p>2018 DB design</p>
		</div>
	</div>
</body>
</html>