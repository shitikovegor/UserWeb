<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>
<fmt:setLocale value="${locale}" scope="session"/>
<main class="page login-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info"><fmt:message key="page.login.buttonName"/></h2>
                <p><fmt:message key="page.login.description"/></p>
            </div>
            <form name="loginForm" action="controller" method="post">
                <input type="hidden" name="command" value="login"/>
                <div class="form-group"><label for="login"><fmt:message key="common.login"/></label>
                    <input class="form-control item" type="text" name="login" id="login" value="" required></div>
                <div class="form-group"><label for="password"><fmt:message key="common.password"/></label>
                    <input class="form-control" type="password" name="password" id="password" value="" required></div>
                <button class="btn btn-primary btn-block" type="submit">
                    <fmt:message key="page.login.buttonName"/></button>

                <c:if test="${login_password_invalid}">
                    <br/>
                    <fmt:message key="error.login"/>
                </c:if>
            </form>
        </div>
    </section>
</main>