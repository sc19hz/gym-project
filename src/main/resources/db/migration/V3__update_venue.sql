ALTER TABLE venue ADD capacity INT NOT NULL DEFAULT 0 AFTER icon;
ALTER TABLE venue ADD days_allow INT NOT NULL DEFAULT 3 AFTER capacity;

ALTER TABLE reservation DROP COLUMN duration;
ALTER TABLE reservation ADD end_time LONG NOT NULL AFTER start_time;

ALTER TABLE time_block DROP COLUMN type;
ALTER TABLE time_block DROP COLUMN duration;
ALTER TABLE time_block ADD end_time LONG NOT NULL AFTER start_time;
