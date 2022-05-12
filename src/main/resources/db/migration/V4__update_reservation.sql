ALTER TABLE reservation ADD reservable_id INT NOT NULL DEFAULT -1 AFTER venue_id;
ALTER TABLE reservation ADD status INT NOT NULL DEFAULT 0;

CREATE TABLE activity(
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO activity (name) VALUES ('Swim');
INSERT INTO activity (name) VALUES ('Tennis');
INSERT INTO activity (name) VALUES ('Other');

ALTER TABLE venue ADD activity_id INT NOT NULL DEFAULT 0 AFTER id;