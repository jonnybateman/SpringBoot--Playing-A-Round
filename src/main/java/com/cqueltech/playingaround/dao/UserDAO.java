package com.cqueltech.playingaround.dao;

import java.util.List;
import com.cqueltech.playingaround.dto.CourseDTO;
import com.cqueltech.playingaround.dto.GameDTO;
import com.cqueltech.playingaround.dto.GamePlayersDTO;
import com.cqueltech.playingaround.dto.ScorecardDTO;
import com.cqueltech.playingaround.entity.Course;
import com.cqueltech.playingaround.entity.Game;
import com.cqueltech.playingaround.entity.Player;
import com.cqueltech.playingaround.entity.Team;

public interface UserDAO {
  
  List<CourseDTO> searchCourses(String search);

  Course findCourseByName(String courseName);

  void save(Course course);

  void save(Game game);

  List<GameDTO> searchGames(String search);

  Team getTeam(int teamId);

  List<GamePlayersDTO> getTeams(int gameId);

  GamePlayersDTO getTeam(int gameId, String user);

  void save(Player player);

  Integer save(Team team);

  List<ScorecardDTO> getScorecard(int gameId);

  int updatePinLocation(int holeId, String locLat, String locLong);
}
