INSERT INTO speakers (speaker_id,first_name,last_name,title,company,speaker_bio,speaker_photo)
VALUES (1,'Sergio','Becker','Senior Developer','MicroOcean Software','Test', null),
       (2,'James','Lowrey','Solutions Architect','Fabrikam Industries','Test', null),
       (3,'Gloria','Franklin','Enerprise Architect','Carved Rock Online','Test', null),
       (4,'Lori','Vanhoose','Java Technical Lead','National Bank','Test', null),
       (5,'Raymond','Hall','Senior Developer','City Power and Electric','Test', null),
       (6,'Sam','Vasquez','Software Analyst','Globalmantics Consulting','Test', null),
       (7,'Justin','Clark','Principal Engineer','Tangerine Hippopotamus Consulting','Test', null),
       (8,'Barbara','Williams','Senior DBA','Contoso','Test', null),
       (9,'James','Sharp','Technical Lead','Adventureworks','Test', null),
       (10,'Julie','Rowe','Software Architect','Northwind Trading','Test', null),
       (11,'Tonya','Burke','Senior Cloud Consultant','Big Data Consulting','Test', null),
       (12,'Nicole','Perry','Engineering Manager','World Wide Importers','Test', null),
       (13,'James','Curtis','Cloud Architect','Farmington Research','Test', null),
       (14,'Patti','White','Program Manager','State Investments','Test', null),
       (15,'Andrew','Graham','Software Architect','Property Insurance Group','Test', null),
       (16,'Lenn','van der Brug','Solutions Architect','Globalmantics Consulting','Test', null),
       (17,'Stephan','Leijtens','Application Development Manager','Bank Europe','Test', null),
       (18,'Anja','Koehler','Software Engineer','Contoso','Test', null),
       (19,'Petra','Holtzmann','Senior API Engineer','European Investment Partners','Test', null),
       (20,'Jens','Lundberg','Consultant','Aqua Sky Consulting','Test', null),
       (21,'Linda','Carver','Senior Developer','Chicago Technology Research','Test', null),
       (22,'Ronald','McMillian','Software Architect','National Bank','Test', null),
       (23,'Dustin','Finn','Software Engineer','Globalmantics Consulting','Test', null),
       (24,'Sharon','Johnson','Solutions Architect','National Aerospace Technologies','Test', null),
       (25,'Karen','McClure','.NET Architect','Adventureworks','Test', null),
       (26,'Matthew','Thompson','Technical Lead','Fabrikam Industries','Test', null),
       (27,'Chris','Moore','Solutions Architect','World Wide Importers','Test', null),
       (28,'Ken','Perry','Software Engineer','International Industrial Works','Test', null),
       (29,'Christie','Fournier','Application Architect','National Software Services','Test', null),
       (30,'Jenny','Lee','Azure Cloud Architect','Prairie Cloud Solutions','Test', null),
       (31,'Alicia','Peng','Senior Cloud Consultant','Cloud Management Partners','Test', null),
       (32,'Page','Reid','Lead Azure Engineer','State Investments','Test', null),
       (33,'Anke','Holzman','Senior AWS Consultant','Cloud Management Partners','Test', null),
       (34,'Dylan','Wilkinson','Principal AWS Engineer','Cloud Native Labs','Test', null),
       (35,'Henry','Duke','Engineering Lead','Wired Brain Coffee','Test', null),
       (36,'Cynthia','Crandall','Senior Business Analyst','Wired Brain Coffee','Test', null),
       (37,'Clara','Dawson','Agile Coach','Agile Coaches Inc','Test', null),
       (38,'Ann','Martinez','Senior AWS Consultant','Western Consulting Services','Test', null),
       (39,'James','King','Staff AWS Engineer','Northern States Bank','Test', null),
       (40,'Simon','Williams','Chief Technology Officer','NorthernSoft Systems','Test', null);

select setval('speakers_speaker_id_seq',COALESCE((select max(speaker_id) + 1 from speakers), 1));