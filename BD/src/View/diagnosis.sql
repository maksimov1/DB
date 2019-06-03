CREATE TABLE diagnosis
(
  iddiagnosis   int PRIMARY KEY UNIQUE NOT NULL CHECK ( iddiagnosis >= 0 ),
  namediagnosis VARCHAR(100)           NOT NULL
);

INSERT into diagnosis (iddiagnosis, namediagnosis)
VALUES (1, 'Good Coat'),
       (2, 'Nice Eyes'),
       (3, 'Big Ears'),
       (4, 'Strong Paws');