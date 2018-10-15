-- Table: public.annotation_history

-- DROP TABLE public.annotation_history;

CREATE TABLE public.annotation_history
(
    id serial NOT NULL,
    annotationid integer NOT NULL,
    json jsonb NOT NULL,
    version integer NOT NULL,
    createddatetime timestamp without time zone NOT NULL,
    modifieddatetime timestamp without time zone,
    deleted boolean NOT NULL,
    CONSTRAINT pk_annotation_history PRIMARY KEY (id),
    CONSTRAINT unq_annotation_history_annotationid_version UNIQUE (annotationid, version),
    CONSTRAINT fk_annotation_history_annotation FOREIGN KEY (annotationid) REFERENCES public.annotation (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
    OIDS = FALSE
);

ALTER TABLE public.annotation_history OWNER TO postgres;
GRANT ALL ON TABLE public.annotation_history TO postgres;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE public.annotation_history TO annotations_role;
