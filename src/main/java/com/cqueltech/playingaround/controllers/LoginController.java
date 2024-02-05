package com.cqueltech.playingaround.controllers;

/*
 * Controllers are used for processing the web request (eg. form submition) and
 * rendering the response to the view. This controller deals with form login and
 * register new user http requests.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import com.cqueltech.playingaround.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {

  private UserService userService;

  /*
   * Inject the UserService using constructor injection. The UserService acts as an
   * intermediary layer between the controller and the DAO that accesses the database.
   */
  public LoginController(UserService userService) {
    this.userService = userService;
  }

  /*
   * InitBinder used to customise the web request being sent to the controller. It helps
   * in controlling and formatting each and every request that comes to it.
   */
  @InitBinder
  public void initBinder(WebDataBinder webDataBinder) {

    // Register a StringTrimmerEditor object as a custom editor to the data binder with
    // String class as target source. InitBinder will now trim all the string values
    // coming as part of a request.
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(false);
    webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

  /*
   * Request mapping to show the user login form.
   */
  @GetMapping("/showMyLoginPage")
  public String showMyLoginPage() {

    // Dislplay the login html page. 'fancy-login' is the name of the login HTML file.
    return "login";
  }

  /*
   * Add request mapping for /access-denied. When user does not have valid authority/role
   * for the resource.
   */
  @GetMapping("/access-denied")
  public String showAccessDenied() {

    // Dislplay the access-denied html page.
    return "access-denied";
  }

  /*
   * Mapping for the login-redirect template. Directs user to appropriate page after
   * successful login.
   */
  @GetMapping("/login-redirect")
  public String showLoginRedirect() {

    return "login-redirect";
  }
}
