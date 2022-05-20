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