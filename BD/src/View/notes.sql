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

INSERT into notes (idnote, idpatient, iddoctor, idhospital, iddiagnosis, text)
VALUES (1, 1, 1, 1, 1, 'BOY, LIVES IN SWEDEN'),
       (2, 1, 4, 1, 2, 'GOOD'),
       (3, 2, 2, 2, 3, 'GIRL, LIVES IN RUSSIA'),
       (4, 3, 5, 3, 4, 'BOY, LIVES IN SWEDEN'),
       (5, 4, 3, 3, 1, 'BOY, LIVES IN ITALY'),
       (6, 5, 5, 2, 1, 'GIRL, LIVES IN UK'),
       (7, 6, 6, 1, 2, 'BOY, LIVES IN FINLAND'),
       (8, 7, 7, 1, 2, 'GIRL, LIVES IN FRANCE');