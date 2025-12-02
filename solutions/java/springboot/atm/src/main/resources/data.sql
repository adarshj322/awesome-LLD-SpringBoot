INSERT INTO account (id, number, holder_name, balance, status) VALUES (1, '1111222233334444', 'Alice Adams', 2500.00, 'ACTIVE');
INSERT INTO account (id, number, holder_name, balance, status) VALUES (2, '9999888877776666', 'Bob Brown', 500.00, 'ACTIVE');

INSERT INTO card (id, card_number, pin_hash, active, account_id) VALUES (1, '4111111111111111', '1234', true, 1);
INSERT INTO card (id, card_number, pin_hash, active, account_id) VALUES (2, '5555444433332222', '4321', true, 2);

INSERT INTO atm (id, code, location, cash_available, status) VALUES (1, 'ATM_A1', 'Airport T1 - Gate A3', 10000.00, 'ACTIVE');
INSERT INTO atm (id, code, location, cash_available, status) VALUES (2, 'ATM_D1', 'Downtown Lobby', 7000.00, 'ACTIVE');
