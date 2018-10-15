-- View: public.annotation_history_get

-- DROP VIEW public.annotation_history_get;

CREATE OR REPLACE VIEW public.annotation_history_get AS
    SELECT
        ah.id,
        a.annotationid,
        ac.collectionid,
        ah.json,
        ah.version,
        ah.createddatetime,
        ah.modifieddatetime,
        ah.deleted
    FROM
        annotation_history ah
            LEFT JOIN annotation a ON ah.annotationid = a.id
            LEFT JOIN annotation_collection ac ON a.collectionid = ac.id;

ALTER TABLE public.annotation_history_get OWNER TO postgres;
GRANT ALL ON TABLE public.annotation_history_get TO postgres;
GRANT ALL ON TABLE public.annotation_history_get TO annotations_role;
