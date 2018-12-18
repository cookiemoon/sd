<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 16/12/2018
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DROPMUSIC : search</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<h1>Search</h1><hr>
<c:choose>
    <c:when test="${session.loggedin == true}">
        <s:form action="search_music" method="post">
            <p><b>Search music</b></p>
            <p>Search by... <select name="type_music">
                <option value="title">Title</option>
                <option value="album">Album</option>
                <option value="artist">Artist</option>
                <option value="genre">Genre</option>
            </select></p>
            <p><s:textfield name="term" /></p>
            <s:submit value="Search Music" />
        </s:form><br><hr><br>

        <s:form action="search_album" method="post">
            <p><b>Search album</b></p>
            <p>Search by... <select name="type_album">
                <option value="title">Title</option>
                <option value="artist">Artist</option>
                <option value="genre">Genre</option>
            </select></p>
            <p><s:textfield name="term" /></p>
            <s:submit value="Search Album" />
        </s:form><br><hr><br>

        <s:form action="search_artist" method="post">
            <p><b>Search artist</b></p>
            <p>Search by... <select name="type_artist">
                <option value="name">Name</option>
                <option value="album">Album</option>
                <option value="music">Music</option>
            </select></p>
            <p><s:textfield name="term" /></p>
            <s:submit value="Search Artist" />
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/login.jsp">Please log in properly.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
