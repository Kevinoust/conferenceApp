INSERT INTO tags (tag_id,description)
VALUES (1,'.NET'),
       (2,'Java'),
       (3,'Python'),
       (4,'JavaScript'),
       (5,'Angular'),
       (6,'React'),
       (7,'Vue.js'),
       (8,'Web'),
       (9,'Architecture'),
       (10,'Soft Skills'),
       (11,'Agile'),
       (12,'Cloud');

select setval('tags_tag_id_seq',COALESCE((select max(tag_id) + 1 from tags), 1));