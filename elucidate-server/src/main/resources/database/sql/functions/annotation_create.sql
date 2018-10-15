-- Function: public.annotation_create(character varying, character varying, jsonb)

-- DROP FUNCTION public.annotation_create(character varying, character varying, jsonb);

CREATE OR REPLACE FUNCTION public.annotation_create(
    _collectionid character varying,
    _annotationid character varying,
    _json jsonb)
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
        RETURN QUERY
            SELECT
                a.annotationid,
                a.cachekey,
                ac.collectionid,
                a.createddatetime,
                a.deleted,
                a.json,
                a.modifieddatetime,
                a.id
            FROM
                annotation a
                    LEFT JOIN annotation_collection ac ON a.collectionid = ac.id
            WHERE
                a.id = _insertedid;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;

ALTER FUNCTION public.annotation_create(character varying, character varying, jsonb) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_create(character varying, character varying, jsonb) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_create(character varying, character varying, jsonb) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_create(character varying, character varying, jsonb) FROM public;
