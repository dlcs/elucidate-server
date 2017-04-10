-- Table: public.annotation_agent_homepage

-- DROP TABLE public.annotation_agent_homepage;

CREATE TABLE public.annotation_agent_homepage
(
    id serial NOT NULL,
    annotationagentid integer NOT NULL,
    homepage text NOT NULL,
    json jsonb NOT NULL,
    createddatetime timestamp without time zone NOT NULL,
    modifieddatetime timestamp without time zone,
    deleted boolean NOT NULL DEFAULT false,
    CONSTRAINT pk_annotation_agent_homepage PRIMARY KEY (id),
    CONSTRAINT fk_annotation_agent_homepage_annotation_agent FOREIGN KEY (annotationagentid) REFERENCES public.annotation_agent (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);

ALTER TABLE public.annotation_agent_homepage OWNER TO postgres;
