<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Flight Searching</title>
</head>
<body>
<form:form method="post" commandName="flightSearchForCustomerCMD">
  <table border="1">
    <caption>Search Flight</caption>

    <tr>
      <th>Leaving from</th>
      <td><form:select path="departLocation">
        <c:forEach items="${airports}" var="airport">
          <form:option value="${airport[0]}" label="${airport[0]}: ${airport[1]}" />
        </c:forEach>
      </form:select></td>
    </tr>
    <tr>
      <th>Going to</th>
      <td><form:select path="arrivalLocation">
        <c:forEach items="${airports}" var="airport">
          <form:option value="${airport[0]}" label="${airport[0]}: ${airport[1]}" />
        </c:forEach>
      </form:select></td>
    </tr>

    <tr>
      <th>Departing Date</th>
      <td><form:select path="departMonth">
        <form:option value="1" label="Jan" />
        <form:option value="2" label="Feb" />
        <form:option value="3" label="Mar" />
        <form:option value="4" label="April" />
        <form:option value="5" label="May" />
        <form:option value="6" label="June" />
        <form:option value="7" label="July" />
        <form:option value="8" label="Aug" />
        <form:option value="9" label="Sep" />
        <form:option value="10" label="Oct" />
        <form:option value="11" label="Nov" />
        <form:option value="12" label="Dec" />
      </form:select><form:select path="departDay">
        <c:forEach var="day" begin="1" end="31" step="1">
          <form:option value="${day}" />
        </c:forEach>
      </form:select><form:select path="departYear">
        <c:forEach var="year" begin="2008" end="2011" step="1">
          <form:option value="${year}" />
        </c:forEach>
      </form:select></td>
    </tr>
    <tr>
      <th>Departing Time</th>
      <td><form:select path="departHour">
        <c:forEach var="hour" begin="0" end="11" step="1">
          <form:option value="${hour}" label="${((hour + 11) % 12) +1} AM" />
        </c:forEach>
        <c:forEach var="hour" begin="12" end="23" step="1">
          <form:option value="${hour}" label="${((hour - 1) % 12) +1} PM" />
        </c:forEach>
      </form:select><!-- : <form:select path="departMin">
            <c:forEach var="min" begin="00" end="60" step="10">
              <form:option value="${min}" />
            </c:forEach>
          </form:select>--></td>
    </tr>

    <tr>
      <th>Trip type</th>
      <td>
      <table border="1">
        <tr>
          <td><form:radiobutton path="tripType" value="roundTrip" id="roundTrip" /><label
            for="roundTrip">Round trip</label></td>
          <td><form:radiobutton path="tripType" value="oneWayTrip" id="oneWayTrip" /><label
            for="oneWayTrip">One way trip</label></td>
        </tr>
      </table>
      </td>
    </tr>

    <tr>
      <th>Returning Date</th>
      <td><form:select path="arrivalMonth">
        <form:option value="1" label="Jan" />
        <form:option value="2" label="Feb" />
        <form:option value="3" label="Mar" />
        <form:option value="4" label="April" />
        <form:option value="5" label="May" />
        <form:option value="6" label="June" />
        <form:option value="7" label="July" />
        <form:option value="8" label="Aug" />
        <form:option value="9" label="Sep" />
        <form:option value="10" label="Oct" />
        <form:option value="11" label="Nov" />
        <form:option value="12" label="Dec" />
      </form:select><form:select path="arrivalDay">
        <c:forEach var="day" begin="1" end="31" step="1">
          <form:option value="${day}" />
        </c:forEach>
      </form:select><form:select path="arrivalYear">
        <c:forEach var="year" begin="2008" end="2011" step="1">
          <form:option value="${year}" />
        </c:forEach>
      </form:select></td>
    </tr>
    <tr>
      <th>Returning Time</th>
      <td><form:select path="arrivalHour">
        <c:forEach var="hour" begin="0" end="11" step="1">
          <form:option value="${hour}" label="${((hour + 11) % 12) +1} AM" />
        </c:forEach>
        <c:forEach var="hour" begin="12" end="23" step="1">
          <form:option value="${hour}" label="${((hour - 1) % 12) +1} PM" />
        </c:forEach>
      </form:select><!-- : <form:select path="arrivalMin">
            <c:forEach var="min" begin="00" end="60" step="10">
              <form:option value="${min}" />
            </c:forEach>
          </form:select>--></td>
    </tr>

    <tr>
      <th>The number of passengers</th>
      <td>
      <table border="1">
        <tr>
          <c:forEach var="nPerson" begin="1" end="10" step="1">
            <td><form:radiobutton path="numPassengers" value="${nPerson}"
              id="passenger${nPerson}" /> <label for="passenger${nPerson}"><c:out
              value="${nPerson}" /></label></td>
          </c:forEach>
        </tr>
      </table>
      </td>
    </tr>
  </table>

  <input type="submit" value="Search">
</form:form>

<c:if test="${searchedFlights != null}">
  <table border="1">
    <thead>
      <tr>
        <th><a href="http://www.tvlon.com/resources/airlinecodes.htm">Airline code/name</a></th>
        <th>Flight number (3 digits)</th>
        <th><a href="http://www.orbitz.com/App/global/airportCodes.jsp">Departure location</a></th>
        <th>Departure day of the week/time</th>
        <th><a href="http://www.orbitz.com/App/global/airportCodes.jsp">Arrival location</a></th>
        <th>Arrival day of the week/time</th>
        <th>cost of business class</th>
        <th>cost of economy class</th>
        <th>Seats for business class</th>
        <th>Seats for economy class</th>
        <th><b>Reserve</b></th>
      </tr>
    </thead>

    <tbody>
      <c:forEach items="${searchedFlights}" var="flight">
        <tr>
          <td><c:out value="${flight.airline_code}" /></td>
          <td><c:out value="${flight.flightNo}" /></td>
          <td><a href="http://maps.google.com"><c:out
            value="${flight.airportByDepartureLocation_code}" /></a></td>
          <td><c:out value="${flight.departureTime}" /></td>
          <td><a href="http://maps.google.com"><c:out
            value="${flight.airportByArrivalLocation_code}" /></a></td>
          <td><c:out value="${flight.arrivalTime}" /></td>
          <td><c:out value="${flight.businessPrice}" /></td>
          <td><c:out value="${flight.economyPrice}" /></td>
          <td><c:out value="${flight.businessSeats}" /></td>
          <td><c:out value="${flight.economySeats}" /></td>
          <td><a href="">Reserve</a></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</c:if>
</body>
</html>