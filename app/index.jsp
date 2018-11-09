<html>
<head>
<title>Welcome to Electric Cloud</title>
</head>
<body>
	<h1>Welcome to Electric Cloud</h1>
	<p>
		It is now
		<%= new java.util.Date() %></p>
	<p>
		You are coming from 
		<%= request.getRemoteAddr()  %></p>
</body>