<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 15/12/2018
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="bean" class="client.model.Bean" scope="session" />

<% bean.setArtist(request.getParameter("id")); %>

<html>
<head>
    <title>DROPMUSIC : artist</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<c:choose>
    <c:when test="${session.loggedin == true}">
        <h1><c:out value="${bean.getArtistName()}" /></h1>
        <p>ID: <c:out value="${bean.getArtistID()}" /></p><hr>
        <p><b>Description</b><br><c:out value="${bean.getArtistDescription()}" /></p><hr>
        <c:choose>
            <c:when test="${session.editor==true}">
                <a href="/remove_artist.jsp?id=${bean.getArtistID()}">Remove this artist</a><hr>
            </c:when>
        </c:choose>
        <p><b>Album List</b></p>
        <c:set value="0" var="count" />
        <c:forEach items="${bean.getArtistAlbum()}" var="item" varStatus="status">
            <c:set value="${count+1}" var="count" />
            <c:out value="${count}. " /><a href="/details_album_page.jsp?id=${bean.getArtistAlbumID(status.index)}"><c:out value="${item}" /></a>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/login.jsp">Please log in properly.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
