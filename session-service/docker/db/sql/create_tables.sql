CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE sessions
(
    session_id          SERIAL        PRIMARY KEY,
    session_name        varchar(80)   NOT NULL,
    session_description varchar(1024) NOT NULL,
    session_length      integer       NOT NULL,
	session_uuid        uuid 		  NOT NULL
);

CREATE TABLE session_schedule
(
    id           SERIAL      PRIMARY KEY,
    session_id   integer     NOT NULL REFERENCES sessions (session_id),
    time_slot_id integer     NOT NULL,
    room         varchar(30) NOT NULL
);

CREATE TABLE session_tags
(
    id         SERIAL           PRIMARY KEY,
    session_id integer NOT NULL REFERENCES sessions (session_id),
    tag_id     integer NOT NULL
);

CREATE TABLE session_speakers
(
    id         SERIAL           PRIMARY KEY,
    session_id integer NOT NULL REFERENCES sessions (session_id),
    speaker_id integer NOT NULL
);
