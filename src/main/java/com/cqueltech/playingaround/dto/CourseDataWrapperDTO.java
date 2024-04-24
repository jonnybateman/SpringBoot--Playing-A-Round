package com.cqueltech.playingaround.dto;

/*
 * A DTO (Data Transfer Object) wrapper class to store hole data for a
 * course. Used to transfer course and hole data between the create-course
 * view and the application controller.
 */

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CourseDataWrapperDTO {

  /*
   * Define class variables
   */
  
  @NotEmpty
  private String courseName;

  @NotNull
  private Float courseRating;

  @NotNull
  private Integer slopeRating;

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

  public Float getCourseRating() {
    return courseRating;
  }

  public void setCourseRating(Float courseRating) {
    this.courseRating = courseRating;
  }

  public Integer getSlopeRating() {
    return slopeRating;
  }

  public void setSlopeRating(Integer slopeRating) {
    this.slopeRating = slopeRating;
  }

  public List<HoleDataDTO> getHoleDataList() {
    return holeDataList;
  }

  public void setHoleDataList(List<HoleDataDTO> holeDataList) {
    this.holeDataList = holeDataList;
  }
  
}
