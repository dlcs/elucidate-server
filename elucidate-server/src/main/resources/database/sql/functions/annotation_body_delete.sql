-- Function: public.annotation_body_delete(integer)

-- DROP FUNCTION public.annotation_body_delete(integer);

CREATE OR REPLACE FUNCTION public.annotation_body_delete(_annotationid integer)
RETURNS SETOF annotation_body_get AS
$BODY$
    BEGIN
RETURN QUERY
    WITH updated_rows AS (
        UPDATE
            annotation_body
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
    a.annotationid,
    ac.collectionid,
    ur.bodyiri,
    ur.createddatetime,
    ur.deleted,
    ur.json,
    ur.modifieddatetime,
    ur.sourceiri,
    ur.id
FROM
    updated_rows ur
        LEFT JOIN annotation a ON  ur.annotationid = a.id
        LEFT JOIN annotation_collection ac ON a.collectionid = ac.id;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;
