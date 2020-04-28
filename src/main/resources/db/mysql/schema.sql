CREATE TABLE IF NOT EXISTS vets (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  INDEX(last_name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS specialties (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(80),
  INDEX(name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS vet_specialties (
  vet_id INT(4) UNSIGNED NOT NULL,
  specialty_id INT(4) UNSIGNED NOT NULL,
  FOREIGN KEY (vet_id) REFERENCES vets(id),
  FOREIGN KEY (specialty_id) REFERENCES specialties(id),
  UNIQUE (vet_id,specialty_id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS types (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(80),
  INDEX(name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS owners (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  address VARCHAR(255),
  city VARCHAR(80),
  telephone VARCHAR(20),
  INDEX(last_name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS pets (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(30),
  birth_date DATE,
  type_id INT(4) UNSIGNED NOT NULL,
  owner_id INT(4) UNSIGNED NOT NULL,
  INDEX(name),
  FOREIGN KEY (owner_id) REFERENCES owners(id),
  FOREIGN KEY (type_id) REFERENCES types(id)
  FOREIGN KEY (tournament_id) REFERENCES tournament_pets(tournament_id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS visits (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT ,
  pet_id INT(4) UNSIGNED NOT NULL,
  visit_date DATE,
  description VARCHAR(255),
  FOREIGN KEY (pet_id) REFERENCES pets(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS judges (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  contact VARCHAR(144),
  city VARCHAR(64),
  FOREIGN KEY (tournament_id) REFERENCES tournament_judges (tournament_id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS sponsor (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(30),
  money DOUBLE(10000),
  url VARCHAR(50)
  FOREIGN KEY (tournament_id) REFERENCES tournament(id)
) engine=InnoDB;


CREATE TABLE IF NOT EXISTS tournaments (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(30),
  date DATE,
  status VARCHAR(10),
  reward_money DOUBLE(10000),
  capacity INT(4),
  FOREIGN KEY (type_id) REFERENCES types(id),
  FOREIGN KEY (pet_id) REFERENCES tournament_pets(pet_id),
  FOREIGN KEY (judge_id) REFERENCES tournament_judges(judge_id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS resulttime (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  pet_id INT(4) UNSIGNED NOT NULL,
  tournament_id INT(4),
  time DOUBLE,
  lowfails INT(3),
  mediumfails INT(3),
  bigfails INT(3),
  FOREIGN KEY (tournament_id) REFERENCES tournament(id),
  FOREIGN KEY (pet_id) REFERENCES pets(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS resultscore (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  pet_id INT(4) UNSIGNED NOT NULL,
  tournament_id INT(4),
  haircut INT(1),
  haircutdif INT(1),
  technique INT(1),
  posture INT(1),
  FOREIGN KEY (tournament_id) REFERENCES tournament(id),
  FOREIGN KEY (pet_id) REFERENCES pets(id)
) engine=InnoDB;