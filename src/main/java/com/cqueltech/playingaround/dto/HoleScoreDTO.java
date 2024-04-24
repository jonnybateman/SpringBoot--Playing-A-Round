package com.cqueltech.playingaround.dto;

/*
 * A DTO (Data Transfer Object) class used in the retrieval of Matchplay
 * scoring data. Each class instance stores the minimum score for a
 * particular hole and a particular team.
 */

public class HoleScoreDTO {

  /*
   * Define class variables
   */
  
  private String name;
  private Integer playerId;
  private Integer holeNo;
  private Integer score;

  /*
   * Define class constructors
   */
  
  public HoleScoreDTO(String name, int holeNo, int score) {
    this.name = name;
    this.holeNo = holeNo;
    this.score = score;
  }

  public HoleScoreDTO(int playerId, int holeNo, int score) {
    this.playerId = playerId;
    this.holeNo = holeNo;
    this.score = score;
  }

  /*
   * Define class getter and setter emthods
   */

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPlayerId() {
    return playerId;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }

  public int getHoleNo() {
    return holeNo;
  }

  public void setHoleNo(int holeNo) {
    this.holeNo = holeNo;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

}
