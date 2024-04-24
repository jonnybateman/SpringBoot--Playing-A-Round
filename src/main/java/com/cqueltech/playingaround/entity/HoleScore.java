package com.cqueltech.playingaround.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hole_scores")
public class HoleScore {

  /*
   * Define entity variables
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "player_id")
  private int playerId;

  @Column(name = "hole_no")
  private int holeNo;

  @Column(name = "score")
  private Integer score;

  /*
   * Define entity constructors
   */

  public HoleScore() {}

  public HoleScore(int playerId, int holeNo, Integer score) {
    this.playerId = playerId;
    this.holeNo = holeNo;
    this.score = score;
  }

  public HoleScore(int id, int playerId, int holeNo, Integer score) {
    this.id = id;
    this.playerId = playerId;
    this.holeNo = holeNo;
    this.score = score;
  }

  /*
   * Define entity getter and setter methods
   */

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getPlayerId() {
    return playerId;
  }

  public void setPlayer(int playerId) {
    this.playerId = playerId;
  }

  public int getHoleNo() {
    return holeNo;
  }

  public void setHoleNo(int holeNo) {
    this.holeNo = holeNo;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

}
