CREATE TABLE IF NOT EXISTS public.url_data
(
    id integer NOT NULL DEFAULT nextval('url_data_id_seq'::regclass),
    long_url text COLLATE pg_catalog."default",
    short_url text COLLATE pg_catalog."default",
    CONSTRAINT url_data_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.url_data
    OWNER to postgres;
