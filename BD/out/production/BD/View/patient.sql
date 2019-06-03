CREATE TABLE patients
(
  idpatient   int PRIMARY KEY UNIQUE NOT NULL CHECK ( idpatient >= 0 ),
  namepatient VARCHAR(100)           NOT NULL,
  age         int                    NOT NULL CHECK ( age >= 0 )
);

INSERT INTO patients (idpatient, namepatient, age)
VALUES (2, 'Doberman Pinscher', 1),
       (3, 'Finnish Spitz', 2),
       (4, 'Irish Wolfhound', 3),
       (5, 'Tibetan Mastiff', 4),
       (6, 'Samoyed', 5),
       (7, 'German Schepherd', 6),
       (1, 'Whippet', 0);