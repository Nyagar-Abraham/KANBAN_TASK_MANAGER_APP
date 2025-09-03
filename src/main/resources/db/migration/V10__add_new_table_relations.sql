-- users
ALTER TABLE users
    ADD CONSTRAINT users_pk
        PRIMARY KEY (id);


ALTER TABLE boards
    ADD CONSTRAINT boards_users_id_fk
        FOREIGN KEY (created_by) REFERENCES users (id);

ALTER TABLE tasks
    ADD CONSTRAINT tasks_users_id_fk
        FOREIGN KEY (created_by) REFERENCES users (id);



-- task_comments
ALTER TABLE task_comments
    ADD CONSTRAINT task_comments_pk
        PRIMARY KEY (id);

-- task_attachments
ALTER TABLE task_attachments
    ADD CONSTRAINT task_attachments_pk
        PRIMARY KEY (id);

-- board_members

ALTER TABLE board_members
    ADD COLUMN id SERIAL NOT NULL;

ALTER TABLE board_members
    ADD CONSTRAINT board_members_boards_id_fk
        FOREIGN KEY (board_id) REFERENCES boards (id)
            ON DELETE CASCADE ;

ALTER TABLE board_members
    ADD CONSTRAINT board_members_users_id_fk
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON DELETE CASCADE;

ALTER TABLE board_members
    ADD CONSTRAINT uc_board_members_user_id_board_id UNIQUE (board_id, user_id);

ALTER TABLE board_members
    ADD CONSTRAINT board_members_pk
        PRIMARY KEY (id);

