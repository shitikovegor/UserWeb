<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>
<main class="page registration-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <c:choose>
                    <c:when test="${edit_car}">
                        <h2 class="text-info"><fmt:message key="page.addEditCar.titleEdit"/></h2>
                    </c:when>
                    <c:otherwise>
                        <h2 class="text-info"><fmt:message key="page.addEditCar.titleAdd"/></h2>
                    </c:otherwise>
                </c:choose>
                <p><fmt:message key="page.addEditCar.text"/></p>
            </div>
            <form action="controller" method="post">
                <c:choose>
                    <c:when test="${edit_car}">
                        <input type="hidden" name="command" value="edit-car"/>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="command" value="add-car"/>
                    </c:otherwise>
                </c:choose>
                <div class="form-group">
                    <label for="car_number"><fmt:message key="car.carNumber"/></label>
                    <input class="form-control car_number" type="text" id="car_number"
                           pattern="\d{4}\p{Alpha}{2}[1-7]" minlength="7" maxlength="7"
                           name="car_number" title="<fmt:message key="required.carNumber"/>" required="<fmt:message key="required.carNumber"/>"
                           value="${car_number}">
                    <small class="text-danger">
                        <c:if test="${number_exists}">
                            <fmt:message key="error.carExists"/>
                        </c:if>
                        <c:if test="${number_invalid}">
                            <fmt:message key="error.dataInvalid"/>
                        </c:if>
                    </small>
                </div>
                <div class="form-group">
                    <label for="carrying_weight"><fmt:message key="car.carryingWeight"/></label>
                    <input class="form-control item" type="text" id="carrying_weight" pattern="\d+\.*\d*"
                           placeholder="0" name="carrying_weight" value="${carrying_weight}">
                </div>
                <div class="form-group">
                    <label for="carrying_volume"><fmt:message key="car.carryingVolume"/><br></label>
                    <input class="form-control item" type="text" id="carrying_volume" name="carrying_volume"
                           placeholder="0" value="${carrying_volume}" pattern="\d+\.*\d*">
                </div>
                <div class="form-group">
                    <label for="passengers_number"><fmt:message key="car.passengersNumber"/></label>
                    <input class="form-control item" type="number" id="passengers_number"
                           placeholder="0" name="passengers_number" value="${passengers_number}">
                </div>
                <c:choose>
                    <c:when test="${edit_car}">
                        <button class="btn btn-primary btn-block" type="submit" name="car_id" value="${car_id}">
                            <fmt:message key="page.addEditCar.submitSave"/>
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-primary btn-block" type="submit">
                            <fmt:message key="page.addEditCar.titleAdd"/>
                        </button>
                    </c:otherwise>
                </c:choose>

                <c:if test="${adding_error}">
                    <div role="alert" class="alert alert-danger" autofocus>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                        <span>
                            <fmt:message key="error.addCar"/>
                        </span>
                    </div>
                </c:if>
            </form>
        </div>
    </section>
</main>