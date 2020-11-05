<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>
<main class="page service-page">
    <section class="clean-block clean-services dark">
        <div class="container">
            <div class="block-heading">
                <c:choose>
                    <c:when test="${order_added}">
                        <h2 class="text-info" style="margin-top: 60px;"><fmt:message key="page.confirmApp.titleValid"/><br></h2>
                        <p><fmt:message key="page.confirmApp.carInfoValid"/> ${car_number}</p>
                        <p><fmt:message key="page.confirmApp.textValid"/></p>
                        <p style="margin-bottom: 10px;"><fmt:message key="page.confirmApp.contact"/> - +${phone}.</p>
                        <a href="controller?command=account-page"><fmt:message key="header.account"/></a>
                    </c:when>
                    <c:otherwise>
                        <h2 class="text-info" style="margin-top: 60px;"><fmt:message key="page.confirmApp.titleInvalid"/><br></h2>
                        <p style="margin-bottom: 10px;"><fmt:message key="page.confirmApp.carInfoInvalid"/></p>
                        <a href="controller?command=home-page"><fmt:message key="common.backHome"/></a>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </section>
</main>