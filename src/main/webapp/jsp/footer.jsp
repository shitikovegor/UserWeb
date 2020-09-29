<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setBundle basename="properties.pagecontent"/>
<footer id="footer" class="page-footer dark">
    <div class="footer-copyright">
        <p class="d-inline-flex" style="margin-right: 10px;margin-left: 126.845px;">© 2020 Help by car</p>
        <div class="dropup d-inline-flex float-right">
            <button class="btn btn-secondary btn-sm dropdown-toggle text-lowercase d-inline" data-toggle="dropdown"
                    aria-expanded="false" type="button"><fmt:message key="footer.language"/>
            </button>
            <div class="dropdown-menu dropdown-menu-right text-lowercase text-left" role="menu">
                <a class="dropdown-item" role="presentation"
                   href="controller?command=change-language&locale=en_US"><fmt:message key="footer.english"/></a>
                <a class="dropdown-item" role="presentation"
                   href="controller?command=change-language&locale=ru_RU"><fmt:message key="footer.russian"/></a>
            </div>
        </div>
    </div>
</footer>
