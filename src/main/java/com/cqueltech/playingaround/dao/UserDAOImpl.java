package com.cqueltech.playingaround.dao;

/*
 * The User Data Access Object. Used as an interface between our app and the database.
 * 
 * The DAO needs a JPA Entity Manager for saving/deleting/changing/retrieving entities (records).
 * The JPA Entity Manager will need to be injected into our DAO.
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cqueltech.playingaround.dto.CourseDTO;
import com.cqueltech.playingaround.dto.GameDTO;
import com.cqueltech.playingaround.dto.GamePlayersDTO;
import com.cqueltech.playingaround.dto.ScorecardDTO;
import com.cqueltech.playingaround.entity.Course;
import com.cqueltech.playingaround.entity.Game;
import com.cqueltech.playingaround.entity.Player;
import com.cqueltech.playingaround.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Repository
public class UserDAOImpl implements UserDAO {

  // Define field for Entity Manager. EntityManager is one of the key abstractions
  // that JPA (Java Persistance API) specification defines. It sits between the
  // database and the application and plays the responsibility of managing entities
  // in context.
  private EntityManager entityManager;

  /*
   * Inject EntityManager using constructor injection.
   */
  @Autowired
  public UserDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
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
    
    // Create a JPQL query.
    TypedQuery<GameDTO> query = entityManager.createQuery(
          "SELECT new com.cqueltech.playingaround.dto.GameDTO(id, gameName) " +
          "FROM Game WHERE lower(gameName) LIKE '" + search.toLowerCase() + "%' ORDER BY gameDate DESC",
          GameDTO.class);

    // Execute query and get the result list.
    List<GameDTO> games = query.getResultList();

    // Return the results
    return games;
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
  public List<GamePlayersDTO> getTeams(int gameId) {
    
    // Create JPQL query to retrive teams and associated players.
    TypedQuery<GamePlayersDTO> query = entityManager.createQuery(
      "SELECT new com.cqueltech.playingaround.dto.GamePlayersDTO(t.id, teamName, username) " +
      "FROM Team t JOIN t.players p WHERE gameId = " + gameId + " ORDER BY gameId", GamePlayersDTO.class);
    
    // Execute the query and get the result list.
    List<GamePlayersDTO> teams = query.getResultList();

    // Return the results to controller.
    return teams;
  }

  /*
   * Retrieve team if one exists for user.
   */
  public GamePlayersDTO getTeam(int gameId, String user) {

    // Create JPQL query to retrive team for current user.
    TypedQuery<GamePlayersDTO> query = entityManager.createQuery(
      "SELECT new com.cqueltech.playingaround.dto.GamePlayersDTO(t.id, teamName, p.id, username," +
      "scoreH1, scoreH2, scoreH3, scoreH4, scoreH5, scoreH6, scoreH7, scoreH8, scoreH9, scoreH10, " +
      "scoreH11, scoreH12, scoreH13, scoreH14, scoreH15, scoreH16, scoreH17, scoreH18) " +
      "FROM Team t JOIN t.players p WHERE t.gameId = " + gameId + " AND p.username = '" + user + "'",
      GamePlayersDTO.class);
    
    // Execute the query.
    GamePlayersDTO player;
    try {
      player = query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    } 

    return player;
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
        "FROM Game g JOIN g.course c JOIN c.holes h WHERE g.id = " + gameId + " ORDER BY h.id ASC",
        ScorecardDTO.class);

    // Execute the query.
    List<ScorecardDTO> scorecardData = query.getResultList();

    return scorecardData;
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
}
