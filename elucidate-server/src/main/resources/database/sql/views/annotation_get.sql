-- View: public.annotation_get

-- DROP VIEW public.annotation_get;

CREATE OR REPLACE VIEW public.annotation_get AS
    SELECT
        a.annotationid,
        a.cachekey,
        ac.collectionid,
        a.createddatetime,
        a.deleted,
        a.json,
        a.modifieddatetime,
        a.id
    FROM
        annotation a
            LEFT JOIN annotation_collection ac ON a.collectionid = ac.id;
