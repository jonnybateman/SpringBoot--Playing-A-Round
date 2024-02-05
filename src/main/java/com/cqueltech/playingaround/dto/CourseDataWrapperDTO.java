package com.cqueltech.playingaround.dto;

/*
 * A DTO (Data Transfer Object) wrapper class to store hole data for a
 * course. Used to transfer course and hole data between the create-course
 * view and the application controller.
 */

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class CourseDataWrapperDTO {

  /*
   * Define class variables
   */
  
  @NotEmpty
  private String courseName;

  @Valid
  private List<HoleDataDTO> holeDataList;

  /*
   * Define class constructors
   */

  public CourseDataWrapperDTO() {}

  /*
   * Define class getter and setter methods
   */

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public List<HoleDataDTO> getHoleDataList() {
    return holeDataList;
  }

  public void setHoleDataList(List<HoleDataDTO> holeDataList) {
    this.holeDataList = holeDataList;
  }
  
}
