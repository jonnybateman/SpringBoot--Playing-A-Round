package com.cqueltech.playingaround.security;

/*
 * Spring Security Configuration is responsible for all the security, including protecting
 * the application/service URLs, validating submitted username and passwords, redirecting
 * to the login form, and so on.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  DataSource dataSource;

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
   * Add a listener bean to your configuration to keep Spring Security updated about
   * session lifecycle events. Allows us to configure concurrent session control (see
   * .sessionManagement). Can now place constraints on a single user's ability to log
   * into application.
   */
  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  /*
   * Configure security of http requests for web application.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, PersistentTokenRepository persistentTokenRepository) throws Exception {

    http
        .csrf((csrf) -> csrf.disable())
        .authorizeHttpRequests(configurer -> configurer
            // Configure the CSS, IMG, and JS directories to be accesible to all without authentication.
            // Ensures directories are accessible even when user has not bee authenticated.
            .requestMatchers(AntPathRequestMatcher.antMatcher("/css/**")).permitAll()
            .requestMatchers(AntPathRequestMatcher.antMatcher("/img/**")).permitAll()
            .requestMatchers(AntPathRequestMatcher.antMatcher("/js/**")).permitAll()
            // Allow anyone to access the register new user page.
            .requestMatchers(AntPathRequestMatcher.antMatcher("/register-user")).permitAll()
            .requestMatchers(AntPathRequestMatcher.antMatcher("/authenticateNewUser")).permitAll()
            .requestMatchers(AntPathRequestMatcher.antMatcher("/login-redirect")).hasAuthority("GOLFER")
            .requestMatchers(AntPathRequestMatcher.antMatcher("/home")).hasAuthority("GOLFER")
            .requestMatchers(AntPathRequestMatcher.antMatcher("/access-denied")).hasAuthority("GOLFER")
            .requestMatchers(AntPathRequestMatcher.antMatcher("/create-game")).hasAuthority("GOLFER")
            .requestMatchers(AntPathRequestMatcher.antMatcher("/create-course")).hasAuthority("GOLFER")
            .requestMatchers(AntPathRequestMatcher.antMatcher("/select-game")).hasAuthority("GOLFER")
            .requestMatchers(AntPathRequestMatcher.antMatcher("/join-game")).hasAuthority("GOLFER")
            .requestMatchers(AntPathRequestMatcher.antMatcher("/create-team")).hasAuthority("GOLFER")
            .requestMatchers(AntPathRequestMatcher.antMatcher("/score-card")).hasAuthority("GOLFER")
            .requestMatchers(AntPathRequestMatcher.antMatcher("/daytona")).hasAuthority("GOLFER")
            .requestMatchers(AntPathRequestMatcher.antMatcher("/matchplay")).hasAuthority("GOLFER")
            .requestMatchers(AntPathRequestMatcher.antMatcher("/drive-distance")).hasAuthority("GOLFER")
            .requestMatchers(AntPathRequestMatcher.antMatcher("/ariel")).hasAnyAuthority("GOLFER")
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
        .logout(logout -> logout
            .permitAll()
            // Use Clear-Site-Data HTTP response header to instruct browser to remove data
            // cached in local storage.
            .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(Directive.ALL)))
            .deleteCookies("JSESSIONID", "remember-me-cookie")
            .invalidateHttpSession(false))
        // Setup exception handling for access denied based on role of user.
        .exceptionHandling(configurer -> configurer
            .accessDeniedPage("/access-denied"))
        .sessionManagement((session) -> session
            // If session has expired direct the user back to the login page.
            .invalidSessionUrl("/showMyLoginPage?logout")
            // Ensure user can have only one active session at a time.
            .maximumSessions(1)
            // If session already exists still allow user to log back in. Existing session
            // will be expired and new session created.
            .maxSessionsPreventsLogin(false))
        .rememberMe(rememberMe -> rememberMe
            .tokenValiditySeconds(1*24*60*60)
            .tokenRepository(persistentTokenRepositoryDatabase())
            .rememberMeCookieName("remember-me-cookie")
            .rememberMeParameter("remember-me"));

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

  /*
  @Bean
  public PersistentTokenRepository persistentTokenRepositoryMemory() {
    InMemoryTokenRepositoryImpl tokenRepositoryImpl = new InMemoryTokenRepositoryImpl();
    return tokenRepositoryImpl;
  }
  */

  @Bean
  public PersistentTokenRepository persistentTokenRepositoryDatabase() {
    JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
    db.setDataSource(dataSource);
    return db;
  }
}
