-- Function: public.annotation_search_by_temporal(boolean, boolean, boolean, character varying[], timestamp without time zone)

-- DROP FUNCTION public.annotation_search_by_temporal(boolean, boolean, boolean, character varying[], timestamp without time zone);

CREATE OR REPLACE FUNCTION public.annotation_search_by_temporal(
    _searchannotations boolean,
    _searchbodies boolean,
    _searchtargets boolean,
    _types character varying[],
    _since timestamp without time zone)
RETURNS SETOF annotation_get AS
$BODY$
    BEGIN
        RETURN QUERY
          SELECT DISTINCT
              a.annotationid,
              a.cachekey,
              ac.collectionid,
              a.createddatetime,
              a.deleted,
              a.json,
              a.modifieddatetime,
              a.id
          FROM
              annotation AS a
                  LEFT JOIN annotation_collection AS ac ON a.collectionid = ac.id
          WHERE
              a.id IN (
                  SELECT
                      a.id
                  FROM
                      annotation AS a
                          LEFT JOIN annotation_temporal atpc ON atpc.annotationid = a.id
                          LEFT JOIN annotation_temporal atpm ON atpm.annotationid = a.id
                          LEFT JOIN annotation_temporal atpg on atpg.annotationid = a.id
                  WHERE
                      a.deleted = false
                      AND CASE _searchannotations
                          WHEN true THEN
                              CASE WHEN _types @> ARRAY['CREATED']::character varying[] THEN
                                  atpc.type = 'CREATED' AND atpc.value >= _since
                              ELSE
                                  true
                              END
                              AND CASE WHEN _types @> ARRAY['MODIFIED']::character varying[] THEN
                                  atpm.type = 'MODIFIED' AND atpm.value >= _since
                              ELSE
                                  true
                              END
                              AND CASE WHEN _types @> ARRAY['GENERATED']::character varying[] THEN
                                  atpg.type = 'GENERATED' AND atpg.value >= _since
                              ELSE
                                  true
                              END
                          ELSE
                              false
                          END
                  UNION
                  SELECT
                      a.id
                  FROM
                      annotation AS a
                          LEFT JOIN annotation_body ab ON ab.annotationid = a.id
                          LEFT JOIN annotation_temporal atpc ON atpc.bodyid = ab.id
                          LEFT JOIN annotation_temporal atpm ON atpm.bodyid = ab.id
                          LEFT JOIN annotation_temporal atpg on atpg.bodyid = ab.id
                  WHERE
                      a.deleted = false
                      AND CASE _searchbodies
                          WHEN true THEN
                              CASE WHEN _types @> ARRAY['CREATED']::character varying[] THEN
                                  atpc.type = 'CREATED' AND atpc.value >= _since
                              ELSE
                                  true
                              END
                              AND CASE WHEN _types @> ARRAY['MODIFIED']::character varying[] THEN
                                  atpm.type = 'MODIFIED' AND atpm.value >= _since
                              ELSE
                                  true
                              END
                              AND CASE WHEN _types @> ARRAY['GENERATED']::character varying[] THEN
                                  atpg.type = 'GENERATED' AND atpg.value >= _since
                              ELSE
                                  true
                              END
                          ELSE
                              false
                          END
                  UNION
                  SELECT
                      a.id
                  FROM
                      annotation AS a
                          LEFT JOIN annotation_target at ON at.annotationid = a.id
                          LEFT JOIN annotation_temporal atpc ON atpc.targetid = at.id
                          LEFT JOIN annotation_temporal atpm ON atpm.targetid = at.id
                          LEFT JOIN annotation_temporal atpg on atpg.targetid = at.id
                  WHERE
                      a.deleted = false
                      AND CASE _searchtargets
                          WHEN true THEN
                              CASE WHEN _types @> ARRAY['CREATED']::character varying[] THEN
                                  atpc.type = 'CREATED' AND atpc.value >= _since
                              ELSE
                                  true
                              END
                              AND CASE WHEN _types @> ARRAY['MODIFIED']::character varying[] THEN
                                  atpm.type = 'MODIFIED' AND atpm.value >= _since
                              ELSE
                                  true
                              END
                              AND CASE WHEN _types @> ARRAY['GENERATED']::character varying[] THEN
                                  atpg.type = 'GENERATED' AND atpg.value >= _since
                              ELSE
                                  true
                              END
                          ELSE
                              false
                          END
              );
END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;
