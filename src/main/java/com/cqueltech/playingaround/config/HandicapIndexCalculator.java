package com.cqueltech.playingaround.config;

/*
 * A Bean component used in the calculation of a players handicap index for
 * a particular round/game.
 */

import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.cqueltech.playingaround.dto.CourseParSiDTO;
import com.cqueltech.playingaround.dto.HoleScoreDTO;
import com.cqueltech.playingaround.service.UserService;

@Slf4j
public class HandicapIndexCalculator {

  @Autowired
  private UserService userService;

  public HandicapIndexCalculator() {}

  /*
   * Calculate a course handicap index as per the World Handicap System 2020.
   */
  public void calculateHandicapIndex(int gameId, int teamId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // Retrieve player scores for the game. Only proceed if all holes have a posted
    // score.
    List<HoleScoreDTO> holeScores = userService.getPlayerScores(teamId, auth.getName());
    // Retrieve par and stroke index for each hole.
    List<CourseParSiDTO> coursePars = userService.getCourseParsSi(gameId);

    if (holeScores.size() == coursePars.size() &&
        coursePars.get(0).getCourseRating() != null &&
        coursePars.get(0).getSlopeRating() != null) {

      // Get player's existing handicap index
      Float handicapIndex = userService.getHandicapIndex(auth.getName());

      // Take a players round and adjust the score for each hole to give Adjusted
      // Gross
      // Score (AGS) as per WHS 2020.
      int ags = 0;
      for (int i = 0; i < coursePars.size(); i++) {
        int adjustmentStrokesForHole = getAdjustmentStrokesForHole(handicapIndex, coursePars.get(i).getSi());
        if (coursePars.get(i).getPar() + adjustmentStrokesForHole < holeScores.get(i).getScore()) {
          ags += (coursePars.get(i).getPar() + adjustmentStrokesForHole);
        } else {
          ags += holeScores.get(i).getScore();
        }
      }

      // Calculate the course handicap index according to WHS 2020.
      BigDecimal bd = new BigDecimal(((ags - coursePars.get(0).getCourseRating()) * 113) /
          coursePars.get(0).getSlopeRating());
      bd = bd.setScale(1, RoundingMode.HALF_UP);
      float courseHandicapIndex = bd.floatValue();

      // Post the course handicap index for the round to the database.
      userService.postHandicapIndex(holeScores.get(0).getPlayerId(), courseHandicapIndex);
    }
  }

  /*
   * Determine number of adjustment strokes for hole depending on stroke index and
   * current handicap index.
   */
  private int getAdjustmentStrokesForHole(Float handicapIndex, int strokeIndex) {

    int adjustmentStrokes = 0;
    if (handicapIndex == null || handicapIndex > 54) {
      // Gross adjustment strokes for no handicap or a handicap greater that 54.
      adjustmentStrokes = 5;
    } else {
      if (handicapIndex / strokeIndex >= 1) {
        adjustmentStrokes++;
        if (handicapIndex > 18 && (handicapIndex - 18) / strokeIndex >= 1) {
          adjustmentStrokes++;
          if (handicapIndex > 36 && (handicapIndex - 36) / strokeIndex >= 1) {
            adjustmentStrokes++;
          }
        }
      }
      // Add gross adjustment strokes for a handicap less then or equal to 54.
      adjustmentStrokes += 2;
    }

    return adjustmentStrokes;
  }
  
}
