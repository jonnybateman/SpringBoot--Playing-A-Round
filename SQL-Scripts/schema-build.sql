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

CREATE TABLE `courses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `courses` VALUES
(1, 'Linlithgow Golf Club'),
(2, 'Silverknowes Golf Course');

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

INSERT INTO `holes` VALUES
(1,1,1,4,13,311,null,null),
(2,1,2,4,6,336,null,null),
(3,1,3,4,9,270,null,null),
(4,1,4,4,4,369,null,null),
(5,1,5,4,17,294,null,null),
(6,1,6,4,12,226,null,null),
(7,1,7,3,14,149,null,null),
(8,1,8,3,8,157,null,null),
(9,1,9,4,1,370,null,null),
(10,1,10,3,7,219,null,null),
(11,1,11,4,15,309,null,null),
(12,1,12,5,3,490,null,null),
(13,1,13,5,10,479,null,null),
(14,1,14,4,18,270,null,null),
(15,1,15,4,11,247,null,null),
(16,1,16,4,2,410,null,null),
(17,1,17,3,16,173,null,null),
(18,1,18,4,5,403,null,null),
(19,2,1,4,8,363,null,null),
(20,2,2,3,16,153,null,null),
(21,2,3,4,11,296,null,null),
(22,2,4,4,13,257,null,null),
(23,2,5,4,5,379,null,null),
(24,2,6,5,9,491,null,null),
(25,2,7,4,1,421,null,null),
(26,2,8,3,18,106,null,null),
(27,2,9,4,3,434,null,null),
(28,2,10,4,15,250,null,null),
(29,2,11,4,10,341,null,null),
(30,2,12,4,12,315,null,null),
(31,2,13,4,2,397,null,null),
(32,2,14,4,7,357,null,null),
(33,2,15,3,17,147,null,null),
(34,2,16,4,4,466,null,null),
(35,2,17,4,14,276,null,null),
(36,2,18,5,6,493,null,null);

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
  `holes_played` int,
  `matchplay` varchar(73),
  `daytona` varchar(73),
  `stableford` int,
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
  `score_h1` int,
  `score_h2` int,
  `score_h3` int,
  `score_h4` int,
  `score_h5` int,
  `score_h6` int,
  `score_h7` int,
  `score_h8` int,
  `score_h9` int,
  `score_h10` int,
  `score_h11` int,
  `score_h12` int,
  `score_h13` int,
  `score_h14` int,
  `score_h15` int,
  `score_h16` int,
  `score_h17` int,
  `score_h18` int,
  PRIMARY KEY (`id`),
  KEY `FK_TEAM_ID_idx` (`team_id`),
  CONSTRAINT `FK_TEAM_ID` FOREIGN KEY (`team_id`)
    REFERENCES `teams` (`id`)
    ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_USERNAME_PLAYERS` FOREIGN KEY (`username`)
    REFERENCES `users` (`username`)
    ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;