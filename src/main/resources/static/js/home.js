/*<![CDATA[*/
// Set the username session storage value.
setSessionStorageValue(getNameUser(), user);

// Set the handicap cookie for user.
setSessionStorageValue(getNameHandicap(), handicap);

document.getElementById("username").innerHTML = "User: " + user;
document.getElementById("handicap").innerHTML = "Handicap: " + (handicap == null ? "" : handicap);
/*]]>*/