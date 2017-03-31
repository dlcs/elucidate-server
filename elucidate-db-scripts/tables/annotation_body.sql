-- Table: public.annotation_body

-- DROP TABLE public.annotation_body;

CREATE TABLE public.annotation_body
(
    id serial NOT NULL,
    bodyiri character varying(100),
    sourceiri character varying(100),
    modifieddatetime timestamp without time zone,
    deleted boolean NOT NULL DEFAULT false,
    annotationid integer NOT NULL,
    createddatetime timestamp without time zone NOT NULL,
    json jsonb NOT NULL,
    CONSTRAINT pk_body PRIMARY KEY (id),
    CONSTRAINT fk_body_annotation FOREIGN KEY (annotationid) REFERENCES public.annotation (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);

ALTER TABLE public.annotation_body OWNER TO postgres;
GRANT ALL ON TABLE public.annotation_body TO postgres;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE public.annotation_body TO annotations_role;
