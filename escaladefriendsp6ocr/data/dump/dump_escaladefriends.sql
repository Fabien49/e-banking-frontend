-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  lun. 19 avr. 2021 à 08:21
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
-- Structure de la table `commentaire`
--

DROP TABLE IF EXISTS `commentaire`;
CREATE TABLE IF NOT EXISTS `commentaire` (
  `commentaire_id` bigint(20) NOT NULL,
  `com` varchar(500) DEFAULT NULL,
  `com_date` datetime DEFAULT NULL,
  `site_id` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`commentaire_id`),
  KEY `FK7poewyx0vupiexwj3o3v5r746` (`site_id`),
  KEY `FKnxdc2ad1nq0dstrheq6tmsoo6` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `commentaire`
--

INSERT INTO `commentaire` (`commentaire_id`, `com`, `com_date`, `site_id`, `user_id`) VALUES
(3, 'Que de souvenirs sur ce site', '2021-04-18 20:41:55', 1, 1),
(7, 'pourquoi pas !ff', '2021-04-18 20:50:08', 2, 5),
(8, 'oui on s\'est bien amusé', '2021-04-18 20:50:30', 1, 5);

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(28),
(28),
(28);

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `demande_reservation` bit(1) DEFAULT NULL,
  `reserve` bit(1) DEFAULT NULL,
  `valider_reservation` bit(1) DEFAULT NULL,
  `topo_id` bigint(20) NOT NULL,
  `user_id_demande` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs6rhp8bjbn9p9imq1k5fcpb6a` (`topo_id`),
  KEY `FKos39dukqhe36qsfj4x69hsctt` (`user_id_demande`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`id`, `demande_reservation`, `reserve`, `valider_reservation`, `topo_id`, `user_id_demande`) VALUES
(3, b'0', b'1', b'1', 1, 5),
(2, b'0', b'0', b'0', 2, 1);

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `role_id` int(11) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `role`
--

INSERT INTO `role` (`role_id`, `role`) VALUES
(1, 'ADMIN'),
(6, 'UTILISATEURCONNECTE'),
(10, 'UTILISATEURCONNECTE');

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

-- --------------------------------------------------------

--
-- Structure de la table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(5, 6),
(9, 10);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
