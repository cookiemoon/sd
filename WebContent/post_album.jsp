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
    <s:text name="Album title:" />
    <s:textfield name="title" /><br>
    <s:text name="Artist ID:" />
    <s:textfield name="artistID" /><br>
    <s:text name="Description:" />
    <s:textfield name="desc" /><br>
    <s:text name="Release Date [DD/MM/YYYY]:" />
    <s:textfield name="release_date" /><br>
    <s:text name="Label:" />
    <s:textfield name="label" /><br>
    <s:submit value="Post music"/>
</s:form>
</body>
</html>
