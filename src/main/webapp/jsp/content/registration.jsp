<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>
<main class="page registration-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info"><fmt:message key="page.registration.pageName"/> </h2>
                <p><fmt:message key="page.registration.description"/></p>
            </div>
            <form action="controller" method="post">
                <div class="form-group"><label for="login"><fmt:message key="page.registration.login"/></label><input
                        class="form-control item" type="text" id="login" pattern="^(?=.*[A-Za-z0-9]$)[a-zA-Z][a-zA-Z0-9._-]+" minlength="6" maxlength="20" name="login" title="Your login must be 6-20 characters long,  can contain latin letters, numbers or -, _, ."
                                                                               required=""></div>
                <div class="form-group"><label for="password"><fmt:message key="page.registration.password"/></label><input
                        class="form-control item" type="password" id="password" required="Your password must be 6-20 characters long, contain upper and lower letters and numbers, and can contain special characters"
                                                                                     pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d@$!%*?&]$" minlength="6" maxlength="20" name="password" title="Your password must be 8-20 characters long, contain upper and lower letters and numbers, and can contain special characters"></div>
                <div
                        class="form-group"><label for="email"><fmt:message key="page.registration.email"/></label><input
                        class="form-control item" type="email" id="email" required="" name="email"></div>
                <div class="form-group"><input type="radio" name="subject" value="individual" required="">
                    <p class="d-inline" style="margin-right: 30px;margin-top: 0px;margin-left: 5px;"><fmt:message
                            key="page.registration.individual"/></p>
                    <input type="radio" name="subject" value="organization" required="">
                    <p class="d-inline" style="margin-right: 30px;margin-top: 0px;margin-left: 5px;"><fmt:message
                            key="page.registration.organization"/></p>
                </div>
                <div class="form-group"><label for="name"><fmt:message key="page.registration.name"/></label><input
                        class="form-control item" type="text" id="name" required="" name="name"></div>
                <div class="form-group"><label for="surname"><fmt:message key="page.registration.surname"/></label><input
                        class="form-control item" type="text" id="surname" name="surname"></div>
                <div class="form-group"><label for="phone"><fmt:message key="page.registration.phone"/></label><input
                        class="form-control item" type="tel" id="phone" required="" name="phone"></div>
                <div class="form-group text-left"><input type="radio" name="role" value="driver" required="">
                    <p class="d-inline" style="margin-right: 30px;margin-top: 0px;margin-left: 5px;"><fmt:message
                            key="page.registration.driver"/></p><input type="radio" name="role" value="client" required="">
                    <p class="d-inline" style="margin-right: 30px;margin-top: 0px;margin-left: 5px;"><fmt:message
                            key="page.registration.client"/></p>
                </div><button class="btn btn-primary btn-block" type="submit"><fmt:message
                    key="page.registration.signup"/></button></form>
        </div>
    </section>
</main>


<%--<html>--%>
<%--<meta charset="UTF-8">--%>
<%--<head>--%>
<%--    <title>Login</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h2>Application</h2>--%>
<%--<FORM name="registrationForm" action="controller" method="POST">--%>
<%--    <h4>Registration</h4>--%>
<%--    <input type="hidden" name="command" value="registration" />--%>
<%--    <br/>Enter login (Latin characters, digits or -, _, .):<br/>--%>
<%--    <input type="text" name="login" value=""/>--%>
<%--    <br/>Enter password (Must contain 1 or more upper and lower character, digit):<br/>--%>
<%--    <input type="password" name="password" value=""/>--%>
<%--    <br/>--%>
<%--    ${errorLoginPassMessage}--%>
<%--    <br/>--%>
<%--&lt;%&ndash;    ${wrongAction}&ndash;%&gt;--%>
<%--&lt;%&ndash;    <br/>&ndash;%&gt;--%>
<%--&lt;%&ndash;    ${nullPage}&ndash;%&gt;--%>
<%--&lt;%&ndash;    <br/>&ndash;%&gt;--%>
<%--    <input type="submit" value="Registration"/>--%>
<%--</FORM>--%>
<%--<input type=button value="Back" onCLick="history.back()">--%>
<%--</body>--%>
<%--</html>--%>