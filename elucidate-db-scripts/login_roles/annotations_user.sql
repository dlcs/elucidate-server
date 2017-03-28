-- Role: annotations_user

-- DROP ROLE annotations_user;

CREATE ROLE annotations_user LOGIN UNENCRYPTED PASSWORD 'changeme' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;
GRANT annotations_role TO annotations_user;
