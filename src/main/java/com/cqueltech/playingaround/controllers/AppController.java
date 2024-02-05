package com.cqueltech.playingaround.controllers;

import java.net.Authenticator.RequestorType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.cqueltech.playingaround.dto.CourseDTO;
import com.cqueltech.playingaround.dto.CourseDataWrapperDTO;
import com.cqueltech.playingaround.dto.GameDTO;
import com.cqueltech.playingaround.dto.GameDataDTO;
import com.cqueltech.playingaround.dto.GamePlayersDTO;
import com.cqueltech.playingaround.dto.HoleDataDTO;
import com.cqueltech.playingaround.dto.ScorecardDTO;
import com.cqueltech.playingaround.dto.TeamDTO;
import com.cqueltech.playingaround.dto.TeamsListDTO;
import com.cqueltech.playingaround.entity.Course;
import com.cqueltech.playingaround.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AppController {

  private UserService userService;

  /*
   * Inject the UserService using constructor injection. The UserService acts as
   * an intermediary layer between the controller and the DAO that accesses the
   * database.
   */
  public AppController(UserService userService) {
    this.userService = userService;
  }

  /*
   * Show the default home page after login.
   */
  @GetMapping("/home")
  public String showHome() {
    return "home";
  }

  /*
   * Mapping to display the create-game tmeplate. Applicable DTO class added to
   * model for transfer of game data from template to controller.
   */
  @GetMapping("/create-game")
  public String showCreateGame(Model model) {

    model.addAttribute("createGameData", new GameDataDTO());
    return "create-game";
  }

  /*
   * Mapping to retrieve list of courses that match search character string from
   * the create game form.
   */
  @RequestMapping(value = "/retrieveMatchingCourses", method = RequestMethod.GET)
  public ResponseEntity<?> getMatchingCourses(@RequestParam String search) {

    List<CourseDTO> courses = userService.searchCourses(search);
    return ResponseEntity.ok(courses);
  }

  /*
   * Mapping for submitting Create Game data to the database.
   */
  @PostMapping("/createNewGame")
  public String createGame(@Valid
                           @ModelAttribute("createGameData") GameDataDTO game,
                           BindingResult bindingResult,
                           Model model) {

    if (bindingResult.hasErrors()) {
      // Course name and/or game name are not valid entries. Add attribute to the
      // Spring model to display the error on the Create Game form.
      model.addAttribute("createGameError", "Game input fields are not valid");
      return "create-game";
    }

    // Check the course name exists in database.
    Course course = userService.findCourseByName(game.getCourseName());
    if (course == null) {
      model.addAttribute("createGameError", "Course does not exist.");
      return "create-game";
    }

    // Save the game to the database.
    userService.saveGame(course, game.getGameName());

    return "home";
  }

  /*
   * Mapping to display create-course template. Applicable DTO class added to model for transfer
   * of course and hole data from create-course template to controller.
   */
  @GetMapping("/create-course")
  public String showCreateCourse(Model model) {

    // Create DTO object for transfer of course data between the view and controller.
    CourseDataWrapperDTO courseDataWrapperDTO = new CourseDataWrapperDTO();
    List<HoleDataDTO> holeDataListLoad = new ArrayList<>();
    for (int hole=1; hole<=18; hole++) {
      holeDataListLoad.add(new HoleDataDTO(hole));
    }
    courseDataWrapperDTO.setHoleDataList(holeDataListLoad);

    // Add DTO object to model so that it is available to the create-course view.
    model.addAttribute("courseDataWrapperLoad", courseDataWrapperDTO);

    return "create-course";
  }

  /*
   * Mapping for submitting new course data to the database.
   */
  @PostMapping("/createNewCourse")
  public String createCourse(@Valid
                           @ModelAttribute("courseDataWrapperLoad") CourseDataWrapperDTO courseDataWrapper,
                           BindingResult bindingResult,
                           Model model) {

    // Check spring boot validation results. If errors found display create-course view
    // and populate it with previously entered data.
    if (bindingResult.hasErrors()) {
      model.addAttribute("courseDataWrapperLoad", courseDataWrapper);
      model.addAttribute("createCourseError", "Invalid entries when creating course...");
      return "create-course";
    }

    // Check course does not already exist.
    if (userService.findCourseByName(courseDataWrapper.getCourseName()) != null) {
      model.addAttribute("courseDataWrapperLoad", courseDataWrapper);
      model.addAttribute("createCourseError", "Course already exists...");
      return "create-course";
    }

    // Submit the new course and hole data to the database.
    userService.saveCourse(courseDataWrapper);
    
    return "home";
  }

  /*
   * Mapping for displaying the select-game template.
   */
  @GetMapping("/select-game")
  public String selectGame(Model model) {

    // Create DTO object for transfer of game data between the view and controller.
    //GameDataDTO gameDataDTO = new GameDataDTO();
    GameDTO gameDTO = new GameDTO();

    // Add DTO object to model so that it is available to the select-game view.
    model.addAttribute("gameData", gameDTO);

    return "select-game";
  }

  /*
   * Mapping to retrieve list of games that match search character string from
   * the select game form.
   */
  @RequestMapping(value = "/retrieveMatchingGames", method = RequestMethod.GET)
  public ResponseEntity<?> getMatchingGames(@RequestParam String search) {

    List<GameDTO> games = userService.searchGames(search);
    return ResponseEntity.ok(games);
  }

  /*
   * Mapping to validate the game string returned from 'select-game' view and display
   * the 'join-game' view.
   */
  @PostMapping("/join-game")
  public String joinGame(@Valid
                         @ModelAttribute("gameData") GameDTO gameDTO,
                         BindingResult bindingResult,
                         Model model) {
    
    if (bindingResult.hasErrors()) {
      model.addAttribute("selectGameError", "Invalid game name.");
      return "select-game";
    }

    // Determine if user has previously joined the game, if so go straight to
    // score-card template setting the necessary model attributes to pass course
    // and player data.
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    GamePlayersDTO playerDetails = userService.getTeam(gameDTO.getGameId(), auth.getName());

    if (playerDetails != null) {
      model.addAttribute("playerDetails", playerDetails);
      List<ScorecardDTO> scorecardData = userService.getScorecard(gameDTO.getGameId());
      model.addAttribute("scorecardData", scorecardData);
      return "score-card";
    }

    // Create model attribute to make game id available to join-game web view.
    model.addAttribute("gameId", gameDTO.getGameId());

    // Get all teams that have so far been created for the game.
    List<GamePlayersDTO> players = userService.getTeams(gameDTO.getGameId());

    // Transform the player list into list of objects, one for each team with
    // associated players.
    List<TeamsListDTO> teams = new ArrayList<>();
    if (players != null && players.size() > 0) {
      int teamId = players.get(0).getTeamId();
      String teamName = players.get(0).getTeamName();
      List<String> playerList = new ArrayList<>();

      for (GamePlayersDTO player : players) {
        if (teamId != player.getTeamId()) {
          teams.add(new TeamsListDTO(teamId, teamName, new ArrayList<>(playerList)));
          teamId = player.getTeamId();
          teamName = player.getTeamName();
          playerList.clear();  
        }
        playerList.add(player.getUsername());
      }
      teams.add(new TeamsListDTO(teamId, teamName, playerList));
    }

    // Create model attribute to make teams list available to join-game web view.
    model.addAttribute("teamsList", teams);

    return "join-game";
  }

  /*
   * Mapping to add user to a specified team. 
   */
  @RequestMapping(value = "/addPlayerToGame", method = RequestMethod.GET)
  public ResponseEntity<?> addUserToGame(@RequestParam int teamId) {

    log.info("Team id:" + teamId);

    // Get the username of current user.
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String user = auth.getName();

    // Add user to the selected team.
    userService.addPlayerToTeam(teamId, user);
    
    return ResponseEntity.ok(true);
  }

  /*
   * Mapping to create the new team DTO for the create-team template and then
   * display that template.
   */
  @GetMapping("/create-team")
  public String showCreateTeam(@RequestParam int gameId, Model model) {

    // Add DTO object to model to allow data transfer between create-team template
    // and controller.
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    TeamDTO team = new TeamDTO(gameId, null, auth.getName());
    model.addAttribute("teamData", team);

    return "create-team";
  }

  /*
   * Mapping to authenticate new team data and save the team to the database.
   */
  @PostMapping("/createNewTeam")
  public String createTeam(@ModelAttribute("teamData") TeamDTO teamDTO,
                           Model model) {
    
    // Add team to the database.
    Integer teamId = userService.saveTeam(teamDTO);

    // Display score-card template
    return "redirect:/score-card?gameId=" + teamDTO.getGameId();
  }

  /*
   * Mapping to dislay the score-card template.
   */
  @GetMapping("/score-card")
  public String showScoreCard(@RequestParam int gameId,
                              Model model) {

    // Get scorecard course
    List<ScorecardDTO> scorecardData = userService.getScorecard(gameId);

    // Get the player details, recorded hole scores, etc.
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    GamePlayersDTO playerDetails = userService.getTeam(gameId, auth.getName());

    // Create model attribute to make player and scorecard details available to
    // score-card template.
    model.addAttribute("scorecardData", scorecardData);
    model.addAttribute("playerDetails", playerDetails);

    return "score-card";
  }

  /*
   * Mapping to take GPS coordinates from mobile device and save them to the\
   * applicable hole.
   */
  @RequestMapping(value = "/addLocationToHole", method = RequestMethod.POST)
  public ResponseEntity<?> addLocationToHole(@RequestParam int holeId,
                                             @RequestParam String lat,
                                             @RequestParam String lon) {
    
    // Save the location data to the database.
    int rowsUpdated = userService.updatePinLocation(holeId, lat, lon);

    if (rowsUpdated == 1) {
      return ResponseEntity.ok(true);
    } else {
      return ResponseEntity.ok(false);
    }
  }
}
