-- Function: public.annotation_search_by_body(boolean, boolean, character varying, boolean, integer, integer, integer, integer, integer, integer, character varying, character varying)

-- DROP FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean, integer, integer, integer, integer, integer, integer, character varying, character varying);

CREATE OR REPLACE FUNCTION public.annotation_search_by_body(
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
    _creatoriri character varying,
    _generatoriri character varying)
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
                a.id,
                ao.user_id as ownerid,
                agm.group_ids
            FROM
                annotation AS a
                    LEFT JOIN annotation_collection AS ac ON a.collectionid = ac.id
                    LEFT JOIN annotation_body AS ab ON ab.annotationid = a.id
                    LEFT JOIN annotation_selector AS asl ON asl.bodyid = ab.id
                    LEFT JOIN annotation_agent AS agc ON agc.bodyid = ab.id
                    LEFT JOIN annotation_agent AS agg ON agg.bodyid = ab.id
                    LEFT JOIN annotation_owner ao ON ao.annotation_id = a.id
                    LEFT JOIN annotation_group_memberships agm ON agm.annotation_id = a.id
            WHERE
                (CASE _searchids
                    WHEN true THEN
                        (
                            CASE _strict
                                WHEN true THEN
                                    ab.bodyiri = _value
                                ELSE
                                    ab.bodyiri LIKE (_value || '%')
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
                                    ab.sourceiri = _value
                                ELSE
                                    ab.sourceiri LIKE (_value || '%')
                            END
                        )
                    ELSE
                        false
                END)
                AND CASE ((_start IS NOT NULL AND _end IS NOT NULL) OR (_x IS NOT NULL AND _y IS NOT NULL AND _w IS NOT NULL AND _h IS NOT NULL))
                    WHEN true THEN
                        (
                            ab.id IN (
                                SELECT
                                    bodyid
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
                                    bodyid
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
                        (agc.agentiri = _creatoriri AND agc.relationshiptype = 'CREATOR')
                    ELSE
                        true
                END
                AND CASE (_generatoriri IS NOT NULL)
                    WHEN true THEN
                        (agg.agentiri = _generatoriri AND agg.relationshiptype = 'GENERATOR')
                    ELSE
                        true
                END
                AND a.deleted = false
                AND ab.deleted = false;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;

ALTER FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean, integer, integer, integer, integer, integer, integer, character varying, character varying) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean, integer, integer, integer, integer, integer, integer, character varying, character varying) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean, integer, integer, integer, integer, integer, integer, character varying, character varying) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_search_by_body(boolean, boolean, character varying, boolean, integer, integer, integer, integer, integer, integer, character varying, character varying) FROM public;
