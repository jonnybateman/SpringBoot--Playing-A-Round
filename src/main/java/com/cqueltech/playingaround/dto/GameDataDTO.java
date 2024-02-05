package com.cqueltech.playingaround.dto;

/*
 * A DTO (Data Transfer Object) class used by the AppController for submitted
 * data from the Create Game form.
 */

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GameDataDTO {

  /* 
   * Define class variables
   */

  @NotNull(message = "is required")
  @Size(min = 3, message = "is required")
  private String courseName;

  @NotNull(message = "is required")
  @Size(min = 1, message = "is required")
  private String gameName;

  /*
   * Define class constructors
   */

  public GameDataDTO() {}

  public GameDataDTO(String courseName, String gameName) {
    this.courseName = courseName;
    this.gameName = gameName;
  }

  /*
   * Define class getter and setter methods
   */

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }
  
}
