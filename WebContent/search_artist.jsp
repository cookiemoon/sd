<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 16/12/2018
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="bean" class="client.model.Bean" scope="session" />
<html>
<head>
    <title>DROPMUSIC : search artist</title>
</head>
<body>
<h1>Search Artist</h1><hr>
<c:choose>
    <c:when test="${session.loggedin == true}">
        <c:set value="0" var="count" />
        <c:forEach items="${bean.getSearchResultNames()}" var="item" varStatus="status">
            <c:set value="${count+1}" var="count" />
            <c:out value="${count}. " /><a href="/details_artist_page.jsp?id=${bean.getSearchResultID(status.index)}"><c:out value="${item}" /></a><br>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/login.jsp">Please log in properly.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
