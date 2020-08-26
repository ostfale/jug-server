DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS location;

CREATE TABLE IF NOT EXISTS location
(
    location_id   SERIAL PRIMARY KEY,
    name          VARCHAR(255),
    country       VARCHAR(255),
    city          VARCHAR(255),
    postal_code   VARCHAR(255),
    street_name   VARCHAR(255),
    contact_id       INTEGER,
    street_number VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS person
(
    person_id  SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    phone      VARCHAR(255),
    bio        TEXT
);

CREATE TABLE IF NOT EXISTS room
(
    location INTEGER,
    name     VARCHAR(255),
    capacity INTEGER,
    remark   TEXT
)
