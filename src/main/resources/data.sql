INSERT INTO category (name, symbol, description)
VALUES ('Electronics', 'E', 'Electronic items'),
       ('Books', 'B', 'Books and literature'),
       ('Clothing', 'C', 'Apparel and accessories');

INSERT INTO location (name, category_name, user_id, description, coordinates)
VALUES ('Warehouse 1', 'Electronics', 1, 'Main electronics warehouse', POINT(40.7128, -74.0060)),
       ('Library', 'Books', 2, 'City library', POINT(34.0522, -118.2437)),
       ('Store 1', 'Clothing', 3, 'Clothing store downtown', POINT(37.7749, -122.4194));