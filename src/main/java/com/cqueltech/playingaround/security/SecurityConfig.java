package com.cqueltech.playingaround.security;

/*
 * Spring Security Configuration is responsible for all the security, including protecting
 * the application/service URLs, validating submitted username and passwords, redirecting
 * to the login form, and so on.
 */

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {

  /*
   * Add support for JDBC...no more hard coded users. Tells Spring Boot that we are
   * using a database for user authentication using the details in the
   * application.properties file. Passwords are BCRYPTed in database, user passwords
   * are 'fun123'
   */
  @Bean
  public UserDetailsManager userDetailsManager(DataSource dataSource) {

    // Here we set up configuration for a custom schema for user authentication.
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

    // Define query to retrieve a user by username. Question mark '?' placeholder will be assigned
    // the username from login by Spring automatically.
    jdbcUserDetailsManager.setUsersByUsernameQuery(
        "select username, password, active from users where username=?");

    // Define query to retrieve the authorities/roles by username.
    jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
        "select username, role from roles where username=?");

    return jdbcUserDetailsManager;
  }

  /*
   * Configure security of http requests for web application.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .csrf((csrf) -> csrf.disable())
        .authorizeHttpRequests(configurer -> configurer
            // Configure the CSS and IMG directories to be accesible to all without authentication.
            // Ensures directories are accessible even when user has not bee authenticated.
            .requestMatchers("/css/**").permitAll()
            .requestMatchers("/img/**").permitAll()
            // Allow anyone to access the register new user page.
            .requestMatchers("/register-user").permitAll()
            .requestMatchers("/authenticateNewUser").permitAll()
            .requestMatchers("/login-redirect").hasAuthority("GOLFER")
            .requestMatchers("/home").hasAuthority("GOLFER")
            .requestMatchers("/access-denied").hasAuthority("GOLFER")
            .requestMatchers("/create-game").hasAuthority("GOLFER")
            .requestMatchers("/create-course").hasAuthority("GOLFER")
            .requestMatchers("/select-game").hasAuthority("GOLFER")
            .requestMatchers("/join-game").hasAuthority("GOLFER")
            .requestMatchers("/create-team").hasAuthority("GOLFER")
            .requestMatchers("/score-card").hasAuthority("GOLFER")
            .requestMatchers("/daytona").hasAuthority("GOLFER")
            .requestMatchers("/matchplay").hasAuthority("GOLFER")
            // Any request to the app must be authenticated
            .anyRequest().authenticated())
        // Customize the form login process.
        .formLogin(form -> form
            // Show the custom form at this mapping. See controller for this mapping.
            .loginPage("/showMyLoginPage")
            // Login form should post data to this URL for processing (check user id and password)
            // No mapping required for this Spring will handle this behind the scenes
            .loginProcessingUrl("/authenticateTheUser")
            // Define the default URL on successful login.
            .defaultSuccessUrl("/login-redirect", true)
            // Allow everyone one to see the login page
            .permitAll())
        // Add logout support for default URL (/logout).
        .logout(logout -> logout.permitAll())
        // Setup exception handling for access denied based on role of user.
        .exceptionHandling(configurer -> configurer
            .accessDeniedPage("/access-denied"));

    return http.build();
  }

  /*
   * Define the PasswordEncoder as a bean for our application. Will be used to
   * encrypt/decrypt passwords when registering/authorising users.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {

    String idForEncode = "bcrypt";
    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put(idForEncode, new BCryptPasswordEncoder());

    //return new BCryptPasswordEncoder();
    return new DelegatingPasswordEncoder(idForEncode, encoders);
  }
}
