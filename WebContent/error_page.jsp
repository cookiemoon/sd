<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 14/12/2018
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DROPMUSIC : error</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<c:choose>
    <c:when test="${session.loggedin == true}">
        <p>This action could not be processed. <br> ${session.error}</p>
        <s:form action="error" method="post">
            <s:submit value="Back" name="back" />
        </s:form>
    </c:when>
    <c:otherwise>
        <p><b>You don't belong here.</b></p><br>
        <a href="/login.jsp">Please log in properly.</a>
    </c:otherwise>
</c:choose>
</body>
</html>
