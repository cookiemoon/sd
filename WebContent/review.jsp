<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 16/12/2018
  Time: 18:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="bean" class="client.model.Bean" scope="session" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<% bean.setAlbum(request.getParameter("id")); %>
<html>
<head>
    <title>DROPMUSIC : review</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<h1>Review Album</h1><hr>
<c:choose>
    <c:when test="${session.loggedin == true}">
        <p>Reviewing <b><c:out value="${bean.getAlbumTitle()}"/></b>.</p>
        <s:form action="review" method="post">
            <p>Score (0-10): <s:textfield name="score" /></p>
            <p>Review: <s:textfield name="review" /></p>
            <s:submit value="Post review"/>
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/login.jsp">Please log in properly.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
