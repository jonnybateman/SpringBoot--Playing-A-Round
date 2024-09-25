DROP SCHEMA IF EXISTS `playing-a-round`;

CREATE SCHEMA `playing-a-round`;

use `playing-a-round`;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `users` (
  `username` varchar(45) NOT NULL,
  `password` char(68) NOT NULL,
  `active` int NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `handicap` float,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `users`
VALUES
('john','{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q',1,'John','Smith'),
('mary','{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q',1,'Mary','Steinberg'),
('susan','{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q',1,'Susan','Black');

CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_USERNAME_ROLES` FOREIGN KEY (`username`) 
    REFERENCES `users` (`username`) 
    ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `roles`
VALUES
(1,'john','ADMIN'),
(2,'john','GOLFER'),
(3,'mike','GOLFER'),
(4,'susan','GOLFER');

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
);

CREATE TABLE `courses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(60) NOT NULL,
  `course_rating` float,
  `slope_rating` int,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `courses` VALUES (1,'Linlithgow Golf Club',67,116),
(2,'Silverknowes Golf Course',68.8,116),
(6,'Uphall Golf Club',70,113),
(7,'St Andrews Eden Course',67.2,115),
(8,'Cameron House The Carrick',70.3,122),
(9,'Dalmahoy East Course',72.7,127),
(10,'Dalmahoy West Course',64,115);

CREATE TABLE `holes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_id` int NOT NULL,
  `hole_no` int NOT NULL,
  `par` int NOT NULL,
  `si` int NOT NULL,
  `yards` int NOT NULL,
  `loc_lat` varchar(20),
  `loc_long` varchar(20),
  PRIMARY KEY (`id`),
  KEY `FK_COURSE_ID_idx` (`id`),
  CONSTRAINT `FK_COURSE_ID_HOLES` FOREIGN KEY (`course_id`)
    REFERENCES `courses` (`id`)
    ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `holes` VALUES (1,1,1,4,13,311,'55.966392','-3.622412'),
(2,1,2,4,6,336,NULL,NULL),
(3,1,3,4,9,270,NULL,NULL),
(4,1,4,4,4,369,NULL,NULL),
(5,1,5,4,17,294,NULL,NULL),
(6,1,6,4,12,226,NULL,NULL),
(7,1,7,3,14,149,NULL,NULL),
(8,1,8,3,8,157,NULL,NULL),
(9,1,9,4,1,370,NULL,NULL),
(10,1,10,3,7,219,NULL,NULL),
(11,1,11,4,15,309,NULL,NULL),
(12,1,12,5,3,490,NULL,NULL),
(13,1,13,5,10,479,NULL,NULL),
(14,1,14,4,18,270,NULL,NULL),
(15,1,15,4,11,247,NULL,NULL),
(16,1,16,4,2,410,NULL,NULL),
(17,1,17,3,16,173,NULL,NULL),
(18,1,18,4,5,403,NULL,NULL),
(19,2,1,4,8,363,NULL,NULL),
(20,2,2,3,16,153,NULL,NULL),
(21,2,3,4,11,296,NULL,NULL),
(22,2,4,4,13,257,NULL,NULL),
(23,2,5,4,5,379,NULL,NULL),
(24,2,6,5,9,491,NULL,NULL),
(25,2,7,4,1,421,NULL,NULL),
(26,2,8,3,18,106,NULL,NULL),
(27,2,9,4,3,434,NULL,NULL),
(28,2,10,4,15,250,NULL,NULL),
(29,2,11,4,10,341,NULL,NULL),
(30,2,12,4,12,315,NULL,NULL),
(31,2,13,4,2,397,NULL,NULL),
(32,2,14,4,7,357,NULL,NULL),
(33,2,15,3,17,147,NULL,NULL),
(34,2,16,4,4,466,NULL,NULL),
(35,2,17,4,14,276,NULL,NULL),
(36,2,18,5,6,493,NULL,NULL),
(38,6,6,4,10,273,NULL,NULL),
(39,6,5,4,16,267,'55.9855185','-3.7179859'),
(40,6,16,3,13,95,NULL,NULL),
(41,6,14,4,1,409,NULL,NULL),
(42,6,18,4,5,376,NULL,NULL),
(43,6,2,4,12,293,NULL,NULL),
(44,6,17,4,11,240,NULL,NULL),
(45,6,9,4,6,410,NULL,NULL),
(46,6,7,3,18,144,NULL,NULL),
(47,6,10,4,3,427,NULL,NULL),
(48,6,15,3,15,152,NULL,NULL),
(49,6,1,4,4,366,NULL,NULL),
(50,6,3,3,14,145,NULL,NULL),
(51,6,4,4,2,389,NULL,NULL),
(52,6,11,5,7,471,NULL,NULL),
(53,6,12,4,17,265,NULL,NULL),
(54,6,13,4,9,293,NULL,NULL),
(55,6,8,4,8,351,NULL,NULL),
(56,7,9,4,12,344,NULL,NULL),
(57,7,11,4,3,422,'55.9855077','-3.7180183'),
(58,7,13,4,7,313,NULL,NULL),
(59,7,10,4,9,407,NULL,NULL),
(60,7,1,4,14,303,NULL,NULL),
(61,7,5,4,10,435,NULL,NULL),
(62,7,15,5,2,504,NULL,NULL),
(63,7,4,4,1,454,NULL,NULL),
(64,7,7,3,18,145,NULL,NULL),
(65,7,2,4,6,428,NULL,NULL),
(66,7,18,4,11,261,NULL,NULL),
(67,7,16,3,17,154,NULL,NULL),
(68,7,8,4,4,367,NULL,NULL),
(69,7,3,3,16,205,NULL,NULL),
(70,7,6,4,8,270,NULL,NULL),
(71,7,14,3,15,179,NULL,NULL),
(72,7,12,4,13,415,NULL,NULL),
(73,7,17,4,5,365,NULL,NULL),
(74,8,5,5,14,498,'56.028982','-4.631287'),
(75,8,9,4,10,383,'56.039869','-4.640173'),
(76,8,13,4,9,406,'56.043641','-4.645084'),
(77,8,1,4,12,378,'56.036619','-4.638851'),
(78,8,6,3,16,142,'56.028439','-4.632528'),
(79,8,12,3,5,175,'56.039793','-4.643768'),
(80,8,15,5,3,498,'56.042504','-4.639125'),
(81,8,18,4,7,379,'56.040485','-4.636528'),
(82,8,7,4,8,398,'56.031287','-4.633607'),
(83,8,10,4,13,350,'56.042902','-4.642533'),
(84,8,17,4,11,381,'56.039482','-4.632080'),
(85,8,3,4,6,383,'56.036037','-4.638153'),
(86,8,4,4,4,352,'56.033263','-4.632912'),
(87,8,8,5,2,557,'56.035113','-4.638767'),
(88,8,2,3,18,138,'56.037369','-4.635006'),
(89,8,16,3,15,132,'56.041727','-4.637724'),
(90,8,11,5,1,531,'56.039068','-4.640841'),
(91,8,14,3,17,184,'56.045535','-4.643151'),
(92,9,13,4,1,428,NULL,NULL),
(93,9,15,4,11,410,NULL,NULL),
(94,9,8,4,12,345,NULL,NULL),
(95,9,16,4,3,410,NULL,NULL),
(96,9,1,5,8,479,NULL,NULL),
(97,9,4,3,18,139,NULL,NULL),
(98,9,10,5,15,477,NULL,NULL),
(99,9,2,4,4,423,NULL,NULL),
(100,9,6,4,6,390,NULL,NULL),
(101,9,7,3,10,173,NULL,NULL),
(102,9,12,4,13,396,NULL,NULL),
(103,9,3,4,2,415,NULL,NULL),
(104,9,14,4,5,371,NULL,NULL),
(105,9,18,5,9,447,NULL,NULL),
(106,9,11,4,7,426,NULL,NULL),
(107,9,5,4,16,306,NULL,NULL),
(108,9,9,5,14,468,NULL,NULL),
(109,9,17,3,17,181,NULL,NULL),
(110,10,10,3,14,161,NULL,NULL),
(111,10,9,3,17,167,NULL,NULL),
(112,10,8,4,2,422,NULL,NULL),
(113,10,13,5,1,531,NULL,NULL),
(114,10,18,4,9,303,NULL,NULL),
(115,10,16,3,16,110,NULL,NULL),
(116,10,4,4,3,375,NULL,NULL),
(117,10,14,4,12,307,NULL,NULL),
(118,10,3,3,18,121,NULL,NULL),
(119,10,12,4,6,339,NULL,NULL),
(120,10,6,4,13,267,NULL,NULL),
(121,10,17,3,11,165,NULL,NULL),
(122,10,5,4,8,306,NULL,NULL),
(123,10,11,4,4,359,NULL,NULL),
(124,10,15,4,7,327,NULL,NULL),
(125,10,7,4,15,255,NULL,NULL),
(126,10,1,4,5,267,NULL,NULL),
(127,10,2,4,10,269,NULL,NULL);

CREATE TABLE `handicap_calculation_parameters` (
  `no_of_score_differentials` int NOT NULL,
  `score_differentials_used` int NOT NULL,
  `adjustment` int NOT NULL,
  PRIMARY KEY (`no_of_score_differentials`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `handicap_calculation_parameters`
VALUES
(3, 1, 2),
(4, 1, 1),
(5, 1, 0),
(6, 2, 1),
(7, 2, 0),
(8, 2, 0),
(9, 3, 0),
(10, 3, 0),
(11, 3, 0),
(12, 4, 0),
(13, 4, 0),
(14, 4, 0),
(15, 5, 0),
(16, 5, 0),
(17, 6, 0),
(18, 6, 0),
(19, 7, 0),
(20, 8, 0);

CREATE TABLE `games` (
  `id` int NOT NULL AUTO_INCREMENT,
  `game_name` varchar(45) NOT NULL,
  `course_id` int NOT NULL,
  `game_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_COURSE_ID_GAMES` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
    ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `teams` (
  `id` int NOT NULL AUTO_INCREMENT,
  `game_id` int NOT NULL,
  `team_name` varchar(45) NOT NULL,
  `matchplay_mode` varchar(9) NOT NULL,
  `matchplay` varchar(73),
  `daytona` varchar(73),
  `stableford` varchar(35),
  PRIMARY KEY (`id`),
  KEY `FK_GAME_ID_idx` (`id`),
  CONSTRAINT `FK_GAME_ID` FOREIGN KEY (`game_id`)
    REFERENCES `games` (`id`)
    ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `players` (
  `id` int NOT NULL AUTO_INCREMENT,
  `team_id` int NOT NULL,
  `username` varchar(45) NOT NULL,
  `drive_distance` int,
  `handicap_index` float,
  `entry_date` timestamp DEFAULT now(),
  PRIMARY KEY (`id`),
  KEY `FK_TEAM_ID_idx` (`team_id`),
  CONSTRAINT `FK_TEAM_ID` FOREIGN KEY (`team_id`)
    REFERENCES `teams` (`id`)
    ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_USERNAME_PLAYERS` FOREIGN KEY (`username`)
    REFERENCES `users` (`username`)
    ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `hole_scores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int NOT NULL,
  `hole_no` int NOT NULL,
  `score` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PLAYER_ID_idx` (`player_id`),
  CONSTRAINT `FK_PLAYER_ID` FOREIGN KEY (`player_id`)
    REFERENCES `players` (`id`)
    ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

delimiter $$
CREATE TRIGGER trg_bu_player BEFORE UPDATE ON `playing-a-round`.players
FOR EACH ROW
BEGIN
	IF NEW.drive_distance <= OLD.drive_distance THEN
		SIGNAL SQLSTATE	'45000' SET MESSAGE_TEXT = 'Drive distance did not exceed previous drive.';
	END IF;
END;$$
delimiter ;

delimiter $$
CREATE TRIGGER trg_au_player AFTER UPDATE ON `playing-a-round`.players
FOR EACH ROW
BEGIN
  DECLARE v_score_differentials_used INT;
  DECLARE v_handicap_adjustment INT;
  DECLARE v_calculated_handicap FLOAT;
    
  IF NEW.handicap_index <> IFNULL(OLD.handicap_index, -99) THEN
		SELECT score_differentials_used, adjustment
			INTO v_score_differentials_used, v_handicap_adjustment
		FROM handicap_calculation_parameters
		WHERE no_of_score_differentials = (SELECT count(*)
										                  FROM players
                                      WHERE username = NEW.username
                                      AND handicap_index <> 0 LIMIT 20);

		IF v_score_differentials_used IS NOT NULL THEN
			SELECT ROUND(AVG(handicap_index), 1) AS average
				INTO v_calculated_handicap
			FROM (SELECT handicap_index
				  FROM players
				  WHERE username = NEW.username
          AND handicap_index IS NOT NULL
				  ORDER BY entry_date ASC, handicap_index ASC LIMIT v_score_differentials_used) AS players;
                  
			SET v_calculated_handicap := v_calculated_handicap - v_handicap_adjustment;
            
      UPDATE users
      SET handicap = v_calculated_handicap
      WHERE username = NEW.username;
    END IF;
	END IF;
END;$$
delimiter ;

delimiter $$
CREATE DEFINER=`root`@`localhost` FUNCTION `str_occurrence`(str VARCHAR(255), delim VARCHAR(255))
RETURNS int
DETERMINISTIC
COMMENT 'Counts the number of occurrences of a string within another string.
		Returns: >0 for 1 or more occurrences
			0 for no occurrences
			null for a null string value'
	RETURN round((length(str) - length(replace(str, delim, ''))) / length(delim));
$$
delimiter ;

delimiter $$
CREATE DEFINER=`root`@`localhost` FUNCTION `str_reverse`(str VARCHAR(255))
RETURNS varchar(255) CHARSET utf8mb4
DETERMINISTIC
BEGIN
    DECLARE indx INTEGER;
    DECLARE rtn_str VARCHAR(255) DEFAULT '';
    
    SET indx := length(str);
    WHILE indx >= 1 DO
	SET rtn_str := CONCAT(rtn_str, substring(str, indx, 1));
        SET indx := indx - 1;
    END WHILE;
    
    RETURN rtn_str;
END;
$$
delimiter ;

delimiter $$
CREATE DEFINER=`root`@`localhost` FUNCTION `splitstring_index`(str VARCHAR(255), delim VARCHAR(1), pos INTEGER)
RETURNS varchar(255) CHARSET utf8mb4
DETERMINISTIC
COMMENT 'Returns the string at index pos from a character delimited string.'
	IF pos > str_occurrence(str, delim) + 1 THEN
		RETURN null;
	ELSE
		RETURN substring_index(substring_index(str, delim, pos), delim, -1);
	END IF;
$$
delimiter ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `replace_delim_occurrence`(str VARCHAR(255),
								occurrence VARCHAR(255),
                                         			delim VARCHAR(1),
                                         			pos INT)
RETURNS varchar(255) CHARSET utf8mb4
DETERMINISTIC
COMMENT 'Replace an occurrence value of a delimited string with a new occurrence value.
		Returns: NULL if str is empty or null or pos exceeds number of occurrences.
			 new delimited string containing new occurrence in correct position.'
BEGIN
    DECLARE occurrences INT;
    DECLARE new_str VARCHAR(255) DEFAULT NULL;
    
    IF length(str) = 0 OR str IS NULL THEN
	SET occurrences := 0;
    ELSE
	SET occurrences = str_occurrence(str, delim) + 1;
    END IF;
    
    IF occurrences > 0 AND pos <= occurrences THEN
	CASE pos
		WHEN 1 THEN
                	SET new_str := concat(occurrence, delim, substring_index(str, delim, (0 - (occurrences - 1))));
		WHEN occurrences THEN
			SET new_str := concat(substring_index(str, delim, occurrences - 1), delim, occurrence);
		ELSE
			SET new_str := concat(substring_index(str, delim, pos - 1), delim, occurrence,
				delim, substring_index(str, delim, (0 - (occurrences - pos))));
	END CASE;
    END IF;
    
    RETURN new_str;			
END;
$$
DELIMITER ;

delimiter $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_update_daytona`(IN p_team_id INT,
									 IN p_player_id INT,
                                     IN p_hole INT,
                                     IN p_score INT)
daytona_label:BEGIN
	DECLARE v_daytona_scores VARCHAR(53);
    DECLARE v_daytona_score VARCHAR(2);
    DECLARE v_par INT;
    DECLARE v_partner_score INT;
    DECLARE v_birdie_for_flag BOOLEAN DEFAULT FALSE;
    DECLARE v_birdie_against_flag BOOLEAN DEFAULT FALSE;
    DECLARE v_record_count INT;
    DECLARE v_team_id INT;
    DECLARE v_cursor_team_id INT DEFAULT 0;
    DECLARE v_score_1 INT;
    DECLARE v_score_2 INT;
    DECLARE done BOOLEAN DEFAULT FALSE;
    DECLARE c_get_other_teams_scores CURSOR FOR
		SELECT team_id, daytona FROM temp_daytona WHERE team_id <> p_team_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    -- Drop the temporary table if it already exists.
    DROP TEMPORARY TABLE IF EXISTS temp_daytona;
    
    -- Get all teams daytona scores.
    SET @sql = concat(
		'CREATE TEMPORARY TABLE temp_daytona ENGINE=MEMORY AS ',
			'SELECT g.id AS game_id, t.id AS team_id, p.id AS player_id, s.score AS score,',
				't.daytona AS daytona ',
			'FROM games g JOIN teams t ON g.id = t.game_id ',
				'JOIN players p ON t.id = p.team_id ',
                'JOIN hole_scores s ON p.id = s.player_id ',
			'WHERE g.id = (select distinct(game_id) from teams where id = ', p_team_id, ') ',
            'AND s.hole_no = ', p_hole);
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
    
    -- Check that there are two players in the team. If not no point in
    -- calculating the daytona score.
    if (SELECT count(*) FROM temp_daytona WHERE team_id = p_team_id) <> 2 THEN
	DROP TEMPORARY TABLE IF EXISTS temp_daytona;
	LEAVE daytona_label;
    END IF;
    
	-- Determine if playing partner has score for hole. If so continue to generate
    -- daytona score for team, cannot calculate daytona score for hole unless both
    -- players have posted a score.
    SELECT IFNULL(
	(SELECT score
	FROM temp_daytona
	WHERE team_id = p_team_id
	AND player_id <> p_player_id), 0)
    INTO v_partner_score;
    
    IF v_partner_score = 0 THEN
	DROP TEMPORARY TABLE IF EXISTS temp_daytona;
        LEAVE daytona_label;
    END IF;
    
    -- For daytona scoring prevent double digit hole scores. Max hole score = 9.
    IF p_score > 9 THEN
	SET p_score := 9;
    END IF;
    IF v_partner_score > 9 THEN
	SET v_partner_score := 9;
    END IF;
    
    -- Get par for the hole.
    SELECT par INTO v_par
    FROM games g JOIN holes h ON g.course_id = h.course_id
    WHERE g.id = (SELECT game_id FROM temp_daytona LIMIT 1)
    AND h.hole_no = p_hole;
    
    -- Determine if player or partner shot sub par for the hole.
    IF p_score < v_par OR v_partner_score < v_par THEN
	SET v_birdie_for_flag := TRUE;
    END IF;
    
    -- If one of the players in the team scored a birdie or better then alter
    -- other teams Daytona score by flipping the hole scores around. Any other
    -- team that also scored a birdie or better would not have their Daytona
    -- score flipped.
    -- Also if another team scored a birdie and we did not then we would have
    -- our Daytona score flipped.
    SET v_record_count := (SELECT COUNT(*) FROM temp_daytona);
    IF v_record_count > 2 THEN -- other teams are involved in this game
	-- Determine if any other team shot sub par for the hole.
        SET v_record_count := (SELECT COUNT(*)
			  FROM temp_daytona
                          WHERE score < v_par
                          AND team_id <> p_team_id);
	IF v_record_count > 0 THEN
		SET v_birdie_against_flag := TRUE;
	END IF;
        
        -- Flip other teams scores if they did not shoot under par for the hole
        -- and the current team did. Only required if no other team has shot sub
        -- par for the hole.
        IF v_birdie_for_flag AND NOT v_birdie_against_flag THEN
		OPEN c_get_other_teams_scores;
            	cursor_loop: LOOP
                	FETCH c_get_other_teams_scores INTO v_team_id, v_daytona_scores;
                
                	IF done THEN
				LEAVE cursor_loop;
			END IF;
                
        	        IF v_team_id <> v_cursor_team_id THEN
				SET v_daytona_score := splitstring_index(v_daytona_scores, ',', p_hole);
				IF cast(v_daytona_score AS UNSIGNED INT) > 0 THEN
					SET v_score_1 := cast(substring(v_daytona_score, 1, 1) AS UNSIGNED INT);
					SET v_score_2 := cast(substring(v_daytona_score, 2, 1) AS UNSIGNED INT);
                    
					IF v_score_1 >= v_score_2 THEN
						SET v_daytona_score :=
							concat(cast(v_score_1 AS CHAR), cast(v_score_2 AS CHAR));
					ELSE
						SET v_daytona_score :=
							concat(cast(v_score_2 AS CHAR), cast(v_score_1 AS CHAR));
					END IF;
                    
					-- Update the Daytona score for the hole in the current team's Daytona
					-- scoring delimited string.
					SET v_daytona_scores := replace_delim_occurrence(
						v_daytona_scores, v_daytona_score, ',', p_hole);
                    
					-- Update the team Daytona score to the database.
					UPDATE teams
					SET daytona = v_daytona_scores
					WHERE id = v_team_id;
                    
					SET v_cursor_team_id := v_team_id;
				END IF;
                	END IF;
		END LOOP;
                    
		CLOSE c_get_other_teams_scores;
        END IF;
        
    END IF;
    
    -- Calculate team Daytona score.
    IF v_birdie_for_flag OR NOT v_birdie_against_flag THEN
	IF p_score <= v_partner_score THEN
		SET v_daytona_score := concat(cast(p_score AS CHAR), cast(v_partner_score AS CHAR));
	ELSE
		SET v_daytona_score := concat(cast(v_partner_score AS CHAR), cast(p_score AS CHAR));
	END IF;
    ELSE
	IF p_score >= v_partner_score THEN
		SET v_daytona_score := concat(cast(p_score AS CHAR), cast(v_partner_score AS CHAR));
	ELSE
		SET v_daytona_score := concat(cast(v_partner_score AS CHAR), cast(p_score AS CHAR));
	END IF;
    END IF;
    
    -- Get the Daytona scores for the team.
    SELECT DISTINCT daytona INTO v_daytona_scores FROM temp_daytona where team_id = p_team_id;
    
    -- Update the Daytona score for the hole in the team's Daytona scoring delimited string.
    SET v_daytona_scores := replace_delim_occurrence(
			v_daytona_scores, v_daytona_score, ',', p_hole);
    
    -- Update the Daytona scores for the team in the database.
    UPDATE teams
    SET daytona = v_daytona_scores
    WHERE id = p_team_id;
    
    -- Drop the temporary table.
    DROP TEMPORARY TABLE IF EXISTS temp_daytona;
END;
$$
delimiter ;

delimiter $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_update_stableford`(IN p_team_id INT,
									 IN p_game_id INT,
                                     IN p_hole INT,
                                     IN p_score INT)
stableford_label:BEGIN
	DECLARE v_par INT;
    DECLARE v_si INT;
    DECLARE v_score INT;
    DECLARE v_handicap FLOAT;
    DECLARE v_strokes INT;	
    DECLARE v_stableford VARCHAR(35);
    DECLARE v_stableford_score INT;
    DECLARE v_stableford_high_score INT;
    DECLARE done BOOLEAN DEFAULT FALSE;
    DECLARE c_get_player_details CURSOR FOR
		SELECT s.score, ifnull(u.handicap, 0)
        FROM players p JOIN hole_scores s ON p.id = s.player_id
			JOIN users u ON p.username = u.username
		WHERE p.team_id = p_team_id
        AND s.hole_no = p_hole;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	SELECT h.par, h.si, t.stableford INTO v_par, v_si, v_stableford
	FROM games g JOIN courses c ON g.course_id = c.id
		JOIN holes h ON c.id = h.course_id
		JOIN teams t ON g.id = t.game_id
	WHERE g.id = p_game_id
	AND h.hole_no = p_hole
    AND t.id = p_team_id;
    
    SET v_stableford_high_score := 0;
    
    -- Determine which player in the team has the highest stableford score
    -- for the hole.
    OPEN c_get_player_details;
    cursor_loop: LOOP
		FETCH c_get_player_details INTO v_score, v_handicap;
		IF done THEN
			LEAVE cursor_loop;
		END IF;
        
        SET v_strokes := 0;
        IF v_handicap >= v_si THEN
			SET v_strokes := v_strokes + 1;
            IF v_handicap >= 18 + v_si THEN
				SET v_strokes := v_strokes + 1;
				IF v_handicap >= 36 + v_si THEN
					SET v_strokes := v_strokes + 1;
				END IF;
			END IF;
		END IF;
        
        SET v_score := v_score - v_strokes;
        
        CASE
			WHEN v_score = v_par + 1 THEN
				SET v_stableford_score := 1;
			WHEN v_score = v_par THEN
				SET v_stableford_score := 2;
			WHEN v_score = v_par - 1 THEN
				SET v_stableford_score := 3;
			WHEN v_score = v_par - 2 THEN
				SET v_stableford_score := 5;
			WHEN v_score = v_par - 3 THEN
				SET v_stableford_score := 8;
			ELSE
				SET v_stableford_score := 0;
		END CASE;
        
        IF v_stableford_score > v_stableford_high_score THEN
			SET v_stableford_high_score := v_stableford_score;
		END IF;
	END LOOP;
    
    -- Set the highest stableford score to the team's stableford score string.
    SET v_stableford := replace_delim_occurrence(v_stableford,
												cast(v_stableford_high_score AS CHAR),
                                                ",",
                                                p_hole);
    
    -- Update the team record in the database with the new Stableford scores string.
    UPDATE teams
    SET stableford = v_stableford
    WHERE id = p_team_id;
    
END;
$$
delimiter ;

delimiter $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `debug_msg`(IN msg VARCHAR(255))
BEGIN
	SELECT concat('*debug->', msg) AS 'DEBUG MSG';
END;
$$
delimiter ;

GRANT EXECUTE ON PROCEDURE proc_update_daytona TO playingaround;
GRANT EXECUTE ON PROCEDURE proc_update_stableford TO playingaround;
GRANT TRIGGER ON trg_au_player TO playingaround;