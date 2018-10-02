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
<title>餐馆得到的评论</title>
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
				数据库课程设计<br />查看餐馆得到的评论页面
			</p>
		</div>
		<hr />
	</div>
	<div class="am-g">
		<div class="am-u-lg-8 am-u-md-10 am-u-sm-centered">
			
			 	 
			<table class="am-table am-table-bordered">
			 
			 	 <caption><b> <font size="5" >餐馆评论</font></b></caption>
			 	 
				 <thead>
					 <tr>
		                <td> <b>评论的客户</b> </td>
		                <td> <b>评分</b> </td>
		                <td> <b>评论</b> </td>
		            </tr>
	            </thead>
	            
	            
				<c:forEach var="comment" items="${CommentList}" varStatus="status">
		            <tr>
		                <td><a href="#" class=""><font size="5">${comment.customer.customerAccountInformation.nickname}</font></a></td>
		                <td><a href="#" class=""><font size="5">${comment.point}</font></a></td>
		                <td><a href="#" class=""><font size="5">${comment.comment}</font></a></td>
		                
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