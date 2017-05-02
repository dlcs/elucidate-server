-- Table: public.annotation_agent_email

-- DROP TABLE public.annotation_agent_email;

CREATE TABLE public.annotation_agent_email
(
    id serial NOT NULL,
    annotationagentid integer NOT NULL,
    email text NOT NULL,
    json jsonb NOT NULL,
    createddatetime timestamp without time zone NOT NULL,
    modifieddatetime timestamp without time zone,
    deleted boolean NOT NULL DEFAULT false,
    CONSTRAINT pk_annotation_agent_email PRIMARY KEY (id),
    CONSTRAINT fk_annotation_agent_email_annotation_agent FOREIGN KEY (annotationagentid) REFERENCES public.annotation_agent (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);

ALTER TABLE public.annotation_agent_email OWNER TO postgres;
GRANT ALL ON TABLE public.annotation_agent_email TO postgres;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE public.annotation_agent_email TO annotations_role;

-- Index: public.idx_annotation_agent_email_annotationagentid

-- DROP INDEX public.idx_annotation_agent_email_annotationagentid;

CREATE INDEX idx_annotation_agent_email_annotationagentid ON public.annotation_agent_email USING btree (annotationagentid);

-- Index: public.idx_annotation_agent_email_email

-- DROP INDEX public.idx_annotation_agent_email_email;

CREATE INDEX idx_annotation_agent_email_email ON public.annotation_agent_email USING btree (email);
