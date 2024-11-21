DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS category;


CREATE TABLE category
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL UNIQUE,
    symbol      VARCHAR(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    description VARCHAR(255)                            NOT NULL
);


CREATE TABLE location
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    category_id  INT                                     NOT NULL,
    user_id      INT                                     NOT NULL,
    status       BOOLEAN   DEFAULT true, -- true = public, false = private
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    description  VARCHAR(255)                            NOT NULL,
    coordinate   GEOMETRY                                NOT NULL SRID 4326,
    deleted      BOOLEAN   DEFAULT false,
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE
);

