CREATE OR REPLACE VIEW public.annotation_get AS
    SELECT
        a.annotationid,
        a.cachekey,
        ac.collectionid,
        a.createddatetime,
        a.deleted,
        a.json,
        a.modifieddatetime,
        a.id,
        ao.user_id as ownerid,
        agm.group_ids
    FROM
        annotation a
            LEFT JOIN annotation_collection ac ON a.collectionid = ac.id
            LEFT JOIN annotation_owner ao ON ao.annotation_id = a.id
            LEFT JOIN annotation_group_memberships agm ON agm.annotation_id = a.id;

CREATE OR REPLACE FUNCTION public.annotation_create(
    _collectionid character varying,
    _annotationid character varying,
    _json jsonb,
    _ownerid integer)
RETURNS SETOF annotation_get AS
$BODY$
    DECLARE
        _insertedid integer;
        _createddatetime timestamp without time zone DEFAULT now();
    BEGIN
        INSERT INTO annotation (
            annotationid,
            collectionid,
            json,
            createddatetime,
            deleted,
            cachekey
        )
        VALUES (
            _annotationid,
            (
                SELECT
                    id
                FROM
                    annotation_collection
                WHERE
                    collectionid = _collectionid
                    AND deleted = false
                ORDER BY
                    modifieddatetime DESC LIMIT 1
            ),
            _json,
            _createddatetime,
            false,
            md5(_createddatetime::text)
        ) RETURNING id INTO _insertedid;
        IF _ownerid IS NOT NULL THEN
          INSERT INTO
            annotation_owner
          (user_id, annotation_id)
            VALUES
          (_ownerid, _insertedid);
        END IF;
        RETURN QUERY
            SELECT
                a.*
            FROM
                annotation_get a
            WHERE
                a.id = _insertedid;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;
