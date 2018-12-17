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

<% bean.setMusic(request.getParameter("id")); %>
<html>
<head>
    <title>DROPMUSIC : music</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<h1><c:out value="${bean.getMusicTitle()}" /></h1>
<p>Music by <a href="/details_artist_page.jsp?id=${bean.getMusicArtistID()}"><c:out value="${bean.getMusicArtist()}" /></a>
    in <a href="/details_album_page.jsp?id=${bean.getMusicAlbumID()}"><c:out value="${bean.getMusicAlbum()}" /></a></p>
<p>ID: <c:out value="${bean.getMusicID()}" /></p><hr>
<p><b>Duration</b> <c:out value="${bean.getMusicDuration()}" /> seconds</p><hr>
<p><b>Genres</b><br>|
<c:forEach items="${bean.getMusicGenres()}" var="item">
    <c:out value="${item} | " />
</c:forEach></p><hr>
<p><b>Lyrics</b><br><c:out value="${bean.getMusicLyrics()}" /></p><hr>
</body>
</html>
