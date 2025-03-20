-- Create the lesSequence table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`lesSequence` (
    `lesSequenceId` CHAR(36) PRIMARY KEY,
    `title` VARCHAR(50) NOT NULL UNIQUE,
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL
);

-- Create the levels table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`levels` (
    `levelId` CHAR(36) PRIMARY KEY,
    `description` VARCHAR(50) NOT NULL UNIQUE,
    `image` VARCHAR(50)
);

-- Create the progressStatussen table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`progressStatussen` (
    `progressStatusId` CHAR(36) PRIMARY KEY,
    `description` VARCHAR(10) NOT NULL UNIQUE,
    `longDescription` VARCHAR(250) NOT NULL
);

-- Create the methodieken table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`methodieken` (
    `methodiekId` CHAR(36) PRIMARY KEY,
    `levelId` CHAR(36) NOT NULL,
    `soort` VARCHAR(100) NOT NULL,
    `methodiek` VARCHAR(100) NOT NULL,
    `description` VARCHAR(10000) NOT NULL,
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL,
    UNIQUE (`levelId`, `soort`, `methodiek`),
    FOREIGN KEY (`levelId`) REFERENCES `zeilvoortgang`.`levels`(`levelId`) ON DELETE CASCADE
);

-- Create the lessen table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`lessen` (
    `lesId` CHAR(36) PRIMARY KEY,
    `lesSequenceId` CHAR(36) NULL DEFAULT 0,
    `lesDateTime` DATETIME NOT NULL,
    `title` VARCHAR(50),
    `description` VARCHAR(200),
    `status` VARCHAR(50),
    `afgesloten` TIMESTAMP NULL DEFAULT NULL,
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`lesSequenceId`) REFERENCES `zeilvoortgang`.`lesSequence`(`lesSequenceId`) ON DELETE CASCADE
);

-- Create the lesCursisten table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`lesCursisten` (
    `lesCursistenId` CHAR(36) PRIMARY KEY,
    `lesId` CHAR(36) NOT NULL,
    `cursistId` CHAR(36) NOT NULL,
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL,
    UNIQUE (`lesId`, `cursistId`),
    FOREIGN KEY (`lesId`) REFERENCES `zeilvoortgang`.`lessen`(`lesId`) ON DELETE CASCADE,
    FOREIGN KEY (`cursistId`) REFERENCES `zeilvoortgang`.`users`(`userId`) ON DELETE CASCADE
);

-- Create the lesmethodieken table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`lesmethodieken` (
    `lesMethodiekId` CHAR(36) PRIMARY KEY,
    `lesId` CHAR(36) NOT NULL,
    `methodiekId` CHAR(36) NOT NULL,
    `doelId` CHAR(36) NULL DEFAULT NULL,
    `description` VARCHAR(200),
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL,
    UNIQUE (`lesId`, `methodiekId`),
    FOREIGN KEY (`lesId`) REFERENCES `zeilvoortgang`.`lessen`(`lesId`) ON DELETE CASCADE,
    FOREIGN KEY (`methodiekId`) REFERENCES `zeilvoortgang`.`methodieken`(`methodiekId`) ON DELETE CASCADE,
    FOREIGN KEY (`doelId`) REFERENCES `zeilvoortgang`.`levels`(`levelId`) ON DELETE CASCADE
);

-- Create the lesStatussen table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`lesStatussen` (
    `lesStatusId` CHAR(36) PRIMARY KEY,
    `lesId` CHAR(36) NOT NULL,
    `methodiekId` CHAR(36) NOT NULL,
    `userId` CHAR(36) NOT NULL,
    `progressId` CHAR(36) NOT NULL,
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL,
    UNIQUE (`lesId`, `methodiekId`, `userId`),
    FOREIGN KEY (`lesId`) REFERENCES `zeilvoortgang`.`lessen`(`lesId`) ON DELETE CASCADE,
    FOREIGN KEY (`methodiekId`) REFERENCES `zeilvoortgang`.`methodieken`(`methodiekId`) ON DELETE CASCADE,
    FOREIGN KEY (`userId`) REFERENCES `zeilvoortgang`.`users`(`userId`) ON DELETE CASCADE,
    FOREIGN KEY (`progressId`) REFERENCES `zeilvoortgang`.`progressStatussen`(`progressStatusId`) ON DELETE CASCADE
);

-- Create the lestrainers table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`lestrainers` (
    `lesTrainersId` CHAR(36) PRIMARY KEY,
    `lesId` CHAR(36) NOT NULL,
    `trainerId` CHAR(36) NOT NULL,
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL,
    UNIQUE (`lesId`, `trainerId`),
    FOREIGN KEY (`lesId`) REFERENCES `zeilvoortgang`.`lessen`(`lesId`) ON DELETE CASCADE,
    FOREIGN KEY (`trainerId`) REFERENCES `zeilvoortgang`.`users`(`userId`) ON DELETE CASCADE
);

-- Create the userLevels table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`userLevels` (
    `userLevelsId` CHAR(36) PRIMARY KEY,
    `userId` CHAR(36) NOT NULL,
    `levelId` CHAR(36) NOT NULL,
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL,
    UNIQUE (`userId`, `levelId`),
    FOREIGN KEY (`userId`) REFERENCES `zeilvoortgang`.`users`(`userId`) ON DELETE CASCADE,
    FOREIGN KEY (`levelId`) REFERENCES `zeilvoortgang`.`levels`(`levelId`) ON DELETE CASCADE
);

-- Create the voortgangStatussen table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`voortgangStatussen` (
    `voortgangStatussenId` CHAR(36) PRIMARY KEY,
    `lesId` CHAR(36) NOT NULL,
    `methodiekId` CHAR(36) NOT NULL,
    `userId` CHAR(36) NOT NULL,
    `progressId` CHAR(36) NOT NULL,
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL,
    UNIQUE (`userId`, `methodiekId`),
    FOREIGN KEY (`lesId`) REFERENCES `zeilvoortgang`.`lessen`(`lesId`) ON DELETE CASCADE,
    FOREIGN KEY (`methodiekId`) REFERENCES `zeilvoortgang`.`methodieken`(`methodiekId`) ON DELETE CASCADE,
    FOREIGN KEY (`userId`) REFERENCES `zeilvoortgang`.`users`(`userId`) ON DELETE CASCADE,
    FOREIGN KEY (`progressId`) REFERENCES `zeilvoortgang`.`progressStatussen`(`progressStatusId`) ON DELETE CASCADE
);

