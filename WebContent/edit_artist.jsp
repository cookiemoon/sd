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
    <title>DROPMUSIC : edit artist</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<h1>Edit Artist</h1><hr>
<c:choose>
    <c:when test="${session.editor == true}">
        <a href="/edit.jsp">Back</a>
        <p>To leave fields as-is, leave the text box empty.</p>
        <s:form action="edit_artist" method="post">
            <p>Artist ID:</p>
            <s:textfield name="artistID" /><br>
            <a href="/help.jsp">Don't know what this means? Click here.</a><br>
            <p>New artist name:</p>
            <s:textfield name="name" /><br>
            <p>New artist description:</p>
            <s:textfield name="desc" /><br>
            <s:submit value="Edit artist"/>
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/main_menu.jsp">Go back to menu.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
