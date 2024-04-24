package com.cqueltech.playingaround.exception;

/*
 * A Controller Advice acts as a layer above the Controller(s). It allows you to handle
 * exceptions across the whole application, not just to an individual controller. You can
 * think of it as an interceptor of exceptions thrown by methods annotated with
 * @RequestMapping, @GetMapping, @PostMapping, etc. When exception logic is placed here it is
 * regarded as global exception handling. There may be multiple rest controllers in a
 * project which all use the Advice Controller to handle exceptions thrown.
 * 
 * A ControllerAdvice to handle exceptions raised during web application http requests.
 * Base package set to only handle exceptions originating from controllers in the stated package.
 * 
 * Adds an attribute ('errorResponse') to the spring model containing the exception details,
 * which can then be utilised by the exception handling Thymeleaf template errors.html.
 */

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.cqueltech.playingaround.dto.ServiceResponseDTO;
import org.springframework.ui.Model;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(basePackages = "com.cqueltech.playingaround.controllers")
    // Comment out this annotation to see full exception stack trace in terminal.
@Slf4j
public class WebAppGlobalExceptionHandler<T> {

  private final static String PACKAGE_PREFIX = "com.cqueltech.playingaround";
  private final static DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  /*
   * Generic exception handler to catch any exception that is not already handled.
   */
  @ExceptionHandler(Exception.class)
  public String handleException(Exception exc, Model model) {

    // Find which class and method of the app threw the exception.
    Writer writer = new StringWriter();
    exc.printStackTrace(new PrintWriter(writer));
    String stackTrace = writer.toString();
    String[] stackTraceArray = stackTrace.split("\n");
    StringBuilder stringBuilder = new StringBuilder();
    for (String trace : stackTraceArray) {
      if (trace.contains(PACKAGE_PREFIX)) {
        stringBuilder.append(trace + "\n");
      }
    }

    // Create a student response to be sent to Rest Client.
    ServiceResponseDTO<T> errorResponse = new ServiceResponseDTO<>();
    errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
    errorResponse.setMessage(exc.getMessage());
    errorResponse.setTimestamp(DATE_TIME_FORMAT.format(ZonedDateTime.now()));
    errorResponse.setStackTrace(stringBuilder.toString());

    // Add attribute to Spring model to make it accessible to Thymeleaf template.
    model.addAttribute("errorResponse", errorResponse);

    // Display the exception handling Thymeleaf template 'errors.html'.
    return "error";
  }

}

