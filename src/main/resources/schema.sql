BEGIN;

CREATE TABLE IF NOT EXISTS public.users
(
    id serial NOT NULL PRIMARY KEY,
    login character varying(20) UNIQUE COLLATE pg_catalog."default",
	password character varying(128) COLLATE pg_catalog."default" ,
	first_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    second_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    email character varying(20) COLLATE pg_catalog."default" UNIQUE ,
    created_date date,
    role character varying(20),
    status character varying(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.visitor
(
    id integer NOT NULL PRIMARY KEY CONSTRAINT "FK_users" REFERENCES public.users(id)
);

CREATE TABLE IF NOT EXISTS public.administrator
(
    id integer NOT NULL PRIMARY KEY CONSTRAINT "FK_users" REFERENCES public.users(id)
);

CREATE TABLE IF NOT EXISTS public.seller
(
    id integer NOT NULL PRIMARY KEY CONSTRAINT "FK_users" REFERENCES public.users(id),
    rating numeric(4, 1) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.item
(
    id serial NOT NULL PRIMARY KEY,
    title character varying(20) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default",
    sold_by integer CONSTRAINT "FK_seller" REFERENCES public.seller(id),
    created_date date,
    updated_date date,
    game_title character varying(50) COLLATE pg_catalog."default"
);

CREATE TABLE IF NOT EXISTS public.comment
(
    id serial NOT NULL PRIMARY KEY,
    text text COLLATE pg_catalog."default" NOT NULL,
    rating numeric(4, 1) NOT NULL,
    author integer CONSTRAINT "FK_author" REFERENCES public.users(id),
	seller integer CONSTRAINT "FK_seller" REFERENCES public.seller(id),
    created_date date,
    status character varying(20) NOT NULL
);

COMMIT;