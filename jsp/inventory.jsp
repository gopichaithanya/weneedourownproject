  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>		
		<title> Inventory Report </title>
		<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
	</head>
	<body>
		<jsp:include page="/WEB-INF/jsp/header1.jsp">
  			<jsp:param name="title2" value="Home > Inventory" />
		</jsp:include>
		
		<table border ="1" width="585">
			<thead>
				<tr>
					<th width="80">Airline
						<div>Flight #</div>
					</th>
					<th width="100">Date</th>
					<th width="130">Departure /
						<div>Arrival</div>
					</th>
					<th width="100">Economy seats</th>
					<th width="100">Business seats</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${flightList}" var="flight">
                  <c:choose>
                  <c:when test="${!empty newFlight && flight.flightNo == newFlight.flightNo}">
					<tr align="center" class="newFlight">
                  </c:when>
                  <c:otherwise>
                    <tr align="center">
                  </c:otherwise>
                  </c:choose>
		      			<td width="80"><c:out value="${flight.airline.code}"/>
		      				<div><c:out value="${flight.flightNo}"/></div>
		      			<br/></td>
		      			<td width="100"><c:out value="${flight.departureTime}"/><br/></td>
		      			<td width="130"><c:out value="${flight.airportByDepartureLocation.city}"/>
		      				(<c:out value="${flight.airportByDepartureLocation.code}"/>)
		      				<div><c:out value="${flight.airportByArrivalLocation.city}"/>
		      					(<c:out value="${flight.airportByArrivalLocation.code}" />)
		      				</div>
		      			<br/></td>
			  			<td width="100"><c:out value="${flight.economySeats}"/><br/></td>
			  			<td width="100"><c:out value="${flight.businessSeats}"/><br/></td>
			  		</tr>
		  		</c:forEach>
		  		<br><br>
		  		<tr align="center">
					<td width="80"></td>
		      		<td width="100"></td>
		      		<td width="130"><b>Totals:</b></td>
			  		<td width="100"><b><c:out value="${emptyEconomySeats}"/></b><br/></td>
			  		<td width="100"><b><c:out value="${emptyBusinessSeats}"/></b><br/></td>
			  	</tr>
		  		
		  	</tbody>
		</table>
		<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	</body>
</html>