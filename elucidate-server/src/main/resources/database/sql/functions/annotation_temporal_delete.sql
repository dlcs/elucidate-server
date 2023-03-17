-- Function: public.annotation_temporal_delete(integer, integer, integer)

-- DROP FUNCTION public.annotation_temporal_delete(integer, integer, integer);

CREATE OR REPLACE FUNCTION public.annotation_temporal_delete(
    _annotationid integer,
    _bodyid integer,
    _targetid integer)
RETURNS SETOF annotation_temporal_get AS
$BODY$
    BEGIN
        CREATE TEMPORARY TABLE deleted_temporals ON COMMIT DROP AS
            WITH updated_rows AS (
                UPDATE
                    annotation_temporal
                SET
                    deleted = true,
                    modifieddatetime = now()
                WHERE
                    CASE _annotationid IS NOT NULL WHEN true THEN (annotationid = _annotationid) ELSE (true) END
                    AND CASE _bodyid IS NOT NULL WHEN true THEN (bodyid = _bodyid) ELSE (true) END
                    AND CASE _targetid IS NOT NULL WHEN true THEN (targetid = _targetid) ELSE (true) END
                    AND deleted = false
                RETURNING
                    *
            )
            SELECT
                id
            FROM
                updated_rows;
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
                atp.id IN (SELECT id FROM deleted_temporals);
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;
