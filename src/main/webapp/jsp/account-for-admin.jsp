<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="properties.pagecontent"/>

<c:import url="baseLayout.jsp">
    <c:param name="title">
        <fmt:message key="title.account"/>
    </c:param>
    <c:param name="page" value="account-for-admin"/>
</c:import>