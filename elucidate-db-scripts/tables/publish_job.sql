-- Table: public.publish_job

-- DROP TABLE public.publish_job;

CREATE TABLE public.publish_job
(
  id integer NOT NULL DEFAULT nextval('publish_job_id_seq'::regclass),
  totaltopublish integer NOT NULL,
  totalpublished integer NOT NULL,
  startdate timestamp without time zone NOT NULL,
  enddate timestamp without time zone,
  status character varying(32) NOT NULL,
  republishdate timestamp without time zone NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.publish_job
  OWNER TO postgres;
GRANT ALL ON TABLE public.publish_job TO postgres;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE public.publish_job TO annotations_role;
