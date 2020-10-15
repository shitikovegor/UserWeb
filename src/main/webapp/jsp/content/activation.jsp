<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>
<main class="page login-page">
    <main class="page service-page">
        <section class="clean-block clean-services dark">
            <div class="container">
                <div class="block-heading">
                    <h2 class="text-info" style="margin-top: 60px;"><fmt:message key="page.activation.name"/></h2>
                    <c:if test="${!isAccountActivated}">
                        <p><fmt:message key="page.activation.activate-account"/></p>
                    </c:if>
                    <p><fmt:message key="page.activation.text"/></p>
                </div>
            </div>
        </section>
    </main>
</main>