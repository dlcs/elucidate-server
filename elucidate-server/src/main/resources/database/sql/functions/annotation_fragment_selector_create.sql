-- Function: public.annotation_fragment_selector_create(integer, integer, character varying, text, text, integer, integer, integer, integer, integer, integer, jsonb)

-- DROP FUNCTION public.annotation_fragment_selector_create(integer, integer, character varying, text, text, integer, integer, integer, integer, integer, integer, jsonb);

CREATE OR REPLACE FUNCTION public.annotation_fragment_selector_create(
    _bodyid integer,
    _targetid integer,
    _selectoriri character varying,
    _conformsto text,
    _value text,
    _x integer,
    _y integer,
    _w integer,
    _h integer,
    _start integer,
    _end integer,
    _json jsonb)
RETURNS SETOF annotation_fragment_selector_get AS
$BODY$
    DECLARE
        _insertedid integer;
        _createddatetime timestamp without time zone DEFAULT now();
    BEGIN
        INSERT INTO annotation_selector (
            bodyid,
            targetid,
            selectoriri,
            type,
            conformsto,
            value,
            x,
            y,
            w,
            h,
            start,
            "end",
            json,
            createddatetime,
            deleted
        )
        VALUES (
            _bodyid,
            _targetid,
            _selectoriri,
            'http://www.w3.org/ns/oa#FragmentSelector',
            _conformsto,
            _value,
            _x,
            _y,
            _w,
            _h,
            _start,
            _end,
            _json,
            _createddatetime,
            false
        ) RETURNING id INTO _insertedid;
        RETURN QUERY
            SELECT
                asl.id,
                asl.selectoriri,
                ab.bodyiri,
                ab.sourceiri AS bodysourceiri,
                at.targetiri,
                at.sourceiri AS targetsourceiri,
                a.annotationid,
                ac.collectionid,
                asl.conformsto,
                asl.value,
                asl.x,
                asl.y,
                asl.w,
                asl.h,
                asl.start,
                asl.end,
                asl.createddatetime,
                asl.modifieddatetime,
                asl.deleted,
                asl.json
            FROM
                annotation_selector asl
                    LEFT JOIN annotation_body ab ON asl.bodyid = ab.id
                    LEFT JOIN annotation_target at ON asl.targetid = at.id
                    LEFT JOIN annotation a ON at.annotationid = a.id
                    LEFT JOIN annotation_collection ac ON a.collectionid = ac.id
            WHERE
                asl.id = _insertedid;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;

ALTER FUNCTION public.annotation_fragment_selector_create(integer, integer, character varying, text, text, integer, integer, integer, integer, integer, integer, jsonb) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_fragment_selector_create(integer, integer, character varying, text, text, integer, integer, integer, integer, integer, integer, jsonb) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_fragment_selector_create(integer, integer, character varying, text, text, integer, integer, integer, integer, integer, integer, jsonb) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_fragment_selector_create(integer, integer, character varying, text, text, integer, integer, integer, integer, integer, integer, jsonb) FROM public;
