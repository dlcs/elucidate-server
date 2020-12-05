-- Function: public.annotation_body_create(integer, character varying, character varying, jsonb)

-- DROP FUNCTION public.annotation_body_create(integer, character varying, character varying, jsonb);

CREATE OR REPLACE FUNCTION public.annotation_body_create(
    _annotationid integer,
    _bodyiri character varying,
    _sourceiri character varying,
    _json jsonb)
RETURNS SETOF annotation_body_get AS
$BODY$
    DECLARE
        _insertedid integer;
        _createddatetime timestamp without time zone DEFAULT now();
    BEGIN
        INSERT INTO annotation_body (
            annotationid,
            bodyiri,
            sourceiri,
            json,
            createddatetime,
            deleted
        )
        VALUES (
            _annotationid,
            _bodyiri,
            _sourceiri,
            _json,
            _createddatetime,
            false
        ) RETURNING id INTO _insertedid;
        RETURN QUERY
            SELECT
                a.annotationid,
                ac.collectionid,
                ab.bodyiri,
                ab.createddatetime,
                ab.deleted,
                ab.json,
                ab.modifieddatetime,
                ab.sourceiri,
                ab.id
            FROM
                annotation_body ab
                    LEFT JOIN annotation a ON ab.annotationid = a.id
                    LEFT JOIN annotation_collection ac ON a.collectionid = ac.id
            WHERE ab.id = _insertedid;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;
