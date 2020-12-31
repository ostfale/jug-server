DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS note;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS tag_name;
DROP TABLE IF EXISTS event_tag;
DROP TABLE IF EXISTS speaker_event;

CREATE TABLE IF NOT EXISTS location
(
    location_id   SERIAL PRIMARY KEY,
    name          VARCHAR(255),
    country       VARCHAR(255),
    city          VARCHAR(255),
    postal_code   VARCHAR(255),
    street_name   VARCHAR(255),
    contact_id    INTEGER,
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
);

CREATE TABLE IF NOT EXISTS event
(
    event_id        SERIAL PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    content         TEXT,
    remark          TEXT,
    date_time       timestamptz,
    is_online_event BOOLEAN,
    is_complete     BOOLEAN,
    event_status    VARCHAR(255),
    location_status VARCHAR(255),
    schedule_status VARCHAR(255),
    location_id     INTEGER
);

CREATE TABLE IF NOT EXISTS note
(
    event     INTEGER,
    timestamp timestamptz,
    content   text
);

CREATE TABLE IF NOT EXISTS tag_name
(
    tag_id SERIAL PRIMARY KEY,
    name   VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS event_tag
(
    event INTEGER,
    tag   INTEGER,
    PRIMARY KEY (event, tag)
);

CREATE TABLE IF NOT EXISTS speaker_event
(
    person INTEGER,
    event  INTEGER,
    PRIMARY KEY (person, event)
)

