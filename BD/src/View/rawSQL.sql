CREATE TABLE hospitals
(
  id   int PRIMARY KEY NOT NULL UNIQUE CHECK ( id >= 0 ),
  name VARCHAR(100)    NOT NULL
);

INSERT INTO hospitals(id, name)
VALUES (2, 'Novosibirsk hospital');


CREATE TABLE doctorsandhosp
(
  iddoctor   int NOT NULL CHECK ( iddoctor >= 0 ),
  idhospital int NOT NULL CHECK ( idhospital >= 0 )
);

INSERT INTO doctorsandhosp (iddoctor, idhospital)
VALUES (1, 1),
       (1, 2),
       (2, 1);


CREATE TABLE patients
(
  idpatient   int PRIMARY KEY UNIQUE NOT NULL CHECK ( idpatient >= 0 ),
  namepatient VARCHAR(100)           NOT NULL,
  age         int                    NOT NULL CHECK ( age >= 0 )
);

CREATE TABLE doctors
(
  iddoctor   int PRIMARY KEY UNIQUE NOT NULL CHECK ( iddoctor >= 0 ),
  namedoctor VARCHAR(100)           NOT NULL,
  degree     VARCHAR(50)            NOT NULL
);
CREATE TABLE hospitals
(
  idhospital   int PRIMARY KEY UNIQUE NOT NULL CHECK ( idhospital >= 0 ),
  namehospital VARCHAR(100)           NOT NULL
);

CREATE TABLE diagnosis
(
  iddiagnosis   int PRIMARY KEY UNIQUE NOT NULL CHECK ( iddiagnosis >= 0 ),
  namediagnosis VARCHAR(100)           NOT NULL
);

CREATE TABLE docthosp
(
  iddoctor   int NOT NULL CHECK ( iddoctor >= 0 ),
  idhospital int NOT NULL CHECK ( idhospital >= 0 ),
  PRIMARY KEY (iddoctor, idhospital),
  FOREIGN KEY (iddoctor) REFERENCES doctors (iddoctor),
  FOREIGN KEY (idhospital) REFERENCES hospitals (idhospital)
);

CREATE TABLE pathosp
(
  idpatient  int NOT NULL CHECK ( idpatient >= 0 ),
  idhospital int NOT NULL CHECK ( idhospital >= 0 ),
  PRIMARY KEY (idpatient, idhospital),
  FOREIGN KEY (idpatient) REFERENCES patients (idpatient),
  FOREIGN KEY (idhospital) REFERENCES hospitals (idhospital)
);

CREATE TABLE notes
(
  idnote      int PRIMARY KEY UNIQUE NOT NULL CHECK ( idnote >= 0 ),
  idpatient   int                    NOT NULL CHECK ( idpatient >= 0 ),
  iddoctor    int                    NOT NULL CHECK ( iddoctor >= 0 ),
  idhospital  int                    NOT NULL CHECK ( idhospital >= 0 ),
  iddiagnosis int                    NOT NULL CHECK ( iddiagnosis >= 0 ),
  text        VARCHAR(500)           NOT NULL,
  FOREIGN KEY (idpatient) REFERENCES patients (idpatient),
  FOREIGN KEY (idhospital) REFERENCES hospitals (idhospital),
  FOREIGN KEY (iddoctor) REFERENCES doctors (iddoctor),
  FOREIGN KEY (iddiagnosis) REFERENCES diagnosis (iddiagnosis)
);

INSERT INTO patients (idpatient, namepatient, age)
VALUES (2, 'Doberman Pinscher', 1),
       (3, 'Finnish Spitz', 2),
       (4, 'Irish Wolfhound', 3),
       (5, 'Tibetan Mastiff', 4),
       (6, 'Samoyed', 5),
       (7, 'German Schepherd', 6),
       (1, 'Whippet', 0);


INSERT INTO doctors (iddoctor, namedoctor, degree)
VALUES (1, 'Irish Terrier', 'PhD'),
       (2, 'Komondor', 'Master'),
       (3, 'Labrador Retriever', 'PhD'),
       (4, 'Mastiff', 'Bachelor'),
       (5, 'Siberian Husky', 'Master'),
       (6, 'Pelmbroke Welsh Corgi', 'PhD');

insert into doctors (iddoctor, namedoctor, degree)
VALUES (7, 'Welsh Springer Spaniel', 'PhD');
insert into docthosp (iddoctor, idhospital)
VALUES (7, 1);

INSERT into hospitals (idhospital, namehospital)
VALUES (1, 'Chambord Castle (Loire Valley, France)'),
       (2, 'Dunrobin Castle (Scotland)'),
       (3, 'Alcazar of Segovia (Spain)');

INSERT into diagnosis (iddiagnosis, namediagnosis)
VALUES (1, 'Good Coat'),
       (2, 'Nice Eyes'),
       (3, 'Big Ears'),
       (4, 'Strong Paws');

INSERT into docthosp (iddoctor, idhospital)
values (1, 1),
       (1, 2),
       (2, 2),
       (2, 3),
       (3, 3),
       (3, 1),
       (4, 1),
       (4, 2),
       (5, 2),
       (5, 3),
       (6, 3),
       (6, 1);

insert into docthosp (iddoctor, idhospital)
values (7, 1);

INSERT into pathosp (idpatient, idhospital)
values (1, 1),
       (2, 2),
       (3, 3),
       (3, 1),
       (4, 1),
       (4, 2),
       (4, 3),
       (5, 2),
       (6, 2),
       (7, 1),
       (7, 2);

INSERT into notes (idnote, idpatient, iddoctor, idhospital, iddiagnosis, text)
VALUES (1, 1, 1, 1, 1, 'BOY, LIVES IN SWEDEN'),
       (2, 1, 4, 1, 2, 'GOOD'),
       (3, 2, 2, 2, 3, 'GIRL, LIVES IN RUSSIA'),
       (4, 3, 5, 3, 4, 'BOY, LIVES IN SWEDEN'),
       (5, 4, 3, 3, 1, 'BOY, LIVES IN ITALY'),
       (6, 5, 5, 2, 1, 'GIRL, LIVES IN UK'),
       (7, 6, 6, 1, 2, 'BOY, LIVES IN FINLAND'),
       (8, 7, 7, 1, 2, 'GIRL, LIVES IN FRANCE');

SELECT idnote,
       namepatient,
       age,
       namedoctor,
       degree,
       namehospital,
       namediagnosis,
       text

FROM (SELECT *
      FROM (SELECT *
            FROM (SELECT *
                  FROM notes
                         LEFT JOIN hospitals ON notes.idhospital = hospitals.idhospital)
                   AS notesh
                   LEFT JOIN patients ON notesh.idpatient = patients.idpatient)
             AS noteshp
             LEFT JOIN doctors ON noteshp.iddoctor = doctors.iddoctor)
       AS noteshpd
       LEFT JOIN diagnosis on noteshpd.iddiagnosis = diagnosis.iddiagnosis
ORDER BY idnote;

SELECT idnote,
       namepatient,
       age,
       namedoctor,
       degree,
       namehospital,
       namediagnosis,
       text
FROM
  (notes LEFT JOIN hospitals ON notes.idhospital = hospitals.idhospital
         LEFT JOIN patients ON notes.idpatient = patients.idpatient
         LEFT JOIN doctors ON notes.iddoctor = doctors.iddoctor
         LEFT JOIN diagnosis on notes.iddiagnosis = diagnosis.iddiagnosis)
ORDER BY notes.idnote;



SELECT *
from notes
       INNER JOIN patients ON notes.idpatient = patients.idpatient
WHERE notes.idpatient = 1;


SELECT diagnosis.iddiagnosis, diagnosis.namediagnosis, count(diagnosis.iddiagnosis) as count
FROM (notes
       left join diagnosis on notes.iddiagnosis = diagnosis.iddiagnosis)
GROUP BY diagnosis.iddiagnosis
ORDER BY diagnosis.iddiagnosis;


SELECT namehospital, namediagnosis
FROM (SELECT *
      FROM notes
             LEFT JOIN hospitals ON notes.idhospital = hospitals.idhospital) as nh
       LEFT JOIN diagnosis ON nh.iddiagnosis = diagnosis.iddiagnosis;


SELECT *
from notes;
SELECT *
from patients;
SELECT *
from pathosp;
SELECT *
from hospitals;
SELECT *
from pathosp;
SELECT count(*)
FROM notes;

SELECT count(*)
FROM patients;

SELECT count(*)
FROM hospitals;

BEGIN;
INSERT INTO patients (idpatient, namepatient, age)
VALUES ((SELECT count(*) FROM patients) + 1, 'Airedale Terrier', 5);
INSERT INTO pathosp (idpatient, idhospital)
VALUES ((SELECT count(*) FROM patients), 1);
INSERT INTO notes (idnote, idpatient, iddoctor, idhospital, iddiagnosis, text)
VALUES ((SELECT count(*) FROM notes) + 1, (SELECT count(*) FROM patients), 1, 1, 1, 'THE BEST');
COMMIT;


CREATE SEQUENCE
  doc_id start with 9 increment by 1;

BEGIN;
INSERT INTO doctors (iddoctor, namedoctor, degree)
VALUES (doc_id.increment_by, 'Alaskan Malamute', 'PhD');
INSERT INTO docthosp (iddoctor, idhospital)
VALUES ((SELECT count(*) FROM doctors), 1);
COMMIT;

BEGIN;
DELETE
FROM patients
WHERE patients.idpatient = 8;
DELETE
FROM notes
WHERE notes.idpatient = 8;
DELETE
FROM pathosp
WHERE idpatient = 8;
END;

UPDATE notes
SET text        = 'new text',
    iddiagnosis = 1
WHERE notes.idnote = 1;
UPDATE patients
SET namepatient = 'new name',
    age         = 3
WHERE patients.idpatient = 1;
UPDATE doctors
SET namedoctor = 'new name',
    degree     = 'new degree'
WHERE doctors.iddoctor = 1;
UPDATE diagnosis
SET namediagnosis = 'new diagnosis'
WHERE diagnosis.iddiagnosis = 1;


CREATE TABLE insurances
(
  idinsurance   int PRIMARY KEY UNIQUE NOT NULL CHECK ( idinsurance >= 0 ),
  nameinsurance VARCHAR(100)           NOT NULL
);
INSERT into insurances (idinsurance, nameinsurance) VALUES
(1, 'Insurance Company Number 1'), (2, 'Insurance Company Number 2');

CREATE TABLE patinsur
(
  idpatient  int NOT NULL CHECK ( idpatient >= 0 ),
  idinsurance int NOT NULL CHECK ( idinsurance >= 0 ),
  PRIMARY KEY (idpatient, idinsurance),
  FOREIGN KEY (idpatient) REFERENCES patients (idpatient),
  FOREIGN KEY (idinsurance) REFERENCES insurances (idinsurance)
);
INSERT into patinsur (idpatient, idinsurance) values
(1, 1), (2, 2), (3, 1), (4, 2), (5, 2), (6, 2);





--------------------------------------------------------------------------------
SELECT * from patients;
SELECT * from doctors;
SELECT * from diagnosis;
SELECT * from notes;
SELECT * from insurances;
SELECT * from hospitals;
SELECT * from docthosp;
SELECT * from pathosp;
SELECT * from patinsur;



--диагнозы по госп.
SELECT namehospital, namediagnosis
FROM (SELECT *
      FROM notes
             LEFT JOIN hospitals ON notes.idhospital = hospitals.idhospital) as nh
       LEFT JOIN diagnosis ON nh.iddiagnosis = diagnosis.iddiagnosis;


--подсчет диагнозов
SELECT diagnosis.iddiagnosis, diagnosis.namediagnosis, count(diagnosis.iddiagnosis) as count
FROM (notes
       left join diagnosis on notes.iddiagnosis = diagnosis.iddiagnosis)
GROUP BY diagnosis.iddiagnosis
ORDER BY diagnosis.iddiagnosis;

--записи по пациенту
SELECT *
from notes
       INNER JOIN patients ON notes.idpatient = patients.idpatient
WHERE notes.idpatient = 1;

--все записи с подстановкой имен и тд
SELECT idnote,
       namepatient,
       age,
       namedoctor,
       degree,
       namehospital,
       namediagnosis,
       text
FROM
  (notes LEFT JOIN hospitals ON notes.idhospital = hospitals.idhospital
         LEFT JOIN patients ON notes.idpatient = patients.idpatient
         LEFT JOIN doctors ON notes.iddoctor = doctors.iddoctor
         LEFT JOIN diagnosis on notes.iddiagnosis = diagnosis.iddiagnosis)
ORDER BY notes.idnote;

-- добавление пациента надо на сиквенсах
BEGIN;
INSERT INTO patients (idpatient, namepatient, age)
VALUES ((SELECT count(*) FROM patients) + 1, 'Airedale Terrier', 5);
INSERT INTO pathosp (idpatient, idhospital)
VALUES ((SELECT count(*) FROM patients), 1);
INSERT INTO notes (idnote, idpatient, iddoctor, idhospital, iddiagnosis, text)
VALUES ((SELECT count(*) FROM notes) + 1, (SELECT count(*) FROM patients), 1, 1, 1, 'THE BEST');
COMMIT;

--добавление врача надо на сиквенсах
BEGIN;
INSERT INTO doctors (iddoctor, namedoctor, degree)
VALUES (doc_id.increment_by, 'Alaskan Malamute', 'PhD');
INSERT INTO docthosp (iddoctor, idhospital)
VALUES ((SELECT count(*) FROM doctors), 1);
COMMIT;

--апдейты всего
UPDATE notes
SET text        = 'new text',
    iddiagnosis = 1
WHERE notes.idnote = 1;
UPDATE patients
SET namepatient = 'new name',
    age         = 3
WHERE patients.idpatient = 1;
UPDATE doctors
SET namedoctor = 'new name',
    degree     = 'new degree'
WHERE doctors.iddoctor = 1;
UPDATE diagnosis
SET namediagnosis = 'new diagnosis'
WHERE diagnosis.iddiagnosis = 1;


---удаление всего по айди пациента
BEGIN;
DELETE
FROM patients
WHERE patients.idpatient = 8;
DELETE
FROM notes
WHERE notes.idpatient = 8;
DELETE
FROM pathosp
WHERE idpatient = 8;
END;

--1 ур влож, секвинсы, внеш кл

DROP TABLE doctors;
DROP TABLE diagnosis;
DROP TABLE notes;
DROP TABLE hospitals;
DROP TABLE docthosp;
DROP TABLE pathosp;
DROP TABLE patinsur;
DROP TABLE patients;
DROP TABLE insurances;