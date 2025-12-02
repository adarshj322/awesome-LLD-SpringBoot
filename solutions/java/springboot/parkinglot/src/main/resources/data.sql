INSERT INTO parking_lot (id, code, display_name) VALUES (1, 'CITY_CENTER', 'City Center Lot');
INSERT INTO parking_lot (id, code, display_name) VALUES (2, 'AIRPORT_EDGE', 'Airport Edge Lot');

INSERT INTO parking_floor (id, floor_number, lot_id) VALUES (1, 1, 1);
INSERT INTO parking_floor (id, floor_number, lot_id) VALUES (2, 2, 1);
INSERT INTO parking_floor (id, floor_number, lot_id) VALUES (3, 1, 2);

INSERT INTO parking_spot (id, label, vehicle_size, status, floor_id) VALUES (1, 'C1-S1', 'SMALL', 'AVAILABLE', 1);
INSERT INTO parking_spot (id, label, vehicle_size, status, floor_id) VALUES (2, 'C1-M1', 'MEDIUM', 'AVAILABLE', 1);
INSERT INTO parking_spot (id, label, vehicle_size, status, floor_id) VALUES (3, 'C1-L1', 'LARGE', 'AVAILABLE', 1);
INSERT INTO parking_spot (id, label, vehicle_size, status, floor_id) VALUES (4, 'C2-M1', 'MEDIUM', 'AVAILABLE', 2);
INSERT INTO parking_spot (id, label, vehicle_size, status, floor_id) VALUES (5, 'C2-L1', 'LARGE', 'AVAILABLE', 2);
INSERT INTO parking_spot (id, label, vehicle_size, status, floor_id) VALUES (6, 'A1-S1', 'SMALL', 'AVAILABLE', 3);
INSERT INTO parking_spot (id, label, vehicle_size, status, floor_id) VALUES (7, 'A1-M1', 'MEDIUM', 'AVAILABLE', 3);
INSERT INTO parking_spot (id, label, vehicle_size, status, floor_id) VALUES (8, 'A1-L1', 'LARGE', 'AVAILABLE', 3);
