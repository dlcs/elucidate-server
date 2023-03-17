-- View: public.annotation_collection_get

-- DROP VIEW public.annotation_collection_get;

CREATE OR REPLACE VIEW public.annotation_collection_get AS
    SELECT
        ac.cachekey,
        ac.collectionid,
        ac.createddatetime,
        ac.deleted,
        ac.json,
        ac.modifieddatetime,
        ac.id
    FROM
        annotation_collection ac;
