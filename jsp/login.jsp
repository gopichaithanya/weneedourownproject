<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>
<title>Login</title>
<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
</head>
<body onLoad="document.getElementById('username').focus();">
<jsp:include page="/WEB-INF/jsp/header1.jsp">
  <jsp:param name="title2" value="Home > Login" />
</jsp:include>

<h3>Login</h3>
<spring:hasBindErrors name="customer">
  <h3>You have errors in your input!</h3>
  <font color="red"> <c:forEach items="${errors.allErrors}" var="error">
    <spring:message code="${error.code}" text="${error.defaultMessage}" />
    <br />
  </c:forEach> </font>
</spring:hasBindErrors>

<!-- note second way of displaying error messages – by field -->
<form:form commandName="customer" method="POST" action="login.spring">
  <table border="1">
    <tr>
      <td>* Name</td>
      <td><form:input path="username" /></td>
      <td>jjohnson</td>
    </tr>
    <tr>
      <td>* Login password</td>
      <td><form:password path="password" /></td>
      <td>harley</td>
    </tr>
    <tr>
      <td><input type="submit" align="center" value="Enter"> <a href="register.spring">Register</a></td>
    </tr>

  </table>
</form:form>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>
