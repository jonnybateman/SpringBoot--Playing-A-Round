package com.cqueltech.playingaround.dto;

public class GamePlayerDTO {

  /*
   * Define class variables
   */

  private int teamId;
  private String teamName;
  private String username;

  /*
   * Define class constructors
   */
  
  public GamePlayerDTO(int teamId, String teamName, String username) {
    this.teamId = teamId;
    this.teamName = teamName;
    this.username = username;
  }

  /*
   * Define class getter and setter methods
   */

  public int getTeamId() {
    return teamId;
  }

  public void setTeamId(int teamId) {
    this.teamId = teamId;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
