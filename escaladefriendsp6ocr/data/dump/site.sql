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
-- Structure de la table `site`
--

DROP TABLE IF EXISTS `site`;
CREATE TABLE IF NOT EXISTS `site` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cotation_max` varchar(255) DEFAULT NULL,
  `cotation_min` varchar(255) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `nb_voies` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `site_image` varchar(255) DEFAULT NULL,
  `certifie` bit(1) NOT NULL,
  `nb_secteurs` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `site`
--

INSERT INTO `site` (`id`, `cotation_max`, `cotation_min`, `description`, `nb_voies`, `nom`, `region`, `site_image`, `certifie`, `nb_secteurs`) VALUES
(1, '8b+', '3a', 'Toujours aussi spectaculaire et merveilleux', 126, 'Le Peyssin', 'Alpes', 'LePeyssin.jpg', b'1', 53),
(2, '7b+', '3a', 'Ce site n\'est pas de tout repos mais il en vaut la chandelle comme on dit, une expositions plein sud de la plupart des parois font que la luminosité y est splendide et ce toute la journée. N\'oubliez pas votre crème solaire', 115, 'Balme Yenne', 'Alpes', 'BalmeYenne.jpg', b'0', 47),
(3, '6b+', '3', 'Pour découvrir l\'escalade en famille ou entre amis, rien de tel que le si de l\'Autoire', 87, 'Autoire', 'Centre', 'Autoire.jpg', b'0', 24),
(4, '5b', '3', 'Très fatiguant malgré la faible cotation', 47, 'Le site qui fait mal', 'Bretagne', NULL, b'0', 18),
(5, '9a', '3', 'yep', 254, 'test site', 'test', NULL, b'0', 56);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
