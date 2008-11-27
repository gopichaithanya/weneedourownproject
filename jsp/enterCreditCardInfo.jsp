<%@ page contentType="text/html;charset=UTF-8" language="java" %>   
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>  
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>  
	<head>  
		<title>Enter Credit Card Information</title>  
		<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
	</head>  

	<body>
		<jsp:include page="/WEB-INF/jsp/header1.jsp">
  			<jsp:param name="title2" value="Home > Payment" />
		</jsp:include>
	
		<spring:hasBindErrors name="itinerary">
			<h3>You have errors in your input!</h3>
				<font color="red">
					<c:forEach items="${errors.allErrors}" var="error">
						<spring:message code="${error.code}" text="${error.defaultMessage}"/><br/>
					</c:forEach>
		</font>
		</spring:hasBindErrors>
		<form:form commandName="itinerary" method="POST" action="enterCreditCardInfo.spring">
			<table border="1">
				<tr><td>Credit card number (16 digits)</td><td><form:input path="customer.ccNo"/></td></tr>
			    <tr><td>Credit card expiration date (mmdd)</td><td><form:input path="customer.expiration"/></td></tr>
			  	<tr><td><input type="submit" align="center" value="Enter"></td></tr>
			</table>
      <form:hidden path="flight.flightNo" />
      <form:hidden path="customer.username" />
      <form:hidden path="seatClass" />
      <form:hidden path="numOfSeats" />
		</form:form>
		<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	</body>
	  
</html>