SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE category;
TRUNCATE TABLE location;

INSERT INTO category (name, symbol, description)
VALUES ('Electronics', 'E', 'Electronic items'),
       ('Books', 'B', 'Books and literature'),
       ('Clothing', 'C', 'Apparel and accessories');

INSERT INTO location (name, category_id, user_id, status, description, coordinates)
VALUES
    ('Central Park', 1, 101, true, 'A large public park in New York City', ST_GeomFromText('POINT(40.785091 -73.968285)')),
    ('Eiffel Tower', 1, 102, true, 'A wrought-iron lattice tower in Paris', ST_GeomFromText('POINT(48.858844 2.294351)')),
    ('Louvre Museum', 2, 103, true, 'The world\'s largest art museum in Paris', ST_GeomFromText('POINT(48.860611 2.337644)')),
    ('Times Square', 1, 104, false, 'A major commercial intersection in New York City', ST_GeomFromText('POINT(40.758896 -73.985130)')),
    ('Statue of Liberty', 1, 105, false, 'A colossal neoclassical sculpture on Liberty Island', ST_GeomFromText('POINT(40.689247 -74.044502)'));