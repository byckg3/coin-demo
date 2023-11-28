DELETE FROM currencies;

INSERT INTO currencies( code, name, created_date, last_modified, version )
VALUES ( 'EUR', '歐元', '2023-03-04 01:30:00', '2023-03-04 01:30:00', 0 );

INSERT INTO headers( description, url, creator, created_date, modifier, last_modified )
VALUES ( 'testing Description', 'testing url', 'admin', '2023-09-11T15:25:52.066318+00', 'admin', '2023-09-11T15:25:52.066318+00' );