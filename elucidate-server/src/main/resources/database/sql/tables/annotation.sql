-- Table: public.annotation

-- DROP TABLE public.annotation;

CREATE TABLE public.annotation
(
    annotationid character varying(100) NOT NULL,
    json jsonb NOT NULL,
    deleted boolean NOT NULL DEFAULT false,
    createddatetime timestamp without time zone NOT NULL,
    modifieddatetime timestamp without time zone,
    cachekey character(32) NOT NULL,
    id serial NOT NULL,
    collectionid integer NOT NULL,
    CONSTRAINT pk_annotation PRIMARY KEY (id),
    CONSTRAINT fk_annotation_annotation_collection FOREIGN KEY (collectionid) REFERENCES public.annotation_collection (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);


-- Index: public.idx_annotation_annotationid

-- DROP INDEX public.idx_annotation_annotationid;

CREATE INDEX idx_annotation_annotationid ON public.annotation USING btree (annotationid);

-- Index: public.idx_annotation_collectionid

-- DROP INDEX public.idx_annotation_collectionid;

CREATE INDEX idx_annotation_collectionid ON public.annotation USING btree (collectionid);
