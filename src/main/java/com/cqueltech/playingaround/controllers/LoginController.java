package com.cqueltech.playingaround.controllers;

/*
 * Controllers are used for processing the web request (eg. form submition) and
 * rendering the response to the view. This controller deals with form login and
 * register new user http requests.
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.cqueltech.playingaround.dto.NewUserDTO;
import com.cqueltech.playingaround.service.UserService;
import jakarta.validation.Valid;
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

    // Check if the user has already been logged in. If so call the login-redirect mapping,
    // otherwise go to the login page.
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || auth instanceof AnonymousAuthenticationToken) {
      // Dislplay the login html page. 'fancy-login' is the name of the login HTML file.
      return "login";
    }
    return "redirect:/login-redirect";
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

  /*
   * Add request mapping for /showFormAddUser. Register a new user with required roles.
   */
  @GetMapping("/register-user")
  public String showFormRegisterUser(Model model) {

    // Add a model attribute. This is an object that will hold the form data for the data binding.
    NewUserDTO newUser = new NewUserDTO();
    model.addAttribute("newUser", newUser);

    // Display the add user page.
    return "register-user";
  }

  /*
   * Mapping to authenticate the registration of a new user.
   */
  @PostMapping("/authenticateNewUser")
  public String createUser(@Valid
                         @ModelAttribute("newUser") NewUserDTO newUser,
                         BindingResult bindingResult,
                         Model model) {

    // Form validation, check formatting of the supplied username and password fields.
    if (bindingResult.hasErrors()) {
      // Username and/or password entries not valid. Display error in registration form
      // by adding an attribute value to the applicable model attribute. 'modelUser' is
      // set up as a 'modelAttribute' in 'add-user.html' form.
      model.addAttribute("newUser", new NewUserDTO());
      model.addAttribute("registrationError", "Invalid username and/or password");
      return "/register-user";
    }

    // Ensure the passwords match.
    if (!newUser.getPassword1().equals(newUser.getPassword2())) {
      model.addAttribute("newUser", new NewUserDTO());
      model.addAttribute("registrationError", "Passwords do not match.");
      return "/register-user";
    }

    // Save the user.
    try {
      userService.save(newUser);
    } catch (DataIntegrityViolationException e) {
      model.addAttribute("newUser", new NewUserDTO());
      model.addAttribute("registrationError", "Username already exists.");
      return "/register-user";
    }

    return "login";
  }
}
