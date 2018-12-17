<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>DROPMUSIC - Dropbox association</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<a href="/details_music_page.jsp?id=2">TEST!!!</a>
<h1><b>Dropbox Menu</b></h1>
<c:choose>
    <c:when test="${session.loggedin == true}">
        <c:choose>
            <c:when test="${session.dropbox_associated == false}">
                <h2><a href="${session.dropbox_auth_url}">Associate your account with dropbox</a></h2>
            </c:when>
            <c:otherwise>
                <h1>REST is done kind of hu3</h1>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/login.jsp">Please log in properly.</a>
    </c:otherwise>
</c:choose>

</body>
</html>