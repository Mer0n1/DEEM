-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jan 03, 2024 at 11:38 AM
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
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `username`, `password`, `score`, `surname`, `fathername`, `name`, `group_id`, `role`) VALUES
(3, 'Meron', '123456', 117, 'Рани', '', 'Мерон', 1, 'ROLE_ADMIN'),
(4, 'Tao', '123456', 127, 'Сакамото', '', 'Тао', 1, 'ROLE_HIGH'),
(5, 'Rendi', '123456', 144, 'Рендиус', '', 'Ренди', 3, 'ROLE_STUDENT'),
(6, 'Miki', '123456', 145, 'Альби', '', 'Мичику', 2, 'ROLE_STUDENT'),
(7, 'Makoto', '123456', 510, 'Макото', '', 'Мику', 6, 'ROLE_STUDENT'),
(8, 'Sera', '123456', 1910, 'Селавина', '', 'Сера', 6, 'ROLE_STUDENT'),
(9, 'Suto', '123456', 129, 'Каяно', '', 'Суто', 1, 'ROLE_STUDENT'),
(10, 'Taro', '123456', 146, 'Такаеджи', '', 'Таро', 4, 'ROLE_STUDENT'),
(11, 'Stella', '123456', 130, 'Аяла', '', 'Стелла', 5, 'ROLE_STUDENT'),
(12, 'Sara', '123456', 151, 'Selavina', '', 'Сара', 2, 'ROLE_STUDENT'),
(13, 'Aria', '123456', 136, 'Taku', '', 'Ария', 2, 'ROLE_STUDENT'),
(15, 'Sacu', '123456', 149, 'Миямото', '', 'Саку', 1, 'ROLE_STUDENT'),
(26, 'Denis', '123456', 0, 'Денис', '', 'Дейс', 8, 'ROLE_STUDENT'),
(27, 'Kostya', '123456', 0, 'Высоцкий', 'Сергеевич', 'Константин', 8, 'ROLE_STUDENT'),
(28, 'Sergo', '123456', 0, 'Колганов', '', 'Сергей', 8, 'ROLE_STUDENT'),
(29, 'Vera', '123456', 0, 'Сыстырева', '', 'Вера', 8, 'ROLE_STUDENT'),
(30, 'Test', '123456', 0, '', '', 'Тест', 8, 'ROLE_STUDENT'),
(31, 'Test1', '123456', 0, '', '', 'Тест1', 8, 'ROLE_STUDENT');

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
(8, 10),
(9, 10),
(10, 10),
(11, 10),
(26, 13),
(27, 13),
(28, 13),
(29, 13),
(30, 13),
(31, 13);

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
(13, '2024-01-01 08:52:49');

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
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `di_event`
--

INSERT INTO `di_event` (`id`, `faculty`, `type`, `name`, `description`, `publication_date`, `group_id`, `start_date`) VALUES
(3, 'EPF', 'type', 'Экзамен по математике', 'Content', '2023-12-31 11:55:01', 1, '2023-12-20 10:25:07'),
(4, 'EPF', 'type', 'Math Test2', 'context', '2023-11-15 12:43:30', 1, '2023-10-28 10:25:07'),
(5, 'EPF', 'type', 'Теоретический экзамен', 'Экзамен по истории, экономике, английскому и политологии', '2023-12-31 12:18:01', 1, '2024-01-15 02:00:00');

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
  `chat_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `di_group`
--

INSERT INTO `di_group` (`group_id`, `faculty`, `course`, `name`, `date_create`, `chat_id`) VALUES
(1, 'EPF', 1, 'G', '2023-08-23 19:44:49.000000', 10),
(2, 'EPF', 1, 'M', '2023-08-23 19:44:49.000000', NULL),
(3, 'EPF', 1, 'T', '2023-08-23 19:44:49.000000', NULL),
(4, 'EPF', 1, 'N', '2023-08-23 19:44:49.000000', NULL),
(5, 'EPF', 1, 'L', '2023-08-23 19:44:49.000000', NULL),
(6, 'EPC', 4, 'B', '2020-08-23 19:44:49.000000', NULL),
(8, 'EPC', 1, 'A', '2024-01-01 15:52:49.757000', 13);

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
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
(1, 'D:/Github/Android apps/Deem/resources/messages/1.png', 'daa627364826ba00b6e5c22d4122714ae4158e721c958c2d25e0b896821bbe45', 1),
(2, 'D:/Github/Android apps/Deem/resources/messages/2.png', 'fa52f90dbfa687a10f5eeb951033b89080bb412e1cd8b89907166615bf93b396', 2)
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
(1, 'D:/Github/Android apps/Deem/resources/news/1.png', '35eb1031a0a0bf4568ef5780516f75f510278691ed9fe28125713b7ccd6db01d', 5),
(2, 'D:/Github/Android apps/Deem/resources/news/2.png', '4f6e6e88f4c1e87337c8c29aac439fe9da8f69c36dd4ed9c9a1292b674af0ec3', 6);

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
(2, '2023-10-23 12:43:32', 'Хорошо, а у тебя?', 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `news`
--

CREATE TABLE `news` (
  `id` bigint(20) NOT NULL,
  `content` text,
  `group_id` int(11) DEFAULT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `faculty` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `news`
--

INSERT INTO `news` (`id`, `content`, `group_id`, `Date`, `faculty`) VALUES
(1, '', 1, '2024-01-03 09:35:14', 'EPF'),
(3, 'Test23', 2, '2023-10-28 11:04:42', 'EPF'),
(5, '', 1, '2023-11-28 09:20:22', 'EPF'),
(6, 'То самое чувство когда не хочешь создавать валидатор потому что знаешь что с первого раза не создашь', 1, '2023-12-13 14:38:20', 'EPF');

-- --------------------------------------------------------

--
-- Table structure for table `transfer_form`
--

CREATE TABLE `transfer_form` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `group_to` varchar(255) DEFAULT NULL,
  `id_student` bigint(20) DEFAULT NULL,
  `id_group` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `group_id` (`group_id`);

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
-- Indexes for table `di_event`
--
ALTER TABLE `di_event`
  ADD PRIMARY KEY (`id`),
  ADD KEY `group_id` (`group_id`);

--
-- Indexes for table `di_group`
--
ALTER TABLE `di_group`
  ADD PRIMARY KEY (`group_id`),
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
  ADD KEY `group_id` (`group_id`);

--
-- Indexes for table `transfer_form`
--
ALTER TABLE `transfer_form`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `chat`
--
ALTER TABLE `chat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `di_event`
--
ALTER TABLE `di_event`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `di_group`
--
ALTER TABLE `di_group`
  MODIFY `group_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `image_news`
--
ALTER TABLE `image_news`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `news`
--
ALTER TABLE `news`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `di_group` (`group_id`);

--
-- Constraints for table `account_chat`
--
ALTER TABLE `account_chat`
  ADD CONSTRAINT `account_chat_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  ADD CONSTRAINT `account_chat_ibfk_2` FOREIGN KEY (`chat_id`) REFERENCES `chat` (`id`);

--
-- Constraints for table `di_event`
--
ALTER TABLE `di_event`
  ADD CONSTRAINT `di_event_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `di_group` (`group_id`);

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
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`id_chat`) REFERENCES `chat` (`id`),
  ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`);

--
-- Constraints for table `news`
--
ALTER TABLE `news`
  ADD CONSTRAINT `news_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `di_group` (`group_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
