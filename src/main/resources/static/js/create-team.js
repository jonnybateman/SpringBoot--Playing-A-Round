/*<![CDATA[*/
// Apply username and handicap cookie values to the appropriate elements in
// the navigation panel.
document.getElementById("username").innerHTML = "User: " + getSessionStorageValue(getNameUser());
let handicap = getSessionStorageValue(getNameHandicap());
if (handicap == 0.0) {
  document.getElementById("handicap").innerHTML = "Handicap: 0.0";
} else {
  document.getElementById("handicap").innerHTML =
  "Handicap: " + (handicap == 'null' ? '' : handicap);
}

/*
 * If the gameId input field is empty then do not submit the form.
 */
function isTeamNameEmpty() {
  var teamName = document.getElementById("teamName").value;
  if (!teamName) { // If team name is null or empty...
    return false;
  }
  return true;
}
/*]]>*/