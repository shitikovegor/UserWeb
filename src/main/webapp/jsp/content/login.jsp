<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>
<main class="page login-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info"><fmt:message key="page.login.buttonName"/></h2>
                <p><fmt:message key="page.login.description"/></p>
            </div>
            <form name="loginForm" action="controller" method="post">
                <input type="hidden" name="command" value="login"/>
                <div class="form-group"><label for="login"><fmt:message key="page.login.login"/></label>
                    <input class="form-control item" type="text" name="login" id="login" required></div>
                <div class="form-group"><label for="password"><fmt:message key="page.login.password"/></label>
                    <input class="form-control" type="password" name="password" id="password" required></div>
                <div class="form-group">
                    <div class="form-check"><input class="form-check-input" type="checkbox" id="checkbox"><label
                            class="form-check-label" for="checkbox"><fmt:message key="page.login.remember"/></label>
                    </div>
                </div>
                <button class="btn btn-primary btn-block" type="submit">
                    <fmt:message key="page.login.buttonName"/></button>
                <br/>
                ${errorLoginPassMessage}
                <br/>
                ${userAddedMessage}
                <br/>
            </form>
        </div>
    </section>
</main>
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