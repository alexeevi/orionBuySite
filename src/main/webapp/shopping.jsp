<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="shopping.title"/> - <fmt:message key="site.title"/></title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
    <h3><fmt:message key="shopping.title"/>:</h3>
    <ul>
        <c:forEach var="ordered" items="${order.ordered}">
            <li>${ordered.key.product.name}: ${ordered.value}</li>
        </c:forEach>
    </ul>

    <p><a href="."><fmt:message key="button.return"/></a></p>

</body>
</html>
