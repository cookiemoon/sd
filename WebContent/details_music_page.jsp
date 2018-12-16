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
</head>
<body>
<h1><c:out value="${bean.getMusicTitle()}" /></h1>
<p>by [Artist] in [Album]</p>
<p>ID: <c:out value="${bean.getMusicID()}" /></p><hr>
<p><b>Duration</b> <c:out value="${bean.getMusicDuration()}" /> seconds</p>
<p><b>Lyrics</b><br><c:out value="${bean.getMusicLyrics()}" /></p>
</body>
</html>
