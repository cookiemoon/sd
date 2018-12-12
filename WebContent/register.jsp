<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>DROPMUSIC : register</title>
</head>
<body>
<p><b>Welcome to DROPMUSIC!</b></p>
<s:form action="register" method="post">
    <s:text name="Username:" />
    <s:textfield name="username" /><br>
    <s:text name="Password:" />
    <s:textfield name="password" /><br>
    <s:submit value="Register"/>
</s:form>
<a href="login.jsp" >Already have an account? Click here to log in.</a>
</body>
</html>