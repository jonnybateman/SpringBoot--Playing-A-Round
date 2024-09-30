/*<![CDATA[*/

// Set height and width of google map container.
var navHeight = document.querySelector("nav").offsetHeight + 3;
//var footerHeight = document.getElementById("footer").offsetHeight + 3;
var screenHeight = document.documentElement.clientHeight;

var mapHeight = screenHeight - navHeight - 3;
var mapContainer = document.getElementById("googleMap");
mapContainer.style.cssText = "margin-top:" + navHeight + "px; height:" + mapHeight + "px; width:100%;";

async function initMap() {
  let map;
  const position = { lat: Number(getSessionStorageValue(getNameLocLat())), lng: Number(getSessionStorageValue(getNameLocLon())) };
  const flag = { lat: Number(getSessionStorageValue(getNamePinLat())), lng: Number(getSessionStorageValue(getNamePinLng())) }

  // Request needed libraries.
  const { Map } = await google.maps.importLibrary("maps");
  const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");
  const { PinElement } = await google.maps.importLibrary("marker");

  // Define the map attributes.
  var mapOptions = {
    center: position,
    zoom: 18,
    mapTypeId: google.maps.MapTypeId.SATELLITE,
    disableDefaultUI: true,
    mapId: "google-map-api-id"
  };

  // Create a map object to be displayed.
  map = new Map(document.getElementById("googleMap"), mapOptions);

  // Place a marker on the map for the players current position.

  const positionOptions = new PinElement({
    scale: 1.5
  });
  
  const positionMarker = new AdvancedMarkerElement({
    map: map,
    position: position,
    title: "I'm here",
    content: positionOptions.element
  });

  // Place a marker on the map for the position of the pin.

  const flagDiv = document.createElement("div");
  flagDiv.innerHTML = '<i class="fa-solid fa-flag fa-3x"></i>';

  const flagOptions = new PinElement({
    glyph: flagDiv,
    glyphColor: "#FFFFFF",
    scale: 2.0
  });

  const flagMarker = new AdvancedMarkerElement({
    map: map,
    position: flag,
    content: flagOptions.element,
    title: "Pin Position"
  });

  // Compute the heading between the player position and flag and set orientation of
  // map to this heading.
  var heading = google.maps.geometry.spherical.computeHeading(positionMarker.position, flagMarker.position);
  map.setHeading(heading);

  // Add a click event listener to the map to add a target marker, used to
  // determine distance to a specific location.
  var targetMarker;
  var markers = [];
  var line;
  var leg1InfoWindow;
  var leg2InfoWindow;
  map.addListener('click', (mapsMouseEvent) => {
    if (targetMarker != null) {
      markers = [];
      targetMarker.setMap(null);
      line.setMap(null);
      leg1InfoWindow.setMap(null);
      leg2InfoWindow.setMap(null);
    }

    // Add a target marker to the map

    const targetDiv = document.createElement("div");
    targetDiv.innerHTML = '<i class="fa-solid fa-crosshairs fa-3x"></i>';

    const targetOptions = new PinElement({
      glyph: targetDiv,
      glyphColor: "#FFFFFF",
      scale: 2.0
    });

    targetMarker = new AdvancedMarkerElement({
      map: map,
      position: mapsMouseEvent.latLng,
      content: targetOptions.element,
      title: "Target Position"
    });

    // Create a polyline between player position, target marker and flag marker.

    markers.push(position);
    markers.push(mapsMouseEvent.latLng);
    markers.push(flag);

    line = new google.maps.Polyline({
      path: markers,
      geodesic: true,
      strokeColor: '#FFFFFF',
      strokeOpacity: 1.0,
      strokeWeight: 10,
      map: map
    });

    // Add labels to the polyline showing distance between markers.

    // Calculate half-way point between player position and target marker.
    var inbetween = google.maps.geometry.spherical.interpolate(position, mapsMouseEvent.latLng, 0.5);
    // Calculate yardage between two markers.
    var yardage = Math.round(calculateYardage(position.lat, position.lng, mapsMouseEvent.latLng.lat(), mapsMouseEvent.latLng.lng()));
    // Add info window to the calculated half-way position.
    var contentHtml = '<div class="iw-font-size">' + yardage + ' yards</div>';
    leg1InfoWindow = new google.maps.InfoWindow({
      map: map,
      position: inbetween,
      headerDisabled: true,
      content: contentHtml
    });

    // Calculate half-way point between target marker and flag.
    inbetween = google.maps.geometry.spherical.interpolate(flag, mapsMouseEvent.latLng, 0.5);
    // Calculate yardage between the two markers.
    yardage = Math.round(calculateYardage(flag.lat, flag.lng, mapsMouseEvent.latLng.lat(), mapsMouseEvent.latLng.lng()));
    // Add info window to the calculated half-way position.
    contentHtml = '<div class="iw-font-size">' + yardage + ' yards</div>';
    leg2InfoWindow = new google.maps.InfoWindow({
      map: map,
      position: inbetween,
      headerDisabled: true,
      content: contentHtml
    })

    map.setHeading(heading);
  });
}

/*
 * Calculate the distance in yards between two sets of GPS coordinates.
 */
function calculateYardage(lat1, long1, lat2, long2) {
  
  if ((lat1 == lat2) && (long1 == long2)) {
    return 0;
  } else {
    var radLat1 = Math.PI * lat1/180;
    var radLat2 = Math.PI * lat2/180;
    var theta = long1 - long2;
    var radTheta = Math.PI * theta/180; 
    var dist = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radTheta);

    if (dist > 1) {
      dist = 1;
    }

    dist = Math.acos(dist);
    dist = dist * 180/Math.PI;
    dist = dist * 60 * 1.1515;
    var yardage = dist * 1760;
    return yardage;
  }

  return null;
}
