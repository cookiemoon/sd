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
    <title>DROPMUSIC : post artist</title>
</head>
<body>
<s:form action="post_artist" method="post">
    <p>Artist name:</p>
    <s:textfield name="name" /><br>
    <p>Artist description:</p>
    <s:textfield name="desc" /><br>
    <p>Start of active period:</p>
    <s:textfield name="start" /><br>
    <p>End of active period:</p>
    <s:textfield name="end" /><br>
    <s:submit value="Post artist"/>
</s:form>
</body>
</html>
