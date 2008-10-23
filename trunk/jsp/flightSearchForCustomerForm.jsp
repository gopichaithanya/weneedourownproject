<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Flight Searching</title>
</head>
<body>
<form:form method="post" commandName="flightSearchForCustomerCMD">
  <table border="1">
    <caption>Search Flight</caption>

    <tr>
      <td rowspan="3">Departure</td>
      <td>Date</td>
      <td><form:select path="departMonth">
        <form:option value="1" label="Jan" />
        <form:option value="2" label="Feb" />
        <form:option value="3" label="Mar" />
        <form:option value="4" label="April" />
        <form:option value="5" label="May" />
        <form:option value="6" label="June" />
        <form:option value="7" label="July" />
        <form:option value="8" label="Aug" />
        <form:option value="9" label="Sep" />
        <form:option value="10" label="Oct" />
        <form:option value="11" label="Nov" />
        <form:option value="12" label="Dec" />
      </form:select><form:select path="departDay">
        <c:forEach var="day" begin="1" end="31" step="1">
          <form:option value="${day}" />
        </c:forEach>
      </form:select><form:select path="departYear">
        <c:forEach var="year" begin="2008" end="2011" step="1">
          <form:option value="${year}" />
        </c:forEach>
      </form:select></td>
    </tr>
    <tr>
      <td>Time</td>
      <td><form:select path="departHour">
        <c:forEach var="hour" begin="1" end="24" step="1">
          <form:option value="${hour}" />
        </c:forEach>
      </form:select> : <form:select path="departMin">
        <c:forEach var="min" begin="00" end="60" step="10">
          <form:option value="${min}" />
        </c:forEach>
      </form:select></td>
    </tr>
    <tr>
      <td>Location</td>
      <td><form:select path="departLocation">
        <c:forEach items="${airports}" var="airport">
          <form:option value="${airport[0]}" label="${airport[1]}" />
        </c:forEach>
      </form:select></td>
    </tr>

    <tr>
      <td rowspan="3">Arrival</td>
      <td>Date</td>
      <td><form:select path="arrivalMonth">
        <form:option value="1" label="Jan" />
        <form:option value="2" label="Feb" />
        <form:option value="3" label="Mar" />
        <form:option value="4" label="April" />
        <form:option value="5" label="May" />
        <form:option value="6" label="June" />
        <form:option value="7" label="July" />
        <form:option value="8" label="Aug" />
        <form:option value="9" label="Sep" />
        <form:option value="10" label="Oct" />
        <form:option value="11" label="Nov" />
        <form:option value="12" label="Dec" />
      </form:select><form:select path="arrivalDay">
        <c:forEach var="day" begin="1" end="31" step="1">
          <form:option value="${day}" />
        </c:forEach>
      </form:select><form:select path="arrivalYear">
        <c:forEach var="year" begin="2008" end="2011" step="1">
          <form:option value="${year}" />
        </c:forEach>
      </form:select></td>
    </tr>
    <tr>
      <td>Time</td>
      <td><form:select path="arrivalHour">
        <c:forEach var="hour" begin="1" end="24" step="1">
          <form:option value="${hour}" />
        </c:forEach>
      </form:select> : <form:select path="arrivalMin">
        <c:forEach var="min" begin="00" end="60" step="10">
          <form:option value="${min}" />
        </c:forEach>
      </form:select></td>
    </tr>
    <tr>
      <td>Location</td>
      <td><form:select path="arrivalLocation">
        <c:forEach items="${airports}" var="airport">
          <form:option value="${airport[0]}" label="${airport[1]}" />
        </c:forEach>
      </form:select></td>
    </tr>
    <tr>
      <td>The number of passengers</td>
      <td colspan="2"><input type="Radio" name="nPassengers" value="1" checked>1<br>
      <input type="Radio" name="nPassengers" value="2">2<br>
      <input type="Radio" name="nPassengers" value="3">3<br>
      <input type="Radio" name="nPassengers" value="4">4<br>
      <input type="Radio" name="nPassengers" value="5">5<br>
      <input type="Radio" name="nPassengers" value="6">6<br>
      <input type="Radio" name="nPassengers" value="7">7</td>
    </tr>
  </table>

  <input type="submit" value="Search">
  <a href="">Inventory report</a>
</form:form>
</body>
</html>