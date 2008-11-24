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
		
		<table border ="1">
			<thead>
				<tr>
					<th>Flight #</th>
					<th>Economy seats</th>
					<th>Business seats</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${flightList}" var="flight">
					<tr>
		      			<td><c:out value="${flight.flightNo}"/><br/></td>
			  			<td><c:out value="${flight.economySeats}"/><br/></td>
			  			<td><c:out value="${flight.businessSeats}"/><br/></td>
			  		</tr>
		  	</c:forEach>
		  	</tbody>
		</table>
		<br>
		Total # of empty Economy seats: 
		<br>
		Total # of empty Business seats:
		<br>
		<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	</body>
</html>