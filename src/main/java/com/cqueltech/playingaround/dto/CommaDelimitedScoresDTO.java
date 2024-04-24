package com.cqueltech.playingaround.dto;

public class CommaDelimitedScoresDTO {

  /*
   * Define class variables
   */

  private String name;
  private String scores;

  /*
   * Define class constructors
   */

  public CommaDelimitedScoresDTO() {}

  public CommaDelimitedScoresDTO(String name, String scores) {
    this.name = name;
    this.scores = scores;
  }

  /*
   * Define class getter and setter methods
   */

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getScores() {
    return scores;
  }

  public void setDaytonaScores(String scores) {
    this.scores = scores;
  }
  
}
