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
<html>
<head>
    <title>DROPMUSIC : remove</title>
</head>
<body>
<h1>Remove Artist</h1><hr>
<p>Are you sure you want to remove <b><c:out value="${bean.getArtistName()}" /></b>?</p>
<s:form action="remove_artist" method="post">
    <s:submit value="Delete" />
    <s:submit value="Cancel" />
</s:form>
</body>
</html>
