<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hey!</title>
</head>
<body>

	<c:choose>
		<c:when test="${session.loggedin == true}">
			<s:form action="main_menu" method="post">
				<s:submit name="multiplex" value="Post" />
				<s:submit name="multiplex" value="Edit" />
				<s:submit name="multiplex" value="Details" />
				<s:submit name="multiplex" value="Search" />
				<s:submit name="multiplex" value="Grant Editor Privilege" />
				<s:submit name="multiplex" value="Dropbox" />
				<s:submit name="multiplex" value="Logout" />
			</s:form>
		</c:when>
	</c:choose>

</body>
</html>