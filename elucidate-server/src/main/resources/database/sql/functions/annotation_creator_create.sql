-- Function: public.annotation_creator_create(integer, integer, integer, character varying, jsonb, character varying[], jsonb[], text[], jsonb[], character varying, text[], jsonb[], text[], jsonb[], text[], jsonb[])

-- DROP FUNCTION public.annotation_creator_create(integer, integer, integer, character varying, jsonb, character varying[], jsonb[], text[], jsonb[], character varying, text[], jsonb[], text[], jsonb[], text[], jsonb[]);

CREATE OR REPLACE FUNCTION public.annotation_creator_create(
    _annotationid integer,
    _bodyid integer,
    _targetid integer,
    _creatoriri character varying,
    _json jsonb,
    _types character varying[],
    _typesjson jsonb[],
    _names text[],
    _namesjson jsonb[],
    _nickname character varying,
    _emails text[],
    _emailsjson jsonb[],
    _emailsha1s text[],
    _emailsha1sjson jsonb[],
    _homepages text[],
    _homepagesjson jsonb[])
RETURNS SETOF annotation_creator_get AS
$BODY$
    DECLARE
        _insertedid integer;
        _createddatetime timestamp without time zone DEFAULT now();
        _loopvar text;
        _loopindex integer;
    BEGIN
        INSERT INTO annotation_agent (
            annotationid,
            bodyid,
            targetid,
            agentiri,
            nickname,
            relationshiptype,
            json,
            createddatetime,
            deleted
        )
        VALUES (
            _annotationid,
            _bodyid,
            _targetid,
            _creatoriri,
            _nickname,
            'CREATOR',
            _json,
            _createddatetime,
            false
        ) RETURNING id INTO _insertedid;
        _loopindex := 1;
        FOREACH _loopvar IN ARRAY _types LOOP
            INSERT INTO annotation_agent_type (
                annotationagentid,
                type,
                json,
                createddatetime,
                deleted
            )
            VALUES (
                _insertedid,
                _loopvar,
                _typesjson[_loopindex],
                _createddatetime,
                false
            );
            _loopindex := _loopindex + 1;
        END LOOP;
        _loopindex := 1;
        FOREACH _loopvar IN ARRAY _names LOOP
            INSERT INTO annotation_agent_name (
                annotationagentid,
                name,
                json,
                createddatetime,
                deleted
            )
            VALUES (
                _insertedid,
                _loopvar,
                _namesjson[_loopindex],
                _createddatetime,
                false
            );
            _loopindex := _loopindex + 1;
        END LOOP;
        _loopindex := 1;
        FOREACH _loopvar IN ARRAY _emails LOOP
            INSERT INTO annotation_agent_email (
                annotationagentid,
                email,
                json,
                createddatetime,
                deleted
            )
            VALUES (
                _insertedid,
                _loopvar,
                _emailsjson[_loopindex],
                _createddatetime,
                false
            );
            _loopindex := _loopindex + 1;
        END LOOP;
        _loopindex := 1;
        FOREACH _loopvar IN ARRAY _emailsha1s LOOP
            INSERT INTO annotation_agent_email_sha1 (
                annotationagentid,
                emailsha1,
                json,
                createddatetime,
                deleted
            )
            VALUES (
                _insertedid,
                _loopvar,
                _emailsha1sjson[_loopindex],
                _createddatetime,
                false
            );
            _loopindex := _loopindex + 1;
        END LOOP;
        _loopindex := 1;
        FOREACH _loopvar IN ARRAY _homepages LOOP
            INSERT INTO annotation_agent_homepage (
                annotationagentid,
                homepage,
                json,
                createddatetime,
                deleted
            )
            VALUES (
                _insertedid,
                _loopvar,
                _homepagesjson[_loopindex],
                _createddatetime,
                false
            );
            _loopindex := _loopindex + 1;
        END LOOP;
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
                aa.id = _insertedid;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;

ALTER FUNCTION public.annotation_creator_create(integer, integer, integer, character varying, jsonb, character varying[], jsonb[], text[], jsonb[], character varying, text[], jsonb[], text[], jsonb[], text[], jsonb[]) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_creator_create(integer, integer, integer, character varying, jsonb, character varying[], jsonb[], text[], jsonb[], character varying, text[], jsonb[], text[], jsonb[], text[], jsonb[]) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_creator_create(integer, integer, integer, character varying, jsonb, character varying[], jsonb[], text[], jsonb[], character varying, text[], jsonb[], text[], jsonb[], text[], jsonb[]) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_creator_create(integer, integer, integer, character varying, jsonb, character varying[], jsonb[], text[], jsonb[], character varying, text[], jsonb[], text[], jsonb[], text[], jsonb[]) FROM public;
