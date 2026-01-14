CREATE TABLE categories (
    id INTEGER PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE projects (
    id UUID PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(500) NOT NULL,
    category_id INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk__projects_categories
      FOREIGN KEY (category_id)
          REFERENCES categories(id)
          ON DELETE SET NULL
);

CREATE TABLE milestones (
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk__milestones_projects
      FOREIGN KEY (project_id)
          REFERENCES projects(id)
          ON DELETE CASCADE
);

INSERT INTO categories(id,name) VALUES(1,'Software');
INSERT INTO categories(id,name) VALUES(2,'Content Creation');
INSERT INTO categories(id,name) VALUES(3,'Learning');