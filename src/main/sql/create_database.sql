FLUSH PRIVILEGES;
CREATE DATABASE demo;
DROP TABLE IF EXISTS demo.records;
CREATE TABLE demo.records
(
    id smallint unsigned NOT NULL auto_increment,
    someProperty text NOT NULL,
    PRIMARY KEY (id)
);