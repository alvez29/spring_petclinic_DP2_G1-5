CREATE TABLE IF NOT EXISTS vets (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  INDEX(last_name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS types (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(80),
  INDEX(name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS specialties (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(80),
  INDEX(name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS vet_specialties (
  vet_id INT UNSIGNED NOT NULL,
  specialty_id INT UNSIGNED NOT NULL,
  FOREIGN KEY (vet_id) REFERENCES vets(id),
  FOREIGN KEY (specialty_id) REFERENCES specialties(id),
  UNIQUE (vet_id,specialty_id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS tournaments (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dtype VARCHAR(30),
  name VARCHAR(30),
  date DATE,
  status VARCHAR(10),
  reward_money DOUBLE(10,2),
  capacity INT,
  type_id INT UNSIGNED NOT NULL,
  canodrome VARCHAR(150),
  place VARCHAR(150),
  circuit VARCHAR(150),
  FOREIGN KEY (type_id) REFERENCES types(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS owners (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  address VARCHAR(255),
  city VARCHAR(80),
  telephone VARCHAR(20),
  INDEX(last_name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS pets (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(30),
  birth_date DATE,
  type_id INT UNSIGNED NOT NULL,
  owner_id INT UNSIGNED NOT NULL,
  INDEX(name),
  FOREIGN KEY (owner_id) REFERENCES owners(id),
  FOREIGN KEY (type_id) REFERENCES types(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS visits (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  pet_id INT UNSIGNED NOT NULL,
  visit_date DATE,
  clinic VARCHAR(150),
  competition_check VARCHAR(10),
  description VARCHAR(255),
  FOREIGN KEY (pet_id) REFERENCES pets(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS judges (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  contact VARCHAR(144),
  city VARCHAR(64)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS sponsor (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(30),
  money DOUBLE(10,2),
  url VARCHAR(50),
  tournament_id INT UNSIGNED NOT NULL,
  FOREIGN KEY (tournament_id) REFERENCES tournaments(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS resulttime (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  pet_id INT UNSIGNED NOT NULL,
  tournament_id INT UNSIGNED NOT NULL,
  time DOUBLE,
  lowfails INT,
  mediumfails INT,
  bigfails INT,
  FOREIGN KEY (tournament_id) REFERENCES tournaments(id),
  FOREIGN KEY (pet_id) REFERENCES pets(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS resultscore (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  pet_id INT UNSIGNED NOT NULL,
  tournament_id INT UNSIGNED NOT NULL,
  haircut INT,
  haircutdif INT,
  technique INT,
  posture INT,
  FOREIGN KEY (tournament_id) REFERENCES tournaments(id),
  FOREIGN KEY (pet_id) REFERENCES pets(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS tournament_pets(
tournament_id INT UNSIGNED NOT NULL,
pet_id INT UNSIGNED NOT NULL,
FOREIGN KEY (tournament_id) REFERENCES tournaments(id),
FOREIGN KEY (pet_id) REFERENCES pets(id),
UNIQUE (tournament_id,pet_id)
)engine=InnoDB;

CREATE TABLE IF NOT EXISTS tournament_judges(
judge_id INT UNSIGNED NOT NULL,
tournament_id INT UNSIGNED NOT NULL,
FOREIGN KEY (judge_id) REFERENCES tournaments(id),
FOREIGN KEY (tournament_id) REFERENCES pets(id),
UNIQUE (judge_id,tournament_id)
)engine=InnoDB;

CREATE TABLE users(
	username varchar (255) NOT NULL PRIMARY KEY,
	password varchar (255) NOT NULL,
	enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
	username varchar(50) NOT NULL,
	authority varchar(50) NOT NULL
);
ALTER TABLE authorities ADD CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username);

CREATE UNIQUE INDEX ix_auth_username ON authorities (username,authority);