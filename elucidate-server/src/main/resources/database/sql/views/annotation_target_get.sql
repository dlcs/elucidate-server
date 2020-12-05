-- View: public.annotation_target_get

-- DROP VIEW public.annotation_target_get;

CREATE OR REPLACE VIEW public.annotation_target_get AS
    SELECT
        a.annotationid,
        ac.collectionid,
        at.targetiri,
        at.createddatetime,
        at.deleted,
        at.json,
        at.modifieddatetime,
        at.sourceiri,
        at.id
    FROM
        annotation_target at
            LEFT JOIN annotation a ON at.annotationid = a.id
            LEFT JOIN annotation_collection ac ON a.collectionid = ac.id;
