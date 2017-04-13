-- Table: public.annotation_target

-- DROP TABLE public.annotation_target;

CREATE TABLE public.annotation_target
(
    id serial NOT NULL,
    targetiri character varying(100),
    sourceiri character varying(100),
    modifieddatetime timestamp without time zone,
    deleted boolean NOT NULL DEFAULT false,
    annotationid integer NOT NULL,
    createddatetime timestamp without time zone NOT NULL,
    json jsonb NOT NULL,
    CONSTRAINT pk_target PRIMARY KEY (id),
    CONSTRAINT fk_target_annotation FOREIGN KEY (annotationid) REFERENCES public.annotation (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);

ALTER TABLE public.annotation_target OWNER TO postgres;
GRANT ALL ON TABLE public.annotation_target TO postgres;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE public.annotation_target TO annotations_role;

-- Index: public.idx_annotation_target_annotationid

-- DROP INDEX public.idx_annotation_target_annotationid;

CREATE INDEX idx_annotation_target_annotationid ON public.annotation_target USING btree (annotationid);

-- Index: public.idx_annotation_target_sourceiri

-- DROP INDEX public.idx_annotation_target_sourceiri;

CREATE INDEX idx_annotation_target_sourceiri ON public.annotation_target USING btree (sourceiri);

-- Index: public.idx_annotation_target_targetiri

-- DROP INDEX public.idx_annotation_target_targetiri;

CREATE INDEX idx_annotation_target_targetiri ON public.annotation_target USING btree (targetiri);
