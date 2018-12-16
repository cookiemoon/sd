<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 15/12/2018
  Time: 21:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DROPMUSIC : details</title>
</head>
<body>
<h1>Details</h1><hr>
<c:choose>
    <c:when test="${session.loggedin == true}">
        <a href="/main_menu.jsp">Back</a><br>
        <p>What would you like to see?</p>
        <s:form action="details" method="post">
            <s:submit name="multiplex" value="Music" />
            <s:submit name="multiplex" value="Album" />
            <s:submit name="multiplex" value="Artist" />
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/login.jsp">Please log in properly.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
