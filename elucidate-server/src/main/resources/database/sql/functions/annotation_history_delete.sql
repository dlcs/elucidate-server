-- Function: public.annotation_history_delete(integer)

-- DROP FUNCTION public.annotation_history_delete(integer);

CREATE OR REPLACE FUNCTION public.annotation_history_delete(
    _annotationid integer)
RETURNS SETOF annotation_history_get AS
$BODY$
    BEGIN
    CREATE TEMPORARY TABLE deleted_histories ON COMMIT DROP AS
        WITH updated_rows AS (
            UPDATE
                annotation_history
            SET
                deleted = true,
                modifieddatetime = now()
            WHERE
                annotationid = _annotationid
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
            ah.id IN (SELECT id FROM deleted_histories);
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;
