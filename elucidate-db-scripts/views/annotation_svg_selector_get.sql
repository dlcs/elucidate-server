-- View: public.annotation_svg_selector_get

-- DROP VIEW public.annotation_svg_selector_get;

CREATE OR REPLACE VIEW public.annotation_svg_selector_get AS 
    SELECT
        asl.id,
        asl.selectoriri,
        ab.bodyiri,
        ab.sourceiri AS bodysourceiri,
        at.targetiri,
        at.sourceiri AS targetsourceiri,
        a.annotationid,
        ac.collectionid,
        asl.value,
        asl.createddatetime,
        asl.modifieddatetime,
        asl.deleted,
        asl.json
    FROM
        annotation_selector asl
            LEFT JOIN annotation_body ab ON asl.bodyid = ab.id
            LEFT JOIN annotation_target at ON asl.targetid = at.id
            LEFT JOIN annotation a ON at.annotationid = a.id
            LEFT JOIN annotation_collection ac ON a.collectionid = ac.id
    WHERE
        asl.type::text = 'http://www.w3.org/ns/oa#SvgSelector'::text;

ALTER TABLE public.annotation_svg_selector_get OWNER TO postgres;
GRANT ALL ON TABLE public.annotation_svg_selector_get TO postgres;
GRANT ALL ON TABLE public.annotation_svg_selector_get TO annotations_role;
