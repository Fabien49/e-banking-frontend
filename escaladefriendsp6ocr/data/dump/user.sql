-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  lun. 19 avr. 2021 à 08:14
-- Version du serveur :  10.4.6-MariaDB
-- Version de PHP :  7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `escaladefriends`
--

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `code_postal` varchar(255) NOT NULL,
  `commune` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `niveau` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `voie` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`user_id`, `active`, `code_postal`, `commune`, `email`, `last_name`, `name`, `niveau`, `password`, `voie`) VALUES
(1, b'1', '75000', 'Paris', 'admin@admin.com', 'min', 'ad', '5c', '$2a$10$FVyWRBbaGu8fwFzYAqo5mOWh7rVnS7PYI4M1P7Tq645l/fxf4PJUa', '42 rue de l\'admin'),
(9, b'1', '75000', 'Paris', 'francois@gmail.com', 'Pignon', 'François', '5a', '$2a$10$bDhGuv698Gh72czX79Qx1O5C8Yi7Ng4MyZitnqxLzvxCL8p.r6P36', '31 rue de la colombre'),
(5, b'1', '69000', 'Lyon', 'cerise@gmail.com', 'Cerisier', 'Cerise', '2', '$2a$10$FFPLu93JG64qoxjCHTUl5unbU.kk1zp/egI4BVZ4ZpLpwh2gVifOO', '1 rue du cerisier');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
