-- View: public.annotation_body_get

-- DROP VIEW public.annotation_body_get;

CREATE OR REPLACE VIEW public.annotation_body_get AS 
    SELECT
        a.annotationid,
        ac.collectionid,
        ab.bodyiri,
        ab.createddatetime,
        ab.deleted,
        ab.json,
        ab.modifieddatetime,
        ab.sourceiri,
        ab.id
    FROM
        annotation_body ab
            LEFT JOIN annotation a ON ab.annotationid = a.id
            LEFT JOIN annotation_collection ac ON a.collectionid = ac.id;

ALTER TABLE public.annotation_body_get OWNER TO postgres;
GRANT ALL ON TABLE public.annotation_body_get TO postgres;
GRANT ALL ON TABLE public.annotation_body_get TO annotations_role;
