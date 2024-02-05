package com.cqueltech.playingaround.dto;

/*
 * A DTO (Data Transfer Object) class used in the retrieval of a list of
 * games from the database that match a supplied search string.
 */

public class GameDTO {

  private int gameId;
  private String gameName;
  private int courseId;
  private String gameDate;

  // Define class comstructors

  public GameDTO() {}

  public GameDTO(int gameId, String gameName) {
    this.gameId = gameId;
    this.gameName = gameName;
  }

  public GameDTO(int gameId, String gameName, int courseId, String gameDate) {
    this.gameId = gameId;
    this.gameName = gameName;
    this.courseId = courseId;
    this.gameDate = gameDate;
  }

  // Define class getter and setter methods

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public int getGameId() {
    return gameId;
  }

  public void setGameId(int gameId) {
    this.gameId = gameId;
  }

  public int getCourseId() {
    return courseId;
  }

  public void setCourseId(int courseId) {
    this.courseId = courseId;
  }

  public String getGameDate() {
    return gameDate;
  }

  public void setGameDate(String gameDate) {
    this.gameDate = gameDate;
  }
  
}
