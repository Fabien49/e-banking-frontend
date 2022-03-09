-- USER
INSERT INTO `user` (`user_id`, `active`, `code_postal`, `commune`, `email`, `last_name`, `name`, `niveau`, `password`, `voie`) VALUES (1, b'1', '75000', 'Paris', 'admin@admin.com', 'min', 'ad', '5c', '$2a$10$FVyWRBbaGu8fwFzYAqo5mOWh7rVnS7PYI4M1P7Tq645l/fxf4PJUa', '42 rue de l\'admin'),
INSERT INTO `user` (`user_id`, `active`, `code_postal`, `commune`, `email`, `last_name`, `name`, `niveau`, `password`, `voie`) VALUES (9, b'1', '75000', 'Paris', 'francois@gmail.com', 'Pignon', 'François', '5a', '$2a$10$bDhGuv698Gh72czX79Qx1O5C8Yi7Ng4MyZitnqxLzvxCL8p.r6P36', '31 rue de la colombre'),
INSERT INTO `user` (`user_id`, `active`, `code_postal`, `commune`, `email`, `last_name`, `name`, `niveau`, `password`, `voie`) VALUES (5, b'1', '69000', 'Lyon', 'cerise@gmail.com', 'Cerisier', 'Cerise', '2', '$2a$10$FFPLu93JG64qoxjCHTUl5unbU.kk1zp/egI4BVZ4ZpLpwh2gVifOO', '1 rue du cerisier');

-- ROLE
INSERT INTO `role` (`role_id`, `role`) VALUES (1, 'ADMIN'),
INSERT INTO `role` (`role_id`, `role`) VALUES (6, 'UTILISATEURCONNECTE'),
INSERT INTO `role` (`role_id`, `role`) VALUES (10, 'UTILISATEURCONNECTE');

-- USER_ROLE
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (1, 1),
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (5, 6),
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (9, 10);

--TOPO
INSERT INTO `topo` (`topo_id`, `nom`, `region`, `user_id`, `date`, `disponible`, `description`) VALUES (1, 'Super topo', 'Centre', 1, '2021-04-20 00:00:00', b'0', 'Il regroupe pas mal de site'),
INSERT INTO `topo` (`topo_id`, `nom`, `region`, `user_id`, `date`, `disponible`, `description`) VALUES (2, 'grimpette', 'Anjou', 5, '2020-07-15 00:00:00', b'0', 'ça monte dur'),
INSERT INTO `topo` (`topo_id`, `nom`, `region`, `user_id`, `date`, `disponible`, `description`) VALUES (3, 'Le topo de francois', 'Alsace', 9, '2021-04-07 00:00:00', b'0', 'il est vraiment complet'),
INSERT INTO `topo` (`topo_id`, `nom`, `region`, `user_id`, `date`, `disponible`, `description`) VALUES (5, 'test2', 'Centre', 1, '2009-02-02 00:00:00', b'0', 'ertfdfc'),
INSERT INTO `topo` (`topo_id`, `nom`, `region`, `user_id`, `date`, `disponible`, `description`) VALUES (6, 'derniertest', 'Bretagne', 1, '2017-03-25 00:00:00', b'1', 'vraiment le dernier?');

-- RESERVATION
INSERT INTO `reservation` (`id`, `demande_reservation`, `reserve`, `valider_reservation`, `topo_id`, `user_id_demande`) VALUES (3, b'0', b'1', b'1', 1, 5),
INSERT INTO `reservation` (`id`, `demande_reservation`, `reserve`, `valider_reservation`, `topo_id`, `user_id_demande`) VALUES (2, b'0', b'0', b'0', 2, 1);

--SITE
INSERT INTO `site` (`id`, `cotation_max`, `cotation_min`, `description`, `nb_voies`, `nom`, `region`, `site_image`, `certifie`, `nb_secteurs`) VALUES (1, '8b+', '3a', 'Toujours aussi spectaculaire et merveilleux', 126, 'Le Peyssin', 'Alpes', 'LePeyssin.jpg', b'1', 53),
INSERT INTO `site` (`id`, `cotation_max`, `cotation_min`, `description`, `nb_voies`, `nom`, `region`, `site_image`, `certifie`, `nb_secteurs`) VALUES (2, '7b+', '3a', 'Ce site n\'est pas de tout repos mais il en vaut la chandelle comme on dit, une expositions plein sud de la plupart des parois font que la luminosité y est splendide et ce toute la journée. N\'oubliez pas votre crème solaire', 115, 'Balme Yenne', 'Alpes', 'BalmeYenne.jpg', b'0', 47),
INSERT INTO `site` (`id`, `cotation_max`, `cotation_min`, `description`, `nb_voies`, `nom`, `region`, `site_image`, `certifie`, `nb_secteurs`) VALUES (3, '6b+', '3', 'Pour découvrir l\'escalade en famille ou entre amis, rien de tel que le si de l\'Autoire', 87, 'Autoire', 'Centre', 'Autoire.jpg', b'0', 24),

-- COMMENTAIRE
INSERT INTO `commentaire` (`commentaire_id`, `com`, `com_date`, `site_id`, `user_id`) VALUES (3, 'Que de souvenirs sur ce site', '2021-04-18 20:41:55', 1, 1),
INSERT INTO `commentaire` (`commentaire_id`, `com`, `com_date`, `site_id`, `user_id`) VALUES (7, 'pourquoi pas !ff', '2021-04-18 20:50:08', 2, 5),
INSERT INTO `commentaire` (`commentaire_id`, `com`, `com_date`, `site_id`, `user_id`) VALUES (8, 'oui on s\'est bien amusé', '2021-04-18 20:50:30', 1, 5);

