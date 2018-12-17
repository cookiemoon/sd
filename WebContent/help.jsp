<%--
  Created by IntelliJ IDEA.
  User: anaquelhas
  Date: 15/12/2018
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DROPMUSIC : help</title>
    <script type="application/javascript" src="js/notifications.js"></script>
    <script>var username = ('${session.username}')</script>
</head>
<body>
<h1>DROPMUSIC Frequently Asked Questions</h1>
<hr>
<p><b>The posting and editing pages are asking me for an ID, but I don't know what to enter!</b><br>
<br>Within DROPMUSIC's database, albums, artists, and music are identified with an internal ID. <br>
    We need this information to know which album you're adding a song to, or what artist you're looking to edit!<br>
    If you don't know where to find this information, first use the <b>search</b> feature to look up your desired object,<br>
    and then look beneath the title, where it says - [ID : x]. This 'x' number is what you'll want to enter!</p>

<a href="/main_menu.jsp">Back</a>
</body>
</html>
