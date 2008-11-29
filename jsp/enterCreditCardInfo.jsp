<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>
<title>Enter Credit Card Information</title>
<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
</head>

<body>
<jsp:include page="/WEB-INF/jsp/header1.jsp">
  <jsp:param name="title2" value="Home > Payment" />
</jsp:include>

<spring:hasBindErrors name="itinerary">
  <h3>You have errors in your input!</h3>
  <font color="red"> <c:forEach items="${errors.allErrors}" var="error">
    <spring:message code="${error.code}" text="${error.defaultMessage}" />
    <br />
  </c:forEach> </font>
</spring:hasBindErrors>
<form:form commandName="itinerary" method="POST" action="enterCreditCardInfo.spring">
  <table border="1">
    <tr>
      <th align="right">Departure airport</th>
      <td><c:out value="${flight.airportByDepartureLocation.name}" /></td>
    </tr>
    <tr>
      <th align="right">Arrival airport</th>
      <td><c:out value="${flight.airportByArrivalLocation.name}" /></td>
    </tr>
    <tr>
      <th align="right">Flight number</th>
      <td><c:out value="${itinerary.flight.flightNo}" /><form:hidden path="flight.flightNo" /></td>
    </tr>
    <tr>
      <th align="right">Seat class</th>
      <td><c:out value="${itinerary.seatClass}" /> <form:hidden path="seatClass" /></td>
    </tr>
    <tr>
      <th align="right">Number of seats</th>
      <td><c:out value="${itinerary.numOfSeats}" /> <form:hidden path="numOfSeats" /></td>
    </tr>
    <tr>
      <th align="right">Price</th>
      <td>$<c:choose>
        <c:when test="${fn:startsWith(itinerary.seatClass, 'Business')}">
          <c:out value="${itinerary.flight.businessPrice * itinerary.numOfSeats}" />
        </c:when>
        <c:otherwise>
          <c:out value="${itinerary.flight.economyPrice * itinerary.numOfSeats}" />
        </c:otherwise>
      </c:choose></td>
    </tr>
    <tr>
      <th align="right">Credit card number (16 digits)</th>
      <td><form:input path="customer.ccNo" /></td>
    </tr>
    <tr>
      <th align="right">Credit card expiration date (mmyy)</th>
      <td><form:input path="customer.expiration" /></td>
    </tr>
    <tr>
      <td align="right"><input type="submit" align="center" value="Enter"></td>
    </tr>
  </table>
  <form:hidden path="customer.username" />
</form:form>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>

</html>