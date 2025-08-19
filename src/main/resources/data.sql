-- Inserta usuario con ID explícito
INSERT INTO USERS (NAME, EMAIL, PASSWORD, TOKEN, CREATED, MODIFIED, LAST_LOGIN, ACTIVE)
VALUES (
    'Atilio Olazo',
    'aolazo@theakylino.com',
    '$2b$12$W23TpJRGB4V2SxWUXPL.HeYCriMNQZg8KzGCGfFLJLzXD1B.9QyPi',
    'token-temporal',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE
);

-- Inserta el teléfono con referencia directa al ID
INSERT INTO PHONES (NUMBER, CITY_CODE, COUNTRY_CODE, USER_ID)
VALUES ('929642455', '1', '57', 1);

