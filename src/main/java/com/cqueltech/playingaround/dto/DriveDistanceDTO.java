package com.cqueltech.playingaround.dto;

/*
 * A DTO (Data Transfer Object) class used in the retrieval of a player's
 * longest drive from the database for a given game id.
 */

public class DriveDistanceDTO {

  /*
   * Define class variables
   */

  private String username;
  private int distance;

  /*
   * Define class constructors
   */

  public DriveDistanceDTO() {}

  public DriveDistanceDTO(String username, int distance) {
    this.username = username;
    this.distance = distance;
  }

  /*
   * Define class getter and setter methods
   */

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public int getDistance() {
    return distance;
  }
  public void setDistance(int distance) {
    this.distance = distance;
  }

}
