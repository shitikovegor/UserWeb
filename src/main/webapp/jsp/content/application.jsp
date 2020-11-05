<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>

<main class="page login-page">
    <section class="clean-block  dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info"><fmt:message key="page.application.title"/></h2>
            </div>
            <div class="shadow" style="max-width: 700px;margin-right: auto;margin-left: auto;">
                <div class="card" style="margin-top: 5px;margin-bottom: 10px;">
                    <div class="card-header bg-light">
                        <div class="row" style="border: none;margin-bottom: 5px;">
                            <div class="col">
                                <h2 class="d-inline">${title}</h2>
                            </div>
                            <div class="col" style="max-width: 300px;">
                                <div class="form-group">
                                    <h4 class="text-info float-right mb-2" id="status">
                                        <fmt:message key="order.status.${status.name}"/></h4>
                                    <h4 class="text-info float-right mb-2" for="status"><fmt:message key="application.status"/>:&nbsp;</h4>
                                </div>
                            </div>
                        </div>
                        <p style="margin-bottom: 0px;"><fmt:message key="application.date"/>:
                            <ctg:format-date date="${date}"/><br></p>
                    </div>
                    <div class="card-body">
                        <p class="card-text" style="margin-bottom: 5px;"><strong class="d-inline-block">
                            <fmt:message key="application.type"/>: <br></strong>
                            <fmt:message key="application.${application_type.name}"/></p>
                        <c:choose>
                            <c:when test="${application_type == 'CARGO'}">
                                <div class="app-data">
                                    <p style="margin-bottom: 5px;padding-left: 20px;"><strong class="d-inline-block">
                                        <fmt:message key="application.cargoWeight"/>: <br></strong>&nbsp;
                                            ${cargo_weight}</p>
                                    <p style="margin-bottom: 5px;padding-left: 20px;"><strong class="d-inline-block">
                                        <fmt:message key="application.cargoVolume"/>: <br></strong> ${cargo_volume}</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="app-data">
                                    <p style="margin-bottom: 5px;padding-left: 20px;"><strong class="d-inline-block">
                                        <fmt:message key="application.passengersNumber"/>: <br></strong>
                                            ${passengers_number}</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <hr style="margin-top: 10px;margin-bottom: 10px;">
                        <div class="app-data">
                            <p style="margin-bottom: 5px;"><strong class="d-inline-block"><fmt:message key="application.departureData"/>:<br></strong></p>
                            <p style="margin-bottom: 5px;padding-left: 20px;"><strong class="d-inline-block">
                                <fmt:message key="application.date"/>: <br></strong>
                                <ctg:format-date date="${departure_date}"/></p>
                            <p style="margin-bottom: 5px;padding-left: 20px;"><strong
                                    class="d-inline-block"><fmt:message key="application.address"/>: <br></strong>
                                <fmt:message key="application.city"/> - ${departure_city}, ${departure_address}</p>
                        </div>
                        <hr style="margin-top: 10px;margin-bottom: 10px;">
                        <div class="app-data">
                            <p style="margin-bottom: 5px;"><strong class="d-inline-block"><fmt:message key="application.arrivalData"/>:<br></strong></p>
                            <p style="margin-bottom: 5px;padding-left: 20px;"><strong class="d-inline-block">
                                <fmt:message key="application.date"/>: <br></strong>
                                <ctg:format-date date="${arrival_date}"/></p>
                            <p style="margin-bottom: 5px;padding-left: 20px;"><strong
                                    class="d-inline-block"><fmt:message key="application.address"/>: <br></strong>
                                <fmt:message key="application.city"/> - ${arrival_city}, ${arrival_address}</p>
                        </div>
                        <hr style="margin-top: 10px;margin-bottom: 10px;">
                        <p class="card-text app-data" style="margin-bottom: 5px;"><strong class="d-inline-block">
                            <fmt:message key="application.description"/>: <br></strong>
                            ${description}</p>
                        <div style="margin-top: 16px;">

                            <form class="form-group d-inline-block" action="controller" method="post">
                                <input type="hidden" name="command" value="back">
                                <input type="hidden" name="previous_page" value="${previous_page}">
                                <button class="btn btn-primary btn-sm" type="submit" style="margin-right: 15px;width: 130px;">
                                    <fmt:message key="page.application.back"/>
                                </button>
                            </form>
                            <c:if test="${status == 'ACTIVE' && sessionScope.role == 'DRIVER'}">
                            <form class="form-group d-inline-block float-right" action="controller" method="post">
                                <input type="hidden" name="command" value="offer-help">
                                <input type="hidden" name="application_id" value="${application_id}">
                                <button class="btn btn-primary btn-sm" type="submit" style="width: 180px;">
                                    <fmt:message key="page.applications.offerHelp"/>
                                </button>
                            </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>