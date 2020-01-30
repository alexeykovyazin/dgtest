CONNECT 'localhost/3050:C:/dgtest/src/test/resurces/WorkDB/ReplicaAsync/ReplicaAsync.FDB' 
USER 'SYSDBA' PASSWORD 'masterkey';
SELECT COUNT(*) FROM TEST1;