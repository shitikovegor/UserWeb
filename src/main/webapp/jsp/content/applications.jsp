<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>
<main class="page catalog-page">
    <section class="clean-block clean-catalog dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info"><fmt:message key="page.applications.title"/></h2>
                <p><fmt:message key="page.applications.text"/></p>
            </div>
            <div class="content">
                <div class="row">
                    <div class="col-md-3">
                        <div class="d-none d-md-block">
                            <div class="filters">
                                <form name="searchForm" action="controller" method="post">
                                    <input type="hidden" name="command" value="search">
                                    <div class="filter-item">
                                        <h3><fmt:message key="application.applicationType"/></h3>
                                        <div class="custom-control custom-checkbox">
                                            <input class="custom-control-input" type="checkbox" id="cargo"
                                                   name="cargo" value="cargo"
                                            <c:if test="${cargo != null}">
                                                   checked
                                            </c:if>>
                                            <label class="custom-control-label" for="cargo"><fmt:message
                                                    key="application.cargo"/></label>
                                        </div>
                                        <div class="custom-control custom-checkbox">
                                            <input class="custom-control-input" type="checkbox" id="passenger"
                                                   name="passenger" value="passenger"
                                            <c:if test="${passenger != null}">
                                                   checked
                                            </c:if>>
                                            <label class="custom-control-label" for="passenger"><fmt:message
                                                    key="application.passenger"/></label>
                                        </div>
                                    </div>
                                    <div class="filter-item">
                                        <h3><fmt:message key="page.applications.departureDate"/></h3>
                                        <div class="form-group" style="margin-bottom: 6px;">
                                            <label class="date-range-name" for="date_from"><fmt:message
                                                    key="page.applications.from"/><br></label>
                                            <input id="date_from" class="form-control item" type="date"
                                                   style="margin-bottom: 4px;"
                                                   placeholder="From" name="departure_date_from"
                                                   value="${departure_date_from}">
                                        </div>
                                        <div
                                                class="form-group">
                                            <label class="date-range-name" for="date_to"><fmt:message
                                                    key="page.applications.to"/><br></label>
                                            <input id="date_to" class="form-control item" type="date"
                                                   style="margin-bottom: 4px;"
                                                   placeholder="<fmt:message key="page.applications.from"/>"
                                                   name="departure_date_to" value="${departure_date_to}">
                                        </div>
                                    </div>
                                    <div class="filter-item">
                                        <h3 for="passenger_number"><fmt:message key="application.passengersNumber"/></h3>
                                        <div class="input-group" id="passenger_number">
                                            <input class="form-control" type="number" id="passenger_number_from"
                                                   placeholder="<fmt:message key="page.applications.from"/>"
                                                   name="passenger_number_from" value="${passenger_number_from}">
                                            <input class="form-control" type="number"
                                                   placeholder="<fmt:message key="page.applications.to"/>"
                                                   name="passenger_number_to" value="${passenger_number_to}">
                                        </div>
                                    </div>
                                    <div class="filter-item">
                                        <h3 for="cargo_weight"><fmt:message key="application.cargoWeight"/><br></h3>
                                        <div class="input-group" id="cargo_weight">
                                            <input class="form-control" type="number" id="cargo_weight_from"
                                                   placeholder="<fmt:message key="page.applications.from"/>"
                                                   name="cargo_weight_from" value="${cargo_weight_from}">
                                            <input class="form-control" type="number" id="cargo_weight_to"
                                                   placeholder="<fmt:message key="page.applications.to"/>"
                                                   name="cargo_weight_to" value="${cargo_weight_to}">
                                        </div>
                                    </div>
                                    <div class="filter-item">
                                        <h3 for="cargo_volume"><fmt:message key="application.cargoVolume"/></h3>
                                        <div class="input-group" id="cargo_volume">
                                            <input class="form-control" type="number" id="cargo_volume_from"
                                                   placeholder="from" name="cargo_volume_from" value="${cargo_volume_from}">
                                            <input class="form-control" type="number" id="cargo_volume_to"
                                                   placeholder="<fmt:message key="page.applications.to"/>"
                                                   name="cargo_volume_to" value="${cargo_volume_to}">
                                        </div>
                                    </div>
                                    <div class="filter-item">
                                        <h3><fmt:message key="application.city"/></h3>
                                        <input type="text" class="form-control item" value="${city}" name="city">
                                    </div>
                                    <div class="filter-item">
                                        <h3><fmt:message key="application.status"/></h3>
                                        <div class="custom-control custom-checkbox">
                                            <input class="custom-control-input" type="checkbox" id="active" name="active"
                                                   value="active"
                                            <c:if test="${active != null}">
                                                   checked
                                            </c:if>>
                                            <label class="custom-control-label" for="active"><fmt:message
                                                    key="order.status.active"/></label>
                                        </div>
                                        <div class="custom-control custom-checkbox">
                                            <input class="custom-control-input" type="checkbox" id="confirmed" name="confirmed"
                                                   value="confirmed"
                                            <c:if test="${confirmed != null}">
                                                   checked
                                            </c:if>>
                                            <label class="custom-control-label" for="confirmed"><fmt:message
                                                    key="order.status.confirmed"/></label>
                                        </div>
                                        <div class="custom-control custom-checkbox">
                                            <input class="custom-control-input" type="checkbox" id="completed" name="completed"
                                                   value="completed"
                                            <c:if test="${completed != null}">
                                                   checked
                                            </c:if>>
                                            <label class="custom-control-label" for="completed"><fmt:message
                                                    key="order.status.completed"/></label>
                                        </div>
                                        <div class="custom-control custom-checkbox">
                                            <input class="custom-control-input" type="checkbox" id="canceled" name="canceled"
                                                   value="canceled"
                                            <c:if test="${canceled != null}">
                                                   checked
                                            </c:if>>
                                            <label class="custom-control-label" for="canceled"><fmt:message
                                                    key="order.status.canceled"/></label>
                                        </div>
                                    </div>
                                    <div>
                                        <button class="btn btn-primary btn-block" type="submit"
                                                style="margin-bottom: 25px;">
                                            <fmt:message key="page.applications.search"/>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-9">
                        <div class="products">
                            <ctg:app-pagination pageNumber="${current_number}" applicationsOnPage="${objects_on_page}"/>
                            <nav>
                                <ul class="pagination" style="margin-top: 40px;">
                                    <c:if test="${current_number > 1}">
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="controller?command=pagination&current_number=${current_number - 1}"
                                           aria-label="Previous"><span
                                                aria-hidden="true">«</span></a></li>
                                    </c:if>
                                    <li class="page-item active disabled"><a class="page-link">${current_number}</a></li>
                                    <c:if test="${current_number < total_pages}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="controller?command=pagination&current_number=${current_number + 1}"
                                               aria-label="Next"><span aria-hidden="true">»</span></a></li>
                                    </c:if>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>