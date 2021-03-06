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

--Generate User (Password: hippos)
INSERT INTO public.users
(id, address, birth_date, email, first_name, last_name, gender, password, phone, username, created_at, last_modified_at, deleted_at)
VALUES
('559bd388-954f-4869-9eda-b185da5092c2'::uuid, 'Jl. Sidobali No. Admin', '2022-04-19', 'admina@email.com', 'Admin', 'A', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'admina', now(), now(), null),
('57c92aab-0e6d-46a9-91fe-8e95d1b228b7'::uuid, 'Jl. Sidobali No. Admin', '2022-04-19', 'adminb@email.com', 'Admin', 'B', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'adminb', now(), now(), null),
('e8a83fa0-9edd-4ad0-b716-5bae4bb048b0'::uuid, 'Jl. Sidobali No. Admin', '2022-04-19', 'adminc@email.com', 'Admin', 'C', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'adminc', now(), now(), null),
('138f1b7a-816f-408f-a967-e4565631a55b'::uuid, 'Jl. Sidobali No. Cashier', '2022-04-19', 'cashiera@email.com', 'Cashier', 'A', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'cashiera', now(), now(), null),
('4eb23058-7f82-4b6d-a81d-4640e6837a7f'::uuid, 'Jl. Sidobali No. Cashier', '2022-04-19', 'cashierb@email.com', 'Cashier', 'B', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'cashierb', now(), now(), null),
('99de318e-dea8-42da-b4f4-0ff4a3854185'::uuid, 'Jl. Sidobali No. Cashier', '2022-04-19', 'cashierc@email.com', 'Cashier', 'C', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'cashierc', now(), now(), null),
('f6c5476f-3fc2-425f-b2db-2ab8737e226a'::uuid, 'Jl. Sidobali No. Stockist', '2022-04-19', 'stockista@email.com', 'Stockist', 'A', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'stockista', now(), now(), null),
('31835e4e-5d06-42e8-9ab6-b3f06ef80b30'::uuid, 'Jl. Sidobali No. Stockist', '2022-04-19', 'stockistb@email.com', 'Stockist', 'B', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'stockistb', now(), now(), null),
('c25dacc7-8931-46fe-bf93-ae49a183f786'::uuid, 'Jl. Sidobali No. Stockist', '2022-04-19', 'stockistc@email.com', 'Stockist', 'C', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'stockistc', now(), now(), null),
('51f1e18c-e020-405c-9fc4-f8647aef3c99'::uuid, 'Jl. Sidobali No. Manager', '2022-04-19', 'managera@email.com', 'Manager', 'A', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247864', 'managera', now(), now(), null),
('c52db44f-38b1-4aa6-97d9-1afa19b6d99b'::uuid, 'Jl. Sidobali No. Manager', '2022-04-19', 'managerb@email.com', 'Manager', 'B', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247865', 'managerb', now(), now(), null),
('7e8f7f33-e644-4e09-9f1c-d2149317577b'::uuid, 'Jl. Sidobali No. Manager', '2022-04-19', 'managerc@email.com', 'Manager', 'C', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'managerc', now(), now(), null),
('2b7a30f6-3e4b-4fe4-a65d-06e9e91736f2'::uuid, 'Jl. Sidobali No. Owner', '2022-04-19', 'ownera@email.com', 'Owner', 'A', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'ownera', now(), now(), null),
('72397d5c-7e9d-4588-8589-74a7e15c0417'::uuid, 'Jl. Sidobali No. Owner', '2022-04-19', 'ownerb@email.com', 'Owner', 'B', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'ownerb', now(), now(), null),
('bcacc9f0-f255-453d-8bf7-bfd18e2941d0'::uuid, 'Jl. Sidobali No. Owner', '2022-04-19', 'ownerc@email.com', 'Owner', 'C', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'ownerc', now(), now(), null);

--Generate User Roles
INSERT INTO public.user_roles
(role_id, user_id)
VALUES
(1, '559bd388-954f-4869-9eda-b185da5092c2'::uuid),
(1, '57c92aab-0e6d-46a9-91fe-8e95d1b228b7'::uuid),
(1, 'e8a83fa0-9edd-4ad0-b716-5bae4bb048b0'::uuid),
(5, '138f1b7a-816f-408f-a967-e4565631a55b'::uuid),
(5, '4eb23058-7f82-4b6d-a81d-4640e6837a7f'::uuid),
(5, '99de318e-dea8-42da-b4f4-0ff4a3854185'::uuid),
(4, 'f6c5476f-3fc2-425f-b2db-2ab8737e226a'::uuid),
(4, '31835e4e-5d06-42e8-9ab6-b3f06ef80b30'::uuid),
(4, 'c25dacc7-8931-46fe-bf93-ae49a183f786'::uuid),
(3, '51f1e18c-e020-405c-9fc4-f8647aef3c99'::uuid),
(3, 'c52db44f-38b1-4aa6-97d9-1afa19b6d99b'::uuid),
(3, '7e8f7f33-e644-4e09-9f1c-d2149317577b'::uuid),
(2, '2b7a30f6-3e4b-4fe4-a65d-06e9e91736f2'::uuid),
(2, '72397d5c-7e9d-4588-8589-74a7e15c0417'::uuid),
(2, 'bcacc9f0-f255-453d-8bf7-bfd18e2941d0'::uuid),
(6, '559bd388-954f-4869-9eda-b185da5092c2'::uuid),
(6, '57c92aab-0e6d-46a9-91fe-8e95d1b228b7'::uuid),
(6, 'e8a83fa0-9edd-4ad0-b716-5bae4bb048b0'::uuid),
(6, '138f1b7a-816f-408f-a967-e4565631a55b'::uuid),
(6, '4eb23058-7f82-4b6d-a81d-4640e6837a7f'::uuid),
(6, '99de318e-dea8-42da-b4f4-0ff4a3854185'::uuid),
(6, 'f6c5476f-3fc2-425f-b2db-2ab8737e226a'::uuid),
(6, '31835e4e-5d06-42e8-9ab6-b3f06ef80b30'::uuid),
(6, 'c25dacc7-8931-46fe-bf93-ae49a183f786'::uuid),
(6, '51f1e18c-e020-405c-9fc4-f8647aef3c99'::uuid),
(6, 'c52db44f-38b1-4aa6-97d9-1afa19b6d99b'::uuid),
(6, '7e8f7f33-e644-4e09-9f1c-d2149317577b'::uuid),
(6, '2b7a30f6-3e4b-4fe4-a65d-06e9e91736f2'::uuid),
(6, '72397d5c-7e9d-4588-8589-74a7e15c0417'::uuid),
(6, 'bcacc9f0-f255-453d-8bf7-bfd18e2941d0'::uuid);

insert into store values
    ('21433690-da31-4332-a6f6-8022b509ab78', now(), null, now(), 'loc 1', 'Store 1', '51f1e18c-e020-405c-9fc4-f8647aef3c99'),
    ('2ccd86d8-91c9-4295-b4c0-a40dc8b190a4', now(), null, now(), 'loc 2', 'Store 2', 'c52db44f-38b1-4aa6-97d9-1afa19b6d99b');

INSERT INTO public.store_item (id,created_at,deleted_at,last_modified_at,fixed_price,price_mode,stock,item_id,store_id) VALUES
	 ('490c44e3-0a48-4281-9810-e83df76b0476',now(),NULL,now(),0.00,'BY_SYSTEM',6000,'f33836af-ef69-489a-845e-5043c8fae64e','21433690-da31-4332-a6f6-8022b509ab78'),
	 ('ab2f493c-e6e4-4788-bcd1-662c709f6e09',now(),NULL,now(),0.00,'BY_SYSTEM',6000,'a2dcdddc-a906-469a-8847-504171e1f171','21433690-da31-4332-a6f6-8022b509ab78');

INSERT INTO public.store_employee (id,created_at,deleted_at,last_modified_at,store_id,user_id) VALUES
	 ('4ae2d837-1d5a-420d-ae35-3bdc99268cae','2022-05-24 10:45:43.486',NULL,'2022-05-24 10:45:43.486','21433690-da31-4332-a6f6-8022b509ab78','f6c5476f-3fc2-425f-b2db-2ab8737e226a'),
	 ('74e6437f-bbfd-4b39-9c80-5298930803ba','2022-05-24 10:46:01.393',NULL,'2022-05-24 10:46:01.393','21433690-da31-4332-a6f6-8022b509ab78','138f1b7a-816f-408f-a967-e4565631a55b');

insert into voucher (id, created_at,deleted_at,last_modified_at,name,code,description,end_date,minimum_purchase,start_date,value,quota) values
    ('2041cf07-1772-4e3b-8016-366f680680c0', now(), null, now(), 'Voucher 1','MUSIMPANAS2022', 'desc voucher 1', current_date, 10000.00,current_date, 2000.00,10),
    ('4621f304-8548-4e2d-86f6-4a3237f781b5', now(), null, now(), 'Voucher 2','MUSIMDINGIN2022', 'desc voucher 2', current_date, 20000.00,current_date, 2000.00,10);

INSERT INTO public.incoming_item
(id, created_at, deleted_at, last_modified_at, buy_date, buy_price, buy_qty, expiry_date, price_per_item, store_item_id, supplier_id)
VALUES
    ('7d99b46f-416e-4adf-881e-b6547a40a0d2'::uuid, '2022-06-01 23:58:56.512', NULL, '2022-06-01 23:58:56.512', '2022-06-01 16:58:53.390', 1000000.00, 5000, '2022-06-30', 100000.00, '490c44e3-0a48-4281-9810-e83df76b0476'::uuid, 'e0f271a7-5a48-43c9-8531-0fd035029f89'::uuid),
    ('3fed2c16-f636-47db-ae63-2e7d52cfaa59'::uuid, '2022-04-01 23:59:12.429', NULL, '2022-04-01 23:59:12.429', '2022-04-01 16:59:08.835', 100000.00, 1000, '2022-04-30', 100.00, '490c44e3-0a48-4281-9810-e83df76b0476'::uuid, 'e0f271a7-5a48-43c9-8531-0fd035029f89'::uuid),
    ('0b498f6d-ffc8-4ae2-9f74-74f17e90408b'::uuid, '2022-06-01 23:59:34.782', NULL, '2022-06-01 23:59:34.782', '2022-06-01 16:59:31.808', 500000.00, 1000, '2022-07-30', 500.00, 'ab2f493c-e6e4-4788-bcd1-662c709f6e09'::uuid, '1990d052-9c52-42eb-85b7-7652ad7a3f4a'::uuid),
    ('498710ab-fba9-47df-a480-ab7e1d06ec7d'::uuid, '2022-05-01 23:59:51.569', NULL, '2022-05-01 23:59:51.569', '2022-05-01 16:59:47.235', 100000000.00, 5000, '2022-05-30', 20000.00, 'ab2f493c-e6e4-4788-bcd1-662c709f6e09'::uuid, '1990d052-9c52-42eb-85b7-7652ad7a3f4a'::uuid);
