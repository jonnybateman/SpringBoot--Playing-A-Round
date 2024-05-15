package com.cqueltech.playingaround.entity;

/*
 * Entity class to define the fields and joins of the Players table.
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "players")
public class Player {

  /*
   * Define entity fields
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private int id;

  // Foreign key constraint for team_id
  @ManyToOne(fetch = FetchType.LAZY)
  private Team team;

  @Column(name="username")
  private String username;

  // One-to-many unidirectional join
  @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "player_id")
  private Set<HoleScore> scores = new HashSet<>();

  @Column(name = "drive_distance")
  private int driveDistance;

  @Column(name = "handicap_index")
  private Float handicapIndex;

  /*
   * Define class constructors
   */

  public Player() {}

  public Player(String username) {
    this.username = username;
    this.driveDistance = 0;
  }

  public Player(Integer id, String username) {
    this.id = id;
    this.username = username;
    this.driveDistance = 0;
  }

  /*
   * Define class getter and setter methods
   */

  public int getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getDriveDistance() {
    return driveDistance;
  }

  public void setDriveDistance(Integer driveDistance) {
    this.driveDistance = driveDistance;
  }

  public Float getHandicapIndex() {
    return handicapIndex;
  }

  public void setHandicapIndex(Float handicapIndex) {
    this.handicapIndex = handicapIndex;
  }
  
}
