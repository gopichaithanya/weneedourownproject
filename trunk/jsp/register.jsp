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
        <form:form commandName="register" method="POST" action="register.htm">  
            <table border="1">
			    <tr><td>* Name</td><td><input path="userName"></td></tr>
			    <tr><td>* Address</td><td><input path="address"></td></tr>
			    <tr><td>* Login name</td><td><form:input path="loginName"/></td></tr>
			    <tr><td>* Login password</td><td><input path="loginPassword"/></td></tr>
			    <tr><td>* E-mail address</td><td><input path="emailAddress"/></td></tr>
			    <tr><td>Credit card number (16 digits)</td><td><input path="creditCardNumber"/></td></tr>
			    <tr><td>Credit card expiration date (mmdd)</td><td><input path="creditCardExpirationDate"/></td></tr>
			  	<tr><td><input type="submit" align="center" value="Enter"></td></tr>

				</table>
     </form:form>     
</center>  
 </body>  
</html>  