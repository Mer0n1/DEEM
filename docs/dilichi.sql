-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 06, 2024 at 09:34 AM
-- Server version: 5.7.24
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dilichi`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `fathername` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `group_id` int(11) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `id_club` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `username`, `password`, `score`, `surname`, `fathername`, `name`, `group_id`, `role`, `id_club`) VALUES
(3, 'Meron', '123456', 137, 'Рани', '', 'Мерон', 1, 'ROLE_ADMIN', NULL),
(4, 'Tao', '123456', 127, 'Сакамото', '', 'Тао', 1, 'ROLE_HIGH', NULL),
(5, 'Rendi', '123456', 144, 'Рендиус', '', 'Ренди', 3, 'ROLE_STUDENT', 1),
(6, 'Miki', '123456', 145, 'Альби', '', 'Мичику', 2, 'ROLE_STUDENT', NULL),
(7, 'Makoto', '123456', 510, 'Макото', '', 'Мику', 6, 'ROLE_STUDENT', 1),
(8, 'Sera', '123456', 1910, 'Селавина', '', 'Сера', 6, 'ROLE_PRESIDENT_COUNCIL', NULL),
(9, 'Suto', '123456', 129, 'Каяно', '', 'Суто', 1, 'ROLE_STUDENT', NULL),
(10, 'Taro', '123456', 146, 'Такаеджи', '', 'Таро', 4, 'ROLE_STUDENT', NULL),
(11, 'Stella', '123456', 130, 'Аяла', '', 'Стелла', 5, 'ROLE_STUDENT', NULL),
(12, 'Sara', '123456', 151, 'Selavina', '', 'Сара', 2, 'ROLE_STUDENT', NULL),
(13, 'Aria', '123456', 136, 'Taku', '', 'Ария', 2, 'ROLE_STUDENT', NULL),
(15, 'Sacu', '123456', 149, 'Миямото', '', 'Саку', 1, 'ROLE_STUDENT', NULL),
(26, 'Denis', '123456', 20, 'Денис', '', 'Дейс', 8, 'ROLE_STUDENT', NULL),
(27, 'Kostya', '123456', 40, 'Высоцкий', 'Сергеевич', 'Константин', 8, 'ROLE_STUDENT', NULL),
(28, 'Sergo', '123456', 0, 'Колганов', '', 'Сергей', 1, 'ROLE_STUDENT', NULL),
(29, 'Vera', '123456', 24, 'Сыстырева', '', 'Вера', 8, 'ROLE_STUDENT', NULL),
(30, 'Test', '123456', 25, '', '', 'Тест', 8, 'ROLE_STUDENT', NULL),
(31, 'Test1', '123456', 0, '', '', 'Тест1', 8, 'ROLE_STUDENT', NULL),
(32, 'TeacherTest', '123456', 0, 'Test', 'Test', 'Test', 9, 'ROLE_TEACHER', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `account_chat`
--

CREATE TABLE `account_chat` (
  `account_id` int(11) NOT NULL,
  `chat_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `account_chat`
--

INSERT INTO `account_chat` (`account_id`, `chat_id`) VALUES
(3, 1),
(4, 1),
(4, 6),
(5, 6),
(4, 8),
(6, 8),
(4, 9),
(7, 9),
(3, 10),
(4, 10),
(5, 10),
(6, 10),
(7, 10),
(9, 10),
(10, 10),
(11, 10),
(26, 13),
(27, 13),
(28, 13),
(29, 13),
(30, 13),
(31, 13),
(4, 27),
(8, 27);

-- --------------------------------------------------------

--
-- Table structure for table `chat`
--

CREATE TABLE `chat` (
  `id` int(11) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `chat`
--

INSERT INTO `chat` (`id`, `Date`) VALUES
(1, '2023-10-09 09:21:39'),
(2, '2023-10-10 08:31:09'),
(3, '2023-10-23 11:19:23'),
(6, '2023-10-24 10:12:05'),
(8, '2023-10-24 11:15:55'),
(9, '2023-10-31 10:13:35'),
(10, '2023-12-04 11:56:48'),
(13, '2024-01-01 08:52:49'),
(20, '2024-02-25 11:23:53'),
(27, '2024-09-16 13:10:44');

-- --------------------------------------------------------

--
-- Table structure for table `class`
--

CREATE TABLE `class` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(10) NOT NULL,
  `place` varchar(50) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `class`
--

INSERT INTO `class` (`id`, `name`, `type`, `place`, `date`) VALUES
(1, 'Математика', 'л.', '306 к.', '2024-01-01 02:30:00'),
(2, 'Информатика', 'л.', '307 к.', '2024-01-02 04:00:00'),
(5, 'Math', 'л.', '305', '2024-12-31 12:11:24'),
(6, 'Math2', 'л.', '305', '2024-12-31 12:11:24');

-- --------------------------------------------------------

--
-- Table structure for table `club`
--

CREATE TABLE `club` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `date_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_group` int(11) DEFAULT NULL,
  `id_leader` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `club`
--

INSERT INTO `club` (`id`, `name`, `date_create`, `id_group`, `id_leader`) VALUES
(1, 'Клуб Журналистики', '2024-02-11 11:27:30', 10, 7),
(2, 'Клуб Труда', '2024-05-03 11:18:33', 15, 8);

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE `department` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `faculty_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `di_event`
--

CREATE TABLE `di_event` (
  `id` bigint(20) NOT NULL,
  `faculty` text NOT NULL,
  `type` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `publication_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `group_id` int(11) NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `course` int(11) NOT NULL,
  `id_exam` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `di_event`
--

INSERT INTO `di_event` (`id`, `faculty`, `type`, `name`, `description`, `publication_date`, `group_id`, `start_date`, `course`, `id_exam`) VALUES
(3, 'EPF', 'type', 'Экзамен по математике', 'Content', '2024-09-25 09:28:09', 1, '2024-10-17 10:25:07', 1, 1),
(4, 'EPF', 'type', 'Math Test2', 'context', '2024-06-23 10:55:04', 1, '2023-10-28 10:25:07', 1, NULL),
(5, 'EPF', 'type', 'Теоретический экзамен', 'Экзамен по истории, экономике, английскому и политологии', '2024-06-23 10:55:03', 1, '2024-03-15 02:00:00', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `di_group`
--

CREATE TABLE `di_group` (
  `group_id` int(11) NOT NULL,
  `faculty` varchar(255) DEFAULT NULL,
  `course` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `date_create` datetime(6) NOT NULL,
  `chat_id` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `di_group`
--

INSERT INTO `di_group` (`group_id`, `faculty`, `course`, `name`, `date_create`, `chat_id`, `type`, `description`) VALUES
(1, 'EPF', 1, 'G', '2023-08-23 19:44:49.000000', 10, 'standard', 'Группа G, цель - 1 место в рейтинге'),
(2, 'EPF', 1, 'M', '2023-08-23 19:44:49.000000', NULL, 'standard', NULL),
(3, 'EPF', 1, 'T', '2023-08-23 19:44:49.000000', NULL, 'standard', NULL),
(4, 'EPF', 1, 'N', '2023-08-23 19:44:49.000000', NULL, 'standard', NULL),
(5, 'EPF', 1, 'L', '2023-08-23 19:44:49.000000', NULL, 'standard', NULL),
(6, 'EPF', 4, 'B', '2020-08-23 19:44:49.000000', NULL, 'standard', NULL),
(8, 'EPC', 1, 'A', '2024-01-01 15:52:49.757000', 13, 'standard', NULL),
(9, 'EPF', 0, 'EPF', '2024-01-31 18:06:38.000000', NULL, 'admin', NULL),
(10, '', 0, 'JC', '2024-02-10 17:41:20.000000', NULL, 'club', 'Клуб журналистики это один из дочерних клубов студсовета'),
(11, '', 0, 'F', '2024-02-14 17:38:32.765000', NULL, 'studentcouncil', NULL),
(15, '', 0, 'JM', '2024-05-03 18:18:33.451000', NULL, 'club', '');

-- --------------------------------------------------------

--
-- Table structure for table `enrollment_form`
--

CREATE TABLE `enrollment_form` (
  `id` bigint(20) NOT NULL,
  `department` varchar(255) DEFAULT NULL,
  `faculty` varchar(255) DEFAULT NULL,
  `past_school` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `exam`
--

CREATE TABLE `exam` (
  `id` int(11) NOT NULL,
  `type` varchar(100) NOT NULL,
  `description` text,
  `duration` time NOT NULL,
  `address` varchar(256) DEFAULT NULL,
  `name` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `exam`
--

INSERT INTO `exam` (`id`, `type`, `description`, `duration`, `address`, `name`) VALUES
(1, 'Test', 'Test', '00:00:20', NULL, 'Экзамен по математике');

-- --------------------------------------------------------

--
-- Table structure for table `exclusion_form`
--

CREATE TABLE `exclusion_form` (
  `id` bigint(20) NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `id_student` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `faculty`
--

CREATE TABLE `faculty` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `faculty`
--

INSERT INTO `faculty` (`id`, `name`) VALUES
(1, 'EPC'),
(2, 'EPF');

-- --------------------------------------------------------

--
-- Table structure for table `group_creation_form`
--

CREATE TABLE `group_creation_form` (
  `id` bigint(20) NOT NULL,
  `department` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE `image` (
  `id` bigint(20) NOT NULL,
  `path` text NOT NULL,
  `name` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `uuid` varchar(225) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `image`
--

INSERT INTO `image` (`id`, `path`, `name`, `type`, `uuid`) VALUES
(1, 'D:\\Github\\Android apps\\Deem\\resources\\icons\\Meron.png', 'Meron', 'profile_icon', '31698c5ae136a0cc7adbbba94959e2d7d18c175598186e9463c9703c58b08da7'),
(5, 'D:/Github/Android apps/Deem/resources/messages', 'Test', 'message_image', 'cd4707bf6b17f4f53197f6b04bb72514f3b2bd08521e3ef390e2a3d81d2485f7'),
(6, 'D:/Github/Android apps/Deem/resources/messages', 'Test', 'message_image', '9642df458192be3d40d925c44e6d87ae1e2d06dfdeb024712c4a2f7734b2d7b2');

-- --------------------------------------------------------

--
-- Table structure for table `image_icon`
--

CREATE TABLE `image_icon` (
  `id` bigint(20) NOT NULL,
  `path` text NOT NULL,
  `uuid` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `image_icon`
--

INSERT INTO `image_icon` (`id`, `path`, `uuid`) VALUES
(7, 'D:/Github/Android apps/Deem/resources/icons/Meron.png', '31698c5ae136a0cc7adbbba94959e2d7d18c175598186e9463c9703c58b08da7'),
(8, 'D:/Github/Android apps/Deem/resources/icons/Tao.png', '80bd55a977a4c2ffdf89f9db8328fab9f5275516af156038cf2ca050d7483e27');

-- --------------------------------------------------------

--
-- Table structure for table `image_message`
--

CREATE TABLE `image_message` (
  `id` bigint(20) NOT NULL,
  `path` text NOT NULL,
  `uuid` varchar(150) NOT NULL,
  `id_message` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `image_message`
--

INSERT INTO `image_message` (`id`, `path`, `uuid`, `id_message`) VALUES
(1, 'D:/Github/Android apps/Deem/resources/messages/1.png', 'daa627364826ba00b6e5c22d4122714ae4158e721c958c2d25e0b896821bbe45', 18),
(2, 'D:/Github/Android apps/Deem/resources/messages/2.png', 'fa52f90dbfa687a10f5eeb951033b89080bb412e1cd8b89907166615bf93b396', 21),
(12, 'D:/Github/Android apps/Deem/resources/messages/3.png', 'd032b091ec5ec725566a935f6211c044840be2e3c350bff7535d0e71a929f256', 47),
(15, 'D:/Github/Android apps/Deem/resources/messages/13.png', '2077826c1c4825f88bca5fbb5ee6f56ced4ee40479049c8f8bb678b3fcbd5383', 50),
(16, 'D:/Github/Android apps/Deem/resources/messages/16.png', '403a038b2c7473ddaa0db89a58d07f3594f0985cbfc8ae4ad9aa6a7e29e89b53', 53),
(21, 'D:/Github/Android apps/Deem/resources/messages/17.png', 'cabdea207f2238892d05a37972b8889a3d3ed289a052eae07edfe3fc192ed6d9', 86),
(22, 'D:/Github/Android apps/Deem/resources/messages/22.png', 'bf832ebf25b46f28ee15f851a14fe783def9e3d297fe9d2d08b719132aba9516', 88);

-- --------------------------------------------------------

--
-- Table structure for table `image_news`
--

CREATE TABLE `image_news` (
  `id` bigint(20) NOT NULL,
  `path` text NOT NULL,
  `uuid` varchar(150) NOT NULL,
  `id_news` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `image_news`
--

INSERT INTO `image_news` (`id`, `path`, `uuid`, `id_news`) VALUES
(1, 'D:/Github/Android apps/Deem/resources/news/1.png', '29325061889601b08340a052bda1da7d8e020def0a2d9e9d785a10bf323db623', 5),
(3, 'D:/Github/Android apps/Deem/resources/news/3.png', '16628824eb699c4aef4b97308db0c1cb4f35f3b306b936901f71e6ac687a7b22', 22),
(9, 'D:/Github/Android apps/Deem/resources/news/4.png', 'afd5862b017c91888a28cb89232abef676be279af1ba5e8026b4f01df62fb3e9', 32),
(11, 'D:/Github/Android apps/Deem/resources/news/10.png', 'b7b1d75dfc06c5571b5ffb939224e1129f3a33800da93347a8c964267e9eca88', 44),
(12, 'D:/Github/Android apps/Deem/resources/news/12.png', '467716f85d6e6edee7456d8d652e84825b6d72b1842c8f115264dc319a645d3d', 45),
(13, 'D:/Github/Android apps/Deem/resources/news/13.png', 'e9f95c2f47e5d88e3a748b226ffd08c3ce4fe9d8fa633d907e9acc0ec39930f9', 46),
(15, 'D:/Github/Android apps/Deem/resources/news/14.png', '241bd3bb311a50e19c7b73fc64d4316e54b80051a008da914138367847688da9', 48);

-- --------------------------------------------------------

--
-- Table structure for table `list_exam_tallers`
--

CREATE TABLE `list_exam_tallers` (
  `id` bigint(20) NOT NULL,
  `id_account` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `list_exam_tallers`
--

INSERT INTO `list_exam_tallers` (`id`, `id_account`) VALUES
(1, 3),
(8, 4);

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `id` bigint(20) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `content` text,
  `id_chat` int(11) NOT NULL,
  `id_account` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id`, `Date`, `content`, `id_chat`, `id_account`) VALUES
(1, '2023-10-23 12:43:29', 'Привет, как дела?', 1, 3),
(2, '2023-10-23 12:43:32', 'Хорошо, а у тебя?', 1, 4),
(3, '2023-10-23 12:43:36', 'Test', 1, 3),
(6, '2023-10-23 12:43:34', 'Привет', 1, 4),
(7, '2023-10-24 09:18:35', 'овитв', 1, 4),
(8, '2023-10-24 10:12:05', 'привеомпн', 6, 4),
(10, '2023-10-24 11:15:55', 'Куртка', 8, 4),
(11, '2023-10-31 09:55:12', 'Test1', 1, 3),
(12, '2023-10-31 10:13:35', 'Тест3', 9, 4),
(13, '2023-11-05 12:07:41', 'ощи', 1, 3),
(14, '2023-11-05 12:13:44', 'Куку', 1, 3),
(15, '2023-11-07 11:08:58', 'Text', 1, 3),
(16, '2023-11-07 11:12:56', 'Text2', 1, 3),
(17, '2023-11-07 11:15:59', 'Хах', 1, 4),
(18, '2023-11-17 12:12:06', 'Мерон, это я, Тао', 1, 4),
(19, '2023-11-28 11:32:55', 'куку', 1, 4),
(20, '2023-11-28 11:36:44', 'кхм', 1, 4),
(21, '2023-11-28 11:42:27', 'хм', 1, 4),
(23, '2023-12-14 10:02:47', 'Привет всем', 10, 4),
(32, '2023-12-14 10:34:29', 'ку', 1, 4),
(33, '2023-12-14 10:34:39', 'такс', 1, 4),
(47, '2023-12-16 10:07:30', 'пр', 1, 4),
(50, '2023-12-16 11:22:08', 'т', 1, 3),
(51, '2024-02-25 11:21:40', 'Привет', 20, 3),
(53, '2024-04-25 10:41:44', 'прр', 1, 3),
(54, '2024-08-31 12:34:21', 'ку как ты?', 1, 3),
(55, '2024-09-25 10:07:28', 'Слышь ты че базаришь как чертила', 1, 3),
(56, '2024-09-25 10:08:31', 'Пойдем выйдем', 1, 3),
(57, '2024-09-25 10:08:44', 'Поясню тебе за статусы задач и ревью', 1, 3),
(58, '2024-09-25 10:09:15', 'А пошли', 1, 4),
(59, '2024-09-25 10:10:07', 'Только на этот рад без лидов, чисто один на один', 1, 4),
(60, '2024-09-25 10:10:45', 'В зум?', 1, 3),
(61, '2024-09-25 10:11:38', 'в зум', 1, 4),
(84, '2024-09-16 13:08:04', 'куку', 27, 8),
(86, '2024-09-16 13:13:37', 'но', 27, 8),
(87, '2024-09-27 12:24:45', 'ку', 1, 4),
(88, '2024-09-27 12:25:24', 'туту', 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `news`
--

CREATE TABLE `news` (
  `id` bigint(20) NOT NULL,
  `content` text,
  `group_id` int(11) DEFAULT NULL,
  `Date` datetime DEFAULT CURRENT_TIMESTAMP,
  `faculty` varchar(100) DEFAULT NULL,
  `author` int(11) NOT NULL,
  `course` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `news`
--

INSERT INTO `news` (`id`, `content`, `group_id`, `Date`, `faculty`, `author`, `course`) VALUES
(1, '', 1, '2023-09-08 14:06:19', 'EPF', 3, 1),
(3, 'Test23', 2, '2023-10-09 12:07:13', 'EPF', 6, 1),
(5, 'TOS', 1, '2024-01-10 11:55:44', 'EPF', 3, 1),
(6, 'То самое чувство когда не хочешь создавать валидатор потому что знаешь что с первого раза не создашь', 1, '2024-02-05 13:06:46', 'EPF', 3, 1),
(15, 'test', 2, '2024-03-10 18:07:11', 'EFC', 6, 1),
(16, 'С сегодняшнего дня появляются административные группы факультетов. Эти группы почти не отличаются от студенческих и клубных. Их отличие в том что они предназначены не для студентов, а для работников факультета. Итак, каждому работнику сообщается что новости от админ-групп появляются на первой странице приложения и являются приоритетом по сравнению с другими новостями!', 9, '2024-04-10 19:07:23', 'EPF', 32, NULL),
(22, 'Test', 1, '2024-05-19 20:18:48', 'EPF', 3, 1),
(32, 'id win', 1, '2024-06-10 19:09:49', 'EPF', 3, 1),
(44, 'это Аля', 6, '2024-09-10 19:07:04', 'EPF', 8, 4),
(45, 'тест', 6, '2024-09-10 19:39:08', 'EPF', 8, 4),
(46, 'приветик', 6, '2024-09-12 16:19:00', 'EPF', 8, 4),
(48, 'Тестирование альфы 2', 1, '2024-09-25 16:59:31', 'EPF', 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `statistic_score_history`
--

CREATE TABLE `statistic_score_history` (
  `id` bigint(20) NOT NULL,
  `id_account` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `score_this_month` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `statistic_score_history`
--

INSERT INTO `statistic_score_history` (`id`, `id_account`, `date`, `score_this_month`) VALUES
(1, 3, '2024-05-10 12:23:50', 0),
(2, 4, '2024-05-10 12:23:50', 0),
(3, 5, '2024-05-10 12:23:50', 0),
(4, 6, '2024-05-10 12:23:50', 0),
(5, 7, '2024-05-10 12:23:50', 0),
(6, 8, '2024-05-10 12:23:50', 0),
(7, 9, '2024-05-10 12:23:50', 0),
(8, 10, '2024-05-10 12:23:50', 0),
(9, 11, '2024-05-10 12:23:50', 0),
(10, 12, '2024-05-10 12:23:50', 0),
(11, 13, '2024-05-10 12:23:50', 0),
(12, 15, '2024-05-10 12:23:50', 0),
(13, 26, '2024-05-10 12:23:50', 0),
(14, 27, '2024-05-10 12:23:50', 0),
(15, 28, '2024-05-10 12:23:50', 200),
(16, 29, '2024-05-10 12:23:50', 0),
(17, 30, '2024-05-10 12:23:50', 0),
(18, 31, '2024-05-10 12:23:50', 0),
(19, 32, '2024-05-10 12:23:50', 0),
(20, 3, '2024-05-10 12:52:00', 0),
(21, 4, '2024-05-10 12:52:00', 0),
(22, 5, '2024-05-10 12:52:00', 0),
(23, 6, '2024-05-10 12:52:00', 0),
(24, 7, '2024-05-10 12:52:00', 0),
(25, 8, '2024-05-10 12:52:00', 0),
(26, 9, '2024-05-10 12:52:00', 0),
(27, 10, '2024-05-10 12:52:00', 0),
(28, 11, '2024-05-10 12:52:00', 0),
(29, 12, '2024-05-10 12:52:00', 0),
(30, 13, '2024-05-10 12:52:00', 0),
(31, 15, '2024-05-10 12:52:00', 0),
(32, 26, '2024-05-10 12:52:00', 0),
(33, 27, '2024-05-10 12:52:00', 0),
(34, 28, '2024-05-10 12:52:00', 200),
(35, 29, '2024-05-10 12:52:00', 0),
(36, 30, '2024-05-10 12:52:00', 0),
(37, 31, '2024-05-10 12:52:00', 0),
(38, 4, '2024-09-25 09:30:58', 100);

-- --------------------------------------------------------

--
-- Table structure for table `student_studcouncil`
--

CREATE TABLE `student_studcouncil` (
  `id` bigint(20) NOT NULL,
  `id_account` int(11) NOT NULL,
  `type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `student_studcouncil`
--

INSERT INTO `student_studcouncil` (`id`, `id_account`, `type`) VALUES
(1, 8, 'president');

-- --------------------------------------------------------

--
-- Table structure for table `submission_form`
--

CREATE TABLE `submission_form` (
  `id` bigint(20) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  `id_account` bigint(20) NOT NULL,
  `score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `submission_form`
--

INSERT INTO `submission_form` (`id`, `date`, `description`, `id_account`, `score`) VALUES
(1, '2024-04-07 11:44:33', NULL, 28, 100),
(2, '2024-05-10 10:38:23', NULL, 28, 100),
(3, '2024-05-10 10:38:24', NULL, 28, 100);

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `id` bigint(20) NOT NULL,
  `id_account` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `transfer_form`
--

CREATE TABLE `transfer_form` (
  `id` bigint(20) NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `id_student` bigint(20) NOT NULL,
  `id_group` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `update_history`
--

CREATE TABLE `update_history` (
  `id` bigint(20) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `update_history`
--

INSERT INTO `update_history` (`id`, `Date`) VALUES
(3, '2024-05-10 12:52:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `group_id` (`group_id`),
  ADD KEY `id_club` (`id_club`);

--
-- Indexes for table `account_chat`
--
ALTER TABLE `account_chat`
  ADD PRIMARY KEY (`account_id`,`chat_id`),
  ADD KEY `chat_id` (`chat_id`);

--
-- Indexes for table `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `club`
--
ALTER TABLE `club`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_group` (`id_group`);

--
-- Indexes for table `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `di_event`
--
ALTER TABLE `di_event`
  ADD PRIMARY KEY (`id`),
  ADD KEY `group_id` (`group_id`),
  ADD KEY `id_exam` (`id_exam`);

--
-- Indexes for table `di_group`
--
ALTER TABLE `di_group`
  ADD PRIMARY KEY (`group_id`),
  ADD UNIQUE KEY `name` (`name`),
  ADD KEY `chat_id` (`chat_id`);

--
-- Indexes for table `enrollment_form`
--
ALTER TABLE `enrollment_form`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exam`
--
ALTER TABLE `exam`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exclusion_form`
--
ALTER TABLE `exclusion_form`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `faculty`
--
ALTER TABLE `faculty`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `group_creation_form`
--
ALTER TABLE `group_creation_form`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `image_icon`
--
ALTER TABLE `image_icon`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `image_message`
--
ALTER TABLE `image_message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_message` (`id_message`);

--
-- Indexes for table `image_news`
--
ALTER TABLE `image_news`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_news` (`id_news`);

--
-- Indexes for table `list_exam_tallers`
--
ALTER TABLE `list_exam_tallers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_account` (`id_account`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_chat` (`id_chat`),
  ADD KEY `id_account` (`id_account`);

--
-- Indexes for table `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`id`),
  ADD KEY `group_id` (`group_id`),
  ADD KEY `author` (`author`);

--
-- Indexes for table `statistic_score_history`
--
ALTER TABLE `statistic_score_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_account` (`id_account`);

--
-- Indexes for table `student_studcouncil`
--
ALTER TABLE `student_studcouncil`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_account` (`id_account`);

--
-- Indexes for table `submission_form`
--
ALTER TABLE `submission_form`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transfer_form`
--
ALTER TABLE `transfer_form`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `update_history`
--
ALTER TABLE `update_history`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `chat`
--
ALTER TABLE `chat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `class`
--
ALTER TABLE `class`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `club`
--
ALTER TABLE `club`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `department`
--
ALTER TABLE `department`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `di_event`
--
ALTER TABLE `di_event`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `di_group`
--
ALTER TABLE `di_group`
  MODIFY `group_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `exam`
--
ALTER TABLE `exam`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `faculty`
--
ALTER TABLE `faculty`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `image`
--
ALTER TABLE `image`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `image_icon`
--
ALTER TABLE `image_icon`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `image_message`
--
ALTER TABLE `image_message`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `image_news`
--
ALTER TABLE `image_news`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `list_exam_tallers`
--
ALTER TABLE `list_exam_tallers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;

--
-- AUTO_INCREMENT for table `news`
--
ALTER TABLE `news`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `statistic_score_history`
--
ALTER TABLE `statistic_score_history`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `student_studcouncil`
--
ALTER TABLE `student_studcouncil`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `submission_form`
--
ALTER TABLE `submission_form`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `teacher`
--
ALTER TABLE `teacher`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `update_history`
--
ALTER TABLE `update_history`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `di_group` (`group_id`),
  ADD CONSTRAINT `account_ibfk_2` FOREIGN KEY (`id_club`) REFERENCES `club` (`id`);

--
-- Constraints for table `account_chat`
--
ALTER TABLE `account_chat`
  ADD CONSTRAINT `account_chat_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  ADD CONSTRAINT `account_chat_ibfk_2` FOREIGN KEY (`chat_id`) REFERENCES `chat` (`id`);

--
-- Constraints for table `club`
--
ALTER TABLE `club`
  ADD CONSTRAINT `club_ibfk_1` FOREIGN KEY (`id_group`) REFERENCES `di_group` (`group_id`);

--
-- Constraints for table `di_event`
--
ALTER TABLE `di_event`
  ADD CONSTRAINT `di_event_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `di_group` (`group_id`),
  ADD CONSTRAINT `di_event_ibfk_2` FOREIGN KEY (`id_exam`) REFERENCES `exam` (`id`);

--
-- Constraints for table `di_group`
--
ALTER TABLE `di_group`
  ADD CONSTRAINT `di_group_ibfk_1` FOREIGN KEY (`chat_id`) REFERENCES `chat` (`id`);

--
-- Constraints for table `image_message`
--
ALTER TABLE `image_message`
  ADD CONSTRAINT `image_message_ibfk_1` FOREIGN KEY (`id_message`) REFERENCES `message` (`id`);

--
-- Constraints for table `image_news`
--
ALTER TABLE `image_news`
  ADD CONSTRAINT `image_news_ibfk_1` FOREIGN KEY (`id_news`) REFERENCES `news` (`id`);

--
-- Constraints for table `list_exam_tallers`
--
ALTER TABLE `list_exam_tallers`
  ADD CONSTRAINT `list_exam_tallers_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`);

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`id_chat`) REFERENCES `chat` (`id`),
  ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`);

--
-- Constraints for table `news`
--
ALTER TABLE `news`
  ADD CONSTRAINT `news_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `di_group` (`group_id`),
  ADD CONSTRAINT `news_ibfk_2` FOREIGN KEY (`author`) REFERENCES `account` (`id`);

--
-- Constraints for table `statistic_score_history`
--
ALTER TABLE `statistic_score_history`
  ADD CONSTRAINT `statistic_score_history_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`);

--
-- Constraints for table `student_studcouncil`
--
ALTER TABLE `student_studcouncil`
  ADD CONSTRAINT `student_studcouncil_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
