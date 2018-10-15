CREATE TABLE public.security_user (
  id               serial                      NOT NULL,
  uid              character varying,
  security_user_id character varying,
  createddatetime  timestamp without time zone NOT NULL,
  modifieddatetime timestamp without time zone,

  CONSTRAINT pk_user PRIMARY KEY (id),
  UNIQUE (uid)
) WITH (
OIDS = FALSE
);

CREATE TABLE public.security_group (
  id               serial                      NOT NULL,
  label            text,
  group_id         text,
  owner_id         integer,
  createddatetime  timestamp without time zone NOT NULL,
  modifieddatetime timestamp without time zone,

  CONSTRAINT pk_group PRIMARY KEY (id),
  CONSTRAINT fk_security_group_owner FOREIGN KEY (owner_id) REFERENCES public.security_user (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
  UNIQUE (group_id)
) WITH (
OIDS = FALSE
);

CREATE TABLE public.security_user_group_membership (
  user_id  integer,
  group_id integer,

  CONSTRAINT fk_user_group_membership_user FOREIGN KEY (user_id) REFERENCES public.security_user (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_user_group_membership_group FOREIGN KEY (group_id) REFERENCES public.security_group (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
  UNIQUE (user_id, group_id)
);

CREATE TABLE public.annotation_owner
(
  user_id       integer,
  annotation_id integer,

  CONSTRAINT fk_annotation_owner_user FOREIGN KEY (user_id) REFERENCES public.security_user (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_annotation_owner_annotation FOREIGN KEY (annotation_id) REFERENCES public.annotation (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
  UNIQUE (user_id, annotation_id)
)
WITH (
OIDS = FALSE
);

CREATE TABLE public.annotation_group_membership
(
  annotation_id integer,
  group_id      integer,

  CONSTRAINT fk_annotation_owner_annotation FOREIGN KEY (annotation_id) REFERENCES public.annotation (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_annotation_group_membership_group FOREIGN KEY (group_id) REFERENCES public.security_group (id) MATCH SIMPLE ON UPDATE RESTRICT ON DELETE RESTRICT,
  UNIQUE (annotation_id, group_id)
);

CREATE OR REPLACE VIEW public.annotation_group_memberships AS
  SELECT
    agm.annotation_id,
    ARRAY_AGG(agm.group_id) as group_ids
  FROM annotation_group_membership agm
  GROUP BY agm.annotation_id;
