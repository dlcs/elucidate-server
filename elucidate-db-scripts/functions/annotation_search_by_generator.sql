-- Function: public.annotation_search_by_generator(boolean, boolean, boolean, character varying, character varying)

-- DROP FUNCTION public.annotation_search_by_generator(boolean, boolean, boolean, character varying, character varying);

CREATE OR REPLACE FUNCTION public.annotation_search_by_generator(
    _searchannotations boolean,
    _searchbodies boolean,
    _searchtargets boolean,
    _type character varying,
    _value character varying)
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
            WHERE
                a.id IN (
                    SELECT
                        a.id
                    FROM
                        annotation AS a
                            LEFT JOIN annotation_collection AS ac ON a.collectionid = ac.id
                            LEFT JOIN annotation_agent AS aa ON aa.annotationid = a.id
                            LEFT JOIN annotation_agent_email AS aae ON aae.annotationagentid = aa.id
                            LEFT JOIN annotation_agent_email_sha1 AS aaes ON aaes.annotationagentid = aa.id
                            LEFT JOIN annotation_agent_name AS aan ON aan.annotationagentid = aa.id
                            LEFT JOIN annotation_agent_type AS aat ON aat.annotationagentid = aa.id
                    WHERE
                        a.deleted = false
                        AND aa.relationshiptype = 'GENERATOR'
                        AND CASE _searchannotations
                            WHEN true THEN
                                CASE
                                    WHEN _type = 'id' THEN
                                        aa.agentiri = _value
                                    WHEN _type = 'name' THEN
                                        aan.name = _value
                                    WHEN _type = 'nickname' THEN
                                        aa.nickname = _value
                                    WHEN _type = 'email' THEN
                                        aae.email = _value
                                    WHEN _type = 'emailsha1' THEN
                                        aaes.emailsha1 = _value
                                END
                            ELSE
                                (false)
                            END
                    UNION
                    SELECT
                        a.id
                    FROM
                        annotation AS a
                            LEFT JOIN annotation_body AS ab ON ab.annotationid = a.id
                            LEFT JOIN annotation_agent AS aa ON aa.bodyid = ab.id
                            LEFT JOIN annotation_agent_email AS aae ON aae.annotationagentid = aa.id
                            LEFT JOIN annotation_agent_email_sha1 AS aaes ON aaes.annotationagentid = aa.id
                            LEFT JOIN annotation_agent_name AS aan ON aan.annotationagentid = aa.id
                            LEFT JOIN annotation_agent_type AS aat ON aat.annotationagentid = aa.id
                    WHERE
                        a.deleted = false
                        AND aa.relationshiptype = 'GENERATOR'
                        AND CASE _searchbodies
                            WHEN true THEN
                                CASE
                                    WHEN _type = 'id' THEN
                                        aa.agentiri = _value
                                    WHEN _type = 'name' THEN
                                        aan.name = _value
                                    WHEN _type = 'nickname' THEN
                                        aa.nickname = _value
                                    WHEN _type = 'email' THEN
                                        aae.email = _value
                                    WHEN _type = 'emailsha1' THEN
                                        aaes.emailsha1 = _value
                                END
                            ELSE
                                (false)
                            END
                    UNION
                    SELECT
                        a.id
                    FROM
                        annotation AS a
                            LEFT JOIN annotation_target AS at ON at.annotationid = a.id
                            LEFT JOIN annotation_agent AS aa ON aa.targetid = at.id
                            LEFT JOIN annotation_agent_email AS aae ON aae.annotationagentid = aa.id
                            LEFT JOIN annotation_agent_email_sha1 AS aaes ON aaes.annotationagentid = aa.id
                            LEFT JOIN annotation_agent_name AS aan ON aan.annotationagentid = aa.id
                            LEFT JOIN annotation_agent_type AS aat ON aat.annotationagentid = aa.id
                    WHERE
                        a.deleted = false
                        AND aa.relationshiptype = 'GENERATOR'
                        AND CASE _searchtargets
                            WHEN true THEN
                                CASE
                                    WHEN _type = 'id' THEN
                                        aa.agentiri = _value
                                    WHEN _type = 'name' THEN
                                        aan.name = _value
                                    WHEN _type = 'nickname' THEN
                                        aa.nickname = _value
                                    WHEN _type = 'email' THEN
                                        aae.email = _value
                                    WHEN _type = 'emailsha1' THEN
                                        aaes.emailsha1 = _value
                                END
                            ELSE
                                (false)
                            END
                );
END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;

ALTER FUNCTION public.annotation_search_by_generator(boolean, boolean, boolean, character varying, character varying) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_search_by_generator(boolean, boolean, boolean, character varying, character varying) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_search_by_generator(boolean, boolean, boolean, character varying, character varying) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_search_by_generator(boolean, boolean, boolean, character varying, character varying) FROM public;
