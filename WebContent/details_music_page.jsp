<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 15/12/2018
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="bean" class="client.model.Bean" scope="session" />
<html>
<head>
    <title>DROPMUSIC : music</title>
</head>
<body>
<c:out value="${bean.getMusicTitle()}" /><br>
<p>ID: </p><c:out value="${bean.getMusicID()}" /><hr>

</body>
</html>
