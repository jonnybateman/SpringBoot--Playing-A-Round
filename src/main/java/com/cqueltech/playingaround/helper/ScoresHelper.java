package com.cqueltech.playingaround.helper;

/*
 * A Thymeleaf template helper class. An instance is passed to the web view via the
 * controller model. ScoresHelper is used in the manipulation of a List of HoleScore
 * objects. 
 */

import java.util.List;
import com.cqueltech.playingaround.dto.HoleScoreDTO;

public class ScoresHelper {

  public static ScoresHelper getInstance() {
    return new ScoresHelper();
  }

  public Integer getHoleScore(List<HoleScoreDTO> scores, int holeNo) {

    // Retrieve an object from the HoleScore list where the hole number attribute
    // is equal to the passed parameter holeNo
    HoleScoreDTO holeScore =
        scores.stream().filter((obj) -> obj.getHoleNo() == holeNo).findFirst().orElse(null);
    
    // If no object was found in the HoleScore list where the hole number attribute
    // is equal to the passed parameter holeNo then...
    if (holeScore == null) {
      return null;
    }

    // Return the hole score of the object retrieved from the list.
    return holeScore.getScore();
  }

}
