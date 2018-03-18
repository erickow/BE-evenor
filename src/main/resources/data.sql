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
  "volunter member","volunter"
);

INSERT INTO user VALUES(
  unhex(replace(uuid(), '-', '')),
  false,"admin@mail.com","admin","admin","ini photo"
);


INSERT INTO user_roles VALUES (
  (select id from user where name='admin'),
  (select id from role where name='admin')
);

INSERT INTO user_roles VALUES (
(select id from user where name='admin'),
(select id from role where name='member')
);