insert into "restaurant_management"."restaurants" (deleted, version, created_at, created_by, name,
                                                   cnpj, type_of_cuisine, latitude, longitude,
                                                   street, number, neighborhood, city, state,
                                                   postal_code, open_at, close_at, people_capacity)
select false,
       0,
       now(),
       'teste',
       'Restaurant teste',
       '04638576000130',
       'Brasileira',
       -23.56390,
       -46.65239,
       'Av São Paulo',
       '1000',
       'Centro',
       'São Paulo',
       'SP',
       '74000000',
       '11:00',
       '23:00',
       200
where not exists(select id
                 from restaurant_management.restaurants r2
                 where r2.cnpj = '04638576000130');