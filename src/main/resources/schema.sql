BEGIN;

CREATE TABLE IF NOT EXISTS public.item
(
    id serial NOT NULL PRIMARY KEY,
    title character varying(20) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default",
    sold_by integer CONSTRAINT "FK_user" REFERENCES public.users(id),
    created date NOT NULL,
    updated date,
    game_title character varying(50) COLLATE pg_catalog."default"
);

CREATE TABLE IF NOT EXISTS public.comment
(
    id serial NOT NULL PRIMARY KEY,
    text text COLLATE pg_catalog."default" NOT NULL,
    rating numeric(4, 1) NOT NULL,
    author integer CONSTRAINT "FK_user" REFERENCES public.users(id),
    created date NOT NULL,
    approved character varying(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.users
(
    id serial NOT NULL PRIMARY KEY,
    login character varying(20) COLLATE pg_catalog."default",
	password character varying(8) COLLATE pg_catalog."default" ,
	first_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    second_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    email character varying(20) COLLATE pg_catalog."default" UNIQUE ,
    created date NOT NULL,
    rating numeric(4, 1) NOT NULL,
    user_role character varying(20) COLLATE pg_catalog."default"
);

COMMIT;