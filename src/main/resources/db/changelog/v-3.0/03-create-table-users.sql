CREATE TABLE USERS
(
    id         SERIAL,
    name       TEXT,
    email      TEXT PRIMARY KEY,
    password   TEXT,
    group_user TEXT
);
GO
INSERT INTO USERS (name, email, password, group_user)
VALUES ('ADMIN', 'admin@site.com',
        '50424b44463257697468486d6163534841353132' ||
        '$10000$73697465636f6d61646d696e' ||
        '$089fba18b085dd5d9d8280ce6916bd3710404929c381026d373ca2d4aada0823eaa' ||
        'df06670e3b403088b8c384fef6efe932d8ab7321f79e8c5acca89ceb19483','administration');

