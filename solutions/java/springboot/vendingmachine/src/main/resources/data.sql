INSERT INTO product (id, name, category, price) VALUES (1, 'Salted Chips', 'snack', 1.50);
INSERT INTO product (id, name, category, price) VALUES (2, 'Chocolate Bar', 'snack', 2.00);
INSERT INTO product (id, name, category, price) VALUES (3, 'Sparkling Water', 'drink', 1.25);
INSERT INTO product (id, name, category, price) VALUES (4, 'Cold Brew', 'drink', 3.00);

INSERT INTO vending_machine (id, code, location, status) VALUES (1, 'AIRPORT_T1', 'Airport Terminal 1 - Gate A3', 'ACTIVE');
INSERT INTO vending_machine (id, code, location, status) VALUES (2, 'DOWNTOWN_LOBBY', 'Downtown Office Lobby', 'ACTIVE');

INSERT INTO slot (id, position, capacity, quantity, machine_id, product_id) VALUES (1, 'A1', 20, 15, 1, 1);
INSERT INTO slot (id, position, capacity, quantity, machine_id, product_id) VALUES (2, 'A2', 15, 10, 1, 2);
INSERT INTO slot (id, position, capacity, quantity, machine_id, product_id) VALUES (3, 'B1', 25, 20, 1, 3);
INSERT INTO slot (id, position, capacity, quantity, machine_id, product_id) VALUES (4, 'C1', 25, 22, 2, 4);
INSERT INTO slot (id, position, capacity, quantity, machine_id, product_id) VALUES (5, 'C2', 20, 18, 2, 2);
