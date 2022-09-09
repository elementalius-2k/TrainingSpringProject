INSERT INTO prod_group (name)
VALUES ('Dairy'),
       ('Meat products'),
       ('Vegetables'),
       ('Bread');

INSERT INTO producer (name, address)
VALUES ('Dairy farm', 'Voronezh'),
       ('Meat farm', 'Rostov'),
       ('Vegetable farm n.1', 'Belgorod'),
       ('Vegetable farm n.2', 'Lipetsk');

INSERT INTO product (group_id, producer_id, name, description, quantity, income_price, outcome_price)
VALUES ((SELECT id FROM prod_group WHERE prod_group.name = 'Dairy'),
        (SELECT id FROM producer WHERE producer.name = 'Dairy farm'), 'Milk', 'Fresh milk', 10, 10.0, 15.0),
       ((SELECT id FROM prod_group WHERE prod_group.name = 'Dairy'),
        (SELECT id FROM producer WHERE producer.name = 'Dairy farm'), 'Yogurt', 'Yogurt', 5, 20.0, 30.0),
       ((SELECT id FROM prod_group WHERE prod_group.name = 'Meat products'),
        (SELECT id FROM producer WHERE producer.name = 'Meat farm'), 'Beef', null, 5, 100.0, 125.5),
       ((SELECT id FROM prod_group WHERE prod_group.name = 'Vegetables'),
        (SELECT id FROM producer WHERE producer.name = 'Vegetable farm n.1'), 'Potatoes', null, 50, 12.25, 17.0);

INSERT INTO worker (name, job)
VALUES ('Ivanov I.I.', 'Manager'),
       ('Petrov P.P.', 'Trainee');

INSERT INTO partner (name, address, email, requisites)
VALUES ('Partner 1', 'Voronezh', 'email@gmail.com', 'OGRN: 1102013232'),
       ('Partner 2', 'Lipetsk', 'another_mail@mail.ru', 'OGRN: 646373143143');

INSERT INTO invoice (partner_id, worker_id, type, date)
VALUES ((SELECT id FROM partner WHERE name = 'Partner 1'), (SELECT id FROM worker WHERE name = 'Ivanov I.I.'),
        'Selling', '01.01.2000'),
       ((SELECT id FROM partner WHERE name = 'Partner 2'), (SELECT id FROM worker WHERE name = 'Petrov P.P.'),
        'Purchase', '01.01.2000');

INSERT INTO item (invoice_id, product_id, quantity)
VALUES ((SELECT id FROM invoice WHERE (type = 'Selling' AND date = '01.01.2000')),
        (SELECT id FROM product WHERE name = 'Milk'), 20),
       ((SELECT id FROM invoice WHERE (type = 'Selling' AND date = '01.01.2000')),
        (SELECT id FROM product WHERE name = 'Yogurt'), 10),
       ((SELECT id FROM invoice WHERE (type = 'Purchase' AND date = '01.01.2000')),
        (SELECT id FROM product WHERE name = 'Milk'), 30),
       ((SELECT id FROM invoice WHERE (type = 'Purchase' AND date = '01.01.2000')),
        (SELECT id FROM product WHERE name = 'Yogurt'), 15),
       ((SELECT id FROM invoice WHERE (type = 'Purchase' AND date = '01.01.2000')),
        (SELECT id FROM product WHERE name = 'Potatoes'), 50);