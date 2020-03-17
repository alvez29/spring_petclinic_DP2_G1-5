INSERT IGNORE INTO vets VALUES (1, 'James', 'Carter');
INSERT IGNORE INTO vets VALUES (2, 'Helen', 'Leary');
INSERT IGNORE INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT IGNORE INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT IGNORE INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT IGNORE INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT IGNORE INTO specialties VALUES (1, 'radiology');
INSERT IGNORE INTO specialties VALUES (2, 'surgery');
INSERT IGNORE INTO specialties VALUES (3, 'dentistry');

INSERT IGNORE INTO vet_specialties VALUES (2, 1);
INSERT IGNORE INTO vet_specialties VALUES (3, 2);
INSERT IGNORE INTO vet_specialties VALUES (3, 3);
INSERT IGNORE INTO vet_specialties VALUES (4, 2);
INSERT IGNORE INTO vet_specialties VALUES (5, 1);

INSERT IGNORE INTO types VALUES (1, 'Beagle');
INSERT IGNORE INTO types VALUES (2, 'Boxer');
INSERT IGNORE INTO types VALUES (3, 'Yorkshire');
INSERT IGNORE INTO types VALUES (4, 'German shepherd');
INSERT IGNORE INTO types VALUES (5, 'Greyhound');
INSERT IGNORE INTO types VALUES (6, 'Labrador');
INSERT IGNORE INTO types VALUES (7, 'Dalmatian');
INSERT IGNORE INTO types VALUES (8, 'Buldog');
INSERT IGNORE INTO types VALUES (9, 'Rottweiler');
INSERT IGNORE INTO types VALUES (10, 'Basset Hound');
INSERT IGNORE INTO types VALUES (11, 'Chow Chow');
INSERT IGNORE INTO types VALUES (12, 'Fox Terrier');
INSERT IGNORE INTO types VALUES (13, 'Golden Retriever');
INSERT IGNORE INTO types VALUES (14, 'Pitbull');
INSERT IGNORE INTO types VALUES (15, 'Bearded Collie');
INSERT IGNORE INTO types VALUES (16, 'Terranova');
INSERT IGNORE INTO types VALUES (17, 'Terrier Norwich');
INSERT IGNORE INTO types VALUES (18, 'Pekingese');
INSERT IGNORE INTO types VALUES (19, 'Siberian Husky');
INSERT IGNORE INTO types VALUES (20, 'Great Dane');
INSERT IGNORE INTO types VALUES (21, 'Bearded Collie'); 

INSERT IGNORE INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT IGNORE INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT IGNORE INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT IGNORE INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT IGNORE INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT IGNORE INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT IGNORE INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT IGNORE INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT IGNORE INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT IGNORE INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');

INSERT IGNORE INTO pets VALUES (1, 'Leo', '2000-09-07', 1, 1);
INSERT IGNORE INTO pets VALUES (2, 'Basil', '2002-08-06', 6, 2);
INSERT IGNORE INTO pets VALUES (3, 'Rosy', '2001-04-17', 2, 3);
INSERT IGNORE INTO pets VALUES (4, 'Jewel', '2000-03-07', 2, 3);
INSERT IGNORE INTO pets VALUES (5, 'Iggy', '2000-11-30', 3, 4);
INSERT IGNORE INTO pets VALUES (6, 'George', '2000-01-20', 4, 5);
INSERT IGNORE INTO pets VALUES (7, 'Samantha', '1995-09-04', 1, 6);
INSERT IGNORE INTO pets VALUES (8, 'Max', '1995-09-04', 1, 6);
INSERT IGNORE INTO pets VALUES (9, 'Lucky', '1999-08-06', 5, 7);
INSERT IGNORE INTO pets VALUES (10, 'Mulligan', '1997-02-24', 2, 8);
INSERT IGNORE INTO pets VALUES (11, 'Freddy', '2000-03-09', 5, 9);
INSERT IGNORE INTO pets VALUES (12, 'Lucky', '2000-06-24', 2, 10);
INSERT IGNORE INTO pets VALUES (13, 'Sly', '2002-06-08', 1, 10);

INSERT IGNORE INTO visits VALUES (1, 7, '2010-03-04', 'rabies shot','canin vet','PASSED');
INSERT IGNORE INTO visits VALUES (2, 8, '2011-03-04', 'rabies shot', 'canin vet','-');
INSERT IGNORE INTO visits VALUES (3, 8, '2009-06-04', 'neutered','Boyton vet','NOT PASSED');
INSERT IGNORE INTO visits VALUES (4, 7, '2008-09-04', 'spayed','Boyton vet','PASSED');


INSERT IGNORE INTO tournaments VALUES (1,'Race Test','2020-06-08','PENDING', 7500.00 ,800,1);


INSERT IGNORE INTO sponsor VALUES (1,'Royal Canin', 4000.0, 'www.royalcanin.com',1);
INSERT IGNORE INTO sponsor VALUES (2,'Affinity', 3000.0, 'www.affinity.com',1);
INSERT IGNORE INTO sponsor VALUES (3,'Pedigree', 7500.0, 'www.pedigree.com',1);

INSERT IGNORE INTO tournament_pets VALUES (1,1);
INSERT IGNORE INTO tournament_pets VALUES (1,7);
INSERT IGNORE INTO tournament_pets VALUES (1,13);
INSERT IGNORE INTO tournament_pets VALUES (1,8);

