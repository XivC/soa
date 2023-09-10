CREATE TABLE IF NOT EXISTS Persons (

    passport_id VARCHAR(47) PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    height BIGINT CHECK (height > 0 OR height IS NULL),
    weight DOUBLE PRECISION CHECK (weight > 0 OR weight IS NULL),
    nationality VARCHAR(64) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Dragons (

    id BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    coordinate_x INTEGER,
    coordinate_y BIGINT NOT NULL CHECK (coordinate_y > -740),
    creation_date DATE NOT NULL,
    age INTEGER CHECK (age > 0 OR age IS NULL),
    color VARCHAR(64),
    "type" VARCHAR(64) NOT NULL,
    "character" VARCHAR(64),
    killer_id VARCHAR(47) REFERENCES Persons(passport_id)
);