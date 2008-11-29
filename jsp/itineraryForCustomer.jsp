<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Itinerary for customer</title>
<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
</head>
<script language="JavaScript">
function cancelReserved(flightNo) {
  location.href="cancelReservedFlightForCustomer.spring?flightNo=" + flightNo;
}

function book(flightNo) {
	  location.href="enterCreditCardInfo.spring?flightNo=" + flightNo;
}
</script>
<body>
<jsp:include page="/WEB-INF/jsp/header1.jsp">
  <jsp:param name="title2" value="Itinerary" />
</jsp:include>

<p>
<table border="1" class="itineraryForCustomerReserved">
  <caption class="itineraryForCustomerReservedTitle">Reserved</caption>
  <c:choose>
    <c:when test="${empty reservedItinerary}">
      <tbody>
        <tr>
          <td class="noFlight">There is no reserved flight.</td>
        </tr>
      </tbody>
    </c:when>
    <c:otherwise>
      <thead>
        <tr>
          <th>Departure location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
          and time</th>
          <th>Arrival location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
          and time</th>
          <th><a href="http://www.tvlon.com/resources/airlinecodes.htm">Airline name</a><br />
          and Cost</th>
          <th><b>Cancel</b></th>
          <th><b>Book</b></th>
        </tr>
      </thead>

      <tbody>
        <c:forEach items="${reservedItinerary}" var="iti">
          <tr>
            <td rowspan="2"><c:out value="${iti.flight.airportByDepartureLocation.name}" /> (<c:out
              value="${iti.flight.airportByDepartureLocation.code}" />)<br />

            <c:out value="${iti.flight.departureTime}" /></td>

            <td rowspan="2"><c:out value="${iti.flight.airportByArrivalLocation.name}" /> (<c:out
              value="${iti.flight.airportByArrivalLocation.code}" />)<br />
            <c:out value="${iti.flight.arrivalTime}" />
            <div>(<c:out value="${iti.flight.durationHours}" /> hours)</div>
            </td>

            <td rowspan="2">
            <table border="0">
              <tr>
                <td><img
                  src="http://www.expedia.com/pubspec/images/airlines/sm${iti.flight.airline.code}.gif"
                  alt="${iti.flight.airline.code}" /></td>
                <td><c:out value="${iti.flight.airline.name}" /></td>
              </tr>
            </table>
            <div>for <c:out value="${iti.numOfSeats}" /> tickets<br />
            <c:if test="${!(empty iti.seatClass)}">$<c:choose>
                <c:when test="${fn:startsWith(iti.seatClass, 'Business')}">
                  <c:out value="${iti.flight.businessPrice * iti.numOfSeats}" />
                </c:when>
                <c:otherwise>
                  <c:out value="${iti.flight.economyPrice * iti.numOfSeats}" />
                </c:otherwise>
              </c:choose> (<c:out value="${iti.seatClass}" />)</c:if></div>
            </td>

            <td><input type="button" value="Cancel"
              onClick="cancelReserved(<c:out value="${iti.flight.flightNo}"/>);" /></td>

            <td><input type="button" value="Book"
              onClick="book(<c:out value="${iti.flight.flightNo}"/>);" /></td>
          </tr>

          <tr>
            <td colspan="2">Reserved time: <c:out value="${iti.reservedTime}" /></td>
          </tr>
        </c:forEach>
      </tbody>
    </c:otherwise>
  </c:choose>
</table>

** When a flight is canceled, the all itineraries that are reserved and booked will become canceled
as well.
<BR>

** The reservation can be canceled by reservation manage, or payment not received after 2 minutes.
<BR>
</p>

<p>
<table border="1" class="itineraryForCustomerBooked">
  <caption class="itineraryForCustomerBookedTitle">Booked</caption>
  <c:choose>
    <c:when test="${empty bookedItinerary}">
      <tbody>
        <tr>
          <td class="noFlight">There is no booked flight.</td>
        </tr>
      </tbody>
    </c:when>
    <c:otherwise>
      <thead>
        <tr>
          <th>Departure location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
          and time</th>
          <th>Arrival location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
          and time</th>
          <th><a href="http://www.tvlon.com/resources/airlinecodes.htm">Airline name</a></th>
          <th>Ticket number</th>
        </tr>
      </thead>

      <tbody>
        <c:forEach items="${bookedItinerary}" var="iti">

          <tr>
            <td><c:out value="${iti.flight.airportByDepartureLocation.name}" /> (<c:out
              value="${iti.flight.airportByDepartureLocation.code}" />)<br />

            <c:out value="${iti.flight.departureTime}" /></td>
            <td><c:out value="${iti.flight.airportByArrivalLocation.name}" /> (<c:out
              value="${iti.flight.airportByArrivalLocation.code}" />)<br />
            <c:out value="${iti.flight.arrivalTime}" />
            <div>(<c:out value="${iti.flight.durationHours}" /> hours)</div>
            </td>
            <td>
            <table border="0">
              <tr>
                <td><img
                  src="http://www.expedia.com/pubspec/images/airlines/sm${iti.flight.airline.code}.gif"
                  alt="${iti.flight.airline.code}" /></td>
                <td><c:out value="${iti.flight.airline.name}" /></td>
              </tr>
            </table>
            </td>
            <td><c:out value="${iti.ticketNo}" /></td>
          </tr>
        </c:forEach>
      </tbody>
    </c:otherwise>
  </c:choose>
</table>
</p>

<p>
<table border="1" class="itineraryForCustomerCanceled">
  <caption class="itineraryForCustomerCanceledTitle">Canceled</caption>
  <c:choose>
    <c:when test="${empty canceledItinerary}">
      <tbody>
        <tr>
          <td class="noFlight">There is no canceled flight.</td>
        </tr>
      </tbody>
    </c:when>
    <c:otherwise>
      <thead>
        <tr>
          <th>Departure location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
          and time</th>
          <th>Arrival location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
          and time</th>
          <th><a href="http://www.tvlon.com/resources/airlinecodes.htm">Airline name</a></th>
        </tr>
      </thead>

      <tbody>
        <c:forEach items="${canceledItinerary}" var="iti">

          <tr>
            <td><c:out value="${iti.flight.airportByDepartureLocation.name}" /> (<c:out
              value="${iti.flight.airportByDepartureLocation.code}" />)<br />

            <c:out value="${iti.flight.departureTime}" /></td>
            <td><c:out value="${iti.flight.airportByArrivalLocation.name}" /> (<c:out
              value="${iti.flight.airportByArrivalLocation.code}" />)<br />
            <c:out value="${iti.flight.arrivalTime}" />
            <div>(<c:out value="${iti.flight.durationHours}" /> hours)</div>
            </td>
            <td>
            <table border="0">
              <tr>
                <td><img
                  src="http://www.expedia.com/pubspec/images/airlines/sm${iti.flight.airline.code}.gif"
                  alt="${iti.flight.airline.code}" /></td>
                <td><c:out value="${iti.flight.airline.name}" /></td>
              </tr>
            </table>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </c:otherwise>
  </c:choose>
</table>
</p>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>

</body>
</html>
