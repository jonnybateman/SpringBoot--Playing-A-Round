/*<![CDATA[*/

let handicap = getSessionStorageValue(getNameHandicap());
document.getElementById("username").innerHTML = "User: " + getSessionStorageValue(getNameUser());
document.getElementById("handicap").innerHTML =
    "Handicap: " + (handicap == 'null' ? '' : handicap);

// Setup an event listener for when the document content has been loaded. It will
// create a GeoLocation WatchPosition object to return the device's current position
// when a change in position has been detected.
var watchId = null;
const geolocationOptions = {
  enableHighAccuracy: true,
  maximumAge: 0
};
document.addEventListener("DOMContentLoaded", function() {
    if (navigator.geolocation) {
      watchId = navigator.geolocation.watchPosition(
                    onGeoSuccessCallback, showError, geolocationOptions);
    } else {
      alert("Sorry, GPS location not available.")
    }
  }
);

// Events to occur prior to redirecting to a different URL.
window.addEventListener("beforeunload", function() {
    // If a Geolocation WatchPosition object has been created, deactivate it on page unload.
    if (watchId != null) {
      navigator.geolocation.clearWatch(watchId);
    }
  }
);

/*
 * Callback method when geolocation.watchPosition has successfully detected a change
 * in the device's location.
 */
function onGeoSuccessCallback(position) {
  setSessionStorageValue(getNameLocLat(), position.coords.latitude);
  setSessionStorageValue(getNameLocLon(), position.coords.longitude);
}

/*
 * Callback method to show any errors raised when attempting to use geo location.
 */
function showError(error) {
  switch(error.code) {
    case error.PERMISSION_DENIED:
      alert("User denied the request for Geolocation.");
      break;
    case error.POSITION_UNAVAILABLE:
      alert("Location information is unavailable.");
      break;
    case error.TIMEOUT:
      alert("The request to get user location timed out.");
      break;
    case error.UNKNOWN_ERROR:
      alert("An unknown error occurred.");
      break;
  }
  navigator.geolocation.clearWatch(watchId);
}

function endGame() {
  let gameId = getSessionStorageValue(getNameGameId());
  let teamId = getSessionStorageValue(getNameTeamId());

  // Delete cookies associated with the game.
  deleteSessionStorageValue(getNameGameId());
  deleteSessionStorageValue(getNameTeamId());
  deleteSessionStorageValue(getNameTeam());
  deleteSessionStorageValue(getNameHoleNo());
  deleteSessionStorageValue(getNameHoleId());
  deleteSessionStorageValue(getNameDriveDistance());

  let objectUrl = new URL(contextPath + "calculateHandicapIndex", document.location);
  if (gameId == "") {
    objectUrl.searchParams.append("gameId", 0);
    objectUrl.searchParams.append("teamId", 0);
  } else {
    objectUrl.searchParams.append("gameId", gameId);
    objectUrl.searchParams.append("teamId", teamId);
  }
  document.getElementById("endGame").href = objectUrl;

  return true;
}

function hasJoinedGame(link) {
  if (getSessionStorageValue(getNameGameId()) == "") {
    return false;
  }

  let objectUrl;
  switch (link) {
    case "scorecard":
      objectUrl = new URL(contextPath + "score-card", document.location);
      objectUrl.searchParams.append("gameId", getSessionStorageValue(getNameGameId()));
      objectUrl.searchParams.append("teamId", getSessionStorageValue(getNameTeamId()));
      document.getElementById("scorecard").href = objectUrl;
    case "strokeplay":
      objectUrl = new URL(contextPath + "strokeplay", document.location);
      objectUrl.searchParams.append("gameId", getSessionStorageValue(getNameGameId()));
      document.getElementById("strokeplay").href = objectUrl;
    case "matchplay":
      objectUrl = new URL(contextPath + "matchplay", document.location);
      objectUrl.searchParams.append("gameId", getSessionStorageValue(getNameGameId()));
      objectUrl.searchParams.append("teamId", getSessionStorageValue(getNameTeamId()));
      objectUrl.searchParams.append("teamName", getSessionStorageValue(getNameTeam()));
      document.getElementById("matchplay").href = objectUrl;
    case "daytona":
      objectUrl = new URL(contextPath + "daytona", document.location);
      objectUrl.searchParams.append("gameId", getSessionStorageValue(getNameGameId()));
      document.getElementById("daytona").href = objectUrl;
    case "stableford":
      objectUrl = new URL(contextPath + "stableford", document.location);
      objectUrl.searchParams.append("gameId", getSessionStorageValue(getNameGameId()));
      document.getElementById("stableford").href = objectUrl;
    case "drive-distance":
      objectUrl = new URL(contextPath + "drive-distance", document.location);
      objectUrl.searchParams.append("gameId", getSessionStorageValue(getNameGameId()));
      document.getElementById("driveDistance").href = objectUrl;
    case "ariel":
      objectUrl = new URL(contextPath + "ariel", document.location);
      document.getElementById("ariel-view").href = objectUrl;
  }
  
  return true;
}
/*]]>*/