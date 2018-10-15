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
    CONSTRAINT fk_annotation_agent_annotation FOREIGN KEY (annotationid)  REFERENCES public.annotation (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_annotation_agent_annotation_body FOREIGN KEY (bodyid) REFERENCES public.annotation_body (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_annotation_agent_annotation_target FOREIGN KEY (targetid) REFERENCES public.annotation_target (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);

ALTER TABLE public.annotation_agent OWNER TO postgres;
GRANT ALL ON TABLE public.annotation_agent TO postgres;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE public.annotation_agent TO annotations_role;

-- Index: public.idx_annotation_agent_agentiri

-- DROP INDEX public.idx_annotation_agent_agentiri;

CREATE INDEX idx_annotation_agent_agentiri ON public.annotation_agent USING btree (agentiri);

-- Index: public.idx_annotation_agent_annotationid

-- DROP INDEX public.idx_annotation_agent_annotationid;

CREATE INDEX idx_annotation_agent_annotationid ON public.annotation_agent USING btree (annotationid);

-- Index: public.idx_annotation_agent_bodyid

-- DROP INDEX public.idx_annotation_agent_bodyid;

CREATE INDEX idx_annotation_agent_bodyid ON public.annotation_agent USING btree (bodyid);

-- Index: public.idx_annotation_agent_nickname

-- DROP INDEX public.idx_annotation_agent_nickname;

CREATE INDEX idx_annotation_agent_nickname ON public.annotation_agent USING btree (nickname);

-- Index: public.idx_annotation_agent_relationshiptype

-- DROP INDEX public.idx_annotation_agent_relationshiptype;

CREATE INDEX idx_annotation_agent_relationshiptype ON public.annotation_agent USING btree (relationshiptype);

-- Index: public.idx_annotation_agent_targetid

-- DROP INDEX public.idx_annotation_agent_targetid;

CREATE INDEX idx_annotation_agent_targetid ON public.annotation_agent USING btree (targetid);
