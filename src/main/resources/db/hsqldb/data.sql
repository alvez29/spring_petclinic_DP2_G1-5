-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities VALUES ('vet1','veterinarian');

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'Beagle');
INSERT INTO types VALUES (2, 'Boxer');
INSERT INTO types VALUES (3, 'Yorkshire');
INSERT INTO types VALUES (4, 'German shepherd');
INSERT INTO types VALUES (5, 'Greyhound');
INSERT INTO types VALUES (6, 'Labrador');
INSERT INTO types VALUES (7, 'Dalmatian');
INSERT INTO types VALUES (8, 'Buldog');
INSERT INTO types VALUES (9, 'Rottweiler');
INSERT INTO types VALUES (10, 'Basset Hound');
INSERT INTO types VALUES (11, 'Chow Chow');
INSERT INTO types VALUES (12, 'Fox Terrier');
INSERT INTO types VALUES (13, 'Golden Retriever');
INSERT INTO types VALUES (14, 'Pitbull');
INSERT INTO types VALUES (15, 'Bearded Collie');
INSERT INTO types VALUES (16, 'Terranova');
INSERT INTO types VALUES (17, 'Terrier Norwich');
INSERT INTO types VALUES (18, 'Pekingese');
INSERT INTO types VALUES (19, 'Siberian Husky');
INSERT INTO types VALUES (20, 'Great Dane');
INSERT INTO types VALUES (21, 'Mongrel Dog'); 
INSERT INTO types VALUES (22, 'Other');


INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 5, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 5, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 5, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits(id,pet_id,visit_date,description,clinic,competition_check) VALUES (1, 7, '2013-01-01', 'rabies shot','canin vet','PASSED');
INSERT INTO visits(id,pet_id,visit_date,description,clinic,competition_check) VALUES (2, 8, '2013-01-02', 'rabies shot','canin vet','-');
INSERT INTO visits(id,pet_id,visit_date,description,clinic,competition_check) VALUES (3, 8, '2013-01-03', 'neutered','Boyton vet','NOT PASSED');
INSERT INTO visits(id,pet_id,visit_date,description,clinic,competition_check) VALUES (4, 7, '2013-01-04', 'spayed', 'Boyton vet', 'PASSED');


INSERT INTO tournaments(dtype,id,name,date,status,reward_money,capacity,type_id,canodrome) VALUES ('Race',1,'First Race Test','2020-04-05','FINISHED', 7500.00 ,800,5,'Gran Hipodromo de Andalucia');
INSERT INTO tournaments(dtype,id,name,date,status,reward_money,capacity,type_id,canodrome) VALUES ('Race',2,'Second Race Test','2020-06-14','DRAFT', 7500.00 ,800,5,'Lorem Ipsum Patata');
INSERT INTO tournaments(dtype,id,name,date,status,reward_money,capacity,type_id,place) VALUES ('Beauty',3,'Beauty ConTEST','2020-06-10','FINISHED', 7500.00,800,1,'Pabellon España');
INSERT INTO tournaments(dtype,id,name,date,status,reward_money,capacity,type_id,circuit) VALUES ('Hability',4,'Hability ConTEST','2020-07-10','DRAFT', 8000.00,800,1,'WiZink Center');
INSERT INTO tournaments(dtype,id,name,date,status,reward_money,capacity,type_id,circuit) VALUES ('Hability',5,'Hability ConTEST sponsors','2020-07-12','DRAFT', 8000.00,800,1,'IFEMA');

INSERT INTO sponsor(id,name,money,url,tournament_id) VALUES (1,'Royal Canin', 4000.0, 'www.royalcanin.com',1);
INSERT INTO sponsor(id,name,money,url,tournament_id) VALUES (2,'Affinity', 3000.0, 'www.affinity.com',1);
INSERT INTO sponsor(id,name,money,url,tournament_id) VALUES (3,'Pedigree', 7500.0, 'www.pedigree.com',1);

INSERT INTO sponsor(id,name,money,url,tournament_id) VALUES (4,'Royal Canin', 4000.0, 'www.royalcanin.com',5);
INSERT INTO sponsor(id,name,money,url,tournament_id) VALUES (5,'Affinity', 3000.0, 'www.affinity.com',5);
INSERT INTO sponsor(id,name,money,url,tournament_id) VALUES (6,'Pedigree', 7500.0, 'www.pedigree.com',5);

INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (1,2);
INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (1,3);
INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (1,4);
INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (1,9);
INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (1,11);

INSERT INTO judges values (1, 'Juez', 'Primero', 'Sevilla', 'primero@example.com');

INSERT INTO tournament_judges(judge_id, tournament_id) VALUES (1,1);
INSERT INTO tournament_judges(judge_id, tournament_id) VALUES (1,3);
INSERT INTO tournament_judges(judge_id, tournament_id) VALUES (1,2);

INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (2,2);
INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (2,3);
INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (2,4);
INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (2,9);
INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (2,11);

INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (3,2);
INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (3,3);
INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (3,4);
INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (3,9);
INSERT INTO tournament_pets(tournament_id, pet_id) VALUES (3,11);

INSERT INTO resulttime(id, pet_id, tournament_id, time, lowfails, mediumfails, bigfails) VALUES (1, 2, 1, 40.086, 0, 0, 0);
INSERT INTO resulttime(id, pet_id, tournament_id, time, lowfails, mediumfails, bigfails) VALUES (2, 3, 1, 38.595, 0, 0, 0);
INSERT INTO resulttime(id, pet_id, tournament_id, time, lowfails, mediumfails, bigfails) VALUES (3, 4, 1, 39.487, 0, 0, 0);
INSERT INTO resulttime(id, pet_id, tournament_id, time, lowfails, mediumfails, bigfails) VALUES (4, 9, 1, 37.9402, 0, 0, 0);
INSERT INTO resulttime(id, pet_id, tournament_id, time, lowfails, mediumfails, bigfails) VALUES (5, 11, 1, 38.276, 0, 0, 0);

INSERT INTO resultscore(id, pet_id, tournament_id, haircut, haircutdif, technique, posture) VALUES (2, 3, 3, 4, 6, 5, 5);
INSERT INTO resultscore(id, pet_id, tournament_id, haircut, haircutdif, technique, posture) VALUES (3, 4, 3, 1, 0, 0, 0);
INSERT INTO resultscore(id, pet_id, tournament_id, haircut, haircutdif, technique, posture) VALUES (4, 9, 3, 0, 0, 0, 0);
INSERT INTO resultscore(id, pet_id, tournament_id, haircut, haircutdif, technique, posture) VALUES (5, 11, 3, 4, 10, 10, 10);
