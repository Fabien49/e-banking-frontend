-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  lun. 19 avr. 2021 à 08:20
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
-- Structure de la table `topo`
--

DROP TABLE IF EXISTS `topo`;
CREATE TABLE IF NOT EXISTS `topo` (
  `topo_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `disponible` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`topo_id`),
  KEY `FKpurggp4l82two9ekgr11hx2qk` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `topo`
--

INSERT INTO `topo` (`topo_id`, `nom`, `region`, `user_id`, `date`, `disponible`, `description`) VALUES
(1, 'Super topo', 'Centre', 1, '2021-04-20 00:00:00', b'0', 'Il regroupe pas mal de site'),
(2, 'grimpette', 'Anjou', 5, '2020-07-15 00:00:00', b'0', 'ça monte dur'),
(3, 'Le topo de francois', 'Alsace', 9, '2021-04-07 00:00:00', b'0', 'il est vraiment complet'),
(5, 'test2', 'Centre', 1, '2009-02-02 00:00:00', b'0', 'ertfdfc'),
(6, 'derniertest', 'Bretagne', 1, '2017-03-25 00:00:00', b'1', 'vraiment le dernier?');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
