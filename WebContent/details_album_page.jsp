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
<h1><c:out value="${bean.getAlbumTitle()}" /></h1>
<p>Album by <a href="/details_artist_page.jsp?id=${bean.getAlbumArtistID()}"><c:out value="${bean.getAlbumArtist()}" /></a></p>
<p>ID: <c:out value="${bean.getAlbumID()}" /></p><hr>
<p><b>Released </b><c:out value="${bean.getAlbumReleaseDate()}" /></p>
<p><b>Sponsored by </b><c:out value="${bean.getAlbumLabel()}" /></p>
<p><b>Description</b><br><c:out value="${bean.getAlbumDescription()}" /></p><hr>
<p><a href="/review.jsp?id=${bean.getAlbumID()}">Review this album</a></p><hr>
<p><b>Music List</b></p>
<c:set value="0" var="count" />
<c:forEach items="${bean.getAlbumMusic()}" var="item" varStatus="status">
    <c:set value="${count+1}" var="count" />
    <c:out value="${count}. " /><a href="/details_music_page.jsp?id=${bean.getAlbumMusicID(status.index)}"><c:out value="${item}" /></a>
</c:forEach>
</body>
</html>
