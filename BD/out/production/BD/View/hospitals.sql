CREATE TABLE hospitals
(
  idhospital   int PRIMARY KEY UNIQUE NOT NULL CHECK ( idhospital >= 0 ),
  namehospital VARCHAR(100)           NOT NULL
);



INSERT INTO hospitals(id, name)
VALUES (2, 'Novosibirsk hospital');

INSERT into hospitals (idhospital, namehospital)
VALUES (1, 'Chambord Castle (Loire Valley, France)'),
       (2, 'Dunrobin Castle (Scotland)'),
       (3, 'Alcazar of Segovia (Spain)');