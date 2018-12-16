<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 15/12/2018
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DROPMUSIC : details music</title>
</head>
<body>
<h1>Music Details</h1><hr>
<c:choose>
    <c:when test="${session.loggedin == true}">
        <a href="/details.jsp">Back</a>
        <s:form action="details_music" method="post">
            <p>Music ID:</p>
            <s:textfield name="musicID" /><br>
            <a href="/help.jsp">Don't know what this means? Click here.</a><br>
            <s:submit value="Look up"/>
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/login.jsp">Please log in properly.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
