USE 'test';
DROP TABLE IF EXISTS 'parts';
CREATE TABLE 'parts'(
'id' INT(11) NOT NULL AUTO_INCREMENT,
'count' INT(11) NOT NULL,
'name' VARCHAR(255) NOT NULL,
'needed' BIT NOT NULL,
PRIMARY KEY ('id'))
DEFAULT CHARSET utf8;
INSERT INTO 'parts' VALUES(1,10,'Видеокарта',1);
INSERT INTO 'parts' VALUES(2,5,'Видеокарта',1);
INSERT INTO 'parts' VALUES(3,10,'Видеокарта',1);
INSERT INTO 'parts' VALUES(4,10,'Видеокарта',1);
INSERT INTO 'parts' VALUES(5,10,'Видеокарта',1);