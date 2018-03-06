INSERT INTO role VALUES(
  unhex(replace(uuid(), '-', '')),
  "admin member","admin"
);

INSERT INTO role VALUES(
  unhex(replace(uuid(), '-', '')),
  "member description","member"
);

INSERT INTO role VALUES(
  unhex(replace(uuid(), '-', '')),
  "member member","member"
);

INSERT INTO user VALUES(
  unhex(replace(uuid(), '-', '')),
  "admin@mail.com","admin","admin","ini photo"
);


INSERT INTO user_role VALUES (
  (select id from user where name='admin'),
  (select id from role where name='admin')
);

INSERT INTO user_role VALUES (
(select id from user where name='admin'),
(select id from role where name='member')
);