-- Table: public.control_panel_user

-- DROP TABLE public.control_panel_user;

CREATE TABLE public.control_panel_user
(
  id integer NOT NULL DEFAULT nextval('control_panel_user_id_seq'::regclass),
  username character varying(50) NOT NULL,
  password character(60) NOT NULL,
  emailaddress character varying(254) NOT NULL,
  createddatetime timestamp without time zone NOT NULL,
  lastlogindatetime timestamp without time zone,
  totallogins integer NOT NULL,
  enabled boolean NOT NULL,
  role character varying(50) NOT NULL,
  CONSTRAINT pk_control_panel_user PRIMARY KEY (id),
  CONSTRAINT unq_control_panel_email_address UNIQUE (emailaddress),
  CONSTRAINT unq_control_panel_user_username UNIQUE (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.control_panel_user
  OWNER TO postgres;
GRANT ALL ON TABLE public.control_panel_user TO postgres;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE public.control_panel_user TO annotations_role;
