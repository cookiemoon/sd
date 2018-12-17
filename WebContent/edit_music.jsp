<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>DROPMUSIC : edit music</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<h1>Edit Music</h1><hr>
<c:choose>
    <c:when test="${session.editor == true}">
        <a href="/edit.jsp">Back</a>
        <p>To leave fields as-is, leave the text box empty.</p>
        <s:form action="edit_music" method="post">
            <p>Music ID:</p>
            <s:textfield name="musicID" /><br>
            <a href="/help.jsp">Don't know what this means? Click here.</a><br>
            <p>New music title:</p>
            <s:textfield name="title" /><br>
            <p>New duration in seconds:</p>
            <s:textfield name="duration" /><br>
            <p>New lyrics:</p>
            <s:textfield name="lyrics" /><br>
            <s:submit value="Edit music"/>
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/main_menu.jsp">Go back to menu.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
