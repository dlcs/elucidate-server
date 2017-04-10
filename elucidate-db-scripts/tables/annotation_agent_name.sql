-- Table: public.annotation_agent_name

-- DROP TABLE public.annotation_agent_name;

CREATE TABLE public.annotation_agent_name
(
    id serial NOT NULL,
    annotationagentid integer NOT NULL,
    name text NOT NULL,
    json jsonb NOT NULL,
    createddatetime timestamp without time zone NOT NULL,
    modifieddatetime timestamp without time zone,
    deleted boolean NOT NULL DEFAULT false,
    CONSTRAINT pk_annotation_agent_name PRIMARY KEY (id),
    CONSTRAINT fk_annotation_agent_name_annotation_agent FOREIGN KEY (annotationagentid) REFERENCES public.annotation_agent (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);

ALTER TABLE public.annotation_agent_name OWNER TO postgres;
