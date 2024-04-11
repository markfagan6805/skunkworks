<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Example JSP</title>
</head>
<body>
    <h1>Hostnames:</h1>
    <ul>
        <c:forEach var="host" items="${hosts}">
            <li>${host}</li>
        </c:forEach>
    </ul>
</body>
</html>
