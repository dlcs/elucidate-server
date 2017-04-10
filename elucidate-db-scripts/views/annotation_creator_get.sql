-- View: public.annotation_creator_get

-- DROP VIEW public.annotation_creator_get;

CREATE OR REPLACE VIEW public.annotation_creator_get AS 
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
        aa.relationshiptype::text = 'CREATOR'::text;

ALTER TABLE public.annotation_creator_get OWNER TO postgres;
GRANT ALL ON TABLE public.annotation_creator_get TO postgres;
GRANT ALL ON TABLE public.annotation_creator_get TO annotations_role;
