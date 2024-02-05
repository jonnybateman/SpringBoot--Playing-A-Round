package com.cqueltech.playingaround.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cqueltech.playingaround.dao.UserDAO;
import com.cqueltech.playingaround.dto.CourseDTO;
import com.cqueltech.playingaround.dto.CourseDataWrapperDTO;
import com.cqueltech.playingaround.dto.GameDTO;
import com.cqueltech.playingaround.dto.HoleDataDTO;
import com.cqueltech.playingaround.dto.ScorecardDTO;
import com.cqueltech.playingaround.dto.TeamDTO;
import com.cqueltech.playingaround.dto.GamePlayersDTO;
import com.cqueltech.playingaround.entity.Course;
import com.cqueltech.playingaround.entity.Game;
import com.cqueltech.playingaround.entity.Hole;
import com.cqueltech.playingaround.entity.Player;
import com.cqueltech.playingaround.entity.Team;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private UserDAO userDAO;

  /*
   * Inject the UserDAO component using constructor injection.
   */
  public UserServiceImpl(UserDAO userDAO) {
    this.userDAO = userDAO;
  }
  
  /*
   * Return list of courses that begin with supplied search string.
   */
  @Override
  public List<CourseDTO> searchCourses(String search) {
    return userDAO.searchCourses(search);
  }

  /*
   * Get a course by name.
   */
  @Override
  public Course findCourseByName(String courseName) {
    return userDAO.findCourseByName(courseName);
  }

  /*
   * Save a new game to the database.
   */
  @Override
  @Transactional
  public void saveGame(Course course, String gameName) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = dateFormat.format(new Date());
    Game game = new Game(gameName, date);
    game.setCourse(course);
    userDAO.save(game);
  }

  /*
   * Save new course and holde data to the database.
   */
  @Override
  @Transactional
  public void saveCourse(CourseDataWrapperDTO courseDataWrapperDTO) {

    // Setup Course and Hole entity classes.
    Course course = new Course();
    course.setCourseName(courseDataWrapperDTO.getCourseName());

    for (HoleDataDTO hole : courseDataWrapperDTO.getHoleDataList()) {
      Hole newHole = new Hole(hole.getHole(),
                            Integer.parseInt(hole.getPar()),
                            Integer.parseInt(hole.getSi()),
                            Integer.parseInt(hole.getYards()),
                            null,
                            null);
      newHole.setCourse(course);
      course.getHoles().add(newHole);
    }

    // Save the Course entity to the database.
    userDAO.save(course);
  }

  /*
   * Return list of games that begin with supplied search string.
   */
  @Override
  public List<GameDTO> searchGames(String search) {
    return userDAO.searchGames(search);
  }

  /*
   * Retrieve team for a particular team id.
   */
  @Override
  public Team getTeam(int teamId) {
    return userDAO.getTeam(teamId);
  }

  /*
   * Retrieve teams associated with a particular game id.
   */
  @Override
  public List<GamePlayersDTO> getTeams(int gameId) {
    return userDAO.getTeams(gameId);
  }

  /*
   * Retrieve player details for given game id and username.
   */
  @Override
  public GamePlayersDTO getTeam(int gameId, String user) {
    return userDAO.getTeam(gameId, user);
  }

  /*
   * Add player to team using specified team id.
   */
  @Override
  @Transactional
  public void addPlayerToTeam(int teamId, String user) {

    Player player = new Player(user);
    Team team = getTeam(teamId);
    player.setTeam(team);
    team.getPlayers().add(player);
    
    // Add the player to the database for the selected team.
    userDAO.save(team);
  }

  /*
   * Transform the team data to a team entity class object and save the team to
   * the database. Adds the current user as the first player of the team.
   */
  @Override
  @Transactional
  public Integer saveTeam(TeamDTO teamDTO) {

    Player player = new Player(teamDTO.getUsername());
    Team team = new Team(teamDTO.getGameId(), teamDTO.getTeamName(), "Fourball");
    player.setTeam(team);
    team.getPlayers().add(player);

    return userDAO.save(team);
  }

  /*
   * Retrieve all course and hole data for a given game to setup the scorecard
   * template.
   */
  @Override
  public List<ScorecardDTO> getScorecard(int gameId) {

    return userDAO.getScorecard(gameId);
  }

  /*
   * Update the pin GPS coordinates for specified hole.
   */
  @Override
  @Transactional
  public int updatePinLocation(int holeId, String locLat, String locLong) {

    return userDAO.updatePinLocation(holeId, locLat, locLong);
  }

}
