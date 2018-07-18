CREATE OR REPLACE FUNCTION public.security_user_create(
  _security_user_id character varying,
  _uid              character varying
)
  RETURNS SETOF security_user AS
$BODY$
DECLARE
  _insertedid      integer;
  _createddatetime timestamp without time zone DEFAULT now();
BEGIN
  INSERT INTO security_user (
    createddatetime,
    security_user_id,
    uid
  )
  VALUES (
    _createddatetime,
    _security_user_id,
    _uid
  )
  RETURNING id
    INTO _insertedid;
  RETURN QUERY
  SELECT u.*
  FROM
    security_user u
  WHERE
    u.id = _insertedid;
END;
$BODY$
LANGUAGE plpgsql
VOLATILE
COST 100
ROWS 1000;

CREATE OR REPLACE FUNCTION public.security_group_create(
  _ownerid integer,
  _id      character varying,
  _label   character varying)
  RETURNS SETOF security_group AS
$BODY$
DECLARE
  _insertedid      integer;
  _createddatetime timestamp without time zone DEFAULT now();
BEGIN
  INSERT INTO security_group (
    createddatetime,
    label,
    group_id,
    owner_id
  ) VALUES (
    _createddatetime,
    _label,
    _id,
    _ownerid
  )
  RETURNING id
    INTO _insertedid;
  RETURN QUERY
  SELECT g.*
  FROM
    security_group g
  WHERE
    g.id = _insertedid;
END;
$BODY$
LANGUAGE plpgsql
VOLATILE
COST 100
ROWS 1000;

CREATE OR REPLACE FUNCTION public.security_group_add_user(
  _group_id integer,
  _user_id  integer)
  RETURNS VOID AS
$BODY$
BEGIN
  INSERT INTO security_user_group_membership (
    user_id,
    group_id
  ) VALUES (
    _user_id,
    _group_id
  );
END;
$BODY$
LANGUAGE plpgsql
VOLATILE;


CREATE OR REPLACE FUNCTION public.security_group_remove_user(
  _group_id integer,
  _user_id  integer)
  RETURNS VOID AS
$BODY$
BEGIN
  DELETE FROM security_user_group_membership
  WHERE user_id = _user_id
        AND group_id = _group_id;
END;
$BODY$
LANGUAGE plpgsql
VOLATILE;

CREATE OR REPLACE FUNCTION public.security_group_add_annotation(
  _group_id      integer,
  _annotation_id integer)
  RETURNS VOID AS
$BODY$
BEGIN
  INSERT INTO annotation_group_membership (
    annotation_id,
    group_id
  ) VALUES (
    _annotation_id,
    _group_id
  );
END;
$BODY$
LANGUAGE plpgsql
VOLATILE;

CREATE OR REPLACE FUNCTION public.security_group_remove_annotation(
  _group_id      integer,
  _annotation_id integer)
  RETURNS VOID AS
$BODY$
BEGIN
  DELETE FROM annotation_group_membership
  WHERE annotation_id = _annotation_id
        AND group_id = _group_id;
END;
$BODY$
LANGUAGE plpgsql
VOLATILE;

CREATE OR REPLACE FUNCTION public.security_group_get_by_user(_user_id integer)
  RETURNS SETOF security_group AS
$BODY$
BEGIN
  RETURN QUERY
  SELECT sg1.*
  FROM security_group sg1
  WHERE sg1.owner_id = _user_id

  UNION

  SELECT sg2.*
  FROM security_user_group_membership sugm
    INNER JOIN security_group sg2 on sugm.group_id = sg2.id;
END;
$BODY$
LANGUAGE plpgsql
VOLATILE
COST 1000
ROWS 1000;
