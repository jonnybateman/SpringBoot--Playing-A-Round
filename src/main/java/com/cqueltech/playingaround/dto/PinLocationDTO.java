package com.cqueltech.playingaround.dto;

/*
 * A DTO (Data Transfer Object) class used to retrieve pin location
 * data for a particular hole and then pass that data to the score-card
 * Thymeleaf template.
 */

public class PinLocationDTO {

  /*
   * Define class variables
   */

  private String locLat;

  private String locLong;

  /*
   * Define class constructors
   */

  public PinLocationDTO() {}

  public PinLocationDTO(String locLat, String locLong) {
    this.locLat = locLat;
    this.locLong = locLong;
  }

  /*
   * Define class getter and setter methods
   */

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
