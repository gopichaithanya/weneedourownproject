<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
	<head>
		<title> Confirmation </title>
		<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
	</head>
	<body>
		<jsp:include page="/WEB-INF/jsp/header1.jsp">
  			<jsp:param name="title2" value="Home > Payment > Confirmation" />
		</jsp:include>
		<h3> Confirmation </h3>
		<table border="1">
  			<tr><td>Ticket #: <c:out value="${ticket}"/> </td></tr>
		</table>
		<br>
		<a href="itineraryForCustomer.spring">Back to Itinerary</a>
		<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	</body>
</html>
