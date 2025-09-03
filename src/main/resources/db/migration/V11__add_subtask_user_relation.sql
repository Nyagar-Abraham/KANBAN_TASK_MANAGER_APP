ALTER TABLE subtasks
  ADD CONSTRAINT subtasks_users_fk
    FOREIGN KEY (assigned_to) REFERENCES users (id);

ALTER TABLE task_attachments
    DROP COLUMN updated_by;

ALTER TABLE task_attachments
    ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT now();