<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="properties.pagecontent"/>
<fmt:setLocale value="${locale}" scope="session"/>

<c:import url="baseLayout.jsp">
    <c:param name="title">
        <fmt:message key="common.login"/>
    </c:param>
    <c:param name="page" value="login"/>
</c:import>