<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
 <title>Airport Listing</title>
<link rel="stylesheet" type="text/css" href="css/proj4398.css" />
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAIQTJJux4wCMpPFMFJLPa7hSfYe32xyc8iPgGkKi4PlUHhtMrSRTWeQOoVYiS_PpdlIa8lKl6kZKMrA"
      type="text/javascript">
</script>
<script type="text/javascript">

	var msIE = navigator.appName.indexOf("Microsoft");
	// Create a base icon for all of our markers that specifies the
    // shadow, icon dimensions, etc.
    //var baseIcon = new GIcon(G_DEFAULT_ICON);




    var iconOptions = {};
    iconOptions.primaryColor = "#FF0000";
    iconOptions.strokeColor = "#000000";
    iconOptions.label = "1";
    iconOptions.labelColor = "#000000";
    iconOptions.addStar = false;
    iconOptions.starPrimaryColor = "#FFFF00";
    iconOptions.starStrokeColor = "#0000FF";
    //var newIcon = MapIconMaker.createLabeledMarkerIcon(iconOptions);

    var gmarkers = [];
    var htmls = [];
    var j = 0;
    var side_bar_html = "";
    // === Create an associative array of GIcons() ===
    var gicons = [];
    var mainimage = "http://www.google.com/mapfiles/marker.png";
    
    // Creates a marker at the given point with the given number label
    function createMarker(point,name, text) {

        
        
   	 	//var letter = String.fromCharCode("A".charCodeAt(0) + number);
      //var numberedIcon = new GIcon(icon);

		//numberIcon.label = "1";
		
       // Set up our GMarkerOptions object
       //markerOptions = { icon:newIcon };
       //var marker = new GMarker(map.getCenter(), {icon: newIcon})
       //var marker = new GMarker(point, markerOptions);
       //alert(name);
       var marker = new GMarker(point);
      
      GEvent.addListener(marker, "click", function() {
          
       marker.openInfoWindowHtml(text);
      });
      
      // save the info we need to use later for the side_bar
      gmarkers[j] = marker;
      htmls[j] = text;
      // add a line to the side_bar html
      if ((j % 20) == 0)
    	  side_bar_html += "</td><td>"   
      side_bar_html += '<a href="javascript:myclick(' + j + ')">' + name + '<\/a><br>';
      //i++;
      j++;
      return marker;
    }

    function myclick(i) {
        gmarkers[i].openInfoWindowHtml(htmls[i]);
      }
    
    function load() {
      if (GBrowserIsCompatible()) {
        var map = new GMap2(document.getElementById("map"));
	map.addControl(new GSmallMapControl());
	map.addControl(new GMapTypeControl());
        map.setCenter(new GLatLng(38.947224, -77.459724), 7);

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
	  var code = "";
          var items = xml.documentElement.getElementsByTagName("Airport");
          for (i = 0; i < items.length; i++) {
              j = i;
        	 
            var childNodes = items[i].childNodes;
            for(var k=0; k<childNodes.length; k++) {
	        if(childNodes[k].nodeName == "Name")
	        {
            	    title = (msIE>=0) ? childNodes[k].text : childNodes[k].textContent;
            	    mystr = new String(title);
            	    //document.getElementById("side_bar").innerHTML += mystr + "<br>";
            	    //gicon[mystr] = new GIcon(G_DEFAULT_ICON, mainimage);
            	    
	        }
	        if(childNodes[k].nodeName == "Code")
	    	    code = (msIE>=0) ? childNodes[k].text : childNodes[k].textContent;
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
	    	var note = "<br>"+ title + "<br>" + description + "<br>" + code;
	    	
            map.addOverlay(createMarker(point, title,note));
          }
       // put the assembled side_bar_html contents into the side_bar div
          document.getElementById("side_bar").innerHTML += side_bar_html;
          document.getElementById("side_bar").innerHTML += "</td>";
        });
      }

    }
</script>
</head>
  <body onload="load()" onunload="GUnload()">
    <jsp:include page="/WEB-INF/jsp/header2.jsp">
  <jsp:param name="title2" value="Airport Listing" />
</jsp:include>
<table border=1>

      <tr>
        <td>
           <div id="map" style="width: 500px; height: 500px"></div>
        </td>
        <td width = 150 valign="top" style="text-decoration: underline; color: #4444ff;">
           <div id="side_bar"></div>
        </td>
      </tr>
    </table>

  <%@ include file="/WEB-INF/jsp/footer.jsp"%>
  </body>
</html>
