<main class="page registration-page">
    <section class="clean-block clean-form dark">
        <div class="container">
            <div class="block-heading">
                <h2 class="text-info"><fmt:message key="page.registration.pageName"/></h2>
                <p><fmt:message key="page.registration.description"/></p>
            </div>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="registration"/>
                <div class="form-group">
                    <label for="login"><fmt:message key="common.login"/></label>
                    <input class="form-control item" type="text" id="login"
                           required="<fmt:message key="page.registration.required.login"/>"
                           pattern="^(?=.*[A-Za-z0-9]$)[a-zA-Z][a-zA-Z0-9._-]+" minlength="4" maxlength="20"
                           name="login"
                           value="${login}"
                           title="<fmt:message key="page.registration.required.login"/>">
                    <small class="text-danger">
                        <c:if test="${loginExists}">
                            <fmt:message key="page.registration.error.loginExists"/>
                        </c:if>
                        <c:if test="${loginError}">
                            <fmt:message key="page.registration.required.login"/>
                        </c:if>
                    </small>
                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="common.password"/></label>
                    <input class="form-control item" type="password" id="password"
                           required="<fmt:message key="page.registration.required.password"/>"
                           pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d@$!%*?&]+" minlength="6" maxlength="20"
                           name="password"
                           value="${password}"
                           title="<fmt:message key="page.registration.required.password"/>">
                    <small class="text-danger">
                        <c:if test="${passwordError}">
                            <fmt:message key="page.registration.required.password"/>
                        </c:if>
                    </small>
                </div>
                <div class="form-group">
                    <label for="email"><fmt:message key="common.email"/></label>
                    <input class="form-control item" type="email" id="email" required="" name="email"
                           value="${email}">
                    <small class="text-danger">
                        <c:if test="${emailError}">
                            <fmt:message key="page.registration.error.email"/>
                        </c:if>
                        <c:if test="${emailExists}">
                            <fmt:message key="page.registration.error.emailExists"/>
                        </c:if>
                    </small>
                </div>

                <c:choose>
                    <c:when test="${subject == 'individual'}">
                        <c:set var="individualSelect" value="true"/>
                    </c:when>
                    <c:when test="${subject == 'organization'}">
                        <c:set var="organizationSelect" value="true"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="invalidSelect" value="true"/>
                    </c:otherwise>
                </c:choose>
                <div class="form-group">
                    <input type="radio" name="subject" value="individual" required=""
                    ${individualSelect ? 'checked' : ''}>
                    <p class="d-inline" style="margin-right: 30px;margin-top: 0px;margin-left: 5px;">
                        <fmt:message key="page.registration.individual"/></p>
                    <input type="radio" name="subject" value="organization" required=""
                    ${organizationSelect ? 'checked' : ''}>
                    <p class="d-inline" style="margin-right: 30px;margin-top: 0px;margin-left: 5px;">
                        <fmt:message key="page.registration.organization"/></p>
                </div>
                <div class="form-group">
                    <label for="name"><fmt:message key="common.name"/></label>
                    <input class="form-control item" type="text" id="name" required="" name="name"
                           pattern="[\p{L}\s-]{1,50}" minlength="1" maxlength="50"
                           value="${name}">
                    <small class="text-danger">
                        <c:if test="${nameError}">
                            <fmt:message key="page.registration.error.name"/>
                        </c:if>
                    </small>
                </div>
                <div class="form-group">
                    <label for="surname"><fmt:message key="common.surname"/></label>
                    <input class="form-control item" type="text" id="surname" name="surname"
                           pattern="[\p{L}\s-]{1,50}" minlength="1" maxlength="50"
                           value="${surname}">
                    <small class="text-danger">
                        <c:if test="${surnameError}">
                            <fmt:message key="page.registration.error.surname"/>
                        </c:if>
                    </small>
                </div>
                <div class="form-group">
                    <label for="phone"><fmt:message key="common.phone"/></label>
                    <input class="form-control item" type="tel" id="phone"
                           required="<fmt:message key="page.registration.required.phone"/>"
                           pattern="^\+?\d{12}" minlength="12" maxlength="13"
                           name="phone"
                           value="${phone}"
                           title="<fmt:message key="page.registration.required.phone"/>">
                    <small class="text-danger">
                        <c:if test="${phoneError}">
                            <fmt:message key="page.registration.required.phone"/>
                        </c:if>
                    </small>
                </div>

                <c:choose>
                    <c:when test="${role == 'driver'}">
                        <c:set var="driverSelect" value="true"/>
                    </c:when>
                    <c:when test="${role == 'client'}">
                        <c:set var="clientSelect" value="true"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="invalidSelect" value="true"/>
                    </c:otherwise>
                </c:choose>
                <div class="form-group text-left">
                    <input type="radio" name="role" value="driver" required=""
                    ${driverSelect ? 'checked' : ''}>
                    <p class="d-inline" style="margin-right: 30px;margin-top: 0px;margin-left: 5px;"><fmt:message
                            key="page.registration.driver"/></p>
                    <input type="radio" name="role" value="client" required=""
                    ${clientSelect ? 'checked' : ''}>
                    <p class="d-inline" style="margin-right: 30px;margin-top: 0px;margin-left: 5px;"><fmt:message
                            key="page.registration.client"/></p>
                </div>
                <input class="btn btn-primary btn-block" type="submit"
                       value="<fmt:message key="page.registration.signup"/>">

            </form>
        </div>
    </section>
</main>