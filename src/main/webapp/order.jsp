<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="/header.jsp" />
<h1>Ricky's Restaurant</h1>
<h2>Order your food</h2>
<form action='orderReceived.html' method='POST' >
    <ul>
        <c:forEach items="${requestScope.menuItems}" var="menuItem">
            <li>${menuItem}<input type="text" name="item_${menuItem.id}"/></li>
        </c:forEach>
    </ul>
    <input type='submit' />
</form></body></html>
<jsp:include page="/footer.jsp" />
