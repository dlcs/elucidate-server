-- Function: public.annotation_target_delete(integer)

-- DROP FUNCTION public.annotation_target_delete(integer);

CREATE OR REPLACE FUNCTION public.annotation_target_delete(_annotationid integer)
RETURNS SETOF annotation_target_get AS
$BODY$
    BEGIN
        RETURN QUERY
            WITH updated_rows AS (
                UPDATE
                    annotation_target
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
                ur.targetiri,
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

ALTER FUNCTION public.annotation_target_delete(integer) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_target_delete(integer) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_target_delete(integer) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_target_delete(integer) FROM public;
