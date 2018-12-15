<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 14/12/2018
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DROPMUSIC : post album</title>
</head>
<body>
<s:form action="post_album" method="post">
    <p>Album title:</p>
    <s:textfield name="title" /><br>
    <p>Artist ID:</p>
    <s:textfield name="artistID" /><br>
    <a href="/help.jsp">Don't know what this means? Click here.</a><br>
    <p>Album description:</p>
    <s:textfield name="desc" /><br>
    <p>Release date:</p>
    <s:textfield name="release_date" /><br>
    <p>Label:</p>
    <s:textfield name="label" /><br>
    <s:submit value="Post music"/>
</s:form>
</body>
</html>
