-- Table: public.annotation_agent_email_sha1

-- DROP TABLE public.annotation_agent_email_sha1;

CREATE TABLE public.annotation_agent_email_sha1
(
    id serial NOT NULL,
    annotationagentid integer NOT NULL,
    emailsha1 text NOT NULL,
    json jsonb NOT NULL,
    createddatetime timestamp without time zone NOT NULL,
    modifieddatetime timestamp without time zone,
    deleted boolean NOT NULL DEFAULT false,
    CONSTRAINT pk_annotation_agent_email_sha1 PRIMARY KEY (id),
    CONSTRAINT fk_annotation_agent_email_sha1_annotation_agent FOREIGN KEY (annotationagentid) REFERENCES public.annotation_agent (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);


-- Index: public.idx_annotation_agent_email_sha1_annotationagentid

-- DROP INDEX public.idx_annotation_agent_email_sha1_annotationagentid;

CREATE INDEX idx_annotation_agent_email_sha1_annotationagentid ON public.annotation_agent_email_sha1 USING btree (annotationagentid);

-- Index: public.idx_annotation_agent_email_sha1_emailsha1

-- DROP INDEX public.idx_annotation_agent_email_sha1_emailsha1;

CREATE INDEX idx_annotation_agent_email_sha1_emailsha1 ON public.annotation_agent_email_sha1 USING btree (emailsha1);
