package com.cqueltech.playingaround.entity;

/*
 * Entity class to define the fields and joins of the Courses table.
 */

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

@Entity
@Table(name="courses")
public class Course {

  /*
   * Define entity fields
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private int id;

  @Column(name="course_name")
  private String courseName;

  @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "course_id")
  private Set<Hole> holes = new HashSet<>();

  @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "course_id")
  private Set<Game> games = new HashSet<>();

  /*
   * Define class constructors
   */

  public Course() {}

  public Course(String courseName) {
    this.courseName = courseName;
  }

  public Course(int id, String courseName) {
    this.id = id;
    this.courseName = courseName;
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

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public Set<Hole> getHoles() {
    return holes;
  }

  public void setHoles(Set<Hole> holes) {
    this.holes = holes;
  }

  public Set<Game> getGames() {
    return games;
  }

  public void setGames(Set<Game> games) {
    this.games = games;
  }

}
