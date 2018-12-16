<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 15/12/2018
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DROPMUSIC : edit</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<h1>Edit</h1><hr>
<c:choose>
    <c:when test="${session.editor == true}">
        <a href="/main_menu.jsp">Back</a><br>
        <p>What do you want to edit?</p>
        <s:form action="edit" method="post">
            <s:submit name="multiplex" value="Music" />
            <s:submit name="multiplex" value="Album" />
            <s:submit name="multiplex" value="Artist" />
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/main_menu.jsp">Go back to menu.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
