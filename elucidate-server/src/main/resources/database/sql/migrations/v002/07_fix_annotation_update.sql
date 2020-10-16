-- Function: public.annotation_update(character varying, character varying, jsonb)

-- DROP FUNCTION public.annotation_update(character varying, character varying, jsonb);

CREATE OR REPLACE FUNCTION public.annotation_update(
    _collectionid character varying,
    _annotationid character varying,
    _json jsonb)
RETURNS SETOF annotation_get AS
$BODY$
    DECLARE
        _modifieddatetime timestamp without time zone DEFAULT now();
    BEGIN
        UPDATE
            annotation
        SET
            json = _json,
            modifieddatetime = _modifieddatetime,
            cachekey = md5(_modifieddatetime::text)
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
                a.id,
                ao.user_id as ownerid,
                agm.group_ids
            FROM
                annotation a
                    LEFT JOIN annotation_collection ac ON a.collectionid = ac.id
                    LEFT JOIN annotation_owner ao ON ao.annotation_id = a.id
                    LEFT JOIN annotation_group_memberships agm ON agm.annotation_id = a.id
            WHERE
                ac.collectionid = _collectionid
                AND ac.deleted = false
                AND a.annotationid = _annotationid
                AND a.deleted = false
            ORDER BY
                modifieddatetime DESC LIMIT 1;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;

ALTER FUNCTION public.annotation_update(character varying, character varying, jsonb) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_update(character varying, character varying, jsonb) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_update(character varying, character varying, jsonb) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_update(character varying, character varying, jsonb) FROM public;
