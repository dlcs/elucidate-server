-- Function: public.annotation_generator_delete(integer, integer, integer)

-- DROP FUNCTION public.annotation_generator_delete(integer, integer, integer);

CREATE OR REPLACE FUNCTION public.annotation_generator_delete(
    _annotationid integer,
    _bodyid integer,
    _targetid integer)
RETURNS SETOF annotation_generator_get AS
$BODY$
    BEGIN
        CREATE TEMPORARY TABLE deleted_generators ON COMMIT DROP AS
            WITH updated_rows AS (
                UPDATE
                    annotation_agent
                SET
                    deleted = true,
                    modifieddatetime = now()
                WHERE
                    CASE _annotationid IS NOT NULL WHEN true THEN (annotationid = _annotationid) ELSE (true) END
                    AND CASE _bodyid IS NOT NULL WHEN true THEN (bodyid = _bodyid) ELSE (true) END
                    AND CASE _targetid IS NOT NULL WHEN true THEN (targetid = _targetid) ELSE (true) END
                    AND relationshiptype = 'GENERATOR'
                    AND deleted = false
                RETURNING
                    *
            )
            SELECT
                id
            FROM
                updated_rows;
        UPDATE annotation_agent_email SET deleted = true, modifieddatetime = now() WHERE annotationagentid IN (SELECT id FROM deleted_generators);
        UPDATE annotation_agent_email_sha1 SET deleted = true, modifieddatetime = now() WHERE annotationagentid IN (SELECT id FROM deleted_generators);
        UPDATE annotation_agent_homepage SET deleted = true, modifieddatetime = now() WHERE annotationagentid IN (SELECT id FROM deleted_generators);
        UPDATE annotation_agent_name SET deleted = true, modifieddatetime = now() WHERE annotationagentid IN (SELECT id FROM deleted_generators);
        UPDATE annotation_agent_type SET deleted = true, modifieddatetime = now() WHERE annotationagentid IN (SELECT id FROM deleted_generators);
        RETURN QUERY
            SELECT
                aa.id,
                a.annotationid,
                ac.collectionid,
                ab.bodyiri,
                ab.sourceiri AS bodysourceiri,
                at.targetiri,
                at.sourceiri AS targetsourceiri,
                aa.agentiri,
                aa.json,
                aat.type,
                aat.json AS typejson,
                aan.name,
                aan.json AS namejson,
                aa.nickname,
                aae.email,
                aae.json AS emailjson,
                aaes.emailsha1,
                aaes.json AS emailsha1json,
                aah.homepage,
                aah.json AS homepagejson,
                aa.createddatetime,
                aa.modifieddatetime,
                aa.deleted
            FROM
                annotation_agent aa
                    LEFT JOIN annotation_agent_email aae ON aae.annotationagentid = aa.id
                    LEFT JOIN annotation_agent_email_sha1 aaes ON aaes.annotationagentid = aa.id
                    LEFT JOIN annotation_agent_homepage aah ON aah.annotationagentid = aa.id
                    LEFT JOIN annotation_agent_name aan ON aan.annotationagentid = aa.id
                    LEFT JOIN annotation_agent_type aat ON aat.annotationagentid = aa.id
                    LEFT JOIN annotation_body ab ON ab.id = aa.bodyid
                    LEFT JOIN annotation_target at ON at.id = aa.targetid
                    LEFT JOIN annotation a ON a.id = aa.annotationid
                    LEFT JOIN annotation_collection ac ON ac.id = a.collectionid
            WHERE
                aa.id IN (SELECT id FROM deleted_generators);
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;
