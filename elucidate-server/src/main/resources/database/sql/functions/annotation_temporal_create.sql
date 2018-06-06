-- Function: public.annotation_temporal_create(integer, integer, integer, character varying, timestamp without time zone, jsonb)

-- DROP FUNCTION public.annotation_temporal_create(integer, integer, integer, character varying, timestamp without time zone, jsonb);

CREATE OR REPLACE FUNCTION public.annotation_temporal_create(
    _annotationid integer,
    _bodyid integer,
    _targetid integer,
    _type character varying,
    _value timestamp without time zone,
    _json jsonb)
RETURNS SETOF annotation_temporal_get AS
$BODY$
    DECLARE
        _insertedid integer;
        _createddatetime timestamp without time zone DEFAULT now();
    BEGIN
        INSERT INTO annotation_temporal (
            annotationid,
            bodyid,
            targetid,
            type,
            value,
            json,
            createddatetime,
            deleted
        )
        VALUES (
            _annotationid,
            _bodyid,
            _targetid,
            _type,
            _value,
            _json,
            _createddatetime,
            false
        ) RETURNING id INTO _insertedid;
        RETURN QUERY
            SELECT
                atp.id,
                a.annotationid,
                ac.collectionid,
                ab.bodyiri,
                ab.sourceiri AS bodysourceiri,
                at.targetiri,
                at.sourceiri AS targetsourceiri,
                atp.type,
                atp.value,
                atp.json,
                atp.createddatetime,
                atp.modifieddatetime,
                atp.deleted
            FROM
                annotation_temporal atp
                    LEFT JOIN annotation_body ab ON ab.id = atp.bodyid
                    LEFT JOIN annotation_target at ON at.id = atp.targetid
                    LEFT JOIN annotation a ON a.id = atp.annotationid
                    LEFT JOIN annotation_collection ac ON ac.id = a.collectionid
                WHERE
                    atp.id = _insertedid;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;

ALTER FUNCTION public.annotation_temporal_create(integer, integer, integer, character varying, timestamp without time zone, jsonb) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_temporal_create(integer, integer, integer, character varying, timestamp without time zone, jsonb) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_temporal_create(integer, integer, integer, character varying, timestamp without time zone, jsonb) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_temporal_create(integer, integer, integer, character varying, timestamp without time zone, jsonb) FROM public;
