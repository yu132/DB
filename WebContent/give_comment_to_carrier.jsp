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
<title>给骑手发送评论</title>
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

}
</style>
</head>
<body>
	<div class="header">
		<div class="am-g">
			<h1>DB design</h1>
			<p>
				数据库课程设计<br />客户给骑手发送评论页面
			</p>
		</div>
		<hr />
	</div>
	
	<div class="header">
		<div class="am-g">
			<h1>给骑手：${Name} 评价</h1>
		</div>
		<br/>
	</div>
				
	<div class="am-g">
		<div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
				
			<form method="post" class="am-form" action="give_comment_to_carrier.action?carrierId=${carrierId}">
			
				<div class="am-g">
				
				<label for="type">评分:</label>  
				<select name="point" id="select_point">
					<option value="1" id="o_1">1</option>
					<option value="2" id="o_2">2</option>
					<option value="3" id="o_3">3</option>
					<option value="4" id="o_4">4</option>
					<option value="5" id="o_5">5</option>
				</select>
				<br />

				<label for="type">评论:</label>  
				<textarea name="comment" clos=",50" rows="5" warp="virtual">${Comment.comment}</textarea>
				
				<br/>

				<input type="submit" name="" value="提交评论"
					class="am-btn am-btn-primary am-btn-block"/>

				</div>
				
			</form>

			<hr>
			<p>2018 DB design</p>
		</div>
	</div>
</body>

<script type="text/javascript">
var option=document.getElementById("o_${Comment.point}");
option.selected=true;
</script>

</html>