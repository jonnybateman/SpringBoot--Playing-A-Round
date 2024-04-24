package com.cqueltech.playingaround.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.cqueltech.playingaround.config.HandicapIndexCalculator;
import com.cqueltech.playingaround.dto.CourseDTO;
import com.cqueltech.playingaround.dto.CourseDataWrapperDTO;
import com.cqueltech.playingaround.dto.ScoresDTO;
import com.cqueltech.playingaround.dto.GameDTO;
import com.cqueltech.playingaround.dto.GameDataDTO;
import com.cqueltech.playingaround.dto.GamePlayerDTO;
import com.cqueltech.playingaround.dto.PlayerDetailsDTO;
import com.cqueltech.playingaround.dto.HoleDataDTO;
import com.cqueltech.playingaround.dto.HoleScoreDTO;
import com.cqueltech.playingaround.dto.ScorecardDTO;
import com.cqueltech.playingaround.dto.TeamDTO;
import com.cqueltech.playingaround.dto.TeamPlayersDTO;
import com.cqueltech.playingaround.entity.Course;
import com.cqueltech.playingaround.entity.HoleScore;
import com.cqueltech.playingaround.helper.ScoresHelper;
import com.cqueltech.playingaround.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;

@Controller
//@Slf4j
public class AppController {

  private UserService userService;
  private HandicapIndexCalculator handicapIndexCalculator;

  /*
   * Inject the UserService using constructor injection. The UserService acts as
   * an intermediary layer between the controller and the DAO that accesses the
   * database.
   * Inject the HandicapIndexCalculator using constructor injection.
   */
  @Autowired
  public AppController(UserService userService, HandicapIndexCalculator handicapIndexCalculator) {
    this.userService = userService;
    this.handicapIndexCalculator = handicapIndexCalculator;
  }

  /*
   * Show the default home page after login.
   */
  @GetMapping("/home")
  public String showHome(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    model.addAttribute("user", auth.getName());

    // Get handicap for the user.
    Float handicap = userService.getHandicapIndex(auth.getName());
    if (handicap != null) {
      // Cast float to string so that if handicap is 0.0 then it will be displayed rather
      // than an empty string in web view.
      model.addAttribute("handicap", handicap.toString());
    } else {
      model.addAttribute("handicap", handicap);
    }

    return "home";
  }

  /*
   * Game has been ended calculate the handicap index for the player.
   */
  @GetMapping("/calculateHandicapIndex")
  public String calculateHandicapIndex(@RequestParam int gameId,
                                       @RequestParam int teamId) {

    // Calculate the handicap index for the round and store it in the database.
    handicapIndexCalculator.calculateHandicapIndex(gameId, teamId);

    // Return to the home page from where a new game can be begun.
    return "redirect:/home";
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
   * Mapping to retrive a game using the specified game name from the select-game.html
   * template's game form
   */
  @RequestMapping(value = "/retrieveGame", method = RequestMethod.GET)
  public ResponseEntity<?> getGame(@RequestParam String gameName) {

    GameDTO game = userService.getGame(gameName);
    return ResponseEntity.ok(game);
  }

  /*
   * Mapping to validate the game string returned from 'select-game' view and display
   * the 'team-selection' or 'score-card' web views depending if the player has joined
   * the game before.
   */
  @PostMapping("/team-selection")
  public String selectTeam(@Valid
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
    PlayerDetailsDTO playerDetails = userService.getPlayerDetails(gameDTO.getGameId(), auth.getName());

    if (playerDetails != null) {
      // Retrieve scorecard data for the game.
      List<ScorecardDTO> scorecardData = userService.getScorecard(gameDTO.getGameId());
      // Retrieve any existing hole score records for the player.
      List<HoleScoreDTO> scores = userService.getPlayerScores(playerDetails.getTeamId(), auth.getName());

      // Add model attributes to make the data we have retrieved available to web view.
      model.addAttribute("playerDetails", playerDetails);
      model.addAttribute("scorecardData", scorecardData);
      model.addAttribute("scores", scores);
      model.addAttribute("scoresHelper", ScoresHelper.getInstance());
      
      return "score-card";
    }

    // Create model attribute to make game id available to team-selection template.
    model.addAttribute("gameId", gameDTO.getGameId());

    // Get all players that have so far been created for the game.
    List<GamePlayerDTO> gamePlayers = userService.getGamePlayers(gameDTO.getGameId());

    // Transform the player list into list of objects, one for each team with
    // associated players.
    List<TeamPlayersDTO> teams = new ArrayList<>();
    if (gamePlayers != null && gamePlayers.size() > 0) {
      int teamId = gamePlayers.get(0).getTeamId();
      String teamName = gamePlayers.get(0).getTeamName();
      List<String> teamPlayers = new ArrayList<>();

      for (GamePlayerDTO gamePlayer : gamePlayers) {
        if (teamId != gamePlayer.getTeamId()) {
          teams.add(new TeamPlayersDTO(teamId, teamName, teamPlayers));
          teamId = gamePlayer.getTeamId();
          teamName = gamePlayer.getTeamName();
          teamPlayers = new ArrayList<>();
        }
        teamPlayers.add(gamePlayer.getUsername());
      }
      teams.add(new TeamPlayersDTO(teamId, teamName, teamPlayers));
    }

    // Create model attribute to make teams list available to team-selection web view.
    model.addAttribute("teamsList", teams);

    return "team-selection";
  }

  /*
   * Mapping to add user to a specified team. 
   */
  @RequestMapping(value = "/addPlayerToGame", method = RequestMethod.GET)
  public ResponseEntity<?> addUserToGame(@RequestParam int teamId) {

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
    if (teamId != null && teamId > 0) {
      return "redirect:/score-card?gameId=" + teamDTO.getGameId() + "&teamId=" + teamId;
    } else {
      return "redirect:/create-team?gameId=" + teamDTO.getGameId();
    }
  }

  /*
   * Mapping to dislay the score-card template.
   */
  @GetMapping("/score-card")
  public String showScoreCard(@RequestParam int gameId,
                              @RequestParam int teamId,
                              Model model) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // Get scorecard course data
    List<ScorecardDTO> scorecardData = userService.getScorecard(gameId);
    // Retrieve player game and team details.
    PlayerDetailsDTO playerDetails = userService.getPlayerDetails(gameId, auth.getName());
    // Retrieve any hole score records for player.
    List<HoleScoreDTO> scores = userService.getPlayerScores(teamId, auth.getName());

    // Create model attributes to make necessary details available to
    // score-card web view.
    model.addAttribute("scorecardData", scorecardData);
    model.addAttribute("playerDetails", playerDetails);
    model.addAttribute("scores", scores);
    model.addAttribute("scoresHelper", ScoresHelper.getInstance());

    return "score-card";
  }

  /*
   * Mapping to retrieve pin location data for current hole.
   */
  @RequestMapping(value = "/getPinLocationData", method = RequestMethod.GET)
  public ResponseEntity<?> getPinLocationData(@RequestParam int holeId) {

    return ResponseEntity.ok(userService.getPinLocationData(holeId));
  }

  /*
   * Mapping to take GPS coordinates from mobile device and save them to the
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

  /*
   * Mapping to set drive distance in database. If the new drive distance is
   * greater than the existing drive distance then the distance value stored
   * in the players table will be updated.
   */
  @RequestMapping(value = "/setLongestDrive", method = RequestMethod.POST)
  public ResponseEntity<?> setLongestDrive(@RequestParam int playerId,
                              @RequestParam int distance) {
                              
    userService.setLongestDrive(playerId, distance);
    return ResponseEntity.ok(true);
  }

  /*
   * Mapping which takes a hole score and posts it to the database. Trigger
   * in the database will then update applicable game scores.
   */
  @RequestMapping(value = "/setHoleScore", method = RequestMethod.POST)
  public ResponseEntity<?> setHoleScore(@RequestParam int gameId,
                                        @RequestParam int teamId,
                                        @RequestParam int playerId,
                                        @RequestParam int holeId,
                                        @RequestParam int score) {

    userService.setHoleScore(gameId, teamId, playerId, holeId, score);
    return ResponseEntity.ok(true);
  }

  /*
   * Mapping to dislay the Daytona scoring template.
   */
  @GetMapping("/daytona")
  public String showDaytona(@RequestParam int gameId,
                            Model model) {

    // Retrieve the Daytona scoring information for each team in the game.
    List<ScoresDTO> daytonaTeamScoresList = userService.getDaytonaTeamScores(gameId);
    model.addAttribute("teamScoresList", daytonaTeamScoresList);

    return "daytona-stableford";
  }

  /*
   * Mapping to display the strokeplay scoring template.
   */
  @GetMapping("/strokeplay")
  public String showStrokeplay(@RequestParam int gameId,
                               Model model) {

    // Retrieve the strokeplay scoring data for each player in the game.
    List<ScoresDTO> strokeplayPlayersScoresList = userService.getStrokeplayPlayersScores(gameId);
    model.addAttribute("strokeplayPlayersScoresList", strokeplayPlayersScoresList);
    
    return "strokeplay";
  }

  /*
   * Mapping to display the Matchplay scoring template.
   */
  @GetMapping("/matchplay")
  public String showMatchplay(@RequestParam int gameId,
                              @RequestParam int teamId,
                              @RequestParam String teamName,
                              Model model) {

    // Retrieve the necessary scoring data for the Matchplay template.
    List<ScoresDTO> matchplayTeamsScoresList = userService.getMatchplayTeamsScores(gameId, teamId);
    ScoresDTO matchplayTeamScores = matchplayTeamsScoresList.stream().filter(
        obj -> teamName.equals(obj.getName())).findFirst().orElse(null);
    matchplayTeamsScoresList.removeIf(obj -> obj.getName().equals(teamName));

    // Add the data objects as attributes to the Spring model to make available
    // to the template.
    model.addAttribute("matchplayTeamScores", matchplayTeamScores);
    model.addAttribute("matchplayTeamsScoresList", matchplayTeamsScoresList);
    
    return "matchplay";
  }

  /*
   * Mapping to dislay the Stableford scoring template.
   */
  @GetMapping("/stableford")
  public String showStableford(@RequestParam int gameId,
                              Model model) {
  
    // Retrieve the Stableford scoring information for each team in the game.
    List<ScoresDTO> stablefordTeamScoresList = userService.getStablefordTeamScores(gameId);
    model.addAttribute("teamScoresList", stablefordTeamScoresList);

    return "daytona-stableford";
  }

}
