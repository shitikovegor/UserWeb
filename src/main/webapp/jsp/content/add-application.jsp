<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>

<main class="page registration-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info"><fmt:message key="page.addApplication.title"/></h2>
                <p>Please, details of your application</p>
            </div>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="add-application">
                <div class="form-group">
                    <label for="title">Title</label>
                    <input class="form-control item" type="text" id="title"
                           pattern="[\p{L}0-9\s\-,]{1,100}" minlength="1" maxlength="100"
                           name="car_number" title="Car number must be in format 1111AA1" required="" value="${title}">
                </div>
                <div class="form-group">
                    <label>Type of&nbsp;transportation</label>
                    <div>
                        <input type="radio" id="cargo" name="application_type" value="cargo" required="" onclick="toggleSet()">
                        <p class="d-inline" style="margin-right: 30px;margin-top: 0px;margin-left: 5px;">cargo</p>
                        <input type="radio" id="passenger" name="application_type" value="passenger" required="" onclick="toggleSet()">
                        <p class="d-inline" style="margin-right: 30px;margin-top: 0px;margin-left: 5px;">passenger</p>
                    </div>
                </div>
                <fieldset id="cargo_set" class="item" style="display: none;">
                    <div class="form-group">
                        <label for="carrying_weight">Carryng (weight)</label>
                        <input class="form-control item" type="text" id="carrying_weight"
                               pattern="\d+.*\d*" name="carrying_weight" value="${carrying_weight}" placeholder="0">
                    </div>
                    <div class="form-group">
                        <label for="carrying_volume">Carryng (volume)<br></label>
                        <input class="form-control item" type="text" id="carrying_volume"
                               name="carrying_volume" value="${carrying_volume}" pattern="\d+.*\d*" placeholder="0" inputmode="numeric">
                    </div>
                </fieldset>
                <fieldset id="passenger_set" class="application_type_item" style="display: none;">
                    <div class="form-group">
                        <label for="passengers_number">Number of passengers</label>
                        <input class="form-control item" type="number" id="passengers_number"
                               name="passengers_number" value="${passengers_number}" placeholder="0">
                    </div>
                </fieldset>
                <h5 class="mb-0" style="padding-bottom: 10px;padding-top: 5px;">Departure data<br></h5>
                <div class="form-group">
                    <label for="departure_date">Date</label>
                    <input class="form-control item" id="departure_date" name="departure_date"
                           required="" value="${departure_date}" type="datetime-local" min="${today}">
                </div>
                <div class="form-group">
                    <label for="departure_address">Address</label>
                    <input class="form-control item" type="text" id="departure_address" placeholder=""
                           name="departure_address" pattern="[\p{L}0-9\s\-,]{1,150}"
                           required="" minlength="1" maxlength="150" value="${departure_address}">
                </div>
                <div class="form-group">
                    <label for="departure_city">City</label>
                    <input class="form-control item" type="text" id="departure_city" placeholder=""
                           name="departure_city" required="" minlength="1" maxlength="50"
                           pattern="[\p{L}0-9\s\-]{1,50}" value="${departure_city}">
                </div>
                <h5 class="mb-0" style="padding-bottom: 10px;padding-top: 5px;">Arrival data<br></h5>
                <div class="form-group">
                    <label for="arrival_date">Date</label>
                    <input class="form-control item" id="arrival_date" name="arrival_date"
                           required="" value="${arrival_date}" type="datetime-local" min="${today}">
                </div>
                <div class="form-group">
                    <label for="arrival_address">Address</label>
                    <input class="form-control item" type="text" id="arrival_address" placeholder=""
                           name="arrival_address" pattern="[\p{L}0-9\s\-,]{1,150}" required=""
                           minlength="1" maxlength="150" value="${arrival_address}">
                </div>
                <div class="form-group">
                    <label for="arrival_city">City</label>
                    <input class="form-control item" type="text" id="arrival_city" placeholder=""
                           name="arrival_city" required="" minlength="1" maxlength="50"
                           pattern="[\p{L}0-9\s\-]{1,50}" value="${arrival_city}">
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea class="form-control item" id="description" name="description"
                              value="${description}">
                    </textarea>
                </div>
                <button class="btn btn-primary btn-block" type="submit">Add application</button>
                <c:if test="${adding_error}">
                    <p class="text-center text-danger"><fmt:message key="page.addApplication.error"/></p>
                </c:if>
            </form>
        </div>
    </section>
</main>



<main class="page registration-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info"><fmt:message key="page.addCar.title"/></h2>
                <p><fmt:message key="page.addCar.text"/></p>
            </div>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="add-car"/>
                <div class="form-group">
                    <label for="car_number"><fmt:message key="page.addCar.carNumber"/></label>
                    <input class="form-control car_number" type="text" id="car_number"
                           pattern="\d{4}\p{Alpha}{2}[1-7]" minlength="7" maxlength="7"
                           name="car_number" title="" required="<fmt:message key="page.addCar.required.carNumber"/>"
                           value="${car_number}">
                    <small class="text-danger">
                        <c:if test="${number_exists}">
                            <fmt:message key="page.registration.error.loginExists"/>
                        </c:if>
                        <c:if test="${number_invalid}">
                            <fmt:message key="page.registration.required.login"/>
                        </c:if>
                    </small>
                </div>
                <div class="form-group">
                    <label for="carrying_weight"><fmt:message key="page.addCar.weight"/></label>
                    <input class="form-control item" type="text" id="carrying_weight" pattern="\d+.*\d*"
                           placeholder="0" name="carrying_weight" value="${carrying_weight}">
                </div>
                <div class="form-group">
                    <label for="carrying_volume"><fmt:message key="page.addCar.volume"/><br></label>
                    <input class="form-control item" type="text" id="carrying_volume" name="carrying_volume"
                           placeholder="0" value="${carrying_volume}" pattern="\d+\.*\d*">
                </div>
                <div class="form-group">
                    <label for="passengers_number"><fmt:message key="page.addCar.passengers"/></label>
                    <input class="form-control item" type="number" id="passengers_number"
                           placeholder="0" name="passengers_number" value="${passengers_number}">
                </div>
                <button class="btn btn-primary btn-block" type="submit"><fmt:message key="page.addCar.title"/></button>
                <c:if test="${adding_error}">
                    <p class="text-center text-danger"><fmt:message key="page.addCar.error"/></p>
                </c:if>
            </form>
        </div>
    </section>
</main>