<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>
<title>Confirmation</title>
<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header1.jsp">
  <jsp:param name="title2" value="Home > Payment > Confirmation" />
</jsp:include>
<h3>Confirmation</h3>
<table border="1">
  <tr>
    <th align="right">Departure airport</th>
    <td><c:out value="${itinerary.flight.airportByDepartureLocation.name}" /></td>
  </tr>
  <tr>
    <th align="right">Arrival airport</th>
    <td><c:out value="${itinerary.flight.airportByArrivalLocation.name}" /></td>
  </tr>
  <tr>
    <th align="right">Flight number</th>
    <td>#<c:out value="${itinerary.flight.flightNoFormatted}" /></td>
  </tr>
  <tr>
    <th align="right">Seat class</th>
    <td><c:out value="${itinerary.seatClass}" /></td>
  </tr>
  <tr>
    <th align="right">Number of seats</th>
    <td><c:out value="${itinerary.numOfSeats}" /></td>
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
    <th align="right"><b>Ticket #:</b></th>
    <td><c:out value="${itinerary.ticketNo}" /></td>
  </tr>
</table>
<br>
<a href="itineraryForCustomer.spring">Back to Itinerary</a>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>
