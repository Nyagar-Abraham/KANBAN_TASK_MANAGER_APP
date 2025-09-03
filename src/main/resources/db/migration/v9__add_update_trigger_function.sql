-- Create or replace the trigger function
CREATE OR REPLACE FUNCTION trigger_set_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Attach the trigger to BOARDS
CREATE TRIGGER set_updated_at_boards
    BEFORE UPDATE ON boards
    FOR EACH ROW
EXECUTE FUNCTION trigger_set_timestamp();

-- Attach the trigger to BOARD_COLUMNS
CREATE TRIGGER set_updated_at_board_columns
    BEFORE UPDATE ON board_columns
    FOR EACH ROW
EXECUTE FUNCTION trigger_set_timestamp();

-- Attach the trigger to TASKS
CREATE TRIGGER set_updated_at_tasks
    BEFORE UPDATE ON tasks
    FOR EACH ROW
EXECUTE FUNCTION trigger_set_timestamp();

-- Attach the trigger to SUBTASKS
CREATE TRIGGER set_updated_at_subtasks
    BEFORE UPDATE ON subtasks
    FOR EACH ROW
EXECUTE FUNCTION trigger_set_timestamp();

-- Attach the trigger to TASK_COMMENTS
CREATE TRIGGER set_updated_at_task_comments
    BEFORE UPDATE ON task_comments
    FOR EACH ROW
EXECUTE FUNCTION trigger_set_timestamp();

-- Attach the trigger to TASK_ATTACHMENTS
CREATE TRIGGER set_updated_at_task_attachments
    BEFORE UPDATE ON task_attachments
    FOR EACH ROW
EXECUTE FUNCTION trigger_set_timestamp();
