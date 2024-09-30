ALTER TABLE vet_users ADD COLUMN role VARCHAR(255) DEFAULT 'ROLE_USER';
ALTER TABLE vet ADD COLUMN role VARCHAR(255) DEFAULT 'ROLE_VET';
ALTER TABLE vet ADD COLUMN username VARCHAR(255) UNIQUE NOT NULL;
ALTER TABLE admin ADD COLUMN role VARCHAR(255) DEFAULT 'ROLE_ADMIN';

INSERT INTO admin (username, password, role) VALUES 
('admin1', '$2a$10$T/NNVkgmV0mzzZ.BOfm8t.Oyk1lzAW1AcEVBR5GRnmchXZmgfAw/y', 'ROLE_ADMIN'),
('admin2', '$2a$10$T/NNVkgmV0mzzZ.BOfm8t.Oyk1lzAW1AcEVBR5GRnmchXZmgfAw/y', 'ROLE_ADMIN');

INSERT INTO vet (clinic_name, address, phone_number, email, password, role, clinic_id) VALUES
('Vet Clinic 1', '123 Street', '555-1234', 'vet1@example.com', '$2a$10$T/NNVkgmV0mzzZ.BOfm8t.Oyk1lzAW1AcEVBR5GRnmchXZmgfAw/y', 'ROLE_VET', 1),
('Vet Clinic 2', '456 Avenue', '555-5678', 'vet2@example.com', '$2a$10$T/NNVkgmV0mzzZ.BOfm8t.Oyk1lzAW1AcEVBR5GRnmchXZmgfAw/y', 'ROLE_VET', 2);
