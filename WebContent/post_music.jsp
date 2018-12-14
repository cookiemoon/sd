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
    <title>DROPMUSIC : post music</title>
</head>
<body>
<s:form action="post_music" method="post">
    <s:text name="Music title:" />
    <s:textfield name="title" /><br>
    <s:text name="Album ID:" />
    <s:textfield name="albumID" /><br>
    <s:submit value="Search for album"/>
    <s:text name="Duration in seconds:" />
    <s:textfield name="duration" /><br>
    <s:text name="Lyrics:" />
    <s:textfield name="lyrics" /><br>
    <s:submit value="Post music"/>
</s:form>
</body>
</html>
