-- Function: public.annotation_search(character varying, boolean, character varying)

-- DROP FUNCTION public.annotation_search(character varying, boolean, character varying);

CREATE OR REPLACE FUNCTION public.annotation_search(_targetid character varying, _strict boolean, _box character varying)
RETURNS SETOF annotation_get AS $BODY$
DECLARE
    box_min_x integer;
    box_min_y integer;
    box_max_x integer;
    box_max_y integer;
BEGIN
    DROP TABLE IF EXISTS matched_target;
    CREATE TEMPORARY TABLE matched_target ON COMMIT DROP AS
        SELECT
            a.annotationid,
            a.cachekey,
            ac.collectionid,
            a.createddatetime,
            a.deleted,
            a.json,
            a.modifieddatetime,
            a.target_id
        FROM (
            SELECT
                a.annotationid,
                a.collectionid,
                a.cachekey,
                a.createddatetime,
                a.deleted,
                a.json,
                a.modifieddatetime,
                jsonb_array_elements(json -> 'http://www.w3.org/ns/oa#hasTarget') ->> '@id' AS target_id
            FROM
                annotation a) AS a
            LEFT JOIN annotation_collection ac ON a.collectionid = ac.id
        WHERE
            CASE _strict WHEN true THEN a.target_id = _targetid ELSE a.target_id LIKE (_targetid || '%') END
            AND a.deleted = false;
    IF _box <> '' THEN
        box_min_x := (regexp_split_to_array(_box, ','))[1]::integer;
        box_min_y := (regexp_split_to_array(_box, ','))[2]::integer;
        box_max_x := box_min_x + (regexp_split_to_array(_box, ','))[3]::integer;
        box_max_y := box_min_y + (regexp_split_to_array(_box, ','))[4]::integer;
        DROP TABLE IF EXISTS selector_values;
        CREATE TEMPORARY TABLE selector_values ON COMMIT DROP AS
            SELECT
                sq.annotationid,
                sq.collectionid,
                sq.cachekey,
                sq.createddatetime,
                sq.deleted,
                sq.json,
                sq.modifieddatetime,
                sq.selector[1]::integer AS min_x,
                sq.selector[2]::integer AS min_y,
                sq.selector[1]::integer + sq.selector[3]::integer AS max_x,
                sq.selector[2]::integer + sq.selector[4]::integer AS max_y
            FROM (
                SELECT
                    mt.annotationid,
                    mt.collectionid,
                    mt.cachekey,
                    mt.createddatetime,
                    mt.deleted,
                    mt.json,
                    mt.modifieddatetime,
                    regexp_matches(target_id, '#xywh\=(\d+),(\d+),(\d+),(\d+)$') AS selector FROM matched_target mt) sq;
        RETURN QUERY
            SELECT
                sv.annotationid,
                sv.cachekey,
                sv.collectionid,
                sv.createddatetime,
                sv.deleted,
                sv.json,
                sv.modifieddatetime
            FROM
                selector_values sv
            WHERE
                NOT(box_min_x >= sv.max_x OR box_max_x <= sv.min_x OR box_min_y >= sv.max_y OR box_max_y <= sv.min_y);
    ELSE
        RETURN QUERY
            SELECT
                mt.annotationid,
                mt.cachekey,
                mt.collectionid,
                mt.createddatetime,
                mt.deleted,
                mt.json,
                mt.modifieddatetime
            FROM
                matched_target mt;
    END IF;
END;$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;
ALTER FUNCTION public.annotation_search(character varying, boolean, character varying) OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_search(character varying, boolean, character varying) TO postgres;
GRANT EXECUTE ON FUNCTION public.annotation_search(character varying, boolean, character varying) TO annotations_role;
REVOKE ALL ON FUNCTION public.annotation_search(character varying, boolean, character varying) FROM public;
