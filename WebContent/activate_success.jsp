<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
<meta charset="UTF-8">
<title>激活成功</title>
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
			数据库课程设计<br />激活成功页面
		</p>
		</div>
		<hr/>
	</div>
	
	<div class="am-g">
		<div class="am-u-lg-8 am-u-md-10 am-u-sm-centered">
			<h1 align="center">激活成功</h1>
			
			<p>您已经激活您的账号，现在可以使用我们为您提供的外卖平台了<p>
		
			
			<input id="btnClose" type="button" value="关闭本页" class="am-btn am-btn-primary am-btn-block" onClick="custom_close()" />

			<hr>
			<p>2018 DB design</p>
		</div>
	</div>
	
</body>
<script type="text/javascript">
	//这个脚本是 ie6和ie7 通用的脚本
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
</html>

