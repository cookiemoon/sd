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
<p><b>Posting Menu</b></p>
<c:choose>
    <p>"What do you want to post?"</p>
    <c:when test="${session.loggedin == true}">
        <s:form action="post" method="post">
            <s:submit name="multiplex" value="Music" />
            <s:submit name="multiplex" value="Album" />
            <s:submit name="multiplex" value="Artist" />
        </s:form>
    </c:when>
</c:choose>

</body>
</html>