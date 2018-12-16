<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 14/12/2018
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DROPMUSIC : post music</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<h1>Post Music</h1><hr>
<c:choose>
    <c:when test="${session.editor == true}">
        <a href="/post.jsp">Back</a>
        <s:form action="post_music" method="post">
            <p>Music title:</p>
            <s:textfield name="title" /><br>
            <p>Album ID:</p>
            <s:textfield name="albumID" /><br>
            <a href="/help.jsp">Don't know what this means? Click here.</a><br>
            <p>Duration in seconds:</p>
            <s:textfield name="duration" /><br>
            <p>Lyrics:</p>
            <s:textfield name="lyrics" /><br>
            <s:submit value="Post music"/>
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/main_menu.jsp">Go back to menu.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
