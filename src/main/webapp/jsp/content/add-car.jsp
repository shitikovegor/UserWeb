<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>
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