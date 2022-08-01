INSERT INTO time_slots (time_slot_id,time_slot_date,start_time,end_time,is_keynote_time_slot)
VALUES (1,'2020-04-09','9:00','9:45',TRUE),
       (2,'2020-04-09','10:00','11:00',FALSE),
       (3,'2020-04-09','11:15','11:45',FALSE),
       (4,'2020-04-09','12:45','13:45',FALSE),
       (5,'2020-04-09','14:00','15:00',FALSE),
       (6,'2020-04-09','15:15','15:45',FALSE),
       (7,'2020-04-09','16:00','17:00',FALSE),
       (8,'2020-04-10','9:00','10:00',FALSE),
       (9,'2020-04-10','10:15','11:15',FALSE),
       (10,'2020-04-10','11:30','12:00',FALSE),
       (11,'2020-04-10','13:00','14:00',FALSE),
       (12,'2020-04-10','14:15','15:00',TRUE);

select setval('time_slots_time_slot_id_seq',COALESCE((select max(time_slot_id) + 1 from time_slots), 1));