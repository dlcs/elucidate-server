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
GRANT ALL ON TABLE public.annotation_agent_homepage TO postgres;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE public.annotation_agent_homepage TO annotations_role;

-- Index: public.idx_annotation_agent_homepage_annotationagentid

-- DROP INDEX public.idx_annotation_agent_homepage_annotationagentid;

CREATE INDEX idx_annotation_agent_homepage_annotationagentid ON public.annotation_agent_homepage USING btree (annotationagentid);

-- Index: public.idx_annotation_agent_homepage_homepage

-- DROP INDEX public.idx_annotation_agent_homepage_homepage;

CREATE INDEX idx_annotation_agent_homepage_homepage ON public.annotation_agent_homepage USING btree (homepage);
