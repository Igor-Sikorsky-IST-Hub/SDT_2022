<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
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

<center>
<div>
<h3>Fill in the forms</h3>
<form method="POST" >
<label for="name">Name</label>
<input type="text" name="name" id="name" placeholder="Write the name" >
<br>
<label for="message">Message</label>
<textarea name="message"  id="message"  placeholder="Write the message"></textarea>
<br>
<input type="submit"  value="Send" >
</form>
</div>
</center>
</body>
</html>