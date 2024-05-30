/*<![CDATA[*/
// Get value of cTeamId cookie to see if user has already joined a game.
let gameIdValue = getSessionStorageValue(getNameGameId());

// Depending on cookie value redirect to applicable page.
if (gameIdValue == null || gameIdValue == "") {
  // Navigate to home web view.
  window.location.href = contextPath + "home";
} else {
  let relativeUrl = contextPath + "score-card";
  let objectUrl = new URL(relativeUrl, document.location);
  // Add game id and team id parameters to the URL.
  objectUrl.searchParams.append("gameId", gameIdValue);
  objectUrl.searchParams.append("teamId", getSessionStorageValue(getNameTeamId()));
  // Navigate to score-card web view.
  window.location.href = objectUrl;
}
/*]]>*/