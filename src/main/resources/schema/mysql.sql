CREATE TABLE zuul_rate_limiter(
    id VARCHAR(50),
    refresh_interval INT(3),
    `limit` INT(3),
    `type` VARCHAR(500),
    PRIMARY KEY(id)
);