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
  			<jsp:param name="title2" value="Home > Register" />
		</jsp:include>
		Ticket information
		<table border="1">
  			<tr><td>Your ticket number is XX-FFF-YYYYYY-ZZZ</td></tr>
		</table>
		<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	</body>
</html>
