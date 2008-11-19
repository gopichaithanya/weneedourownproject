var airportDivID = "airportSearchDiv";
var airportTextID = "airportSearchTextId";
var airportCandidateID = "airportSearchCandidateID";

function addSearchAirport(x, y) {
	if (!document.getElementById(airportDivID)) {
		var newNode = document.createElement("div");
		newNode.setAttribute("id", airportDivID);
		newNode.setAttribute("class", "dpDiv");
		newNode.setAttribute("style", "visibility: hidden;");
		document.body.appendChild(newNode);
	}

	var apDiv = document.getElementById(airportDivID);
	apDiv.style.position = "absolute";
	apDiv.style.left = x + "px";
	apDiv.style.top = y + "px";
	apDiv.style.visibility = (apDiv.style.visibility == "visible" ? "hidden"
			: "visible");
	apDiv.style.display = (apDiv.style.display == "block" ? "none" : "block");
	apDiv.style.zIndex = 1000;
}

function refreshAirport(fieldName) {
	var crlf = "\r\n";
	var TABLE = "<table cols=7 class='dpTable'>" + crlf;
	var xTABLE = "</table>" + crlf;
	var TR_title = "<tr class='dpTitleTR'>";
	var xTR = "</tr>" + crlf;
	var TD_title = "<td colspan=5 class='dpTitleTD'>";
	var xTD = "</td>" + crlf;

	// start generating the code for the calendar table
	var html = TABLE;
	html += TR_title + TD_title;
	html += '<input type="text" size="45" onChange="updateCandidate();" id="'
			+ airportTextID + '"/>' + crlf;
	html += '<input type="button" value="select" onClick="updateAirport(' + "'"
			+ fieldName + "'" + ');" />' + crlf;
	html += '<input type="button" value="close" onClick="closeAirport()"/>' + crlf;
	html += xTD + xTR;
	html += TR_title + TD_title;
	html += '<div id="' + airportCandidateID + '"> </div>' + crlf;
	html += xTD + xTR;
	html += xTABLE;

	document.getElementById(airportDivID).innerHTML = html;

	var apTxt = document.getElementById(airportTextID);
	if (null != apTxt)
		apTxt.focus();
}

function closeAirport() {
	var apDiv = document.getElementById(airportDivID);
	apDiv.style.visibility = "hidden";
	apDiv.style.display = "none";
}

function updateCandidate() {

}

function updateAirport(fieldName) {
	var field = document.getElementById(fieldName);
	var apId = document.getElementById(airportTextID).value;

	var f = field.options;
	for ( var i = 0; i < f.length; ++i)
		if (f[i].value.toLowerCase() == apId.toLowerCase())
			f.selectedIndex = i;

	closeAirport();
}

function displayAirport(fieldName, displayBelowThisId) {
	var displayBelowThisObject = document.getElementById(displayBelowThisId);

	var x = displayBelowThisObject.offsetLeft;
	var y = displayBelowThisObject.offsetTop
			+ displayBelowThisObject.offsetHeight;

	// deal with elements inside tables and such
	var parent = displayBelowThisObject;
	while (parent.offsetParent) {
		parent = parent.offsetParent;
		x += parent.offsetLeft;
		y += parent.offsetTop;
	}

	addSearchAirport(x, y);

	// draw the datepicker table
	refreshAirport(fieldName);
}
