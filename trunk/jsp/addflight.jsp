<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page import="java.util.List"%>
<html>
<head>
<title>Adding flight</title>
<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
<link rel="stylesheet" type="text/css" href="css/datePicker.css" />
<script type="text/javascript" src="js/proj4398.js"></script>
<script type="text/javascript" src="js/datePicker.js"></script>
<script type="text/javascript" src="js/searchAirport.js"></script>
</head>
<script language="JavaScript"><!--//

function updateAirlineIcon() {
  var airline = document.getElementById('airline');
  var icon = document.getElementById('airlineIcon');
  if (null == airline || null == icon) return;

  var os = airline.options;
  var idx = os.selectedIndex;
  var code = os[idx].value;
  var url = "http://www.expedia.com/pubspec/images/airlines/sm" + code + ".gif";
  icon.setAttribute('src', url);
}

function onlyNumbers(e) {
  var keynum;
  var keychar;
  var numcheck;

  if(window.event) // IE
    keynum = e.keyCode;
  else if(e.which) // Netscape/Firefox/Opera
    keynum = e.which;

  keychar = String.fromCharCode(keynum);
  numcheck = /\d/;
  return numcheck.test(keychar);
}

function checkFlightNo(e) {
  if(false == onlyNumbers(e)) e.stopPropagation();
}

function initEvent() {
  document.getElementById('flightNo').addEventListener( "keydown", checkFlightNo, false );
  document.getElementById('flightNo').setAttribute('maxlength', 3);
  document.getElementById('airline').addEventListener( "change", updateAirlineIcon, false );
  updateAirlineIcon();
}
//-->
</script>
<body onLoad="initEvent();">
<jsp:include page="/WEB-INF/jsp/header2.jsp">
  <jsp:param name="title2" value="Flight Search" />
</jsp:include>

<form:form commandName="flight" method="POST" name="flight">

  <table border="1">
    <tr>
      <td>Flight number<br />
      (3 digits)</td>
      <td><form:input path="flightNo" /></td>
    </tr>
    <tr>
      <td>Airline<br />
      (code: name)</td>
      <td><form:select path="airline">
        <c:forEach items="${airlines}" var="airline">
          <form:option value="${airline[0]}" label="${airline[0]}: ${airline[1]}" />
        </c:forEach>
      </form:select> <img id="airlineIcon"/></td>
    </tr>
    <tr>
      <td>Departure airport</td>
      <td><form:select path="airportByArrivalLocation">
        <c:forEach items="${airports}" var="airport">
          <form:option value="${airport[0]}" label="${airport[0]}: ${airport[1]}" />
        </c:forEach>
      </form:select> <input type="button" value="Search"
        onClick="displayAirport('airportByArrivalLocation', 'airportByArrivalLocation')" /></td>
    </tr>
    <tr>
      <td>Departing Date</td>
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
      </form:select> / <form:select path="departDay">
        <c:forEach var="day" begin="1" end="31" step="1">
          <form:option value="${day}" />
        </c:forEach>
      </form:select> / <form:select path="departYear">
        <c:forEach var="year" begin="2008" end="2011" step="1">
          <form:option value="${year}" />
        </c:forEach>
      </form:select> <input type="button"
        onClick="displayDatePicker('departYear','departMonth','departDay', 'departMonth');"
        value="Calendar" /></td>
    </tr>
    <tr>
      <td>Departure time</td>
      <td><form:select path="departHour">
        <c:forEach var="hour" begin="0" end="11" step="1">
          <form:option value="${hour}" label="${((hour + 11) % 12) +1} AM" />
        </c:forEach>
        <c:forEach var="hour" begin="12" end="23" step="1">
          <form:option value="${hour}" label="${((hour - 1) % 12) +1} PM" />
        </c:forEach>
      </form:select></td>
    </tr>
    <tr>
      <td>Arrival airport</td>
      <td><form:select path="airportByDepartureLocation">
        <c:forEach items="${airports}" var="airport">
          <form:option value="${airport[0]}" label="${airport[0]}: ${airport[1]}" />
        </c:forEach>
      </form:select> <input type="button" value="Search"
        onClick="displayAirport('airportByDepartureLocation', 'airportByDepartureLocation')" /></td>
    </tr>
    <tr>
      <td>Arrival day</td>
      <td><form:select path="returnMonth">
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
      </form:select> / <form:select path="returnDay">
        <c:forEach var="day" begin="1" end="31" step="1">
          <form:option value="${day}" />
        </c:forEach>
      </form:select> / <form:select path="returnYear">
        <c:forEach var="year" begin="2008" end="2011" step="1">
          <form:option value="${year}" />
        </c:forEach>
      </form:select> <input type="button"
        onClick="displayDatePicker('returnYear','returnMonth','returnDay', 'returnMonth');"
        value="Calendar" /></td>
    </tr>
    <tr>
      <td>Arrival time</td>
      <td><form:select path="returnHour">
        <c:forEach var="hour" begin="0" end="11" step="1">
          <form:option value="${hour}" label="${((hour + 11) % 12) +1} AM" />
        </c:forEach>
        <c:forEach var="hour" begin="12" end="23" step="1">
          <form:option value="${hour}" label="${((hour - 1) % 12) +1} PM" />
        </c:forEach>
      </form:select></td>
    </tr>
    <tr>
      <td>Ecomomy Seats</td>
      <td><form:select path="economySeats">
        <form:option value="10" label="10" />
        <form:option value="20" label="20" />
        <form:option value="30" label="30" />
        <form:option value="40" label="40" />
        <form:option value="50" label="50" />
      </form:select> seats</td>
    </tr>
    <tr>
      <td>Economy Price<br />
      (dollar)</td>
      <td>$<form:input path="economyPrice" /></td>
    </tr>
    <tr>
      <td>Business Seats</td>
      <td><form:select path="businessSeats">
        <form:option value="10" label="10" />
        <form:option value="20" label="20" />
        <form:option value="30" label="30" />
        <form:option value="40" label="40" />
        <form:option value="50" label="50" />
      </form:select> seats</td>
    </tr>
    <tr>
      <td>Business Prices<br />
      (dollar)</td>
      <td>$<form:input path="businessPrice" /></td>
    </tr>

    <tr>
      <td colspan="2"><input type="submit" value="Add Flight" /></td>
    </tr>
  </table>
</form:form>
</body>
</html>
