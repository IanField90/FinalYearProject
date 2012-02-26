/*
 Navicat Premium Data Transfer

 Source Server         : TeachReach
 Source Server Type    : MySQL
 Source Server Version : 50515
 Source Host           : localhost
 Source Database       : teachreach

 Target Server Type    : MySQL
 Target Server Version : 50515
 File Encoding         : utf-8

 Date: 02/26/2012 16:25:15 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `courses`
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name_en` text,
  `course_name_fr` text,
  `course_name_es` text,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `courses`
-- ----------------------------
BEGIN;
INSERT INTO `courses` VALUES ('2', 'Teamwork', 'Teamwork', 'Teamwork', '2011-12-09 16:37:23', '2011-12-15 14:09:20'), ('3', 'Course 2', 'Course 2', 'Course 2', '2011-12-15 14:15:54', '2011-12-15 14:15:54');
COMMIT;

-- ----------------------------
--  Table structure for `materials`
-- ----------------------------
DROP TABLE IF EXISTS `materials`;
CREATE TABLE `materials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `part_id` int(11) DEFAULT NULL,
  `material_en` text,
  `material_fr` text,
  `material_es` text,
  `material_type` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `materials`
-- ----------------------------
BEGIN;
INSERT INTO `materials` VALUES ('1', '1', 'abcdefghijklmnopqrstuv\r\n\r\nabcdefghijklmnopqrstuv\r\n\r\nabcdefghijklmnopqrstuv\r\n\r\nabcdefghijklmnopqrstuv', 'ab', 'a', null, '2011-12-15 13:41:25', '2011-12-15 14:30:16'), ('2', '1', 'b', 'b', 'b', null, '2011-12-15 13:54:31', '2011-12-15 13:54:31'), ('7', '3', 'Hello there', 'aah', 'aah', null, '2011-12-15 14:45:58', '2011-12-31 14:50:17');
COMMIT;

-- ----------------------------
--  Table structure for `options`
-- ----------------------------
DROP TABLE IF EXISTS `options`;
CREATE TABLE `options` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) DEFAULT NULL,
  `content_en` text,
  `content_fr` text,
  `content_es` text,
  `answer` tinyint(1) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `options`
-- ----------------------------
BEGIN;
INSERT INTO `options` VALUES ('1', '3', 'Option 1', 'option1', 'option1', '0', '2012-01-10 14:40:16', '2012-01-10 14:45:53'), ('2', '7', '3', '', '', '1', '2012-01-13 11:26:28', '2012-01-13 11:26:28'), ('3', '7', '5', '', '', '0', '2012-01-13 11:26:34', '2012-01-13 11:26:34'), ('4', '7', '6', '', '', '0', '2012-01-13 11:26:39', '2012-01-13 11:26:39'), ('8', '4', 'A', 'A', 'A', '0', '2012-02-21 13:05:09', '2012-02-21 13:05:09'), ('9', '4', 'B', 'B', 'B', '1', '2012-02-21 13:05:16', '2012-02-21 13:05:16'), ('10', '4', 'C', 'C', 'C', '0', '2012-02-21 13:05:24', '2012-02-21 13:05:24'), ('11', '5', 'True', 'NA', 'NA', '1', '2012-02-26 16:15:16', '2012-02-26 16:15:16'), ('12', '5', 'False', 'NA', 'NA', '0', '2012-02-26 16:15:24', '2012-02-26 16:15:24'), ('13', '8', 'A', 'NA', 'NA', '1', '2012-02-26 16:17:05', '2012-02-26 16:17:05'), ('14', '8', 'B', 'NA', 'NA', '1', '2012-02-26 16:17:17', '2012-02-26 16:17:17'), ('15', '8', 'C', 'NA', 'NA', '1', '2012-02-26 16:17:30', '2012-02-26 16:17:30'), ('16', '8', 'D', 'NA', 'NA', '1', '2012-02-26 16:17:56', '2012-02-26 16:17:56'), ('17', '8', 'E', 'NA', 'NA', '1', '2012-02-26 16:18:05', '2012-02-26 16:19:49'), ('18', '9', 'Observing and reflecting', 'NA', 'NA', '1', '2012-02-26 16:22:36', '2012-02-26 16:22:36'), ('19', '9', 'Understanding the reasons behind it', 'NA', 'NA', '1', '2012-02-26 16:22:59', '2012-02-26 16:22:59'), ('20', '9', 'Active experimentation \'having a go\'', 'NA', 'NA', '1', '2012-02-26 16:23:24', '2012-02-26 16:23:24'), ('21', '9', 'Doing and experimenting', 'NA', 'NA', '1', '2012-02-26 16:23:34', '2012-02-26 16:23:34');
COMMIT;

-- ----------------------------
--  Table structure for `parts`
-- ----------------------------
DROP TABLE IF EXISTS `parts`;
CREATE TABLE `parts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `part_name_en` text,
  `part_name_fr` text,
  `part_name_es` text,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `programme_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `parts`
-- ----------------------------
BEGIN;
INSERT INTO `parts` VALUES ('1', 'Perception', 'Perception', 'Perception', '2011-12-12 12:20:47', '2011-12-15 14:10:08', '9'), ('2', 'test2', 'test2', 'test2', '2011-12-12 12:21:08', '2011-12-12 12:21:08', '23'), ('3', 'Part 2', 'Part 2', 'Part 2', '2011-12-15 14:45:50', '2011-12-15 14:45:50', '9');
COMMIT;

-- ----------------------------
--  Table structure for `programmes`
-- ----------------------------
DROP TABLE IF EXISTS `programmes`;
CREATE TABLE `programmes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `programme_name_en` text,
  `programme_name_fr` text,
  `programme_name_es` text,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `programmes`
-- ----------------------------
BEGIN;
INSERT INTO `programmes` VALUES ('2', 'B', 'B', 'B', '2011-12-10 18:27:44', '2011-12-10 18:27:44', null), ('3', 'A', 'B', 'C', '2011-12-10 18:32:33', '2011-12-10 18:32:33', null), ('4', 'C', 'C', 'C', '2011-12-10 18:37:39', '2011-12-10 18:37:39', null), ('5', 'D', 'D', 'D', '2011-12-10 18:46:55', '2011-12-10 18:46:55', null), ('6', 'U', 'U', 'U', '2011-12-11 17:17:15', '2011-12-11 17:17:15', null), ('7', 'Te', 'Te', 'Te', '2011-12-11 17:33:03', '2011-12-11 17:33:03', null), ('8', 'ra', 'ra', 'ra', '2011-12-11 17:46:00', '2011-12-11 17:46:00', null), ('9', 'Leadership', 'Leadership', 'Leadership', '2011-12-11 20:27:19', '2011-12-15 14:09:42', '2'), ('13', 'ZZ', 'ZZ', 'ZZ', '2011-12-11 20:31:32', '2011-12-11 20:31:32', null), ('14', 'Z2', 'Z2', 'Z2', '2011-12-11 20:39:23', '2011-12-11 20:39:23', null), ('16', 'a', 'a', 'a', '2011-12-11 20:42:17', '2011-12-11 20:42:17', null), ('19', 'a', 'a', 'a', '2011-12-11 20:46:18', '2011-12-11 20:46:18', null), ('20', 'lul', 'lul', 'lul', '2011-12-11 20:48:48', '2011-12-11 20:48:48', null), ('21', 'test', 'test', 'test', '2011-12-11 20:56:02', '2011-12-11 20:56:02', null), ('22', 'test', 'test', 'test', '2011-12-11 20:56:41', '2011-12-11 20:56:41', null), ('23', 'test', 'test', 'test', '2011-12-11 21:04:56', '2011-12-11 21:04:56', '2'), ('24', 'test3', 'test3', 'test3', '2011-12-11 21:05:56', '2011-12-11 21:07:34', '24'), ('26', 'c', 'c', 'c', '2011-12-11 21:08:22', '2011-12-11 21:08:22', null), ('27', 'test2', 'test2', 'test2', '2011-12-11 21:15:41', '2011-12-11 21:15:41', '2'), ('28', 'testzor', '', '', '2011-12-11 21:18:40', '2011-12-11 21:18:40', '2');
COMMIT;

-- ----------------------------
--  Table structure for `questions`
-- ----------------------------
DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_en` text,
  `question_fr` text,
  `question_es` text,
  `quiz_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `feedback_en` text,
  `feedback_fr` text,
  `feedback_es` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `questions`
-- ----------------------------
BEGIN;
INSERT INTO `questions` VALUES ('1', '', '', '', null, '2012-01-10 10:23:15', '2012-01-10 10:23:15', null, '', '', ''), ('2', 'a', 'a', 'a', null, '2012-01-10 10:30:35', '2012-01-10 10:30:35', null, 'a', 'a', 'a'), ('4', 'One good thing a Leader should do is:\r\n\r\nA) Tell others what should be done.\r\n\r\nB) Allow a free exchange of ideas and support decision making.\r\n\r\nC) Not interfere with the group as they have specialist areas.', 'FR One good thing a Leader should do is:\r\n\r\nA) Tell others what should be done.\r\n\r\nB) Allow a free exchange of ideas and support decision making.\r\n\r\nC) Not interfere with the group as they have specialist areas.', 'ES One good thing a Leader should do is:\r\n\r\nA) Tell others what should be done.\r\n\r\nB) Allow a free exchange of ideas and support decision making.\r\n\r\nC) Not interfere with the group as they have specialist areas.', '1', '2012-01-13 11:20:44', '2012-02-26 16:13:14', '1', 'A leader always allows his team members to speak up and share their ideas and then support them in decision making to ensure that end goals are met. It is important that a leader does not dictate to others but it is equally important to spend time communicating. Communication skills and willingness and ability to coach and mentor others are essential characteristics of a successful leader.', 'Not available in French', 'Not available in Spanish'), ('5', 'Is a leader’s main responsibility to overcome the conflicts and challenges that arise during the course of a normal day, project etc.', 'Is a leader’s main responsibility to overcome the conflicts and challenges that arise during the course of a normal day, project etc.', 'Is a leader’s main responsibility to overcome the conflicts and challenges that arise during the course of a normal day, project etc.', '1', '2012-01-13 11:24:37', '2012-02-26 16:18:51', '1', 'Example Feedback', 'NA', 'NA'), ('7', 'What is 1 + 2?', '', '', '3', '2012-01-13 11:26:21', '2012-01-13 11:26:21', '1', '', '', ''), ('8', 'Order these qualities into the order that you feel are the most important for a leader to possess.\r\n\r\nA) Time Management\r\nB) Delegation\r\nC) Authority\r\nD) Communication\r\nE) Patience', 'NA', 'NA', '1', '2012-02-26 16:16:52', '2012-02-26 16:19:19', '3', 'These are all important aspects of leadership and will need to be used at different time for different purposes however communication is a particularly important skill.', 'NA', 'NA'), ('9', 'Please pair these options together:\r\n\r\nA) A reflector learns by...\r\n\r\nB) A theorist learns by...\r\n\r\nC) A Pragmatist learns by...\r\n\r\nD) An activist learns by...', 'NA', 'NA', '1', '2012-02-26 16:22:17', '2012-02-26 16:22:17', '3', 'Knowing how people learn best allows you to help them learn better as a leader.', 'NA', 'NA');
COMMIT;

-- ----------------------------
--  Table structure for `quizzes`
-- ----------------------------
DROP TABLE IF EXISTS `quizzes`;
CREATE TABLE `quizzes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `part_id` int(11) DEFAULT NULL,
  `name_en` text,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `name_fr` text,
  `name_es` text,
  `published` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `quizzes`
-- ----------------------------
BEGIN;
INSERT INTO `quizzes` VALUES ('1', null, '1', 'Leadership Quiz', '2012-01-05 12:52:37', '2012-02-21 13:02:19', 'Leadership Quiz', 'Prueba de Liderazgo', '1'), ('2', null, '1', 'test2', '2012-01-05 13:07:27', '2012-01-05 13:07:27', 'test2', 'test2', '0'), ('3', null, '1', 'Test 2', '2012-01-13 11:26:08', '2012-01-13 11:26:08', 'Test 2', 'Test 2', '0'), ('4', null, '1', 'test2', '2012-02-07 14:11:11', '2012-02-07 14:11:11', 'test2', 'test2', '0'), ('5', null, '1', 'test3', '2012-02-07 14:11:46', '2012-02-07 14:11:46', 'test3', 'test3', '1'), ('6', null, '2', 'testpart2', '2012-02-07 15:07:47', '2012-02-07 15:07:47', '', '', '1');
COMMIT;

-- ----------------------------
--  Table structure for `schema_migrations`
-- ----------------------------
DROP TABLE IF EXISTS `schema_migrations`;
CREATE TABLE `schema_migrations` (
  `version` varchar(255) NOT NULL,
  UNIQUE KEY `unique_schema_migrations` (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `schema_migrations`
-- ----------------------------
BEGIN;
INSERT INTO `schema_migrations` VALUES ('20110913155258'), ('20110914163106'), ('20110914164334'), ('20111003070414'), ('20111124093859'), ('20111124093941'), ('20111124094023'), ('20111124094528'), ('20111124094556'), ('20111124121434'), ('20111124121513'), ('20111124121704'), ('20111124122912'), ('20111124124415'), ('20111124124448'), ('20111124124707'), ('20120105121306'), ('20120105122638'), ('20120110095838'), ('20120111110313'), ('20120111110634'), ('20120111111508');
COMMIT;

-- ----------------------------
--  Table structure for `types`
-- ----------------------------
DROP TABLE IF EXISTS `types`;
CREATE TABLE `types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `types`
-- ----------------------------
BEGIN;
INSERT INTO `types` VALUES ('1', 'Multiple Choice', '2012-01-11 11:17:06', '2012-01-11 11:17:06'), ('2', 'Fill-in-the-blanks', '2012-01-11 11:17:06', '2012-01-11 11:17:06'), ('3', 'Match-up', '2012-01-11 11:17:06', '2012-01-11 11:17:06'), ('4', 'Slider/Opinion', '2012-01-11 11:17:06', '2012-01-11 11:17:06');
COMMIT;

-- ----------------------------
--  Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password_digest` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `auth_token` varchar(255) DEFAULT NULL,
  `password_reset_token` varchar(255) DEFAULT NULL,
  `password_reset_sent_at` datetime DEFAULT NULL,
  `admin` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `users`
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('1', 'test@example.com', '$2a$10$ilwjSaMpcn8VLUQqiZuCne7Od.4YzuIwK9.QtlC2mWgGqPYSEy2TW', '2011-11-19 17:21:36', '2011-11-19 17:21:36', '701d89c5f4ea203eebb3617954c58d59', null, null, '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
