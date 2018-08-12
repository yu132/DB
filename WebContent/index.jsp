<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title>Add a account</title>
</head>
<body>
   <h1>Add a account</h1>
   <form action="${pageContext.request.contextPath }/add.action">
      <label for="name">add</label><br/>
      <input type="submit" value="add your account"/>
   </form>
</body>
</html>