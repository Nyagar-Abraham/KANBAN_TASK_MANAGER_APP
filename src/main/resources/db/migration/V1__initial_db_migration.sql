CREATE TABLE boards(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE board_columns(
   id SERIAL PRIMARY KEY ,
   name VARCHAR(255) NOT NULL ,
   board_id INTEGER NOT NULL ,

  CONSTRAINT boards_columns_id_fk
    FOREIGN KEY (board_id) REFERENCES boards(id)
                    ON DELETE CASCADE

);

CREATE TYPE status_enum AS ENUM('TODO','DOING','DONE');

CREATE TABLE tasks(
    id SERIAL PRIMARY KEY ,
    title VARCHAR(255) NOT NULL ,
    description TEXT,
    status status_enum not null default 'TODO',
    column_id INTEGER NOT NULL ,

    CONSTRAINT board_columns_tasks_id_fk
        FOREIGN KEY (column_id) REFERENCES board_columns(id)
                  ON DELETE CASCADE
);

CREATE TABLE subtasks(
    id SERIAL PRIMARY KEY ,
    title VARCHAR(255) NOT NULL ,
    is_completed BOOLEAN DEFAULT FALSE,
    task_id INTEGER NOT NULL ,

    CONSTRAINT tasks_subtasks_id_fk
        FOREIGN KEY (task_id) REFERENCES tasks(id)
                     ON DELETE CASCADE
);