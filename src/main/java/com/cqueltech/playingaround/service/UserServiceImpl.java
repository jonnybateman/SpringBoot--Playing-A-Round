package com.cqueltech.playingaround.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cqueltech.playingaround.dao.UserDAO;
import com.cqueltech.playingaround.dto.CommaDelimitedScoresDTO;
import com.cqueltech.playingaround.dto.CourseDTO;
import com.cqueltech.playingaround.dto.CourseDataWrapperDTO;
import com.cqueltech.playingaround.dto.CourseParSiDTO;
import com.cqueltech.playingaround.dto.ScoresDTO;
import com.cqueltech.playingaround.dto.GameDTO;
import com.cqueltech.playingaround.dto.GamePlayerDTO;
import com.cqueltech.playingaround.dto.HoleDataDTO;
import com.cqueltech.playingaround.dto.HoleScoreDTO;
import com.cqueltech.playingaround.dto.NewUserDTO;
import com.cqueltech.playingaround.dto.PinLocationDTO;
import com.cqueltech.playingaround.dto.ScorecardDTO;
import com.cqueltech.playingaround.dto.TeamDTO;
import com.cqueltech.playingaround.dto.PlayerDetailsDTO;
import com.cqueltech.playingaround.entity.Course;
import com.cqueltech.playingaround.entity.Game;
import com.cqueltech.playingaround.entity.Hole;
import com.cqueltech.playingaround.entity.HoleScore;
import com.cqueltech.playingaround.entity.Player;
import com.cqueltech.playingaround.entity.Role;
import com.cqueltech.playingaround.entity.Team;
import com.cqueltech.playingaround.entity.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private UserDAO userDAO;

  @Autowired
  private PasswordEncoder passwordEncoder;

  /*
   * Inject the UserDAO component using constructor injection.
   */
  public UserServiceImpl(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  /*
   * Save new user and associated roles to the database.
   */
  @Override
  @Transactional
  public void save(NewUserDTO newUser) {

    // Encrypt the password using the BCryptPasswordEncoder.
    String password = passwordEncoder.encode(newUser.getPassword1());

    User user = new User(newUser.getUsername(),
                        password, 
                        1,
                        newUser.getFirstName(),
                        newUser.getLastName(),
                        newUser.getHandicap());

    Role role = new Role("GOLFER");
    role.setUser(user);
    user.getRoles().add(role);

    userDAO.save(user);
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
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String gameNamePrefix = auth.getName();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = dateFormat.format(new Date());
    Game game = new Game("(" + gameNamePrefix + ") " + gameName, date);
    game.setCourse(course);
    userDAO.save(game);
  }

  /*
   * Save new course and hole data to the database.
   */
  @Override
  @Transactional
  public void saveCourse(CourseDataWrapperDTO courseDataWrapperDTO) {

    // Setup Course and Hole entity classes.
    Course course = new Course();
    course.setCourseName(courseDataWrapperDTO.getCourseName());
    course.setCourseRating(courseDataWrapperDTO.getCourseRating());
    course.setSlopeRating(courseDataWrapperDTO.getSlopeRating());

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
   * Return a game as specified by the supplied game name.
   */
  @Override
  public GameDTO getGame(String gameName) {
    return userDAO.getGame(gameName);
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
  public List<GamePlayerDTO> getGamePlayers(int gameId) {
    return userDAO.getGamePlayers(gameId);
  }

  /*
   * Retrieve player and associated team details.
   */
  @Override
  public PlayerDetailsDTO getPlayerDetails(int gameId, String user) {
    return userDAO.getPlayerDetails(gameId, user);
  }

  /*
   * Retrieve player details for given game id and username.
   */
  public List<HoleScoreDTO> getPlayerScores(int teamId, String user) {

    return userDAO.getPlayerScores(teamId, user);
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
   * Get the pin GPS coordinates for the specified hole.
   */
  @Override
  public PinLocationDTO getPinLocationData(int holeId) {

    return userDAO.getPinLocationData(holeId);
  }

  /*
   * Update the pin GPS coordinates for specified hole.
   */
  @Override
  @Transactional
  public int updatePinLocation(int holeId, String locLat, String locLong) {

    return userDAO.updatePinLocation(holeId, locLat, locLong);
  }

  /*
   * Set the longest drive distance for the player.
   */
  @Override
  @Transactional
  public void setLongestDrive(int playerId, int distance) {

    userDAO.setLongestDrive(playerId, distance);
  }

  /*
   * Post player's score for a particular hole on the database.
   */
  @Override
  @Transactional
  public void setHoleScore(int gameId, int teamId, int playerId, int hole, int score) {

    userDAO.setHoleScore(gameId, teamId, playerId, hole, score);
  }

  /*
   * Retrieve the Daytona scores for each team in a specified game.
   */
  @Override
  public List<ScoresDTO> getDaytonaTeamScores(int gameId) {

    List<ScoresDTO> daytonaScoresDTOList = new ArrayList<>();
    List<CommaDelimitedScoresDTO> daytonaTeamScoresList = userDAO.getDaytonaTeamScores(gameId);

    // For each team, split the delimited scores string and asign the scores to the
    // applicable hole variables in an instance of the ScoresDTO class.
    for (CommaDelimitedScoresDTO teamDelimitedScores : daytonaTeamScoresList) {
      // Add the team scores object to the team scores list.
      daytonaScoresDTOList.add(convertScoresDelimitedString(teamDelimitedScores));
    }

    // Sort the daytonaTeamScoresList by scoreTotal ascending.
    Collections.sort(daytonaScoresDTOList, (o1, o2) -> o1.getScoreTotal() - o2.getScoreTotal());

    return daytonaScoresDTOList;
  }

  /*
   * Retrieve the Stableford scores for each team in a specified game.
   */
  @Override
  public List<ScoresDTO> getStablefordTeamScores(int gameId) {

    List<ScoresDTO> stablefordScoresDTOList = new ArrayList<>();
    List<CommaDelimitedScoresDTO> stablefordTeamScoresList = userDAO.getStablefordTeamScores(gameId);

    // For each team, split the delimited scores string and asign the scores to the
    // applicable hole variables in an instance of the ScoresDTO class.
    for (CommaDelimitedScoresDTO teamDelimitedScores : stablefordTeamScoresList) {
      // Add the team scores object to the team scores list.
      stablefordScoresDTOList.add(convertScoresDelimitedString(teamDelimitedScores));
    }

    // Sort the daytonaTeamScoresList by scoreTotal descending.
    Collections.sort(stablefordScoresDTOList, (o1, o2) -> o2.getScoreTotal() - o1.getScoreTotal());

    return stablefordScoresDTOList;
  }

  /*
   * Convert comma delimited scores string returned from database to a ScoresDTO instance.
   */
  private ScoresDTO convertScoresDelimitedString(CommaDelimitedScoresDTO scoresObject) {

    ScoresDTO scores = new ScoresDTO();
    Integer[] scoresArray = Arrays.stream(scoresObject.getScores().split(",")).map(Integer::parseInt)
        .toArray(Integer[]::new);
    scores.setName(scoresObject.getName());
    scores.setScoreH1(scoresArray[0]);
    scores.setScoreH2(scoresArray[1]);
    scores.setScoreH3(scoresArray[2]);
    scores.setScoreH4(scoresArray[3]);
    scores.setScoreH5(scoresArray[4]);
    scores.setScoreH6(scoresArray[5]);
    scores.setScoreH7(scoresArray[6]);
    scores.setScoreH8(scoresArray[7]);
    scores.setScoreH9(scoresArray[8]);
    scores.setScoreH10(scoresArray[9]);
    scores.setScoreH11(scoresArray[10]);
    scores.setScoreH12(scoresArray[11]);
    scores.setScoreH13(scoresArray[12]);
    scores.setScoreH14(scoresArray[13]);
    scores.setScoreH15(scoresArray[14]);
    scores.setScoreH16(scoresArray[15]);
    scores.setScoreH17(scoresArray[16]);
    scores.setScoreH18(scoresArray[17]);
    scores.setScoreTotal(Arrays.stream(scoresArray).mapToInt(Integer::intValue).sum());

    return scores;
  }

  /*
   * Retrieve the strokeplay scores for each player in a specified game.
   */
  @Override
  public List<ScoresDTO> getStrokeplayPlayersScores(int gameId) {

    // Retrieve the strokeplay scores for each player in game.
    List<HoleScoreDTO> holeScoreDTOList = userDAO.getStrokeplayPlayersScores(gameId);

    // Format the retrieved score data into a ScoresDTO object for each player.
    List<ScoresDTO> scoresDTOList = convertHoleScoreDTOList(holeScoreDTOList);

    // Sort the list using objects total score field in ascending order.
    Collections.sort(scoresDTOList, (o1, o2) -> o1.getScoreTotal() - o2.getScoreTotal());

    return scoresDTOList;
  }

  /*
   * For every team, except the user's team, retrieve the lowest score for each hole.
   */
  @Override
  public List<ScoresDTO> getMatchplayTeamsScores(int gameId, int teamId) {
    
    // Retrieve list of posted minimum score for each hole and for every team.
    List<HoleScoreDTO> holeScoreDTOList = userDAO.getMatchplayTeamsScores(gameId, teamId);

    // Format the retrieved score data into a ScoresDTO object for each team.
    List<ScoresDTO> scoresDTOList = convertHoleScoreDTOList(holeScoreDTOList);

    return scoresDTOList;
  }

  /*
   * Retrieve the par score for each hole of a given course as well as the Course
   * Rating and Slope Rating. Data utilized in the calculation of Handicap Index.
   */
  @Override
  public List<CourseParSiDTO> getCourseParsSi(int gameId) {

    return userDAO.getCourseParsSi(gameId);
  }

  /*
   * Retrieve a player's handicap index.
   */
  @Override
  public Float getHandicapIndex(String username) {

    return userDAO.getHandicapIndex(username);
  }

  /*
   * Post the calculated handicap index for a round to the database.
   */
  @Override
  @Transactional
  public void postHandicapIndex(int playerId, float handicapIndex) {

    userDAO.postHandicapIndex(playerId, handicapIndex);
  }

  /*
   * Convert a list of HoleScoreDTO objects into a list of ScoresDTO objects.
   * HoleScoreDTO objects are grouped by the property 'name'. Each group will
   * form a single ScoresDTO instance.
   */
  private List<ScoresDTO> convertHoleScoreDTOList(List<HoleScoreDTO> holeScoreDTOList) {

    List<ScoresDTO> scoresDTOList = new ArrayList<>();
    if (holeScoreDTOList != null) {
      String name = holeScoreDTOList.get(0).getName();
      ScoresDTO scoresDTO = new ScoresDTO();
      int scoreTotal = 0;

      for (HoleScoreDTO score : holeScoreDTOList) {
        if (!score.getName().equals(name)) {
          scoresDTO.setName(name);
          scoresDTO.setScoreTotal(scoreTotal);
          scoresDTO.setStrokeplayOut();
          scoresDTO.setStrokeplayIn();
          scoresDTOList.add(scoresDTO);
          scoresDTO = new ScoresDTO();
          name = score.getName();
          scoreTotal = 0;
        }
        switch (score.getHoleNo()) {
          case 1:
            scoresDTO.setScoreH1(score.getScore());
            break;
          case 2:
            scoresDTO.setScoreH2(score.getScore());
            break;
          case 3:
            scoresDTO.setScoreH3(score.getScore());
            break;
          case 4:
            scoresDTO.setScoreH4(score.getScore());
            break;
          case 5:
            scoresDTO.setScoreH5(score.getScore());
            break;
          case 6:
            scoresDTO.setScoreH6(score.getScore());
            break;
          case 7:
            scoresDTO.setScoreH7(score.getScore());
            break;
          case 8:
            scoresDTO.setScoreH8(score.getScore());
            break;
          case 9:
            scoresDTO.setScoreH9(score.getScore());
            break;
          case 10:
            scoresDTO.setScoreH10(score.getScore());
            break;
          case 11:
            scoresDTO.setScoreH11(score.getScore());
            break;
          case 12:
            scoresDTO.setScoreH12(score.getScore());
            break;
          case 13:
            scoresDTO.setScoreH13(score.getScore());
            break;
          case 14:
            scoresDTO.setScoreH14(score.getScore());
            break;
          case 15:
            scoresDTO.setScoreH15(score.getScore());
            break;
          case 16:
            scoresDTO.setScoreH16(score.getScore());
            break;
          case 17:
            scoresDTO.setScoreH17(score.getScore());
            break;
          case 18:
            scoresDTO.setScoreH18(score.getScore());
            break;
        }
        scoreTotal += score.getScore();
      }
      scoresDTO.setName(name);
      scoresDTO.setScoreTotal(scoreTotal);
      scoresDTO.setStrokeplayOut();
      scoresDTO.setStrokeplayIn();
      scoresDTOList.add(scoresDTO);
    }

    return scoresDTOList;
  }

}