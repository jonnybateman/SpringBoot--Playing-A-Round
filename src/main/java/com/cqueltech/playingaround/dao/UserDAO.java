package com.cqueltech.playingaround.dao;

import java.util.List;
import com.cqueltech.playingaround.dto.CourseDTO;
import com.cqueltech.playingaround.dto.CourseParSiDTO;
import com.cqueltech.playingaround.dto.CommaDelimitedScoresDTO;
import com.cqueltech.playingaround.dto.GameDTO;
import com.cqueltech.playingaround.dto.GamePlayerDTO;
import com.cqueltech.playingaround.dto.HoleScoreDTO;
import com.cqueltech.playingaround.dto.PlayerDetailsDTO;
import com.cqueltech.playingaround.dto.PinLocationDTO;
import com.cqueltech.playingaround.dto.ScorecardDTO;
import com.cqueltech.playingaround.entity.Course;
import com.cqueltech.playingaround.entity.Game;
import com.cqueltech.playingaround.entity.Player;
import com.cqueltech.playingaround.entity.Team;
import com.cqueltech.playingaround.entity.User;

public interface UserDAO {

  void save(User user);
  
  List<CourseDTO> searchCourses(String search);

  Course findCourseByName(String courseName);

  void save(Course course);

  void save(Game game);

  List<GameDTO> searchGames(String search);

  GameDTO getGame(String gameName);

  Team getTeam(int teamId);

  List<GamePlayerDTO> getGamePlayers(int gameId);

  PlayerDetailsDTO getPlayerDetails(int gameId, String user);

  List<HoleScoreDTO> getPlayerScores(int teamId, String user);

  void save(Player player);

  Integer save(Team team);

  List<ScorecardDTO> getScorecard(int gameId);

  PinLocationDTO getPinLocationData(int holeId);

  int updatePinLocation(int holeId, String locLat, String locLong);

  void setLongestDrive(int playerId, int distance);

  void setHoleScore(int gameId, int teamId, int playerId, int hole, int score);

  List<CommaDelimitedScoresDTO> getDaytonaTeamScores(int gameId);

  List<CommaDelimitedScoresDTO> getStablefordTeamScores(int gameId);

  List<HoleScoreDTO> getStrokeplayPlayersScores(int gameId);

  List<HoleScoreDTO> getMatchplayTeamsScores(int gameId, int teamId);

  List<CourseParSiDTO> getCourseParsSi(int gameId);

  Float getHandicapIndex(String username);

  void postHandicapIndex(int playerId, float handicapIndex);
}
