package com.cqueltech.playingaround.dto;

/*
 * A DTO (Data Transfer Object) class used by the AppController for relaying
 * new team information from the create-team template to the controller.
 */

public class TeamDTO {
  
  /*
   * Define class variables
   */

  private int gameId;
  private String teamName;
  private String username;

  /*
   * Define class constructors
   */

   public TeamDTO(int gameId, String teamName, String username) {
    this.gameId = gameId;
    this.teamName = teamName;
    this.username = username;
  }

  /*
   * Define class getter and setter methods
   */

  public int getGameId() {
    return gameId;
  }

  public void setGameId(int gameId) {
    this.gameId = gameId;
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
