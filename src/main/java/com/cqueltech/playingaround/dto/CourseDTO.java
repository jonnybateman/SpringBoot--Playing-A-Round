package com.cqueltech.playingaround.dto;

/*
 * A DTO (Data Transfer Object) class used in the retrieval of a list of
 * courses from the database that match a supplied search string.
 */

public class CourseDTO {

  private int courseId;
  private String courseName;

  public CourseDTO(int courseId, String courseName) {
    this.courseName = courseName;
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
  
}
