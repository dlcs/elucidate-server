-- Function: public.annotation_delete(character varying, character varying)

-- DROP FUNCTION public.annotation_delete(character varying, character varying);

CREATE OR REPLACE FUNCTION public.annotation_delete(
    _collectionid character varying,
    _annotationid character varying)
RETURNS SETOF annotation_get AS
$BODY$
    BEGIN
        UPDATE
            annotation
        SET
            deleted = true,
            modifieddatetime = now()
        WHERE
            collectionid = (
                SELECT
                    id
                FROM
                    annotation_collection
                WHERE
                    collectionid = _collectionid
                    AND deleted = false
                ORDER BY
                    modifieddatetime DESC LIMIT 1
            )
            AND annotationid = _annotationid
            AND deleted = false;
        RETURN QUERY
            SELECT
                a.annotationid,
                a.cachekey,
                ac.collectionid,
                a.createddatetime,
                a.deleted,
                a.json,
                a.modifieddatetime,
                a.id
            FROM
                annotation a
                    LEFT JOIN annotation_collection ac ON a.collectionid = ac.id
            WHERE
                ac.collectionid = _collectionid
                AND ac.deleted = false
                AND a.annotationid = _annotationid
                AND a.deleted = true
            ORDER BY
                modifieddatetime DESC LIMIT 1;
    END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.annotation_delete(character varying, character varying)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_delete(character varying, character varying) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_delete(character varying, character varying) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_delete(character varying, character varying) FROM public;
