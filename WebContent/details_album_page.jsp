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

<% bean.setAlbum(request.getParameter("id")); %>
<html>
<head>
    <title>DROPMUSIC : album</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<c:choose>
    <c:when test="${session.loggedin == true}">
        <h1><c:out value="${bean.getAlbumTitle()}" /></h1>
        <p>Album by <a href="/details_artist_page.jsp?id=${bean.getAlbumArtistID()}"><c:out value="${bean.getAlbumArtist()}" /></a></p>
        <p>ID: <c:out value="${bean.getAlbumID()}" /></p><hr>
        <p><b>Released </b><c:out value="${bean.getAlbumReleaseDate()}" /></p><hr>
        <p><b>Sponsored by </b><c:out value="${bean.getAlbumLabel()}" /></p><hr>
        <p><b>Genres</b><br>|
        <c:forEach items="${bean.getAlbumGenres()}" var="item">
            <c:out value="${item} | " />
        </c:forEach></p><hr>
        <p><b>Description</b><br><c:out value="${bean.getAlbumDescription()}" /></p><hr>
        <p><a href="/review.jsp?id=${bean.getAlbumID()}">Review this album</a></p><hr>
        <p><b>Music List</b></p>
        <c:set value="0" var="count" />
        <c:forEach items="${bean.getAlbumMusic()}" var="item" varStatus="status">
            <c:set value="${count+1}" var="count" />
            <c:out value="${count}. " /><a href="/details_music_page.jsp?id=${bean.getAlbumMusicID(status.index)}"><c:out value="${item}" /></a>
        </c:forEach>
        <hr>
        <p><b>Reviews</b></p>
        <p>This album has an average score of <b><c:out value="${bean.getAlbumScore()}" /></b></p><hr>
        <c:forEach items="${bean.getAlbumReview()}" var="item" varStatus="status">
            <p><b>Reviewed by </b><c:out value="${bean.getReviewer(status.index)}" /></p>
            <p><b>Score </b><c:out value="${bean.getReviewScore(status.index)}" /></p><hr>
            <c:out value="${item}" />
            <hr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/login.jsp">Please log in properly.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
