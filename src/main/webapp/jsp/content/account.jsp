<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>
<c:set var="show" value="${show_accordion ? 'show' : ''}"/>
<main
<c:if test="${show_accordion}">
    style="margin-bottom: 387px"
</c:if>>
    <div class="container justify-content-center">
        <h2 class="text-center text-primary mb-4"
            style="padding-top: 20px;"><fmt:message key="page.account.title"/></h2>
        <c:if test="${sessionScope.role == 'CLIENT'}">
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="text-primary font-weight-bold m-0"><fmt:message key="page.account.applications"/></h6>
                </div>
                <div class="card-body">
                    <c:if test="${applications.isEmpty()}">
                        <p style="margin-bottom: 5px;">
                            <fmt:message key="page.account.noOrders"/>
                        </p>
                    </c:if>
                    <c:forEach var="entry" items="${applications}">
                        <c:set var="application" value="${entry.key}"/>
                        <c:set var="status" value="${entry.value}"/>
                        <div class="card" style="margin-top: 5px;margin-bottom: 10px;">
                            <div class="card-header bg-light">
                                <p style="margin-bottom: 5px;">
                                    <strong><fmt:message key="application.appTitle"/>: ${application.title}</strong>
                                    <span class="float-right">
                                        <strong><fmt:message key="application.status"/>:
                                                <fmt:message key="order.status.${status.name}"/></strong>
                                    </span>
                                </p>
                                <div class="row" style="margin-top: 6px;margin-bottom: 5px;margin-right: 0px;margin-left: 0px;">
                                    <div class="col-lg-3" style="padding-right: 0px;padding-left: 0px;">
                                        <p style="margin-bottom: 0px;">
                                            <fmt:message key="application.date"/>:
                                            <ctg:format-date date="${application.date}"/><br />
                                        </p>
                                    </div>
                                    <div class="col-lg-3" style="padding-right: 0px;padding-left: 0px;">
                                        <p style="margin-bottom: 0px;">
                                            <fmt:message key="application.type"/>:
                                            <fmt:message key="application.${application.applicationType.name}"/><br />
                                        </p>
                                    </div>
                                    <div class="col-lg-6 offset-lg-0" style="margin-right: 0px; margin-bottom: -1em;">
                                        <c:if test="${status == 'ACTIVE'}">
                                            <form class="float-right" style="padding-left: 20px;margin-right: -15px;"
                                                  id="remove-app${application.applicationId}" action="controller" method="post">
                                                <input type="hidden" name="application_id" value="${application.applicationId}">
                                                <input type="hidden" name="command" value="remove-application">
                                                <a href="javascript:document.getElementById('remove-app${application.applicationId}').submit()">
                                                    <fmt:message key="page.account.remove"/></a>
                                            </form>
                                            <form class="float-right" id="edit-page${application.applicationId}" action="controller"
                                                  method="post">
                                                <input type="hidden" name="application_id" value="${application.applicationId}">
                                                <input type="hidden" name="command" value="edit-application-page">
                                                <a href="javascript:document.getElementById('edit-page${application.applicationId}').submit()">
                                                    <fmt:message key="page.account.edit"/></a>
                                            </form>
                                        </c:if>
                                        <c:if test="${status == 'CONFIRMED'}">
                                            <form class="float-right" style="padding-left: 20px;margin-right: -15px;"
                                                  id="cancel-app${application.applicationId}" action="controller"
                                                  method="post">
                                                <input type="hidden" name="application_id" value="${application.applicationId}">
                                                <input type="hidden" name="command" value="cancel-application">
                                                <a
                                                        href="javascript:document.getElementById('cancel-app${application.applicationId}').submit()">
                                                    <fmt:message key="page.account.cancelOrder"/></a>
                                            </form>
                                            <form class="float-right" id="complete${application.applicationId}"
                                                  action="controller"
                                                  method="post">
                                                <input type="hidden" name="application_id" value="${application.applicationId}">
                                                <input type="hidden" name="command" value="complete-application">
                                                <a href="javascript:document.getElementById('complete${application.applicationId}').submit()">
                                                    <fmt:message key="page.account.complete"/></a>
                                            </form>
                                        </c:if>
                                    </div>
                                    <c:if test="${app_remove_error}">
                                        <div role="alert" class="alert alert-danger" autofocus>
                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                            <span><fmt:message key="error.removeFailed"/></span>
                                        </div>
                                    </c:if>
                                    <c:if test="${app_cancel_error}">
                                        <div role="alert" class="alert alert-danger" autofocus>
                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                            <span><fmt:message key="error.cancelFailed"/></span>
                                        </div>
                                    </c:if>
                                    <c:if test="${app_complete_error}">
                                        <div role="alert" class="alert alert-danger" autofocus>
                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                            <span><fmt:message key="error.completeFailed"/></span>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="card-body">
                                <p class="card-text" style="margin-bottom: 5px;">
                                    <strong class="d-inline-block"><fmt:message key="application.departure"/>: <br /></strong>
                                    <fmt:message key="application.date"/> -
                                    <ctg:format-date date="${application.addressTimeData.departureDate}"/>,
                                    <fmt:message key="application.address"/> -
                                        ${application.addressTimeData.departureAddress.streetHome},
                                    <fmt:message key="application.city"/> -
                                        ${application.addressTimeData.departureAddress.city}
                                </p>
                                <p class="card-text" style="margin-bottom: 5px;">
                                    <strong class="d-inline-block"><fmt:message key="application.arrival"/>: <br /></strong>
                                     <fmt:message key="application.date"/> -
                                    <ctg:format-date date="${application.addressTimeData.arrivalDate}"/>,
                                    <fmt:message key="application.address"/> -
                                        ${application.addressTimeData.arrivalAddress.streetHome},
                                    <fmt:message key="application.city"/> -
                                        ${application.addressTimeData.arrivalAddress.city}
                                </p>
                                <p class="card-text" style="margin-bottom: 5px;">
                                    <c:choose>
                                        <c:when test="${application.applicationType == 'CARGO'}">
                                            <fmt:message key="page.account.weight"/> -
                                            ${application.cargoWeight},
                                            <fmt:message key="page.account.volume"/> -
                                            ${application.cargoVolume}
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="application.passengersNumber"/> -
                                            ${application.passengersNumber}
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                                <p class="card-text" style="margin-bottom: 5px;">
                                    <strong class="d-inline-block">
                                        <fmt:message key="application.description"/>: <br /></strong>
                                     ${application.description}
                                </p>
                            </div>
                        </div>
                    </c:forEach>
                    <form class="form-group" action="controller" method="post">
                        <input type="hidden" name="command" value="add-application-page">
                        <button class="btn btn-primary btn-sm" type="submit" style="margin-top: 10px;"><fmt:message
                                key="page.account.addApplication"/></button>
                    </form>
                </div>
            </div>
        </c:if>
        <c:if test="${sessionScope.role == 'DRIVER'}">
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="text-primary font-weight-bold m-0"><fmt:message key="page.account.orders"/></h6>
                </div>
                <div class="card-body">
                    <c:if test="${orders.isEmpty()}">
                        <p style="margin-bottom: 5px;">
                            <fmt:message key="page.account.noOrders"/>
                        </p>
                    </c:if>
                    <c:forEach var="entry" items="${orders}">
                        <c:set var="order" value="${entry.key}"/>
                        <c:set var="phone" value="${entry.value}"/>
                        <c:set var="application" value="${order.application}"/>
                        <div class="card" style="margin-bottom: 15px;">
                            <div class="card-body">
                                <div class="row" style="border: none;margin-bottom: 5px;">
                                    <div class="col">
                                        <h5 class="d-inline">${application.getTitle()}</h5>
                                    </div>
                                    <div class="col" style="max-width: 215px;">
                                        <div class="form-group">
                                            <h6 class="text-info float-right mb-2"
                                                id="status" name="status"><fmt:message
                                                    key="order.status.${order.status.name}"/></h6>
                                            <h6 class="text-info float-right mb-2" for="status">
                                                <fmt:message key="application.status"/>:&nbsp;</h6>
                                        </div>
                                    </div>
                                </div>
                                <hr style="margin-top: 6px;margin-bottom: 8px;">
                                <div class="row" style="border: none;margin-bottom: 5px;">
                                    <div class="col">
                                        <p style="margin-bottom: 5px;">
                                            <strong class="d-inline-block">
                                                <fmt:message key="application.type"/>: <br></strong>
                                            <fmt:message key="application.${application.getApplicationType().name}"/>
                                        </p>
                                    </div>
                                    <c:set var="appAddressTimeData" value="${application.getAddressTimeData()}"/>
                                    <div class="col">
                                        <p style="margin-bottom: 5px;">
                                            <strong class="d-inline-block">
                                                <fmt:message key="application.date"/>: <br></strong>
                                            <ctg:format-date
                                                    date="${application.getAddressTimeData().getDepartureDate()}"/></p>
                                    </div>
                                    <div class="col">
                                        <p style="margin-bottom: 5px;">
                                            <strong class="d-inline-block">
                                                <fmt:message key="application.city"/>: <br></strong>
                                                ${application.getAddressTimeData().getDepartureAddress().getCity()}</p>
                                    </div>
                                </div>
                                <div>
                                    <p class="d-inline" style="margin-bottom: 5px;">
                                        <strong class="d-inline-block">
                                            <fmt:message key="common.phone"/>: <br></strong>
                                            ${phone}</p>
                                    <div class="float-right" style="margin-bottom: 5px;">
                                        <c:if test="${order.status == 'CONFIRMED' && sessionScope.role == 'DRIVER'}">
                                            <form class="float-right app-button" id="cancel-order${order.orderId}"
                                                  action="controller" method="post" style="padding-left: 20px;">
                                                <input type="hidden" name="command" value="remove-order">
                                                <input type="hidden" name="order_id" value="${order.orderId}">
                                                <a href="javascript:document.getElementById('cancel-order${order.orderId}').submit()">
                                                    <fmt:message key="page.account.cancelOrder"/></a>
                                            </form>
                                        </c:if>
                                        <form class="float-right app-button" id="open-app${application.applicationId}"
                                              action="controller" method="post">
                                            <input type="hidden" name="command" value="application-page">
                                            <input type="hidden" name="application_id" value="${application.applicationId}">
                                            <input type="hidden" name="current_page" value="${sessionScope.current_page}">
                                            <input type="hidden" name="status" value="${order.status}">
                                            <a href="javascript:document.getElementById('open-app${application.applicationId}').submit()">
                                                <fmt:message key="page.applications.details"/></a>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <c:if test="${order_remove_error}">
                        <div role="alert" class="alert alert-danger" autofocus>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                            <span>
                                <fmt:message key="error.removeFailed"/>
                            </span>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="text-primary font-weight-bold m-0"><fmt:message key="page.account.cars"/></h6>
                </div>
                <div class="card-body">
                    <c:if test="${cars.isEmpty()}">
                        <p style="margin-bottom: 5px;">
                            <fmt:message key="page.account.noCars"/>
                        </p>
                    </c:if>
                    <c:forEach var="car" items="${cars}">
                        <div class="border rounded shadow-sm"
                             style="padding: 8px; margin-bottom: 10px; margin-top: 5px;">
                            <div>
                                <p class="d-inline" style="margin-bottom: 5px;">
                                        <c:set var="carId" value="${car.carId}"/>
                                    <strong><fmt:message key="car.carNumber"/>: ${car.carNumber}</strong>
                                <form class="float-right" id="remove-car${carId}" action="controller"
                                      method="post" style="padding-left: 20px;">
                                    <input type="hidden" name="car_id" value="${carId}">
                                    <input type="hidden" name="command" value="remove-car">
                                    <a href="javascript:document.getElementById('remove-car${carId}').submit()">
                                        <fmt:message key="page.account.remove"/></a>
                                </form>
                                <form class="float-right" id="edit-car${carId}" action="controller"
                                      method="post">
                                    <input type="hidden" name="car_id" value="${carId}">
                                    <input type="hidden" name="command" value="edit-car-page">
                                    <a href="javascript:document.getElementById('edit-car${carId}').submit()">
                                        <fmt:message key="page.account.edit"/></a>
                                </form>
                                </p>
                            </div>
                            <div>
                                <p style="margin-bottom: 5px;margin-top: 6px;">
                                    <fmt:message key="page.account.carryingCapacity"/>: 
                                    <fmt:message key="page.account.weight"/> - ${car.carryingWeight},
                                    <fmt:message key="page.account.volume"/> - ${car.carryingVolume}
                                    <br /></p>
                                <p style="margin-bottom: 5px;">
                                    <fmt:message key="car.passengersNumber"/>: ${car.passengers}
                                    <br /></p>
                            </div>
                        </div>
                    </c:forEach>
                    <form class="form-group" action="controller" method="post">
                        <input type="hidden" name="command" value="add-car-page">
                        <button class="btn btn-primary btn-sm" type="submit" style="margin-top: 10px;"><fmt:message
                                key="page.account.addCar"/></button>
                    </form>
                    <c:if test="${car_remove_error}">
                        <div role="alert" class="alert alert-danger" autofocus>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                            <span>
                                <fmt:message key="error.removeFailed"/>
                            </span>
                        </div>
                    </c:if>
                    <c:if test="${have_orders}">
                        <div role="alert" class="alert alert-danger" autofocus>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                            <span>
                                <fmt:message key="error.ordersToComplete"/>
                            </span>
                        </div>
                    </c:if>
                </div>
            </div>
        </c:if>
    </div>
    <div role="tablist" id="accordion-1">
        <div class="card">
            <div class="card-header" role="tab">
                <h5 class="mb-0"><a data-toggle="collapse" aria-expanded="false" aria-controls="accordion-1 .item-1"
                                    href="#accordion-1 .item-1" style="font-size: 17px;">
                    <strong><fmt:message key="page.account.settings"/></strong></a></h5>
            </div>
            <div class="collapse ${show} item-1" role="tabpanel" data-parent="#accordion-1">
                <div class="card-body">
                    <div class="card shadow mb-3">
                        <div class="card-header py-3">
                            <p class="text-primary m-0 font-weight-bold"><fmt:message
                                    key="page.account.userSettings"/></p>
                        </div>
                        <div class="card-body">
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="save-user-settings">
                                <div class="form-row">
                                    <div class="col">
                                        <div class="form-group"><label for="login"><fmt:message
                                                key="common.login"/></label>
                                            <input class="form-control" type="text" placeholder="" name="login"
                                                   id="login" value="${sessionScope.user.login}"
                                                   pattern="^(?=.*[A-Za-z0-9]$)[a-zA-Z][a-zA-Z0-9._-]+" minlength="4"
                                                   maxlength="20"
                                                   required="<fmt:message key="required.login"/>">
                                        </div>
                                        <c:choose>
                                            <c:when test="${login_exists}">
                                                <sup class="text-danger">
                                                    <fmt:message key="error.loginExists"/>
                                                </sup>
                                            </c:when>
                                            <c:when test="${login_invalid}">
                                                <sup class="text-danger">
                                                    <fmt:message key="required.login"/>
                                                </sup>
                                            </c:when>
                                        </c:choose>
                                        <c:if test="${login_updated}">
                                            <sup class="text-success">
                                                <fmt:message key="page.account.fieldUpdated"/>
                                            </sup>
                                        </c:if>
                                    </div>
                                    <div class="col">
                                        <div class="form-group"><label for="email"><fmt:message
                                                key="common.email"/></label>
                                            <input class="form-control" type="email" id="email" name="email"
                                                   placeholder="" required=""
                                                   value="${sessionScope.user.email}">
                                        </div>
                                        <c:choose>
                                            <c:when test="${email_exists}">
                                                <sup class="text-danger">
                                                    <fmt:message key="error.emailExists"/>
                                                </sup>
                                            </c:when>
                                            <c:when test="${email_invalid}">
                                                <sup class="text-danger">
                                                    <fmt:message key="error.email"/>
                                                </sup>
                                            </c:when>
                                        </c:choose>
                                        <c:if test="${email_updated}">
                                            <sup class="text-success">
                                                <fmt:message key="page.account.fieldUpdated"/>
                                            </sup>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="col">
                                        <div class="form-group"><label for="name"><fmt:message
                                                key="common.name"/></label>
                                            <input class="form-control" type="text" placeholder=""
                                                   value="${sessionScope.user.name}"
                                                   id="name" name="name" required=""
                                                   pattern="[\p{L}\s-]{1,50}" minlength="1" maxlength="50">
                                        </div>
                                        <c:if test="${name_invalid}">
                                            <sup class="text-danger">
                                                <fmt:message key="error.name"/>
                                            </sup>
                                        </c:if>
                                        <c:if test="${name_updated}">
                                            <sup class="text-success">
                                                <fmt:message key="page.account.fieldUpdated"/>
                                            </sup>
                                        </c:if>
                                    </div>
                                    <div class="col">
                                        <div class="form-group"><label for="surname"><fmt:message
                                                key="common.surname"/></label>
                                            <input class="form-control" type="text" placeholder=""
                                                   value="${sessionScope.user.surname}"
                                                   id="surname" name="surname" required=""
                                                   pattern="[\p{L}\s-]{1,50}" minlength="1" maxlength="50">
                                        </div>
                                        <c:if test="${surname_invalid}">
                                            <sup class="text-danger">
                                                <fmt:message key="error.surname"/>
                                            </sup>
                                        </c:if>
                                        <c:if test="${surname_updated}">
                                            <sup class="text-success">
                                                <fmt:message key="page.account.fieldUpdated"/>
                                            </sup>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button class="btn btn-primary btn-sm" type="submit"><fmt:message
                                            key="page.account.save"/></button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="card shadow">
                        <div class="card-header py-3">
                            <p class="text-primary m-0 font-weight-bold"><fmt:message
                                    key="page.account.contactSettings"/></p>
                        </div>
                        <div class="card-body">
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="save-contact-settings">
                                <div class="form-group"><label for="address"><fmt:message
                                        key="page.account.address"/></label>
                                    <input class="form-control" type="text" id="address" name="address"
                                           pattern="[\p{L}0-9\s-,.]{1,150}" minlength="1" maxlength="150"
                                           value="${address}" title="<fmt:message key="required.address"/>"
                                    <c:if test="${not empty address}">
                                           required="<fmt:message key="required.address"/>"
                                    </c:if>>
                                </div>
                                <c:if test="${address_invalid}">
                                    <sup class="text-danger">
                                        <fmt:message key="error.address"/>
                                    </sup>
                                </c:if>
                                <div class="form-row">
                                    <div class="col">
                                        <div class="form-group"><label for="city"><fmt:message
                                                key="page.account.city"/></label>
                                            <input class="form-control" type="text" id="city" name="city"
                                                   pattern="[\p{L}0-9\s\-]{1,50}" minlength="1" maxlength="50"
                                                   value="${city}" title="<fmt:message key="required.city"/>"
                                            <c:if test="${not empty city}">
                                                   required="<fmt:message key="required.city"/>"
                                            </c:if>>
                                        </div>
                                        <c:if test="${city_invalid}">
                                            <sup class="text-danger">
                                                <fmt:message key="error.city"/>
                                            </sup>
                                        </c:if>
                                    </div>
                                    <div class="col">
                                        <div class="form-group"><label for="phone"><fmt:message
                                                key="common.phone"/></label>
                                            <input class="form-control" type="tel" placeholder=""
                                                   value="${sessionScope.user.phone}"
                                                   id="phone" name="phone"
                                                   required="<fmt:message key="required.phone"/>"
                                                   pattern="^\+?\d{12}" minlength="12" maxlength="13">
                                        </div>
                                        <c:if test="${phone_invalid}">
                                            <sup class="text-danger">
                                                <fmt:message key="required.phone"/>
                                            </sup>
                                        </c:if>
                                        <c:if test="${phone_updated}">
                                            <sup class="text-success">
                                                <fmt:message key="page.account.fieldUpdated"/>
                                            </sup>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button class="btn btn-primary btn-sm" type="submit"><fmt:message
                                            key="page.account.save"/></button>
                                </div>
                                <c:if test="${data_invalid}">
                                    <div role="alert" class="alert alert-danger" autofocus>
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                        <span>
                                            <fmt:message key="error.dataInvalid"/>
                                        </span>
                                    </div>
                                </c:if>
                                <c:if test="${contact_updated}">
                                    <div role="alert" class="alert alert-success" autofocus>
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                        <span>
                                            <strong><fmt:message key="page.account.contactUpdated"/></strong>
                                        </span>
                                    </div>
                                </c:if>
                            </form>
                        </div>
                    </div>
                    <div class="card shadow">
                        <div class="card-header py-3">
                            <p class="text-primary m-0 font-weight-bold"><fmt:message
                                    key="page.account.changePassword"/></p>
                        </div>
                        <div class="card-body">
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="save-password">
                                <div class="form-row">
                                    <div class="col">
                                        <div class="form-group"><label for="password"><fmt:message
                                                key="common.password"/></label>
                                            <input class="form-control" type="password" id="password" placeholder=""
                                                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d@$!%*?&]+"
                                                   minlength="6" maxlength="20"
                                                   required="<fmt:message key="required.password"/>"
                                                   name="password">
                                        </div>
                                        <c:if test="${password_invalid}">
                                            <sup class="text-danger">
                                                <fmt:message key="error.password"/>
                                            </sup>
                                        </c:if>
                                    </div>
                                    <div class="col">
                                        <div class="form-group"><label for="new_password"><fmt:message
                                                key="page.account.newPassword"/></label>
                                            <input class="form-control" type="password" id="new_password"
                                                   placeholder=""
                                                   required="<fmt:message key="required.password"/>"
                                                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d@$!%*?&]+"
                                                   name="new_password">
                                        </div>
                                        <c:if test="${new_password_invalid}">
                                            <sup class="text-danger">
                                                <fmt:message key="required.password"/>
                                            </sup>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button class="btn btn-primary btn-sm" type="submit"><fmt:message
                                            key="page.account.savePassword"/></button>
                                </div>
                                <c:if test="${password_updated}">
                                    <div role="alert" class="alert alert-success" autofocus>
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                                        <span>
                                            <strong><fmt:message key="page.account.passwordUpdated"/></strong>
                                        </span>
                                    </div>
                                </c:if>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>