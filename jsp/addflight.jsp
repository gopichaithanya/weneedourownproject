<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>  
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<body>
<h1>Add Flight</h1>
<form:form action="addFlightResult.html">
<table border="1">
  <tr><td>Flight number (3 digits)</td><td><form:input path="flightNumber"></td></tr>
  <tr><td>Airline code/name</td><td><form:input path="airlineCode"></td></tr>
  <tr><td>Departure location</td><td><form:input path="departureLocation"></td></tr>
  <tr><td>Departure day</td><td><form:input path="departureDate"></td></tr>
  <tr><td>Departure time</td><td><form:input path="departureTime"></td></tr>
  <tr><td>Arrival location</td><td><form:input path="arrivalLocation"></td></tr>
  <tr><td>Arrival day</td><td><form:input path="arrivalDate"></td></tr>
  <tr><td>Arrival time</td><td><form:input path="arrivalTime"></td></tr>
  <tr><td>Cost of business class</td><td><form:input path="costOfBusiness"></td></tr>
  <tr><td>Cost of economy class</td><td><form:input path="costOfEconomy"></td></tr>
</table>
<form:input type="submit" value="Add flight">
</form>
</body>
</html>
