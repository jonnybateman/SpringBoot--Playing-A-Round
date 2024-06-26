/*<![CDATA[*/
  var numberOfHoles = 18;

  // Set margin height for first element below nav bar.
  var navHeight = document.querySelector("nav").offsetHeight;
  var divContainer = document.getElementById("container");
  divContainer.style.cssText = "margin-top: " + (navHeight + 50) + "px;";

  // Compare user's team hole scores against every other team's hole scores.
  for (let i=0; i<matchplayTeamsScoresList.length; i++) {
    let matchplayScore = 0;
    let teamRowElements = document.getElementById(matchplayTeamsScoresList[i].name).children;

    // For the current other team, compare each hole score against the hole scores
    // of the user's team.
    for (let h = 1; h <= numberOfHoles; h++) {
      let userTeamHoleScore = eval(`matchplayTeamScores.scoreH${h}`);
      let otherTeamHoleScore = eval(`matchplayTeamsScoresList[${i}].scoreH${h}`);

      // Only compare scores if both teams have logged a score for the current hole.
      if (userTeamHoleScore > 0 && otherTeamHoleScore > 0) {
        matchplayScore += compareScores(userTeamHoleScore, otherTeamHoleScore);

        // If the matchplay score exceeds the number of holes remaining then the game against
        // the current other team is over, display the result and continue with next team.
        if (h != numberOfHoles && Math.abs(matchplayScore) > (numberOfHoles - h)) {
          if (matchplayScore < 0) {
            teamRowElements.item(0).innerHTML = matchplayTeamsScoresList[i].name;
            teamRowElements.item(1).innerHTML = Math.abs(matchplayScore) + "&" + (numberOfHoles - h);
            teamRowElements.item(2).innerHTML = matchplayTeamScores.name;
          } else if (matchplayScore > 0) {
            teamRowElements.item(0).innerHTML = matchplayTeamScores.name;
            teamRowElements.item(1).innerHTML = Math.abs(matchplayScore) + "&" + (numberOfHoles - h);
            teamRowElements.item(2).innerHTML = matchplayTeamsScoresList[i].name;
          }
          break;
        }
      }
      // If we have reached the last hole, dislay result against current other team.
      if (h == numberOfHoles) {
        teamRowElements.item(0).innerHTML = matchplayTeamScores.name;
        teamRowElements.item(2).innerHTML = matchplayTeamsScoresList[i].name;

        if (matchplayScore > 0) {
          teamRowElements.item(1).innerHTML = matchplayScore + "up";
        } else if (matchplayScore == 0) {
          teamRowElements.item(1).innerHTML = "AS";
        } else {
          teamRowElements.item(1).innerHTML = matchplayScore.toString().substring(1) + "dn";
        }
      }
    }
  }

  function compareScores(userTeamScore, otherTeamScore) {
    if (userTeamScore > otherTeamScore) {
      return -1;
    } else if (userTeamScore == otherTeamScore) {
      return 0;
    } else {
      return 1;
    }
  }
/*]]>*/