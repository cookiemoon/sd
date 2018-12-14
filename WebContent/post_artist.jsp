<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 14/12/2018
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DROPMUSIC : post artist</title>
</head>
<body>
<s:form action="post_artist" method="post">
    <s:text name="Artist name:" />
    <s:textfield name="name" /><br>
    <s:text name="Description:" />
    <s:textfield name="desc" /><br>
    <s:text name="Period start [DD/MM/YYYY]:" />
    <s:textfield name="start" /><br>
    <s:text name="Period end [DD/MM/YYYY]:" />
    <s:textfield name="end" /><br>
    <s:submit value="Post artist"/>
</s:form>
</body>
</html>
