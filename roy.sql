-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 12, 2023 at 01:02 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `roy`
--

-- --------------------------------------------------------

--
-- Table structure for table `book_details`
--

CREATE TABLE `book_details` (
  `book_id` int(11) NOT NULL,
  `book_name` varchar(250) NOT NULL,
  `Author` varchar(250) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book_details`
--

INSERT INTO `book_details` (`book_id`, `book_name`, `Author`, `Quantity`) VALUES
(101, 'Wings of Fire', 'A.P.J Abdul Kalam', 9),
(102, 'Brave New World', 'Aldous Huxley', 0),
(103, 'To Kill a Mockingbird', ' Harper Lee', 6),
(104, 'Wings of Fire', 'A.P.J Abdul Kalam', 10),
(105, 'd', 'd', 0);

-- --------------------------------------------------------

--
-- Table structure for table `issue_book`
--

CREATE TABLE `issue_book` (
  `id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `book_name` varchar(200) NOT NULL,
  `student_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `issue_date` date NOT NULL,
  `due_date` date NOT NULL,
  `status` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `issue_book`
--

INSERT INTO `issue_book` (`id`, `book_id`, `book_name`, `student_id`, `name`, `issue_date`, `due_date`, `status`) VALUES
(1, 101, 'Wings of Fire', 1005, 'user', '2023-05-02', '2023-05-09', 'returned'),
(2, 102, 'Brave New World', 1005, 'user', '2023-05-02', '2023-05-09', 'returned'),
(3, 103, 'To Kill a Mockingbird', 1005, 'user', '2023-05-02', '2023-05-03', 'pending'),
(4, 102, 'Brave New World', 1002, 'Gautham', '2023-05-01', '2023-05-16', 'pending'),
(5, 101, 'Wings of Fire', 1002, 'Gauthamc', '2023-05-02', '2023-05-16', 'pending');

-- --------------------------------------------------------

--
-- Table structure for table `student_details`
--

CREATE TABLE `student_details` (
  `student_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `course` varchar(100) DEFAULT NULL,
  `branch` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_details`
--

INSERT INTO `student_details` (`student_id`, `name`, `course`, `branch`) VALUES
(1001, 'vyshnav', 'BCA', 'SCA'),
(1002, 'Gauthamc', 'BCA', 'SCA'),
(1003, 'Pranoy', 'MSC', 'IT'),
(1004, 'Roshit', 'MSC', 'SCA'),
(1005, 'fghj', 'MSC', 'SCA'),
(24355, 'dfdhfgh', 'BCA', 'CSE');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `contact` varchar(30) NOT NULL,
  `status` varchar(30) NOT NULL,
  `sissueDate` varchar(100) DEFAULT NULL,
  `details` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `password`, `email`, `contact`, `status`, `sissueDate`, `details`) VALUES
(1, 'vyshnav', 'vyshnav', 'vyshnav@gmail.com', '7025327675', 'admin', NULL, NULL),
(2, 'admin', 'admin', 'g@g.com', '9847159048', 'admin', NULL, NULL),
(3, 'v', 'v', 'v@g.com', '9847159048', 'admin', NULL, NULL),
(4, 'josh', '4554', 'webjoshua@gmail.com', '8369266944', 'admin', NULL, NULL),
(5, 'vwerty', '23456', 'A22@wfh.gh', '7025327675', 'admin', NULL, NULL),
(6, 'pooja', 'pooja', 'pooja@gmail.com', '1234567890', 'librarian', NULL, NULL),
(7, 'z', 'z', 'vy@gmil.com', '7025327675', 'librarian', NULL, NULL),
(8, 'user', 'u', 'vyshnav@g.co', '7025327675', 'user', NULL, NULL),
(9, 'Rohit', 'o', 'r@gmail.com', '9573553066', 'user', NULL, NULL),
(10, 'r', 'r', 'a@gmail.com', 'r', 'user', '2023-05-01', 'r'),
(11, 'r', 'o', 'r@gmail.com', 'r', 'user', '2023-05-10', 'r'),
(12, 'k', 'k', 'k@gmail.com', 'k', 'user', '2023-05-01', 'k'),
(13, 'qw', 'q', 'q@gmail.com', 'q', 'user', '2023-05-02', 'qwe'),
(14, 'wqeqwe', 'a', 'a@gmail.com', 'a', 'user', '2023-05-02', '');

-- --------------------------------------------------------

--
-- Table structure for table `user_key`
--

CREATE TABLE `user_key` (
  `key_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `student_id` int(100) NOT NULL,
  `key_value` varchar(100) NOT NULL,
  `key_status` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_key`
--

INSERT INTO `user_key` (`key_id`, `book_id`, `student_id`, `key_value`, `key_status`) VALUES
(1, 103, 1003, '1e0a3d01-ec28-4b44-9e73-d2d47556e052', 'pending'),
(2, 103, 1003, 'aeaa6b5a-2368-4137-a5bd-5ad331dee8a6', 'pending'),
(3, 103, 1003, '545c8be7-0160-4aa7-b165-334ff7295bcd', 'pending'),
(4, 103, 1004, '7131f7a6-7318-4e60-af1f-407ed9990666', 'pending'),
(5, 103, 1003, 'bb1cbecb-0c99-40a0-bfd2-57cec23d0af1', 'pending'),
(6, 103, 1003, '3a8ce259-74e5-4087-b804-6fba6c2981f4', 'approved'),
(7, 103, 1003, '39088429-f310-4f6e-8ab3-90aa0f477b0b', 'pending'),
(8, 103, 1002, '337fc9e5-4930-4541-94cc-6cd6b7b59777', 'pending');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book_details`
--
ALTER TABLE `book_details`
  ADD PRIMARY KEY (`book_id`);

--
-- Indexes for table `issue_book`
--
ALTER TABLE `issue_book`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student_details`
--
ALTER TABLE `student_details`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_key`
--
ALTER TABLE `user_key`
  ADD PRIMARY KEY (`key_id`),
  ADD KEY `user_key_ibfk_1` (`student_id`),
  ADD KEY `user_key_ibfk_2` (`book_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book_details`
--
ALTER TABLE `book_details`
  MODIFY `book_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123;

--
-- AUTO_INCREMENT for table `issue_book`
--
ALTER TABLE `issue_book`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `student_details`
--
ALTER TABLE `student_details`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24356;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `user_key`
--
ALTER TABLE `user_key`
  MODIFY `key_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_key`
--
ALTER TABLE `user_key`
  ADD CONSTRAINT `user_key_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student_details` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_key_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book_details` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
