INSERT INTO workshops (workshop_id,workshop_name,description,requirements,room,capacity)
VALUES (1,'More Effective Agile Practices','','','Cedar',50),
       (2,'Azure DevOps One Day Bootcamp','','','Cherry',50),
       (3,'Level Up Your Architecure Skills','','','Maple',20),
       (4,'Building Microservices with Spring','','','Aspen',30),
       (5,'SQL Server Performance Tuning','','','Hickory',40),
       (6,'Serverless Architectures Using AWS','','','Cottonwood',30),
       (7,'Architecting Large Scale React Applications','','','Sycamore',30),
       (8,'Machine Learning Quick Start','','','Chestnut',40),
       (9,'Data Analytics with Tableau','','','Poplar',40),
       (10,'Python for Enterprise Developers','','','Birch',40),
       (11,'Hands on Vue.js','','','Ash',40),
       (12,'Building APIs in ASP.NET Core','','','Oak',30);

select setval('workshops_workshop_id_seq',COALESCE((select max(workshop_id) + 1 from workshops), 1));
select setval('workshop_speakers_id_seq',COALESCE((select max(id) + 1 from workshop_speakers), 1));
select setval('workshop_registrations_id_seq',COALESCE((select max(id) + 1 from workshop_registrations), 1));