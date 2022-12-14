CREATE TABLE workshops
(
    workshop_id   SERIAL PRIMARY KEY,
    workshop_name varchar(60)   NOT NULL,
    description   varchar(1024) NOT NULL,
    requirements  varchar(1024) NOT NULL,
    room          varchar(30)   NOT NULL,
    capacity      integer       NOT NULL
);

CREATE TABLE workshop_speakers
(
    id          SERIAL  PRIMARY KEY,
    workshop_id integer NOT NULL REFERENCES workshops (workshop_id),
    speaker_id  integer NOT NULL
);

CREATE TABLE workshop_registrations
(
    id                 SERIAL  PRIMARY KEY,
    workshop_id        integer NOT NULL REFERENCES workshops (workshop_id),
    attendee_ticket_id integer NOT NULL
);