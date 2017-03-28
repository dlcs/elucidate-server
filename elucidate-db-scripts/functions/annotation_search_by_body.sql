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
                ac.collectionid,
                a.createddatetime,
                a.deleted,
                a.json,
                a.modifieddatetime
            FROM
                annotation AS a
                    LEFT JOIN annotation_collection AS ac ON a.collectionid = ac.id
                    LEFT JOIN LATERAL (SELECT jsonb_array_elements(a.json -> 'http://www.w3.org/ns/oa#hasBody') AS body) AS sq1 ON true
                    LEFT JOIN LATERAL (SELECT jsonb_array_elements(sq1.body -> 'http://www.w3.org/ns/oa#hasSource') AS bodysource) AS sq2 ON true
            WHERE
                CASE _searchids WHEN true THEN (CASE _strict WHEN true THEN (sq1.body ->> '@id') = _value ELSE (sq1.body ->> '@id') LIKE (_value || '%') END) ELSE (false) END
                OR CASE _searchsources WHEN true THEN (CASE _strict WHEN true THEN (bodysource ->> '@id') = _value ELSE (bodysource ->> '@id') LIKE (_value || '%') END) ELSE (false) END
                AND a.deleted = false
            ORDER BY
                COALESCE(a.modifieddatetime, a.createddatetime);
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;

ALTER FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean) FROM public;
