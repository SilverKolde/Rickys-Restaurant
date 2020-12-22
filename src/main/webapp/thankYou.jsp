<%--
  Created by IntelliJ IDEA.
  User: silverko
  Date: 26.08.2020
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <script>
            function updateStatus(){
                var request = new XMLHttpRequest();

                // This is a callback function. Basically if status is changed (somewhere), this function changes the text.
                // This way only status text is updated and whole page is not reloaded.
                request.onreadystatechange = function () {
                    if (this.readyState === 4) {
                        var json = JSON.parse(this.responseText);
                        document.getElementById("status").innerHTML = this.status;
                        document.getElementById("time").innerHTML = "Last updated (polling for updates) : " + json.time;
                    }
                }

                request.open("GET", "/updatedStatus?id=${id}", true)
                request.send();
            }

            // now we will let this function run every 2 seconds
            window.setInterval(function (){
                updateStatus()
            }, 1000)
        </script>
    </head>
    <body>
        <jsp:include page="/header.jsp"/>

        <%-- With the jstl taglib we can access all the request variables with the ${foo} syntax--%>

        <p>Thank you - your order has been received. You need to pay</p>
        <fmt:formatNumber currencyCode="${currency}" value="${total}" type="currency"/>
        <p>The current status of your order is: <span id="status">${status}</span> <input type="button"
                                                                                      value="refresh status"
                                                                                      onclick="updateStatus()"/></p>
        <p id="time"></p>

        <jsp:include page="/footer.jsp"/>
    </body>
</html>

