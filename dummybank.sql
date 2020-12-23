-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 23, 2020 at 09:39 AM
-- Server version: 10.4.16-MariaDB
-- PHP Version: 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dummybank`
--

-- --------------------------------------------------------

--
-- Table structure for table `datanasabah`
--

CREATE TABLE `datanasabah` (
  `id` bigint(20) NOT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `accountnumber` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `balance` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `datanasabah`
--

INSERT INTO `datanasabah` (`id`, `fullname`, `password`, `phonenumber`, `status`, `username`, `accountnumber`, `address`, `balance`) VALUES
(1, 'Jojo Wikoko', 'RInomor1', '081100001111', 'Active', 'maspresiden', '772020212101', 'Jakarta', 500000),
(2, 'Prabowo Sukianto', 'superdeffense007', '082200003333', 'Active', 'deffensiveline@indohebat.co.id', '772020212102', 'Jakarta', 500000),
(3, 'Ridwan Jamils', 'parisvanjava77', '087700770077', 'Active', 'rdwanjamil@bandungzzz.co.id', '772020212109', 'Bandung', 500000),
(4, 'Manies Basewedan', 'superdkijakarta1', '08667755664', 'Active', 'nies@gmail.com', '772020212106', 'Jakarta', 500000),
(5, 'Sri Malyuni', 'moneygold666', '08666111000', 'Active', 'srimul@menkeu.co.xyz', '772020212103', 'Jakarta', 500000),
(6, 'Mahfud D.M', 'King0FL4W', '081100110011', 'Active', 'dmfud@superlaw.co.id', '772020221014', 'Jakarta', 500000),
(11, 'Sasi Pudjiastuti', 'pirateking', '082211331313', 'Active', 'susissasi@susair.com', '772020212133', 'Lombok', 500000),
(22, 'Dyhan', 'dyhandhc', '087783877455', 'Active', 'dyhandhc', '7007209797', 'Bekasi', 500000);

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(23);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `datanasabah`
--
ALTER TABLE `datanasabah`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `accountnumber` (`accountnumber`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
