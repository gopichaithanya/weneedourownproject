<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head><title>Hello :: Spring Application</title></head>
  <body>
    <h1>Hibernate example with Spring framework</h1>
    <table border="1">
    <tr><td>Flight#</td><td>Departure time</td><td>Arrival time</td></tr>
	<c:forEach items="${flights}" var="flight">
      <tr><td><c:out value="${flight.flightNo}"/></td>
      	  <td><c:out value="${flight.departureTime}"/></td>
      	  <td><c:out value="${flight.arrivalTime}"/></td>
      </tr>
    </c:forEach>
    </table>
  </body>
</html>
