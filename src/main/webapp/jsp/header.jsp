<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>
<c:set var="userRole" value="${sessionScope.user == null ? 'GUEST' : sessionScope.user.roleType}"/>

<nav class="navbar navbar-light navbar-expand-lg shadow bg-white clean-navbar">
    <div class="container"><a class="navbar-brand logo" href="controller?command=home-page">HelpByCar</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse"
             id="navcol-1">
            <ul class="nav navbar-nav ml-auto">
                <li class="nav-item" role="presentation"><a class="nav-link"
                    href="controller?command=home-page"><fmt:message key="header.home"/></a></li>
                <li class="nav-item" role="presentation"><a class="nav-link"
                    href="controller?command=requests-page"><fmt:message key="header.applications"/></a></li>
                <c:if test="${userRole == 'GUEST'}">
                    <li class="nav-item" style="display-role: guest" role="presentation">
                        <a class="nav-link"
                           href="controller?command=login-page"><fmt:message key="common.enter"/></a>
                    </li>
                    <li class="nav-item" style="display-role: guest" role="presentation">
                        <a class="nav-link" href="controller?command=registration-page">
                            <fmt:message key="header.register"/></a>
                    </li>
                </c:if>
                <c:if test="${userRole != 'GUEST'}">
                    <li class="nav-item" style="display-role: driver, client, administrator" role="presentation"><a
                            class="nav-link" href="controller?command=account-page">
                        <fmt:message key="header.account"/></a>
                    </li>
                    <li class="nav-item" role="presentation">
                        <a class="nav-link" href="controller?command=logout">
                            <fmt:message key="header.logout"/></a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
