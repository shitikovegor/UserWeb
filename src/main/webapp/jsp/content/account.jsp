<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>


<main>
    <div class="container justify-content-center">
        <h2 class="text-center text-primary mb-4"
            style="padding-top: 20px;"><fmt:message key="page.account.title"/></h2>
        <div class="card d-none mb-3">
            <div class="card-body text-center shadow"><img class="rounded-circle mb-3 mt-4" src="dogs/image2.jpeg"
                                                           width="160" height="160">
                <div class="mb-3">
                    <button class="btn btn-primary btn-sm" type="button">Change Photo</button>
                </div>
            </div>
        </div>
        <c:if test="${sessionScope.user.roleType == 'CLIENT'}">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="text-primary font-weight-bold m-0"><fmt:message key="page.account.applications"/></h6>
            </div>
            <div class="card-body">
                <c:forEach var="car-request" items="${car-requests}">
                    <div>
                        <p style="margin-bottom: 5px;"><strong>${car-request.name}</strong>
                            <span class="float-right"><strong>${car-request.status}</strong></span></p>
                        <p style="margin-bottom: 5px;">${car-request.address}, ${car-request.date}
                            <a class="float-right" href="controller?command=edit-request"><fmt:message key="page.account.edit"/></a></p>
                    </div>
                </c:forEach>
                <form class="form-group" action="controller" method="post">
                    <input type="hidden" name="command" value="add-request">
                    <button class="btn btn-primary btn-sm" type="submit" style="margin-top: 10px;"><fmt:message
                            key="page.account.addApplication"/></button>
                </form>
            </div>
        </div>
        </c:if>
        <c:if test="${sessionScope.user.roleType == 'DRIVER'}">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="text-primary font-weight-bold m-0"><fmt:message key="page.account.cars"/></h6>
            </div>
            <div class="card-body">
                <c:forEach var="car" items="${cars}">
                    <div>
                        <p style="margin-bottom: 5px;"><strong>${car.getNumber()}</strong></p>
                        <p style="margin-bottom: 5px;">${car.getCarryng()}, ${car.getPassengers()}
                            <a class="float-right" href="controller?command=edit-car"><fmt:message
                                    key="page.account.edit"/></a></p>
                    </div>
                </c:forEach>
                <form class="form-group" action="controller" method="post">
                    <input type="hidden" name="command" value="add-car">
                    <button class="btn btn-primary btn-sm" type="submit" style="margin-top: 10px;"><fmt:message
                            key="page.account.addCar"/></button>
                </form>
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
            <div class="collapse item-1" role="tabpanel" data-parent="#accordion-1">
                <div class="card-body">
                    <div class="card shadow mb-3">
                        <div class="card-header py-3">
                            <p class="text-primary m-0 font-weight-bold"><fmt:message
                                    key="page.account.userSettings"/></p>
                        </div>
                        <div class="card-body">
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="save-settings">
                                <div class="form-row">
                                    <div class="col">
                                        <div class="form-group"><label for="login"><fmt:message
                                                key="common.login"/></label>
                                            <input class="form-control" type="text" placeholder="" name="login"
                                                   id="login" value="${sessionScope.user.login}"
                                                   pattern="^(?=.*[A-Za-z0-9]$)[a-zA-Z][a-zA-Z0-9._-]+" minlength="4" maxlength="20">
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group"><label for="email"><fmt:message
                                                key="common.email"/></label>
                                            <input class="form-control" type="email" id="email" name="email" placeholder=""
                                                   value="${sessionScope.user.email}">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="col">
                                        <div class="form-group"><label for="name"><fmt:message
                                                key="common.name"/></label>
                                            <input class="form-control" type="text" placeholder="" value="${sessionScope.user.name}"
                                                   id="name" name="name"
                                                   pattern="[\p{L}\s-]{1,50}" minlength="1" maxlength="50"></div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group"><label for="surname"><fmt:message
                                                key="common.surname"/></label>
                                            <input class="form-control" type="text" placeholder="" value="${sessionScope.user.surname}"
                                                   id="surname" name="surname"
                                                   pattern="[\p{L}\s-]{1,50}" minlength="1" maxlength="50">
                                        </div>
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
                                <input type="hidden" name="command" value="save-settings">
                                <div class="form-group"><label for="address"><fmt:message
                                        key="page.account.address"/></label>
                                    <input class="form-control" type="text" placeholder="" id="address" name="address"
                                           value="${address}"></div>
                                <div class="form-row">
                                    <div class="col">
                                        <div class="form-group"><label for="city"><fmt:message
                                                key="page.account.city"/></label>
                                            <input class="form-control" type="text" id="city" name="city" placeholder=""
                                                   value="${city}"></div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group"><label for="phone"><fmt:message
                                                key="common.phone"/></label>
                                            <input class="form-control" type="tel" placeholder="" value="${sessionScope.user.phone}"
                                                   id="phone" name="phone"
                                                   pattern="^\+?\d{12}" minlength="12" maxlength="13"></div>
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
                                    key="page.account.changePassword"/></p>
                        </div>
                        <div class="card-body">
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="save-password">
                                <div class="form-row">
                                    <div class="col">
                                        <div class="form-group"><label for="password"><fmt:message
                                                key="common.password"/></label>
                                            <input class="form-control" type="password" id="password"  placeholder=""
                                                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d@$!%*?&]+" minlength="6" maxlength="20"
                                                   name="old-password"></div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group"><label for="password"><fmt:message
                                                key="page.account.newPassword"/></label>
                                            <input class="form-control" type="password" placeholder=""
                                                   name="new-password"></div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button class="btn btn-primary btn-sm" type="submit"><fmt:message
                                            key="page.account.savePassword"/></button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>