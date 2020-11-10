<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>

<main class="page landing-page">
    <section class="clean-block clean-hero" style="min-height: 330px;
    background-image: url('${pageContext.request.contextPath}assets/img/title_image.png');
            color:rgba(9, 162, 255, 0.85);background-position: center;">
        <img src="">
        <div class="text">
    <img class="navbar-brand logo" src="${pageContext.request.contextPath}assets/img/logoWB.png"/>
            <p><fmt:message key="page.home.titleText"/><br></p>
        </div>
    </section>
    <section class="clean-block clean-info dark" style="padding-bottom: 65px;">
        <div class="container">
            <div class="block-heading" style="padding-top: 80px;">
                <h2 class="text-info"><fmt:message key="page.home.lastApps"/></h2>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <c:forEach var="entry" items="${applications}">
                <c:set var="application" value="${entry.key}"/>
                <c:set var="status" value="${entry.value}"/>
                <div class="col-lg-4" style="max-width: 33.3333%;">
                    <div class="card clean-testimonial-item border-0 rounded-0">
                        <div class="card-body">
                            <h3 class="d-inline-block">${application.title}</h3>
                            <p class="card-text" style="margin-bottom: 5px;"><strong><fmt:message key="application.type"/>:<br></strong>
                                <fmt:message key="application.${application.applicationType.name}"/></p>
                            <p class="card-text" style="margin-bottom: 5px;"><strong><fmt:message
                                    key="page.applications.departureDate"/>:<br></strong>
                                <ctg:format-date date="${application.getAddressTimeData().getDepartureDate()}"/></p>
                            <p class="card-text"><strong><fmt:message key="application.city"/>:<br></strong>
                                    ${application.getAddressTimeData().getDepartureAddress().getCity()}</p>
                            <form id="application${application.applicationId}" action="controller"
                                  method="post">
                                <input type="hidden" name="application_id" value="${application.applicationId}">
                                <input type="hidden" name="current_page" value="${sessionScope.current_page}">
                                <input type="hidden" name="status" value="${status}">
                                <input type="hidden" name="command" value="application-page">
                                <a href="javascript:document.getElementById('application${application.applicationId}').submit()">
                                    <fmt:message key="page.home.showDetails"/></a>
                            </form>
                        </div>
                    </div>
                </div>
                </c:forEach>
            </div>
        </div>
    </section>
    <section class="clean-block features">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info"><fmt:message key="page.home.features"/></h2>
            </div>
            <div class="row justify-content-center">
                <div class="col-md-5 feature-box">
                    <i class="fas fa-hands-helping icon"></i>
                    <h4><fmt:message key="page.home.target"/></h4>
                    <p><fmt:message key="page.home.targetText"/></p>
                </div>
                <div class="col-md-5 feature-box"><i class="fas fa-car icon"></i>
                    <h4><fmt:message key="page.home.appType"/></h4>
                    <p><fmt:message key="page.home.appTypeText"/></p>
                </div>
                <div class="col-md-5 feature-box"><i class="fas fa-user-shield icon"></i>
                    <h4><fmt:message key="page.home.confidentiality"/></h4>
                    <p><fmt:message key="page.home.confText"/></p>
                </div>
                <div class="col-md-5 feature-box"><i class="fas fa-search icon"></i>
                    <h4><fmt:message key="page.home.searchHelp"/></h4>
                    <p><fmt:message key="page.home.searchHelpText"/></p>
                </div>
            </div>
        </div>
    </section>
</main>