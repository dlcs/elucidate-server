-- View: public.annotation_temporal_get

-- DROP VIEW public.annotation_temporal_get;

CREATE OR REPLACE VIEW public.annotation_temporal_get AS
    SELECT
        atp.id,
        a.annotationid,
        ac.collectionid,
        ab.bodyiri,
        ab.sourceiri AS bodysourceiri,
        at.targetiri,
        at.sourceiri AS targetsourceiri,
        atp.type,
        atp.value,
        atp.json,
        atp.createddatetime,
        atp.modifieddatetime,
        atp.deleted
    FROM
        annotation_temporal atp
            LEFT JOIN annotation_body ab ON ab.id = atp.bodyid
            LEFT JOIN annotation_target at ON at.id = atp.targetid
            LEFT JOIN annotation a ON a.id = atp.annotationid
            LEFT JOIN annotation_collection ac ON ac.id = a.collectionid;
