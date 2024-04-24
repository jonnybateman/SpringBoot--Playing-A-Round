package com.cqueltech.playingaround.entity;

/*
 * Entity class to define the fields of the 'holes' table.
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "holes")
public class Hole {

  /*
   * Define entity fields
   */

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "hole_no")
  private int holeNo;

  @Column(name = "par")
  private int par;

  @Column(name = "si")
  private int si;

  @Column(name = "yards")
  private int yards;

  @Column(name = "loc_lat")
  private String locLat;

  @Column(name = "loc_long")
  private String locLong;

  @ManyToOne(fetch = FetchType.LAZY,
             cascade = {CascadeType.ALL})
  private Course course;

  /*  
   * Define class instructors
   */

  public Hole() {}

  public Hole(int holeNo, int par, int si, int yards, String locLat, String locLong) {
    this.holeNo = holeNo;
    this.par = par;
    this.si = si;
    this.yards = yards;
    this.locLat = locLat;
    this.locLong = locLong;
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

  public int getHoleNo() {
    return holeNo;
  }

  public void setHoleNo(int holeNo) {
    this.holeNo = holeNo;
  }

  public int getPar() {
    return par;
  }

  public void setPar(int par) {
    this.par = par;
  }

  public int getSi() {
    return si;
  }

  public void setSi(int si) {
    this.si = si;
  }

  public int getYards() {
    return yards;
  }

  public void setYards(int yards) {
    this.yards = yards;
  }

  public String getLocLat() {
    return locLat;
  }

  public void setLocLat(String locLat) {
    this.locLat = locLat;
  }

  public String getLocLong() {
    return locLong;
  }

  public void setLocLong(String locLong) {
    this.locLong = locLong;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

}
