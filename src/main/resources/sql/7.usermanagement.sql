#delete from user_teams where 1;
#delete from teams where 1;
#delete from organizations where 1;
#delete from users where 1;


INSERT INTO `zeilvoortgang`.`users` (`userId`, `username`, `password`, `email`, `lastUpdateTMS`, `lastUpdateIdentifier`) VALUES
(1, 'admin', '$2a$10$gcysOFrMetFetyyT2Oxrb.sbV5IqPH5ZfzsbNRXwsY53aqb1QifKm', 'admin@test.com', NOW(), 'import'),
(2, 'user', '$2a$10$YCAs.0rRmhTyKF4BLc0p0uOGBu61c33yhbEqm3vkga8OpL8ZWZtTu', 'user@test.com', NOW(), 'import'),
(3, 'trainer', '$2a$10$YCAs.0rRmhTyKF4BLc0p0uOGBu61c33yhbEqm3vkga8OpL8ZWZtTu', 'trainer@test.com', NOW(), 'import'),
(4, 'cursist', '$2a$10$YCAs.0rRmhTyKF4BLc0p0uOGBu61c33yhbEqm3vkga8OpL8ZWZtTu', 'cursist@test.com', NOW(), 'import');

INSERT INTO `zeilvoortgang`.`organizations` (`organizationId`, `name`, `lastUpdateTMS`, `lastUpdateIdentifier`) VALUES
(1, 'zeilvoortgang', NOW(), 'import');

INSERT INTO `zeilvoortgang`.`teams` (`teamId`, `organizationId`, `name`, `lastUpdateTMS`, `lastUpdateIdentifier`) VALUES
(1, 1, 'admin', NOW(), 'import'),
(2, 1, 'trainer', NOW(), 'import'),
(3, 1, 'cursist', NOW(), 'import');

INSERT INTO `zeilvoortgang`.`user_teams` (`userTeamId`, `userId`, `teamId`, `lastUpdateTMS`, `lastUpdateIdentifier`) VALUES
(1, 1, 1, NOW(), 'import'),
(3, 2, 3, NOW(), 'import'),
(4, 3, 3, NOW(), 'import');
