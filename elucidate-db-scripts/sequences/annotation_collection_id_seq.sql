-- Sequence: public.annotation_collection_id_seq

-- DROP SEQUENCE public.annotation_collection_id_seq;

CREATE SEQUENCE public.annotation_collection_id_seq
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;
ALTER TABLE public.annotation_collection_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.annotation_collection_id_seq TO postgres;
GRANT ALL ON SEQUENCE public.annotation_collection_id_seq TO annotations_role;
