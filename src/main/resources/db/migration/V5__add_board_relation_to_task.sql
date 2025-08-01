ALTER TABLE tasks
    ADD board_id INTEGER NOT NULL ;

ALTER TABLE tasks
    ADD CONSTRAINT tasks_boards_id__fk
        FOREIGN KEY (board_id) REFERENCES boards (id)
          ON DELETE CASCADE

