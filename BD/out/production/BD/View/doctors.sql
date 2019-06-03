CREATE TABLE doctors
(
  iddoctor   int PRIMARY KEY UNIQUE NOT NULL CHECK ( iddoctor >= 0 ),
  namedoctor VARCHAR(100)           NOT NULL,
  degree     VARCHAR(50)            NOT NULL
);

INSERT INTO doctors (iddoctor, namedoctor, degree)
VALUES (1, 'Irish Terrier', 'PhD'),
       (2, 'Komondor', 'Master'),
       (3, 'Labrador Retriever', 'PhD'),
       (4, 'Mastiff', 'Bachelor'),
       (5, 'Siberian Husky', 'Master'),
       (6, 'Pelmbroke Welsh Corgi', 'PhD');

INSERT INTO doctors (iddoctor, namedoctor, degree)
VALUES (7, 'Welsh Springer Spaniel', 'PhD');