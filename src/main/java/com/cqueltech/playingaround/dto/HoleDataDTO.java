package com.cqueltech.playingaround.dto;

/*
 * A DTO (Data Transfer Object) class to transfer hole data between the
 * create-course view and the application controller.
 */

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class HoleDataDTO {
  
  /*
   * Define class variables
   */
  private int hole;

  @NotEmpty
  @Pattern(regexp = "[0-9]{2,3}")
  private String yards;

  @NotEmpty
  @Pattern(regexp = "[3-5]{1,1}")
  private String par;

  @NotEmpty
  @Pattern(regexp = "([1-9]|1[0-8])")
  private String si;

  /*
   * Define class constructors
   */

  public HoleDataDTO() {}

  public HoleDataDTO(int hole) {
    this.hole = hole;
  }

  // Define class getter and setter methods

  public int getHole() {
    return hole;
  }

  public void setHole(int hole) {
    this.hole = hole;
  }

  public String getYards() {
    return yards;
  }

  public void setYards(String yards) {
    this.yards = yards;
  }

  public String getPar() {
    return par;
  }

  public void setPar(String par) {
    this.par = par;
  }

  public String getSi() {
    return si;
  }

  public void setSi(String si) {
    this.si = si;
  }

}
