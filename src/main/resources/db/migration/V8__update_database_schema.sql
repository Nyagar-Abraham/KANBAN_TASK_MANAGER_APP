-- BOARDS TABLE
ALTER TABLE boards
    ADD description TEXT;

ALTER TABLE boards
    ADD created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL;

ALTER TABLE boards
    ADD updated_at TIMESTAMP WITH TIME ZONE;

ALTER TABLE boards
    ADD created_by INTEGER NOT NULL;

ALTER TABLE boards
    ADD is_active BOOLEAN DEFAULT TRUE NOT NULL;

ALTER TABLE boards
    ADD color VARCHAR(30);

-- BOARD_COLUMNS
ALTER TABLE board_columns
    ADD position SMALLSERIAL;

ALTER TABLE board_columns
    ADD color VARCHAR(30);

ALTER TABLE board_columns
    ADD task_limit INTEGER;

ALTER TABLE board_columns
    ADD created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL;

ALTER TABLE board_columns
    ADD updated_at TIMESTAMP WITH TIME ZONE;

-- TASKS
ALTER TABLE tasks
    ADD position SMALLSERIAL;

ALTER TABLE tasks
    ADD priority VARCHAR(50) NOT NULL;

ALTER TABLE tasks
    ADD due_date DATE;

ALTER TABLE tasks
    ADD created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL;

ALTER TABLE tasks
    ADD updated_at TIMESTAMP WITH TIME ZONE;

ALTER TABLE tasks
    ADD assigned_to INTEGER;

ALTER TABLE tasks
    ADD created_by INTEGER NOT NULL;

ALTER TABLE tasks
    ADD TAGS VARCHAR(300);

ALTER TABLE tasks
    ADD estimated_hours INTEGER NOT NULL;

ALTER TABLE tasks
    ADD attachment_url TEXT;

-- SUBTASKS
ALTER TABLE subtasks
    ADD position SMALLSERIAL;

ALTER TABLE subtasks
    ADD description TEXT;

ALTER TABLE subtasks
    ADD created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL;

ALTER TABLE subtasks
    ADD updated_at TIMESTAMP WITH TIME ZONE;

ALTER TABLE subtasks
    ADD assigned_to INTEGER;

ALTER TABLE subtasks
    ADD due_date DATE;

-- CREATE USERS TABLE
CREATE TABLE users
(
    id       INTEGER      NOT NULL,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL
);

-- BOARD_MEMBERS
CREATE TABLE board_members
(
    board_id  INTEGER                                NOT NULL,
    user_id   INTEGER                                NOT NULL,
    role      VARCHAR(100)                           NOT NULL,
    joined_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    is_active BOOLEAN DEFAULT TRUE                   NOT NULL
);

-- TASK_COMMENTS
CREATE TABLE task_comments
(
    id         SERIAL                                 NOT NULL,
    task_id    INTEGER                                NOT NULL,
    user_id    INTEGER                                NOT NULL,
    content    TEXT                                   NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE
);

-- TASK_ATTACHMENTS
CREATE TABLE task_attachments
(
    id          SERIAL                                 NOT NULL,
    task_id     INTEGER                                NOT NULL,
    filename    VARCHAR(255)                           NOT NULL,
    file_url    VARCHAR(255)                           NOT NULL,
    file_size   INTEGER                                NOT NULL,
    file_type   VARCHAR(255)                           NOT NULL,
    updated_by  INTEGER                                NOT NULL,
    uploaded_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL
);
