package com.cqueltech.playingaround.dao;

/*
 * The User Data Access Object. Used as an interface between our app and the database.
 * 
 * The DAO needs a JPA Entity Manager for saving/deleting/changing/retrieving entities (records).
 * The JPA Entity Manager will need to be injected into our DAO.
 */

import java.util.List;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cqueltech.playingaround.dto.CourseDTO;
import com.cqueltech.playingaround.dto.CourseParSiDTO;
import com.cqueltech.playingaround.dto.DriveDistanceDTO;
import com.cqueltech.playingaround.dto.CommaDelimitedScoresDTO;
import com.cqueltech.playingaround.dto.GameDTO;
import com.cqueltech.playingaround.dto.GamePlayerDTO;
import com.cqueltech.playingaround.dto.HoleScoreDTO;
import com.cqueltech.playingaround.dto.PlayerDetailsDTO;
import com.cqueltech.playingaround.dto.PinLocationDTO;
import com.cqueltech.playingaround.dto.ScorecardDTO;
import com.cqueltech.playingaround.entity.Course;
import com.cqueltech.playingaround.entity.Game;
import com.cqueltech.playingaround.entity.HoleScore;
import com.cqueltech.playingaround.entity.Player;
import com.cqueltech.playingaround.entity.Team;
import com.cqueltech.playingaround.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserDAOImpl implements UserDAO {

  // Define fields for Entity Manager and Entity Manager Factor. They are a couple
  // of the key abstractions that JPA (Java Persistance API) specification defines.
  // It sits between the database and the application and plays the responsibility
  // of managing entities in context.
  private EntityManager entityManager;

  /*
   * Inject EntityManager using constructor injection.
   */
  @Autowired
  public UserDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /*
   * Attempt to save a new user to the database.
   */
  public void save(User user) {

    entityManager.persist(user);
  }
  
  /*
   * Search courses table for a course that begins with the supplied string.
   */
  public List<CourseDTO> searchCourses(String search) {
    
    // Create a JPQL query.
    TypedQuery<CourseDTO> query = entityManager.createQuery(
          "SELECT new com.cqueltech.playingaround.dto.CourseDTO(id, courseName) " +
          "FROM Course WHERE lower(courseName) LIKE '" + search.toLowerCase() + "%' ORDER BY courseName",
          CourseDTO.class);

    // Execute query and get the result list.
    List<CourseDTO> courses = query.getResultList();

    // Return the results
    return courses;
  }

  /*
   * Retrieve a course object from database using course name in WHERE clause.
   */
  public Course findCourseByName(String courseName) {

    // Create a JPQL query.
    TypedQuery<Course> query = entityManager.createQuery(
      "SELECT new com.cqueltech.playingaround.entity.Course(id, courseName) " +
      "FROM Course WHERE courseName = '" + courseName + "'",
      Course.class);
    
    // Execute the query.
    List<Course> courses = query.getResultList();

    // Return a course if found.
    if (courses.size() > 0) {
      return courses.get(0);
    } else {
      return null;
    }
  }

  /*
   * Save new course entity to the database
   */
  public void save(Course course) {

    entityManager.persist(course);
  }

  /*
   * Save a game entity to the database.
   */
  public void save(Game game) {

    entityManager.persist(game);
  }

  /*
   * Search courses table for a course that begins with the supplied string.
   */
  public List<GameDTO> searchGames(String search) {
    
    // Create a HQL query.
    TypedQuery<GameDTO> query = entityManager.createQuery(
          "SELECT new com.cqueltech.playingaround.dto.GameDTO(id, gameName) " +
          "FROM Game WHERE lower(gameName) LIKE '%" + search.toLowerCase() +
          "%' ORDER BY gameDate DESC LIMIT 6",
          GameDTO.class);

    // Execute query and get the result list.
    List<GameDTO> games = query.getResultList();

    // Return the results
    return games;
  }

  /*
   * Retrieve game details using game name as identifier
   */
  public GameDTO getGame(String gameName) {

    // Create a HQL query.
    TypedQuery<GameDTO> query = entityManager.createQuery(
        "SELECT new com.cqueltech.playingaround.dto.GameDTO(id, gameName) " +
        "FROM Game WHERE lower(gameName) = '" + gameName.toLowerCase() + "'",
        GameDTO.class);

    List<GameDTO> games = query.getResultList();

    if (games.size() != 1) {
      // No games, or more than 1 game, match the supplied search string.
      return null;
    } else {
      return games.get(0);
    }
  }

  /*
   * Retrieve team based on team id.
   */
  public Team getTeam(int teamId) {

    return entityManager.find(Team.class, teamId);
  }

  /*
   * Retrieve teams based on a particular game id.
   */
  public List<GamePlayerDTO> getGamePlayers(int gameId) {
    
    // Create JPQL query to retrive teams and associated players.
    TypedQuery<GamePlayerDTO> query = entityManager.createQuery(
      "SELECT new com.cqueltech.playingaround.dto.GamePlayerDTO(t.id, teamName, username) " +
      "FROM Team t JOIN t.players p WHERE gameId = " + gameId + " ORDER BY gameId", GamePlayerDTO.class);
    
    // Execute the query and get the result list.
    List<GamePlayerDTO> gamePlayers = query.getResultList();

    // Return the results to controller.
    return gamePlayers;
  }

  /*
   * Retrieve team details for a particular player.
   */
  public PlayerDetailsDTO getPlayerDetails(int gameId, String user) {

    // Create JPQL query to retrieve player and associate team details.
    TypedQuery<PlayerDetailsDTO> query = entityManager.createQuery(
      "SELECT new com.cqueltech.playingaround.dto.PlayerDetailsDTO(g.gameName, t.id, teamName, p.id) " +
      "FROM Game g JOIN g.teams t JOIN t.players p " +
      "WHERE g.id = " + gameId + " " +
      "AND p.username = '" + user + "'", PlayerDetailsDTO.class);

    // Execute the query and return the results.
    PlayerDetailsDTO playerDetails;
    try {
      playerDetails = query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }

    return playerDetails;
  }


  public List<HoleScoreDTO> getPlayerScores(int teamId, String user) {

    // Create JPQL query to retrive player data for current user.
    TypedQuery<HoleScoreDTO> query = entityManager.createQuery(
      "SELECT new com.cqueltech.playingaround.dto.HoleScoreDTO(s.playerId, s.holeNo, s.score) " +
      "FROM Team t JOIN t.players p JOIN p.scores s " +
      "WHERE t.id = :a " +
      "AND p.username = '" + user + "' " +
      "ORDER BY s.holeNo",
      HoleScoreDTO.class);
    query.setParameter("a", teamId);
    //  query.setParameter("b", user);

    return query.getResultList();
  }

  /*
   * Save a player entity to the database.
   */
  public void save(Player player) {

    entityManager.persist(player);
  }

  /*
   * Save a team entity to the database.
   */
  public Integer save(Team team) {

    // Save the new team to the database.
    entityManager.persist(team);

    // Get the id (auto-generated) of the new team and return it.
    entityManager.flush();
    return team.getId();
  }

  /*
   * Retrieve scorecard data for the joined game.
   */
  public List<ScorecardDTO> getScorecard(int gameId) {

    // Create JPQL query.
    TypedQuery<ScorecardDTO> query = entityManager.createQuery(
        "SELECT new com.cqueltech.playingaround.dto.ScorecardDTO(g.id, g.gameName, " +
        "c.id, c.courseName, h.id, h.holeNo, h.par, h.si, h.yards, h.locLat, h.locLong) " +
        "FROM Game g JOIN g.course c JOIN c.holes h WHERE g.id = " + gameId + " ORDER BY h.holeNo ASC",
        ScorecardDTO.class);

    // Execute the query.
    List<ScorecardDTO> scorecardData = query.getResultList();

    return scorecardData;
  }

  /*
   * Retrieve pin location data from database for specified hole.
   */
  public PinLocationDTO getPinLocationData(int holeId) {

    // Create HQL query.
    TypedQuery<PinLocationDTO> query = entityManager.createQuery(
        "SELECT new com.cqueltech.playingaround.dto.PinLocationDTO(locLat, locLong) " +
        "FROM Hole WHERE id = " + holeId,
        PinLocationDTO.class);

    // Execute the query.
    PinLocationDTO pinLocationData = query.getSingleResult();

    return pinLocationData;
  }

  /*
   * Update a particular hole with pin location data.
   */
  public int updatePinLocation(int holeId, String lat, String lon) {

    Query query = entityManager.createQuery("UPDATE Hole SET locLat = :p1, locLong = :p2 " +
        "WHERE id = :p3");
    query.setParameter("p1", lat);
    query.setParameter("p2", lon);
    query.setParameter("p3", holeId);

    return query.executeUpdate();
  }

  /*
   * Set the longest drive distance for the player in the database.
   */
  public void setLongestDrive(int playerId, int distance) {

    Query query = entityManager.createQuery("UPDATE Player SET driveDistance = :p1 " +
        "WHERE id = :p2");
    query.setParameter("p1", distance);
    query.setParameter("p2", playerId);
    try {
      query.executeUpdate();
    } catch (JDBCException e) {
      // Drive distance did not exceed previous drive, do nothing.
    }
  }

  /*
   * Post player's score for a particular hole on the database.
   */
  public void setHoleScore(int gameId, int teamId, int playerId, int hole, int score) {

    TypedQuery<HoleScore> query = entityManager.createQuery(
        "SELECT s from HoleScore s WHERE playerId = :p1 AND holeNo = :p2",
        HoleScore.class);
    query.setParameter("p1", playerId);
    query.setParameter("p2", hole);
    HoleScore holeScore = null;
    try {
      holeScore = query.getSingleResult();
    } catch (NoResultException e) {
      // Do nothing
    }

    if (holeScore == null) {
      // No existing record for score, create new HoleScore entity and persist in
      // database.
      entityManager.persist(new HoleScore(playerId, hole, score));
      // Call stored procedure to update daytona scores in database as required.
      callUpdateDaytonaStoredProc(teamId, playerId, hole, score);
      // Call stored procedure to update Stableford scores for team.
      callUpdateStablefordStoredProc(teamId, gameId, hole, score);
    } else {
      if (holeScore.getScore() != score) {
        // Hole score has changed update score.
        holeScore.setScore(score);
        entityManager.merge(holeScore);
        // Flush entity data to the database as transaction is yet to be committed.
        // Data will then be available in database when the stored database procedures
        // are executed.
        entityManager.flush();
        // Call database stored procedure to update daytona scores in database as required.
        callUpdateDaytonaStoredProc(teamId, playerId, hole, score);
        // Call dataase stored procedure to update Stableford scores for team.
        callUpdateStablefordStoredProc(teamId, gameId, hole, score);
      }
    }
  }

  /*
   * Call the database's stored procedure to update Daytona scores for all teams
   * as required. Database Procedure called via the EntityManager as it cannot be
   * invoked from database trigger as it contains dynamic SQL.
   */
  private void callUpdateDaytonaStoredProc(int teamId,
                                           int playerId,
                                           int hole,
                                           int score) {

    StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(
        "procUpdateDaytona");
    query.setParameter("teamId", teamId);
    query.setParameter("playerId", playerId);
    query.setParameter("hole", hole);
    query.setParameter("score", score);
    query.execute();
  }

  /*
   * Call the database's stored procedure to update Stableford score for the
   * specified team.
   */
  private void callUpdateStablefordStoredProc(int teamId, int gameId, int hole, int score) {

    StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(
        "procUpdateStableford");
    query.setParameter("teamId", teamId);
    query.setParameter("gameId", gameId);
    query.setParameter("hole", hole);
    query.setParameter("score", score);
    query.execute();
  }

  /*
   * For a particular game retrieve each team's Daytona delimited score string from
   * the database.
   */
  public List<CommaDelimitedScoresDTO> getDaytonaTeamScores(int gameId) {

    // Define the HQL query to retrieve tha data.
    TypedQuery<CommaDelimitedScoresDTO> query = entityManager.createQuery(
        "SELECT new com.cqueltech.playingaround.dto.CommaDelimitedScoresDTO(" +
            "teamName, daytona) " +
        "FROM Team WHERE gameId = " + gameId, CommaDelimitedScoresDTO.class);
    
    // Execute the query and return the results.
    return query.getResultList();
  }

  /*
   * For a particular game retrieve each team's Stableford delimited score string
   * from the database.
   */
  public List<CommaDelimitedScoresDTO> getStablefordTeamScores(int gameId) {
  
    // Define the HQL query to retrieve tha data.
    TypedQuery<CommaDelimitedScoresDTO> query = entityManager.createQuery(
      "SELECT new com.cqueltech.playingaround.dto.CommaDelimitedScoresDTO(" +
      "teamName, stableford) " +
      "FROM Team WHERE gameId = " + gameId, CommaDelimitedScoresDTO.class);

    // Execute the query and return the results.
    return query.getResultList();
  }

  /*
   * For each player in a specified game retrieve strokeplay scores from the database.
   */
  public List<HoleScoreDTO> getStrokeplayPlayersScores(int gameId) {

    // Define the HQL query to retrieve the necessary data.
    TypedQuery<HoleScoreDTO> query = entityManager.createQuery(
        "SELECT new com.cqueltech.playingaround.dto.HoleScoreDTO(" +
        "p.username, ifnull(s.holeNo, 0), ifnull(s.score, 0)) " +
        "FROM Team t JOIN t.players p LEFT JOIN p.scores s " +
        "WHERE t.gameId = " + gameId + " " +
        "ORDER BY p.id",
        HoleScoreDTO.class);

    // Execute the query and return the results.
    return query.getResultList();
  }

  /*
   * For every team, except the team the user belongs to, get the minimum score
   * shot for each hole. Data used when rendering the Matchplay scoring template.
   */
  public List<HoleScoreDTO> getMatchplayTeamsScores(int gameId, int teamId) {

    // Define the HQL query to retrieve the necessary data.
    TypedQuery<HoleScoreDTO> query = entityManager.createQuery(
        "SELECT new com.cqueltech.playingaround.dto.HoleScoreDTO(" +
        "t.teamName, ifnull(s.holeNo, 0), ifnull(min(s.score), 0)) " +
        "FROM Team t JOIN t.players p LEFT JOIN p.scores s " +
        "WHERE t.gameId = " + gameId + " " +
        "GROUP BY t.teamName, s.holeNo " +
        "ORDER BY t.teamName, s.holeNo",
        HoleScoreDTO.class);

    // Execute the query and return the results
    return query.getResultList();
  }

  /*
   * Retrieve the par score for each hole of a given course as well as the Course
   * Rating and Slope Rating. Data utilized in the calculation of Handicap Index.
   */
  public List<CourseParSiDTO> getCourseParsSi(int gameId) {

    // Define the HQL query to retrieve the necessary data.
    TypedQuery<CourseParSiDTO> query = entityManager.createQuery(
        "SELECT new com.cqueltech.playingaround.dto.CourseParSiDTO(" +
            "h.par, h.si, c.courseRating, c.slopeRating) " +
        "FROM Course c JOIN c.games g " +
            "JOIN c.holes h " +
        "WHERE g.id = " + gameId + " " +
        "ORDER BY h.holeNo",
        CourseParSiDTO.class);

     return query.getResultList();
  }

  /*
   * Retrieve a player's handicap index from the database.
   */
  public Float getHandicapIndex(String username) {

    Query query = entityManager.createQuery(
        "SELECT handicap " +
        "FROM User " +
        "WHERE username = '" + username + "'"
    );

    //Float handicap = Float.valueOf(query.getSingleResult().toString());
    Object obj = query.getSingleResult();
    if (obj != null) {
      return (float)obj;
    } else {
      return null;
    }
  }

  /*
   * Post handicap index for the round to the database.
   */
  public void postHandicapIndex(int playerId, float handicapIndex) {

    Query query = entityManager.createQuery("UPDATE Player SET handicapIndex = :p1 " +
        "WHERE id = :p2");
    query.setParameter("p1", handicapIndex);
    query.setParameter("p2", playerId);

    query.executeUpdate();
  }

  /*
   * Retrieve the longest recorded drive for each player from the database.
   */
  public List<DriveDistanceDTO> getLongestDrives(int gameId) {

    // Define the HQL query to retrieve the necessary data.
    TypedQuery<DriveDistanceDTO> query = entityManager.createQuery(
        "SELECT new com.cqueltech.playingaround.dto.DriveDistanceDTO(" +
            "p.username, p.driveDistance) " +
        "FROM Team t JOIN t.players p " +
        "WHERE t.gameId = " + gameId + " " +
        "ORDER BY p.driveDistance DESC",
        DriveDistanceDTO.class);

     return query.getResultList();
  }
}
