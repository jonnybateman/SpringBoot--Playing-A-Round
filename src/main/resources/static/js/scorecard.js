/*<![CDATA[*/

// A global function that is called whenever an ajax request is made. Here we
// use it to analyse the returned status code from the server.
$(document).ajaxError(function(event, jqxhr, settings, thrownError) {
  // If code 404 is returned the user's session on the server has timed out,
  // log the user out.
  if (jqxhr.status == 404) {
    window.location.href = contextPath + "logout";
  }
});

var scoreChanged = false;

// Get the document's root element so that css variables can be utilized.
var root = document.documentElement;
var styles = getComputedStyle(root);

// Set teamId as a session storage value.
setSessionStorageValue(getNameTeamId(), teamId);

// Set gameId as a session storage value.
setSessionStorageValue(getNameGameId(), gameId);

// Set the session storage value for the team name.
setSessionStorageValue(getNameTeam(), teamName);

// Set the session storage value for drive distance if not already set.
var driveDistance = getSessionStorageValue(getNameDriveDistance());
if (driveDistance == null || driveDistance == "") {
  setSessionStorageValue(getNameDriveDistance(), 0);
}

// Calculate the Out and In score totals for the hole scores and assign to the
// applicable input fields in the web view.
calculateOutInTotals();

// Set margin height for first element below nav bar.
var navHeight = document.querySelector("nav").offsetHeight;
var divScorecardCourse = document.getElementById("divScorecardCourse");
divScorecardCourse.style.cssText = "margin-top: " + (navHeight + 50) + "px;";

// Set the session storage value for default hole on page load if not already set.
if (getSessionStorageValue(getNameHoleNo()) == null) {
  setSessionStorageValue(getNameHoleNo(), 1);
  setSessionStorageValue(getNameHoleId(), document.getElementById("hole1").dataset.holeId);
                // element attribute data-hole-id  => holeId
  setSessionStorageValue(getNamePinLat(), document.getElementById("hole1").dataset.locLat);
  setSessionStorageValue(getNamePinLng(), document.getElementById("hole1").dataset.locLong);
}

// Get the element to store hole number as a data attribute.
var hole = document.getElementById("hole");
hole.setAttribute("data-hole-no", getSessionStorageValue(getNameHoleNo()));

// Highlight applicable hole as the current hole, deduced from session storage value.
document.getElementById("hole" + getSessionStorageValue(getNameHoleNo())).style.backgroundColor =
    styles.getPropertyValue('--table-body-bg-color');

// Events to occur prior to redirecting to a different URL.
window.addEventListener("beforeunload", function() {
    // Before redirect, post score for current hole to the database if it has changed.
    if (scoreChanged) {
      let holeNo = hole.dataset.holeNo;
      let holeScore = document.getElementById("inputHole" + holeNo).value;

      if (holeScore != "" && holeScore > 0 && scoreChanged) {
        // Send hole score to the app controller.
        $.ajax({
            type: "POST",
            url: contextPath + "setHoleScore",
            dataType: "json",
            data: { "gameId": gameId,
                    "teamId": teamId,
                    "playerId": playerId,
                    "holeId": holeNo,
                    "score": holeScore
                  }
        });
      }
    }
  }
);

// Setup event listeners for 'next' and 'previous' hole buttons.
var prevHoleButton = document.getElementById("prevHole");
prevHoleButton.addEventListener("click", function() {
  let holeNo = parseInt(getSessionStorageValue(getNameHoleNo()));

  // If the score has changed for the previously selected hole post the score to the
  // database.
  if (scoreChanged) {
    scoreChanged = false;
    postHoleScore(holeNo);
  }
  
  if (holeNo > 1) {
    // Highlight the new current hole and set value of hole session storage key value
    // pair accordingly.
    let currHole = document.getElementById("hole" + (holeNo - 1));
    setSessionStorageValue(getNameHoleNo(), holeNo - 1);
    hole.setAttribute("data-hole-no", holeNo - 1);

    currHole.style.backgroundColor = styles.getPropertyValue('--table-body-bg-color');
    document.getElementById("hole" + holeNo).style.backgroundColor =
        styles.getPropertyValue('--table-header-bg-color');
    document.getElementById("range").innerHTML = "- - - yards";

    // If we do not have pin location data for hole, query database to see if it is now available.
    if (currHole.dataset.locLat == null) {
      $.ajax({
        type: "GET",
        url: contextPath + "getPinLocationData",
        dataType: "json",
        data: { "holeId": currHole.dataset.holeId },
        success: function (result) {
          if (result.locLat != null) {
            currHole.dataset.locLat = result.locLat;
            currHole.dataset.locLong = result.locLong;
          }
        }
      });
    }

    // Update the session storage pin location values for the new hole.
    setSessionStorageValue(getNamePinLat(), currHole.dataset.locLat);
    setSessionStorageValue(getNamePinLng(), currHole.dataset.locLong);
  }
});

var nextHoleButton = document.getElementById("nextHole");
nextHoleButton.addEventListener("click", function() {
  let holeNo = parseInt(getSessionStorageValue(getNameHoleNo()));

  // If the score has changed for the previously selected hole post the score to the
  // database.
  if (scoreChanged) {
    scoreChanged = false;
    postHoleScore(holeNo);
  }
  
  if (holeNo < 18) {
    // Highlight the new current hole and set value of hole number cookie accordingly.
    let currHole = document.getElementById("hole" + (holeNo + 1));
    setSessionStorageValue(getNameHoleNo(), holeNo + 1);
    hole.setAttribute("data-hole-no", holeNo + 1);

    currHole.style.backgroundColor = styles.getPropertyValue('--table-body-bg-color');
    document.getElementById("hole" + holeNo).style.backgroundColor =
        styles.getPropertyValue('--table-header-bg-color');
    document.getElementById("range").innerHTML = "- - - yards";

    // If we do not have pin location data for hole, query database to see if it is now available.
    if (currHole.dataset.locLat == null) {
      $.ajax({
        type: "GET",
        url: contextPath + "getPinLocationData",
        dataType: "json",
        data: { "holeId": currHole.dataset.holeId },
        success: function (result) {
          if (result.locLat != null) {
            currHole.dataset.locLat = result.locLat;
            currHole.dataset.locLong = result.locLong;
          }
        }
      });
    }

    // Update the session storage pin location values for the new hole.
    setSessionStorageValue(getNamePinLat(), currHole.dataset.locLat);
    setSessionStorageValue(getNamePinLng(), currHole.dataset.locLong);
  }
});

// Create click handler for setPinLocation icon.
var setPinLocation = document.getElementById("setPinLocation");
setPinLocation.addEventListener("click", function () {
  if (confirm("Do you want to set pin location?")) {
    var hole = document.getElementById("hole" + getSessionStorageValue(getNameHoleNo()));
    // If hole has no location data for pin.
    if (hole.dataset.locLat == null) {
      // Get the current GPS coordinates.
      if (navigator.geolocation) {
        // Retrieve the current position of the device and pass the coordinates to the
        // app controller.
        $.ajax({
          type: "post",
          url: contextPath + "addLocationToHole",
          dataType: "json",
          data: {
            "holeId": hole.dataset.holeId,
            "lat": getSessionStorageValue(getNameLocLat),
            "lon": getSessionStorageValue(getNameLocLon)
          },
          success: function (result) {
            if (result == true) {
              alert("Pin location data saved.")
            } else {
              alert("Pin location data could not be saved.");
            }
          }
        });
      } else {
        alert("Sorry, geolocation not supported.");
      }
    }
  }
});

// Create click listener for measureDrive icon. A long press of >500ms will cancel
// the drive measurement action.
var measureDriveFlag = false;
var driveLocLat;
var driveLocLong;
var measureDrive = document.getElementById("measureDrive");
var touchStartTime;
measureDrive.addEventListener("touchstart", function(e) {
  touchStartTime = Date.now();
});
measureDrive.addEventListener("touchend", function () {
  let touchTimeLength = Date.now() - touchStartTime;
  if (touchTimeLength < 500) {
    if (!measureDriveFlag) {
      if (confirm("Do you want to measure your drive?\nLong press ball location button to cancel.")) {
        // Get the GPS location for drive position.
        if (navigator.geolocation) {
          driveLocLat = Number(getSessionStorageValue(getNameLocLat()));
          driveLocLong = Number(getSessionStorageValue(getNameLocLon()));
          measureDrive.classList.remove("fa-golf-ball-tee");
          measureDrive.classList.add("fa-arrows-to-circle");
          measureDriveFlag = true;
        } else {
          document.getElementById("range").innerHTML = "Sorry, geolocation not supported";
        }
      }
    } else {
      // Get the GPS location for where the ball ended up and calculate distance.
      if (navigator.geolocation) {
        // Calculate drive distance
        let distance = Math.round(calculateYardage(Number(getSessionStorageValue(getNameLocLat())),
                                                  Number(getSessionStorageValue(getNameLocLon())),
                                                  driveLocLat,
                                                  driveLocLong));

        // Display the distance driven on scorecard.
        document.getElementById("range").innerHTML = "Drive: " + distance + " yards";

        if (distance > getSessionStorageValue(getNameDriveDistance())) {
          // Post the distance to the database. If the distance is longer than the
          // current longest drive for the player then the distance value will be
          // updated in the database.
          $.ajax({
            type: "post",
            url: contextPath + "setLongestDrive",
            dataType: "json",
            data: {
              "playerId": playerId,
              "distance": distance
            },
            success: function (result) {
              setSessionStorageValue(getNameDriveDistance(), distance);
            }
          });
        }
        measureDrive.classList.remove("fa-arrows-to-circle");
        measureDrive.classList.add("fa-golf-ball-tee");
        measureDriveFlag = false;
      } else {
        document.getElementById("range").innerHTML = "Sorry, geolocation not supported";
      }
    }
  } else {
    measureDrive.classList.remove("fa-arrows-to-circle");
    measureDrive.classList.add("fa-golf-ball-tee");
    measureDriveFlag = false;
  }
});

/*
 * If pin location data is available display the distance to pin in yards.
 */
var rangeToPin = document.getElementById("rangeToPin");
rangeToPin.addEventListener("click", function() {
  let pinLocLat = document.getElementById("hole" + getSessionStorageValue(getNameHoleNo())).dataset.locLat;
  let pinLocLong = document.getElementById("hole" + getSessionStorageValue(getNameHoleNo())).dataset.locLong;

  // Calculate the the distance to pin if we have pin location data.
  if (pinLocLat != null) {
    // If GeoLocation is available calculate distance to the pin.
    if (navigator.geolocation) {
      let yardage = calculateYardage(Number(getSessionStorageValue(getNameLocLat())),
                                    Number(getSessionStorageValue(getNameLocLon())),
                                    Number(pinLocLat),
                                    Number(pinLocLong));

      if (yardage != null) {
        document.getElementById("range").innerHTML = Math.round(yardage) + " yards to pin";
      } else {
        document.getElementById("range").innerHTML = "Range data not available";
      }
    } else {
      document.getElementById("range").innerHTML = "Sorry, geolocation not supported";
    }
  } else {
    document.getElementById("range").innerHTML = "Range data not available";
  }
});

// Setup event listener to reduce score for the current hole by 1.
var strokeMinus = document.getElementById("deleteStroke");
strokeMinus.addEventListener("click", function() {
  let scoreInputValue = document.getElementById("inputHole" + getSessionStorageValue(getNameHoleNo())).value;
  scoreInputValue = Number(scoreInputValue == null ? 0 : scoreInputValue);
  if (scoreInputValue > 0) {
    document.getElementById("inputHole" + getSessionStorageValue(getNameHoleNo())).value = scoreInputValue - 1;
    scoreChanged = true;
  }
});

// Setup event listener to increase score for current hole by 1.
var strokeMinus = document.getElementById("addStroke");
strokeMinus.addEventListener("click", function() {
  let scoreInputValue = document.getElementById("inputHole" + getSessionStorageValue(getNameHoleNo())).value;
  scoreInputValue = Number(scoreInputValue == null ? 0 : scoreInputValue);
  document.getElementById("inputHole" + getSessionStorageValue(getNameHoleNo())).value = scoreInputValue + 1;
  scoreChanged = true;
});

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

/*
 * Calculate the Out and In score totals for the player.
 */
function calculateOutInTotals() {

  let scoreOut = 0;
  let scoreIn = 0;

  for (let i=1; i<=9; i++) {
    let score = Number(eval("document.getElementById('inputHole" + i + "').value"));
    scoreOut += (score === null ? 0 : score);
  }
  document.getElementById('inputOut').value = scoreOut;

  for (let i=10; i<=18; i++) {
    let score = Number(eval("document.getElementById('inputHole" + i + "').value"));
    scoreIn += (score === null ? 0 : score);
  }
  document.getElementById('inputIn').value = scoreIn;
}

/*
 * When a hole input has focus set the variable currentHoleScore to the value of the
 * input. The variable can then be compared to the new value entered during the
 * onblur event. If the values are the same no need to send to the app controller.
 */
function getCurrentHoleScore(holeNo) {

  // Get the score set for the hole.
  let holeInput = document.getElementById("inputHole" + (holeNo));
  oldHoleScore = holeInput.value == '' ? 0 : holeInput.value;
}

/*
 * When a hole input element loses focus send the score to the app controller
 * so that it can be posted to the database.
 */
function postHoleScore(holeNo) {

  // Get the score set for the hole.
  let holeScore = document.getElementById("inputHole" + (holeNo)).value;

  if (holeScore != "" && holeScore > 0) {
    // Calculate new Out and In totals
    calculateOutInTotals();

    // Send hole score to the app controller.
    $.ajax({
        type: "POST",
        url: contextPath + "setHoleScore",
        dataType: "json",
        data: { "gameId": gameId,
                "teamId": teamId,
                "playerId": playerId,
                "holeId": holeNo,
                "score": holeScore
              }
    });
  }

  if (holeScore == 0) {
    document.getElementById("inputHole" + (holeNo)).value = null;
  }
}

/*
 * Function to highlight button onclick event for set period of time. Used when
 * 'double-tap-zoom' has been disabled for the button via css styling. Using the
 * normal method of css styling to highlight a button click would leave it highlighted.
 */
function onClickHighlight(element) {
  element.style.color = styles.getPropertyValue('--btn-highlight-color');
  setTimeout(function() {
      element.style.color = styles.getPropertyValue('--btn-icon-color');
    }, 200);
}
/*]]>*/