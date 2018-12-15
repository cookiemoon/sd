<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>DROPMUSIC : post</title>
</head>
<body>
<h1>Post</h1><hr>
<c:choose>
    <c:when test="${session.editor == true}">
        <a href="/main_menu.jsp">Back</a><br>
        <p>What do you want to post?</p>
        <s:form action="post" method="post">
            <s:submit name="multiplex" value="Music" />
            <s:submit name="multiplex" value="Album" />
            <s:submit name="multiplex" value="Artist" />
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/main_menu.jsp">Go back to menu.</a>
    </c:otherwise>
</c:choose>

</body>
</html>