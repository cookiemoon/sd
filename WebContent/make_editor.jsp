<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 14/12/2018
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>DROPMUSIC : make editor</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<h1>Grant editor privilege</h1><hr>
<c:choose>
    <c:when test="${session.editor==true}">
        <a href="/main_menu.jsp">Back</a>
        <p>Who would you like to grant editor privileges to?</p>
        <s:form action="editor" method="post">
            <p>Username:</p>
            <s:textfield name="grantee" /><br>
            <s:submit value="Make Editor"/>
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/main_menu.jsp">Go back to menu.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
