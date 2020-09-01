<%@ page language="java" import="javax.servlet.jsp.PageContext" isErrorPage="true"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exception</title>
</head>
<body>
<h2>Exception occurred while processing the request</h2>
<p>Type: ${pageContext.exception}</p>
<p>Message: ${pageContext.exception.message}</p>
</body>
</html>