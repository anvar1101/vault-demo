create table foo_data
(
    id    uuid,
    key   varchar(500),
    value varchar(1000),
    PRIMARY KEY (id)
);

GRANT ALL PRIVILEGES ON TABLE foo_data TO PUBLIC;

create table baz_data
(
    id    uuid,
    key   varchar(500),
    value varchar(1000),
    PRIMARY KEY (id)
);

GRANT ALL PRIVILEGES ON TABLE baz_data TO PUBLIC;


CREATE OR REPLACE FUNCTION create_user_with_privileges(
    IN username VARCHAR(255),
    IN password VARCHAR(255),
    IN database_name VARCHAR(255)
) RETURNS VOID AS
$$
BEGIN
    -- Step 1: Create user
    EXECUTE FORMAT('CREATE USER %I WITH PASSWORD %L', username, password);

    -- Step 2: Grant all privileges on the specified database
   -- EXECUTE FORMAT('GRANT ALL PRIVILEGES ON DATABASE %I TO %I', database_name, username);
END;
$$ LANGUAGE plpgsql;
