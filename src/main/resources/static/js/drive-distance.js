/*<![CDATA[*/
  // Set margin height for first element below nav bar.
  var navHeight = document.querySelector("nav").offsetHeight;
  var divContainer = document.getElementById("container");
  divContainer.style.cssText = "margin-top: " + (navHeight + 50) + "px;";
/*]]>*/