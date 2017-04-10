-- Table: public.annotation_agent_type

-- DROP TABLE public.annotation_agent_type;

CREATE TABLE public.annotation_agent_type
(
    id serial NOT NULL,
    annotationagentid integer NOT NULL,
    type character varying(100) NOT NULL,
    json jsonb NOT NULL,
    createddatetime timestamp without time zone NOT NULL,
    modifieddatetime timestamp without time zone,
    deleted boolean NOT NULL DEFAULT false,
    CONSTRAINT pk_annotation_agent_type PRIMARY KEY (id),
    CONSTRAINT fk_annotation_agent_type_annotation_agent FOREIGN KEY (annotationagentid) REFERENCES public.annotation_agent (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);

ALTER TABLE public.annotation_agent_type  OWNER TO postgres;
