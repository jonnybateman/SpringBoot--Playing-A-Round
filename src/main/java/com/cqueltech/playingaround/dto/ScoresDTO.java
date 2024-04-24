package com.cqueltech.playingaround.dto;

public class ScoresDTO {

  /*
   * Define class variables
   */

  private String name;
  private Integer scoreH1;
  private Integer scoreH2;
  private Integer scoreH3;
  private Integer scoreH4;
  private Integer scoreH5;
  private Integer scoreH6;
  private Integer scoreH7;
  private Integer scoreH8;
  private Integer scoreH9;
  private Integer scoreH10;
  private Integer scoreH11;
  private Integer scoreH12;
  private Integer scoreH13;
  private Integer scoreH14;
  private Integer scoreH15;
  private Integer scoreH16;
  private Integer scoreH17;
  private Integer scoreH18;
  private Integer scoreTotal;
  private Integer strokeplayOut;
  private Integer strokeplayIn;

  /*
   * Define class constructors
   */

  public ScoresDTO() {}

  public ScoresDTO(String name, Integer scoreH1, Integer scoreH2, Integer scoreH3,
                           Integer scoreH4, Integer scoreH5, Integer scoreH6, Integer scoreH7,
                           Integer scoreH8, Integer scoreH9, Integer scoreH10, Integer scoreH11,
                           Integer scoreH12, Integer scoreH13, Integer scoreH14, Integer scoreH15,
                           Integer scoreH16, Integer scoreH17, Integer scoreH18, Integer scoreTotal) {

    this.name = name;
    this.scoreH1 = scoreH1;
    this.scoreH2 = scoreH2;
    this.scoreH3 = scoreH3;
    this.scoreH4 = scoreH4;
    this.scoreH5 = scoreH5;
    this.scoreH6 = scoreH6;
    this.scoreH7 = scoreH7;
    this.scoreH8 = scoreH8;
    this.scoreH9 = scoreH9;
    this.scoreH10 = scoreH10;
    this.scoreH11 = scoreH11;
    this.scoreH12 = scoreH12;
    this.scoreH13 = scoreH13;
    this.scoreH14 = scoreH14;
    this.scoreH15 = scoreH15;
    this.scoreH16 = scoreH16;
    this.scoreH17 = scoreH17;
    this.scoreH18 = scoreH18;
    this.scoreTotal = scoreTotal;
  }

  /*
   * Define class getter and setter methods
   */

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getScoreH1() {
    return scoreH1;
  }

  public void setScoreH1(Integer scoreH1) {
    this.scoreH1 = scoreH1;
  }

  public Integer getScoreH2() {
    return scoreH2;
  }

  public void setScoreH2(Integer scoreH2) {
    this.scoreH2 = scoreH2;
  }

  public Integer getScoreH3() {
    return scoreH3;
  }

  public void setScoreH3(Integer scoreH3) {
    this.scoreH3 = scoreH3;
  }

  public Integer getScoreH4() {
    return scoreH4;
  }

  public void setScoreH4(Integer scoreH4) {
    this.scoreH4 = scoreH4;
  }

  public Integer getScoreH5() {
    return scoreH5;
  }

  public void setScoreH5(Integer scoreH5) {
    this.scoreH5 = scoreH5;
  }

  public Integer getScoreH6() {
    return scoreH6;
  }

  public void setScoreH6(Integer scoreH6) {
    this.scoreH6 = scoreH6;
  }

  public Integer getScoreH7() {
    return scoreH7;
  }

  public void setScoreH7(Integer scoreH7) {
    this.scoreH7 = scoreH7;
  }

  public Integer getScoreH8() {
    return scoreH8;
  }

  public void setScoreH8(Integer scoreH8) {
    this.scoreH8 = scoreH8;
  }

  public Integer getScoreH9() {
    return scoreH9;
  }

  public void setScoreH9(Integer scoreH9) {
    this.scoreH9 = scoreH9;
  }

  public Integer getScoreH10() {
    return scoreH10;
  }

  public void setScoreH10(Integer scoreH10) {
    this.scoreH10 = scoreH10;
  }

  public Integer getScoreH11() {
    return scoreH11;
  }

  public void setScoreH11(Integer scoreH11) {
    this.scoreH11 = scoreH11;
  }

  public Integer getScoreH12() {
    return scoreH12;
  }

  public void setScoreH12(Integer scoreH12) {
    this.scoreH12 = scoreH12;
  }

  public Integer getScoreH13() {
    return scoreH13;
  }

  public void setScoreH13(Integer scoreH13) {
    this.scoreH13 = scoreH13;
  }

  public Integer getScoreH14() {
    return scoreH14;
  }

  public void setScoreH14(Integer scoreH14) {
    this.scoreH14 = scoreH14;
  }

  public Integer getScoreH15() {
    return scoreH15;
  }

  public void setScoreH15(Integer scoreH15) {
    this.scoreH15 = scoreH15;
  }

  public Integer getScoreH16() {
    return scoreH16;
  }

  public void setScoreH16(Integer scoreH16) {
    this.scoreH16 = scoreH16;
  }

  public Integer getScoreH17() {
    return scoreH17;
  }

  public void setScoreH17(Integer scoreH17) {
    this.scoreH17 = scoreH17;
  }

  public Integer getScoreH18() {
    return scoreH18;
  }

  public void setScoreH18(Integer scoreH18) {
    this.scoreH18 = scoreH18;
  }

  public Integer getScoreTotal() {
    return scoreTotal;
  }

  public void setScoreTotal(Integer scoreTotal) {
    this.scoreTotal = scoreTotal;
  }

  public Integer getStrokeplayOut() {
    return strokeplayOut;
  }

  public void setStrokeplayOut() {
    this.strokeplayOut = (scoreH1 == null ? 0 : scoreH1) +
        (scoreH2 == null ? 0 : scoreH2) +
        (scoreH3 == null ? 0 : scoreH3) +
        (scoreH4 == null ? 0 : scoreH4) +
        (scoreH5 == null ? 0 : scoreH5) +
        (scoreH6 == null ? 0 : scoreH6) +
        (scoreH7 == null ? 0 : scoreH7) +
        (scoreH8 == null ? 0 : scoreH8) +
        (scoreH9 == null ? 0 : scoreH9);
  }

  public Integer getStrokeplayIn() {
    return strokeplayIn;
  }

  public void setStrokeplayIn() {
    this.strokeplayIn = (scoreH10 == null ? 0 : scoreH10) +
        (scoreH11 == null ? 0 : scoreH11) +
        (scoreH12 == null ? 0 : scoreH12) +
        (scoreH13 == null ? 0 : scoreH13) +
        (scoreH14 == null ? 0 : scoreH14) +
        (scoreH15 == null ? 0 : scoreH15) +
        (scoreH16 == null ? 0 : scoreH16) +
        (scoreH17 == null ? 0 : scoreH17) +
        (scoreH18 == null ? 0 : scoreH18);
  }
  
}
