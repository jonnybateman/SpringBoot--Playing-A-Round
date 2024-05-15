package com.cqueltech.playingaround.service;

/*
 * This Service layer lies between the Rest Controller and the DAO. It acts as an intermediate
 * layer for custom business logic. It is able to integrate data from multiple sources (DAOs/
 * repositories) and pass the data to our rest controller.
 * The service has the @Service annotation to allow Spring Boot to see it as a component
 * and automatically register it thanks to component scanning.
 */

import java.util.List;
import com.cqueltech.playingaround.dto.CourseDTO;
import com.cqueltech.playingaround.dto.CourseDataWrapperDTO;
import com.cqueltech.playingaround.dto.CourseParSiDTO;
import com.cqueltech.playingaround.dto.DriveDistanceDTO;
import com.cqueltech.playingaround.dto.ScoresDTO;
import com.cqueltech.playingaround.dto.GameDTO;
import com.cqueltech.playingaround.dto.GamePlayerDTO;
import com.cqueltech.playingaround.dto.HoleScoreDTO;
import com.cqueltech.playingaround.dto.PlayerDetailsDTO;
import com.cqueltech.playingaround.dto.NewUserDTO;
import com.cqueltech.playingaround.dto.PinLocationDTO;
import com.cqueltech.playingaround.dto.ScorecardDTO;
import com.cqueltech.playingaround.dto.TeamDTO;
import com.cqueltech.playingaround.entity.Course;
import com.cqueltech.playingaround.entity.Team;

public interface UserService {

  void save(NewUserDTO user);
  
  List<CourseDTO> searchCourses(String search);

  Course findCourseByName(String courseName);

  void saveGame(Course course, String gameName);

  void saveCourse(CourseDataWrapperDTO courseDataWrapper);

  List<GameDTO> searchGames(String search);

  GameDTO getGame(String gameName);

  Team getTeam(int teamId);

  List<GamePlayerDTO> getGamePlayers(int gameId);

  PlayerDetailsDTO getPlayerDetails(int gameId, String user);

  List<HoleScoreDTO> getPlayerScores(int teamId, String user);

  void addPlayerToTeam(int teamId, String user);

  Integer saveTeam(TeamDTO teamDTO);

  List<ScorecardDTO> getScorecard(int gameId);

  PinLocationDTO getPinLocationData(int holeId);

  int updatePinLocation(int holeId, String locLat, String locLong);

  void setLongestDrive(int playerId, int distance);

  void setHoleScore(int gameId, int teamId, int playerId, int hole, int score);

  List<ScoresDTO> getDaytonaTeamScores(int gameId);

  List<ScoresDTO> getStablefordTeamScores(int gameId);

  List<ScoresDTO> getStrokeplayPlayersScores(int gameId);

  List<ScoresDTO> getMatchplayTeamsScores(int gameId, int teamId);

  List<CourseParSiDTO> getCourseParsSi(int gameId);

  Float getHandicapIndex(String username);

  void postHandicapIndex(int playerId, float handicapIndex);

  List<DriveDistanceDTO> getLongestDrives(int gameId);
  
}