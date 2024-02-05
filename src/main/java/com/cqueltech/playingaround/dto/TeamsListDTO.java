package com.cqueltech.playingaround.dto;

import java.util.List;

/*
 * A DTO (Data Transfer Object) class used by the AppController for making
 * player information, grouped by team, available to the join-game web view.
 */

public class TeamsListDTO {

  /*
   * Define class variables
   */

  private int teamId;
  private String teamName;
  private List<String> players;

  /*
   * Define class constructors
   */

  public TeamsListDTO(int teamId, String teamName, List<String> players) {
    this.teamId = teamId;
    this.teamName = teamName;
    this.players = players;
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

  public List<String> getPlayers() {
    return players;
  }

  public void setPlayers(List<String> players) {
    this.players = players;
  }

}
