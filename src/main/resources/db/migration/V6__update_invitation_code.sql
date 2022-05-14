ALTER TABLE invitation_code ADD make_time INT NOT NULL DEFAULT 0;

CREATE TABLE user_request(
	id INT AUTO_INCREMENT PRIMARY KEY,
	user_id INT NOT NULL,
	manager_id INT NOT NULL,
	reservation_id INT NOT NULL,
	description VARCHAR(256) NOT NULL,
	make_time BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;