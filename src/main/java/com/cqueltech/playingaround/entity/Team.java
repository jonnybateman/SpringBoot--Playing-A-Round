package com.cqueltech.playingaround.entity;

/*
 * Entity class to define the fields and joins of the Teams table.
 */

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

@Entity
@Table(name="teams")
@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(name = "procUpdateDaytona", procedureName = "proc_update_daytona",
      parameters = {@StoredProcedureParameter(mode = ParameterMode.IN, name = "teamId", type = Integer.class),
                    @StoredProcedureParameter(mode = ParameterMode.IN, name = "playerId", type = Integer.class),
                    @StoredProcedureParameter(mode = ParameterMode.IN, name = "hole", type = Integer.class),
                    @StoredProcedureParameter(mode = ParameterMode.IN, name = "score", type = Integer.class)
      }
  ),
  @NamedStoredProcedureQuery(name = "procUpdateStableford", procedureName = "proc_update_stableford",
      parameters = {@StoredProcedureParameter(mode = ParameterMode.IN, name = "teamId", type = Integer.class),
                    @StoredProcedureParameter(mode = ParameterMode.IN, name = "gameId", type = Integer.class),
                    @StoredProcedureParameter(mode = ParameterMode.IN, name = "hole", type = Integer.class),
                    @StoredProcedureParameter(mode = ParameterMode.IN, name = "score", type = Integer.class)
      }
  )
})
public class Team {

  /*
   * Define entity fields
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private int id;

  @Column(name="game_id")
  private int gameId;

  @Column(name="team_name")
  private String teamName;
  
  @Column(name="matchplay_mode")
  private String matchplayMode;

  @Column(name="holes_played")
  private int holesPlayed;

  @Column(name="matchplay")
  private String matchplay;

  @Column(name="daytona")
  private String daytona;

  @Column(name="stableford")
  private String stableford;

  @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "team_id")
  private Set<Player> players = new HashSet<>();

  /*
   * Define class constructors
   */

  public Team() {}

  public Team(int gameId, String teamName, String matchplayMode) {
    this.gameId = gameId;
    this.teamName = teamName;
    this.matchplayMode = matchplayMode;
    this.daytona = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
    this.stableford = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
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

  public int getGameId() {
    return gameId;
  }

  public void setGameId(int gameId) {
    this.gameId = gameId;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public String getMatchplayMode() {
    return matchplayMode;
  }

  public void setMatchplayMode(String matchplayMode) {
    this.matchplayMode = matchplayMode;
  }

  public int getHolesPlayed() {
    return holesPlayed;
  }

  public void setHolesPlayed(int holesPlayed) {
    this.holesPlayed = holesPlayed;
  }

  public String getMatchplay() {
    return matchplay;
  }

  public void setMatchplay(String matchplay) {
    this.matchplay = matchplay;
  }

  public String getDaytona() {
    return daytona;
  }

  public void setDaytona(String daytona) {
    this.daytona = daytona;
  }

  public String getStableford() {
    return stableford;
  }

  public void setStableford(String stableford) {
    this.stableford = stableford;
  }

  public Set<Player> getPlayers() {
    return players;
  }

  public void setPlayers(Set<Player> players) {
    this.players = players;
  }

}
