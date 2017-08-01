-- FUNCTION: public.annotation_history_create(integer, jsonb)

-- DROP FUNCTION public.annotation_history_create(integer, jsonb);

CREATE OR REPLACE FUNCTION public.annotation_history_create(
    _annotationid integer,
    _json jsonb)
RETURNS SETOF annotation_history_get  AS
$BODY$
    DECLARE
        _insertedid integer;
        _createddatetime timestamp without time zone DEFAULT now();
    BEGIN
        INSERT INTO annotation_history (
            annotationid,
            json,
            createddatetime,
            version,
            deleted
        )
        VALUES (
            _annotationid,
            _json,
            _createddatetime,
            (
                SELECT
                    COALESCE(MAX(version), 0) + 1
                FROM
                    annotation_history
                WHERE
                    annotationid = _annotationid
            ),
            false
        ) RETURNING id INTO _insertedid;
        RETURN QUERY
            SELECT
                ah.id,
                a.annotationid,
                ac.collectionid,
                ah.json,
                ah.version,
                ah.createddatetime,
                ah.modifieddatetime,
                ah.deleted
            FROM
                annotation_history ah
                    LEFT JOIN annotation a ON ah.annotationid = a.id
                    LEFT JOIN annotation_collection ac ON a.collectionid = ac.id
            WHERE
                ah.id = _insertedid;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;

ALTER FUNCTION public.annotation_history_create(integer, jsonb) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_history_create(integer, jsonb) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_history_create(integer, jsonb) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_history_create(integer, jsonb) FROM public;
