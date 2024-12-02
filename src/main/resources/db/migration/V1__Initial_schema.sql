CREATE TABLE book
 id BIGSERIAL PRIMARY KEY,
 author varchar(255) NOT NULL,	
 isbn   varchar(255) UNIQUE NOT NULL,
 price  float8 NOT NULL,
 title varchar(255) NOT NULL,
 publisher varchar(255).
 created_date timestamp NOT NULL,
 last_modified_date timestamp NOT NULL,
 version integer NOT NULL
)