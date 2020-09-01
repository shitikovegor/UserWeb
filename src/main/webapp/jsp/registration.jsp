<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head>
    <title>Login</title>
</head>
<body>
<h2>Application</h2>
<FORM name="registrationForm" action="controller" method="POST">
    <h4>Registration</h4>
    <input type="hidden" name="command" value="registration" />
    <br/>Enter login (Latin characters, digits or -, _, .):<br/>
    <input type="text" name="login" value=""/>
    <br/>Enter password (Must contain 1 or more upper and lower character, digit):<br/>
    <input type="password" name="password" value=""/>
    <br/>
    ${errorLoginPassMessage}
    <br/>
<%--    ${wrongAction}--%>
<%--    <br/>--%>
<%--    ${nullPage}--%>
<%--    <br/>--%>
    <input type="submit" value="Registration"/>
</FORM>
<input type=button value="Back" onCLick="history.back()">
</body>
</html>