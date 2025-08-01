ALTER TABLE tasks
 ADD CONSTRAINT unique_col_id_board_id_title UNIQUE (title, board_id, column_id);