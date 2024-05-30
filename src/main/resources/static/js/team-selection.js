/*<![CDATA[*/
  var navHeight = document.querySelector("nav").offsetHeight;
  var footerHeight = document.getElementById("footer").offsetHeight;
  var screenHeight = document.documentElement.clientHeight;

  // Apply necessary margins to info text and get height of instruction container including margins.
  var instructionContainer = document.getElementById("instructionContainer");
  instructionContainer.style.cssText = "margin-top: " + (navHeight + 50) + "px;" +
      "margin-bottom: 50px;";
  var instructionHeight = instructionContainer.offsetHeight + 100; // 100 accounts for top/bottom margins

  // Set height of card container div.
  var teamContainerHeight = screenHeight - navHeight - footerHeight - instructionHeight;
  var teamContainer = document.getElementById("teamContainer");
  teamContainer.style.cssText = 
      "height: " + teamContainerHeight + "px;";
  
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

  // Create touch event listeners for team cards.
  var teamsArray = document.getElementsByClassName("card");
  for (var team of teamsArray) {
    // If team already has two players then do not create event listener for it.
    var playersArray = team.getElementsByClassName("card-text");
    if (playersArray.length < 2) {
      team.addEventListener("click", function () {
        // Get the team id and use JQuery to call controller and add user to team.
        var teamId = this.dataset.value;

        // Create cookie with the team id indicating that the player has joined a game.
        setSessionStorageValue(getNameTeamId(), teamId);

        // Send team id to controller to add user to the specified team.
        $.ajax({
          type: "GET",
          url: contextPath + "addPlayerToGame",
          dataType: "json",
          data: {"teamId": teamId, "gameId": instructionContainer.dataset.value},
          success: function(result) {
            if (result == true) {
              window.location.href = contextPath + "score-card?gameId=" + instructionContainer.dataset.value +
                  "&teamId=" + teamId;
            }
          }  
        });
      });
    }
  }
/*]]>*/