<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Flight Searching</title>
<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
</head>
<body>
<form:form method="post" commandName="flightSearchForCustomerCMD">
  <table border="1">
    <caption>Search Flight (1 / 3 steps)</caption>

    <tr>
      <th>Leaving from</th>
      <td><form:select path="departLocation">
        <c:forEach items="${airports}" var="airport">
          <form:option value="${airport[0]}" label="${airport[0]}: ${airport[1]}" />
        </c:forEach>
      </form:select> <input type="button" value="Search Airport" /> <form:errors path="departLocation"
        cssClass="error" /></td>
    </tr>
    <tr>
      <th>Going to</th>
      <td><form:select path="arrivalLocation">
        <c:forEach items="${airports}" var="airport">
          <form:option value="${airport[0]}" label="${airport[0]}: ${airport[1]}" />
        </c:forEach>
      </form:select> <input type="button" value="Search Airport" /> <form:errors path="arrivalLocation"
        cssClass="error" /></td>
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
      </form:select> / <form:select path="departDay">
        <c:forEach var="day" begin="1" end="31" step="1">
          <form:option value="${day}" />
        </c:forEach>
      </form:select> / <form:select path="departYear">
        <c:forEach var="year" begin="2008" end="2011" step="1">
          <form:option value="${year}" />
        </c:forEach>
      </form:select> <input type="button" value="Calendar" /> <form:errors path="departMonth" cssClass="error" />
      <form:errors path="departDay" cssClass="error" /> <form:errors path="departYear"
        cssClass="error" /></td>
    </tr>
    <tr>
      <th>Departing Time</th>
      <td><form:select path="departHour">
        <form:option value="anytime" label="Anytime" />
        <c:forEach var="hour" begin="0" end="11" step="1">
          <form:option value="${hour}" label="${((hour + 11) % 12) +1} AM" />
        </c:forEach>
        <c:forEach var="hour" begin="12" end="23" step="1">
          <form:option value="${hour}" label="${((hour - 1) % 12) +1} PM" />
        </c:forEach>
      </form:select></td>
    </tr>

    <tr>
      <th>Trip type</th>
      <td>
      <table border="1">
        <tr>
          <td><form:radiobutton path="tripType" value="roundTrip" id="roundTrip" /><label
            for="roundTrip">Round trip</label></td>
          <td><form:radiobutton path="tripType" value="oneWayTrip" id="oneWayTrip" /><label
            for="oneWayTrip">One way trip</label> <form:errors path="tripType" cssClass="error" />
          </td>
        </tr>
      </table>
      </td>
    </tr>

    <tr>
      <th>Returning Date</th>
      <td><form:select path="returnMonth">
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
      </form:select> / <form:select path="returnDay">
        <c:forEach var="day" begin="1" end="31" step="1">
          <form:option value="${day}" />
        </c:forEach>
      </form:select> / <form:select path="returnYear">
        <c:forEach var="year" begin="2008" end="2011" step="1">
          <form:option value="${year}" />
        </c:forEach>
      </form:select> <input type="button" value="Calendar" /> <form:errors path="returnMonth" cssClass="error" />
      <form:errors path="returnDay" cssClass="error" /> <form:errors path="returnYear"
        cssClass="error" /></td>
    </tr>
    <tr>
      <th>Returning Time</th>
      <td><form:select path="returnHour">
        <form:option value="anytime" label="Anytime" />
        <c:forEach var="hour" begin="0" end="11" step="1">
          <form:option value="${hour}" label="${((hour + 11) % 12) +1} AM" />
        </c:forEach>
        <c:forEach var="hour" begin="12" end="23" step="1">
          <form:option value="${hour}" label="${((hour - 1) % 12) +1} PM" />
        </c:forEach>
      </form:select></td>
    </tr>

    <tr>
      <th>The number of passengers</th>
      <td>
      <table border="1">
        <tr>
          <c:forEach var="nPerson" begin="1" end="8" step="1">
            <td><form:radiobutton path="numPassengers" value="${nPerson}"
              id="passenger${nPerson}" /> <label for="passenger${nPerson}"><c:out
              value="${nPerson}" /></label></td>
          </c:forEach>
          <td>
          <div id="passengerMore"></div>
          <input type="button" value="More" /> <form:errors path="numPassengers" cssClass="error" />
          </td>
        </tr>
      </table>
      </td>
    </tr>

    <tr>
      <td colspan="2" align="right"><input type="reset" /> <input type="submit"
        value="====Search Flight====" /></td>
    </tr>
  </table>

</form:form>
<c:if test="${searchedFlights != null}">
  <p>
  <table border="1">
    <caption>Choose a departing flight (2 / 3 steps)</caption>
    <thead>
      <tr>
        <th>Departure location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
        and time</th>
        <th>Arrival location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
        and time</th>
        <th>cost of economy class (business class)</th>
        <th><a href="http://www.tvlon.com/resources/airlinecodes.htm">Airline name</a></th>
        <th><b>Reserve</b></th>
      </tr>
    </thead>

    <tbody>
      <c:forEach items="${searchedFlights}" var="flight">
        <tr>
          <td><a href="http://maps.google.com"><c:out
            value="${flight.airportByDepartureLocation_name}" /> (<c:out
            value="${flight.airportByDepartureLocation_code}" />)</a><br />
          <c:out value="${flight.departureTime}" /></td>
          <td><a href="http://maps.google.com"><c:out
            value="${flight.airportByArrivalLocation_name}" /> (<c:out
            value="${flight.airportByArrivalLocation_code}" />)</a><br />
          <c:out value="${flight.arrivalTime}" /> (<c:out value="${flight.durationHours}" />
          hours)</td>
          <td>$<c:out value="${flight.economyPrice}" /> ($<c:out
            value="${flight.businessPrice}" />)</td>
          <td>
          <table border="0">
            <tr>
              <td><img
                src="http://www.expedia.com/pubspec/images/airlines/sm${flight.airline_code}.gif"
                alt="${flight.airline_code}" /></td>
              <td><c:out value="${flight.airline_name}" /></td>
            </tr>
          </table>
          </td>
          <td><a href="">Reserve</a></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  </p>
</c:if>
</body>
</html>