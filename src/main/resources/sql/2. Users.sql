-- Create the organizations table
CREATE TABLE IF NOT EXISTS zeilvoortgang.organizations (
    organizationId CHAR(36) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    lastUpdateTMS DATETIME NOT NULL,
    lastUpdateIdentifier VARCHAR(255) NOT NULL,
    PRIMARY KEY (organizationId)
);

-- Create the Users table
CREATE TABLE IF NOT EXISTS zeilvoortgang.users (
    userId CHAR(36) PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    lastUpdateTMS DATETIME NOT NULL,
    lastUpdateIdentifier VARCHAR(255) NOT NULL
);

-- Create the Permission table
CREATE TABLE IF NOT EXISTS zeilvoortgang.permissions (
    permissionId CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255),
    lastUpdateTMS DATETIME NOT NULL,
    lastUpdateIdentifier VARCHAR(255) NOT NULL
);

-- Create the Team table
CREATE TABLE IF NOT EXISTS zeilvoortgang.teams (
    teamId CHAR(36) PRIMARY KEY,
    organizationId CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    lastUpdateTMS DATETIME NOT NULL,
    lastUpdateIdentifier VARCHAR(255) NOT NULL,
    FOREIGN KEY (organizationId) REFERENCES zeilvoortgang.organizations(organizationId) ON DELETE CASCADE,
    UNIQUE (organizationId, name)
);

-- Create the User_Team table to establish many-to-many relationship between users and teams
CREATE TABLE IF NOT EXISTS zeilvoortgang.user_teams (
    userTeamId CHAR(36) PRIMARY KEY,
    userId CHAR(36) NOT NULL,
    teamId CHAR(36) NOT NULL,
    lastUpdateTMS DATETIME NOT NULL,
    lastUpdateIdentifier VARCHAR(255) NOT NULL,
    FOREIGN KEY (userId) REFERENCES zeilvoortgang.users(userId) ON DELETE CASCADE,
    FOREIGN KEY (teamId) REFERENCES zeilvoortgang.teams(teamId) ON DELETE CASCADE,
    UNIQUE (userId, teamId)
);

CREATE TABLE IF NOT EXISTS zeilvoortgang.groupNames (
    groupId CHAR(36) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    lastUpdateTMS DATETIME NOT NULL,
    lastUpdateIdentifier VARCHAR(255) NOT NULL,
    PRIMARY KEY (groupId)
);

-- Create the group_permissions table to establish many-to-many relationship between groupNames and permissions
CREATE TABLE IF NOT EXISTS zeilvoortgang.group_permissions (
    groupPermissionId CHAR(36) NOT NULL UNIQUE,
    groupId CHAR(36) NOT NULL,
    permissionId CHAR(36) NOT NULL,
    lastUpdateTMS DATETIME NOT NULL,
    lastUpdateIdentifier VARCHAR(255) NOT NULL,
    PRIMARY KEY (groupPermissionId),
    FOREIGN KEY (permissionId) REFERENCES zeilvoortgang.permissions(permissionId) ON DELETE CASCADE,
    FOREIGN KEY (groupId) REFERENCES zeilvoortgang.groupNames(groupId) ON DELETE CASCADE,
    UNIQUE (groupId, permissionId)
);

-- Create the Team_Permission table to establish many-to-many relationship between teams and permissions
CREATE TABLE IF NOT EXISTS zeilvoortgang.team_permissions (
    teamId CHAR(36) NOT NULL,
    groupPermissionId CHAR(36) NOT NULL,
    lastUpdateTMS DATETIME NOT NULL,
    lastUpdateIdentifier VARCHAR(255) NOT NULL,
    PRIMARY KEY (teamId, groupPermissionId),
    FOREIGN KEY (teamId) REFERENCES zeilvoortgang.teams(teamId) ON DELETE CASCADE,
    FOREIGN KEY (groupPermissionId) REFERENCES zeilvoortgang.group_permissions(groupPermissionId) ON DELETE CASCADE,
    UNIQUE (teamId, groupPermissionId)
);

