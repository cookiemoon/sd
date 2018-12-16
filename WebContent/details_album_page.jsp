<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 15/12/2018
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DROPMUSIC : album</title>
</head>
<body>
<h1><c:out value="${session.album.title}" /></h1><br>
<p>ID: <c:out value="${session.album.id}" /></p><hr>
<c:forEach items="${session.album.music}" var="obj">
    <c:out value="${obj.title}" /><br>
    <p>Length: <c:out value="${obj.duration}" /></p>
    <p>ID: <c:out value="${obj.id}" /></p>
    <hr>
</c:forEach>
</body>
</html>
