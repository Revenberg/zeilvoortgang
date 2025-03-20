CREATE TABLE zeilvoortgang.authorizations (
    authorizationId CHAR(36) PRIMARY KEY,
    description VARCHAR(55) NOT NULL UNIQUE,
    page VARCHAR(55) NOT NULL,
    raci VARCHAR(55) NOT NULL,
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL            
);
CREATE TABLE zeilvoortgang.authorizationRoles (
    authorizationRoleId CHAR(36) PRIMARY KEY,
    roleName VARCHAR(55) NOT NULL UNIQUE,
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL
);

CREATE TABLE zeilvoortgang.authorizationGroups (
    authorizationGroupsId CHAR(36) PRIMARY KEY,
    authorizationId CHAR(36)  NOT NULL,
    authorizationRoleId CHAR(36) NOT NULL,
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL,        
    FOREIGN KEY (authorizationRoleId) REFERENCES zeilvoortgang.authorizationRoles(authorizationRoleId) ON DELETE CASCADE,
    UNIQUE (`authorizationId`, `authorizationRoleId`)
);

CREATE TABLE zeilvoortgang.userRoles (
    userRolesId CHAR(36) PRIMARY KEY,
    userId CHAR(36) NOT NULL,
    authorizationRoleId CHAR(36) NOT NULL,
    `lastUpdateTMS` DATETIME NOT NULL,
    `lastUpdateIdentifier` VARCHAR(255) NOT NULL,    
    FOREIGN KEY (userId) REFERENCES zeilvoortgang.users(userId) ON DELETE CASCADE,
    FOREIGN KEY (authorizationRoleId) REFERENCES zeilvoortgang.authorizationRoles(authorizationRoleId) ON DELETE CASCADE,
    UNIQUE (`userId`, `authorizationRoleId`)
);

