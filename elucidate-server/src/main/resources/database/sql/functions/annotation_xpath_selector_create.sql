-- Function: public.annotation_xpath_selector_create(integer, integer, character varying, text, jsonb)

-- DROP FUNCTION public.annotation_xpath_selector_create(integer, integer, character varying, text, jsonb);

CREATE OR REPLACE FUNCTION public.annotation_xpath_selector_create(
    _bodyid integer,
    _targetid integer,
    _selectoriri character varying,
    _value text,
    _json jsonb)
RETURNS SETOF annotation_xpath_selector_get AS
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
            value,
            json,
            createddatetime,
            deleted
        )
        VALUES (
            _bodyid,
            _targetid,
            _selectoriri,
            'http://www.w3.org/ns/oa#XPathSelector',
            _value,
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
                asl.value,
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
