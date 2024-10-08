/*<![CDATA[*/
const expires = 8*60*60*1000;  // Cookie will expire in 8 hours.
const nameUser = "PlayingAroundUser";
const nameHandicap = "PlayinAroundHandicap";
const nameGameId = "PlayingAroundGameId";
const nameTeamId = "PlayingAroundTeamId";
const nameTeam = "PlayingAroundTeamName";
const nameHoleNo = "PlayingAroundHoleNo";
const nameHoleId = "PlayingAroundHoleId";
const nameDriveDistance = "PlayingAroundDriveDistance";
const nameLocLat = "PlayingAroundLocLat";
const nameLocLng = "PlayingAroundLocLon";
const namePinLat = "PlayingAroundPinLat";
const namePinLng = "PlayingAroundPinLng";

function getNameUser() {
  return nameUser;
}

function getNameHandicap() {
  return nameHandicap;
}

function getNameGameId() {
  return nameGameId;
}

function getNameTeamId() {
  return nameTeamId;
}

function getNameTeam() {
  return nameTeam;
}

function getNameHoleNo() {
  return nameHoleNo;
}

function getNameHoleId() {
  return nameHoleId;
}

function getNameDriveDistance() {
  return nameDriveDistance;
}

function getNameLocLat() {
  return nameLocLat;
}

function getNameLocLon() {
  return nameLocLng;
}

function getNamePinLat() {
  return namePinLat;
}

function getNamePinLng() {
  return namePinLng
}

/*
 * Set the specified cookie with the supplied value.
 */
function setCookie(key, value) {
  const dateTime = new Date();
  dateTime.setTime(dateTime.getTime() + expires);
  let expiresAttr = "expires=" + dateTime.toUTCString;
  document.cookie = key + "=" + value + ";" + expiresAttr + ";path=/";
}

/*
 * Set a session storage key value pair.
 */
function setSessionStorageValue(key, value) {
  sessionStorage.setItem(key, value);
}

/*
 * Method to retrieve the value for a particular cookie in the cookies document string.
 */
function getCookie(key) {
  
  let cookieKey = key + "=";
  let decodedCookieString = decodeURIComponent(document.cookie);
  let cookieArray = decodedCookieString.split(';');

  for (let i=0; i<cookieArray.length; i++) {
    let currentCookie = cookieArray[i];
    if (currentCookie.charAt(0) == ' ') { // remove space at beginning if cookie string if there is one.
      currentCookie = currentCookie.substring(1);
    }
    if (currentCookie.indexOf(cookieKey) == 0) {
      return currentCookie.substring(cookieKey.length, currentCookie.length);
    }
  }

  return "";
}

/*
 * Return the value for a particular session storage key value pair.
 */
function getSessionStorageValue(key) {
  return sessionStorage.getItem(key);
}

/*
 * Delete a specific cookie from the document.cookie string.
 */
function deleteCookie(key) {
  if (getCookie(key) != "") {
    document.cookie = key + "=; Path=/; expires=Thu, 01 Jan 1970 00:00:01 UTC;";
  }
}

/*
 * Delete a specific session storage key value pair.
 */
function deleteSessionStorageValue(key) {
  sessionStorage.removeItem(key);
}

/*
 * Delete all cookies affiliated with this application.
 */
function deleteAllCookies() {
  var cookiePairs = document.cookie.split(";"); 
  for (let i=0; i<cookiePairs.length; i++) {
    let cookiePair = cookiePairs[i].split("=");
    document.cookie = cookiePair[0].trim() + "=; Path=/; expires=Thu, 01 Jan 1970 00:00:01 UTC;";
  }
}

/*
 * Delete all the session storage key value pairs for the Playing-A-Round application.
 */
function deleteAllSessionStorageValues() {
  sessionStorage.clear();
}
/*]]>*/