<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Flight Searching</title>
<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
<script
  src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAIQTJJux4wCMpPFMFJLPa7hSfYe32xyc8iPgGkKi4PlUHhtMrSRTWeQOoVYiS_PpdlIa8lKl6kZKMrA"
  type="text/javascript"></script>
<script language="JavaScript"><!--
//

var msIE = navigator.appName.indexOf("Microsoft");
// Creates a marker at the given point with the given number label
function createMarker(point, text) {
  var marker = new GMarker(point);
  GEvent.addListener(marker, "click", function() {
   marker.openInfoWindowHtml(text);
  });
  return marker;
}

function getdegrees()
{
	var x=document.getElementById("airportlat");
	if(x.length() > 0)
		alert(x);
}
function load() {
  if (GBrowserIsCompatible()) {
    var map = new GMap2(document.getElementById("map"));
map.addControl(new GSmallMapControl());
map.addControl(new GMapTypeControl());
    map.setCenter(new GLatLng(39.17536111111111, -75.33166666666666), 6);
    directions = new GDirections(map);

//	var x=document.getElementById("airportlat");
//	if(x.length() > 0)
//		alert(x);

    // Download the data in data.xml and load it on the map. The format we
    // expect is:
    // <markers>
    //   <marker lat="37.441" lng="-122.141"/>
    //   <marker lat="37.322" lng="-121.213"/>
    // </markers>
    GDownloadUrl("phase1/airport.xml", function(data) {
      var xml = GXml.parse(data);
  var title = "";
  var description = "";
  var lat = "";
  var lng = "";
      var items = xml.documentElement.getElementsByTagName("Airport");
      for (var i = 0; i < items.length; i++) {
        var childNodes = items[i].childNodes;
        for(var k=0; k<childNodes.length; k++) {
        if(childNodes[k].nodeName == "Name")
        	    title = (msIE>=0) ? childNodes[k].text : childNodes[k].textContent;
    	if(childNodes[k].nodeName == "Location")
    	    description = (msIE>=0) ? childNodes[k].text : childNodes[k].textContent;
    	if(childNodes[k].nodeName == "Latitude")
    	    lat = (msIE>=0) ? childNodes[k].text : childNodes[k].textContent;
	    	// alert("lat: " + lat);
    	if(childNodes[k].nodeName == "Longitude")
    	    lng = (msIE>=0) ? childNodes[k].text : childNodes[k].textContent;
	    	// alert("lng: " + lng);
    }
	
        var point = new GLatLng(parseFloat(lat), parseFloat(lng));
    var note = title + "<br>" + description;
        map.addOverlay(createMarker(point, note));
      }
    });

    directions.load("from: Baltimore, MD to: Washington, DC");
  }
  
}


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
// 
--></script>
</head>
<body onload="load()" onunload="GUnload()">

<jsp:include page="/WEB-INF/jsp/header2.jsp">
  <jsp:param name="title2" value="Flight Search" />
</jsp:include>

<!-- div id="map" style="width: 600px; height: 400px"></div-->
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
        <th>Leaving from</th>
        <td><form:select path="departLocation">
          <c:forEach items="${airports}" var="airport">
            <form:option value="${airport[0]}" label="${airport[0]}: ${airport[1]}" />
          </c:forEach>
        </form:select> <input type="button" value="Search Airport" /> <form:errors path="departLocation"
          cssClass="error" /></td>
      </tr>
      <tr>
        <th>Going to</th>
        <td><form:select path="arrivalLocation">
          <c:forEach items="${airports}" var="airport">
            <form:option value="${airport[0]}" label="${airport[0]}: ${airport[1]}" />
          </c:forEach>
        </form:select> <input type="button" value="Search Airport" /> <form:errors path="arrivalLocation"
          cssClass="error" /></td>
      </tr>

      <tr>
        <th>Departing Date</th>
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
        </form:select> <input type="button" value="Calendar" /> <form:errors path="departMonth" cssClass="error" />
        <form:errors path="departDay" cssClass="error" /> <form:errors path="departYear"
          cssClass="error" /></td>
      </tr>
      <tr>
        <th>Departing Time</th>
        <td><form:select path="departHour">
          <form:option value="anytime" label="Anytime" />
          <c:forEach var="hour" begin="0" end="11" step="1">
            <form:option value="${hour}" label="${((hour + 11) % 12) +1} AM" />
          </c:forEach>
          <c:forEach var="hour" begin="12" end="23" step="1">
            <form:option value="${hour}" label="${((hour - 1) % 12) +1} PM" />
          </c:forEach>
        </form:select></td>
      </tr>

      <tr>
        <th>Trip type</th>
        <td>
        <table border="1">
          <tr>
            <td><c:forEach items="${tripTypes}" var="type">
              <form:radiobutton path="tripType" value="${type}" id="${type}" />
              <label for="${type}">${type.description}</label>
            </c:forEach> <form:errors path="tripType" cssClass="error" /></td>
          </tr>
        </table>
        </td>
      </tr>

      <tr>
        <th>Returning Date</th>
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
        </form:select> <input type="button" value="Calendar" /> <form:errors path="returnMonth" cssClass="error" />
        <form:errors path="returnDay" cssClass="error" /> <form:errors path="returnYear"
          cssClass="error" /></td>
      </tr>
      <tr>
        <th>Returning Time</th>
        <td><form:select path="returnHour">
          <form:option value="anytime" label="Anytime" />
          <c:forEach var="hour" begin="0" end="11" step="1">
            <form:option value="${hour}" label="${((hour + 11) % 12) +1} AM" />
          </c:forEach>
          <c:forEach var="hour" begin="12" end="23" step="1">
            <form:option value="${hour}" label="${((hour - 1) % 12) +1} PM" />
          </c:forEach>
        </form:select></td>
      </tr>

      <tr>
        <th>The number of<br />
        passengers</th>
        <td>
        <table border="1">
          <tr>
            <c:forEach var="nPerson" begin="1" end="8" step="1">
              <td><form:radiobutton path="numPassengers" value="${nPerson}"
                id="passenger${nPerson}" /> <label for="passenger${nPerson}"><c:out
                value="${nPerson}" /></label></td>
            </c:forEach>
            <td><select>
              <option value="">More</option>
              <c:forEach var="nPerson" begin="9" end="30" step="1">
                <option value="${nPerson}" /><c:out value="${nPerson}" /></option>
              </c:forEach>
            </select> <form:errors path="numPassengers" cssClass="error" /></td>
          </tr>
        </table>
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
  <!-- 
<input type="hidden" id="airportlat" value="<c:out value="${flight.airportByDepartureLocation_latitude2}" />"/><br />
  				  <input type="hidden" id="airportlong" value="<c:out value="${flight.airportByDepartureLocation_longitude2}" />"/><br />
             -->
  <form:errors path="departFlightNo" cssClass="error" />
  <form:errors path="returnFlightNo" cssClass="error" />

  <c:set var="step1"
    value="${(searchedDepartFlights == null) && (empty flightSearchForCustomerCMD.departFlightNo)}" />
  <c:set var="step2"
    value="${(searchedDepartFlights != null) && (empty flightSearchForCustomerCMD.departFlightNo)}" />
  <c:set var="step3"
    value="${(searchedReturnFlights != null) && (!empty flightSearchForCustomerCMD.departFlightNo)}" />

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
              <td><a href="http://maps.google.com"><c:out
                value="${flight.airportByDepartureLocation.name}" /> (<c:out
                value="${flight.airportByDepartureLocation.code}" />)</a><br />

              <c:out value="${flight.departureTime}" /></td>
              <td><a href="http://maps.google.com"><c:out
                value="${flight.airportByArrivalLocation.name}" /> (<c:out
                value="${flight.airportByArrivalLocation.code}" />)</a><br />
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
            <td><a href="http://maps.google.com"><c:out
              value="${selectedDepartFlight.airportByDepartureLocation.name}" /> (<c:out
              value="${selectedDepartFlight.airportByDepartureLocation.code}" />)</a><br />
            <c:out value="${selectedDepartFlight.departureTime}" /></td>
            <td><a href="http://maps.google.com"><c:out
              value="${selectedDepartFlight.airportByArrivalLocation.name}" /> (<c:out
              value="${selectedDepartFlight.airportByArrivalLocation.code}" />)</a><br />
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
              <td><a href="http://maps.google.com"><c:out
                value="${flight.airportByDepartureLocation.name}" /> (<c:out
                value="${flight.airportByDepartureLocation.code}" />)</a><br />
              <c:out value="${flight.departureTime}" /></td>
              <td><a href="http://maps.google.com"><c:out
                value="${flight.airportByArrivalLocation.name}" /> (<c:out
                value="${flight.airportByArrivalLocation.code}" />)</a><br />
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