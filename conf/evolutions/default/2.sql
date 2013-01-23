# OCRE schema
 
# --- !Ups

CREATE SEQUENCE fixture_id_seq;
CREATE TABLE Fixture (
    fixtureId bigint(20) NOT NULL DEFAULT nextval('fixture_id_seq'),
    home varchar(60), 
    away varchar(60),
    datetime timestamp,
    primary key (fixtureId)
);
# --- !Downs
 
DROP TABLE Fixture;
DROP SEQUENCE fixture_id_seq;