<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Flight Searching for Managers</title>
<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
<link rel="stylesheet" type="text/css" href="css/datePicker.css" />
<script type="text/javascript" src="js/proj4398.js"></script>
<script type="text/javascript" src="js/datePicker.js"></script>
<script type="text/javascript" src="js/searchAirport.js"></script>
<script language="JavaScript"><!--//

function submitSearch() {
  document.flightSearchForManagerForm.submit();
}

function updateAirlineIcon() {
    var airline = document.getElementById('airline');
    var icon = document.getElementById('airlineIcon');
    if (null == airline || null == icon) return;

    var os = airline.options;
    var idx = os.selectedIndex;
    var code = os[idx].value;
    if(code.length > 0) {
      var url = "http://www.expedia.com/pubspec/images/airlines/sm" + code + ".gif";
      icon.setAttribute('src', url);
    } else
        icon.removeAttribute('src');
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

  function focusOn() {
    document.getElementById('flightNo').focus();
  }

--></script>
</head>
<body onLoad="initEvent(); focusOn();">
<jsp:include page="/WEB-INF/jsp/header2.jsp">
  <jsp:param name="title2" value="Flight Search For Managers" />
</jsp:include>

<form:form method="post" commandName="flightSearchForManagerCMD" name="flightSearchForManagerForm">
  <table border="1" class="searchFlightForCustomer1step">
    <tbody>
      <tr>
        <th>Flight number</th>
        <td><form:input path="flightNo" /> (3-digits)<form:errors path="flightNo"
          cssClass="error" /></td>
      </tr>

      <tr>
        <th>Airline</th>
        <td><form:select path="airline">
          <form:option value="" label="Any airline" />
          <c:forEach items="${airlines}" var="airline">
            <form:option value="${airline[0]}" label="${airline[0]}: ${airline[1]}" />
          </c:forEach>
        </form:select> <img id="airlineIcon" /> <form:errors path="airline" cssClass="error" /></td>
      </tr>

      <tr>
        <th>Departure <img src="phase2/images/airportDepart.png" width="17" height="17" /></th>
        <td>
        <div><form:select path="departLocation" id="departLocation">
          <form:option value="" label="Any departing location" />
          <c:forEach items="${airports}" var="airport">
            <form:option value="${airport[0]}" label="${airport[0]}: ${airport[1]}" />
          </c:forEach>
        </form:select> <input type="button" value="Search Airport"
          onClick="displayAirport('departLocation', 'departLocation')" /></div>
        <form:errors path="departLocation" cssClass="error" /></td>
      </tr>

      <tr>
        <th>Arrival <img src="phase2/images/airportArrival.png" width="17" height="17" /></th>
        <td>
        <div><form:select path="arrivalLocation">
          <form:option value="" label="Any arriving location" />
          <c:forEach items="${airports}" var="airport">
            <form:option value="${airport[0]}" label="${airport[0]}: ${airport[1]}" />
          </c:forEach>
        </form:select> <input type="button" value="Search Airport"
          onClick="displayAirport('arrivalLocation', 'arrivalLocation')" /></div>
        <form:errors path="arrivalLocation" cssClass="error" /></td>
      </tr>

      <tr>
        <th>Departing Date</th>
        <td>
        <div><form:select path="departMonth">
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
          value="Calendar" /></div>
        <form:errors path="departMonth" cssClass="error" /> <form:errors path="departDay"
          cssClass="error" /> <form:errors path="departYear" cssClass="error" /></td>
      </tr>

      <tr>
        <th>Departing Time</th>
        <td>
        <div><form:select path="departHour">
          <form:option value="-1" label="Anytime" />
          <c:forEach var="hour" begin="0" end="11" step="1">
            <form:option value="${hour}" label="${((hour + 11) % 12) +1} AM" />
          </c:forEach>
          <c:forEach var="hour" begin="12" end="23" step="1">
            <form:option value="${hour}" label="${((hour - 1) % 12) +1} PM" />
          </c:forEach>
        </form:select></div>
        </td>
      </tr>

      <tr>
        <th>Arriving Date</th>
        <td>
        <div><form:select path="arriveMonth">
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
        </form:select> / <form:select path="arriveDay">
          <c:forEach var="day" begin="1" end="31" step="1">
            <form:option value="${day}" />
          </c:forEach>
        </form:select> / <form:select path="arriveYear">
          <c:forEach var="year" begin="2008" end="2011" step="1">
            <form:option value="${year}" />
          </c:forEach>
        </form:select> <input type="button"
          onClick="displayDatePicker('arriveYear','arriveMonth','arriveDay', 'arriveMonth');"
          value="Calendar" /></div>
        <form:errors path="arriveMonth" cssClass="error" /> <form:errors path="arriveDay"
          cssClass="error" /> <form:errors path="arriveYear" cssClass="error" /></td>
      </tr>

      <tr>
        <th>Arriving Time</th>
        <td>
        <div><form:select path="arriveHour">
          <form:option value="-1" label="Anytime" />
          <c:forEach var="hour" begin="0" end="11" step="1">
            <form:option value="${hour}" label="${((hour + 11) % 12) +1} AM" />
          </c:forEach>
          <c:forEach var="hour" begin="12" end="23" step="1">
            <form:option value="${hour}" label="${((hour - 1) % 12) +1} PM" />
          </c:forEach>
        </form:select></div>
        </td>
      </tr>

      <tr>
        <td colspan="2" align="right"><input type="reset" /> <input type="button"
          value="====Search Flight====" onClick="submitSearch();" /></td>
      </tr>

    </tbody>
  </table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>

</body>
</html>