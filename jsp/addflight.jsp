<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@page import="java.util.List"%>
<html>
<head>Adding flight</head>
<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
<script type="text/javascript" src="js/proj4398.js"></script>
<body>
<jsp:include page="/WEB-INF/jsp/header2.jsp">
  <jsp:param name="title2" value="Flight Search" />
</jsp:include>

<form:form commandName="flight" method="POST" name="flight">

<table border="1">
  <tr><td>Departure time</td><td><form:input path="departureTime"/></td></tr>
  <tr><td>Arrival time</td><td><form:input path="arrivalTime"/></td></tr>
  <tr><td>Business seats</td><td><form:input path="businessSeats"/></td></tr>
  <tr><td>Cost of business class</td><td><form:input path="businessPrice"/></td></tr>
  <tr><td>Economy seats</td><td><form:input path="economySeats"/></td></tr>
  <tr><td>Cost of economy class</td><td><form:input path="economyPrice"/></td></tr>
  
  <tr><td>Flight number (3 digits)</td><td><form:input path="flightNo"/></td></tr>
  <tr><td>Airline code/name</td>
  		<td>
  		<form:select path="airline">
        <c:forEach items="${airlines}" var="airline">
          <form:option value="${airline[0]}" label="${airline[0]}: ${airline[1]}" />
        </c:forEach>
      </form:select>
      </td></tr>
  <tr><td>Departure location</td>
  	<td>
		<form:select path="airportByArrivalLocation">
	        <c:forEach items="${airports}" var="airport">
	          <form:option value="${airport[0]}" label="${airport[0]}: ${airport[1]}" />
	        </c:forEach>
      	</form:select>
  	</td>
  </tr>
  <tr><td>Departure day</td>
  <td><form:input path="departureTime"/></td></tr>
  <tr><td>Arrival location</td>
  	<td>
  		<form:select path="airportByDepartureLocation">
	        <c:forEach items="${airports}" var="airport">
	          <form:option value="${airport[0]}" label="${airport[0]}: ${airport[1]}" />
	        </c:forEach>
      	</form:select>
  	</td>
  </tr>
  <tr><td>Arrival day</td>
  <td><form:input path="arrivalTime"/></td></tr>
  <tr><td colspan="2"><input type="submit" align="center" value="Enter"></td></tr>
</table>
 </form:form>   
</body>
</html>
