-- Table: public.annotation_agent

-- DROP TABLE public.annotation_agent;

CREATE TABLE public.annotation_agent
(
    id serial NOT NULL,
    agentiri character varying(100),
    nickname text,
    annotationid integer,
    bodyid integer,
    targetid integer,
    relationshiptype character varying(10) NOT NULL,
    json jsonb NOT NULL,
    createddatetime timestamp without time zone NOT NULL,
    modifieddatetime timestamp without time zone,
    deleted boolean NOT NULL DEFAULT false,
    CONSTRAINT pk_annotation_agent PRIMARY KEY (id),
    CONSTRAINT fk_annotation_agent_annotation FOREIGN KEY (annotationid) REFERENCES public.annotation (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_annotation_agent_annotation_body FOREIGN KEY (bodyid) REFERENCES public.annotation_body (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_annotation_agent_annotation_target FOREIGN KEY (targetid) REFERENCES public.annotation_target (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);

ALTER TABLE public.annotation_agent OWNER TO postgres;
