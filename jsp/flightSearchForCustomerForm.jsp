<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Flight Searching</title>
<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
<link rel="stylesheet" type="text/css" href="css/datePicker.css" />
<!--script type="text/javascript" src="js/MochiKit/MochiKit.js"></script-->
<script type="text/javascript"
  src="http://www.google.com/jsapi?key=ABQIAAAAIQTJJux4wCMpPFMFJLPa7hSfYe32xyc8iPgGkKi4PlUHhtMrSRTWeQOoVYiS_PpdlIa8lKl6kZKMrA"></script>
<script type="text/javascript" src="js/proj4398.js"></script>
<script type="text/javascript" src="js/datePicker.js"></script>
<script type="text/javascript" src="js/searchAirport.js"></script>
<script language="JavaScript"><!--//

google.load("maps", "2");

//Call this function when the page has been loaded
function initGMap() {
  var mapObj = document.getElementById("map");
  if(null == mapObj) return;
  if (false == GBrowserIsCompatible()) return;

  var dLat = document.getElementById("departAirportLat").value;
  var dLng = document.getElementById("departAirportLng").value;
  var aLat = document.getElementById("arrivalAirportLat").value;
  var aLng = document.getElementById("arrivalAirportLng").value;
  var dLatLng = new GLatLng(dLat, dLng);
  var aLatLng = new GLatLng(aLat, aLng);
  var center = new GLatLng((parseFloat(dLat)+parseFloat(aLat))/2, (parseFloat(dLng)+parseFloat(aLng))/2);
  var n = Math.max(dLatLng.lat(), aLatLng.lat());
  var s = Math.min(dLatLng.lat(), aLatLng.lat());
  var e = Math.max(dLatLng.lng(), aLatLng.lng());
  var w = Math.min(dLatLng.lng(), aLatLng.lng());
  var bound = new GLatLngBounds(new GLatLng(s,w), new GLatLng(n,e));
  
  var iconDepart = new GIcon(G_DEFAULT_ICON);
  var iconArrival = new GIcon(G_DEFAULT_ICON);
  iconDepart.image = "phase2/images/airportDepart.png";
  iconArrival.image = "phase2/images/airportArrival.png";
  var markerDepart = new GMarker(dLatLng, { icon:iconDepart });
  var markerArrival = new GMarker(aLatLng, { icon:iconArrival });

  var polyOptions = {geodesic:true};
  var polyline = new GPolyline([ dLatLng, aLatLng ], "#ff0000", 10, 1, polyOptions);

  var map = new GMap2(mapObj);
  var depth = map.getBoundsZoomLevel(bound);
  map.setCenter(center, depth);
  map.addControl(new GSmallMapControl());
  map.addControl(new GMapTypeControl());
  map.addOverlay(markerDepart);
  map.addOverlay(markerArrival);
  map.addOverlay(polyline);
}

google.setOnLoadCallback(initGMap);


function returnObjById( id )
{
    if (document.getElementById)
        var returnVar = document.getElementById(id);
    else if (document.all)
        var returnVar = document.all[id];
    else if (document.layers)
        var returnVar = document.layers[id];
    return returnVar;
}

function submitSearch() {
  returnObjById("departFlightNo").value = "";
  returnObjById("returnFlightNo").value = "";
	document.flightSearchForCustomerForm.submit();
}

function submitWithDepartFlightNo(no) {
	returnObjById("departFlightNo").value = no;
	document.flightSearchForCustomerForm.submit();
}

function submitWithReturnFlightNo(no) {
  returnObjById("returnFlightNo").value = no;
  document.flightSearchForCustomerForm.submit();
}

function updateTripType() {
  var m = returnObjById('returnMonth');
  var d = returnObjById('returnDay');
  var y = returnObjById('returnYear');
  var h = returnObjById('returnHour');
  var r = returnObjById('returnHourRange');
  var c = returnObjById('returnCalendar');
  var w = returnObjById('returnWeek');
  var roundTrip = returnObjById('ROUND_TRIP');
  var oneWayTrip = returnObjById('ONEWAY_TRIP');

  if (oneWayTrip.checked)
    m.disabled = d.disabled = y.disabled = h.disabled = r.disabled = c.disabled = w.disabled = "true";
  else {
    m.removeAttribute('disabled');
    d.removeAttribute('disabled');
    y.removeAttribute('disabled');
    h.removeAttribute('disabled');
    r.removeAttribute('disabled');
    c.removeAttribute('disabled');
    w.removeAttribute('disabled');

    var wOs = w.options;
    wOs.selectedIndex = 0;
  }
}

function disableDates(idYear, idMonth, idDay, idWeek, idCalendar) {
  var dy = returnObjById (idYear);
  var dm = returnObjById (idMonth);
  var dd = returnObjById (idDay);
  var dw = returnObjById (idWeek);
  var dc = returnObjById (idCalendar);

  var dwOs = dw.options;
  var dwIdx = dwOs.selectedIndex;
  var dwVal = dwOs[dwIdx].value;

  if ("" == dwVal) {
    dy.removeAttribute('disabled');
    dm.removeAttribute('disabled');
    dd.removeAttribute('disabled');
    dc.removeAttribute('disabled');
  } else {
    dy.setAttribute('disabled', true);
    dm.setAttribute('disabled', true);
    dd.setAttribute('disabled', true);
    dc.setAttribute('disabled', true);
  } 
}

function disableDepartDate() {
 disableDates('departYear', 'departMonth', 'departDay', 'departWeek', 'departCalendar' ); 
}

function disableReturnDate() {
 disableDates('returnYear', 'returnMonth', 'returnDay', 'returnWeek', 'returnCalendar' ); 
}

function initEvents() {
  returnObjById('departWeek').addEventListener( 'change', disableDepartDate, false );
  returnObjById('returnWeek').addEventListener( 'change', disableReturnDate, false );
  disableDepartDate();
  disableReturnDate();

  returnObjById('ROUND_TRIP').addEventListener( 'click', updateTripType, false );
  returnObjById('ONEWAY_TRIP').addEventListener( 'click', updateTripType, false );
  updateTripType();
}

--></script>
</head>
<body onload="initEvents();" onunload="GUnload()">

<jsp:include page="/WEB-INF/jsp/header2.jsp">
  <jsp:param name="title2" value="Flight Search" />
</jsp:include>

<c:choose>
  <c:when test="${isOneWayTrip}">
    <c:set var="totalSteps" value="2" />
  </c:when>
  <c:otherwise>
    <c:set var="totalSteps" value="3" />
  </c:otherwise>
</c:choose>
<form:form method="post" commandName="flightSearchForCustomerCMD" name="flightSearchForCustomerForm">
  <table border="1" class="searchFlightForCustomer1step">
    <caption class="searchFlightForCustomer1StepTitle">Search Flight (1 / <c:out
      value="${totalSteps}" /> steps)</caption>
    <tbody>
      <tr>
        <th>Leaving from <img src="phase2/images/airportDepart.png" width="17" height="17" /></th>
        <td>
        <div><form:select path="departLocation" id="departLocation">
          <c:forEach items="${airports}" var="airport">
            <form:option value="${airport[0]}" label="${airport[0]}: ${airport[1]}" />
          </c:forEach>
        </form:select> <input type="button" value="Search Airport"
          onClick="displayAirport('departLocation', 'departLocation')" /></div>
        <form:errors path="departLocation" cssClass="error" /></td>
      </tr>
      <tr>
        <th>Going to <img src="phase2/images/airportArrival.png" width="17" height="17" /></th>
        <td>
        <div><form:select path="arrivalLocation">
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
        <div><form:select path="departWeek">
          <form:option value="">Any week</form:option>
          <c:forEach items="${weeks}" var="week">
            <form:option value="${week}">${week.description}</form:option>
          </c:forEach>
        </form:select> or <form:select path="departMonth">
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
          value="Calendar" id="departCalendar" /></div>

        <div><form:errors path="departMonth" cssClass="error" /> <form:errors
          path="departDay" cssClass="error" /> <form:errors path="departYear" cssClass="error" />
        <form:errors path="departWeek" cssClass="error" /></div>
        </td>
      </tr>

      <tr>
        <th>Departing Time</th>
        <td>
        <div><form:select path="departHour">
          <form:option value="" label="Anytime" />
          <c:forEach var="hour" begin="0" end="11" step="1">
            <form:option value="${hour}" label="${((hour + 11) % 12) +1} AM" />
          </c:forEach>
          <c:forEach var="hour" begin="12" end="23" step="1">
            <form:option value="${hour}" label="${((hour - 1) % 12) +1} PM" />
          </c:forEach>
        </form:select> Search range: &#177;<img height="10" width="10"
          src="http://www.fileformat.info/info/unicode/char/00b1/plusminus_sign.png"><form:select
          path="departHourRange">
          <form:option value="1" label="1 hour" />
          <c:forEach var="hour" begin="2" end="24" step="1">
            <form:option value="${hour}" label="${hour} hours" />
          </c:forEach>
        </form:select></div>
        </td>
      </tr>

      <tr>
        <th>Trip type</th>
        <td>
        <table border="1">
          <tr>
            <td>
            <div><c:forEach items="${tripTypes}" var="type">
              <form:radiobutton path="tripType" value="${type}" id="${type}" />
              <label for="${type}">${type.description}</label>
            </c:forEach></div>
            <form:errors path="tripType" cssClass="error" /></td>
          </tr>
        </table>
        </td>
      </tr>

      <tr>
        <th>Returning Date</th>
        <td>
        <div><form:select path="returnWeek">
          <form:option value="">Any week</form:option>
          <c:forEach items="${weeks}" var="week">
            <form:option value="${week}">${week.description}</form:option>
          </c:forEach>
        </form:select> or <form:select path="returnMonth">
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
          value="Calendar" id="returnCalendar" /></div>

        <div><form:errors path="returnMonth" cssClass="error" /> <form:errors
          path="returnDay" cssClass="error" /> <form:errors path="returnYear" cssClass="error" />
        <form:errors path="returnWeek" cssClass="error" /></div>
        </td>
      </tr>

      <tr>
        <th>Returning Time</th>
        <td>
        <div><form:select path="returnHour">
          <form:option value="" label="Anytime" />
          <c:forEach var="hour" begin="0" end="11" step="1">
            <form:option value="${hour}" label="${((hour + 11) % 12) +1} AM" />
          </c:forEach>
          <c:forEach var="hour" begin="12" end="23" step="1">
            <form:option value="${hour}" label="${((hour - 1) % 12) +1} PM" />
          </c:forEach>
        </form:select> Search range: &#177;<form:select path="returnHourRange">
          <form:option value="1" label="1 hour" />
          <c:forEach var="hour" begin="2" end="24" step="1">
            <form:option value="${hour}" label="${hour} hours" />
          </c:forEach>
        </form:select></div>
        </td>
      </tr>

      <tr>
        <th>Type of seat</th>
        <td>
        <div><c:forEach items="${seatClass}" var="type">
          <form:radiobutton path="seatClass" value="${type}" id="${type}" />
          <label for="${type}">${type.description}</label>
        </c:forEach></div>
        <form:errors path="seatClass" cssClass="error" /></td>
      </tr>

      <tr>
        <th>The number of<br />
        passengers</th>
        <td>
        <div>
        <table border="1">
          <tr>
            <c:forEach var="nPerson" begin="1" end="8" step="1">
              <td><form:radiobutton path="numPassengers" value="${nPerson}"
                id="passenger${nPerson}" /> <label for="passenger${nPerson}"><c:out
                value="${nPerson}" /></label></td>
            </c:forEach>
            <td><!-- select>
              <option value="">More</option>
              <c:forEach var="nPerson" begin="9" end="30" step="1">
                <option value="${nPerson}" /><c:out value="${nPerson}" /></option>
              </c:forEach>
            </select --> <form:errors path="numPassengers" cssClass="error" /></td>
          </tr>
        </table>
        </div>
        </td>
      </tr>

      <tr>
        <td colspan="2" align="right"><input type="reset" /> <input type="button"
          value="====Search Flight====" onClick="submitSearch();" /></td>
      </tr>
    </tbody>
  </table>


  <form:hidden path="departFlightNo" id="departFlightNo" />
  <form:hidden path="returnFlightNo" id="returnFlightNo" />
  <form:errors path="departFlightNo" cssClass="error" />
  <form:errors path="returnFlightNo" cssClass="error" />

  <c:set var="step1"
    value="${(searchedDepartFlights == null) && (empty flightSearchForCustomerCMD.departFlightNo)}" />
  <c:set var="step2"
    value="${(searchedDepartFlights != null) && (empty flightSearchForCustomerCMD.departFlightNo)}" />
  <c:set var="step3"
    value="${(searchedReturnFlights != null) && (!empty flightSearchForCustomerCMD.departFlightNo)}" />

  <c:choose>
    <c:when test="${step2 || step3}">
      <table border="1">
        <tr>
          <td>
          <div id="map" style="width: 600px; height: 300px"></div>
          </td>
        </tr>
      </table>
      <input type="hidden" id="departAirportLat" value="<c:out value="${departAirportLat}" />" />
      <input type="hidden" id="departAirportLng" value="<c:out value="${departAirportLng}" />" />
      <input type="hidden" id="arrivalAirportLat" value="<c:out value="${arrivalAirportLat}" />" />
      <input type="hidden" id="arrivalAirportLng" value="<c:out value="${arrivalAirportLng}" />" />
    </c:when>
  </c:choose>

  <c:choose>
    <%-- step 1/3 --%>
    <c:when test="${step1}">
      <p><span class="searchFlightForCustomer2StepTitle">Selecting Departing Flight (2 /
      <c:out value="${totalSteps}" /> steps)</span></p>
      <p><span class="searchFlightForCustomer3StepTitle">Selecting Returning Flight (3 /
      <c:out value="${totalSteps}" /> steps)</span></p>
    </c:when>

    <%-- step 2/3 --%>
    <c:when test="${step2}">
      <c:choose>
        <c:when test="${isOneWayTrip}">
          <c:set var="msgSelectOrReserve" value="Reserve" />
        </c:when>
        <c:when test="${isRoundTrip}">
          <c:set var="msgSelectOrReserve" value="Select" />
        </c:when>
      </c:choose>
      <p>
      <table border="1" class="searchFlightForCustomer2step">
        <caption class="searchFlightForCustomer2StepTitle">Choose a departing flight (2 /
        <c:out value="${totalSteps}" /> steps)</caption>
        <thead>
          <tr>
            <th>Departure location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
            and time</th>
            <th>Arrival location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
            and time</th>
            <th>cost of economy class (business class)</th>
            <th><a href="http://www.tvlon.com/resources/airlinecodes.htm">Airline name</a></th>
            <th><b><c:out value="${msgSelectOrReserve}" /></b></th>
          </tr>
        </thead>

        <tbody>
          <c:forEach items="${searchedDepartFlights}" var="flight">

            <tr>
              <td><c:out value="${flight.airportByDepartureLocation.name}" /> (<c:out
                value="${flight.airportByDepartureLocation.code}" />)<br />

              <c:out value="${flight.departureTime}" /></td>
              <td><c:out value="${flight.airportByArrivalLocation.name}" /> (<c:out
                value="${flight.airportByArrivalLocation.code}" />)<br />
              <c:out value="${flight.arrivalTime}" />
              <div>(<c:out value="${flight.durationHours}" /> hours)</div>
              </td>
              <td>
              <div>$<c:out value="${flight.economyPrice}" /></div>
              <div>($<c:out value="${flight.businessPrice}" />)</div>
              </td>
              <td>
              <table border="0">
                <tr>
                  <td><img
                    src="http://www.expedia.com/pubspec/images/airlines/sm${flight.airline.code}.gif"
                    alt="${flight.airline.code}" /></td>
                  <td><c:out value="${flight.airline.name}" /></td>
                </tr>
              </table>
              </td>
              <td><input type="button" value="<c:out value="${msgSelectOrReserve}"/>"
                onClick="submitWithDepartFlightNo(<c:out value="${flight.flightNo}"/>);" /></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <c:choose>
        <c:when test="${isOneWayTrip}">
        </c:when>
        <c:when test="${isRoundTrip}">
          <span class="searchFlightForCustomer3StepTitle">Selecting Returning Flight (3 / <c:out
            value="${totalSteps}" /> steps)</span>
        </c:when>
      </c:choose>
      </p>
    </c:when>

    <%-- step 3/3 --%>
    <c:when test="${step3}">
      <p>
      <table border="1" class="searchFlightForCustomer2step">
        <caption class="searchFlightForCustomer2StepTitle">Selected Departing Flight (2 /
        <c:out value="${totalSteps}" /> steps)</caption>
        <thead>
          <tr>
            <th>Departure location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
            and time</th>
            <th>Arrival location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
            and time</th>
            <th>cost of economy class (business class)</th>
            <th><a href="http://www.tvlon.com/resources/airlinecodes.htm">Airline name</a></th>
            <th><b>Cancel</b></th>
          </tr>
        </thead>

        <tbody>
          <tr>
            <td><c:out value="${selectedDepartFlight.airportByDepartureLocation.name}" /> (<c:out
              value="${selectedDepartFlight.airportByDepartureLocation.code}" />)<br />
            <c:out value="${selectedDepartFlight.departureTime}" /></td>
            <td><c:out value="${selectedDepartFlight.airportByArrivalLocation.name}" /> (<c:out
              value="${selectedDepartFlight.airportByArrivalLocation.code}" />)<br />
            <c:out value="${selectedDepartFlight.arrivalTime}" />
            <div>(<c:out value="${selectedDepartFlight.durationHours}" /> hours)</div>
            </td>
            <td>
            <div>$<c:out value="${selectedDepartFlight.economyPrice}" /></div>
            <div>($<c:out value="${selectedDepartFlight.businessPrice}" />)</div>
            </td>
            <td>
            <table border="0">
              <tr>
                <td><img
                  src="http://www.expedia.com/pubspec/images/airlines/sm${selectedDepartFlight.airline.code}.gif"
                  alt="${selectedDepartFlight.airline.code}" /></td>
                <td><c:out value="${selectedDepartFlight.airline.name}" /></td>
              </tr>
            </table>
            </td>
            <td><input type="button" value="Cancel" onClick="submitSearch();" /></td>
          </tr>
        </tbody>
      </table>
      </P>

      <p>
      <table border="1" class="searchFlightForCustomer3step">
        <caption class="searchFlightForCustomer3StepTitle">Choose Returning Flight (3 / <c:out
          value="${totalSteps}" /> steps)</caption>
        <thead>
          <tr>
            <th>Departure location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
            and time</th>
            <th>Arrival location (<a href="http://www.orbitz.com/App/global/airportCodes.jsp">Code</a>)<br />
            and time</th>
            <th>cost of economy class (business class)</th>
            <th><a href="http://www.tvlon.com/resources/airlinecodes.htm">Airline name</a></th>
            <th><b>Reserve</b></th>
          </tr>
        </thead>

        <tbody>
          <c:forEach items="${searchedReturnFlights}" var="flight">
            <tr>
              <td><c:out value="${flight.airportByDepartureLocation.name}" /> (<c:out
                value="${flight.airportByDepartureLocation.code}" />)<br />
              <c:out value="${flight.departureTime}" /></td>
              <td><c:out value="${flight.airportByArrivalLocation.name}" /> (<c:out
                value="${flight.airportByArrivalLocation.code}" />)<br />
              <c:out value="${flight.arrivalTime}" />
              <div>(<c:out value="${flight.durationHours}" /> hours)</div>
              </td>
              <td>
              <div>$<c:out value="${flight.economyPrice}" /></div>
              <div>($<c:out value="${flight.businessPrice}" />)</div>
              </td>
              <td>
              <table border="0">
                <tr>
                  <td><img
                    src="http://www.expedia.com/pubspec/images/airlines/sm${flight.airline.code}.gif"
                    alt="${flight.airline.code}" /></td>
                  <td><c:out value="${flight.airline.name}" /></td>
                </tr>
              </table>
              </td>
              <td><input type="button" value="Reserve"
                onClick="submitWithReturnFlightNo(<c:out value="${flight.flightNo}"/>);" /></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      </p>
    </c:when>

  </c:choose>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>

</body>
</html>
