<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 16/12/2018
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="bean" class="client.model.Bean" scope="session" />

<html>
<head>
    <title>DROPMUSIC : search music</title>
</head>
<body>
<h1>Search Music</h1><hr>
<c:set value="0" var="count" />
<c:forEach items="${bean.getSearchResultNames()}" var="item" varStatus="status">
    <c:set value="${count+1}" var="count" />
    <c:out value="${count}. " /><a href="/details_music_page.jsp?id=${bean.getSearchResultID(status.index)}"><c:out value="${item}" /></a>
    <p>by <c:out value="${bean.getResultArtist(status.index)}" /> in <c:out value="${bean.getMusicResultAlbum(status.index)}" /></p><br>
</c:forEach>
</body>
</html>
