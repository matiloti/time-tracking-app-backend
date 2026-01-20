CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS categories (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS projects (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(50) UNIQUE NOT NULL,
  description VARCHAR(500),
  category_id INTEGER NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  CONSTRAINT fk__projects_categories
      FOREIGN KEY (category_id)
          REFERENCES categories(id)
          ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS milestones (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    project_id UUID NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk__milestones_projects
    FOREIGN KEY (project_id)
    REFERENCES projects(id)
    ON DELETE CASCADE
);

INSERT INTO categories(name) VALUES('Software');
INSERT INTO categories(name) VALUES('Content Creation');
INSERT INTO categories(name) VALUES('Learning');

COMMIT;