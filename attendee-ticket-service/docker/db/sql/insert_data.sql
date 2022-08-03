INSERT INTO ticket_types (ticket_type_code,ticket_type_name,description,includes_workshop)
VALUES ('P','Premium','Access to all conference events plus attend the workshop of your choice.',TRUE),
       ('S','Standard','Access to all conference keynotes,sessions,community open spaces and the exhibition hall',FALSE),
       ('C','Community','Access to keynotes,community open spaces and the exhibition hall',FALSE);

INSERT INTO pricing_categories (pricing_category_code,pricing_category_name,pricing_start_date,pricing_end_date)
VALUES ('E','Early Bird','2019-12-01','2020-01-15'),
       ('R','Regular','2020-01-16','2020-03-20'),
       ('L','Last Minute','2020-03-21','2020-04-07');

INSERT INTO ticket_prices (ticket_price_id,ticket_type_code,pricing_category_code,base_price)
VALUES (1,'P','E',800),
       (2,'P','R',1000),
       (3,'P','L',1200),
       (4,'S','E',500),
       (5,'S','R',700),
       (6,'S','L',1000),
       (7,'C','E',100),
       (8,'C','R',200),
       (9,'C','L',300);

INSERT INTO discount_codes (discount_code_id, discount_code, discount_name, discount_type, discount_amount)
VALUES (1, 'X12345', 'Return To School 2022', 'P', 500),
       (2, 'Y12345', '10th Anniversary 2022', 'T', 200),
       (3, 'Z12345', 'Visa Card Exclusive 2022', 'T', 100);

INSERT INTO attendees
(attendee_id, first_name, last_name, title, company, email, phone_number)
VALUES (1, 'Peter', 'Chan' , 'Programmer', 'ABC company', 'peterchan@email.com', '12345678'),
       (2, 'John', 'Sara' , 'Programmer', 'ABC company', 'omg@email.com', '11111'),
       (3, 'Ben', 'Lamaa' , 'Programmer', 'ABC company', 'ben@email.com', '123');

INSERT INTO attendee_tickets
(attendee_ticket_id, attendee_id, ticket_price_id, discount_code_id, net_price)
VALUES (1, 1, 1, 1, 300),
       (2, 2, 3, 3, 1100);


select setval('attendees_attendee_id_seq',COALESCE((select max(attendee_id) + 1 from attendees), 1));
select setval('attendee_tickets_attendee_ticket_id_seq',COALESCE((select max(attendee_ticket_id) + 1 from attendee_tickets), 1));
select setval('discount_codes_discount_code_id_seq',COALESCE((select max(discount_code_id) + 1 from discount_codes), 1));
select setval('ticket_prices_ticket_price_id_seq',COALESCE((select max(ticket_price_id) + 1 from ticket_prices), 1));