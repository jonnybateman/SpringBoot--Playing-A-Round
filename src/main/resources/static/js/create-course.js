/*<![CDATA[*/
// Setup page variables.
var feedback = document.getElementById("feedback");
var cardContainer = document.getElementById("cardContainer");

// Set height and position of create course card.
var navHeight = document.querySelector("nav").offsetHeight;
var footerHeight = document.getElementById("footer").offsetHeight;
var screenHeight = document.documentElement.clientHeight;

var cardHeight = screenHeight - navHeight - footerHeight - (screenHeight * 0.1);
var marginTop = (screenHeight * 0.1) / 2;

cardContainer.style.maxHeight = cardHeight + "px";
cardContainer.style.marginTop = marginTop + "px";

// Create event listeners for form inputs. To validate user input data and
// display feedback should invalid values be entered.
//    focusout : fired when focus moved away from element
//    keypress : fired for any printable keystroke.
const eventTypes = ["focusout","keypress"];

var course = document.getElementById("courseName");
eventTypes.forEach(eventType => course.addEventListener(eventType, event => {
  doesCourseExist(event.target.value, event);
}));

var yardsArray = document.getElementsByClassName("field-yards");
for (var yards of yardsArray) {
  eventTypes.forEach(eventType => yards.addEventListener(eventType, event => {
    yardsListenerTriggered(event.target.value, event);
  }));
}

var parArray = document.getElementsByClassName("field-par");
for (var par of parArray) {
  eventTypes.forEach(eventType => par.addEventListener(eventType, event => {
    parListenerTriggered(event.target.value, event);
  }));
}

var siArray = document.getElementsByClassName("field-si");
for (var si of siArray) {
  eventTypes.forEach(eventType => si.addEventListener(eventType, event => {
    siListenerTriggered(event.target.value, event);
  }));
}

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
 * Check if supplied course name already matches a course in the database.
 */
function doesCourseExist(value, event) {
  if (event.type == "keypress") {
    closeFeedbackError();
  } else if (event.type == "focusout") {
    // Using jQuery .ajax to access contoller so it may query the database
    // and check if course already exists.
    $.ajax({
      type: "GET",
      url: contextPath + "retrieveMatchingCourses",
      contentType: "applcation/json",
      dataType: "json",
      data: {search: value},
      success: function(result) {
        // result contains a JSON array of course names.
        //    [{courseId: id, courseName": "course-name-1"},{courseId: 0, courseName: "course-name-2"},...]
        if (result != null && result.length > 0) {
          feedback.setAttribute("class", "alert alert-danger col-xs-offset-1 col-xs-10");
          feedback.innerHTML = "'" + result[0].courseName + "' already exits.";
        }
      }
    });
  }
}

/*
 * Validate user input for 'yards' input element.
 */
function yardsListenerTriggered(value, event) {
  if (event.type == "keypress") {
    closeFeedbackError();
  } else if (event.type == "focusout" && (value != null && value.length > 0)) {
    if (value < 90 || value > 600) {
      feedback.setAttribute("class", "alert alert-danger col-xs-offset-1 col-xs-10");
      feedback.innerHTML = "Invalid yards value: >90, <600"
    }
  }
}

/*
 * Validate user input for 'par' input element.
 */
function parListenerTriggered(value, event) {
  if (event.type == "keypress") {
    closeFeedbackError();
  } else if (event.type == "focusout" && (value != null && value.length > 0)) {
    if (value < 3 || value > 5) {
      feedback.setAttribute("class", "alert alert-danger col-xs-offset-1 col-xs-10");
      feedback.innerHTML = "Invalid par value entered."
    }
  }
}

/*
 * Validate user input for 'si' input element.
 */
function siListenerTriggered(value, event) {
  if (event.type == "keypress") {
    closeFeedbackError();
  } else if (event.type == "focusout" && (value != null && value.length > 0)) {
    if (value < 1 || value > 18) {
      feedback.setAttribute("class", "alert alert-danger col-xs-offset-1 col-xs-10");
      feedback.innerHTML = "Invalid stroke index: 1 - 18"
    }
  }
}

/*
 * Remove feedback and error elements from DOM.
 */
function closeFeedbackError() {
  if (feedback.innerHTML.length > 0) {
    feedback.setAttribute("class", "");
    feedback.innerHTML = "";
  }

  var error = document.getElementById("error");
  if (error != null) {
    error.remove();
  }
}
/*]]>*/