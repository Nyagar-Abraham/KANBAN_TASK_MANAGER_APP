ALTER TABLE board_columns
    ADD CONSTRAINT uc_board_columns_name UNIQUE (name);

ALTER TABLE boards
    ADD CONSTRAINT uc_boards_name UNIQUE (name);