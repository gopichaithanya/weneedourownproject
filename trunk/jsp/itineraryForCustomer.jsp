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
<body>
<c:out value="${aa}" />
<jsp:include page="/WEB-INF/jsp/header1.jsp">
  <jsp:param name="title2" value="Itinerary" />
</jsp:include>

<p>
<table border="1" class="itineraryForCustomerReserved">
  <caption class="itineraryForCustomerReservedTitle">Reserved</caption>
  <c:choose>
    <c:when test="${empty reservedFlights}">
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
          <th>cost of economy class (business class)</th>
          <th><a href="http://www.tvlon.com/resources/airlinecodes.htm">Airline name</a></th>
          <th><b>Cancel</b></th>
          <th><b>Book</b></th>
        </tr>
      </thead>

      <tbody>
        <c:forEach items="${reservedFlights}" var="flight">
          <tr>
            <td><a href="http://maps.google.com"><c:out
              value="${flight.airportByDepartureLocation.name}" /> (<c:out
              value="${flight.airportByDepartureLocation.code}" />)</a><br />

            <c:out value="${flight.departureTime}" /></td>
            <td><a href="http://maps.google.com"><c:out
              value="${flight.airportByArrivalLocation.name}" /> (<c:out
              value="${flight.airportByArrivalLocation.code}" />)</a><br />
            <c:out value="${flight.arrivalTime}" />
            <div>(<c:out value="${flight.durationHours}" /> hours)</div>
            </td>
            <td>
            <div>$<c:out value="${flight.economyPrice}" /></div>
            <div>($<c:out value="${flight.businessPrice}" />)</div>
            </td>
            <td>
            <table border="0">
              <tr>
                <td><img
                  src="http://www.expedia.com/pubspec/images/airlines/sm${flight.airline.code}.gif"
                  alt="${flight.airline.code}" /></td>
                <td><c:out value="${flight.airline.name}" /></td>
              </tr>
            </table>
            </td>
            <td><input type="button" value="Cancel"
              onClick="cancelReserve(<c:out value="${flight.flightNo}"/>);" /></td>
            <td><input type="button" value="Book"
              onClick="book(<c:out value="${flight.flightNo}"/>);" /></td>
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
    <c:when test="${empty bookedFlights}">
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
        <c:forEach items="${bookedFlights}" var="flight">

          <tr>
            <td><a href="http://maps.google.com"><c:out
              value="${flight.airportByDepartureLocation.name}" /> (<c:out
              value="${flight.airportByDepartureLocation.code}" />)</a><br />

            <c:out value="${flight.departureTime}" /></td>
            <td><a href="http://maps.google.com"><c:out
              value="${flight.airportByArrivalLocation.name}" /> (<c:out
              value="${flight.airportByArrivalLocation.code}" />)</a><br />
            <c:out value="${flight.arrivalTime}" />
            <div>(<c:out value="${flight.durationHours}" /> hours)</div>
            </td>
            <td>
            <table border="0">
              <tr>
                <td><img
                  src="http://www.expedia.com/pubspec/images/airlines/sm${flight.airline.code}.gif"
                  alt="${flight.airline.code}" /></td>
                <td><c:out value="${flight.airline.name}" /></td>
              </tr>
            </table>
            </td>
            <td>XX-FF-YYYYYY-ZZZ</td>
          </tr>
        </c:forEach>
      </tbody>
    </c:otherwise>
  </c:choose>
</table>

** XX is the ariline code,
<br>
** FF is the 3-digit flight number,
<br>
** YYYYYY is the traveler's login name, and
<br>
** ZZZ is a 3-digit sequence number generated by the system, which is unique for each traveler.
<br>
</p>

<p>
<table border="1" class="itineraryForCustomerCanceled">
  <caption class="itineraryForCustomerCanceledTitle">Canceled</caption>
  <c:choose>
    <c:when test="${empty canceledFlights}">
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
        <c:forEach items="${canceledFlights}" var="flight">

          <tr>
            <td><a href="http://maps.google.com"><c:out
              value="${flight.airportByDepartureLocation.name}" /> (<c:out
              value="${flight.airportByDepartureLocation.code}" />)</a><br />

            <c:out value="${flight.departureTime}" /></td>
            <td><a href="http://maps.google.com"><c:out
              value="${flight.airportByArrivalLocation.name}" /> (<c:out
              value="${flight.airportByArrivalLocation.code}" />)</a><br />
            <c:out value="${flight.arrivalTime}" />
            <div>(<c:out value="${flight.durationHours}" /> hours)</div>
            </td>
            <td>
            <table border="0">
              <tr>
                <td><img
                  src="http://www.expedia.com/pubspec/images/airlines/sm${flight.airline.code}.gif"
                  alt="${flight.airline.code}" /></td>
                <td><c:out value="${flight.airline.name}" /></td>
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
