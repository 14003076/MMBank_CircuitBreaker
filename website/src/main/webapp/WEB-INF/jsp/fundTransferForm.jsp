<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>${message}</h1>
<h1>FundTransfer Form</h1>
<form action="fundTransfer" method="get">
	Enter SendersAccount Number: <input name="sendersAccountNumber"/><br/>
	Enter receiversAccount Number: <input name="receiversAccountNumber"/><br/>
	Enter Amount To Transfer : <input name="amount"/><br/>
	<input type="submit" value="fundTransfer"/>
</form>
</body>
</html>