<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 14/12/2018
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DROPMUSIC : make editor</title>
</head>
<body>
<p>Who would you like to grant editor privileges to?</p>
<s:form action="make_editor" method="post">
    <s:text name="Username:" />
    <s:textfield name="grantee" /><br>
    <s:submit value="Make this person an editor"/>
</s:form>
</body>
</html>
