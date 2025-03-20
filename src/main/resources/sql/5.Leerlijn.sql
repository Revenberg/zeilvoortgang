-- Create the leerlijn table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`leerlijnen` (
    `leerlijnId` CHAR(36) NOT NULL,
    `levelId` CHAR(36) NOT NULL,
    `title` VARCHAR(50) NOT NULL,
    `description` VARCHAR(200),
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`leerlijnId`),   
    FOREIGN KEY (`levelId`) REFERENCES `zeilvoortgang`.`levels`(`levelId`) ON DELETE CASCADE,
    UNIQUE (`levelId`, `title`) 
);

-- Create the leerlijnLes table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`leerlijnLessen` (
    `leerlijnLesId` CHAR(36) NOT NULL,    
    `leerlijnId` CHAR(36) NOT NULL,
    `levelId` CHAR(36) NOT NULL,
    `lesOrder` int NOT NULL,
    `title` VARCHAR(50),
    `description` VARCHAR(200),
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL,    
    PRIMARY KEY (`leerlijnLesId`),
    FOREIGN KEY (`leerlijnId`) REFERENCES `zeilvoortgang`.`leerlijnen`(`leerlijnId`) ON DELETE CASCADE,
    FOREIGN KEY (`levelId`) REFERENCES `zeilvoortgang`.`levels`(`levelId`) ON DELETE CASCADE,
    UNIQUE (`leerlijnId`, `lesOrder`) 
);

-- Create the leerlijn table
CREATE TABLE IF NOT EXISTS `zeilvoortgang`.`leerlijnLesDetails` (
    `leerlijnLesDetailsId` CHAR(36) NOT NULL,
    `leerlijnLesId` CHAR(36) NOT NULL,
    `methodiekId` CHAR(36) NOT NULL,
    `progressStatusId` CHAR(36) NOT NULL,
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`leerlijnLesDetailsId`),
    FOREIGN KEY (`leerlijnLesId`) REFERENCES `zeilvoortgang`.`leerlijnLessen`(`leerlijnLesId`) ON DELETE CASCADE,
    FOREIGN KEY (`methodiekId`) REFERENCES `zeilvoortgang`.`methodieken`(`methodiekId`) ON DELETE CASCADE,
    FOREIGN KEY (`progressStatusId`) REFERENCES `zeilvoortgang`.`progressStatussen`(`progressStatusId`) ON DELETE CASCADE,
    UNIQUE (`leerlijnLesId`, `methodiekId`) 
);

