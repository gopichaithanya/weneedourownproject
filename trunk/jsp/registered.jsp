<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<body>
<h1>Customer Registered</h1>
	    
* Name<c:out value="${customer.username}"/><br>
			    * Address<c:out value="${customer.firstName}"/><br>
			    * Login name<c:out value="${customer.lastName}"/><br>
			    * Login password<c:out value="${customer.password}"/><br>
			    * E-mail address<c:out value="${customer.city}"/><br>
			    Credit card number (16 digits)<c:out value="${customer.ccNo}"/><br>
			    Credit card expiration date (mmdd)<c:out value="${customer.expiration}"/><br>

    <br>

</body>
</html>
