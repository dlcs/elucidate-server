-- Function: public.annotation_search_by_creator(boolean, boolean, boolean, character varying, character varying, boolean)

-- DROP FUNCTION public.annotation_search_by_creator(boolean, boolean, boolean, character varying, character varying, boolean);

CREATE OR REPLACE FUNCTION public.annotation_search_by_creator(
    _searchannotations boolean,
    _searchbodies boolean,
    _searchtargets boolean,
    _type character varying,
    _value character varying,
    _strict boolean)
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
                    LEFT JOIN annotation_owner ao ON ao.annotation_id = a.id
                    LEFT JOIN annotation_group_memberships agm ON agm.annotation_id = a.id
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
                        AND aa.relationshiptype = 'CREATOR'
                        AND CASE _searchannotations
                            WHEN true THEN
                                CASE
                                    WHEN _type = 'id' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aa.agentiri = _value
                                                    AND aa.deleted = false
                                                ELSE
                                                    aa.agentiri LIKE (_value || '%')
                                                    AND aa.deleted = false
                                            END
                                        )
                                    WHEN _type = 'name' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aan.name = _value
                                                    AND aan.deleted = false
                                                ELSE
                                                    aan.name LIKE (_value || '%')
                                                    AND aan.deleted = false
                                            END
                                        )
                                    WHEN _type = 'nickname' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aa.nickname = _value
                                                    AND aa.deleted = false
                                                ELSE
                                                    aa.nickname LIKE (_value || '%')
                                                    AND aa.deleted = false
                                            END
                                        )
                                    WHEN _type = 'email' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aae.email = _value
                                                    AND aae.deleted = false
                                                ELSE
                                                    aae.email LIKE (_value || '%')
                                                    AND aae.deleted = false
                                            END
                                        )
                                    WHEN _type = 'emailsha1' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aaes.emailsha1 = _value
                                                    AND aaes.deleted = false
                                                ELSE
                                                    aaes.emailsha1 LIKE (_value || '%')
                                                    AND aaes.deleted = false
                                            END
                                        )
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
                        AND aa.relationshiptype = 'CREATOR'
                        AND CASE _searchbodies
                            WHEN true THEN
                                CASE
                                    WHEN _type = 'id' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aa.agentiri = _value
                                                    AND aa.deleted = false
                                                ELSE
                                                    aa.agentiri LIKE (_value || '%')
                                                    AND aa.deleted = false
                                            END
                                        )
                                    WHEN _type = 'name' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aan.name = _value
                                                    AND aan.deleted = false
                                                ELSE
                                                    aan.name LIKE (_value || '%')
                                                    AND aan.deleted = false
                                            END
                                        )
                                    WHEN _type = 'nickname' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aa.nickname = _value
                                                    AND aa.deleted = false
                                                ELSE
                                                    aa.nickname LIKE (_value || '%')
                                                    AND aa.deleted = false
                                            END
                                        )
                                    WHEN _type = 'email' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aae.email = _value
                                                    AND aae.deleted = false
                                                ELSE
                                                    aae.email LIKE (_value || '%')
                                                    AND aae.deleted = false
                                            END
                                        )
                                    WHEN _type = 'emailsha1' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aaes.emailsha1 = _value
                                                    AND aaes.deleted = false
                                                ELSE
                                                    aaes.emailsha1 LIKE (_value || '%')
                                                    AND aaes.deleted = false
                                            END
                                        )
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
                        AND aa.relationshiptype = 'CREATOR'
                        AND CASE _searchtargets
                            WHEN true THEN
                                CASE
                                    WHEN _type = 'id' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aa.agentiri = _value
                                                    AND aa.deleted = false
                                                ELSE
                                                    aa.agentiri LIKE (_value || '%')
                                                    AND aa.deleted = false
                                            END
                                        )
                                    WHEN _type = 'name' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aan.name = _value
                                                    AND aan.deleted = false
                                                ELSE
                                                    aan.name LIKE (_value || '%')
                                                    AND aan.deleted = false
                                            END
                                        )
                                    WHEN _type = 'nickname' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aa.nickname = _value
                                                    AND aa.deleted = false
                                                ELSE
                                                    aa.nickname LIKE (_value || '%')
                                                    AND aa.deleted = false
                                            END
                                        )
                                    WHEN _type = 'email' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aae.email = _value
                                                    AND aae.deleted = false
                                                ELSE
                                                    aae.email LIKE (_value || '%')
                                                    AND aae.deleted = false
                                            END
                                        )
                                    WHEN _type = 'emailsha1' THEN
                                        (
                                            CASE _strict
                                                WHEN true THEN
                                                    aaes.emailsha1 = _value
                                                    AND aaes.deleted = false
                                                ELSE
                                                    aaes.emailsha1 LIKE (_value || '%')
                                                    AND aaes.deleted = false
                                            END
                                        )
                                END
                            ELSE
                                (false)
                            END
                );
END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;
