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
import com.cqueltech.playingaround.dto.GameDTO;
import com.cqueltech.playingaround.dto.GamePlayersDTO;
import com.cqueltech.playingaround.dto.ScorecardDTO;
import com.cqueltech.playingaround.dto.TeamDTO;
import com.cqueltech.playingaround.entity.Course;
import com.cqueltech.playingaround.entity.Team;

public interface UserService {
  
  List<CourseDTO> searchCourses(String search);

  Course findCourseByName(String courseName);

  void saveGame(Course course, String gameName);

  void saveCourse(CourseDataWrapperDTO courseDataWrapper);

  List<GameDTO> searchGames(String search);

  Team getTeam(int teamId);

  List<GamePlayersDTO> getTeams(int gameId);

  GamePlayersDTO getTeam(int gameId, String user);

  void addPlayerToTeam(int teamId, String user);

  Integer saveTeam(TeamDTO teamDTO);

  List<ScorecardDTO> getScorecard(int gameId);

  int updatePinLocation(int holeId, String locLat, String locLong);
}
