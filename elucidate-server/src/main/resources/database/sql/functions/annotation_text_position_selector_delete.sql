-- Function: public.annotation_text_position_selector_delete(integer, integer)

-- DROP FUNCTION public.annotation_text_position_selector_delete(integer, integer);

CREATE OR REPLACE FUNCTION public.annotation_text_position_selector_delete(
    _bodyid integer,
    _targetid integer)
RETURNS SETOF annotation_text_position_selector_get AS
$BODY$
    BEGIN
        IF (_bodyid IS NULL AND _targetid IS NULL) THEN
            RAISE EXCEPTION 'Both _bodyid AND _targetid cannot be NULL';
        END IF;
        RETURN QUERY
            WITH updated_rows AS (
                UPDATE
                    annotation_selector
                SET
                    deleted = true,
                    modifieddatetime = now()
                WHERE
                    CASE _bodyid IS NOT NULL WHEN true THEN (bodyid = _bodyid) ELSE (true) END
                    AND CASE _targetid IS NOT NULL WHEN true THEN (targetid = _targetid) ELSE (true) END
                    AND type = 'http://www.w3.org/ns/oa#TextPositionSelector'
                    AND deleted = false
                RETURNING
                    *
            )
            SELECT
                ur.id,
                ur.selectoriri,
                ab.bodyiri,
                ab.sourceiri AS bodysourceiri,
                at.targetiri,
                at.sourceiri AS targetsourceiri,
                a.annotationid,
                ac.collectionid,
                ur.start,
                ur.end,
                ur.createddatetime,
                ur.modifieddatetime,
                ur.deleted,
                ur.json
            FROM
                updated_rows ur
                    LEFT JOIN annotation_body ab ON ur.bodyid = ab.id
                    LEFT JOIN annotation_target at ON ur.targetid = at.id
                    LEFT JOIN annotation a ON at.annotationid = a.id
                    LEFT JOIN annotation_collection ac ON a.collectionid = ac.id;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;
