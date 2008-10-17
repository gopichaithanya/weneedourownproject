<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>  
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>  
<head>  
<title>Register</title>  
</head>  
<body>  
<center>  
    
<h3>Register</h3>  
 
 
         <!-- note second way of displaying error messages – by field -->  
        <form:form commandName="customer" method="POST" action="register.spring">  
            <table border="1">
			    <tr><td>* Name</td><td><input path="username"></td></tr>
			    <tr><td>* First name</td><td><form:input path="firstName"/></td></tr>
				<tr><td>* Last name</td><td><form:input path="lastName"/></td></tr>
			    <tr><td>* Login password</td><td><input path="password"/></td></tr>
			    <tr><td>* Street</td><td><input path="street"/></td></tr>
				<tr><td>* City</td><td><form:input path="city"/></td></tr>
			    <tr><td>* State</td><td><input path="state"/></td></tr>
			    <tr><td>* Zip Code</td><td><input path="zip"/></td></tr>
			    <tr><td>Credit card number (16 digits)</td><td><input path="ccNo"/></td></tr>
			    <tr><td>Credit card expiration date (mmdd)</td><td><input path="expiration"/></td></tr>
			  	<tr><td><input type="submit" align="center" value="Enter"></td></tr>

				</table>
     </form:form>     
</center>  
 </body>  
</html>  