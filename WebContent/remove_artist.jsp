<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 16/12/2018
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="bean" class="client.model.Bean" scope="session" />

<% bean.setArtist(request.getParameter("id")); %>
<html>
<head>
    <title>DROPMUSIC : remove</title>
</head>
<body>
<h1>Remove Artist</h1><hr>
<c:choose>
    <c:when test="${session.editor==true}">
        <p>Are you sure you want to remove <b><c:out value="${bean.getArtistName()}" /></b>? <br>
            (This can only be done for artists without any albums or music associated.)</p>
        <s:form action="remove_artist" method="post">
            <s:submit name="choice" value="Confirm" />
            <s:submit name="choice" value="Cancel" />
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/main_menu.jsp">Go back to menu.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
