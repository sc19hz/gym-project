CREATE TABLE IF NOT EXISTS user(
	user_id INT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(64) NOT NULL,
	picture VARCHAR(64) NOT NULL,
	email VARCHAR(64) NOT NULL,
	password VARCHAR(64) NOT NULL,
	user_status BOOLEAN NOT NULL,
	last_login BIGINT NOT NULL,
	reservation_status INT NOT NULL,
	amount INT NOT NULL,
	gender VARCHAR(16) NOT NULL,
	height INT NOT NULL,
	weight INT NOT NULL,
	total_training_hours INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS record(
	record_id INT AUTO_INCREMENT PRIMARY KEY,
	user_id INT NOT NULL,
	month INT NOT NULL,
	venue_id INT NOT NULL,
	activity_length INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS employee(
	employee_id INT AUTO_INCREMENT PRIMARY KEY,
	employee_name VARCHAR(64) NOT NULL,
	position VARCHAR(64) NOT NULL,
	user_id INT NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS manager(
	manager_id INT AUTO_INCREMENT PRIMARY KEY,
	manager_name VARCHAR(64) NOT NULL,
	position VARCHAR(64) NOT NULL,
	user_id INT NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS reservation(
	reservation_id INT AUTO_INCREMENT PRIMARY KEY,
	user_id INT NOT NULL,
	venue_id INT NOT NULL,
	reservation_start_time BIGINT NOT NULL,
	reservation_end_time BIGINT NOT NULL,
	reservation_make_time BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS activity(
	activity_id INT AUTO_INCREMENT PRIMARY KEY,
	activity_name VARCHAR(64),
	max_venue_number INT NOT NULL,
	current_venue_number INT NOT NULL,
	current_status BOOLEAN NOT NULL,
	location VARCHAR(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS venue(
	venue_id INT AUTO_INCREMENT PRIMARY KEY,
	activity_id INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
