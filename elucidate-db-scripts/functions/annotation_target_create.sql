-- Function: public.annotation_target_create(integer, character varying, character varying, jsonb)

-- DROP FUNCTION public.annotation_target_create(integer, character varying, character varying, jsonb);

CREATE OR REPLACE FUNCTION public.annotation_target_create(
    _annotationid integer,
    _targetiri character varying,
    _sourceiri character varying,
    _json jsonb)
RETURNS SETOF annotation_target_get AS
$BODY$
    DECLARE
        _insertedid integer;
        _createddatetime timestamp without time zone DEFAULT now();
    BEGIN
        INSERT INTO annotation_target (
            annotationid,
            targetiri,
            sourceiri,
            json,
            createddatetime,
            deleted
        )
        VALUES (
            _annotationid,
            _targetiri,
            _sourceiri,
            _json,
            _createddatetime,
            false
        ) RETURNING id INTO _insertedid;
        RETURN QUERY
            SELECT
                a.annotationid,
                ac.collectionid,
                at.targetiri,
                at.createddatetime,
                at.deleted,
                at.json,
                at.modifieddatetime,
                at.sourceiri,
                at.id
            FROM
                annotation_target at
                    LEFT JOIN annotation a ON at.annotationid = a.id
                    LEFT JOIN annotation_collection ac ON a.collectionid = ac.id
            WHERE at.id = _insertedid;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;

ALTER FUNCTION public.annotation_target_create(integer, character varying, character varying, jsonb) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_target_create(integer, character varying, character varying, jsonb) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_target_create(integer, character varying, character varying, jsonb) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_target_create(integer, character varying, character varying, jsonb) FROM public;
