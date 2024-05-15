package com.cqueltech.playingaround.dto;

/*
 * A DTO (Data Transfer Object) class used by the AppController for retrieving
 * team information for a given game id.
 */

public class PlayerDetailsDTO {

  /*
   * Define class variables
   */
  
  private String gameName;
  private int teamId;
  private String teamName;
  private int playerId;

  /*
   * Define class constructors
   */

  public PlayerDetailsDTO() {}

  public PlayerDetailsDTO(String gameName, int teamId, String teamName, int playerId) {
    this.gameName = gameName;
    this.teamId = teamId;
    this.teamName = teamName;
    this.playerId = playerId;
  }

  /*
   * Define class getter and setter methods
   */

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

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

  public int getPlayerId() {
    return playerId;
  }

  public void setPlayerId(Integer playerId) {
    this.playerId = playerId;
  }

}
