CONNECT 'localhost/3050:C:/dgtest/src/test/resurces/WorkDB/MasterAsync/MasterAsync.FDB'
USER 'SYSDBA' PASSWORD 'masterkey';
CREATE TABLE TEST1(i1 integer not null primary key);
INSERT INTO TEST1(i1) VALUES(1);
COMMIT;