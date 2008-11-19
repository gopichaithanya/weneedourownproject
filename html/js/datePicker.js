var datePickerDivID = "datepicker";
var iFrameDivID = "datepickeriframe";

var dayArrayShort = new Array('Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa');
var dayArrayMed = new Array('Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat');
var dayArrayLong = new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday',
		'Thursday', 'Friday', 'Saturday');
var monthArrayShort = new Array('Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
		'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec');
var monthArrayMed = new Array('Jan', 'Feb', 'Mar', 'Apr', 'May', 'June',
		'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec');
var monthArrayLong = new Array('January', 'February', 'March', 'April', 'May',
		'June', 'July', 'August', 'September', 'October', 'November',
		'December');

function displayDatePicker(dateFieldNameYear, dateFieldNameMonth,
		dateFieldNameDay, displayBelowThisId) {
	var targetYear = document.getElementById(dateFieldNameYear);
	var targetMonth = document.getElementById(dateFieldNameMonth);
	var targetDay = document.getElementById(dateFieldNameDay);

	// if we weren't told what node to display the datepicker beneath, just
	// display it
	// beneath the date field we're updating
	var displayBelowThisObject = document.getElementById(displayBelowThisId);
	if (!displayBelowThisObject)
		displayBelowThisObject = targetMonth;

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

	addDatePicker(targetYear, targetMonth, targetDay, x, y);

	// draw the datepicker table
	var iy = targetYear.options.selectedIndex;
	var im = targetMonth.options.selectedIndex;
	var id = targetDay.options.selectedIndex
	var y = targetYear.options[iy].value;
	var m = targetMonth.options[im].value;
	var d = targetDay.options[id].value;
	var dt = getFieldDate(y, m, d);
	refreshDatePicker(dateFieldNameYear, dateFieldNameMonth, dateFieldNameDay,
			dt.getFullYear(), dt.getMonth() +1, dt.getDate());
}

/**
 * Convert a string to a JavaScript Date object.
 */
function getFieldDate(year, month, day) {
	var dateVal = new Date();
	try {
		dateVal = new Date(year, month - 1, day);
	} catch (e) {
	}
	return dateVal;
}

function addDatePicker(targetYear, targetMonth, targetDay, x, y) {
	if (!document.getElementById(datePickerDivID)) {
		var newNode = document.createElement("div");
		newNode.setAttribute("id", datePickerDivID);
		newNode.setAttribute("class", "dpDiv");
		newNode.setAttribute("style", "visibility: hidden;");
		document.body.appendChild(newNode);
	}

	// move the datepicker div to the proper x,y coordinate and toggle the
	// visiblity
	var pickerDiv = document.getElementById(datePickerDivID);
	pickerDiv.style.position = "absolute";
	pickerDiv.style.left = x + "px";
	pickerDiv.style.top = y + "px";
	pickerDiv.style.visibility = (pickerDiv.style.visibility == "visible" ? "hidden"
			: "visible");
	pickerDiv.style.display = (pickerDiv.style.display == "block" ? "none"
			: "block");
	pickerDiv.style.zIndex = 10000;
}

/**
 * Convenience function for writing the code for the buttons that bring us back
 * or forward a month.
 */
function getMonthButtonCode(dateFieldNameYear, dateFieldNameMonth,
		dateFieldNameDay, dateVal, adjust, label) {
	var newMonth = (dateVal.getMonth() + adjust) % 12;
	var newYear = dateVal.getFullYear()
			+ parseInt((dateVal.getMonth() + adjust) / 12);
	if (newMonth < 0) {
		newMonth += 12;
		newYear += -1;
	}

	return "<button class='dpButton' onClick='refreshDatePicker(\""
			+ dateFieldNameYear + "\", \"" + dateFieldNameMonth + "\", \""
			+ dateFieldNameDay + "\", " + newYear + ", " + (newMonth + 1)
			+ ");'>" + label + "</button>";
}

/**
 * This is the function that actually draws the datepicker calendar.
 */
function refreshDatePicker(dateFieldNameYear, dateFieldNameMonth,
		dateFieldNameDay, year, month, day) {
	// if no arguments are passed, use today's date; otherwise, month and year
	// are required (if a day is passed, it will be highlighted later)
	var thisDay = new Date();

	if ((month > 0) && (year > 0)) {
		thisDay = new Date(year, month - 1, 1);
	} else {
		day = thisDay.getDate();
		thisDay.setDate(1);
	}

	// the calendar will be drawn as a table
	// you can customize the table elements with a global CSS style sheet,
	// or by hardcoding style and formatting elements below
	var crlf = "\r\n";
	var TABLE = "<table cols=7 class='dpTable'>" + crlf;
	var xTABLE = "</table>" + crlf;
	var TR = "<tr class='dpTR'>";
	var TR_title = "<tr class='dpTitleTR'>";
	var TR_days = "<tr class='dpDayTR'>";
	var TR_todaybutton = "<tr class='dpTodayButtonTR'>";
	var xTR = "</tr>" + crlf;
	var TD = "<td class='dpTD' onMouseOut='this.className=\"dpTD\";' onMouseOver=' this.className=\"dpTDHover\";' "; // leave
	var TD_title = "<td colspan=5 class='dpTitleTD'>";
	var TD_buttons = "<td class='dpButtonTD'>";
	var TD_todaybutton = "<td colspan=7 class='dpTodayButtonTD'>";
	var TD_days = "<td class='dpDayTD'>";
	var TD_selected = "<td class='dpDayHighlightTD' onMouseOut='this.className=\"dpDayHighlightTD\";' onMouseOver='this.className=\"dpTDHover\";' "; // leave
	var xTD = "</td>" + crlf;
	var DIV_title = "<div class='dpTitleText'>";
	var DIV_selected = "<div class='dpDayHighlight'>";
	var xDIV = "</div>";

	// start generating the code for the calendar table
	var html = TABLE;

	// this is the title bar, which displays the month and the buttons to
	// go back to a previous month or forward to the next month
	html += TR_title;
	html += TD_buttons
			+ getMonthButtonCode(dateFieldNameYear, dateFieldNameMonth,
					dateFieldNameDay, thisDay, -1, "&lt;") + xTD;
	html += TD_title + DIV_title + monthArrayLong[thisDay.getMonth()] + " "
			+ thisDay.getFullYear() + xDIV + xTD;
	html += TD_buttons
			+ getMonthButtonCode(dateFieldNameYear, dateFieldNameMonth,
					dateFieldNameDay, thisDay, 1, "&gt;") + xTD;
	html += xTR;

	// this is the row that indicates which day of the week we're on
	html += TR_days;
	for (i = 0; i < dayArrayShort.length; i++)
		html += TD_days + dayArrayShort[i] + xTD;
	html += xTR;

	// now we'll start populating the table with days of the month
	html += TR;

	// first, the leading blanks
	for (i = 0; i < thisDay.getDay(); i++)
		html += TD + "&nbsp;" + xTD;

	// now, the days of the month
	do {
		var dayNum = thisDay.getDate();
		TD_onclick = " onclick='updateDateField(\"" + dateFieldNameYear
				+ "\", \"" + dateFieldNameMonth + "\", \"" + dateFieldNameDay
				+ "\", " + year + ", " + month + ", " + dayNum + ");'>";

		if (dayNum == day)
			html += TD_selected + TD_onclick + DIV_selected + dayNum + xDIV
					+ xTD;
		else
			html += TD + TD_onclick + dayNum + xTD;

		// if this is a Saturday, start a new row
		if (thisDay.getDay() == 6)
			html += xTR + TR;

		// increment the day
		thisDay.setDate(thisDay.getDate() + 1);
	} while (thisDay.getDate() > 1)

	// fill in any trailing blanks
	if (thisDay.getDay() > 0) {
		for (i = 6; i > thisDay.getDay(); i--)
			html += TD + "&nbsp;" + xTD;
	}
	html += xTR;

	// add a button to allow the user to easily return to today, or close the
	// calendar
	var today = new Date();
	var todayString = "Today is " + dayArrayMed[today.getDay()] + ", "
			+ monthArrayMed[today.getMonth()] + " " + today.getDate();
	html += TR_todaybutton + TD_todaybutton;
	html += "<button class='dpTodayButton' onClick='refreshDatePicker(\""
			+ dateFieldNameYear + "\", \"" + dateFieldNameMonth + "\", \""
			+ dateFieldNameDay + "\", " + today.getFullYear() + ", "
			+ (today.getMonth() + 1) + ");'>this month</button> ";
	html += "<button class='dpTodayButton' onClick='updateDateField();'>close</button>";
	html += xTD + xTR;

	// and finally, close the table
	html += xTABLE;

	document.getElementById(datePickerDivID).innerHTML = html;
	// add an "iFrame shim" to allow the datepicker to display above selection
	// lists
	adjustiFrame();
}

function updateDateField(dateFieldNameYear, dateFieldNameMonth,
		dateFieldNameDay, year, month, day) {
	if (null != dateFieldNameYear) {
		var targetYear = document.getElementById(dateFieldNameYear);
		var targetMonth = document.getElementById(dateFieldNameMonth);
		var targetDay = document.getElementById(dateFieldNameDay);
		var y = targetYear.options;
		var m = targetMonth.options;
		var d = targetDay.options;
		for ( var i = 0; i < y.length; ++i)
			if (y[i].value == year)
				y.selectedIndex = i;
		for ( var i = 0; i < m.length; ++i)
			if (m[i].value == month)
				m.selectedIndex = i;
		for ( var i = 0; i < d.length; ++i)
			if (d[i].value == day)
				d.selectedIndex = i;
	}

	var pickerDiv = document.getElementById(datePickerDivID);
	pickerDiv.style.visibility = "hidden";
	pickerDiv.style.display = "none";

	adjustiFrame();
	// targetDateField.focus();

	// after the datepicker has closed, optionally run a user-defined function
	// called
	// datePickerClosed, passing the field that was just updated as a parameter
	// (note that this will only run if the user actually selected a date from
	// the datepicker)
	if (typeof (datePickerClosed) == "function")
		datePickerClosed(targetDateField);
}

function adjustiFrame(pickerDiv, iFrameDiv) {
	// we know that Opera doesn't like something about this, so if we
	// think we're using Opera, don't even try
	var is_opera = (navigator.userAgent.toLowerCase().indexOf("opera") != -1);
	if (is_opera)
		return;

	// put a try/catch block around the whole thing, just in case
	try {
		if (!document.getElementById(iFrameDivID)) {
			// don't use innerHTML to update the body, because it can cause
			// global variables
			// that are currently pointing to objects on the page to have bad
			// references
			// document.body.innerHTML += "<iframe id='" + iFrameDivID + "'
			// src='javascript:false;' scrolling='no' frameborder='0'>";
			var newNode = document.createElement("iFrame");
			newNode.setAttribute("id", iFrameDivID);
			newNode.setAttribute("src", "javascript:false;");
			newNode.setAttribute("scrolling", "no");
			newNode.setAttribute("frameborder", "0");
			document.body.appendChild(newNode);
		}

		if (!pickerDiv)
			pickerDiv = document.getElementById(datePickerDivID);
		if (!iFrameDiv)
			iFrameDiv = document.getElementById(iFrameDivID);

		try {
			iFrameDiv.style.position = "absolute";
			iFrameDiv.style.width = pickerDiv.offsetWidth;
			iFrameDiv.style.height = pickerDiv.offsetHeight;
			iFrameDiv.style.top = pickerDiv.style.top;
			iFrameDiv.style.left = pickerDiv.style.left;
			iFrameDiv.style.zIndex = pickerDiv.style.zIndex - 1;
			iFrameDiv.style.visibility = pickerDiv.style.visibility;
			iFrameDiv.style.display = pickerDiv.style.display;
		} catch (e) {
		}

	} catch (ee) {
	}

}
