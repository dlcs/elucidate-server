-- Function: public.annotation_search_by_target(boolean, boolean, character varying, boolean, integer, integer, integer, integer, integer, integer, character varying)

-- DROP FUNCTION public.annotation_search_by_target(boolean, boolean, character varying, boolean, integer, integer, integer, integer, integer, integer, character varying);

CREATE OR REPLACE FUNCTION public.annotation_search_by_target(
    _searchids boolean,
    _searchsources boolean,
    _value character varying,
    _strict boolean,
    _x integer,
    _y integer,
    _w integer,
    _h integer,
    _start integer,
    _end integer,
    _creatoriri character varying)
RETURNS SETOF annotation_get AS
$BODY$
    BEGIN
        RETURN QUERY
            SELECT DISTINCT
                a.annotationid,
                a.cachekey,
                ac.collectionid,
                a.createddatetime,
                a.deleted,
                a.json,
                a.modifieddatetime,
                a.id
            FROM
                annotation AS a
                    LEFT JOIN annotation_collection AS ac ON a.collectionid = ac.id
                    LEFT JOIN annotation_target AS at ON at.annotationid = a.id
                    LEFT JOIN annotation_selector AS asl ON asl.targetid = at.id
                    LEFT JOIN annotation_agent AS ag ON ag.targetid = at.id
            WHERE
                CASE _searchids
                    WHEN true THEN
                        (
                            CASE _strict
                                WHEN true THEN
                                    at.targetiri = _value
                                ELSE
                                    at.targetiri LIKE (_value || '%') 
                            END
                        )
                    ELSE
                        false
                END
                OR CASE _searchsources
                    WHEN true THEN
                        (
                            CASE _strict
                                WHEN true THEN
                                    at.sourceiri = _value
                                ELSE
                                    at.sourceiri LIKE (_value || '%')
                            END
                        )
                    ELSE
                        false
                END
                AND CASE ((_start IS NOT NULL AND _end IS NOT NULL) OR (_x IS NOT NULL AND _y IS NOT NULL AND _w IS NOT NULL AND _h IS NOT NULL))
                    WHEN true THEN
                        (
                            at.id IN (
                                SELECT
                                    targetid
                                FROM
                                    annotation_selector
                                WHERE
                                    CASE (_x IS NOT NULL AND _y IS NOT NULL AND _w IS NOT NULL AND _h IS NOT NULL)
                                        WHEN true THEN
                                            (
                                                (_x + _w) > x
                                                AND _x < (x + w)
                                                AND (_y + _h) > y
                                                AND _y < (y + h)
                                                AND type = 'http://www.w3.org/ns/oa#FragmentSelector'
                                                AND deleted = false
                                            )
                                        ELSE
                                            (true)
                                    END
                                INTERSECT
                                SELECT
                                    targetid
                                FROM
                                    annotation_selector
                                WHERE
                                    CASE (_start IS NOT NULL AND _end IS NOT NULL)
                                        WHEN true THEN
                                            (
                                                _start < "end"
                                                AND _end > start
                                                AND type = 'http://www.w3.org/ns/oa#FragmentSelector'
                                                AND deleted = false
                                            )
                                        ELSE
                                            (true)
                                    END
                            )
                        )
                        
                    ELSE
                        true
                END
                AND CASE (_creatoriri IS NOT NULL)
                    WHEN true THEN
                        ag.agentiri = _creatoriri
                        AND ag.relationshiptype = 'CREATOR'
                    ELSE
                        true
                END
                AND a.deleted = false;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;

ALTER FUNCTION public.annotation_search_by_target(boolean, boolean, character varying, boolean, integer, integer, integer, integer, integer, integer, character varying) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_search_by_target(boolean, boolean, character varying, boolean, integer, integer, integer, integer, integer, integer, character varying) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_search_by_target(boolean, boolean, character varying, boolean, integer, integer, integer, integer, integer, integer, character varying) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_search_by_target(boolean, boolean, character varying, boolean, integer, integer, integer, integer, integer, integer, character varying) FROM public;
