<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head>
    <title>Login</title>
</head>
<body>
<h2>Application</h2>
<form name="loginForm" action="controller" method="post">
    <input type="hidden" name="command" value="login" />
    <br/>Login:<br/>
    <input type="text" name="login" value=""/>
    <br/>Password:<br/>
    <input type="password" name="password" value=""/>
    <br/>
    ${errorLoginPassMessage}
    <br/>
    ${userAddedMessage}
    <br/>
<%--    ${wrongAction}--%>
<%--    <br/>--%>
<%--    ${nullPage}--%>
<%--    <br/>--%>
    <input type="submit" value="Log in"/>
</form>

<form name="registrationForm" action="controller" method="post">
    <input type="hidden" name="command" value="open-page"/>
    <input type="hidden" name="page" value="/jsp/registration.jsp">
    <input type="submit" value="Registration"/>
</form>

</body>
</html>