/*<![CDATA[*/
// Set margin height for first element below nav bar.
var navHeight = document.querySelector("nav").offsetHeight;
var errorPageHeading = document.getElementById("errorPageHeading");
errorPageHeading.style.cssText = "margin-top: " + (navHeight + 50) + "px;";
/*]]>*/