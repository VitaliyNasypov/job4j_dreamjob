ALTER TABLE posts
    ADD USER_EMAIL TEXT REFERENCES USERS (EMAIL);