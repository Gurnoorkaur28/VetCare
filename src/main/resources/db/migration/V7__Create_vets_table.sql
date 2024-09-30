CREATE TABLE IF NOT EXISTS vet (
    vet_id SERIAL PRIMARY KEY,
    clinic_name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone_number VARCHAR(20),
    email VARCHAR(100),
    password VARCHAR(100) NOT NULL,
    clinic_id BIGINT,
    FOREIGN KEY (clinic_id) REFERENCES vet_booking(ID)
);