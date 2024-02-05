package com.cqueltech.playingaround.dto;

/*
 * A DTO (Data Transfer Object) class to transfer course and game data from the
 * controller to the score-card tenplate.
 */

public class ScorecardDTO {

  /*
   * Define class variables
   */

  private int gameId;
  private String gameName;
  private int courseId;
  private String courseName;
  private int holeId;
  private int holeNo;
  private int par;
  private int si;
  private int yards;
  private String locLat;
  private String locLong;

  /*
   * Define class constructors
   */

  public ScorecardDTO() {}

  public ScorecardDTO(int gameId, String gameName, int courseId, String courseName,
      int holeId, int holeNo, int par, int si, int yards, String locLat, String locLong) {
    this.gameId = gameId;
    this.gameName = gameName;
    this.courseId = courseId;
    this.courseName = courseName;
    this.holeId = holeId;
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

  public int getGameId() {
    return gameId;
  }

  public void setGameId(int gameId) {
    this.gameId = gameId;
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public int getCourseId() {
    return courseId;
  }

  public void setCourseId(int courseId) {
    this.courseId = courseId;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public int getHoleId() {
    return holeId;
  }

  public void setHoleId(int holeId) {
    this.holeId = holeId;
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

}
