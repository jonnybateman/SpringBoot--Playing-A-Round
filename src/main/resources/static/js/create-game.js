/*<![CDATA[*/
  var divFeedback = document.getElementById("feedback");
  var inputCourse = document.getElementById("courseName");
  var inputGame = document.getElementById("gameName");
  var divCourseList = document.createElement("DIV");
  divCourseList.setAttribute("id", "course-list");
  // Apply course drop down div to it's parent inputCourse.
  inputCourse.parentNode.append(divCourseList);

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
   * Create an input event listener for the form text field 'course'.
   * Each time there is a change to the input of 'course' text field
   * check if the length of the string in the text field is >= 3. If
   * so call retrieveCourses() to return a list of courses based on the
   * supplied input string.
   */
  inputCourse.addEventListener("input", event => {
    // If the form's error alert is displayed remove it.
    var divError = document.getElementById('error');
    if (divError != null) {
      divError.parentElement.remove(divError);
    }

    var searchString = document.getElementById('courseName').value;
    if (searchString.length == 3 && !divCourseList.hasChildNodes()) {
      retrieveCourses(searchString);
    } else if (searchString.length < 3 && divCourseList.hasChildNodes) {
      closeCourseList();
    }
  });

  /*
   * Generate a HTTP request to retrieve a list of courses where the
   * name of those courses begin with the supplied string from the
   * 'course' input field.
   */
  function retrieveCourses(searchString) {
    // Use JQuery Ajax to make call to controller.
    $.ajax({
      type: "GET",
      url: contextPath + "retrieveMatchingCourses",
      contentType: "applcation/json",
      dataType: "json",
      data: {search: searchString},
      success: function(result) {
        // result contains a JSON array of course names.
        //    [{courseId: id, courseName": "course-name-1"},{courseId: id, courseName: "course-name-2"},...]

        divFeedback.setAttribute("class", "alert alert-danger col-xs-offset-1 col-xs-10");

        if (result != null && result.length > 0) {
          divCourseList.setAttribute("class", "dropdown-list-items");

          // Add course names to divCourseList drop down list.
          for (var i=0; i<result.length; i++) {
            var divCourse = document.createElement("DIV");
            divCourse.setAttribute("class", "course-item");
            divCourse.innerHTML = "<strong>" + result[i].courseName + "</strong>";
            divCourse.innerHTML += "<input type='hidden' value='" + result[i].courseName + "'>";
            divCourse.addEventListener("click", function() {
              inputCourse.value = this.getElementsByTagName("input")[0].value;
              closeCourseList();
            });
            divCourseList.appendChild(divCourse);
          }

          // Set message in feedback div telling user to select applicable list from drop down.
          divFeedback.innerHTML = "Select course...";
        } else {
          divFeedback.innerHTML = "No matching courses in database.";
        }
      }
    });
  }

  /*
   * Remove course list drop down list by removing child nodes of divCourseList.
   */
  function closeCourseList() {
    if (divCourseList.hasChildNodes) {
      // Remove all child elements of divCourseList.
      divCourseList.innerHTML = "";
      divCourseList.setAttribute("class", "");

      // Remove feedback div.
      divFeedback.innerHTML = "";
      divFeedback.setAttribute("class", "");
    }
  }
/*]]>*/