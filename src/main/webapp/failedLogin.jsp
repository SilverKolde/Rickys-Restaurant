<%@ page contentType="text/html;charset=UTF-8" %>


<jsp:include page="/header.jsp" />
<h2>Sorry, we don't recognize you. Please try again...</h2>

<form action="j_security_check" method="POST">
    Username: <input type="text" name="j_username" />
    Password: <input type="password" name="j_password" />
    <input type="submit" value="login" />
</form>

<jsp:include page="/footer.jsp" />
