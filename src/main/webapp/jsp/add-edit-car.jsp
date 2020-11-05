<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="properties.pagecontent"/>

<c:import url="baseLayout.jsp">
    <c:param name="title">
        <c:choose>
            <c:when test="${edit_car}">
                <fmt:message key="title.editCar"/>
            </c:when>
            <c:otherwise>
                <fmt:message key="title.addCar"/>
            </c:otherwise>
        </c:choose>
    </c:param>
    <c:param name="page" value="add-edit-car"/>
</c:import>