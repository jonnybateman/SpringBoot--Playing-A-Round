/*<![CDATA[*/
var divGameCard = document.getElementById("selectGameCard");
  var divFeedback = document.getElementById("feedback");
  var inputGame = document.getElementById("gameName");
  var inputGameId = document.getElementById("gameId");
  var cardSelectButtonHeight = document.getElementById("selectButton").offsetHeight;
  var gameForm = document.getElementById("gameForm");
  var divGameList = document.createElement("DIV");
  divGameList.setAttribute("id", "game-list");
  // Apply game drop down div to it's parent inputGame.
  inputGame.parentNode.append(divGameList);

  // Apply username and handicap session storage values to the appropriate elements in
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
   * Create an input event listener for the form text field 'gameName'.
   * Each time there is a change to the input of 'gameName' text field
   * check if the length of the string in the text field is >= 3. If
   * so call retrieveGames() to return a list of games based on the
   * supplied input string.
   */
  inputGame.addEventListener("input", event => {
    // If the form's error alert is displayed remove it.
    let divError = document.getElementById('error');
    if (divError != null) {
      divError.parentElement.remove(divError);
    }
    
    let searchString = document.getElementById('gameName').value;
  
    if (searchString.length == 3 && !divGameList.hasChildNodes()) {
      retrieveGames(searchString);
    } else if (searchString.length < 3 && divGameList.hasChildNodes) {
      closeGameList();
    }
  });

  /*
   * Generate a HTTP request to retrieve a list of courses where the
   * name of those courses begin with the supplied string from the
   * 'course' input field.
   */
  function retrieveGames(searchString) {
    // Use JQuery Ajax to make call to controller.
    $.ajax({
      type: "GET",
      url: contextPath + "retrieveMatchingGames",
      contentType: "application/json",
      dataType: "json",
      data: {search: searchString},
      success: function(result) {
        // result contains a JSON array of game names.
        //    [{gameId: "game-id-1", gameName: "game-name-1"},
        //          {gameId: "game-id-2", gameName: "game-name-2"},...]
        divFeedback.setAttribute("class", "alert alert-danger col-xs-offset-1 col-xs-10");

        if (result != null && result.length > 0) {
          divGameList.setAttribute("class", "dropdown-list-items");

          // Add game names to divGameList drop down list.
          for (var i=0; i<result.length; i++) {
            var divGame = document.createElement("DIV");
            //divGame.setAttribute("class", "course-item");
            divGame.innerHTML = "<strong>" + result[i].gameName + "</strong>";
            divGame.innerHTML += "<input type='hidden' value='" + result[i].gameName + "'>";
            divGame.innerHTML += "<input type='hidden' value='" + result[i].gameId + "'>";

            // Create click event listener for each game retrieved. When game selected from list
            // add the values to the select game form inputs.
            divGame.addEventListener("click", function() {
              // Game selected set game name and id.
              inputGame.value = this.getElementsByTagName("input")[0].value;
              inputGameId.value = this.getElementsByTagName("input")[1].value;
              closeGameList();
            });
            divGameList.appendChild(divGame);
          }

          // Set message in feedback div telling user to select applicable list from drop down.
          divFeedback.innerHTML = "Select game...";

          // Adjust the height of the card to accomodate the dropdown list.
          var gameListHeight = divGameList.offsetHeight;
          divGameCard.setAttribute("style", "height: " +
              (divGameCard.offsetHeight + gameListHeight - cardSelectButtonHeight) + "px");
        } else {
          divFeedback.innerHTML = "No matching games.";
        }
      }
    });
  }

  /*
   * Remove course list drop down list by removing child nodes of divCourseList.
   */
  function closeGameList() {
    if (divGameList.hasChildNodes) {
      // Remove all child elements of divCourseList.
      divGameList.innerHTML = "";
      divGameList.setAttribute("class", "");

      // Remove feedback div.
      divFeedback.innerHTML = "";
      divFeedback.setAttribute("class", "");

      // Reduce height of card.
      //divGameCard.setAttribute("style", "height: " + divGameCardInitialHeight + "px");
      divGameCard.setAttribute("style", "height: auto");
    }
  }

  /*
   * If the gameId input field is empty then do not submit the form.
   */
  function isGameIdEmpty() {
    let gameId = document.getElementById("gameId").value;
    if (gameId == "" || gameId == 0 || gameId == null) {
      // Game name may have been specified in full by user and not selected from drop
      // down list. See if game can be retrieved from user input.
      let searchString = document.getElementById("gameName").value;
      console.log("searchString:" + searchString + ";");
      if (searchString != null && searchString != "") {
        $.ajax({
          type: "GET",
          url: contextPath + "retrieveGame",
          contentType: "application/json",
          dataType: "json",
          data: { gameName: searchString },
          success: function (result) {
            // Result is not null so success.
            // A single game was found with matching game name. Set the game id so it will
            // be submitted with the form within the gameData object.
            inputGameId.value = result.gameId;
            gameForm.submit();
          },
          error: function (result) {
            // Result is null.
            divFeedback.setAttribute("class", "alert alert-danger col-xs-offset-1 col-xs-10");
            divFeedback.innerHTML = "No matching game...";
          }
        });
      }
    } else {
      gameForm.submit();
    }
  }
/*]]>*/