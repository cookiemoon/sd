<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>DROPMUSIC : main menu</title>
	<script type="application/javascript" src="js/notifications.js"></script>
	<script>var username = ('${session.username}')</script>
</head>
<body>
<p><b>Main Menu</b></p>
	<c:choose>
		<c:when test="${session.loggedin == true}">
			<p>Welcome to dropmusic, ${session.username}. Please choose an action.</p>
			<s:form action="main_menu" method="post">
				<c:choose>
					<c:when test="${session.editor == true}">
						<s:submit name="multiplex" value="Post" /><br>
						<s:submit name="multiplex" value="Edit" /><br>
						<s:submit name="multiplex" value="Grant Editor Privilege" /><br>
					</c:when>
				</c:choose>
				<s:submit name="multiplex" value="Search" /><br>
				<s:submit name="multiplex" value="Dropbox" /><br>
				<s:submit name="multiplex" value="Logout" />
			</s:form>
		</c:when>
		<c:otherwise>
			<p><b>You don't belong here.</b></p><br>
			<a href="/login.jsp">Please log in properly.</a>
		</c:otherwise>
	</c:choose>

</body>
</html>