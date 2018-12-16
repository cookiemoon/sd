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
    <title>DROPMUSIC : post artist</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<h1>Post Artist</h1><hr>
<c:choose>
    <c:when test="${session.editor == true}">
        <a href="/post.jsp">Back</a>
        <s:form action="post_artist" method="post">
            <p>Artist name:</p>
            <s:textfield name="name" /><br>
            <p>Artist description:</p>
            <s:textfield name="desc" /><br>
            <p>Start of active period:</p>
            <s:textfield name="start" /><br>
            <p>End of active period:</p>
            <s:textfield name="end" /><br>
            <s:submit value="Post artist"/>
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/main_menu.jsp">Go back to menu.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
