/*<![CDATA[*/

let handicap = getSessionStorageValue(getNameHandicap());
document.getElementById("username").innerHTML = "User: " + getSessionStorageValue(getNameUser());
document.getElementById("handicap").innerHTML =
    "Handicap: " + (handicap == 'null' ? '' : handicap);

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
  }
  
  return true;
}
/*]]>*/