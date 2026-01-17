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
    updated_at TIMESTAMP NOT NULL,
	CONSTRAINT fk__projects_categories
		FOREIGN KEY (category_id)
		REFERENCES categories(id)
		ON DELETE SET NULL
);