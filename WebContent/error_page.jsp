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
</head>
<body>
<p>An error has occurred.<br> ${session.error}</p>
<s:form action="error" method="post">
        <s:submit value="Back" name="back" />
    </s:form>
</body>
</html>
