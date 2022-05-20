insert into parameter (created_at,deleted_at,last_modified_at, name, value, id) values
 (now(), null, now(),'tax_percentage', '10', gen_random_uuid()),
 (now(), null, now(),'profit_percentage', '12', gen_random_uuid());

insert into roles values
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_OWNER'),
    (3, 'ROLE_MANAGER'),
    (4, 'ROLE_STOCKIST'),
    (5, 'ROLE_CASHIER'),
    (6, 'ROLE_EMPLOYEE');

insert into category values
    ('be3698af-2db7-48b1-93d7-f6730b7d8a8a', now(), null, now(), 'makanan'),
    ('5c0fbeb7-2b1b-433f-8823-473768231e4d', now(), null, now(), 'minuman');

insert into item values
    ('f33836af-ef69-489a-845e-5043c8fae64e', now(), null, now(), 'makanan1', 'image1', 'makanan 1', 'string', 'be3698af-2db7-48b1-93d7-f6730b7d8a8a'),
    ('9c407bd4-bc94-4608-97fb-72bb6ce0d897', now(), null, now(), 'makanan2', 'image2', 'makanan 2', 'string', 'be3698af-2db7-48b1-93d7-f6730b7d8a8a'),
    ('a2dcdddc-a906-469a-8847-504171e1f171', now(), null, now(), 'minuman1', 'image3', 'minuman 1', 'string', '5c0fbeb7-2b1b-433f-8823-473768231e4d');

insert into suppliers values
    ('e0f271a7-5a48-43c9-8531-0fd035029f89', now(), null, now(), 'address 1', 'makanan supp', 'makanan@gmail.com', 'supplier makanan', '03819203'),
    ('1990d052-9c52-42eb-85b7-7652ad7a3f4a', now(), null, now(), 'minuman 1', 'minuman supp', 'minuman@gmail.com', 'supplier minuman', '213123123');

insert into store values
    ('21433690-da31-4332-a6f6-8022b509ab78', now(), null, now(), 'loc 1', 'Store 1', null),
    ('2ccd86d8-91c9-4295-b4c0-a40dc8b190a4', now(), null, now(), 'loc 2', 'Store 2', null);