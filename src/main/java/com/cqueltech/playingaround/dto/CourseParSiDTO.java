package com.cqueltech.playingaround.dto;

public class CourseParSiDTO {

  /*
   * Define class variables
   */
  private Integer par;
  private Integer si;
  private Float courseRating;
  private Integer slopeRating;

  /*
   * Define class constructors
   */

  public CourseParSiDTO() {}

  public CourseParSiDTO(Integer par, Integer si, Float courseRating, Integer slopeRating) {
    this.par = par;
    this.si = si;
    this.courseRating = courseRating;
    this.slopeRating = slopeRating;
  }

  /*
   * Define class getter and setter methods.
   */

  public Integer getPar() {
    return par;
  }

  public void setPar(Integer par) {
    this.par = par;
  }

  public Integer getSi() {
    return si;
  }

  public void setSi(Integer si) {
    this.si = si;
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
  
}
