CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    hashedPassword VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL
);

CREATE TABLE note(
    id BIGSERIAL PRIMARY KEY,
    users_id BIGINT,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(50) NOT NULL,
    CONSTRAINT fk_users FOREIGN KEY(users_id) REFERENCES users(id)
);