CREATE TYPE todo_status AS ENUM (
    'TODO',
    'DOING',
    'DONE'
);

CREATE TYPE todo_priority AS ENUM (
    'LOW',
    'MEDIUM',
    'HIGH'
);

CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       full_name VARCHAR(255) NOT NULL,
                       created_at TIMESTAMPTZ NOT NULL,
                       updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE todos (
                       id UUID PRIMARY KEY,
                       owner_id UUID NOT NULL,
                       parent_id UUID,
                       status todo_status NOT NULL DEFAULT 'TODO',
                       priority todo_priority NOT NULL DEFAULT 'LOW',
                       title VARCHAR(65) NOT NULL,
                       description VARCHAR(255),
                       created_at TIMESTAMPTZ NOT NULL,
                       updated_at TIMESTAMPTZ NOT NULL,

                       CONSTRAINT fk_todos_owner
                           FOREIGN KEY (owner_id)
                               REFERENCES users(id)
                               ON DELETE CASCADE,

                       CONSTRAINT fk_todos_parent
                           FOREIGN KEY (parent_id)
                               REFERENCES todos(id)
                               ON DELETE CASCADE,

                       CONSTRAINT ck_todos_parent_not_self
                           CHECK (parent_id IS NULL OR parent_id <> id)
);

CREATE INDEX idx_todos_owner_id
    ON todos(owner_id);

CREATE INDEX idx_todos_parent_id
    ON todos(parent_id);