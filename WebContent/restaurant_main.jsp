<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
<meta charset="UTF-8">
<title>餐馆主页</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate icon" type="image/png" href="assets/i/favicon.png">
<link rel="stylesheet" href="assets/css/amazeui.min.css" />
<link rel="stylesheet" href="assets/css/customer_main.css" />
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

}
</style>
</head>
<body>
	<div class="header">
		<div class="am-g">
			<h1>DB design</h1>
			<p>
				数据库课程设计<br />餐馆主页
			</p>
		</div>
		<hr />
	</div>

	<div class="am-g">
		<div class="am-u-lg-8 am-u-md-12 am-u-sm-centered">
			<div><h1 align="center">欢迎使用外卖送餐系统</h1></div>
			<div><h2>餐馆用户：${CurrentUser.restaurantAccountInformation.username}，你需要什么服务呢：</h2></div>
			
			<div>
				<p class="p1">
					<a class="am-btn am-btn-primary" href="check_order_restaurant.action" target="_blank">查看当前订单</a>
					<a class="am-btn am-btn-primary" href="check_menu.action" target="_blank">添加/删除菜单</a>
					<a class="am-btn am-btn-primary" href="get_state_restaurant.action" target="_blank">修改营业状态</a>
					<a class="am-btn am-btn-primary" href="get_information_restaurant.action" target="_blank">修改餐馆信息</a>
					<a class="am-btn am-btn-primary" href="get_money.action" target="_blank">查看餐馆盈利</a>
				</p>
			</div>
			
			<br/>
			
			<hr>
			<p>2018 DB design</p>
		</div>
	</div>
</body>
</html>