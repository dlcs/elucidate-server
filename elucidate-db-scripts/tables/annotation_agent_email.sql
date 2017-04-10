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
