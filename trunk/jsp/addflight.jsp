<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>  
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<body>
<h1>Add Flight</h1>
<form:form commandName="flight" method="POST" action="addflight.spring">
<table border="1">
  <tr><td>Departure time</td><td><form:input path="departureTime"/></td></tr>
  <tr><td>Arrival time</td><td><form:input path="arrivalTime"/></td></tr>
  <tr><td>Business seats</td><td><form:input path="businessSeats"/></td></tr>
  <tr><td>Cost of business class</td><td><form:input path="businessPrice"/></td></tr>
  <tr><td>Economy seats</td><td><form:input path="economySeats"/></td></tr>
  <tr><td>Cost of economy class</td><td><form:input path="economyPrice"/></td></tr>
  
  <tr><td>Flight number (3 digits)</td><td><!--form:input path="flightNo"/--></td></tr>
  <tr><td>Airline code/name</td><td><!--form:input path="airlineCode"/--></td></tr>
  <tr><td>Departure location</td><td><!--form:input path="departureLocation"/--></td></tr>
  <tr><td>Departure day</td><td><!--form:input path="departureDate"/--></td></tr>
  <tr><td>Arrival location</td><td><!--form:input path="arrivalLocation"/--></td></tr>
  <tr><td>Arrival day</td><td><!--form:input path="arrivalDate"/--></td></tr>
  <tr><td colspan="2"><input type="submit" align="center" value="Enter"></td></tr>
</table>
 </form:form>   
</body>
</html>
