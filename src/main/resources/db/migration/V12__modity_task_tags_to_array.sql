ALTER TABLE tasks
    ALTER COLUMN tags TYPE VARCHAR(15)[]
        USING string_to_array(tags, ',');
