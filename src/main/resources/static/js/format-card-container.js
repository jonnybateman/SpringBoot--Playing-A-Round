/*<![CDATA[*/
var navHeight = document.querySelector("nav").offsetHeight;
var footerHeight = document.getElementById("footer").offsetHeight;
var screenHeight = document.documentElement.clientHeight;

// Set the height and top margin for the container containing the team cards.
var divContainer = document.getElementById("container");
var containerHeight = screenHeight - navHeight - footerHeight;
divContainer.style.cssText = "margin-top: " + (navHeight) + "px;" +
    "height: " + containerHeight + "px; padding-top: 50px";
/*]]>*/