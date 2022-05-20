insert into parameter (created_at,deleted_at,last_modified_at, name, value, id) values
 (now(), null, now(),'tax', 10, gen_random_uuid()),
 (now(), null, now(),'profit', 12, gen_random_uuid());

insert into roles values
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_OWNER'),
    (3, 'ROLE_MANAGER'),
    (4, 'ROLE_STOCKIST'),
    (5, 'ROLE_CASHIER'),
    (6, 'ROLE_EMPLOYEE');

--Generate User (Password: hippos)
INSERT INTO users
(id, address, birth_date, email, first_name, last_name, gender, password, phone, username)
VALUES
('559bd388-954f-4869-9eda-b185da5092c2'::uuid, 'Jl. Sidobali No. Admin', '2022-04-19', 'admina@email.com', 'Admin', 'A', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'admina'),
('57c92aab-0e6d-46a9-91fe-8e95d1b228b7'::uuid, 'Jl. Sidobali No. Admin', '2022-04-19', 'adminb@email.com', 'Admin', 'B', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'adminb'),
('e8a83fa0-9edd-4ad0-b716-5bae4bb048b0'::uuid, 'Jl. Sidobali No. Admin', '2022-04-19', 'adminc@email.com', 'Admin', 'C', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'adminc'),
('559bd388-954f-4869-9eda-b185da5092c2'::uuid, 'Jl. Sidobali No. Cashier', '2022-04-19', 'cashiera@email.com', 'Cashier', 'A', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'cashiera'),
('559bd388-954f-4869-9eda-b185da5092c2'::uuid, 'Jl. Sidobali No. Cashier', '2022-04-19', 'cashierb@email.com', 'Cashier', 'B', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'cashierb'),
('559bd388-954f-4869-9eda-b185da5092c2'::uuid, 'Jl. Sidobali No. Cashier', '2022-04-19', 'cashierc@email.com', 'Cashier', 'C', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'cashierc'),
('f6c5476f-3fc2-425f-b2db-2ab8737e226a'::uuid, 'Jl. Sidobali No. Stockist', '2022-04-19', 'stockista@email.com', 'Stockist', 'A', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'stockista'),
('31835e4e-5d06-42e8-9ab6-b3f06ef80b30'::uuid, 'Jl. Sidobali No. Stockist', '2022-04-19', 'stockistb@email.com', 'Stockist', 'B', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'stockistb'),
('c25dacc7-8931-46fe-bf93-ae49a183f786'::uuid, 'Jl. Sidobali No. Stockist', '2022-04-19', 'stockistc@email.com', 'Stockist', 'C', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'stockistc'),
('51f1e18c-e020-405c-9fc4-f8647aef3c99'::uuid, 'Jl. Sidobali No. Manager', '2022-04-19', 'managera@email.com', 'Manager', 'A', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247864', 'managera'),
('c52db44f-38b1-4aa6-97d9-1afa19b6d99b'::uuid, 'Jl. Sidobali No. Manager', '2022-04-19', 'managerb@email.com', 'Manager', 'B', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247865', 'managerb'),
('7e8f7f33-e644-4e09-9f1c-d2149317577b'::uuid, 'Jl. Sidobali No. Manager', '2022-04-19', 'managerc@email.com', 'Manager', 'C', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'managerc'),
('2b7a30f6-3e4b-4fe4-a65d-06e9e91736f2'::uuid, 'Jl. Sidobali No. Owner', '2022-04-19', 'ownera@email.com', 'Owner', 'A', 'Female', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'ownera'),
('72397d5c-7e9d-4588-8589-74a7e15c0417'::uuid, 'Jl. Sidobali No. Owner', '2022-04-19', 'ownerb@email.com', 'Owner', 'B', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'ownerb'),
('bcacc9f0-f255-453d-8bf7-bfd18e2941d0'::uuid, 'Jl. Sidobali No. Owner', '2022-04-19', 'ownerc@email.com', 'Owner', 'C', 'Male', '$2a$10$8ERgXm8KdBhF4Tt0DwUQxe//4zsSpCwCvN2rrHv1O7gIri9.XD7v.', '08583247866', 'ownerc');

--Generate User Roles
INSERT INTO public.user_roles
(role_id, user_id)
VALUES
(1, '559bd388-954f-4869-9eda-b185da5092c2'::uuid),
(1, '57c92aab-0e6d-46a9-91fe-8e95d1b228b7'::uuid),
(1, 'e8a83fa0-9edd-4ad0-b716-5bae4bb048b0'::uuid),
(2, '559bd388-954f-4869-9eda-b185da5092c2'::uuid),
(2, '559bd388-954f-4869-9eda-b185da5092c2'::uuid),
(2, '559bd388-954f-4869-9eda-b185da5092c2'::uuid),
(3, 'f6c5476f-3fc2-425f-b2db-2ab8737e226a'::uuid),
(3, '31835e4e-5d06-42e8-9ab6-b3f06ef80b30'::uuid),
(3, 'c25dacc7-8931-46fe-bf93-ae49a183f786'::uuid),
(4, '51f1e18c-e020-405c-9fc4-f8647aef3c99'::uuid),
(4, 'c52db44f-38b1-4aa6-97d9-1afa19b6d99b'::uuid),
(4, '7e8f7f33-e644-4e09-9f1c-d2149317577b'::uuid),
(5, '2b7a30f6-3e4b-4fe4-a65d-06e9e91736f2'::uuid),
(5, '72397d5c-7e9d-4588-8589-74a7e15c0417'::uuid),
(5, 'bcacc9f0-f255-453d-8bf7-bfd18e2941d0'::uuid),
(6, '559bd388-954f-4869-9eda-b185da5092c2'::uuid),
(6, '57c92aab-0e6d-46a9-91fe-8e95d1b228b7'::uuid),
(6, 'e8a83fa0-9edd-4ad0-b716-5bae4bb048b0'::uuid),
(6, '559bd388-954f-4869-9eda-b185da5092c2'::uuid),
(6, '559bd388-954f-4869-9eda-b185da5092c2'::uuid),
(6, '559bd388-954f-4869-9eda-b185da5092c2'::uuid),
(6, 'f6c5476f-3fc2-425f-b2db-2ab8737e226a'::uuid),
(6, '31835e4e-5d06-42e8-9ab6-b3f06ef80b30'::uuid),
(6, 'c25dacc7-8931-46fe-bf93-ae49a183f786'::uuid),
(6, '51f1e18c-e020-405c-9fc4-f8647aef3c99'::uuid),
(6, 'c52db44f-38b1-4aa6-97d9-1afa19b6d99b'::uuid),
(6, '7e8f7f33-e644-4e09-9f1c-d2149317577b'::uuid),
(6, '2b7a30f6-3e4b-4fe4-a65d-06e9e91736f2'::uuid),
(6, '72397d5c-7e9d-4588-8589-74a7e15c0417'::uuid),
(6, 'bcacc9f0-f255-453d-8bf7-bfd18e2941d0'::uuid);
