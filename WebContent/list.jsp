<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<h1>List page</h1><br>

Welcome <shiro:principal></shiro:principal>

<!-- 对应applicationContext.xml中的shiro/logout配置
	清除缓存
 -->
 <br><br><br>
<a href="shiro/logout">Logout</a>
<br><br><br>
<!-- 管理员 -->
<shiro:hasRole name="admin"> 
<a href="admin.jsp">admin</a>
<br><br><br>
</shiro:hasRole>
<shiro:hasRole name="user">
<a href="user.jsp">user</a>
</shiro:hasRole>
<br><br><br>
<a href="shiro/testShiroAnnotation">testShiroAnnotation</a>

</body>
</html>