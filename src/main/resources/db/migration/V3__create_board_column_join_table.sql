ALTER TABLE board_columns
    DROP CONSTRAINT boards_columns_id_fk;

CREATE TABLE board_column_join
(
    board_id  SERIAL NOT NULL,
    column_id SERIAL NOT NULL,

    CONSTRAINT pk_board_column_join PRIMARY KEY (board_id,column_id)
);

ALTER TABLE board_column_join
    ADD CONSTRAINT fk_board_column_join_on_board FOREIGN KEY (board_id) REFERENCES boards (id);

ALTER TABLE board_column_join
    ADD CONSTRAINT fk_board_column_join_on_board_column FOREIGN KEY (column_id) REFERENCES board_columns (id);