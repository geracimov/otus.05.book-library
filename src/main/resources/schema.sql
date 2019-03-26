/************ create tables ***************/
CREATE TABLE IF NOT EXISTS genre (
	id   UUID NOT NULL,
	name VARCHAR(50),
	CONSTRAINT genre_pkey
		PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS author (
	id    UUID NOT NULL,
	name  VARCHAR(50),
	birth DATE,
	CONSTRAINT author_pkey
		PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book (
	id         UUID NOT NULL,
	name       TEXT,
	year       SMALLINT,
	page_count SMALLINT,
	isbn       VARCHAR(30),
	CONSTRAINT book_pkey
		PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book_genre (
	id       UUID NOT NULL
		CONSTRAINT book_genre_pkey
			PRIMARY KEY,
	book_id  UUID NOT NULL
		CONSTRAINT book_genre_book_id_fkey
			REFERENCES book
			ON UPDATE CASCADE ON DELETE CASCADE,
	genre_id UUID NOT NULL
		CONSTRAINT book_genre_genre_id_fkey
			REFERENCES genre
			ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS book_author (
	id        UUID NOT NULL
		CONSTRAINT book_author_pkey
			PRIMARY KEY,
	book_id   UUID NOT NULL
		CONSTRAINT book_author_book_id_fkey
			REFERENCES book
			ON UPDATE CASCADE ON DELETE CASCADE,
	author_id UUID NOT NULL
		CONSTRAINT book_author_author_id_fkey
			REFERENCES author
			ON UPDATE CASCADE ON DELETE CASCADE
);

/************ create indexes ************/
CREATE UNIQUE INDEX IF NOT EXISTS book_author_book_id_author_id_uindex
	ON book_author (book_id, author_id);

CREATE UNIQUE INDEX IF NOT EXISTS book_genre_book_id_genre_id_uindex
	ON book_genre (book_id, genre_id);



