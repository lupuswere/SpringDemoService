FLUSH PRIVILEGES;
CREATE DATABASE demo;
DROP TABLE IF EXISTS demo.records;
CREATE TABLE demo.records
(
    recordId text NOT NULL,
    someProperty text NOT NULL,
    PRIMARY KEY (recordId(255))
);