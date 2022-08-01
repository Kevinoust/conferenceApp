CREATE TABLE time_slots
(
    time_slot_id            SERIAL                 PRIMARY KEY,
    time_slot_date          date                   NOT NULL,
    start_time              time without time zone NOT NULL,
    end_time                time without time zone NOT NULL,
    is_keynote_time_slot    boolean default false  NOT NULL
);