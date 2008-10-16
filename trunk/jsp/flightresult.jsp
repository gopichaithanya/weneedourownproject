<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>  
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<body>
<h1>Add Flight</h1>
<c:forEach items="${model.flights}" var="flight">
      <c:out value="${flight.flightNumber}"/><br>
	  <c:out value="${flight.scheduleID}"/><br>
	  <c:out value="${flight.airlineCode}"/><br>
	  <c:out value="${flight.economySeats}"/><br>
	  <c:out value="${flight.economyPrice}"/><br>
	  <c:out value="${flight.businessSeats}"/> <br>
	  <c:out value="${flight.businessPrice}"/> <br> 
</c:forEach>
    <br>

</body>
</html>
