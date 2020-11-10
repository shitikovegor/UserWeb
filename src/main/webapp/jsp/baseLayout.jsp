<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="properties.pagecontent"/>
<fmt:setLocale value="${locale}" scope="session"/>


<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${param.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="${pageContext.request.contextPath}assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}assets/css/smoothproducts.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}assets/css/untitled.css">
</head>
<body>
<header><c:import url="header.jsp"/></header>
<c:import url="content/${param.page}.jsp"/>
<c:import url="footer.jsp"/>
<script src="${pageContext.request.contextPath}assets/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}assets/js/webproject.js"></script>
<script src="${pageContext.request.contextPath}assets/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
<script src="${pageContext.request.contextPath}assets/js/smoothproducts.min.js"></script>
<script src="${pageContext.request.contextPath}assets/js/theme.js"></script>
</body>
</html>
