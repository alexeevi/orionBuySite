<%@ page import="ivan.controller.MainController" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="site.title"/></title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
    <h3><fmt:message key="available.title"/>:</h3>
    <ul>
        <c:forEach var="stock" items="${stocks}">
            <c:if test="${stock.quantity > 0}">
                <li>${stock.product.name}: <fmt:message key="items.left"><fmt:param value="${stock.quantity}"/></fmt:message></li>
            </c:if>
        </c:forEach>
    </ul>

    <h3><fmt:message key="shopping.title"/>:</h3>
    <form method="post" action="order">
        <c:if test="${not empty errors}">
            <div class="error"><fmt:message key="error.quantity.fix"/></div>
        </c:if>
        <ul>
            <c:forEach var="stock" items="${stocks}">
                <c:if test="${stock.quantity > 0}">
                    <li>${stock.product.name}
                        <c:set var="stockId"><c:out value="${stock.id}"/></c:set>
                        <c:set var="paramName"><%= MainController.STOCK_PREFIX %>${stock.id}</c:set>
                        <input type="number" name="${paramName}" value="${param[paramName]}"
                               min="0" max="${stock.quantity}" step="1" pattern="^[0-9]+$" size="4"
                               autocomplete="off"/>
                        <c:if test="${not empty errors and not empty errors[stockId]}">
                            <span class="error">${errors[stockId]}</span>
                        </c:if>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
        <input type="submit" value="<fmt:message key="button.buy"/>"/>
    </form>

</body>
</html>
