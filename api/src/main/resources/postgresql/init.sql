CREATE TABLE users(
    id INT PRIMARY KEY,
    email TEXT NOT NULL,
    pass_hash TEXT NOT NULL,
    salt TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_permissions(
    user_id INT REFERENCES users(id),
    permission TEXT NOT NULL
);

CREATE TABLE authorizations(
    email TEXT NOT NULL,
    token TEXT NOT NULL
);
