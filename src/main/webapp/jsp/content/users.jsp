<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>
<main class="container justify-content-center">
    <h2 class="text-center text-primary mb-4" style="margin-top: 0px;padding-top: 15px;">
        <fmt:message key="page.users.title"/></h2>
    <div class="card shadow" style="max-width: 925px;">
        <div class="card-header py-3">
            <p class="text-primary m-0 font-weight-bold"><fmt:message key="page.users.tableTitle"/></p>
        </div>
        <div class="card-body">
            <div class="table-responsive table mt-2" id="dataTable" role="grid" aria-describedby="dataTable_info">
                <table class="table my-0" id="userTable">
                    <thead>
                    <tr>
                        <th><fmt:message key="page.users.id"/></th>
                        <th><fmt:message key="common.login"/></th>
                        <th><fmt:message key="page.users.role"/></th>
                        <th><fmt:message key="page.users.activated"/></th>
                        <th><fmt:message key="page.users.blocked"/></th>
                        <th><fmt:message key="page.users.block"/></th>
                        <th><fmt:message key="header.account"/></th>
                    </tr>
                    </thead>
                    <tbody>
                        <ctg:user-pagination pageNumber="${current_number}" usersOnPage="${objects_on_page}"/>
                    </tbody>
                </table>
            </div>
            <nav class="d-lg-flex justify-content-lg-center dataTables_paginate paging_simple_numbers">
                <ul class="pagination" style="margin-top: 20px;">
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
</main>