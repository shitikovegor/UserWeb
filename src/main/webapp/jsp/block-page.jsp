<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="properties.pagecontent"/>
<fmt:setLocale value="${language}" scope="session"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="page.blockPage.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="${pageContext.request.contextPath}assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}assets/css/smoothproducts.css">
</head>
<body>
<header><c:import url="header.jsp"/></header>
<main class="page contact-us-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading" style="padding-top: 150px;">
                <h1 class="text-info" style="font-size: 90px;margin-bottom: 10px;"><fmt:message key="page.blockPage.title"/></h1>
                <h2 class="text-info"><fmt:message key="page.blockPage.message"/></h2>
                <a href="controller?command=home-page" style="font-size: 20px;padding-top: 0px;margin-top: 0px;">
                    <fmt:message key="common.backHome"/>
                </a>
            </div>
        </div>
    </section>
</main>
<c:import url="footer.jsp"/>
<script src="${pageContext.request.contextPath}assets/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}assets/js/webproject.js"></script>
<script src="${pageContext.request.contextPath}assets/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
<script src="${pageContext.request.contextPath}assets/js/smoothproducts.min.js"></script>
<script src="${pageContext.request.contextPath}assets/js/theme.js"></script>
</body>
</html>