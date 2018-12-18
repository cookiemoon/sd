<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DROPMUSIC : login</title>

</head>
<body>
<h1>Welcome to DROPMUSIC!</h1><hr>
<c:choose>
	<c:when test="${session.loggedin == true}">
		<p>Please use the logout button to log out.</p><br>
		<a href="/main_menu.jsp">Go back to main menu.</a>
	</c:when>
	<c:otherwise>
		<s:form action="login" method="post">
			<p>Username:</p>
			<s:textfield name="username" /><br>
			<p>Password:</p>
			<s:textfield name="password" /><br>
			<s:submit value="Login"/>
		</s:form>
		<a href="register.jsp" >Don't have an account? Click here to register.</a>
	</c:otherwise>
</c:choose>



</body>
</html>