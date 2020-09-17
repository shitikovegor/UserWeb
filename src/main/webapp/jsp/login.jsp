<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Login - Brand</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="${pageContext.request.contextPath}assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}assets/css/smoothproducts.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar">
    <div class="container"><a class="navbar-brand logo" href="#">Brand</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse"
             id="navcol-1">
            <ul class="nav navbar-nav ml-auto">
                <li class="nav-item" role="presentation"><a class="nav-link" href="index.html">Home</a></li>
                <li class="nav-item" role="presentation"><a class="nav-link" href="service-page.html">Services</a></li>
                <li class="nav-item" role="presentation"><a class="nav-link" href="about-us.html">About Us</a></li>
                <li class="nav-item" role="presentation"><a class="nav-link" href="contact-us.html">Contact Us</a></li>
                <li class="nav-item" role="presentation"><a class="nav-link active" href="login.html">Login</a></li>
                <li class="nav-item" role="presentation">
                    <a class="nav-link" href="controller?command=registration-page">Register</a></li>
<%--                <form name="registrationForm" action="controller" method="post">&ndash;%&gt;--%>
                    <%--    <input type="hidden" name="command" value="open-page"/>--%>
                    <%--    <input type="hidden" name="page" value="/jsp/registration.jsp">--%>
                    <%--    <input type="submit" value="Registration"/>--%>
            </ul>
        </div>
    </div>
</nav>
<main class="page login-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info">Log In</h2>
                <p>Please enter login and password.</p>
            </div>
            <form name="loginForm" action="controller" method="post">
                <input type="hidden" name="command" value="login" />
                <div class="form-group"><label for="login">Login</label>
                    <input class="form-control item" type="text" name="login" id="login" required></div>
                <div class="form-group"><label for="password">Password</label>
                    <input class="form-control" type="password" name="password" id="password" required></div>
                <div class="form-group">
                    <div class="form-check"><input class="form-check-input" type="checkbox" id="checkbox"><label class="form-check-label" for="checkbox">Remember me</label></div>
                </div><button class="btn btn-primary btn-block" type="submit">Log In</button>
                <br/>
                ${errorLoginPassMessage}
                <br/>
                ${userAddedMessage}
                <br/>
            </form>
        </div>
    </section>
</main>
<footer class="page-footer dark">
    <div class="footer-copyright">
        <p>© 2020 Copyright Text</p>
    </div>
</footer>
<script src="${pageContext.request.contextPath}assets/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}assets/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
<script src="${pageContext.request.contextPath}assets/js/smoothproducts.min.js"></script>
<script src="${pageContext.request.contextPath}assets/js/theme.js"></script>
</body>

</html>
<%--${pageContext.request.contextPath}--%>

<%--<html>--%>
<%--<meta charset="UTF-8">--%>
<%--<head>--%>
<%--    <title>Login</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h2>Application</h2>--%>
<%--<form name="loginForm" action="controller" method="post">--%>
<%--    <input type="hidden" name="command" value="login" />--%>
<%--    <br/>Login:<br/>--%>
<%--    <input type="text" name="login" value=""/>--%>
<%--    <br/>Password:<br/>--%>
<%--    <input type="password" name="password" value=""/>--%>
<%--    <br/>--%>
<%--    ${errorLoginPassMessage}--%>
<%--    <br/>--%>
<%--    ${userAddedMessage}--%>
<%--    <br/>--%>
<%--&lt;%&ndash;    ${wrongAction}&ndash;%&gt;--%>
<%--&lt;%&ndash;    <br/>&ndash;%&gt;--%>
<%--&lt;%&ndash;    ${nullPage}&ndash;%&gt;--%>
<%--&lt;%&ndash;    <br/>&ndash;%&gt;--%>
<%--    <input type="submit" value="Log in"/>--%>
<%--</form>--%>

<%--<form name="registrationForm" action="controller" method="post">--%>
<%--    <input type="hidden" name="command" value="open-page"/>--%>
<%--    <input type="hidden" name="page" value="/jsp/registration.jsp">--%>
<%--    <input type="submit" value="Registration"/>--%>
<%--</form>--%>

<%--</body>--%>
<%--</html>--%>