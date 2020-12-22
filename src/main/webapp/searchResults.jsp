<%@ page import="com.virtualpairprogrammers.domain.MenuItem" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="/header.jsp" />
<jsp:include page="/searchBoilerplate.jsp" />
<h2>Your search results</h2>
<ul>
    <%
        List<MenuItem> menuItems = (List<MenuItem>) request.getAttribute("menuItems");
        for (MenuItem menuItem : menuItems) {
    %>
    <li> <%=menuItem%> </li>
    <% } %>
</ul>
<jsp:include page="/footer.jsp" />
