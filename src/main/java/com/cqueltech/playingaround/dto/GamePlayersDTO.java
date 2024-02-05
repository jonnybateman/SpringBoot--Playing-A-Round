package com.cqueltech.playingaround.dto;

/*
 * A DTO (Data Transfer Object) class used by the AppController for retrieving
 * team information for a given game id.
 */

public class GamePlayersDTO {

  /*
   * Define class variables
   */
  
  private int teamId;
  private String teamName;
  private int playerId;
  private String username;
  private int scoreH1;
  private int scoreH2;
  private int scoreH3;
  private int scoreH4;
  private int scoreH5;
  private int scoreH6;
  private int scoreH7;
  private int scoreH8;
  private int scoreH9;
  private int scoreH10;
  private int scoreH11;
  private int scoreH12;
  private int scoreH13;
  private int scoreH14;
  private int scoreH15;
  private int scoreH16;
  private int scoreH17;
  private int scoreH18;

  /*
   * Define class constructors
   */

  public GamePlayersDTO(int teamId, String teamName, String username) {
    this.teamId = teamId;
    this.teamName = teamName;
    this.username = username;
  }

  public GamePlayersDTO(int teamId, String teamName, int playerId, String username,
                        int scoreH1, int scoreH2, int scoreH3, int scoreH4, int scoreH5,
                        int scoreH6, int scoreH7, int scoreH8, int scoreH9, int scoreH10,
                        int scoreH11, int scoreH12, int scoreH13, int scoreH14, int scoreH15,
                        int scoreH16, int scoreH17, int scoreH18) {
    this.teamId = teamId;
    this.teamName = teamName;
    this.playerId = playerId;
    this.username = username;
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
  }

  /*
   * Define class getter and setter methods
   */

  public int getTeamId() {
    return teamId;
  }

  public void setTeamId(int teamId) {
    this.teamId = teamId;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public int getPlayerId() {
    return playerId;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getScoreH1() {
    return scoreH1;
  }

  public void setScoreH1(int scoreH1) {
    this.scoreH1 = scoreH1;
  }

  public int getScoreH2() {
    return scoreH2;
  }

  public void setScoreH2(int scoreH2) {
    this.scoreH2 = scoreH2;
  }

  public int getScoreH3() {
    return scoreH3;
  }

  public void setScoreH3(int scoreH3) {
    this.scoreH3 = scoreH3;
  }

  public int getScoreH4() {
    return scoreH4;
  }

  public void setScoreH4(int scoreH4) {
    this.scoreH4 = scoreH4;
  }

  public int getScoreH5() {
    return scoreH5;
  }

  public void setScoreH5(int scoreH5) {
    this.scoreH5 = scoreH5;
  }

  public int getScoreH6() {
    return scoreH6;
  }

  public void setScoreH6(int scoreH6) {
    this.scoreH6 = scoreH6;
  }

  public int getScoreH7() {
    return scoreH7;
  }

  public void setScoreH7(int scoreH7) {
    this.scoreH7 = scoreH7;
  }

  public int getScoreH8() {
    return scoreH8;
  }

  public void setScoreH8(int scoreH8) {
    this.scoreH8 = scoreH8;
  }

  public int getScoreH9() {
    return scoreH9;
  }

  public void setScoreH9(int scoreH9) {
    this.scoreH9 = scoreH9;
  }

  public int getScoreH10() {
    return scoreH10;
  }

  public void setScoreH10(int scoreH10) {
    this.scoreH10 = scoreH10;
  }

  public int getScoreH11() {
    return scoreH11;
  }

  public void setScoreH11(int scoreH11) {
    this.scoreH11 = scoreH11;
  }

  public int getScoreH12() {
    return scoreH12;
  }

  public void setScoreH12(int scoreH12) {
    this.scoreH12 = scoreH12;
  }

  public int getScoreH13() {
    return scoreH13;
  }

  public void setScoreH13(int scoreH13) {
    this.scoreH13 = scoreH13;
  }

  public int getScoreH14() {
    return scoreH14;
  }

  public void setScoreH14(int scoreH14) {
    this.scoreH14 = scoreH14;
  }

  public int getScoreH15() {
    return scoreH15;
  }

  public void setScoreH15(int scoreH15) {
    this.scoreH15 = scoreH15;
  }

  public int getScoreH16() {
    return scoreH16;
  }

  public void setScoreH16(int scoreH16) {
    this.scoreH16 = scoreH16;
  }

  public int getScoreH17() {
    return scoreH17;
  }

  public void setScoreH17(int scoreH17) {
    this.scoreH17 = scoreH17;
  }

  public int getScoreH18() {
    return scoreH18;
  }

  public void setScoreH18(int scoreH18) {
    this.scoreH18 = scoreH18;
  }

}
