-- Function: public.annotation_search_by_body(boolean, boolean, character varying, boolean)

-- DROP FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean);

CREATE OR REPLACE FUNCTION public.annotation_search_by_body(
    _searchids boolean,
    _searchsources boolean,
    _value character varying,
    _strict boolean)
RETURNS SETOF annotation_get AS
$BODY$
    BEGIN
        RETURN QUERY
            SELECT
                a.annotationid,
                a.cachekey,
                a.collectionid,
                a.createddatetime,
                a.deleted,
                a.json,
                a.modifieddatetime
            FROM
                (
                    SELECT
                        DISTINCT(a.id),
                        a.annotationid,
                        a.cachekey,
                        ac.collectionid,
                        a.createddatetime,
                        a.deleted,
                        a.json,
                        a.modifieddatetime
                    FROM
                        (
                            SELECT
                                a.id,
                                a.annotationid,
                                a.cachekey,
                                a.collectionid,
                                a.createddatetime,
                                a.deleted,
                                a.json,
                                a.modifieddatetime,
                                jsonb_array_elements(json -> 'http://www.w3.org/ns/oa#hasBody') ->> '@id' AS bodyid,
                                jsonb_array_elements(jsonb_array_elements(json -> 'http://www.w3.org/ns/oa#hasBody') -> 'http://www.w3.org/ns/oa#hasSource') ->> '@id' AS bodysource
                            FROM
                                annotation a
                        ) AS a
                            LEFT JOIN annotation_collection ac ON a.collectionid = ac.id
                    WHERE
                        CASE _searchids WHEN true THEN (CASE _strict WHEN true THEN a.bodyid = _value ELSE a.bodyid LIKE (_value || '%') END) ELSE (false) END
                        OR CASE _searchsources WHEN true THEN (CASE _strict WHEN true THEN a.bodysource = _value ELSE a.bodysource LIKE (_value || '%') END) ELSE (false) END
                        AND a.deleted = false
                ) AS a;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;

ALTER FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean) FROM public;
