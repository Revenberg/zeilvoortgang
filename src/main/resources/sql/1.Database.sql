DROP DATABASE if exists zeilvoortgang;

CREATE DATABASE if not exists zeilvoortgang;

-- Drop existing tables if they exist
DROP TABLE IF EXISTS zeilvoortgang.leerlijnLesDetails;
DROP TABLE IF EXISTS zeilvoortgang.leerlijnLessen;
DROP TABLE IF EXISTS zeilvoortgang.leerlijnen;
DROP TABLE IF EXISTS zeilvoortgang.lesCursisten;
DROP TABLE IF EXISTS zeilvoortgang.lesmethodieken;
DROP TABLE IF EXISTS zeilvoortgang.lesStatussen;
DROP TABLE IF EXISTS zeilvoortgang.lestrainers;
DROP TABLE IF EXISTS zeilvoortgang.voortgangStatussen;
DROP TABLE IF EXISTS zeilvoortgang.lessen;
DROP TABLE IF EXISTS zeilvoortgang.userLevels;
DROP TABLE IF EXISTS zeilvoortgang.methodieken;
DROP TABLE IF EXISTS zeilvoortgang.progressStatussen;
DROP TABLE IF EXISTS zeilvoortgang.lesSequence;
DROP TABLE IF EXISTS zeilvoortgang.levels;

DROP TABLE IF EXISTS zeilvoortgang.user_teams;
DROP TABLE IF EXISTS zeilvoortgang.team_permissions;
DROP TABLE IF EXISTS zeilvoortgang.group_permissions;

DROP TABLE IF EXISTS zeilvoortgang.users;
DROP TABLE IF EXISTS zeilvoortgang.teams;
DROP TABLE IF EXISTS zeilvoortgang.permissions;
DROP TABLE IF EXISTS zeilvoortgang.groupNames;
DROP TABLE IF EXISTS zeilvoortgang.organizations;

#connect zeilvoortgang;
