<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="/header.jsp" />
<h1>Ricky's Restaurant</h1>
<h2>Menu</h2>
<ul>
    <%--            This here is a usual for-loop, but within JSTL tags. Check documentation for more detailed info--%>
    <c:forEach items="${requestScope.menuItems}" var="menuItem">
        <c:if test="${menuItem.price <= 10}">
            <%--                        .description will be transformed to getDescription() in the servlet file--%>
            <li>${menuItem} | ${menuItem.description}</li>
        </c:if>
    </c:forEach>
</ul>
<jsp:include page="/footer.jsp" />
