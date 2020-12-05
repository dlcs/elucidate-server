-- Table: public.annotation_temporal

-- DROP TABLE public.annotation_temporal;

CREATE TABLE public.annotation_temporal
(
    id serial NOT NULL,
    annotationid integer,
    bodyid integer,
    targetid integer,
    type character varying NOT NULL,
    value timestamp without time zone NOT NULL,
    createddatetime timestamp without time zone NOT NULL,
    modifieddatetime timestamp without time zone,
    deleted boolean NOT NULL DEFAULT false,
    json jsonb NOT NULL,
    CONSTRAINT pk_annotation_temporal PRIMARY KEY (id),
    CONSTRAINT fk_annotation_temporal_annotation FOREIGN KEY (annotationid)  REFERENCES public.annotation (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_annotation_temporal_annotation_body FOREIGN KEY (bodyid) REFERENCES public.annotation_body (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_annotation_temporal_annotation_target FOREIGN KEY (targetid) REFERENCES public.annotation_target (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);


-- Index: public.idx_annotation_temporal_annotationid

-- DROP INDEX public.idx_annotation_temporal_annotationid;

CREATE INDEX idx_annotation_temporal_annotationid ON public.annotation_temporal USING btree (annotationid);

-- Index: public.idx_annotation_temporal_id

-- DROP INDEX public.idx_annotation_temporal_id;

CREATE INDEX idx_annotation_temporal_bodyid ON public.annotation_temporal USING btree (bodyid);

-- Index: public.idx_annotation_temporal_id

-- DROP INDEX public.idx_annotation_temporal_id;

CREATE INDEX idx_annotation_temporal_targetid ON public.annotation_temporal USING btree (targetid);
