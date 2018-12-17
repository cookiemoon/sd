<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 15/12/2018
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DROPMUSIC : edit album</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<h1>Edit Album</h1><hr>
<c:choose>
    <c:when test="${session.editor == true}">
        <a href="/edit.jsp">Back</a>
        <p>To leave fields as-is, leave the text box empty.</p>
        <s:form action="edit_album" method="post">
            <p>Album ID:</p>
            <s:textfield name="albumID" /><br>
            <a href="/help.jsp">Don't know what this means? Click here.</a><br>
            <p>New album title:</p>
            <s:textfield name="title" /><br>
            <p>New album description:</p>
            <s:textfield name="desc" /><br>
            <p>New release date [DD/MM/YYYY]:</p>
            <s:textfield name="release_date" /><br>
            <p>New label:</p>
            <s:textfield name="label" /><br>
            <s:submit value="Edit album"/>
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/main_menu.jsp">Go back to menu.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
