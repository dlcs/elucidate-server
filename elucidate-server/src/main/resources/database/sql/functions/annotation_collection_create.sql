-- Function: public.annotation_collection_create(character varying, jsonb)

-- DROP FUNCTION public.annotation_collection_create(character varying, jsonb);

CREATE OR REPLACE FUNCTION public.annotation_collection_create(
    _collectionid character varying,
    _json jsonb)
RETURNS SETOF annotation_collection_get AS
$BODY$
    DECLARE
        _insertedid integer;
        _createddatetime timestamp without time zone DEFAULT now();
    BEGIN
        INSERT INTO annotation_collection (
            collectionid,
            json,
            createddatetime,
            deleted,
            cachekey
        )
        VALUES (
            _collectionid,
            _json,
            _createddatetime,
            false,
            md5(_createddatetime::text)
        ) RETURNING id INTO _insertedid;
        RETURN QUERY
            SELECT
                ac.cachekey,
                ac.collectionid,
                ac.createddatetime,
                ac.deleted,
                ac.json,
                ac.modifieddatetime,
                ac.id
            FROM
                annotation_collection ac
            WHERE
                ac.id = _insertedid;
    END;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100 ROWS 1000;
