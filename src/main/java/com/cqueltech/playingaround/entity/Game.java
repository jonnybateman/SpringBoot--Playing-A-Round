package com.cqueltech.playingaround.entity;

/*
 * Entity class to define the fields and joins of the Games table.
 */

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
import jakarta.persistence.Column;

@Entity
@Table(name = "games")
public class Game {

  /*
   * Define entity fields
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private int id;

  @Column(name="game_name")
  private String gameName;

  // Foreign key constraint for course_id
  @ManyToOne(fetch = FetchType.LAZY)
  private Course course;

  @Column(name = "game_date")
  private String gameDate;

  @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "game_id")
  private Set<Team> teams = new HashSet<>();

  /*
   * Define class constructors
   */

  public Game() {}

  public Game(String gameName, String gameDate) {
    this.gameName = gameName;
    //this.courseId = courseId;
    this.gameDate = gameDate;
  }

  /*
   * Define class getter and setter methods
   */

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

//  public Integer getCourseId() {
//    return courseId;
//  }

//  public void setCourseId(Integer courseId) {
//    this.courseId = courseId;
//  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public String getGameDate() {
    return gameDate;
  }

  public void setGameDate(String gameDate) {
    this.gameDate = gameDate;
  }

  public Set<Team> getTeams() {
    return teams;
  }

  public void setTeams(Set<Team> teams) {
    this.teams = teams;
  }
  
}
