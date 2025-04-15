insert into users (username, email, password, created_at) values ('admin', 'admin@aulab.it','$2y$10$Aon1eN7mr5mQQxt8a/CoZeOJ1MgL4FTuppTaAKv5VlfMThjXdwEga', '20240607');

insert into roles (name) values ('ROLE_ADMIN'), ('ROLE_REVISOR'), ('ROLE_WRITER'), ('ROLE_USER');


insert into user_roles (user_id, role_id) values (1, 1);

insert into categories (name) values ('politica'), ('economia'),  ('food&drink'), ('sport'), ('intrattenimento'), ('tech');