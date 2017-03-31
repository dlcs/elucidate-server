-- Table: public.annotation_selector

-- DROP TABLE public.annotation_selector;

CREATE TABLE public.annotation_selector
(
    id serial NOT NULL,
    type character varying(100) NOT NULL,
    value text,
    conformsto character varying(100),
    exact text,
    prefix text,
    suffix text,
    start integer,
    "end" integer,
    targetid integer,
    bodyid integer,
    json jsonb NOT NULL,
    createddatetime timestamp without time zone NOT NULL,
    modifieddatetime timestamp without time zone,
    deleted boolean NOT NULL DEFAULT false,
    selectoriri character varying(100),
    x integer,
    y integer,
    w integer,
    h integer,
    CONSTRAINT pk_selector PRIMARY KEY (id),
    CONSTRAINT fk_selector_body FOREIGN KEY (bodyid) REFERENCES public.annotation_body (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_selector_target FOREIGN KEY (targetid) REFERENCES public.annotation_target (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT chk_selector_bodyid_targetid CHECK (bodyid IS NOT NULL OR targetid IS NOT NULL)
)
WITH (
  OIDS=FALSE
);

ALTER TABLE public.annotation_selector OWNER TO postgres;
GRANT ALL ON TABLE public.annotation_selector TO postgres;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE public.annotation_selector TO annotations_role;
