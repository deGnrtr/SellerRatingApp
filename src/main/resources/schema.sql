BEGIN;


CREATE TABLE IF NOT EXISTS public.item
(
    id integer NOT NULL DEFAULT nextval('"item_id_seq"'::regclass),
    title character varying(20) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default",
    sold_by integer NOT NULL,
    created date NOT NULL,
    updated date NOT NULL,
    game_title character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT "item_pkey" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.comment
(
    id serial NOT NULL,
    text text COLLATE pg_catalog."default" NOT NULL,
    rating numeric(4, 1) NOT NULL,
    author integer NOT NULL,
    created date NOT NULL,
    approved character varying(20) NOT NULL,
    CONSTRAINT "comment_pkey" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.users
(
    id integer NOT NULL DEFAULT nextval('"users_id_seq"'::regclass),
    login character varying(20) COLLATE pg_catalog."default",
	password character varying(8) COLLATE pg_catalog."default" ,
	first_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    email character varying(20) COLLATE pg_catalog."default" ,
    created date NOT NULL,
    rating numeric(4, 1) NOT NULL,
    user_role user_role,
    CONSTRAINT "users_pkey" PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.item
    ADD CONSTRAINT "FK_user" FOREIGN KEY (sold_by)
    REFERENCES public.users (id) MATCH FULL
    NOT VALID;


ALTER TABLE IF EXISTS public.comment
    ADD CONSTRAINT "FK_user" FOREIGN KEY (author)
    REFERENCES public.users (id) MATCH FULL
    NOT VALID;

END;