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
  id integer NOT NULL DEFAULT nextval('annotation_id_seq'::regclass),
  collectionid integer NOT NULL,
  CONSTRAINT pk_annotation PRIMARY KEY (id),
  CONSTRAINT fk_annotation_annotation_collection FOREIGN KEY (collectionid)
      REFERENCES public.annotation_collection (id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.annotation
  OWNER TO postgres;
GRANT ALL ON TABLE public.annotation TO postgres;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE public.annotation TO annotations_role;

-- Index: public.fki_annotation_annotation_collection

-- DROP INDEX public.fki_annotation_annotation_collection;

CREATE INDEX fki_annotation_annotation_collection
  ON public.annotation
  USING btree
  (collectionid);

