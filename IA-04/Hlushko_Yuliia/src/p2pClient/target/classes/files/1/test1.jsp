<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<center>
<h3>Page has been visited <%= request.getParameter("numberOfVisits")%> times</h3><br>
<br>
<%= "Name : "%>
<%= request.getParameter("name")%>
<br>
<br>
<% for (int i=0;i<5;i++)%>
<%= i%>
<br>
<br>
<%= "Message : "%>
<%= request.getParameter("message")%>
</center>
</body>
</html>