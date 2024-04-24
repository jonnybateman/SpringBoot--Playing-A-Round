package com.cqueltech.playingaround.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class NewUserDTO {

  /*
   * Define class variables
   */

  @NotNull(message = "is required")
  @Size(min = 1, message = "is required")
  @Pattern(regexp = "[a-zA-Z0-9]*")
  private String username;

  @NotNull(message = "is required")
  @Size(min = 1, message = "is required")
  @Pattern(regexp = "[a-zA-Z0-9@!\\-_*&%$\\/]*")
  private String password1;

  @NotNull(message = "is required")
  @Size(min = 1, message = "is required")
  @Pattern(regexp = "[a-zA-Z0-9@!\\-_*&%$\\/]*")
  private String password2;

  private int active;
  private String firstName;
  private String lastName;
  private float handicap;

  /*
   * Define class constructors
   */

  public NewUserDTO() {}

  public NewUserDTO(String username,
                    String password1,
                    String password2,
                    String firstName,
                    String lastName,
                    float handicap) {
    this.username = username;
    this.password1 = password1;
    this.password2 = password2;
    this.active = 1;
    this.firstName = firstName;
    this.lastName = lastName;
    this.handicap = handicap;
  }

  /*
   * Define class getter and setter metods
   */

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword1() {
    return password1;
  }

  public void setPassword1(String password1) {
    this.password1 = password1;
  }

  public String getPassword2() {
    return password2;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  public int getActive() {
    return active;
  }

  public void setActive(int active) {
    this.active = active;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public float getHandicap() {
    return handicap;
  }

  public void setHandicap(float handicap) {
    this.handicap = handicap;
  }
  
}
