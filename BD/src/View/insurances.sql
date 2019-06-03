CREATE TABLE insurances
(
  idinsurance   int PRIMARY KEY UNIQUE NOT NULL CHECK ( idinsurance >= 0 ),
  nameinsurance VARCHAR(100)           NOT NULL
);


INSERT into insurances (idinsurance, nameinsurance) VALUES
(1, 'Insurance Company Number 1'),
(2, 'Insurance Company Number 2');