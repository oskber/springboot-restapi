SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE category;
TRUNCATE TABLE location;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO category (name, symbol, description)
VALUES ('Electronics', '❤️', 'Electronic items'),
       ('Books', '❤️', 'Books and literature'),
       ('Clothing', '❤️', 'Apparel and accessories');

INSERT INTO location (name, category_id, user_id, status, description, coordinate)
VALUES
    ('Central Park', 1, 1, true, 'A large public park in New York City', ST_GeomFromText('POINT(-73.968285 40.785091)', 4326)),
    ('Eiffel Tower', 1, 1, false, 'A wrought-iron lattice tower in Paris', ST_GeomFromText('POINT(2.294351 48.858844)', 4326)),
    ('Louvre Museum', 2, 3, true, 'The world\'s largest art museum in Paris', ST_GeomFromText('POINT(2.337644 48.860611)', 4326)),
    ('Times Square', 1, 4, false, 'A major commercial intersection in New York City', ST_GeomFromText('POINT(-73.985130 40.758896)', 4326)),
    ('Statue of Liberty', 1, 5, false, 'A colossal neoclassical sculpture on Liberty Island', ST_GeomFromText('POINT(-74.044502 40.689247)', 4326));
