-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 16 mars 2021 à 15:13
-- Version du serveur :  10.4.11-MariaDB
-- Version de PHP : 7.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `tpspringboot`
--

-- --------------------------------------------------------

--
-- Structure de la table `ami`
--

CREATE TABLE `ami` (
  `user1_id` int(11) NOT NULL,
  `user2_id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `etat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `ami`
--

INSERT INTO `ami` (`user1_id`, `user2_id`, `date`, `etat`) VALUES
(99, 92, '2021-03-01', 2),
(99, 102, '2021-03-06', 3),
(102, 92, '2021-03-02', 2);

-- --------------------------------------------------------

--
-- Structure de la table `configuration`
--

CREATE TABLE `configuration` (
  `id` int(11) NOT NULL,
  `age_max` int(11) NOT NULL,
  `age_min` int(11) NOT NULL,
  `rayon` int(11) NOT NULL,
  `sexe` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `configuration`
--

INSERT INTO `configuration` (`id`, `age_max`, `age_min`, `rayon`, `sexe`, `user_id`) VALUES
(20, 80, 15, 100, 'Tout', 91),
(21, 80, 15, 100, 'Tout', 92),
(28, 80, 15, 100, 'Tout', 99),
(31, 31, 17, 100, 'M', 102);

-- --------------------------------------------------------

--
-- Structure de la table `position`
--

CREATE TABLE `position` (
  `id` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `position`
--

INSERT INTO `position` (`id`, `date`, `latitude`, `longitude`, `user_id`) VALUES
(122, '2021-02-03 14:58:47', 33.2427002, -8.4991002, 91),
(123, '2021-03-01 17:15:04', 31.6295, -7.9811, 92),
(124, '2021-02-03 15:08:02', 33.24271, -8.4990458, 91),
(125, '2021-02-03 15:08:19', 33.242762, -8.4990635, 91),
(126, '2021-02-03 15:10:04', 33.2427648, -8.4990951, 91),
(127, '2021-02-03 15:10:20', 33.2427619, -8.4990966, 91),
(128, '2021-02-03 15:11:20', 33.2427614, -8.4990969, 91),
(129, '2021-02-03 15:13:52', 33.2427434, -8.4990716, 91),
(130, '2021-02-03 15:18:02', 33.2427616, -8.4990997, 91),
(131, '2021-02-03 15:35:29', 33.2427375, -8.4991041, 91),
(133, '2021-02-24 12:38:45', 33.2427934, -8.4990835, 91),
(134, '2021-02-24 12:38:58', 33.242789, -8.4990892, 91),
(135, '2021-02-24 12:39:47', 33.2427944, -8.4990834, 91),
(136, '2021-02-24 12:39:58', 31.6295, -7.9811, 91),
(138, '2021-03-01 16:07:27', 33.2427266, -8.4990725, 91),
(139, '2021-03-01 16:07:57', 33.2437624, -8.4991678, 91),
(140, '2021-03-01 16:08:19', 33.2427523, -8.4990694, 91),
(141, '2021-03-01 16:09:24', 33.2427328, -8.4990465, 91),
(142, '2021-03-01 16:09:24', 33.2427328, -8.4990465, 91),
(143, '2021-03-01 16:11:34', 33.242763, -8.4990541, 91),
(144, '2021-03-01 16:11:34', 33.242763, -8.4990541, 91),
(145, '2021-03-01 16:11:44', 33.242773, -8.4990378, 91),
(146, '2021-03-01 16:13:27', 33.2427228, -8.4990533, 91),
(147, '2021-03-01 17:14:14', 33.2427419, -8.4990496, 99),
(148, '2021-03-06 12:35:42', 33.2427833, -8.499079, 92),
(149, '2021-03-01 16:37:16', 33.2427382, -8.4990611, 91),
(150, '2021-03-01 16:38:47', 33.2427813, -8.4990825, 91),
(151, '2021-03-01 16:39:30', 33.2427866, -8.499083, 91),
(152, '2021-03-01 16:39:35', 33.2427771, -8.4990376, 91),
(153, '2021-03-01 16:40:30', 33.24278, -8.4990369, 91),
(154, '2021-03-01 16:41:11', 33.2427823, -8.4990595, 91),
(155, '2021-03-01 16:41:21', 33.2427719, -8.4990526, 91),
(156, '2021-03-01 16:46:19', 33.242725, -8.4990516, 91),
(157, '2021-03-01 16:46:44', 33.2427551, -8.4990377, 91),
(158, '2021-03-01 16:47:42', 33.2427603, -8.4990701, 91),
(159, '2021-03-01 16:48:19', 33.2427265, -8.4990815, 91),
(160, '2021-03-01 17:15:04', 33.2427694, -8.4990416, 99),
(161, '2021-03-01 17:15:40', 33.2427335, -8.4990348, 99),
(162, '2021-03-01 17:16:22', 33.2427683, -8.4990553, 99),
(163, '2021-03-01 17:16:41', 33.2427555, -8.4990618, 99),
(164, '2021-03-01 17:17:11', 33.2427717, -8.4990689, 99),
(165, '2021-03-01 17:17:16', 33.2427462, -8.4990716, 99),
(166, '2021-03-01 17:18:07', 33.2427807, -8.499076, 99),
(167, '2021-03-02 19:13:09', 33.2427317, -8.4990603, 101),
(168, '2021-03-02 19:27:41', 33.2427499, -8.4990925, 102),
(169, '2021-03-02 19:28:19', 33.242763, -8.4990392, 102),
(170, '2021-03-06 12:24:58', 33.2427277, -8.4990659, 102),
(171, '2021-03-06 12:25:03', 33.2427261, -8.4990627, 102),
(172, '2021-03-06 12:28:32', 33.2426905, -8.4990562, 102),
(173, '2021-03-06 12:32:50', 33.2427662, -8.4990843, 102),
(174, '2021-03-06 12:33:18', 33.2427197, -8.4990702, 102),
(175, '2021-03-06 12:33:57', 33.2427493, -8.4990656, 102),
(176, '2021-03-06 12:34:25', 33.2427567, -8.4990549, 102),
(177, '2021-03-06 12:34:43', 33.24275, -8.4990548, 102),
(178, '2021-03-06 12:35:58', 33.2427663, -8.4990532, 102),
(179, '2021-03-06 12:36:29', 33.2427586, -8.4990541, 102),
(180, '2021-03-06 12:37:57', 33.2427325, -8.4990753, 102),
(181, '2021-03-06 12:38:19', 33.2427317, -8.499056, 102),
(182, '2021-03-06 12:39:45', 33.2427337, -8.4990446, 102),
(183, '2021-03-06 12:40:16', 33.2427546, -8.4990249, 102),
(184, '2021-03-06 12:40:48', 33.2427361, -8.4990409, 102);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `date_naissance` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `imei` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `sexe` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `date_naissance`, `email`, `imei`, `nom`, `prenom`, `sexe`, `telephone`) VALUES
(91, '1991-10-14', 'hassarmhammed@gmail.com', 'a5100337026e9780', 'hassar', 'mhamed', 'M', '+212637229617'),
(92, '1999-07-30', 'hhassar99@gmail.com', '6b8abe955fac921c', 'HASSAR', 'Hassane', 'M', '+212680975683'),
(99, '1991-10-23', 'hassarmhammed@gmail.com', 'a5100337026e9784', 'Ali', 'mohamed', 'M', '+212637229618'),
(101, '1999-07-30', 'hassarmhammed@gmail.com', 'a5100337026e9789', 'Hassar', 'Hassan', 'M', '+212637229610'),
(102, '1999-07-30', 'hassarmhammed@gmail.com', 'a5100337026e9787', 'Hassar', 'Hassan', 'M', '+212637229619');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `ami`
--
ALTER TABLE `ami`
  ADD PRIMARY KEY (`user1_id`,`user2_id`),
  ADD KEY `FKig44ahxrgvdj5yxk9e1rr78g` (`user2_id`);

--
-- Index pour la table `configuration`
--
ALTER TABLE `configuration`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5u3jsr211iwxnybkb9fn9m1lr` (`user_id`);

--
-- Index pour la table `position`
--
ALTER TABLE `position`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjmw29eckwum6dcll6sknilc6u` (`user_id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `configuration`
--
ALTER TABLE `configuration`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT pour la table `position`
--
ALTER TABLE `position`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=185;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `ami`
--
ALTER TABLE `ami`
  ADD CONSTRAINT `FKig44ahxrgvdj5yxk9e1rr78g` FOREIGN KEY (`user2_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKkd2g3qtpa8f0emn2vn0a6katq` FOREIGN KEY (`user1_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `configuration`
--
ALTER TABLE `configuration`
  ADD CONSTRAINT `FK5u3jsr211iwxnybkb9fn9m1lr` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `position`
--
ALTER TABLE `position`
  ADD CONSTRAINT `FKjmw29eckwum6dcll6sknilc6u` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
