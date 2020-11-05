<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>

<main class="page registration-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <c:choose>
                    <c:when test="${edit_application}">
                        <h2 class="text-info"><fmt:message key="page.addApplication.titleEdit"/></h2>
                    </c:when>
                    <c:otherwise>
                        <h2 class="text-info"><fmt:message key="page.addApplication.titleAdd"/></h2>
                    </c:otherwise>
                </c:choose>
                <p><fmt:message key="page.addApplication.text"/></p>
            </div>
            <form action="controller" method="post">
                <c:choose>
                    <c:when test="${edit_application}">
                        <input type="hidden" name="command" value="edit-application"/>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="command" value="add-application"/>
                    </c:otherwise>
                </c:choose>
                <div class="form-group">
                    <label for="title"><fmt:message key="application.appTitle"/></label>
                    <input class="form-control item" type="text" id="title"
                           pattern="[\p{L}0-9\s-,._!?%#&*+]{1,100}" minlength="1" maxlength="100"
                           name="title" value="${title}"
                           title="<fmt:message key="required.text"/>"
                           required="<fmt:message key="required.text"/>">
                    <c:if test="${title_invalid}">
                        <sup class="text-danger">
                            <fmt:message key="error.title"/>
                        </sup>
                    </c:if>
                </div>
                <c:choose>
                    <c:when test="${application_type == 'cargo'}">
                        <c:set var="cargoSelect" value="true"/>
                    </c:when>
                    <c:when test="${application_type == 'passenger'}">
                        <c:set var="passengerSelect" value="true"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="invalidSelect" value="true"/>
                    </c:otherwise>
                </c:choose>
                <div class="form-group">
                    <label><fmt:message key="application.applicationType"/></label>
                    <div>
                        <input type="radio" id="cargo" name="application_type" value="cargo" required="" onclick="toggleSet()"
                               ${cargoSelect ? 'checked' : ''}>
                        <p class="d-inline" style="margin-right: 30px;margin-top: 0px;margin-left: 5px;"><fmt:message
                                key="application.cargo"/></p>
                        <input type="radio" id="passenger" name="application_type" value="passenger" required="" onclick="toggleSet()"
                               ${passengerSelect ? 'checked' : ''}>
                        <p class="d-inline" style="margin-right: 30px;margin-top: 0px;margin-left: 5px;"><fmt:message
                                key="application.passenger"/></p>
                    </div>
                </div>
                <fieldset id="cargo_set" class="item" style="display: none;">
                    <div class="form-group">
                        <label for="cargo_weight"><fmt:message key="application.cargoWeight"/></label>
                        <input class="form-control item" type="text" id="cargo_weight"
                               pattern="\d+\.*\d*" name="cargo_weight" value="${cargo_weight}" placeholder="0">
                    </div>
                    <c:if test="${cargo_weight_invalid}">
                        <sup class="text-danger">
                            <fmt:message key="error.dataInvalid"/>
                        </sup>
                    </c:if>
                    <div class="form-group">
                        <label for="cargo_volume"><fmt:message key="application.cargoVolume"/><br></label>
                        <input class="form-control item" type="text" id="cargo_volume"
                               name="cargo_volume" value="${cargo_volume}" pattern="\d+\.*\d*" placeholder="0"
                               inputmode="numeric">
                    </div>
                    <c:if test="${cargo_volume_invalid}">
                        <sup class="text-danger">
                            <fmt:message key="error.dataInvalid"/>
                        </sup>
                    </c:if>
                </fieldset>
                <fieldset id="passenger_set" class="application_type_item" style="display: none;">
                    <div class="form-group">
                        <label for="passengers_number"><fmt:message key="application.passengersNumber"/></label>
                        <input class="form-control item" type="number" id="passengers_number"
                               name="passengers_number" value="${passengers_number}" pattern="\d+" placeholder="0">
                        <c:if test="${passengers_number_invalid}">
                            <sup class="text-danger">
                                <fmt:message key="error.dataInvalid"/>
                            </sup>
                        </c:if>
                    </div>
                </fieldset>
                <h5 class="mb-0" style="padding-bottom: 10px;padding-top: 5px;"><fmt:message
                        key="application.departureData"/><br></h5>
                <div class="form-group">
                    <label for="date_from"><fmt:message key="application.date"/></label>
                    <input class="form-control date-input" id="date_from" name="departure_date"
                           required="" value="<ctg:format-date date="${departure_date}" input="true"/>"
                           type="date">
                </div>
                <div class="form-group">
                    <label for="departure_address"><fmt:message key="application.address"/></label>
                    <input class="form-control item" type="text" id="departure_address" placeholder=""
                           name="departure_address" pattern="[\p{L}0-9\s-,.]{1,150}" minlength="1" maxlength="150"
                           title="<fmt:message key="required.address"/>"
                           required="<fmt:message key="required.address"/>"
                           value="${departure_address}">
                    <c:if test="${departure_address_invalid}">
                        <sup class="text-danger">
                            <fmt:message key="error.address"/>
                        </sup>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="departure_city"><fmt:message key="application.city"/></label>
                    <input class="form-control item" type="text" id="departure_city" placeholder=""
                           name="departure_city" minlength="1" maxlength="50"
                           pattern="[\p{L}0-9\s\-]{1,50}" value="${departure_city}"
                           title="<fmt:message key="required.city"/>"
                           required="<fmt:message key="required.city"/>">
                    <c:if test="${departure_city_invalid}">
                        <sup class="text-danger">
                            <fmt:message key="error.city"/>
                        </sup>
                    </c:if>
                </div>
                <h5 class="mb-0" style="padding-bottom: 10px;padding-top: 5px;"><fmt:message
                        key="application.arrivalData"/><br></h5>
                <div class="form-group">
                    <label for="date_to"><fmt:message key="application.date"/></label>
                    <input class="form-control date-input" id="date_to" name="arrival_date"
                           required="" value="<ctg:format-date date="${arrival_date}" input="true"/>"
                           type="date">
                </div>
                <div class="form-group">
                    <label for="arrival_address"><fmt:message key="application.address"/></label>
                    <input class="form-control item" type="text" id="arrival_address" placeholder=""
                           name="arrival_address" pattern="[\p{L}0-9\s-,./]{1,150}"
                           minlength="1" maxlength="150" value="${arrival_address}"
                           title="<fmt:message key="required.address"/>"
                           required="<fmt:message key="required.address"/>">
                    <c:if test="${arrival_address_invalid}">
                        <sup class="text-danger">
                            <fmt:message key="error.address"/>
                        </sup>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="arrival_city"><fmt:message key="application.city"/></label>
                    <input class="form-control item" type="text" id="arrival_city" placeholder=""
                           name="arrival_city" minlength="1" maxlength="50"
                           pattern="[\p{L}0-9\s\-]{1,50}" value="${arrival_city}"
                           title="<fmt:message key="required.city"/>"
                           required="<fmt:message key="required.city"/>">
                    <c:if test="${arrival_city_invalid}">
                        <sup class="text-danger">
                            <fmt:message key="error.city"/>
                        </sup>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="description"><fmt:message key="application.description"/></label>
                    <textarea class="form-control item" id="description" name="description" required=""
                              value="${description}" pattern="[\p{L}0-9\s-,._!?%#&*+\n]+"
                              title="<fmt:message key="required.text"/>">${description}
                    </textarea>
                </div>
                <c:if test="${description_invalid}">
                    <sup class="text-danger" style="margin-bottom: 5px">
                        <fmt:message key="error.description"/>
                    </sup>
                </c:if>
                <div class="form-group">
                <c:choose>
                    <c:when test="${edit_application}">
                        <button class="btn btn-primary btn-block" type="submit" name="application_id" value="${application_id}">
                            <fmt:message key="page.addApplication.submitSave"/>
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-primary btn-block"
                                type="submit"><fmt:message key="page.addApplication.titleAdd"/></button>
                    </c:otherwise>
                </c:choose>
                </div>
                <c:if test="${adding_error}">
                    <div role="alert" class="alert alert-danger" autofocus>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                        <span>
                            <fmt:message key="error.addApplication"/>
                        </span>
                    </div>
                </c:if>
            </form>
        </div>
    </section>
</main>