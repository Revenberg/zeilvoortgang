#delete from userRoles where 1;
#delete from authorizationRoles where 1;
#delete from authorizations where 1;

insert INTO zeilvoortgang.authorizations (authorizationId, description, page, raci, lastUpdateTMS, lastUpdateIdentifier) VALUES
('1', 'admin', 'admin', 'R', NOW(), 'import'),
('2', 'user', 'user', 'R', NOW(), 'import'),
('3', 'trainer', 'trainer', 'R', NOW(), 'import'),
('4', 'cursist', 'cursist', 'R', NOW(), 'import');

insert INTO zeilvoortgang.authorizationRoles (authorizationRoleId, roleName, lastUpdateTMS, lastUpdateIdentifier) VALUES
('1', 'adminRole', NOW(), 'import'),
('2', 'userRole', NOW(), 'import'),
('3', 'trainerRole', NOW(), 'import'),
('4', 'cursistRole', NOW(), 'import');


INSERT INTO zeilvoortgang.userRoles (userRolesId, userId, authorizationRoleId, lastUpdateTMS, lastUpdateIdentifier) values
('1', '1', '1', NOW(), 'import'),
('2', '2', '2', NOW(), 'import'),
('3', '3', '3', NOW(), 'import'),
('4', '4', '4', NOW(), 'import');

Select ar.roleName, u.username, u.password
from 
zeilvoortgang.authorizationRoles as ar,
zeilvoortgang.userRoles as ur,
zeilvoortgang.users as u
WHERE
ar.authorizationRoleId = ur.authorizationRoleId
and
ur.userId = u.userId
AND
u.username = 'user'
 ;
