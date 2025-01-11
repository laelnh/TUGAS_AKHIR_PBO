-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 29, 2024 at 06:00 PM
-- Server version: 8.0.40-0ubuntu0.22.04.1
-- PHP Version: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `snake_game`
--

-- --------------------------------------------------------

--
-- Table structure for table `leaderboard`
--

CREATE TABLE `leaderboard` (
  `id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `score` int NOT NULL,
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `leaderboard`
--

INSERT INTO `leaderboard` (`id`, `name`, `score`, `timestamp`) VALUES
(1, 'rany', 0, '2024-12-29 16:49:38'),
(2, 'rany', 0, '2024-12-29 16:49:38'),
(3, 'rany', 1, '2024-12-29 16:49:38'),
(4, 'rany', 13, '2024-12-29 16:49:38'),
(5, 'rany', 0, '2024-12-29 16:49:38'),
(6, 'lalal', 0, '2024-12-29 17:08:35'),
(7, 'lalal', 0, '2024-12-29 17:08:59'),
(8, 'lalal', 3, '2024-12-29 17:09:10'),
(9, 'lalal', 0, '2024-12-29 17:09:21'),
(10, 'oo', 0, '2024-12-29 17:14:06'),
(11, 'oo', 1, '2024-12-29 17:14:30'),
(12, 'oo', 0, '2024-12-29 17:14:35'),
(13, 'oo', 0, '2024-12-29 17:14:47'),
(14, 'oo', 0, '2024-12-29 17:14:57'),
(15, 'oo', 0, '2024-12-29 17:15:07'),
(16, 'oo', 9, '2024-12-29 17:15:49'),
(17, 'w', 7, '2024-12-29 17:44:04');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `leaderboard`
--
ALTER TABLE `leaderboard`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `leaderboard`
--
ALTER TABLE `leaderboard`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
