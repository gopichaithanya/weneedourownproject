<%@ page contentType="text/html;charset=UTF-8" language="java" %>   
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>  
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>  
	<head>  
		<title>Enter Credit Card Information</title>  
	</head>  

	<body>  
		<form:form commandName="customer" method="POST" action="enterCreditCardInfo.spring">
			<table border="1">
				<tr><td>Credit card number (16 digits)</td><td><form:input path="ccNo"/></td></tr>
			    <tr><td>Credit card expiration date (mmdd)</td><td><form:input path="expiration"/></td></tr>
			  	<tr><td><input type="submit" align="center" value="Enter"></td></tr>
			</table>
		</form:form>
	</body>
	  
</html>