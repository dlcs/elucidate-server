-- Function: public.annotation_search_by_target(character varying)
-- DROP FUNCTION public.annotation_search_by_target(character varying);
CREATE
	OR REPLACE FUNCTION PUBLIC.annotation_search_by_target (_targetid CHARACTER VARYING)
RETURNS SETOF annotation_get AS $BODY$

BEGIN
	RETURN query

	SELECT a.annotationid
		,a.cachekey
		,ac.collectionid
		,a.createddatetime
		,a.deleted
		,a.json
		,a.modifieddatetime
	FROM (
		SELECT a.annotationid
			,a.cachekey
			,a.collectionid
			,a.createddatetime
			,a.deleted
			,a.json
			,a.modifieddatetime
			,jsonb_array_elements(json -> 'http://www.w3.org/ns/oa#hasTarget') ->> '@id' AS target_ids
		FROM annotation a
		) a
	LEFT JOIN annotation_collection ac ON a.collectionid = ac.id
	WHERE a.target_ids = _targetid
		AND a.deleted = false;
END;$BODY$

LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;

ALTER FUNCTION PUBLIC.annotation_search_by_target (CHARACTER VARYING) OWNER TO postgres;

GRANT EXECUTE
	ON FUNCTION PUBLIC.annotation_search_by_target(CHARACTER VARYING)
	TO postgres;

GRANT EXECUTE
	ON FUNCTION PUBLIC.annotation_search_by_target(CHARACTER VARYING)
	TO annotations_role;

REVOKE ALL
	ON FUNCTION PUBLIC.annotation_search_by_target(CHARACTER VARYING)
	FROM PUBLIC;
