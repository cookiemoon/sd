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
</head>
<body>
<h1><c:out value="${bean.getArtistName()}" /></h1><br>
<p>ID: <c:out value="${bean.getArtistID()}" /></p><hr>
<p><b>Description</b><br><c:out value="${bean.getArtistDescription()}" /></p>
</body>
</html>
